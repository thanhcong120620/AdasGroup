package SpringbootProject.algorithms.PersonProfileProcessAlgorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SpringbootProject.entity.notSaving.ExcelObject;

public class PersonProfileProcessFunction {
	NameProcess nameProcess = new NameProcess();


//=============================================NAME PROCES================================================================
	public String extractFirstName (String fullName, String nickname) {
		return nameProcess.extractFirstName(fullName, nickname);
		
	}
	
	public String getNomalizeName (String name) {
		return nameProcess.convertVietnameseNameToEnglishName(name);
	}
	
//=============================================PHONE PROCESS==============================================================
	
	/*
	 * Lấy hàm FilterInternalDuplicatesOnly trong Phone Process
	 * */
	public Map<String, Object> phoneProcessFilterInternalDuplicatesOnly (List<ExcelObject> excelObjectList) {
		PhoneProcess phoneProcess = new PhoneProcess();
		PhoneProcess.PhoneProcessingResult result = phoneProcess.filterInternalDuplicatesOnly(excelObjectList);
		
		List<ExcelObject> objectFilteredList =  result.getFilteredList();
		List<ExcelObject> objectErrorList = result.getRemovedItems();
		String[] objectCountStatus = result.getStatusMessages();
		
		Map<String, Object> excelResult = new HashMap<>();
        excelResult.put("filteredList", objectFilteredList);
        excelResult.put("errorList", objectErrorList);
        excelResult.put("countStatus", objectCountStatus);
		
		return excelResult;
		
	}
	
	/*
	 * Lấy hàm filterAndValidatePhoneDataIntergrated trong Phone Process
	 * */
	public Map<String, Object> phoneProcessFilterAndValidatePhoneDataIntergrated (List<ExcelObject> excelObjectListOrigin, List<ExcelObject> excelObjectListFilter) {
		

		
		PhoneProcess phoneProcess = new PhoneProcess();
		PhoneProcess.PhoneProcessingResult result = phoneProcess.filterAndValidatePhoneDataIntergrated(excelObjectListOrigin, excelObjectListFilter);
		
		List<ExcelObject> objectFilteredIntergratedList =  result.getFilteredList();
		List<ExcelObject> objectErrorIntergratedList = result.getRemovedItems();
		String[] objectCountIntergratedStatus = result.getStatusMessages();
		

		
		Map<String, Object> excelResult = new HashMap<>();
        excelResult.put("filteredList", objectFilteredIntergratedList);
        excelResult.put("errorList", objectErrorIntergratedList);
        excelResult.put("countStatus", objectCountIntergratedStatus);
		
		return excelResult;
		
	}
	
	
	/*
	 * Lấy hàm extractAndValidateVietnameseNumbers trong Phone Process
	 * */
	public List<String> phoneProcessExtractAndValidateVietnameseNumbers (String rawPhone) {
		PhoneProcess phoneProcess = new PhoneProcess();
		return phoneProcess.extractAndValidateVietnameseNumbers(rawPhone);
	}
}