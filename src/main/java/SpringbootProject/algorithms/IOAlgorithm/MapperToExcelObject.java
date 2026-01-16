package SpringbootProject.algorithms.IOAlgorithm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import FileUtil.DateAndTimeProcess;
import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.GenderProcess;
import SpringbootProject.algorithms.PersonProfileProcessAlgorithm.PhoneProcess;
import SpringbootProject.controller.CRMControlers.DataProcess.DTP3FilterAndRawDataController;
import SpringbootProject.entity.CRMEntity.DTP3FilterData;
import SpringbootProject.entity.enums.DataType;
import SpringbootProject.entity.enums.Gender;
import SpringbootProject.entity.enums.NextAction;
import SpringbootProject.entity.enums.Salutation;
import SpringbootProject.entity.enums.Status;
import SpringbootProject.entity.notSaving.ExcelObject;

public class MapperToExcelObject {
	
    private static final Logger log = LoggerFactory.getLogger(MapperToExcelObject.class);
	
//=================================================Akabiz Form==========================================================================
	
    public String[] akabizExcelHeader () {
    	String[] akabizExcelHeader = {"Fullname", "Uid", "Mobile", "Email", "Info1", "Info2", "Info3", "Info4", "Info5"};
    	return akabizExcelHeader;
    }
    
    /*
	 * DTP3FilterData Entity to Input Akabiz excel form
	 * Has Salutation
	 * chưa làm xong
	 * */
	public List<ExcelObject> convertDTP3FilterDataToAkabizExcelNoSalutation(List<DTP3FilterData> dtp3FilterDataListInput) {
		
		List<ExcelObject> excelObjectListOutput = new ArrayList<ExcelObject>();
		
		ExcelObject excelObjectSample = new ExcelObject();
		excelObjectSample.setColumn1("Nguyễn Thành Công");
		excelObjectSample.setColumn3("0368279613");
		excelObjectSample.setColumn5("Công");
		excelObjectSample.setColumn6("Anh");
		excelObjectSample.setColumn7("anh");
		excelObjectSample.setColumn8("Em");
		excelObjectSample.setColumn9("em");
		
		excelObjectListOutput.add(excelObjectSample);
		
		if(dtp3FilterDataListInput ==null) {
			log.info(">>> SERVICE: Check DTP3FilterData is null: {}", dtp3FilterDataListInput);
			return null;
		}
		
		for(DTP3FilterData dtp3FilterData : dtp3FilterDataListInput) {
			//check obligatory condition
			if(!PhoneProcess.isVietnamPhoneNumber(dtp3FilterData.getPhoneNumber1()) && !PhoneProcess.isVietnamPhoneNumber(dtp3FilterData.getPhoneNumber2())) {
				log.info(">>> SERVICE: Check dtp3FilterData.getPhoneNumber1() ko hợp lệ: {}", dtp3FilterData.getPhoneNumber1());
				log.info(">>> SERVICE: Check dtp3FilterData.getPhoneNumber2() ko hợp lệ: {}", dtp3FilterData.getPhoneNumber2());
				continue;
			}  
			if(!dtp3FilterData.getSalutation().equals(Salutation.UNDEFINED)) {	
				log.info(">>> SERVICE: Salutiation is available");
				continue;
			}
			if(dtp3FilterData.getLastName()==null || dtp3FilterData.getLastName().isEmpty()) {
				log.info(">>> SERVICE: Last Name is not exist !");
				continue;
			}
			
			
			//set data
			else {
				ExcelObject excelObject = new ExcelObject();
				//set phone
				if(PhoneProcess.isVietnamPhoneNumber(dtp3FilterData.getPhoneNumber1())) {
					excelObject.setColumn3(safeToString(dtp3FilterData.getPhoneNumber1()));
				} else if(!PhoneProcess.isVietnamPhoneNumber(dtp3FilterData.getPhoneNumber1()) && PhoneProcess.isVietnamPhoneNumber(dtp3FilterData.getPhoneNumber2())) {
					excelObject.setColumn3(safeToString(dtp3FilterData.getPhoneNumber2()));
				}
				
				excelObject.setColumn5(safeToString(dtp3FilterData.getLastName()));
				excelObject.setColumn1(safeToString(dtp3FilterData.getFullName1()));
				excelObject.setColumn2(safeToString(dtp3FilterData.getZaloUid()));
				excelObjectListOutput.add(excelObject);
			}
		}
		
		
		
		return excelObjectListOutput;
	}
    
	/*
	 * DTP3FilterData Entity to Input Akabiz excel form
	 * Has Salutation
	 * Chưa xử lý trường hợp check Salutation
	 * */
	public List<ExcelObject> convertDTP3FilterDataToAkabizExcelHasSalutation(List<DTP3FilterData> dtp3FilterDataListInput) {
		
		List<ExcelObject> excelObjectListOutput = new ArrayList<ExcelObject>();
		
		ExcelObject excelObjectSample = new ExcelObject();
		excelObjectSample.setColumn1("Nguyễn Thành Công");
		excelObjectSample.setColumn3("0368279613");
		excelObjectSample.setColumn5("Công");
		excelObjectSample.setColumn6("Anh");
		excelObjectSample.setColumn7("anh");
		excelObjectSample.setColumn8("Em");
		excelObjectSample.setColumn9("em");
		
		excelObjectListOutput.add(excelObjectSample);
		
		if(dtp3FilterDataListInput ==null) {
			log.info(">>> SERVICE: Check DTP3FilterData is null: {}", dtp3FilterDataListInput);
			return null;
		}
		
		for(DTP3FilterData dtp3FilterData : dtp3FilterDataListInput) {
			//check obligatory condition
			if(!PhoneProcess.isVietnamPhoneNumber(dtp3FilterData.getPhoneNumber1()) && !PhoneProcess.isVietnamPhoneNumber(dtp3FilterData.getPhoneNumber2())) {
				log.info(">>> SERVICE: Check dtp3FilterData.getPhoneNumber1() ko hợp lệ: {}", dtp3FilterData.getPhoneNumber1());
				log.info(">>> SERVICE: Check dtp3FilterData.getPhoneNumber2() ko hợp lệ: {}", dtp3FilterData.getPhoneNumber2());
				continue;
			}  
			if((dtp3FilterData.getSalutation()==null || dtp3FilterData.getSalutation().equals(Salutation.UNDEFINED)) && (dtp3FilterData.getDateOfBirth()==null || dtp3FilterData.getGender()==null || dtp3FilterData.getGender().equals(Gender.UNDEFINED))) {	
				log.info(">>> SERVICE: Cann't create Salutiation");
				continue;
			}
			if(dtp3FilterData.getLastName()==null || dtp3FilterData.getLastName().isEmpty()) {
				log.info(">>> SERVICE: Last Name is not exist !");
				continue;
			}
			
			
			//set data
			else {
				ExcelObject excelObject = new ExcelObject();
				//set phone
				if(PhoneProcess.isVietnamPhoneNumber(dtp3FilterData.getPhoneNumber1())) {
					excelObject.setColumn3(safeToString(dtp3FilterData.getPhoneNumber1()));
				} else if(!PhoneProcess.isVietnamPhoneNumber(dtp3FilterData.getPhoneNumber1()) && PhoneProcess.isVietnamPhoneNumber(dtp3FilterData.getPhoneNumber2())) {
					excelObject.setColumn3(safeToString(dtp3FilterData.getPhoneNumber2()));
				}
				
				//set Salutation (Infor 2 - Infor 5 at Column 6,7,8,9
				if(dtp3FilterData.getSalutation()!=null && !dtp3FilterData.getSalutation().equals(Salutation.UNDEFINED)) { // trường hợp entity có sẵn Salutation
					excelObject.setColumn6(safeToString(dtp3FilterData.getSalutation().getLabel()));
					excelObject.setColumn7(safeToString(dtp3FilterData.getSalutation().getLabel()).toLowerCase());
					excelObject.setColumn8(safeToString(GenderProcess.getFirstObjectPronoun(dtp3FilterData.getSalutation())));
					excelObject.setColumn9(safeToString(GenderProcess.getFirstObjectPronoun(dtp3FilterData.getSalutation())).toLowerCase());
				
					//Trường hợp Entity ko có Salutation thì set từ birthday & Gender
				} else if(dtp3FilterData.getSalutation()==null &&dtp3FilterData.getSalutation().equals(Salutation.UNDEFINED) && dtp3FilterData.getGender()!=null && dtp3FilterData.getDateOfBirth()!=null) {
					excelObject.setColumn6(safeToString(GenderProcess.detectSalutationFromGender(dtp3FilterData.getGender(), dtp3FilterData.getDateOfBirth()).getLabel()));
					excelObject.setColumn7(safeToString(GenderProcess.detectSalutationFromGender(dtp3FilterData.getGender(), dtp3FilterData.getDateOfBirth()).getLabel()).toLowerCase());
					excelObject.setColumn8(safeToString(GenderProcess.getFirstObjectPronoun(dtp3FilterData.getDateOfBirth())));
					excelObject.setColumn9(safeToString(GenderProcess.getFirstObjectPronoun(dtp3FilterData.getDateOfBirth())).toLowerCase());
				} 
				
				excelObject.setColumn5(safeToString(dtp3FilterData.getLastName()));
				excelObject.setColumn1(safeToString(dtp3FilterData.getFullName1()));
				excelObject.setColumn2(safeToString(dtp3FilterData.getZaloUid()));
				excelObjectListOutput.add(excelObject);
			}
			
			
			
		}
		
		
		
		return excelObjectListOutput;
	}
	
	
	
	
	/*
	 * Result Akabiz excel to DTP3FilterData Entity
	 * */
	public List<DTP3FilterData> convertAkabizExcelToDTP3FilterData(List<ExcelObject> excelObjectInput) {
		List<DTP3FilterData> dtp3FilterDataListOutput = new ArrayList<DTP3FilterData>();
		DateAndTimeProcess dateProcess = new DateAndTimeProcess();
		PhoneProcess phoneProcess = new PhoneProcess();
		
		if(excelObjectInput ==null) {
			log.info(">>> SERVICE: Check excelObjectInput is null: {}", excelObjectInput);
			return null;
		}
		
		for(ExcelObject excelObject : excelObjectInput) {
			DTP3FilterData dtp3FilterData = new  DTP3FilterData();
			dtp3FilterData.setZaloName(excelObject.getColumn5());
			dtp3FilterData.setZaloUid(excelObject.getColumn6());
			dtp3FilterData.setConsultDiary(excelObject.getColumn4());
			dtp3FilterData.setDateOfLead(dateProcess.parseLocalDateFlexible(excelObject.getColumn28()));
			dtp3FilterData.setPhoneNumber1(phoneProcess.normalizeAndValidateVietnameseNumber(excelObject.getColumn7()));
			
			if(excelObject.getColumn18()!=null && !excelObject.getColumn18().isEmpty()) {
				dtp3FilterData.setResultFollow(excelObject.getColumn18());
			} else if(excelObject.getColumn17()!=null && !excelObject.getColumn17().isEmpty()) {
				dtp3FilterData.setResultFollow(excelObject.getColumn17());
			} else {
				dtp3FilterData.setResultFollow("Chiến dịch thất bại !");
			}
			
			dtp3FilterDataListOutput.add(dtp3FilterData);
		}
		
		
		return dtp3FilterDataListOutput;
	}
	
	
	
	
	
//======================================DTP3 Filter and Raw Data==========================================================================
	
	public List<ExcelObject> DPT3FilterDataToExcelObject (List<DTP3FilterData> dtp3FilterDataList) {
		List<ExcelObject> excelObjectList = new ArrayList<ExcelObject>();
		
		for(DTP3FilterData dtp3FilterData:dtp3FilterDataList) {
			ExcelObject excelObject = new ExcelObject();
			
			
			excelObject.setColumn1(safeToString(dtp3FilterData.getId()));
		    excelObject.setColumn2(safeToString(dtp3FilterData.getDataType().getLabel()));
		    excelObject.setColumn3(safeToString(dtp3FilterData.getDataSource()));
		    excelObject.setColumn4(safeToString(dtp3FilterData.getDateOfLead()));
		    excelObject.setColumn5(safeToString(dtp3FilterData.getConsultDiary()));
		    excelObject.setColumn6(safeToString(dtp3FilterData.getFullName1()));
		    excelObject.setColumn7(safeToString(dtp3FilterData.getFullName2()));
			excelObject.setColumn8(safeToString(dtp3FilterData.getLastName()));
			excelObject.setColumn9(safeToString(dtp3FilterData.getSalutation().getLabel()));
			excelObject.setColumn10(safeToString(dtp3FilterData.getGender().getLabel()));
			excelObject.setColumn11(safeToString(dtp3FilterData.getZaloName()));
			excelObject.setColumn12(safeToString(dtp3FilterData.getZaloUid()));
			excelObject.setColumn13(safeToString(dtp3FilterData.getFacebookLink()));
			excelObject.setColumn14(safeToString(dtp3FilterData.getPhoneNumber1()));
			excelObject.setColumn15(safeToString(dtp3FilterData.getPhoneNumber2()));
			excelObject.setColumn16(safeToString(dtp3FilterData.getGmail()));
			excelObject.setColumn17(safeToString(dtp3FilterData.getDateOfBirth()));
			excelObject.setColumn18(safeToString(dtp3FilterData.getSavingsAmount()));
			excelObject.setColumn19(safeToString(dtp3FilterData.getAddress()));
			excelObject.setColumn20(safeToString(dtp3FilterData.getWorkingArea()));
			excelObject.setColumn21(safeToString(dtp3FilterData.getProductBought()));
			excelObject.setColumn22(safeToString(dtp3FilterData.getMixContacts()));
			excelObject.setColumn23(safeToString(dtp3FilterData.getNextAction().getLabel()));
			excelObject.setColumn24(safeToString(dtp3FilterData.getNextFollowDate()));
			excelObject.setColumn25(safeToString(dtp3FilterData.getResultFollow()));
			excelObject.setColumn26(safeToString(dtp3FilterData.getAccountFollow()));
			excelObject.setColumn27(safeToString(dtp3FilterData.getCreatedAt()));
			excelObject.setColumn28(safeToString(dtp3FilterData.getUpdatedAt()));
			
			excelObjectList.add(excelObject);
		}
		
		
		return excelObjectList;
		
	}
	
	public ExcelObject DPT3FilterDataToExcelObjectGetHead () {
		ExcelObject excelObject = new ExcelObject();
		
		excelObject.setColumn1("STT");
		excelObject.setColumn2("Raw / Filter");
		excelObject.setColumn3("Nguồn data");
		excelObject.setColumn4("Ngày nhập dữ liệu");
		excelObject.setColumn5("Người nhập");
		excelObject.setColumn6("Họ và tên 1");
		excelObject.setColumn7("Họ và tên 2");
		excelObject.setColumn8("Tên");
		excelObject.setColumn9("Danh xưng");
		excelObject.setColumn10("Giới tính");
		excelObject.setColumn11("Tên Zalo");
		excelObject.setColumn12("Zalo - UID");
		excelObject.setColumn13("Link Facebook");
		excelObject.setColumn14("Số điện thoại 1");
		excelObject.setColumn15("Số điện thoại 2");
		excelObject.setColumn16("Gmail");
		excelObject.setColumn17("Ngày tháng năm sinh");
		excelObject.setColumn18("Vốn/tiền tiết kiệm");
		excelObject.setColumn19("Địa chỉ");
		excelObject.setColumn20("Nơi làm việc");
		excelObject.setColumn21("Sản phẩm đã mua");
		excelObject.setColumn22("Thông tin liên lạc chung");
		excelObject.setColumn23("Hành động tiếp theo");
		excelObject.setColumn24("Ngày thực hiện");
		excelObject.setColumn25("Kết quả thực hiện");
		excelObject.setColumn26("Tài khoản / Người thực hiện");
		excelObject.setColumn27("Ngày tạo");
		excelObject.setColumn28("Thời gian Update lần cuối");
		
		return excelObject;
		
	}
	
	public String[] DPT3FilterDataExcelGetHead () {
		String[] excelHeadArr = {
		        "STT","Raw / Filter","Nguồn data","Ngày nhập dữ liệu","Người nhập","Họ và tên 1","Họ và tên 2","Tên","Danh xưng",
				"Giới tính","Tên Zalo","Zalo - UID","Link Facebook","Số điện thoại 1","Số điện thoại 2","Gmail","Ngày tháng năm sinh",
				"Vốn/tiền tiết kiệm","Địa chỉ","Nơi làm việc","Sản phẩm đã mua","Thông tin liên lạc chung","Hành động tiếp theo",
				"Ngày thực hiện","Kết quả thực hiện","Tài khoản / Người thực hiện","Ngày tạo","Thời gian Update lần cuối"
		};
		
		
		return excelHeadArr;
		
	}
	
	public List<DTP3FilterData> ExcelObjectToDPT3FilterData (List<ExcelObject> excelObjectList) {
		List<DTP3FilterData> dtp3FilterDataList = new ArrayList<DTP3FilterData>();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		DateAndTimeProcess dateProcess = new DateAndTimeProcess();
		
		
		for(ExcelObject excelObject : excelObjectList) {
			DTP3FilterData dtp3FilterData = new DTP3FilterData();
			PhoneProcess phoneProcess = new PhoneProcess();
			

			//Process input data and add data if null

			
//			Long id = excelObject.getColumn1().isEmpty() ? null : Long.valueOf(excelObject.getColumn1());
			DataType dataType = excelObject.getColumn2().isEmpty()|| excelObject.getColumn2()==null ? DataType.RAW_DATA : DataType.fromLabel(excelObject.getColumn2());
			LocalDate dateOfLead = excelObject.getColumn4().isEmpty()|| excelObject.getColumn4()==null ? LocalDate.now() : dateProcess.parseLocalDateFlexible(excelObject.getColumn4());
			Salutation salutation = excelObject.getColumn9().isEmpty()|| excelObject.getColumn9()==null ? Salutation.UNDEFINED : Salutation.fromLabel(excelObject.getColumn9());
			Gender gender = excelObject.getColumn10().isEmpty()|| excelObject.getColumn10()==null ? Gender.UNDEFINED : Gender.fromLabel(excelObject.getColumn10());
			LocalDate dateOfBirth = excelObject.getColumn17().isEmpty()|| excelObject.getColumn17()==null ? null : dateProcess.parseLocalDateFlexible(excelObject.getColumn17());
			BigDecimal SavingsAmount = excelObject.getColumn18().isEmpty()|| excelObject.getColumn18()==null ? null :new BigDecimal(excelObject.getColumn18());
			NextAction nextAction = excelObject.getColumn23().isEmpty()|| excelObject.getColumn23()==null ? NextAction.UNDEFINED : NextAction.fromLabel(excelObject.getColumn23());
			LocalDate nextFollowDate = excelObject.getColumn24().isEmpty()|| excelObject.getColumn24()==null ? null : dateProcess.parseLocalDateFlexible(excelObject.getColumn24());
			String col27 = excelObject.getColumn27();
			LocalDateTime createdAt = (col27 == null || col27.isBlank()) ? LocalDateTime.now() : dateProcess.parseLocalDateTimeFlexible(col27);
			String col28 = excelObject.getColumn28();
			LocalDateTime updatedAt = (col28 == null || col28.isBlank()) ? LocalDateTime.now() : dateProcess.parseLocalDateTimeFlexible(col28);
			
//			dtp3FilterData.setId(id);
			dtp3FilterData.setDataType(dataType);
			dtp3FilterData.setDataSource(excelObject.getColumn3());
			dtp3FilterData.setDateOfLead(dateOfLead);
			dtp3FilterData.setConsultDiary(excelObject.getColumn5());
			dtp3FilterData.setFullName1(excelObject.getColumn6());
			dtp3FilterData.setFullName2(excelObject.getColumn7());
			dtp3FilterData.setLastName(excelObject.getColumn8());
			dtp3FilterData.setSalutation(salutation);
			dtp3FilterData.setGender(gender);
			dtp3FilterData.setZaloName(excelObject.getColumn11());
			dtp3FilterData.setZaloUid(excelObject.getColumn12());
			dtp3FilterData.setFacebookLink(excelObject.getColumn13());
			dtp3FilterData.setPhoneNumber1(phoneProcess.normalizeAndValidateVietnameseNumber(excelObject.getColumn14()));
			dtp3FilterData.setPhoneNumber2(phoneProcess.normalizeAndValidateVietnameseNumber(excelObject.getColumn15()));
			dtp3FilterData.setGmail(excelObject.getColumn16());
			dtp3FilterData.setDateOfBirth(dateOfBirth);
			dtp3FilterData.setSavingsAmount(SavingsAmount);
			dtp3FilterData.setAddress(excelObject.getColumn19());
			dtp3FilterData.setWorkingArea(excelObject.getColumn20());
			dtp3FilterData.setProductBought(excelObject.getColumn21());
			dtp3FilterData.setMixContacts(excelObject.getColumn22());
			dtp3FilterData.setNextAction(nextAction);
			dtp3FilterData.setNextFollowDate(nextFollowDate);
			dtp3FilterData.setResultFollow(excelObject.getColumn25());
			dtp3FilterData.setAccountFollow(excelObject.getColumn26());
			dtp3FilterData.setCreatedAt(createdAt);
			dtp3FilterData.setUpdatedAt(updatedAt);
			
			dtp3FilterDataList.add(dtp3FilterData);
			
		}
		
		
		
		return dtp3FilterDataList;
		
	}
	
	
	
	//==========================================Helper==========================================================	
	private static String safeToString(Object value) {
	    return value == null ? "" : value.toString();
	}
}
