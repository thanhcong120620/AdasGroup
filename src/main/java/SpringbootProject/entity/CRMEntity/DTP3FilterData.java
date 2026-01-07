package SpringbootProject.entity.CRMEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import SpringbootProject.entity.enums.DataType;
import SpringbootProject.entity.enums.Gender;
import SpringbootProject.entity.enums.LeadSource;
import SpringbootProject.entity.enums.NextAction;
import SpringbootProject.entity.enums.Salutation;

@Entity
@Table(name = "DTP3FilterData")
public class DTP3FilterData {
	
 	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type", nullable = false)
    private DataType dataType;

 	@Column(name = "data_source", length = 30)
 	private String dataSource;
 	
    @Column(name = "date_of_lead")
    private LocalDate dateOfLead; 
    
 	@Column(name = "consult_diary", length = 100)
    private String consultDiary;
 	
    // Thông tin liên hệ cơ bản
    @Column(name = "full_name1", nullable = false, length = 150)
    private String fullName1; // Họ và tên
    
    @Column(name = "full_name2", nullable = false, length = 150)
    private String fullName2; // Họ và tên
    
    @Column(name = "last_name", nullable = false, length = 150)
    private String lastName; // Tên

    @Enumerated(EnumType.STRING)
    @Column(name = "salutation", length = 10)
    private Salutation salutation;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 15)
    private Gender gender;

    @Column(name = "zalo_name", length = 100)
    private String zaloName; // Tên Zalo

    @Column(name = "zalo_uid", length = 100, unique = true) // Zalo UID có thể là unique
    private String zaloUid; // Zalo-UID

    @Column(name = "facebook_link", length = 200)
    private String facebookLink; // Link facebook

    @Column(name = "phone_number1", length = 20, unique = true) // Số điện thoại có thể là unique
    private String phoneNumber1; // Contact (SĐT)
    
    @Column(name = "phone_number2", length = 20, unique = true) // Số điện thoại có thể là unique
    private String phoneNumber2; // Contact (SĐT)

    @Column(name = "gmail", length = 100, unique = true) // Email cũng có thể là unique
    private String gmail;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth; // Năm sinh (chỉ cần ngày, không cần giờ)
    
    @Digits(integer = 15, fraction = 0)
    @Column(name = "savings_amount", precision = 18, scale = 0)
    private BigDecimal savingsAmount;
    
    @Lob
    @Column(name = "address", columnDefinition = "TEXT")
    private String address; // Địa chỉ

    @Column(name = "working_area", length = 200)
    private String workingArea; // Nơi công tác

    @Lob
    @Column(name = "product_bought", columnDefinition = "TEXT")
    private String productBought; //dự án đã mua

    @Lob// Dùng @Lob cho các trường text dài
    @Column(name = "mix_contacts", columnDefinition = "TEXT")
    private String mixContacts; //Contact tổng hợp

    @Enumerated(EnumType.STRING)
    @Column(name = "next_action", length = 30, nullable = false)
    private NextAction nextAction;
    
    @Column(name = "next_follow_date")
    private LocalDate nextFollowDate; // ngày follow tiếp theo

    @Column(name = "account_follow", length = 200)
    private String accountFollow; // Tài khoản phụ trách

    @Column(name = "result_follow", length = 200)
    private String resultFollow; // Kết quả tương tác.
    
    @Column(name = "created_at", nullable = true, updatable = false)
    private LocalDateTime createdAt;

//    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public LocalDate getDateOfLead() {
		return dateOfLead;
	}

	public void setDateOfLead(LocalDate dateOfLead) {
		this.dateOfLead = dateOfLead;
	}

	public String getConsultDiary() {
		return consultDiary;
	}

	public void setConsultDiary(String consultDiary) {
		this.consultDiary = consultDiary;
	}

	public String getFullName1() {
		return fullName1;
	}

	public void setFullName1(String fullName1) {
		this.fullName1 = fullName1;
	}

	public String getFullName2() {
		return fullName2;
	}

	public void setFullName2(String fullName2) {
		this.fullName2 = fullName2;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Salutation getSalutation() {
		return salutation;
	}

	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getZaloName() {
		return zaloName;
	}

	public void setZaloName(String zaloName) {
		this.zaloName = zaloName;
	}

	public String getZaloUid() {
		return zaloUid;
	}

	public void setZaloUid(String zaloUid) {
		this.zaloUid = zaloUid;
	}

	public String getFacebookLink() {
		return facebookLink;
	}

	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}

	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public BigDecimal getSavingsAmount() {
		return savingsAmount;
	}

	public void setSavingsAmount(BigDecimal savingsAmount) {
		this.savingsAmount = savingsAmount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWorkingArea() {
		return workingArea;
	}

	public void setWorkingArea(String workingArea) {
		this.workingArea = workingArea;
	}

	public String getProductBought() {
		return productBought;
	}

	public void setProductBought(String productBought) {
		this.productBought = productBought;
	}

	public String getMixContacts() {
		return mixContacts;
	}

	public void setMixContacts(String mixContacts) {
		this.mixContacts = mixContacts;
	}

	public NextAction getNextAction() {
		return nextAction;
	}

	public void setNextAction(NextAction nextAction) {
		this.nextAction = nextAction;
	}

	public LocalDate getNextFollowDate() {
		return nextFollowDate;
	}

	public void setNextFollowDate(LocalDate nextFollowDate) {
		this.nextFollowDate = nextFollowDate;
	}

	public String getAccountFollow() {
		return accountFollow;
	}

	public void setAccountFollow(String accountFollow) {
		this.accountFollow = accountFollow;
	}

	public String getResultFollow() {
		return resultFollow;
	}

	public void setResultFollow(String resultFollow) {
		this.resultFollow = resultFollow;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "DTP3FilterData [id=" + id + ", dataType=" + dataType + ", dataSource=" + dataSource + ", dateOfLead="
				+ dateOfLead + ", consultDiary=" + consultDiary + ", fullName1=" + fullName1 + ", fullName2="
				+ fullName2 + ", lastName=" + lastName + ", salutation=" + salutation + ", gender=" + gender
				+ ", zaloName=" + zaloName + ", zaloUid=" + zaloUid + ", facebookLink=" + facebookLink
				+ ", phoneNumber1=" + phoneNumber1 + ", phoneNumber2=" + phoneNumber2 + ", gmail=" + gmail
				+ ", dateOfBirth=" + dateOfBirth + ", savingsAmount=" + savingsAmount + ", address=" + address
				+ ", workingArea=" + workingArea + ", productBought=" + productBought + ", mixContacts=" + mixContacts
				+ ", nextAction=" + nextAction + ", nextFollowDate=" + nextFollowDate + ", accountFollow="
				+ accountFollow + ", resultFollow=" + resultFollow + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	
    
    
    
    
}
