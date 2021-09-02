package com.plf.common.command;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author panlf
 * @date 2021/9/2
 */
public class ExecuteCommand {

    private final static Runtime run = Runtime.getRuntime();

    /**
     * 执行命令，返回执行结果
     * @param cmd
     * @param charsetName
     * @return
     */
    public static String executeCommand(String cmd,String charsetName){

        if(charsetName==null){
            charsetName = "utf-8";
        }

        StringBuffer result = new StringBuffer();

        String[] cmdArr = new String[]{"sh","-c",cmd};
        try {
            Process process = run.exec(cmdArr);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,charsetName);
            BufferedReader br = new BufferedReader(isr);
            String content = "";
            while ((content =br.readLine())!= null) {
                result.append(content);
            }
        } catch (Exception e) {
            return result.toString();
        }
        return result.toString();
    }


    /**
     * 执行结果，返回是否执行成功
     * @param cmd
     * @return
     */
    public static boolean executeCommand(String cmd){
        boolean result = true;
        try {
            String[] cmdArr = new String[]{"sh","-c",cmd};
            Process process = run.exec(cmdArr);
            //执行结果 0 表示正常退出
            int exeResult=process.waitFor();
            if(exeResult!=0){
                result = false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return result;
    }

}
