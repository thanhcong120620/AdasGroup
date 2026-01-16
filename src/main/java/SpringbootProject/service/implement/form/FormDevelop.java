package SpringbootProject.service.implement.form;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.HTMLFileToString;
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
			String gender, Long id,String htmlPath, List<String> imgList, List<String> paragraphList) throws IOException {
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
			
			// Đọc file HTML và thay thế các placeholder
            Map<String, String> variables = new HashMap<>();
            variables.put("headerName", headerName);
            variables.put("normalName", normalName);
            variables.put("gender", gender);
            variables.put("caplockName", caplockName);
            
            for(int i=0;i<paragraphList.size();i++) {
            	String pragraph = "pragraph" + i;
            	variables.put(pragraph, paragraphList.get(i));
            }
            

            String htmlText = hfts.convertHTMLtoString(htmlPath, variables);
            
            // Tạo MimeMultipart
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart textPart = new MimeBodyPart();
            textPart.setContent(htmlText, CONTENT_TYPE_TEXT_HTML);
            multipart.addBodyPart(textPart);
			
            for(int i=0; i<imgList.size();i++) {
            	BodyPart imagePart0 = new MimeBodyPart();
    			DataSource fds0 = new FileDataSource(imgList.get(i));
    			imagePart0.setDataHandler(new DataHandler(fds0));
    			String headerValue = "<image"+i+">";
    			imagePart0.setHeader("Content-ID", headerValue);
    			imagePart0.setDisposition(MimeBodyPart.INLINE);
    			multipart.addBodyPart(imagePart0);
            }
			
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
			userService.userCreateUpdate(user);

			//Send
			Transport.send(message);
		} catch (MessagingException e) {
			System.out.println("===>Lỗi e: " + e);
			//Set status
			UserEntity user = userService.findById(id);
			user.setStatus("Error");
			userService.userCreateUpdate(user);
			e.printStackTrace();
		}
	}
}
