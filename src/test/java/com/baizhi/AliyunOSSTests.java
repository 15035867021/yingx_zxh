package com.baizhi;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.baizhi.entity.Video;
import com.baizhi.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

@SpringBootTest
class AliyunOSSTests {
    @Resource
    private VideoService videoService;

    @Test
        //创建存储空间
    void contextLoads() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fz1ybFV6tpdmCN9gXPm";
        String accessKeySecret = "QEdHTJ1d9pg7hrYAvqRLtHDAfZbLKP";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建CreateBucketRequest对象。  //
        CreateBucketRequest createBucketRequest = new CreateBucketRequest("yingx-2020");

        // 如果创建存储空间的同时需要指定存储类型以及数据容灾类型, 可以参考以下代码。
        // 此处以设置存储空间的存储类型为标准存储为例。
        // createBucketRequest.setStorageClass(StorageClass.Standard);
        // 默认情况下，数据容灾类型为本地冗余存储，即DataRedundancyType.LRS。如果需要设置数据容灾类型为同城冗余存储，请替换为DataRedundancyType.ZRS。
        // createBucketRequest.setDataRedundancyType(DataRedundancyType.ZRS)

        // 创建存储空间。
        ossClient.createBucket(createBucketRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //列举所有存储空间
    @Test
    void testQueryBucket() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fz1ybFV6tpdmCN9gXPm";
        String accessKeySecret = "QEdHTJ1d9pg7hrYAvqRLtHDAfZbLKP";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 列举存储空间。
        List<Bucket> buckets = ossClient.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(" - " + bucket.getName());
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //上传本地文件
    @Test
    void testUpload() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fz1ybFV6tpdmCN9gXPm";
        String accessKeySecret = "QEdHTJ1d9pg7hrYAvqRLtHDAfZbLKP";
        String yourBucketName = "yingx-205";    //存储空间名字
        String yourObjectName = "创建存储空间.png";    //保存的文件名
        String yourLocalFile = "G:\\framework\\10.后期项目\\后期项目\\Day6 类别实现，视频设计\\笔记\\阿里云存储\\笔记\\img\\创建存储空间.png";  //本地路径

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(yourBucketName, yourObjectName, new File(yourLocalFile));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }


    @Test
        //上传本地视频
    void testuploadVideo() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fz1ybFV6tpdmCN9gXPm";
        String accessKeySecret = "QEdHTJ1d9pg7hrYAvqRLtHDAfZbLKP";
        String yourBucketName = "yingx-205";    //存储空间名字

        String yourObjectName = "流云.mp4";    //保存的文件名
        String yourLocalFile = "G:\\framework\\10.后期项目\\后期项目\\Day6-3 视频设计\\流云.mp4";  //本地路径

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(yourBucketName, yourObjectName, new File(yourLocalFile));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //删除  视频
    @Test
    void testdeleteVideo() {
        Video video = new Video();
        video.setId("396e69f7-1124-4485-ba94-0dff6e8396e8");
        videoService.deleteAliyun(video);
    }

    //修改 视频
    @Test
    void testupdateVideo() {
        Video video1 = videoService.selectOne("39c93acb-6aaa-4d9e-9039-fe92b0fbddd9");
        System.out.println("=============================" + video1);
        video1.setTitle("啊啊啊");
        videoService.update(video1);
    }

    //视频截取
    @Test
    void test() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fz1ybFV6tpdmCN9gXPm";
        String accessKeySecret = "QEdHTJ1d9pg7hrYAvqRLtHDAfZbLKP";
        String yourBucketName = "yingx-205";    //存储空间名字
        String yourObjectName = "火花.mp4";    //保存的文件名
//        String yourLocalFile = "G:\\framework\\10.后期项目\\后期项目\\Day6-3 视频设计\\火花.mp4";  //本地路径
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_50000,f_jpg,w_800,h_600";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(yourBucketName, yourObjectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);


        //进行上传  网络流
        //uploadInteger(signedUrl.toString());

        // 关闭OSSClient。
        ossClient.shutdown();

    }


    void uploadInteger(String signedUrl) {
        //上传网络流
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fz1ybFV6tpdmCN9gXPm";
        String accessKeySecret = "QEdHTJ1d9pg7hrYAvqRLtHDAfZbLKP";
        String yourBucketName = "yingx-205";    //存储空间名字
        String yourObjectName = "aaa/火花.jpg";    //保存的文件名

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传网络流。
        InputStream inputStream = null;
        try {
            inputStream = new URL(signedUrl).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.putObject(yourBucketName, yourObjectName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
