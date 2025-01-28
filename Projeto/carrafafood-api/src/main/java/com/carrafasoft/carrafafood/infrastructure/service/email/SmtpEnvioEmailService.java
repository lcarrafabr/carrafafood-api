package com.carrafasoft.carrafafood.infrastructure.service.email;

import com.carrafasoft.carrafafood.core.email.EmailProperties;
import com.carrafasoft.carrafafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    private static final String NAO_FOI_POSSIVEL_ENVIAR_EMAIL = "Não foi possível enviar o e-mail";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Mensagem mensagem) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(mensagem.getCorpo(), true);


            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException(NAO_FOI_POSSIVEL_ENVIAR_EMAIL, e);
        }
    }
}
