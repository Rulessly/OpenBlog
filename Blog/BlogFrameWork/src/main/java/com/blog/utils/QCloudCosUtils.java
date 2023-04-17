package com.blog.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class QCloudCosUtils implements InitializingBean {


    //API密钥secretId
    @Value("${qcloud.secretId}")
    private String secretId;
    //API密钥secretKey
    @Value("${qcloud.secretKey}")
    private String secretKey;
    //存储桶所属地域
    @Value("${qcloud.region}")
    private String region;
    //存储桶空间名称
    @Value("${qcloud.bucketName}")
    private String bucketName;
    //存储桶访问域名
    @Value("${qcloud.url}")
    private String url;
    //上传文件前缀路径(eg:/images/)
    @Value("${qcloud.prefix}")
    private String prefix;


    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    public static String URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT=region;
        ACCESS_KEY_ID=secretId;
        ACCESS_KEY_SECRET=secretKey;
        BUCKET_NAME=bucketName;
        URL=url;

    }
}
