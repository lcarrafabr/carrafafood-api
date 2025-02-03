package com.carrafasoft.carrafafood.infrastructure.service.email;

import com.carrafasoft.carrafafood.core.email.EmailProperties;
import com.carrafasoft.carrafafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

//@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    private static final String NAO_FOI_POSSIVEL_ENVIAR_EMAIL = "Não foi possível enviar o e-mail";
    private static final String NAO_FOI_POSSIVEL_MONTAR_TEMPLATE = "Não foi possível monotar o template do e-mail";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freeMakerConfig;

    @Override
    public void enviar(Mensagem mensagem) {

        try {

            String corpo = processarTemplate(mensagem);

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(corpo, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException(NAO_FOI_POSSIVEL_ENVIAR_EMAIL, e);
        }
    }

    String processarTemplate(Mensagem mensagem) {

        try {
            Template templete =  freeMakerConfig.getTemplate(mensagem.getCorpo());
            return FreeMarkerTemplateUtils.processTemplateIntoString(templete, mensagem.getVariaveis());

        } catch (Exception e) {
            throw new EmailException(NAO_FOI_POSSIVEL_MONTAR_TEMPLATE, e);
        }
    }
}
