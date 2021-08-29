package io.github.ehlxr.config;


import io.github.ehlxr.util.SendAlarmUtil;
import io.github.ehlxr.util.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目初始化
 *
 * @author ehlxr
 * @since 2020/4/22.
 */
@Component
public class Initialization implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(Initialization.class);

    @Value("${aliyun.oss.endpoint:}")
    private String endpoint;
    @Value("${aliyun.oss.accesskey.id:}")
    private String accessKeyId;
    @Value("${aliyun.oss.accesskey.secret:}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucket:}")
    private String bucket;
    @Value("${aliyun.oss.key-path:}")
    private String keyPath;
    @Value("${aliyun.oss.url-lifetime:0}")
    private Long urlLifetime;

    @Value("${lk.jmeter.cmd:}")
    private String jmeterCmd;
    @Value("${location:}")
    private String location;

    @Value("${lk.dingtalk-url:}")
    private String dingtalkUrl;


    @Override
    public void run(ApplicationArguments args) {
        // 初始化阿里云 OSS 配置信息
        // AliyunOssConfig config = AliyunOssConfig.newBuilder()
        //         .endpoint(endpoint)
        //         .accessKeyId(accessKeyId)
        //         .accessKeySecret(accessKeySecret)
        //         .bucketName(bucket)
        //         .keyPath(keyPath)
        //         .urlLifetime(urlLifetime)
        //         .build();
        // log.info("aliyun oss config {}", config);
        // FileUploadUtil.init(config);
        //
        // if (!"develop".equals(location) && !"PRO2".equals(location)) {
        //     try {
        //         ExecShell.execShellCommand(jmeterCmd);
        //     } catch (Exception e) {
        //         log.error("exec jmeter cmd error {}", e.getMessage());
        //     }
        // }

        // 预警信息发送初始化
        Try.of(() -> SendAlarmUtil.init(dingtalkUrl))
                .trap(e -> log.warn("int SendAlarmUtil error: {}", e.getMessage()))
                .run();
    }
}
