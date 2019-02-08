package common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import pageObjects.PageObjects;

public class SendMail {

	public static void sendMail(
			 final String from, 
			 final String password, 
			 String []to, 
			 String []cc) {

		Date date = new Date();  
		 SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
		 formatter = new SimpleDateFormat("dd MMMM yyyy");  
		   String strDate = formatter.format(date);  
		    System.out.println("Date Format with dd MMMM yyyy : "+strDate);  
		    
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		
		try {
			System.out.println(from+" - "+password);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			
			InternetAddress[] sendTo = new InternetAddress[cc.length];
			for (int i = 0; i <to.length; i++) {
				System.out.println("Send to: " + to[i]);
				sendTo[i] = new InternetAddress(to[i]);
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo[i].toString()));
			}
			
			message.setSubject("Automation Testing Report : Completed on : "+strDate);
			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart
					.setText("Mr Recipient," + "\n \n      Testing activity of Automation testing with attached excel test-data is completed."+
							"\n Please find attachments for the Test-Report of API testing with the test-data."+
							"\n \n Thanks & Regard's," + "\n Sagar Bobade."+
							"\n \n <b>Note :- Kindly download the HTML report then open for the better view.</b>"+
							"\n This is system generated E-mail, please do not reply to this E-mail..!"
							);
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			BodyPart messageBodyPart2 = new MimeBodyPart();
			String report = PageObjects.htmlReportPath;
			System.out.println(report);
			DataSource source1 = new FileDataSource(report);
			messageBodyPart2.setDataHandler(new DataHandler(source1));
			messageBodyPart2.setFileName(report);
			multipart.addBodyPart(messageBodyPart2);
			
			message.setContent(multipart);
			
			InternetAddress[] sendToCC = new InternetAddress[cc.length];
			for (int i = 0; i <cc.length; i++) {
				System.out.println("Send to CC: " + cc[i]);
				sendToCC[i] = new InternetAddress(cc[i]);
				message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(sendToCC[i].toString()));
			}
			
		Transport.send(message);
		System.out.println("Mail sent successfylly");

		} catch (MessagingException e) {
			System.out.println("Exception occured");
			throw new RuntimeException(e);
		}

	}
}