package com.test.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：cloud
 * 功能描述：文件服务工具类
 * 创建人： yeshiyuan
 * 创建时间：2018/4/20 9:28
 * 修改人： yeshiyuan
 * 修改时间：2018/4/20 9:28
 * 修改描述：
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    /**
     * 描述：获取指定时间内的图片
     *
     * @author 李帅
     * @created 2018年1月10日 下午7:00:19
     * @since
     * @param videoFilename:视频路径
     * @param thumbFilename:图片保存路径
     * @param width:图片长
     * @param height:图片宽
     * @param hour:指定时
     * @param min:指定分
     * @param sec:指定秒
     * @throws IOException
     * @throws InterruptedException
     */
    @SuppressWarnings("unused")
    public static void getThumb(String ffmpegUrl, String videoFilename, String thumbFilename, int width, int height, int hour,
                         int min, float sec) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegUrl, "-y", "-i", videoFilename, "-vframes", "1", "-ss",
                hour + ":" + min + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height, "-an", thumbFilename);

        Process process = processBuilder.start();

        InputStream stderr = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null)
            ;
        process.waitFor();

        if (br != null)
            br.close();
        if (isr != null)
            isr.close();
        if (stderr != null)
            stderr.close();
    }


    /**
     *
     * 描述：从输入流中获取字节数组
     *
     * @author 李帅
     * @created 2018年1月11日 上午10:19:25
     * @since
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


    /**
     *
     * 描述：从网络Url中下载文件
     *
     * @author 李帅
     * @created 2018年1月11日 上午10:19:35
     * @since
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        // 获取自己数组
        byte[] getData = FileUtil.readInputStream(inputStream);

        // 文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }
    /**
     *
     * 描述：删除文件或者文件夹
     * @author 李帅
     * @created 2017年9月23日 下午4:08:48
     * @since
     * @param path
     */
    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }

    /**
      * @despriction：删除文件路径下的所有文件
      * @author  yeshiyuan
      * @created 2018/11/27 16:44
      */
    public static void deleteByFilePath(String filePath){
        File file = new File(filePath);
        deleteAllFilesOfDir(file);
    }

    public static List<File> getFiles(String path) {
        List<File> files = new ArrayList<>();
        File file = new File(path);
        if(!file.exists()) {
            throw new RuntimeException("file path not exists : " + path);
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (File drlFile : childFiles) {
                if (drlFile.isDirectory()) {
                    files.addAll(getFiles(drlFile.getPath()));
                } else {
                    files.add(drlFile);
                }
            }
        } else if (file.isFile()) {
            files.add(file);
        }
        return files;
    }



}
