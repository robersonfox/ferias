package br.com.robersonfox.security.jwtsecurity.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {

    @Autowired
    private Environment env;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(env.getProperty("host"));
        mailSender.setPort(env.getProperty("port", Integer.class));
        mailSender.setUsername(env.getProperty("username"));
        mailSender.setPassword(env.getProperty("password"));        


        Properties p = new Properties();
        p.put("mail.transport.protocol", "smtp");
        p.put("mail.smtp.AUTH", true);
        p.put("mail.smtp.starttls.enable", true);
        p.put("mail.smtp.connectiontimeout", 10000);
        
        mailSender.setJavaMailProperties(p);
        return mailSender;        
    }
}