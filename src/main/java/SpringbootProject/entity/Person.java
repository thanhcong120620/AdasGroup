package SpringbootProject.entity;

public class Person {
    private String fullName;
    private String gender;
    private String phone;
    private String gmail;
    private String note;
    
    
    public Person() {}
    
	public Person(String fullName, String gender, String phone, String gmail, String note) {
		super();
		this.fullName = fullName;
		this.gender = gender;
		this.phone = phone;
		this.gmail = gmail;
		this.note = note;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGmail() {
		return gmail;
	}
	public void setGmail(String gmail) {
		this.gmail = gmail;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

    
    
}
