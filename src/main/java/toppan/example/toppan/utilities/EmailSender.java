package toppan.example.toppan.utilities;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailSender {

        public static void send( String to){

//            final String toto = "o.klymchuk@zhi.hsc.gov.ua";       // sender email
            final String from = "o.klymchuk@zhi.hsc.gov.ua";       // receiver email
            final String host = "10.30.1.1";            // mail server host

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);
//            System.out.println("Email Sent successfully...." + to);
//            System.out.println("Email Sent successfully...." + toto);
            Session session = Session.getDefaultInstance(properties); // default session

            try {
                MimeMessage message = new MimeMessage(session); // email message

                message.setFrom(new InternetAddress(from)); // setting header fields

                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                // Create a multipar message
                Multipart multipart = new MimeMultipart();
                message.setSubject("Звіт по рубину"); // subject line

                MimeBodyPart messageBodyPart = new MimeBodyPart();
//                String filename = "rubin.xlsx";
                String filename = "c:/RSC1840/rubin.xlsx";
                DataSource source = new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename);
                multipart.addBodyPart(messageBodyPart);
                // Send the complete message parts
                message.setContent(multipart );
                Transport.send(message);
                System.out.println("Email Sent successfully...." + to);
            } catch (MessagingException mex){ mex.printStackTrace(); }

        }

}
