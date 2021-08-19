package hard.compra.model;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

public class MailJavaSender {
    public void senderMail(final MailJava mail) throws
            UnsupportedEncodingException, MessagingException {

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mail.getSmtpHostMail());
        props.setProperty("mail.smtp.auth", mail.getSmtpAuth());
        props.setProperty("mail.smtp.starttls.enable", mail.getSmtpStarttls());
        props.setProperty("mail.smtp.port", mail.getSmtpPortMail());
        props.setProperty("mail.mime.charset", mail.getCharsetMail());
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        mail.getUserMail(), mail.getPassMail()
                );
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(
                mail.getUserMail(), mail.getFromNameMail())
        );
        boolean first = true;
        for (Map.Entry<String, String> map : mail.getToMailsUsers().entrySet()) {
            if (first) {
                msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(map.getKey(), map.getValue())
                );
                first = false;
            } else {
                msg.addRecipient(Message.RecipientType.CC,
                        new InternetAddress(map.getKey(), map.getValue())
                );
            }
        }

        msg.setSubject(mail.getSubjectMail());
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(mail.getBodyMail(), mail.getTypeTextMail());
        Multipart mps = new MimeMultipart();
        mps.addBodyPart(textPart);
        msg.setContent(mps);
        Transport.send(msg);
    }

}
