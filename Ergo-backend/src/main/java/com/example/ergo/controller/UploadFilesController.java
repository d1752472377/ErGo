package com.example.ergo.controller;

import com.example.ergo.config.Result;
import com.example.ergo.util.FileUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

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
    public Result vMdEditor(@RequestParam(value = "file") MultipartFile file){
        try{
            return Result.success(FileUtils.uploadImg(file,
                    "C:\\Users\\17524\\Desktop\\文档\\Redis\\代码\\GitHub\\ErGo\\Ergo-backend\\src\\main\\resources\\public\\img\\",
                    Objects.requireNonNull(file.getOriginalFilename()),
                    "http://localhost:8080/img/"));
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail("图片上传失败");
        }
    }
}
