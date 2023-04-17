package com.blog.service.impl;

import com.blog.domain.ResponseResult;
import com.blog.service.FileService;
import com.blog.utils.QCloudCosUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public ResponseResult upload(MultipartFile file) {
        String secretId = QCloudCosUtils.ACCESS_KEY_ID;
        String secretKey = QCloudCosUtils.ACCESS_KEY_SECRET;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        Region region = new Region(QCloudCosUtils.END_POINT);
        ClientConfig clientConfig = new ClientConfig(region);

        clientConfig.setHttpProtocol(HttpProtocol.https);

        COSClient cosClient = new COSClient(cred, clientConfig);

        String bucketName = QCloudCosUtils.BUCKET_NAME;
        String key ="/img/"+ UUID.randomUUID()+file.getOriginalFilename();

        try {
            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();

            ObjectMetadata objectMetadata = new ObjectMetadata();

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

            //返回上传文件的路径
            String url=QCloudCosUtils.URL+key;
            return ResponseResult.okResult(url);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
