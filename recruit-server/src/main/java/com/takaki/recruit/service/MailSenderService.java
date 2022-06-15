package com.takaki.recruit.service;

import com.takaki.recruit.entity.dto.mail.MailReceiver;

import java.util.concurrent.ExecutionException;

/**
 * @author Takaki
 * @date 2022/6/15
 */
public interface MailSenderService {

    /**
     * 发送邮箱验证码
     * @param receiver 邮件接收者
     * @return 验证码
     */
    String sendCode(MailReceiver receiver) throws ExecutionException, InterruptedException;
}
