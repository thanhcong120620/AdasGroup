package SpringbootProject.service.implement.SunCosmo;

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

import SpringbootProject.entity.UserEntity;
import SpringbootProject.service.IUser;
import SpringbootProject.service.implement.ThymeleafService;

@Service
public class SuncosmoSuccess250923 {
	private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";

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
			String gender, Long id) {
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
			String htmlText = 
					"<html><head>"
					+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
					
					+ "<link\r\n"
					+ "      rel=\"stylesheet\"\r\n"
					+ "      href=\"https://pro.fontawesome.com/releases/v5.15.4/css/all.css?fbclid=IwAR1YsW4Cd3uJltNc6k1kJ8R9VzNjmSXk9rjmW32BPoA6LWxNrZBCUY-D4i8\"\r\n"
					+ "    />\r\n"
					+ "\r\n"
					+ "    <style>\r\n"
					+ "      body {\r\n"
					+ "        padding: 10px;\r\n"
					+ "      }"
					+ "      .centerP {\r\n"
					+ "        text-align: center;\r\n"
					+ "      }\r\n"
					+ "      ul.myUL {\r\n"
					+ "        display: inline-block;\r\n"
					+ "        text-align: left;\r\n"
					+ "        list-style-type: none;\r\n"
					+ "        margin-top: -12px;\r\n"
					+ "      }\r\n"
					+ "      .gmail p {\r\n"
					+ "        font-size: 15px;\r\n"
					+ "      }\r\n"
					+ "      .gmail {\r\n"

					+ "        position: center;\r\n"
					+ "        width: 98%;\r\n"
					+ "        justify-content: center;\r\n"
					+ "      }\r\n"
					+ "\r\n"
					+ "      @media only screen and (min-width: 786px) {\r\n"
					+ "        .grimg1 {\r\n"
					+ "          width: 80%;\r\n"
					+ "          display: block;\r\n"
					+ "          margin-left: auto;\r\n"
					+ "          margin-right: auto;\r\n"
					+ "        }\r\n"
					+ "        .grimg2 {\r\n"
					+ "          width: 90%;\r\n"
					+ "          display: block;\r\n"
					+ "          margin-left: auto;\r\n"
					+ "          margin-right: auto;\r\n"
					+ "        }\r\n"
					+ "        .sign {\r\n"
					+ "          display: inline-block;\r\n"
					+ "        }\r\n"
					+ "        .signimg {\r\n"
					+ "          width: 30%;\r\n"
					+ "          float: left;\r\n"
					+ "        }\r\n"
					+ "        .my-contact {\r\n"
					+ "          float: right;\r\n"
					+ "          width: 60%;\r\n"
					+ "          margin-left: 40px;\r\n"
					+ "        }\r\n"
					+ "      }\r\n"
					+ "      @media only screen and (min-width: 992px) {\r\n"
					+ "        .grimg1 {\r\n"
					+ "          width: 60%;\r\n"
					+ "          display: block;\r\n"
					+ "          margin-left: auto;\r\n"
					+ "          margin-right: auto;\r\n"
					+ "        }\r\n"
					+ "        .grimg2 {\r\n"
					+ "          width: 80%;\r\n"
					+ "          display: block;\r\n"
					+ "          margin-left: auto;\r\n"
					+ "          margin-right: auto;\r\n"
					+ "        }\r\n"
					+ "        .sign {\r\n"
					+ "          display: inline-block;\r\n"
					+ "        }\r\n"
					+ "        .signimg {\r\n"
					+ "          width: 30%;\r\n"
					+ "          float: left;\r\n"
					+ "        }\r\n"
					+ "        .my-contact {\r\n"
					+ "          float: right;\r\n"
					+ "          width: 60%;\r\n"
					+ "          margin-left: 40px;\r\n"
					+ "        }\r\n"
					+ "      }\r\n"
					+ "    </style>\r\n"
					+ "  </head>\r\n"
					+ "  <body>\r\n"
					+ "<div class=\"gmail\">\r\n"
					+ "      <div class=\"begin\">\r\n"
					+ "        <h3>Kinh gửi "+headerName+" !</h3>\r\n"
					+ "        <p>\r\n"
					+ "          Tôi là\r\n"
					+ "          <a\r\n"
					+ "            href=\"https://zalo.me/0368279613\"\r\n"
					+ "            target=\"_blank\"\r\n"
					+ "            >Thành Công</a\r\n"
					+ "          >, rất hân hạnh đại diện Sun Group cung cấp thông tin đến "+normalName+" về\r\n"
					+ "          một sản phẩm đang được săn đón rất nhiều bởi người dân Đà Nẵng và các\r\n"
					+ "          quý khách hàng từ Miền Bắc.\r\n"
					+ "        </p>\r\n"
					+ "        <div class=\"grimg1\">\r\n"
					+ "          <img src=\"cid:image1.1\" style=\"width: 100%; height: auto\" />\r\n"
					+ "        </div>\r\n"
					+ "        <hr />\r\n"
					+ "        <p>\r\n"
					+ "          Tòa căn hộ trực diện sông Hàn Đà Nẵng, từ 1-3 phòng ngủ và căn Duplex\r\n"
					+ "          đẳng cấp. Cư dân có thể tận hưởng view ấn tượng của sông Hàn và thành\r\n"
					+ "          phố Đà Nẵng lung linh về đêm và đường bở biển Mỹ Khê đẹp nhất Đà Nẵng.\r\n"
					+ "        </p>\r\n"
					+ "        <div class=\"grimg1\">\r\n"
					+ "          <img src=\"cid:image1.2\" style=\"width: 100%; height: auto\" />\r\n"
					+ "          <img src=\"cid:image2.1\" style=\"width: 100%; height: auto\" />\r\n"
					+ "        </div>\r\n"
					+ "        <br />\r\n"
					+ "        <hr />\r\n"
					+ "        <p style=\"text-align: center; font-size: 18px\">\r\n"
					+ "          <b>**Sở hữu vị Trí Huyết Mạch của Đà Nẵng**</b>\r\n"
					+ "        </p>\r\n"
					+ "        <p>\r\n"
					+ "          Tại giao lộ cầu Trần Thị Lý và cung đường triệu đô Trần Hưng Đạo , là tọa độ có 1 không 2 kết nối khu vực du lịch Đà Nẵng với trung tâm Hành chính. Trục đường Nguyễn Văn Thoại hướng thẳng biển du lịch Mỹ Khê. Tòa căn hộ này không chỉ có tiện ích nội khu sang trọng mà tiện ích liền kề đa dang, mọi thứ bạn cần trong tầm tay.\r\n"
					+ "        </p>\r\n"
					+ "        <div class=\"grimg1\">\r\n"
					+ "          <img src=\"cid:image2.2\" style=\"width: 100%; height: auto\" />\r\n"
					+ "          <img src=\"cid:image2.3\" style=\"width: 100%; height: auto\" />\r\n"
					+ "          <img src=\"cid:image2.4\" style=\"width: 100%; height: auto\" />\r\n"
					+ "          <img src=\"cid:image2.5\" style=\"width: 100%; height: auto\" />\r\n"
					+ "        </div>\r\n"
					+ "      </div>\r\n"
					+ "\r\n"
					+ "      <!------------------------------------------------------------------------------------------------------------------------------------------->\r\n"
					+ "      <!--MÔ TẢ ĐẶC ĐIỂM-->\r\n"
					+ "      <div class=\"overview\">\r\n"
					+ "        <br />\r\n"
					+ "        <hr />\r\n"
					+ "        <p style=\"text-align: center; font-size: 18px\">\r\n"
					+ "          <b>Biển, Sông, Và Cả Thành Phố trong tầm tay</b>\r\n"
					+ "        </p>\r\n"
					+ "        <p>\r\n"
					+ "          Sun Group lại tiếp tục khắc thêm 1 dấu ấn tại Đà Nẵng, tòa căn hộ này\r\n"
					+ "          là một thiên đàng biển và sông hòa quyện. Bạn có thể thả mình vào làn\r\n"
					+ "          nước biển tuyệt đẹp hoặc thư thả ngắm cảnh sông Hàn bằng một buổi tàu\r\n"
					+ "          buổi chiều. Khi buổi tối buông xuống, thành phố lung linh hiện ngay\r\n"
					+ "          trong tầm mắt.\r\n"
					+ "        </p>\r\n"
					+ "        <p style=\"font-size: 16px\">\r\n"
					+ "          <b>Ưu đãi hấp dẫn từ chủ đầu tư</b>\r\n"
					+ "        </p>\r\n"
					+ "        <p>*Giá mở bán với chiết khấu lên đến 20.5%.</p>\r\n"
					+ "        <p>*Thanh toán 30% có thể ký hợp đồng mua bán.</p>\r\n"
					+ "        <p>*Hỗ trợ vay 70% với lãi suất 0% lên đến 30 tháng.</p>\r\n"
					+ "        <div class=\"grimg1\">\r\n"
					+ "          <img src=\"cid:image3.1\" style=\"width: 100%; height: auto\" />\r\n"
					+ "          <img src=\"cid:image3.2\" style=\"width: 100%; height: auto\" />\r\n"
					+ "        </div>\r\n"
					+ "      </div>\r\n"
					+ "\r\n"
					+ "      <p>\r\n"
					+ "        <b>Chiết khấu 2% mở bán tòa Panamo 2 cho khách hàng booking đặt chỗ</b>.\r\n"
					+ "        Đừng để cơ hội này trôi qua! Hãy liên hệ ngay với chúng tôi để biết thêm\r\n"
					+ "        chi tiết dự án.\r\n"
					+ "      </p>\r\n"
					+ "      <p>\r\n"
					+ "        Xin cảm ơn "+normalName+" đã dành thời gian đọc hết thư này, hy vọng chúng\r\n"
					+ "        tôi có cơ hội được hợp tác làm việc với "+gender+". Chúc một "+normalName+"\r\n"
					+ "        ngày vui vẻ và có những chuỗi công việc hiệu quả !\r\n"
					+ "      </p>\r\n"
					+ "\r\n"
					+ "      <p>Trân trọng !</p>\r\n"
					+ "      <p>---</p>\r\n"
					+ "\r\n"
					+ "      <!------------------------------------------------------------------------------------------------------------------------------------------->\r\n"
					+ "\r\n"
					+ "      <hr />\r\n"
					+ "      <p>\r\n"
					+ "        <b>Mọi thông tin chi tiết xin liên hệ:</b>\r\n"
					+ "      </p>\r\n"
					+ "\r\n"
					+ "      <div class=\"sign\">\r\n"
					+ "        <span class=\"signimg\">\r\n"
					+ "          <img\r\n"
					+ "            src=\"cid:imagesign\"\r\n"
					+ "            style=\"\r\n"
					+ "              width: 250px;\r\n"
					+ "              height: auto;\r\n"
					+ "              margin-top: 70px;\r\n"
					+ "              display: block;\r\n"
					+ "              margin-left: auto;\r\n"
					+ "              margin-right: auto;\r\n"
					+ "            \"\r\n"
					+ "          />\r\n"
					+ "        </span>\r\n"
					+ "        <span class=\"my-contact\" style=\"margin-top: 30px\">\r\n"
					+ "          <p><b>NGUYỄN THÀNH CÔNG – CHUYÊN VIÊN KINH DOANH</b></p>\r\n"
					+ "          <p><b>Mobile</b>: 0795.73.73.84</p>\r\n"
					+ "          <p style=\"margin-top: -10px\">\r\n"
					+ "            <b>Zalo: </b>\r\n"
					+ "             <a href=\"https://zalo.me/0368279613\" target=\"_blank\" \r\n"
					+ "             >CVKD.THANHCONG</a \r\n"
					+ "             > \r\n"
					+ "          </p>\r\n"
					+ "          <hr />\r\n"
					+ "          <p><b>SUNCOSMO RESIDENCE</b></p>\r\n"
					+ "          <p style=\"margin-top: -10px\">\r\n"
					+ "            Giao lộ phía đông cầu Trần Thị Lý & đường Trần Hưng Đạo, Đà Nẵng\r\n"
					+ "          </p>\r\n"
					+ "        </span>\r\n"
					+ "      </div>\r\n"
					+ "    </div>"
					
					+ "</body></html>";

			textPart.setContent(htmlText, "text/html;charset=\"utf-8\"");
			multipart.addBodyPart(textPart);
			
			BodyPart imagePart11 = new MimeBodyPart();
			DataSource fds11 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\1.1.jpg");
			imagePart11.setDataHandler(new DataHandler(fds11));
			imagePart11.setHeader("Content-ID", "<image1.1>");
			imagePart11.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart11);
			
			BodyPart imagePart21 = new MimeBodyPart();
			DataSource fds21 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\2.1.jpg");
			imagePart21.setDataHandler(new DataHandler(fds21));
			imagePart21.setHeader("Content-ID", "<image2.1>");
			imagePart21.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart21);
			
			BodyPart imagePart22 = new MimeBodyPart();
			DataSource fds22 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\2.2.png");
			imagePart22.setDataHandler(new DataHandler(fds22));
			imagePart22.setHeader("Content-ID", "<image2.2>");
			imagePart22.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart22);
			
			BodyPart imagePart23 = new MimeBodyPart();
			DataSource fds23 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\2.3.jpg");
			imagePart23.setDataHandler(new DataHandler(fds23));
			imagePart23.setHeader("Content-ID", "<image2.3>");
			imagePart23.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart23);
			
			BodyPart imagePart24 = new MimeBodyPart();
			DataSource fds24 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\2.4.jpg");
			imagePart24.setDataHandler(new DataHandler(fds24));
			imagePart24.setHeader("Content-ID", "<image2.4>");
			imagePart24.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart24);
			
			BodyPart imagePart25 = new MimeBodyPart();
			DataSource fds25 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\2.5.jpg");
			imagePart25.setDataHandler(new DataHandler(fds25));
			imagePart25.setHeader("Content-ID", "<image2.5>");
			imagePart25.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart25);
			
			BodyPart imagePart31 = new MimeBodyPart();
			DataSource fds31 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\3.1.png");
			imagePart31.setDataHandler(new DataHandler(fds31));
			imagePart31.setHeader("Content-ID", "<image3.1>");
			imagePart31.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart31);
			
			BodyPart imagePart32 = new MimeBodyPart();
			DataSource fds32 = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\3.2.jpg");
			imagePart32.setDataHandler(new DataHandler(fds32));
			imagePart32.setHeader("Content-ID", "<image3.2>");
			imagePart32.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePart32);
			
			
			
			BodyPart imagePartsign = new MimeBodyPart();
			DataSource fdssign = new FileDataSource("D:\\Desktop\\My data\\1.My working\\2.Retizy\\2.Markeing\\3.Gmail channel\\1.Panoma240923\\img\\sign.png");
			imagePartsign.setDataHandler(new DataHandler(fdssign));
			imagePartsign.setHeader("Content-ID", "<imagesign>");
			imagePartsign.setDisposition(MimeBodyPart.INLINE);
			multipart.addBodyPart(imagePartsign);
			
			
			
			
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
