package Notification;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailNotifier implements Notifier {
    private Notifier wrappedEmail;

    public EmailNotifier(Notifier wrappedEmail) {
        this.wrappedEmail = wrappedEmail;
    }

    @Override
    public void send(String message) {
        //String to = "andrei.viman04@gmail.com";
        String to = "guzar107@gmail.com";
        String from = "andreiviman11@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {

                return new javax.mail.PasswordAuthentication("andreiviman11@gmail.com", "Calcumeu.4201");

            }

        });
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage mess = new MimeMessage(session);

            // Set From: header field of the header.
            mess.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            mess.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            mess.setSubject("Modificare credentiale");

            // Now set the actual message
            mess.setText(message);

            System.out.println("sending...");
            // Send message
            Transport.send(mess);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
