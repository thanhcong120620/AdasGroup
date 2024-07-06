package SpringbootProject.service.implement.form;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import org.hibernate.internal.build.AllowSysOut;
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
			String gender, Long id,String htmlPath, List<String> imgList) throws IOException {
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
			//---------------------------------- Cũ
//			MimeMultipart multipart = new MimeMultipart("related");
//			BodyPart textPart = new MimeBodyPart();
//			String htmlText = hfts.convertHTMLtoString(htmlPath, headerName, normalName, caplockName, gender);
//
//			textPart.setContent(htmlText, "text/html;charset=\"utf-8\"");
//			multipart.addBodyPart(textPart);
			//------------------------------
			
			// Đọc file HTML và thay thế các placeholder
            Map<String, String> variables = new HashMap<>();
            variables.put("HeaderName", normalName);
            variables.put("Gender", gender);
//            variables.put("imageUrl", "cid:image1.1");

            String htmlText = hfts.convertHTMLtoString(htmlPath, variables);

            // Tạo MimeMultipart
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart textPart = new MimeBodyPart();
            textPart.setContent(htmlText, CONTENT_TYPE_TEXT_HTML);
            multipart.addBodyPart(textPart);

			
			BodyPart imagePart10 = new MimeBodyPart();
			DataSource fds10 = new FileDataSource(imgList.get(0));
			imagePart10.setDataHandler(new DataHandler(fds10));
			imagePart10.setHeader("Content-ID", "<image1.0>");
			imagePart10.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart10);
			
			BodyPart imagePart11 = new MimeBodyPart();
			DataSource fds11 = new FileDataSource(imgList.get(1));
			imagePart11.setDataHandler(new DataHandler(fds11));
			imagePart11.setHeader("Content-ID", "<image1.1>");
			imagePart11.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart11);
			
			BodyPart imagePart12 = new MimeBodyPart();
			DataSource fds12 = new FileDataSource(imgList.get(2));
			imagePart12.setDataHandler(new DataHandler(fds12));
			imagePart12.setHeader("Content-ID", "<image1.2>");
			imagePart12.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart12);
			
			BodyPart imagePart13 = new MimeBodyPart();
			DataSource fds13 = new FileDataSource(imgList.get(3));
			imagePart13.setDataHandler(new DataHandler(fds13));
			imagePart13.setHeader("Content-ID", "<image1.3>");
			imagePart13.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart13);
			
			BodyPart imagePart14 = new MimeBodyPart();
			DataSource fds14 = new FileDataSource(imgList.get(4));
			imagePart14.setDataHandler(new DataHandler(fds14));
			imagePart14.setHeader("Content-ID", "<image1.4>");
			imagePart14.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart14);
			
			BodyPart imagePart15 = new MimeBodyPart();
			DataSource fds15 = new FileDataSource(imgList.get(5));
			imagePart15.setDataHandler(new DataHandler(fds15));
			imagePart15.setHeader("Content-ID", "<image1.5>");
			imagePart15.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart15);
			
			
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
