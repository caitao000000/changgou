package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/****
 * 文件操作工具类
 */
public class FastDFSUtil {
    /*  加载tracker连接信息
    * */
    static{

        try {
            //查找classpath下的文件路径
            String filename = new ClassPathResource("fdfs_client.conf").getPath();
            //加载tracker连接信息
            ClientGlobal.init(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****
     * 文件上传
     */
    public static String[] upload(FastDFSFile fastDFSFile){
        //附加参数
        NameValuePair[] metalist = new NameValuePair[1];
        metalist[0] = new NameValuePair("author",fastDFSFile.getAuthor());
        /***
         * 文件上传后的返回值
         * uploadResults[0]:文件上传所存储的组名，例如:group1
         * uploadResults[1]:文件存储路径,例如：M00/00/00/wKjThF0DBzaAP23MAAXz2mMp9oM26.jpeg
         */
        String[] uploadResult = null;
        try{
            //创建一个Tracker访问的客户端对象TrackerClient
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient访问TrackerServer服务，获取连接信息
            TrackerServer trackerServer = trackerClient.getConnection();
            //通过TrackerServer的连接信息可以获取Storage的连接信息，创建StorageClient对象，存储Storage的连接信息
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //通过StorageClient访问Storage，实现文件上传，并且获取文件上传后的存储信息
             uploadResult = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), metalist);
        }catch (Exception e){
            e.printStackTrace();
        }
        return uploadResult;
    }
        /****
         * @param groupName
         * @param remoteFileName
         */
        public static FileInfo getFile(String groupName,String remoteFileName) throws Exception {
            //创建一个Tracker访问的客户端对象TrackerClient
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient访问TrackerServer服务，获取连接信息
            TrackerServer trackerServer = trackerClient.getConnection();
            //通过TrackerServer的连接信息可以获取Storage的连接信息，创建StorageClient对象，存储Storage的连接信息
            StorageClient storageClient = new StorageClient(trackerServer, null);
            FileInfo file_info = storageClient.get_file_info(groupName, remoteFileName);
            return file_info;
        }

    /**
     * 文件下载  http://192.168.200.128:8080/group1/M00/00/00/wKjIgF5g5r6AW2MSAATnz4NmpuE629.jpg
     * @param groupName group1
     *  @param    remoteFileName M00/00/00/wKjIgF5g5r6AW2MSAATnz4NmpuE629.jpg
     */
    public static  InputStream  downloadFile(String groupName,String remoteFileName)throws  Exception{
        //创建一个Tracker访问的客户端对象TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient访问TrackerServer服务，获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //通过TrackerServer的连接信息可以获取Storage的连接信息，创建StorageClient对象，存储Storage的连接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        byte[] bytes = storageClient.download_file(groupName, remoteFileName);
        return  new ByteArrayInputStream(bytes);
    }

    /**
     * 文件下载  http://192.168.200.128:8080/group1/M00/00/00/wKjIgF5g5r6AW2MSAATnz4NmpuE629.jpg
     * @param groupName group1
     *  @param    remoteFileName M00/00/00/wKjIgF5g5r6AW2MSAATnz4NmpuE629.jpg
     */
    public static void deleteFile(String groupName,String remoteFileName)throws  Exception{
        //创建一个Tracker访问的客户端对象TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient访问TrackerServer服务，获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //通过TrackerServer的连接信息可以获取Storage的连接信息，创建StorageClient对象，存储Storage的连接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        storageClient.delete_file(groupName,remoteFileName);
    }

    /****
     * 获取Tracker信息
     * @param
     */
    public static String getTrackerInfo() throws Exception{
        //创建一个Tracker访问的客户端对象TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient访问TrackerServer服务，获取连接信息
        TrackerServer trackerServer = trackerClient.getConnection();
        //Tracker的IP和端口
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int tracker_http_port = ClientGlobal.getG_tracker_http_port();
        String url = "http://"+ip+":"+tracker_http_port;
        return url;
    }

    public static void main(String[] args) throws Exception {
        //获取文件信息
//        FileInfo fileInfo = getFile("group1", "M00/00/00/wKjIgF5g5r6AW2MSAATnz4NmpuE629.jpg");
//        System.out.println(fileInfo.getFileSize());
//        System.out.println(fileInfo.getSourceIpAddr());

        //文件下载
//        InputStream is = downloadFile("group1", "M00/00/00/wKjIgF5g5r6AW2MSAATnz4NmpuE629.jpg");
////        //将文件写入磁盘
////        FileOutputStream os = new FileOutputStream("D:/caitao.jpg");
////        // 定义一个缓冲区
////        byte[] buffer = new byte[1024];
////        while(is.read(buffer)!= -1){
////            os.write(buffer);
////        }
////        os.flush();
////        os.close();
////        is.close();
        System.out.println(getTrackerInfo());


    }
}
