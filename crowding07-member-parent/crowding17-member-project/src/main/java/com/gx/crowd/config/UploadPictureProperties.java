package com.gx.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 21:21
 */
@ConfigurationProperties(prefix = "aliyun.oss")
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadPictureProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String bucketDomain;
    private InputStream inputStream;
    private String originalName;
}
