package com.plf.common.file;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author panlf
 * @date 2021/12/25
 */
public class RandomAccessFileTest {

    // RandomAccessFile
    // 指定的位置开始读

    @Test
    public void read() throws Exception {
        File file = new File("D:\\TempData\\file\\hello.txt");

        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
        //读取一个字节
        randomAccessFile.read();
        // 相对当前读取的位置，跳过指定的字节数
        randomAccessFile.skipBytes(5);
        // 绝对位置，相对于文件的开始位置
        //randomAccessFile.seek(5);
        byte[] bytes = new byte[1024 * 8];

        //读取文件的内容
        int len = randomAccessFile.read(bytes);
        System.out.println(len);

        System.out.println(new String(bytes,0, len));
    }
    @Test
    public void write() throws Exception {
        File file = new File("D:\\TempData\\file\\hello.txt");

        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");

        randomAccessFile.seek(6);
        randomAccessFile.write("Java RandomAccess".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void testSingle() throws Exception {
        File file = new File("D:\\TempData\\file\\1.mp4");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\TempData\\file\\2.mp4");

        byte[] bytes = new byte[1024 * 8];
        int len = -1;
        long begin = System.currentTimeMillis();
        while((len = fileInputStream.read(bytes)) != -1){
            fileOutputStream.write(bytes,0,len);
        }
        //拷贝文件共消耗了1221
        System.out.println("拷贝文件共消耗了"+(System.currentTimeMillis()-begin));
    }

    @Test
    public void testThread() throws Exception {
        File file = new File("D:\\TempData\\file\\1.mp4");
        //开启5个线程
        int threadNum = 5;
        //获取文件的大小
        long length = file.length();
        // 每一个线程读取的大小
        int part = (int) Math.ceil(length/threadNum);



        List<Thread> threadList = new ArrayList<>();

        for(int i=0;i<threadNum;i++){
            final int k = i;
            Thread thread =  new Thread(()->{
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
                    RandomAccessFile outFile = new RandomAccessFile("D:\\TempData\\file\\3.mp4","rw");
                    randomAccessFile.seek(k * part);
                    outFile.seek(k * part);
                    byte[] bytes = new byte[1024 * 8];
                    int len = -1,plen = 0;
                    while(true){
                        len = randomAccessFile.read(bytes);
                        if(len == -1){
                            break;
                        }
                        //如果不等于-1,则累加求和
                        plen += len;

                        //将读取到的数据，进行写入
                        outFile.write(bytes,0,len);

                        if(plen >= part){
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            threadList.add(thread);
        }
        long begin = System.currentTimeMillis();
        for(Thread t:threadList){
            t.join(); //将线程加入，并阻塞主线程
        }
        long end = System.currentTimeMillis();
        //拷贝文件共消耗了564
        System.out.println("拷贝文件共消耗了"+(System.currentTimeMillis()-begin));
    }


    @Test
    public void testInterruptThread() throws Exception {
        File file = new File("D:\\TempData\\file\\file.zip");
        //开启5个线程
        int threadNum = 3;
        //获取文件的大小
        long length = file.length();
        // 每一个线程读取的大小
        int part = (int) Math.ceil(length/threadNum);

        final Map<Integer,Integer> map = new ConcurrentHashMap<>();

        String[] dataArr = null;
        // 读取日志文件的数据
        File dataFile = new File("D:\\TempData\\file\\file.log");
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
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
                    RandomAccessFile outFile = new RandomAccessFile("D:\\TempData\\file\\file_copy.zip","rw");
                    logFile = new RandomAccessFile("D:\\TempData\\file\\file.log","rw");

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
        long begin = System.currentTimeMillis();
        for(Thread t:threadList){
            t.join(); //将线程加入，并阻塞主线程
        }
        long end = System.currentTimeMillis();

        if(dataFile.exists()){
            dataFile.delete();
        }
        //拷贝文件共消耗了564
        System.out.println("拷贝文件共消耗了"+(System.currentTimeMillis()-begin));
    }

    @Test
    public void test(){
        try {
            FileUploadUtils.transportFile("D:\\TempData\\file\\file.zip","D:\\TempData\\file\\file_copy.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
