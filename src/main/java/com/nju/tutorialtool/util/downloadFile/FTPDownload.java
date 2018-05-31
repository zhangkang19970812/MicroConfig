package com.nju.tutorialtool.util.downloadFile;

import com.nju.tutorialtool.util.enums.ServerMessage;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class FTPDownload {
    /***
     * 连接ftp
     * @param url
     * @param port
     * @param username
     * @param password
     * @return
     */
    public static FtpClient connectFTP(String url, int port, String username, String password) {
        //创建ftp
        FtpClient ftp = null;
        try {
            //创建地址
            SocketAddress addr = new InetSocketAddress(url, port);
            //连接
            ftp = FtpClient.create();
            ftp.connect(addr);
            //登陆
            ftp.login(username, password.toCharArray());
            ftp.setBinaryType();
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftp;
    }

    public static InputStream download(String ftpFile) {
//        List<String> list = new ArrayList<String>();
        FtpClient ftp = connectFTP(ServerMessage.serverIp,21,"anonymous",ServerMessage.serverPassword);
        String str = "";
        InputStream is = null;
        BufferedReader br = null;
        try {
            // 获取ftp上的文件
            is = ftp.getFileStream(ftpFile);
//            //转为字节流
//            br = new BufferedReader(new InputStreamReader(is));
//            while((str=br.readLine())!=null){
//                list.add(str);
//            }
//            br.close();
        }catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

//    public static void main(String[] args) {
//        FtpClient ftp = connectFTP("114.115.137.102",21,"anonymous","stk0123STK0123");
//        List<String> list = download("/pub/account_service/account_service.iml",ftp);
//        for(int i=0;i<list.size();i++){
//            System.out.println("list "+ i + " :"+list.get(i));
//        }
//        try {
//            ftp.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
