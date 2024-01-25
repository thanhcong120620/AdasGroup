package SpringbootProject.service.implement.form;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import SpringbootProject.algorithms.HTMLFileToString;
import SpringbootProject.entity.UserEntity;
import SpringbootProject.service.IUser;
import SpringbootProject.service.implement.ThymeleafService;

@Service
public class FormDevelop {
	private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";
	HTMLFileToString hfts = new HTMLFileToString();

	@Value("${config.mail.host}")
	private String host;
	@Value("${config.mail.port}")
	private String port;
	@Value("${config.mail.username}")
	private String email;
	@Value("${config.mail.password}")
	private String password;

	@Autowired
	ThymeleafService thymeleafService;
	
	@Autowired
	private IUser userService;

	public void sendMail(String subjectMail, String mailUser, String headerName, String normalName, String caplockName,
			String gender, Long id,String htmlPath, String img1) {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});
		Message message = new MimeMessage(session);
		try {
			//
			MimeMultipart multipart = new MimeMultipart("related");
			BodyPart textPart = new MimeBodyPart();
			String htmlText = hfts.convertHTMLtoString(htmlPath);

			textPart.setContent(htmlText, "text/html;charset=\"utf-8\"");
			multipart.addBodyPart(textPart);
			
			BodyPart imagePart11 = new MimeBodyPart();
			DataSource fds11 = new FileDataSource(img1);
			imagePart11.setDataHandler(new DataHandler(fds11));
			imagePart11.setHeader("Content-ID", "<image1.1>");
			imagePart11.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart11);
//			
//			BodyPart imagePart21 = new MimeBodyPart();
//			DataSource fds21 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\2.1.jpg");
//			imagePart21.setDataHandler(new DataHandler(fds21));
//			imagePart21.setHeader("Content-ID", "<image2.1>");
//			imagePart21.setDisposition(MimeBodyPart.INLINE);
//			multipart.addBodyPart(imagePart21);
//			
//			BodyPart imagePart22 = new MimeBodyPart();
//			DataSource fds22 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\2.2.png");
//			imagePart22.setDataHandler(new DataHandler(fds22));
//			imagePart22.setHeader("Content-ID", "<image2.2>");
//			imagePart22.setDisposition(MimeBodyPart.INLINE);
//			multipart.addBodyPart(imagePart22);
//			
//			BodyPart imagePart23 = new MimeBodyPart();
//			DataSource fds23 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\2.3.jpg");
//			imagePart23.setDataHandler(new DataHandler(fds23));
//			imagePart23.setHeader("Content-ID", "<image2.3>");
//			imagePart23.setDisposition(MimeBodyPart.INLINE);
//			multipart.addBodyPart(imagePart23);
//			
//			BodyPart imagePart24 = new MimeBodyPart();
//			DataSource fds24 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\2.4.jpg");
//			imagePart24.setDataHandler(new DataHandler(fds24));
//			imagePart24.setHeader("Content-ID", "<image2.4>");
//			imagePart24.setDisposition(MimeBodyPart.INLINE);
//			multipart.addBodyPart(imagePart24);
//			
//			BodyPart imagePart25 = new MimeBodyPart();
//			DataSource fds25 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\2.5.jpg");
//			imagePart25.setDataHandler(new DataHandler(fds25));
//			imagePart25.setHeader("Content-ID", "<image2.5>");
//			imagePart25.setDisposition(MimeBodyPart.INLINE);
//			multipart.addBodyPart(imagePart25);
//			
//			BodyPart imagePart31 = new MimeBodyPart();
//			DataSource fds31 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\3.1.png");
//			imagePart31.setDataHandler(new DataHandler(fds31));
//			imagePart31.setHeader("Content-ID", "<image3.1>");
//			imagePart31.setDisposition(MimeBodyPart.INLINE);
//			multipart.addBodyPart(imagePart31);
//			
//			BodyPart imagePart32 = new MimeBodyPart();
//			DataSource fds32 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\3.2.jpg");
//			imagePart32.setDataHandler(new DataHandler(fds32));
//			imagePart32.setHeader("Content-ID", "<image3.2>");
//			imagePart32.setDisposition(MimeBodyPart.INLINE);
//			multipart.addBodyPart(imagePart32);
//			
//			
//			
//			BodyPart imagePartsign = new MimeBodyPart();
//			DataSource fdssign = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\sign.png");
//			imagePartsign.setDataHandler(new DataHandler(fdssign));
//			imagePartsign.setHeader("Content-ID", "<imagesign>");
//			imagePartsign.setDisposition(MimeBodyPart.INLINE);
//			multipart.addBodyPart(imagePartsign);
			
			
			
			
			message.setContent(multipart);
			//
			message.setRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(mailUser) });

			message.setFrom(new InternetAddress(email));
			message.setSubject(subjectMail);
//          message.setContent(thymeleafService.getContent(nameUser, passwordUser), CONTENT_TYPE_TEXT_HTML);
			message.setContent(multipart);
			
			//Set status
			UserEntity user = userService.findById(id);
			user.setStatus("Đã gửi");
			userService.save(user);


			//Send
			Transport.send(message);
		} catch (MessagingException e) {
			System.out.println("===>Lỗi e: " + e);
			//Set status
			UserEntity user = userService.findById(id);
			user.setStatus("Error");
			userService.save(user);
			e.printStackTrace();
		}
	}
}
