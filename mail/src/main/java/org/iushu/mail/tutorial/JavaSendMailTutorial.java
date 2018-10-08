package org.iushu.mail.tutorial;

import com.sun.mail.smtp.SMTPMessage;
import com.sun.mail.smtp.SMTPSSLTransport;
import com.sun.mail.smtp.SMTPTransport;

import javax.mail.*;
import javax.mail.event.TransportAdapter;
import javax.mail.event.TransportEvent;
import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.util.Properties;

/**
 * Created by iuShu on 18-9-30
 */
public class JavaSendMailTutorial {

    // send mail
    private static final String SMTP_HOST = "smtp-mail.outlook.com";
    private static final int OUTLOOK_PORT = 587;

    public static void main(String[] args) throws Exception {
        String username = "HentonWu128@outlook.com";
        String password = "58CSM@outlook";
        Address to = new InternetAddress("13622409484@163.com"); // netease email address

        Properties props = new Properties();
        props.put("mail.debug", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", OUTLOOK_PORT);

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.checkserveridentity", "false");
        props.put("mail.smtp.ssl.trust", SMTP_HOST);

        Authenticator auth = new TutorialAuthenticator(username, password);
        Session session = Session.getDefaultInstance(props, auth);

        /** ready Message */
        Message message = getMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, to);

        // directly send
        Transport.send(message);

        System.err.println(">>> [end] send message over.");

    }

    private static Message getMessage(Session session) throws MessagingException {
        String content = "<html><body><h2>Attention</h2><hr/>" +
                "<h5>The volcano GONNA BE ERUPTION! RUN AWAY! RIGHT NOW</h5>" +
                "<cite>volcano broadcast - " + new Date().toGMTString() + "</cite></body></html>";

        Message message = new SMTPMessage(session);
        message.setSubject("Javax Mail Send Tutorial 5");
        message.setSentDate(new Date());
        message.setText(content);
        message.setContent(content, "text/html;charset=UTF-8");
        return message;
    }

}

class TutorialTransportListener extends TransportAdapter {

    @Override
    public void messageDelivered(TransportEvent e) {
        System.out.println(">>> [event] message delivered " + e.getType());
    }

    @Override
    public void messageNotDelivered(TransportEvent e) {
        System.out.println(">>> [event] message not delivered " + e.getType());
    }

    @Override
    public void messagePartiallyDelivered(TransportEvent e) {
        System.out.println(">>> [event] message partially delivered " + e.getType());
    }
}
