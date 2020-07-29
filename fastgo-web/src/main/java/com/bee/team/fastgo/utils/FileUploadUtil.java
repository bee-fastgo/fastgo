package com.bee.team.fastgo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author xqx
 * @date 2020/7/28 15:11
 * @desc 文件上传工具
 **/

public class FileUploadUtil {
    public static boolean upload(MultipartFile file, String path, String fileName) {

        // 生成新的文件名
        String realPath = path + fileName;
        //使用原文件名
        // String realPath = path + "/" + fileName;
        File dest = new File(realPath);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
