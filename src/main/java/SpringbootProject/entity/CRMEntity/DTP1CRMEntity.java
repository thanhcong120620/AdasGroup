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

import SpringbootProject.entity.enums.ContactTimeSlot;
import SpringbootProject.entity.enums.CustomerFeedback;
import SpringbootProject.entity.enums.CustomerHesitate;
import SpringbootProject.entity.enums.CustomerStyle;
import SpringbootProject.entity.enums.DemandForProperty;
import SpringbootProject.entity.enums.Entangled;
import SpringbootProject.entity.enums.Gender;
import SpringbootProject.entity.enums.InteractionLevel;
import SpringbootProject.entity.enums.LeadSource;
import SpringbootProject.entity.enums.NextAction;
import SpringbootProject.entity.enums.PropertyTypeInterest;
import SpringbootProject.entity.enums.Region;
import SpringbootProject.entity.enums.RelationshipStatus;
import SpringbootProject.entity.enums.Salutation;
import SpringbootProject.entity.enums.SegmentCode;
import SpringbootProject.entity.enums.Status;

//
/**
 * 
 */
@Entity
@Table(name = "DTP1CRMEntity")
public class DTP1CRMEntity {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	 	@Column(name = "code", length = 100)
	    private String codeCrmDTP1;
	    
	 	@Enumerated(EnumType.STRING)
	 	@Column(length = 20, nullable = false)
	 	private InteractionLevel interactionLevel;

	 	@Enumerated(EnumType.STRING)
	 	@Column(name = "lead_source", length = 30)
	 	private LeadSource leadSource;
	 	
	    @Column(name = "date_of_lead")
	    private LocalDate dateOfLead; 
	    
	 	@Column(name = "consult_diary", length = 100)
	    private String consultDiary;
	    
	 	@Column(name = "sale_name", length = 100)
	    private String saleName;// sau nào join one to many với User Enity
	 	
	    // Thông tin liên hệ cơ bản
	    @Column(name = "full_name", nullable = false, length = 150)
	    private String fullName; // Họ và tên
	    
	    @Column(name = "last_name", nullable = false, length = 150)
	    private String lastName; // Tên

	    @Enumerated(EnumType.STRING)
	    @Column(name = "salutation", length = 10)
	    private Salutation salutation;
	    
	    @Enumerated(EnumType.STRING)
	    @Column(name = "gender", length = 15)
	    private Gender gender;

	    @Column(name = "date_of_birth")
	    private LocalDate dateOfBirth; // Năm sinh (chỉ cần ngày, không cần giờ)

	    @Enumerated(EnumType.STRING)
	    @Column(name = "customer_style", length = 20)
	    private CustomerStyle customerStyle;
	    
	    

	    @Column(name = "phone_number1", length = 20, unique = true) // Số điện thoại có thể là unique
	    private String phoneNumber1; // Contact (SĐT)
	    
	    @Column(name = "phone_number2", length = 20, unique = true) // Số điện thoại có thể là unique
	    private String phoneNumber2; // Contact (SĐT)

	    @Column(name = "gmail", length = 100, unique = true) // Email cũng có thể là unique
	    private String gmail;
	    
	    @Lob
	    @Column(name = "address", columnDefinition = "TEXT")
	    private String address; // Địa chỉ

	    @Enumerated(EnumType.STRING)
	    @Column(name = "region", length = 30)
	    private Region region;

	    @Column(name = "zalo_name", length = 100)
	    private String zaloName; // Tên Zalo

	    @Column(name = "zalo_uid", length = 100, unique = true) // Zalo UID có thể là unique
	    private String zaloUid; // Zalo-UID

	    @Column(name = "zalo_friend_account", length = 20, unique = true)
	    private String zaloFriendAccount; // Xác định tài khoản nào đã kết bạn với dữ liệu này

	    @Column(name = "facebook_link", length = 200)
	    private String facebookLink; // Link facebook

	    @Column(name = "working_area", length = 200)
	    private String workingArea; // Nơi công tác
	    
	    

	    // Thông tin về nhu cầu và tương tác

	    @Column(name = "project_interest", length = 100)
	    private String projectInterest; // Dự án quan tâm

	    @Enumerated(EnumType.STRING)
	    @Column(name = "segment_code", length = 10, nullable = false)
	    private SegmentCode segmentCode; // Mã phân khúc

	    @Enumerated(EnumType.STRING)
	    @Column(name = "property_type_interest", length = 30, nullable = false)
	    private PropertyTypeInterest propertyTypeInterest;// Loại hình quan tâm (VD: Căn hộ chung cư)
	    
	    @Digits(integer = 15, fraction = 0)
	    @Column(name = "savings_amount", precision = 18, scale = 0)
	    private BigDecimal savingsAmount;
	    //Hiển thị trên frontend
//	    public class CustomerDTO {
//
//	        private BigDecimal savingsAmount;
//	        private String savingsAmountDisplay;
//	    }
//	    Format ở Service / Mapper:
//	    NumberFormat formatter =
//	    	    NumberFormat.getInstance(new Locale("vi", "VN"));
//
//	    	dto.setSavingsAmountDisplay(
//	    	    formatter.format(entity.getSavingsAmount()) + " VNĐ"
//	    	);
	    
	    @Column(name = "investment_capital_from", precision = 18, scale = 1)
	    private BigDecimal investmentCapitalFrom;
	    @Column(name = "investment_capital_to", precision = 18, scale = 1)
	    private BigDecimal investmentCapitalTo;
//	    @AssertTrue(message = "Vốn đầu tư không hợp lệ")
//	    public boolean isValidInvestmentRange() {
//	        if (investmentCapitalFrom == null || investmentCapitalTo == null) {
//	            return true;
//	        }
//	        return investmentCapitalFrom.compareTo(investmentCapitalTo) <= 0;
//	    }
	    
	    @Column(name = "priority_area", length = 100)
	    private String priorityArea; // khu vực ưu tiên
	    
	    @Enumerated(EnumType.STRING)
	    @Column(name = "customer_hesitate", nullable = false)
	    private CustomerHesitate customerHesitate; // điều mong muốn của khách hàng

	    @Enumerated(EnumType.STRING)
	    @Column(name = "demand_for_property", length = 30, nullable = false)
	    private DemandForProperty demandForProperty; //Nhu cầu

	    @Lob// Dùng @Lob cho các trường text dài
	    @Column(name = "demand_details", columnDefinition = "TEXT")
	    private String demandDetails; // Mô tả nhu cầu chi tiết

	    @Lob
	    @Column(name = "product_bought", columnDefinition = "TEXT")
	    private String productBought; //dự án đã mua
	    
	    


	    @Enumerated(EnumType.STRING)
	    @Column(name = "relationship_status", length = 20, nullable = false)
	    private RelationshipStatus relationshipStatus;

	    @Column(name = "last_interaction_date")
	    private LocalDate lastInteractionDate; // Latest (Ngày tương tác cuối, chỉ cần ngày)
	    
	    @Enumerated(EnumType.STRING)
	    @Column(name = "status", nullable = false)
	    private Status status; 
	    
	    @Enumerated(EnumType.STRING)
	    @Column(name = "customer_feedback", nullable = false)
	    private CustomerFeedback customerFeedback;  // phản hồi của khách hàng
	    
	    @Enumerated(EnumType.STRING)
	    @Column(name = "entangled", nullable = false)
	    private Entangled entangled;
	    
	    @Lob// Dùng @Lob cho các trường text dài
	    @Column(name = "entangled_point", columnDefinition = "TEXT")
	    private String entangledPointCustomer; // Mô tả điểm vướng mắc của khách hàng chi tiết
	    
	    
	    
	    @Column(name = "next_follow_date")
	    private LocalDate nextFollowDate; // ngày follow tiếp theo

	    // "Ngày trống không tương tác" có thể là một trường được tính toán, không cần lưu trực tiếp
	    // Hoặc nếu muốn lưu, có thể là Integer (số ngày) và cập nhật bằng trigger/batch job.
	    // private Integer days_since_last_interaction;


	    // Cách lấy dữ liệu của status
//	    public class CustomerDTO {
//	        private String status;       // CLOSING
//	        private String statusLabel;  // Sắp chốt
//	    }
//	    -->Mapping
//	    dto.setStatus(entity.getStatus().name());
//	    dto.setStatusLabel(entity.getStatus().getLabel());

	    @Enumerated(EnumType.STRING)
	    @Column(name = "next_action", length = 30, nullable = false)
	    private NextAction nextAction;

	    @Lob
	    @Column(name = "next_action_details", columnDefinition = "TEXT")
	    private String nextActionDetails; // Mô tả hành động tiếp theo chi tiết
	    
	    
	    //Khung giờ có thể tương tác
	    @Enumerated(EnumType.STRING)
	    @Column(name = "preferred_contact_time", length = 30)
	    private ContactTimeSlot preferredContactTime;

//	    @Lob
	    @Column(name = "how_to_consult", columnDefinition = "TEXT")
	    private String howToConsult; 

//	    @Lob
	    @Column(name = "dificult_consulting", columnDefinition = "TEXT")
	    private String difficultConsulting;
	    
	    @Lob
	    @Column(name = "customer_profile_description", columnDefinition = "TEXT")
	    private String customerProfileDescription; // Mô tả chân dung KH

//	    @Lob
	    @Column(name = "notes", columnDefinition = "TEXT")
	    private String notes; // Ghi chú chung

	    // Người quản lý/phụ trách khách hàng này
//	    @ManyToOne(fetch = FetchType.LAZY)
//	    @JoinColumn(name = "account_manager_id", nullable = false) // Nhân viên kinh doanh phụ trách là bắt buộc
//	    private User accountManager; // Liên kết với User (Nhân viên)
	    
	 // Trong CustomerEntity.java
//	    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	    private List<CustomerInteractionHistory> interactionHistories = new ArrayList<>();

	    // Timestamps
//	    @CreationTimestamp
	    @Column(name = "created_at", nullable = true, updatable = false)
	    private LocalDateTime createdAt;

//	    @UpdateTimestamp
	    @Column(name = "updated_at", nullable = true)
	    private LocalDateTime updatedAt;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCodeCrmDTP1() {
			return codeCrmDTP1;
		}

		public void setCodeCrmDTP1(String codeCrmDTP1) {
			this.codeCrmDTP1 = codeCrmDTP1;
		}

		public InteractionLevel getInteractionLevel() {
			return interactionLevel;
		}

		public void setInteractionLevel(InteractionLevel interactionLevel) {
			this.interactionLevel = interactionLevel;
		}

		public LeadSource getLeadSource() {
			return leadSource;
		}

		public void setLeadSource(LeadSource leadSource) {
			this.leadSource = leadSource;
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

		public String getSaleName() {
			return saleName;
		}

		public void setSaleName(String saleName) {
			this.saleName = saleName;
		}

		public String getFullName() {
			return fullName;
		}

		public void setFullName(String fullName) {
			this.fullName = fullName;
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

		public LocalDate getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public CustomerStyle getCustomerStyle() {
			return customerStyle;
		}

		public void setCustomerStyle(CustomerStyle customerStyle) {
			this.customerStyle = customerStyle;
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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Region getRegion() {
			return region;
		}

		public void setRegion(Region region) {
			this.region = region;
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

		public String getZaloFriendAccount() {
			return zaloFriendAccount;
		}

		public void setZaloFriendAccount(String zaloFriendAccount) {
			this.zaloFriendAccount = zaloFriendAccount;
		}

		public String getFacebookLink() {
			return facebookLink;
		}

		public void setFacebookLink(String facebookLink) {
			this.facebookLink = facebookLink;
		}

		public String getWorkingArea() {
			return workingArea;
		}

		public void setWorkingArea(String workingArea) {
			this.workingArea = workingArea;
		}

		public String getProjectInterest() {
			return projectInterest;
		}

		public void setProjectInterest(String projectInterest) {
			this.projectInterest = projectInterest;
		}

		public SegmentCode getSegmentCode() {
			return segmentCode;
		}

		public void setSegmentCode(SegmentCode segmentCode) {
			this.segmentCode = segmentCode;
		}

		public PropertyTypeInterest getPropertyTypeInterest() {
			return propertyTypeInterest;
		}

		public void setPropertyTypeInterest(PropertyTypeInterest propertyTypeInterest) {
			this.propertyTypeInterest = propertyTypeInterest;
		}

		public BigDecimal getSavingsAmount() {
			return savingsAmount;
		}

		public void setSavingsAmount(BigDecimal savingsAmount) {
			this.savingsAmount = savingsAmount;
		}

		public BigDecimal getInvestmentCapitalFrom() {
			return investmentCapitalFrom;
		}

		public void setInvestmentCapitalFrom(BigDecimal investmentCapitalFrom) {
			this.investmentCapitalFrom = investmentCapitalFrom;
		}

		public BigDecimal getInvestmentCapitalTo() {
			return investmentCapitalTo;
		}

		public void setInvestmentCapitalTo(BigDecimal investmentCapitalTo) {
			this.investmentCapitalTo = investmentCapitalTo;
		}

		public String getPriorityArea() {
			return priorityArea;
		}

		public void setPriorityArea(String priorityArea) {
			this.priorityArea = priorityArea;
		}

		public CustomerHesitate getCustomerHesitate() {
			return customerHesitate;
		}

		public void setCustomerHesitate(CustomerHesitate customerHesitate) {
			this.customerHesitate = customerHesitate;
		}

		public DemandForProperty getDemandForProperty() {
			return demandForProperty;
		}

		public void setDemandForProperty(DemandForProperty demandForProperty) {
			this.demandForProperty = demandForProperty;
		}

		public String getDemandDetails() {
			return demandDetails;
		}

		public void setDemandDetails(String demandDetails) {
			this.demandDetails = demandDetails;
		}

		public String getProductBought() {
			return productBought;
		}

		public void setProductBought(String productBought) {
			this.productBought = productBought;
		}

		public RelationshipStatus getRelationshipStatus() {
			return relationshipStatus;
		}

		public void setRelationshipStatus(RelationshipStatus relationshipStatus) {
			this.relationshipStatus = relationshipStatus;
		}

		public LocalDate getLastInteractionDate() {
			return lastInteractionDate;
		}

		public void setLastInteractionDate(LocalDate lastInteractionDate) {
			this.lastInteractionDate = lastInteractionDate;
		}

		public Status getStatus() {
			return status;
		}

		public void setStatus(Status status) {
			this.status = status;
		}

		public CustomerFeedback getCustomerFeedback() {
			return customerFeedback;
		}

		public void setCustomerFeedback(CustomerFeedback customerFeedback) {
			this.customerFeedback = customerFeedback;
		}

		public Entangled getEntangled() {
			return entangled;
		}

		public void setEntangled(Entangled entangled) {
			this.entangled = entangled;
		}

		public String getEntangledPointCustomer() {
			return entangledPointCustomer;
		}

		public void setEntangledPointCustomer(String entangledPointCustomer) {
			this.entangledPointCustomer = entangledPointCustomer;
		}

		public LocalDate getNextFollowDate() {
			return nextFollowDate;
		}

		public void setNextFollowDate(LocalDate nextFollowDate) {
			this.nextFollowDate = nextFollowDate;
		}

		public NextAction getNextAction() {
			return nextAction;
		}

		public void setNextAction(NextAction nextAction) {
			this.nextAction = nextAction;
		}

		public String getNextActionDetails() {
			return nextActionDetails;
		}

		public void setNextActionDetails(String nextActionDetails) {
			this.nextActionDetails = nextActionDetails;
		}

		public ContactTimeSlot getPreferredContactTime() {
			return preferredContactTime;
		}

		public void setPreferredContactTime(ContactTimeSlot preferredContactTime) {
			this.preferredContactTime = preferredContactTime;
		}

		public String getHowToConsult() {
			return howToConsult;
		}

		public void setHowToConsult(String howToConsult) {
			this.howToConsult = howToConsult;
		}

		public String getDifficultConsulting() {
			return difficultConsulting;
		}

		public void setDifficultConsulting(String difficultConsulting) {
			this.difficultConsulting = difficultConsulting;
		}

		public String getCustomerProfileDescription() {
			return customerProfileDescription;
		}

		public void setCustomerProfileDescription(String customerProfileDescription) {
			this.customerProfileDescription = customerProfileDescription;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
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
			return "DTP1CRMEntity [id=" + id + ", codeCrmDTP1=" + codeCrmDTP1 + ", interactionLevel=" + interactionLevel
					+ ", leadSource=" + leadSource + ", dateOfLead=" + dateOfLead + ", consultDiary=" + consultDiary
					+ ", saleName=" + saleName + ", fullName=" + fullName + ", lastName=" + lastName + ", salutation="
					+ salutation + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", customerStyle="
					+ customerStyle + ", phoneNumber1=" + phoneNumber1 + ", phoneNumber2=" + phoneNumber2 + ", gmail="
					+ gmail + ", address=" + address + ", region=" + region + ", zaloName=" + zaloName + ", zaloUid="
					+ zaloUid + ", zaloFriendAccount=" + zaloFriendAccount + ", facebookLink=" + facebookLink
					+ ", workingArea=" + workingArea + ", projectInterest=" + projectInterest + ", segmentCode="
					+ segmentCode + ", propertyTypeInterest=" + propertyTypeInterest + ", savingsAmount="
					+ savingsAmount + ", investmentCapitalFrom=" + investmentCapitalFrom + ", investmentCapitalTo="
					+ investmentCapitalTo + ", priorityArea=" + priorityArea + ", customerHesitate=" + customerHesitate
					+ ", demandForProperty=" + demandForProperty + ", demandDetails=" + demandDetails
					+ ", productBought=" + productBought + ", relationshipStatus=" + relationshipStatus
					+ ", lastInteractionDate=" + lastInteractionDate + ", status=" + status + ", customerFeedback="
					+ customerFeedback + ", entangled=" + entangled + ", entangledPointCustomer="
					+ entangledPointCustomer + ", nextFollowDate=" + nextFollowDate + ", nextAction=" + nextAction
					+ ", nextActionDetails=" + nextActionDetails + ", preferredContactTime=" + preferredContactTime
					+ ", howToConsult=" + howToConsult + ", difficultConsulting=" + difficultConsulting
					+ ", customerProfileDescription=" + customerProfileDescription + ", notes=" + notes + ", createdAt="
					+ createdAt + ", updatedAt=" + updatedAt + "]";
		}
	    
//	    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	    private List<CustomerPhoneNumber> phoneNumbers = new ArrayList<>(); // Khởi tạo để tránh NullPointerException


	    
	    

}
