package com.mmr.wordtalk.app.trigger;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailTrigger {

    @Lazy
    @Autowired
    private MailSender mailSender;

    // 注册时向传入的邮箱发送验证码的方法
    @Async
    public void sendRegisterCode(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        String template = "{} 验证码十分钟内有效";
        message.setFrom("zero@zwany.live");
        message.setTo(email);
        message.setSubject("欢迎注册WordTalk");
        message.setText(StrUtil.format(template, code));
        try {
            mailSender.send(message);
        } catch (Exception e) {
            log.error("发送邮件出错，错误信息为:[{}]", e.getMessage(), e);
        }
    }
}
