package com.takaki.recruit.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Takaki
 * @date 2022/6/15
 */
@Async
@Slf4j
@Component
public class AsyncTask {

    public void sendMail(JavaMailSender sender, SimpleMailMessage message) {
        log.info("创建发送邮件任务");
        sender.send(message);
    }
}
