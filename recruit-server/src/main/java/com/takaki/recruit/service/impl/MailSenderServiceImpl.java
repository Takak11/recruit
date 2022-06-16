package com.takaki.recruit.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.takaki.recruit.constant.ResponseStateConstant;
import com.takaki.recruit.entity.dto.mail.MailReceiver;
import com.takaki.recruit.entity.dto.mail.MailVerification;
import com.takaki.recruit.entity.po.MailEntity;
import com.takaki.recruit.exception.BusinessBaseException;
import com.takaki.recruit.mapper.MailMapper;
import com.takaki.recruit.service.MailSenderService;
import com.takaki.recruit.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author Takaki
 * @date 2022/6/15
 */
@Service
public class MailSenderServiceImpl implements MailSenderService {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private MailMapper mailMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AsyncTask task;

    private ConcurrentLinkedQueue<Runnable> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public String sendCode(MailReceiver receiver) {


        String to = receiver.getMail();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);

        message.setSubject("山石在线招聘验证码：");

        String code = RandomUtil.randomString(6);
        message.setText("您的验证码为：" + code + "\n" + "验证码30分钟内有效，请不要泄露给他人");

        this.taskQueue.add( () -> task.sendMail(mailSender, message));

        return this.insertIntoTable(receiver.getMail(), code) ? code : null;
    }


    private boolean insertIntoTable(String email, String code) {
        List<MailEntity> list = mailMapper.selectByMap(MapUtil.of("email", email));

        if (list.size() == 1) {
            MailEntity mailEntity = list.get(0);
            mailEntity.setCode(code);
            mailMapper.updateById(mailEntity);
            this.taskQueue.poll().run();

            return true;
        }

        if (list.size() > 1) {
            list.forEach(record -> mailMapper.deleteById(record.getId()));
        }

        MailEntity mailEntity = new MailEntity();
        mailEntity.setEmail(email);
        mailEntity.setCode(code);

        mailMapper.insert(mailEntity);
        this.taskQueue.poll().run();
        return true;
    }
}
