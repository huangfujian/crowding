package com.gx.crowd.utils;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.gx.crowd.entity.vo.ReturnJson;

import javax.xml.crypto.Data;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/12 15:46
 */
public class CrowdUtils {
    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    private static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret, String domain) throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = domain;
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static ReturnJson<String> sendMessage(String accessKeyId,
                                                 String accessKeySecret,
                                                 String domain,
                                                 String signName, String templateCode, String phoneNumber) {
        try {
            com.aliyun.dysmsapi20170525.Client client = createClient(accessKeyId, accessKeySecret, domain);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                int num = (int) (Math.random() * 10);
                sb.append(num);
            }
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setPhoneNumbers(phoneNumber)
                    .setTemplateParam("{code:" + sb.toString() + "}"); //code：验证码
            RuntimeOptions runtime = new RuntimeOptions();
            // 复制代码运行请自行打印 API 的返回值
            client.sendSmsWithOptions(sendSmsRequest, runtime);
            return ReturnJson.returnSuccessWithData(sb.toString());
        } catch (Exception error) {
            // 如有需要，请打印 error
            return ReturnJson.returnFail(error.getMessage());
        }

    }

    /**
     * 文件上传
     *
     * @param endpoint
     * @param accessKeyId
     * @param accessKeySecret
     * @param bucketName
     * @param bucketDomain
     * @param inputStream
     * @param originalName
     * @return
     */
    public static ReturnJson<String> uploadPictureFile(String endpoint,
                                                       String accessKeyId,
                                                       String accessKeySecret,
                                                       String bucketName,
                                                       String bucketDomain,
                                                       InputStream inputStream,
                                                       String originalName) {
        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dateString = sdf.format(new Date());
            String fileName = UUID.randomUUID().toString().replace("-", "");
            String extensionName = originalName.substring(originalName.lastIndexOf("."));
            String objectName = dateString + "/" + fileName + extensionName;
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);
            //从响应结果中获取具体响应信息
            ResponseMessage response = putObjectResult.getResponse();
            if (response == null) {
                //没有响应说明，上传成功
                String ossFileAccessPath = bucketDomain + "/" + objectName;
                //返回成功
                return ReturnJson.returnSuccessWithData(ossFileAccessPath);
            } else {
                //获取响应状态码
                int statusCode = response.getStatusCode();
                //如果请求没有成功，获取错误信息
                String errorMessage = response.getErrorResponseAsString();
                //当前方法返回失败
                return ReturnJson.returnFail(" 当 前 响 应 状 态 码 =" + statusCode + " 错 误 消 息 =" + errorMessage);
            }
        } catch (Exception oe) {
            return ReturnJson.returnFail(oe.getMessage());
        } finally {
            ossClient.shutdown();
        }
    }
}
