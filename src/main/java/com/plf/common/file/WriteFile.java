package com.plf.common.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 写入文件的方式
 * @author panlf
 * @date 2021/8/29
 */
public class WriteFile {
    public static void fileWriterMethod(String filepath, String content) {
        try {
            FileWriter fileWriter = new FileWriter(filepath);
            fileWriter.append(content);
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void bufferedWriterMethod(String filepath, String content) {
        try  {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath));
            bufferedWriter.write(content);
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void printWriterMethod(String filepath, String content)  {
        try  {
            PrintWriter printWriter = new PrintWriter(new FileWriter(filepath));
            printWriter.print(content);
            printWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void fileOutputStreamMethod(String filepath, String content) {
        try  {
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            byte[] bytes = content.getBytes();
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void bufferedOutputStreamMethod(String filepath, String content) {
        try  {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(filepath));
            bufferedOutputStream.write(content.getBytes());
            bufferedOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void filesWrite(String filepath, String content) throws IOException {
        Files.write(Paths.get(filepath), content.getBytes());
    }
        public static void main(String[] args) {
        //WriteFile.fileWriterMethod("D:\\TempData\\1.txt ","000");
        //WriteFile.bufferedWriterMethod("D:\\TempData\\1.txt ","111");
        //WriteFile.printWriterMethod("D:\\TempData\\1.txt ","222");
        //WriteFile.fileOutputStreamMethod("D:\\TempData\\1.txt ","333");
          WriteFile.bufferedOutputStreamMethod("D:\\TempData\\1.txt ","444");
    }
}
