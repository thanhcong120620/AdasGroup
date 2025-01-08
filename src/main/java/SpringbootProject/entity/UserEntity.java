
package SpringbootProject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

	@Column(name = "fullName")
	private String fullName;

	@Column(name = "genderUser")
	private String genderUser;

	@Column(name = "gmail")
	private String gmail;

	@Column(name = "status")
	private String status;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGenderUser() {
		return genderUser;
	}

	public void setGenderUser(String genderUser) {
		this.genderUser = genderUser;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserEntity [fullName=" + fullName + ", genderUser=" + genderUser + ", gmail=" + gmail + ", status="
				+ status + "]";
	}
	
	//Note: Nếu sử file entity, hãy sửa các file phụ thuộc dữ liệu bởi UserEntity trong package (IOAlgorithm; GmailMKTAlgorithm)

}
