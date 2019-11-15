package com.atguigu.gmall.auth.config;


import com.atguigu.core.utils.RsaUtils;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@Data
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {
/*
* auth:
  jwt:
    publicKeyPath: D:\\project-0615\\tem\\rsa.pub
    privateKeyPath: D:\\project-0615\\tem\\rsa.pri
    expire: 180 #单位是分钟
    cookieName: GMALL_TOKEN
* */

    private String publicKeyPath;
    private String privateKeyPath;
    private Integer expire;
    private String cookieName;
    private String secret;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @PostConstruct
    public void init(){

        try {
            //1.初始化公私钥文件
            File publicFile = new File(publicKeyPath);
            File privateFile = new File(privateKeyPath);
            //2.检查文件对象是否为空
            if(!publicFile.exists()||!privateFile.exists()){
                RsaUtils.generateKey(publicKeyPath,privateKeyPath,secret);
            }
            //3.读取密码
            RsaUtils.getPrivateKey(publicKeyPath);
            RsaUtils.getPrivateKey(privateKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥和私钥失败");
            e.printStackTrace();
        }
    }
}
