package com.hiLunch.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "hilunch.paypay")
public class PaypayProperties {
    private boolean productionMode;
    private String apiKey;
    private String apiSecretKey;
    private String assumeMerchant;

}
