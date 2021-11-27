package toppan.example.toppan.utilities;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
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

                message.setSubject("Test Mail from Java Program"); // subject line

                // actual mail body
                message.setText("You can send mail from Java program by using mail API, but you need" +
                        "couple of more JAR files e.g. smtp.jar and activation.jar");

                MimeBodyPart messageBodyPart = new MimeBodyPart();
                String filename = "file.txt";
                DataSource source = (DataSource) new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(filename);
//                multipart.addBodyPart(messageBodyPart);

                Transport.send(message); System.out.println("Email Sent successfully....");
            } catch (MessagingException mex){ mex.printStackTrace(); }

        }


}
