package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.addCache;
import com.baizhi.annotation.deleteCache;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.City;
import com.baizhi.entity.Mc;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @addCache     //添加缓存
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> queryAll(Integer page, Integer rows) {
        int start = (page - 1) * rows;
        return userDao.queryAll(start, rows);
    }

    @addCache     //添加缓存
    //查所有数据
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer queryConut() {
        return userDao.count();
    }



    @AddLog("修改用户状态")
    //修改
    @deleteCache   //删除缓存
    @Override
    public void update(User user) {
        User users = userDao.queryOne(user.getId());
        System.out.println(users);
        if(users.getStatus().equals("0")){
            users.setStatus("1");
            userDao.update(users);
        }else{
            users.setStatus("0");
            userDao.update(users);
        }
    }

    @addCache     //添加缓存
    //查一个
    @Override
    public User queryOne(String id) {
        return userDao.queryOne(id);
    }

    //添加
    @deleteCache   //删除缓存
    @Override
    public String add(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        user.setStatus("0");   //状态
        user.setCreatDate(new Date());  //上传日期
        userDao.add(user);
        return id;
    }

    @addCache     //添加缓存
    //查所有
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> queryAlls() {
        return userDao.queryAlls();
    }


    //查各个性别 分布的 地区
    @Override
    public List<City> queryAllSexCity(String sex) {
        List<City> lists = userDao.queryAllSexCity(sex);
        return lists;
    }

    //查每个月  各个性别 的注册人数
    @Override
    public List<Mc> queryAllMonth(String sex,Integer month) {
        return userDao.queryAllMonth(sex,month);
    }











    /*//添加图片到阿里云
    @Override
    public void UploadAliyun(MultipartFile picImg, String id) {
        //获取上传的图片名
        String filename = picImg.getOriginalFilename();
        //重新截取文件名
        String newName = new Date().getTime()+"-"+filename;

        String[] split = newName.split("\\.");
        //拼接图片名
        String coverName = split[0] + ".jpg";

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4Fz1ybFV6tpdmCN9gXPm";
        String accessKeySecret = "QEdHTJ1d9pg7hrYAvqRLtHDAfZbLKP";
        String yourBucketName = "yingx-205";    //存储空间名字

        String yourObjectName = "流云.mp4";    //保存的文件名
        String yourLocalFile = "";  //本地路径

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



    }*/
}
