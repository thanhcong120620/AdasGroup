
package SpringbootProject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {
	
	private int x;
	private int y;

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
	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "UserEntity [fullName=" + fullName + ", genderUser=" + genderUser + ", gmail=" + gmail + ", status="
				+ status + "]";
	}
	
	public int caculator() {
		return (x+y);
	}
	
	
	//Note: Nếu sử file entity, hãy sửa các file phụ thuộc dữ liệu bởi UserEntity trong package (IOAlgorithm; GmailMKTAlgorithm)

}
