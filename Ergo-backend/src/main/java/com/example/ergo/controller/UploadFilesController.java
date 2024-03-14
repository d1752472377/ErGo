package com.example.ergo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ergo.util.FileUtils;
import com.example.ergo.util.OkHttpClientUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @date 2024/2/27
 * 描述：
 */
@Tag(name = "图片上传管理")
@RestController()
@Slf4j
@CrossOrigin
@RequestMapping("/files")

public class UploadFilesController {
    @Operation(summary = "图片上传")
    @PostMapping("/vMdEditor")
    public Object vMdEditor(@RequestParam(value = "file") MultipartFile file) throws Exception {
        File localFile = FileUtils.multipartFileToFile(file);
        String filename = file.getOriginalFilename();

        String url = "https://p.sda1.dev/api/v1/upload_external_noform"+"?"+"filename="+filename;
        System.out.println(url);
        String s = OkHttpClientUtil.doPostFile(url, localFile);
        localFile.delete();
        JSONObject jsonObject =  JSON.parseObject(s);
        return jsonObject;
    }
//    @PostMapping("/ceshi")
//    public String liulang(@RequestParam(value = "file")MultipartFile file)throws Exception{
//
//    }
//    public String ceshi(File file) throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/octet-stream");
//        RequestBody body = RequestBody.create(mediaType, file);
//        Request request = new Request.Builder()
//                .url("https://p.sda1.dev/api/v1/upload_external_noform?filename=132.jpg")
//                .method("POST", body)
//                .addHeader("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
//                .build();
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }
}
