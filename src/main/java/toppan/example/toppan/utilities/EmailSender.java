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

        public static void send(){

            String to = "o.klymchuk@zhi.hsc.gov.ua";         // sender email
            String from = "oleg@zhi.hsc.gov.ua";       // receiver email
            String host = "10.6.1.1";            // mail server host

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", host);

            Session session = Session.getDefaultInstance(properties); // default session

            try {
                MimeMessage message = new MimeMessage(session); // email message

                message.setFrom(new InternetAddress(from)); // setting header fields

                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                // Create a multipar message
                Multipart multipart = new MimeMultipart();
                message.setSubject("Звіт по рубину"); // subject line
//
//                // actual mail body
//                message.setText("You can send mail from Java program by using mail API, but you need" +
//                        "couple of more JAR files e.g. smtp.jar and activation.jar");

                MimeBodyPart messageBodyPart = new MimeBodyPart();
//                String filename = "C:/demo/rubin.xlsx";
                String filename = "c:/RSC1840/rubin.xlsx";
                DataSource source = new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename);
                multipart.addBodyPart(messageBodyPart);
                // Send the complete message parts
                message.setContent(multipart );
                Transport.send(message);
                System.out.println("Email Sent successfully....");
            } catch (MessagingException mex){ mex.printStackTrace(); }

        }


}
