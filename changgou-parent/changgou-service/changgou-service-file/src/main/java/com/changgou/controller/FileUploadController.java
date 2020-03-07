package com.changgou.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class FileUploadController {
    @PostMapping
   public Result upload(@RequestParam(value = "file")MultipartFile file){
        //封装文件信息
        String url = null;//上传成功后返回的URL地址
        try {
            FastDFSFile fastDFSFile = new FastDFSFile(
                    file.getOriginalFilename(),// 文件名
                    file.getBytes(),//文件字节数组
                    StringUtils.getFilenameExtension(file.getOriginalFilename())
            );
            String[] uploads = FastDFSUtil.upload(fastDFSFile);
           // url = "http://192.168.200.128:8080/"+uploads[0]+"/"+uploads[1];
            url = FastDFSUtil.getTrackerInfo()+"/"+uploads[0]+"/"+uploads[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new Result(true,StatusCode.OK,"上传成功",url);

    }
}
