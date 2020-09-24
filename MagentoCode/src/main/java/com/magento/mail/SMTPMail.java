package com.magento.mail;

import com.magento.loggers.Loggers;
import com.magento.utilities.Property;

import javax.mail.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SMTPMail {
    private static Properties properties;

    /**
     * Send the mail to Specified email from properties file
     */
    public static void sendEmail() {
        final String FROM_EMAIL = Property.getProperty("fromMail");
        final String PASSWORD = Property.getProperty("password");
        final String TO_EMAILS = Property.getProperty("toMail");

        List<String> to_emails = Arrays.asList(TO_EMAILS.split(","));

        // Configuring Mail properties
        properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Authenticator auth = new Authenticator() {
            // Override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        };

        // Setting the Session
        Session session = Session.getInstance(properties, auth);

        // Sending the mail
        try {
            Transport.send(MailMessage.attachMessage(session, FROM_EMAIL, to_emails));
            Loggers.getLogger().info("Report Email sent Successfully");
        } catch (MessagingException e) {
            Loggers.getLogger().error("Report Mail could not be sent");
        }

    }
}
