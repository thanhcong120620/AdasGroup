package SpringbootProject.dto.dataProcess;

import java.time.LocalDate;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

import SpringbootProject.entity.enums.DataType;
import SpringbootProject.entity.enums.Gender;

public class DTP3SearchRequest {
	 private String zaloName;
	    private String phoneNumber1;
	    private DataType dataType;
	    private Gender gender;
	    private String accountFollow;
	    private String resultFollow;
	    private String consultDiary;
	    
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

	    public DataType getDataType() { return dataType; }
	    public void setDataType(DataType dataType) { this.dataType = dataType; }

	    public Gender getGender() { return gender; }
	    public void setGender(Gender gender) { this.gender = gender; }
	    
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

		@Override
		public String toString() {
			return "DTP3SearchRequest [zaloName=" + zaloName + ", phoneNumber1=" + phoneNumber1 + ", dataType="
					+ dataType + ", gender=" + gender + ", accountFollow=" + accountFollow + ", resultFollow="
					+ resultFollow + "]";
		}


		
		
	
}
