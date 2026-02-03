package SpringbootProject.dto.dataProcess;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.enums.DataType;
import SpringbootProject.entity.enums.Gender;
import SpringbootProject.entity.enums.NextAction;

public class DTP3SearchRequest {
	 	private String zaloName;
	    private String phoneNumber1;
	    private String dataType;
	    private String gender;
	    private String nextAction;
	    private String accountFollow;
	    private String resultFollow;
	    private String consultDiary;
	    private String fullName1;
	    private LocalDate dateFollow;
	    
	    // Sử dụng @DateTimeFormat để Spring Boot có thể parse String từ Form sang LocalDate
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private LocalDate fromDate;
	    
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private LocalDate toDate;
	    

	    public DTP3SearchRequest() {}

	    // Getters and Setters
	    public String getZaloName() { return zaloName; }
	    public void setZaloName(String zaloName) { this.zaloName = zaloName; }

	    public String getPhoneNumber1() { return phoneNumber1; }
	    public void setPhoneNumber1(String phoneNumber1) { this.phoneNumber1 = phoneNumber1; }

	    public String getDataType() { return dataType; }
	    public void setDataType(String dataType) { this.dataType = dataType; }

	    public String getGender() { return gender; }
	    public void setGender(String gender) { this.gender = gender; }
	    
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
		
		public LocalDate getFromDate() { return fromDate; }
	    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

	    public LocalDate getToDate() { return toDate; }
	    public void setToDate(LocalDate toDate) { this.toDate = toDate; }
	    
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
		


		@Override
		public String toString() {
			return "DTP3SearchRequest [zaloName=" + zaloName + ", phoneNumber1=" + phoneNumber1 + ", dataType="
					+ dataType + ", gender=" + gender + ", accountFollow=" + accountFollow + ", resultFollow="
					+ resultFollow + "]";
		}

		public String getNextAction() {
			return nextAction;
		}

		public void setNextAction(String nextAction) {
			this.nextAction = nextAction;
		}

		public LocalDate getDateFollow() {
			return dateFollow;
		}

		public void setDateFollow(LocalDate dateFollow) {
			this.dateFollow = dateFollow;
		}

		/**
		 * Mapper DTO to Entity
		 * */
		public DTP3FilterData convertDTP3SearchRequestToEntity(DTP3SearchRequest dtp3SearchRequest) {
			DTP3FilterData dtp3FilterData = new DTP3FilterData();
			dtp3FilterData.setPhoneNumber1(dtp3SearchRequest.getPhoneNumber1());
			dtp3FilterData.setDataType(DataType.fromLabel(dtp3SearchRequest.getDataType()));
			dtp3FilterData.setGender(Gender.fromLabel(dtp3SearchRequest.getGender()));
			dtp3FilterData.setNextAction(NextAction.fromLabel(dtp3SearchRequest.getNextAction()));
			dtp3FilterData.setPhoneNumber1(dtp3SearchRequest.getPhoneNumber1());
			dtp3FilterData.setZaloName(dtp3SearchRequest.getZaloName());
			dtp3FilterData.setAccountFollow(dtp3SearchRequest.getAccountFollow());
			dtp3FilterData.setResultFollow(dtp3SearchRequest.getResultFollow());
			dtp3FilterData.setConsultDiary(dtp3SearchRequest.getConsultDiary());
			dtp3FilterData.setFullName1(dtp3SearchRequest.getFullName1());
			dtp3FilterData.setNextFollowDate(dtp3SearchRequest.getDateFollow());
			
			return dtp3FilterData;
		}

	
}
