package com.hiLunch.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.hiLunch.properties.AwsProperties;
import com.hiLunch.service.AwsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.UUID;

@Service
@Slf4j
public class AwsServiceImpl implements AwsService {
    @Autowired
    AwsProperties awsProperties;
    @Autowired
    AmazonS3 amazonS3;


    @Override
    public String upLoadFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new RuntimeException("File is empty！");
        }
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());
            String bucketName = awsProperties.getBucketName();
            //get the format of file
            String originalFilename = multipartFile.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new RuntimeException("Invalid file format!");
            }
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String key = UUID.randomUUID().toString()+suffix ;
            // if bucket does not exist,create first
            if (!amazonS3.doesBucketExistV2(bucketName)) {
                amazonS3.createBucket(bucketName);
            }
            PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(bucketName, key, multipartFile.getInputStream(), objectMetadata));
            // upload successfully
            if (null != putObjectResult) {
                GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, key);
                URL url = amazonS3.generatePresignedUrl(urlRequest);
                // return url
                return url.toString();
            }
        } catch (Exception e) {
            log.error("Upload files to the bucket,Failed:{}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
