package com.magento.mail;

import com.magento.extent_reports.ExtentReport;
import com.magento.interfaces.Constants;
import com.magento.loggers.Loggers;
import com.magento.pickers.DatePicker;
import com.magento.project_setup.TestNGBase;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class MailAttachment extends TestNGBase implements Constants {
    final static String REPORT_NAME = "Test_Report.html";
    private static BodyPart messageBodyPart;
    private static Multipart multipart;

    public Multipart attachReport() {
        try {
            // Setting the Message to display
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("The Test run is Completed and PFA Report below <br>" +
                    "<strong>Start Time:</strong> " + ExtentReport.getDateTime() + "<br>" +
                    "<strong>End Time:</strong> " + DatePicker.getDateTime(), "text/html; charset=utf-8");

            // Attaching the Message
            multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Fetching the Report file
            messageBodyPart = new MimeBodyPart();
            DataSource dataSource = (DataSource)
                    new FileDataSource(EXTENT_PATH + ExtentReport.getDateTime() + ".html");
            messageBodyPart.setDataHandler(new DataHandler(dataSource));
            messageBodyPart.setFileName(REPORT_NAME);

            // Attaching the report file
            multipart.addBodyPart(messageBodyPart);

            return multipart;
        } catch (Exception e) {
            Loggers.getLogger().error("Could not find the Report File");
        }
        return null;
    }
}
