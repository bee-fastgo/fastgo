package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.job.core.util.IpUtil;
import com.bee.team.fastgo.utils.FileUploadUtil;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.exception.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.spring.simple.development.support.exception.ResponseCode.RES_DATA_NOT_EXIST;
import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @author xqx
 * @date 2020/7/27 17:04
 * @desc 文件管理
 **/
@RequestMapping("/file")
@RestController
@Api(tags = "文件管理")
public class FileController {
    @Value("${server.file.root.parh}")
    private String fileRootPath;
    @Value("${server.file.static.path}")
    private String mapPath;
    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传")
    @ApiImplicitParam(name = "file", value = "文件", dataTypeClass = String.class)
    public ResBody uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "上传的文件为空");
        }
        //定义要上传文件 的存放路径
        String localPath = fileRootPath;
        //获得文件名字
        String fileName = file.getOriginalFilename();
        if (FileUploadUtil.upload(file, localPath, fileName)) {
            // 得到去掉了uri的路径
            String url = request.getScheme() + "://" + IpUtil.getIp() + ":" + port + request.getContextPath() + mapPath + fileName;
            System.out.println(url);
            return new ResBody().buildSuccessResBody(url);
        }
        return ResBody.buildFailResBody();
    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "文件下载")
    @ApiImplicitParam(name = "filePath", value = "文件地址", dataTypeClass = String.class)
    public ResBody downloadFile(String filePath, HttpServletResponse response) throws IOException {
        URL url = new URL(filePath);
        File file = new File(url.getFile());
        FileUtils.copyURLToFile(url, file);
        //判断文件是否存在
        if (!file.exists()) {
            throw new GlobalException(RES_DATA_NOT_EXIST, "文件不存在");
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(file.getName(), "UTF-8"));
        FileUtils.copyFile(file, response.getOutputStream());
        return new ResBody().buildSuccessResBody();
    }
}



