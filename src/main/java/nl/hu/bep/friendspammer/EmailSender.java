package nl.hu.bep.friendspammer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private EmailSender() {}

    public static void sendEmail(String subject, String to, String messageBody, boolean asHtml) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mailtrap.io");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.auth", "true");

        String username = Config.getProp("mailtrap.username");
        String password = Config.getProp("mailtrap.password");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("spammer@spammer.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);

            if (asHtml) {
                message.setContent(messageBody, "text/html; charset=utf-8");
            } else {
                message.setText(messageBody);
            }
            Transport.send(message);

            MongoSaver.saveEmail(to, "spammer@spamer.com", subject, messageBody, asHtml);

        } catch (MessagingException e) {
            throw new EmailSenderRuntimeException();
        }
    }

    public static void sendEmail(String subject, String[] toList, String messageBody, boolean asHtml) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mailtrap.io");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.auth", "true");

        String username = Config.getProp("mailtrap.username");
        String password = Config.getProp("mailtrap.password");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            for (int index = 0; index < toList.length; index++) {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("spammer@spammer.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(toList[index]));
                message.setSubject(subject);

                if (asHtml) {
                    message.setContent(messageBody, "text/html; charset=utf-8");
                } else {
                    message.setText(messageBody);
                }
                Transport.send(message);

                logger.info("Done");
            }

        } catch (MessagingException e) {
            throw new EmailSenderRuntimeException();
        }
    }

}
