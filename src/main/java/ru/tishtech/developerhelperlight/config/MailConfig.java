package ru.tishtech.developerhelperlight.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${path.to.mail.configs.file}")
    private String path;

    private Map<String, String> mailConfigs;

    @Bean
    public JavaMailSender javaMailSender() {
        mailConfigs = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                mailConfigs.put(line.substring(0, line.indexOf("=")), line.substring(line.indexOf("=") + 1));
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailConfigs.get("host"));
        javaMailSender.setPort(Integer.parseInt(mailConfigs.get("port")));
        javaMailSender.setUsername(mailConfigs.get("username"));
        javaMailSender.setPassword(mailConfigs.get("password"));
        Properties properties = javaMailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", mailConfigs.get("protocol"));
        properties.setProperty("mail.debug", mailConfigs.get("debug"));
        return javaMailSender;
    }

    public Map<String, String> getMailConfigs() {
        return mailConfigs;
    }
}
