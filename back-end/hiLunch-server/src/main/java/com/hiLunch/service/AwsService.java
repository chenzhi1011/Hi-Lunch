package com.hiLunch.service;


import org.springframework.web.multipart.MultipartFile;

public interface AwsService {
    String upLoadFile(MultipartFile multipartFile);
}
