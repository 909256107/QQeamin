package com.itdfq.qqemail.Controller;

import com.itdfq.qqemail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DFQ on 2020/10/16 14:03
 */
@RestController
@RequestMapping("/dfq")
public class EmailController {

    @Autowired
    private MailService mailService;
    @Autowired
    private TemplateEngine templateEngine;

    Map<String,Object> map = new HashMap<>();

    /**
     * 发送简单纯文本邮件
     */
    @RequestMapping("/sendTest")
    public Map<String,Object> sendSimpleMail() {
        map.clear();
        try {
            mailService.sendSimpleMail("1184683152@qq.com", "发送邮件测试", "大家好，这是我用springboot进行发送邮件测试");
            map.put("msg",1);
        } catch (Exception e) {
            map.put("msg",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 发送HTML邮件
     */
    @RequestMapping("/sendHtml")
    public  Map<String,Object> sendHtmlMail() {
        map.clear();
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件" + "</font></h3></body></html>";
        try {
            map.put("msg",1);
            mailService.sendHtmlMail("917095152@qq.com", "发送邮件测试", content);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg",e.getMessage());
        }
        return map;
    }

    /**
     * 发送带附件的邮件
     */
    @RequestMapping("/sendFu")
    public  Map<String,Object> sendAttachmentMail() {
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件，有附件哦" + "</font></h3></body></html>";
        String filePath = "static/index.html";
        try {
            mailService.sendAttachmentMail("917095152@qq.com", "发送邮件测试", content, filePath);
            map.put("msg",1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg",e.getMessage());
        }
        return map;
    }

    /**
     * 发送带图片的邮件
     */
    public void sendInlineResourceMail() {
        String rscPath = "your picture path";
        String rscId = "skill001";
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件，有图片哦" + "</font></h3>"
                + "<img src=\'cid:" + rscId + "\'></body></html>";
        mailService.sendInlineResourceMail("receiver@email.com", "发送邮件测试", content, rscPath, rscId);
    }

    /**
     * 指定模板发送邮件
     */
    @RequestMapping("/send")
    public Map<String,Object> testTemplateMail() {
        //向Thymeleaf模板传值，并解析成字符串
        Context context = new Context();
        context.setVariable("id", "001");
        String emailContent = templateEngine.process("emailTemplate", context);
        map.clear();
        try {
            mailService.sendHtmlMail("917095152@qq.com", "这是一个模板文件", emailContent);
            map.put("msg",1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg",e.getMessage());
        }
        return map;
    }
}
