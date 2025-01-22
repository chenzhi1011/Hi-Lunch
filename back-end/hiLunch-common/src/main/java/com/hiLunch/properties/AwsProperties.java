package com.hiLunch.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "hilunch.aws")
public class AwsProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

}

