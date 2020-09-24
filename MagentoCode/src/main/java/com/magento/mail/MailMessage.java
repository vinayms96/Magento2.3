package com.magento.mail;

import com.magento.loggers.Loggers;
import com.magento.pickers.DatePicker;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

public class MailMessage {

    /**
     * @param session   - Mail Session
     * @param FROM_MAIL - From Mail Id
     * @param to_emails - Multiple To Email Ids
     * @return - MimeMessage
     */
    public static MimeMessage attachMessage(Session session, String FROM_MAIL, List<String> to_emails) {
        try {

            // Setting the mail headers
            MimeMessage message = new MimeMessage(session);
            message.addHeader("Content-type", "text/HTML; charset=UTF-8");
            message.addHeader("Content-Transfer-Encoding", "8bit");
            message.setFrom(new InternetAddress(FROM_MAIL));

            // Setting the Multiple To mail ids
            for (String to_mail :
                    to_emails) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to_mail, "Test Report"));
            }

            // Set the Mail Subject
            message.setSubject("Testing Report " + DatePicker.getDayDate());

            // Attaching the Report to message reference
            MailAttachment mailAttachment = new MailAttachment();
            message.setContent(mailAttachment.attachReport());

            return message;
        } catch (Exception e) {
            Loggers.getLogger().error("Mail could not be sent");
        }
        return null;
    }

}
