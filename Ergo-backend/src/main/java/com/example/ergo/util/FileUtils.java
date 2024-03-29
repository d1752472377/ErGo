package com.example.ergo.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Random;
import java.util.UUID;

/**
 * @date 2024/2/27
 * 描述：MultipartFile 转 file
 */
public class FileUtils {
    public static String uploadImg(MultipartFile file, String path1, String name, String path2){
        // 通过uuid产生一个图片名字
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-","");
        String imgName = uuid + name;
        // 这是我随机选择了一文件夹 这里只是我个人爱好
        String code = Integer.toString(new Random().nextInt(5) + 1);
        // 拼接路径
        String imgPath = path1 + "img" + code + "\\";
        String requestPath = path2 + "img" + code + "/";
        try {
            // 上传操作
            File imgFile = new File(imgPath, imgName);
            file.transferTo(imgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回图片的路就
        return requestPath + imgName;
    }
    public static File multipartFileToFile(MultipartFile file ) throws Exception {
        File toFile = null;
        if(file.equals("")||file.getSize()<=0){
            file = null;
        }else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            toFile = inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }
    private static File inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
            return file;
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return null;
    }

}
