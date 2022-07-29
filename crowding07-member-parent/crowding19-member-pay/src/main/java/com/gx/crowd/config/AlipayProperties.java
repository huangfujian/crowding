package com.gx.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 15:54
 */
@ConfigurationProperties(prefix = "ali.pay")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AlipayProperties {
    private String appId; //appId
    private String merchantPrivateKey; //私钥
    private String alipayPublicKey; //支付宝公钥
    private String notifyUrl;//通知网址
    private String returnUrl;//返回网址
    private String signType;//标志类型
    private String charset;//字符集
    private String gatewayUrl;//网关地址
}
