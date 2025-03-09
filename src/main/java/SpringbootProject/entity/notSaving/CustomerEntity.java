
package SpringbootProject.entity.notSaving;

//import javax.persistence.Entity;

//@Entity
public class CustomerEntity   {

	private String fullName;

	private String phoneNumber;

	private String titleCustomer;

	private String zaloCustomer;
	
	private String statusCustomer;

	private String infor1;

	private String infor2;

	private String infor3;

	private String infor4;

	private String infor5;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTitleCustomer() {
		return titleCustomer;
	}

	public void setTitleCustomer(String titleCustomer) {
		this.titleCustomer = titleCustomer;
	}

	public String getZaloCustomer() {
		return zaloCustomer;
	}

	public void setZaloCustomer(String zaloCustomer) {
		this.zaloCustomer = zaloCustomer;
	}

	public String getStatusCustomer() {
		return statusCustomer;
	}

	public void setStatusCustomer(String statusCustomer) {
		this.statusCustomer = statusCustomer;
	}

	public String getInfor1() {
		return infor1;
	}

	public void setInfor1(String infor1) {
		this.infor1 = infor1;
	}

	public String getInfor2() {
		return infor2;
	}

	public void setInfor2(String infor2) {
		this.infor2 = infor2;
	}

	public String getInfor3() {
		return infor3;
	}

	public void setInfor3(String infor3) {
		this.infor3 = infor3;
	}

	public String getInfor4() {
		return infor4;
	}

	public void setInfor4(String infor4) {
		this.infor4 = infor4;
	}

	public String getInfor5() {
		return infor5;
	}

	public void setInfor5(String infor5) {
		this.infor5 = infor5;
	}

	@Override
	public String toString() {
		return "CustomerEntity [fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", titleCustomer="
				+ titleCustomer + ", zaloCustomer=" + zaloCustomer + ", statusCustomer=" + statusCustomer + ", infor1="
				+ infor1 + ", infor2=" + infor2 + ", infor3=" + infor3 + ", infor4=" + infor4 + ", infor5=" + infor5
				+ "]";
	}

	
	
	//Note: Nếu sử file entity, hãy sửa các file phụ thuộc dữ liệu bởi UserEntity trong package (IOAlgorithm; GmailMKTAlgorithm)

}
