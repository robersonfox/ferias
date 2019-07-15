package br.com.robersonfox.security.jwtsecurity.helper;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Configurable
public class SendEmailHelper {
    private final static String url = "http://localhost:8080";

    public void sendEmailWithAttachment(String para, Long idQrcode) throws MessagingException, IOException {
        JavaMailSender javaMailSender = ApplicationContextHolder.getContext().getBean(JavaMailSender.class);
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(SendEmailHelper.class);
        ctx.refresh();
        
        helper.setFrom("robersonfox@gmail.com");
        helper.setTo(para);

        helper.setSubject("Testing from Spring Boot");

        helper.setText("<h1>Exemplo de email com qrcode inserido</h1><br>", true);
        helper.setText("<img src=\""+ url +"/rest/ferias/qrcode/"+ idQrcode +"\">", true);


		// hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));

        // helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("robersonfox@gmail.com");
        mailSender.setPassword("password");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
