package com.devandre.moviebox.user.application.service;

import com.devandre.moviebox.user.domain.model.ActivationCode;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender sender;
    private final Configuration freemarkerConfig;

    private static final String subject = "Activate your account - Daft Events";

    @Value("${spring.mail.username}")
    private String from;

    @SneakyThrows
    public void sendActivationCode(ActivationCode activationCode) {
        log.info("Sending activation code to user with email: {}", activationCode.getUser().getEmail().value());
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        Template template = freemarkerConfig.getTemplate("email.html");

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", activationCode.getUser().getFirstName());
        map.put("code", activationCode.getKey());
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setTo(activationCode.getUser().getEmail().value());
        helper.setText(html, true);

        sender.send(message);
    }

}
