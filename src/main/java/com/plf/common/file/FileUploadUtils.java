package com.plf.common.file;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author panlf
 * @date 2021/12/26
 */
public class FileUploadUtils {

    /**
     * 支持断点续传
     * @param src 拷贝的源文件
     * @param desc 拷贝的地址
     * @param threadNum  开启的线程数
     */
    public static void transportFile(File src, File desc, int threadNum) throws Exception {
        // 每一个线程读取的大小
        int part = (int) Math.ceil(src.length() / threadNum);

        final Map<Integer,Integer> map = new ConcurrentHashMap<>();

        String[] dataArr = null;

        String logName = desc.getCanonicalPath()+".log";

        // 读取日志文件的数据
        File dataFile = new File(logName);
        if(dataFile.exists()){
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            String data = reader.readLine();
            //拆分字符串
            dataArr = data.split(",");
            reader.close();
        }

        final String[] _dataArr = dataArr;

        List<Thread> threadList = new ArrayList<>();

        for(int i=0;i<threadNum;i++){
            final int k = i;
            Thread thread =  new Thread(()->{
                RandomAccessFile logFile = null;
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(src,"r");
                    RandomAccessFile outFile = new RandomAccessFile(desc,"rw");
                    logFile = new RandomAccessFile(logName,"rw");

                    randomAccessFile.seek(_dataArr==null ? k * part:Integer.parseInt(_dataArr[k]));
                    outFile.seek(_dataArr==null ? k * part:Integer.parseInt(_dataArr[k]));

                    byte[] bytes = new byte[1024 * 2];
                    int len = -1,plen = 0;
                    while(true){
                        len = randomAccessFile.read(bytes);
                        if(len == -1){
                            break;
                        }
                        //如果不等于-1,则累加求和
                        plen += len;

                        //将读取的字节数，放入到map中
                        map.put(k,plen+(_dataArr==null?k*part:Integer.parseInt(_dataArr[k])));

                        //将读取到的数据，进行写入
                        outFile.write(bytes,0,len);

                        //将map中的数据进行写入文件
                        logFile.seek(0);//直接覆盖全部文件
                        StringJoiner joiner = new StringJoiner(",");
                        map.forEach((key,val)->{
                            joiner.add(String.valueOf(val));
                        });
                        logFile.write(joiner.toString().getBytes(StandardCharsets.UTF_8));

                        if(plen+(_dataArr==null?k*part:Integer.parseInt(_dataArr[k])) >= (k+1)*part){
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(logFile!=null){
                        try {
                            logFile.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
            threadList.add(thread);
        }
        for(Thread t:threadList){
            t.join(); //将线程加入，并阻塞主线程
        }
        if(dataFile.exists()){
            dataFile.delete();
        }
    }

    /**
     * 支持断点续传
     * @param src 拷贝的源文件
     * @param desc 拷贝的地址
     */
    public static void transportFile(File src, File desc) throws Exception {
        transportFile(src,desc,5);
    }

    /**
     * 支持断点续传
     * @param src 拷贝的源文件
     * @param desc 拷贝的地址
     */
    public static void transportFile(String src, String desc) throws Exception {
        transportFile(new File(src),new File(desc),5);
    }
}
