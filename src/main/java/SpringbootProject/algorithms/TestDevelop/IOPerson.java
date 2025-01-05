package SpringbootProject.algorithms.TestDevelop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.nio.charset.StandardCharsets;

import SpringbootProject.entity.Person;
import SpringbootProject.entity.UserEntity;

public class IOPerson {
	private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "Full_Name,Gender,Phone,Gmail,Note";
    
	public static List<Person> PersonList = new ArrayList<>();      
	
	public IOPerson() {}
	
//    public List<Address> getAddresses(List<Address> addresses){
//    	
//    } 
	
//---------------Upload file from web------------------------------------------------------------------	
	
	/*
	 * Đọc đối tượng từ File Excel
	 * */
	 public static List<UserEntity> readExcelFile(MultipartFile multipartFile) throws IOException {
		File file = null;
		try {
			file = convertMultipartFileToFile(multipartFile);
	        System.out.println("0");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        List<UserEntity> userList = new ArrayList<>();
	        System.out.println("1");

	   //--------Process---------
	        try {
	            FileInputStream excelFile = new FileInputStream(file);
		        System.out.println("2");
	            Workbook workbook = new XSSFWorkbook(excelFile);
		        System.out.println("3");
	            Sheet datatypeSheet = workbook.getSheetAt(0);
	            DataFormatter fmt = new DataFormatter();
		        System.out.println("4");
	            Iterator<Row> iterator = datatypeSheet.iterator();
	            Row firstRow = iterator.next();
		        System.out.println("5");
	            Cell firstCell = firstRow.getCell(0);
		        System.out.println("6");
	            System.out.println(firstCell.getStringCellValue());
		        System.out.println("7");
		        
	            while (iterator.hasNext()) {
	              Row currentRow = iterator.next();
	              UserEntity customer = new UserEntity();
	              customer.setId(Long.parseLong(fmt.formatCellValue(currentRow.getCell(0))));
	              customer.setFullName(currentRow.getCell(1).getStringCellValue());
	              customer.setGenderUser(currentRow.getCell(2).getStringCellValue());
	              customer.setGmail(currentRow.getCell(3).getStringCellValue());
	              customer.setStatus(currentRow.getCell(4).getStringCellValue());
	              userList.add(customer);
	            }
	            for (UserEntity customer : userList) {
	              System.out.println(customer);
	            }
	            workbook.close();
	          } catch (FileNotFoundException e) {
	            e.printStackTrace();
	          } catch (IOException e) {
	            e.printStackTrace();
	          }
	        
	        
	   //-----------------------     
	    
	    
//--------------------------------------
//	        try (FileInputStream fis = new FileInputStream(file);
//	        		Workbook workbook = new XSSFWorkbook(fis)) {
//	            System.out.println("2");
//	            Sheet sheet = workbook.getSheetAt(0);
//	            System.out.println("3");
//	            Iterator<Row> rowIterator = sheet.iterator();
//	            System.out.println("4");
//
//	            while (rowIterator.hasNext()) {
//	                Row row = rowIterator.next();
//	                // Skip header row
//	                if (row.getRowNum() == 0) {
//	                    continue;
//	                }
//
//	                UserEntity user = new UserEntity();
//
//	                Long Id = (new Double(row.getCell(0).getNumericCellValue())).longValue();
//	                String FullName = row.getCell(1).getStringCellValue();
//	                String GenderUser = row.getCell(2).getStringCellValue();
//	                String Gmail = row.getCell(3).getStringCellValue();
//	                String Status = row.getCell(4).getStringCellValue();
//
//	                user.setId(Id);
//	                System.out.println("-------------------------------Set id: ");
//	                user.setFullName(FullName);
//	                System.out.println("set FullName: "+ FullName);
//	                user.setGenderUser(GenderUser);
//	                System.out.println("set GenderUser: "+ GenderUser);
//	                user.setGmail(Gmail);
//	                System.out.println("set Gmail: "+ Gmail);
//	                user.setStatus(Status);
//	                System.out.println("set Status: "+ Status);
//
//	                userList.add(user);
//	            }
//	        }
//----------------------------------------------------------------------------------------------------
	        
	        
	        return userList;
	    }
	
	
	
	/*
	 * Đọc đối tượng từ MultipartFile Excel
	 * ĐANG BỊ LỖI
	 * */
	@SuppressWarnings("removal")
	public static List<UserEntity> readExcelMultipartFile(MultipartFile file) throws IOException {
        List<UserEntity> userList = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // Skip header row
                if (row.getRowNum() == 0) {
                    continue;
                }

                UserEntity user = new UserEntity();
                
                Long Id = (new Double(row.getCell(0).getNumericCellValue())).longValue();
                String FullName = row.getCell(1).getStringCellValue();
                String GenderUser = row.getCell(2).getStringCellValue();
                String Gmail = row.getCell(3).getStringCellValue();
                String Status = row.getCell(4).getStringCellValue();

                user.setId(Id);
                System.out.println("-------------------------------Set id: ");
                user.setFullName(FullName);
                System.out.println("set FullName: "+ FullName);
                user.setGenderUser(GenderUser);
                System.out.println("set GenderUser: "+ GenderUser);
                user.setGmail(Gmail);
                System.out.println("set Gmail: "+ Gmail);
                user.setStatus(Status);
                System.out.println("set Status: "+ Status);

                userList.add(user);
            }
        }
        
        return userList;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * Đọc đối tượng từ CSV
	 * */
	public static List<Person> ReadFileCsvUpload(MultipartFile fileCSV) throws CsvValidationException {
//		MultipartFile fileCSV = null;
//		try {
//			fileCSV = ExcelToCsvConverter.convertExcelToCsv(fileExcel);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println(">>> Read data");
		// Read data
        List<Person> person = new ArrayList<>();      
        try (CSVReader reader = new CSVReader(new InputStreamReader(fileCSV.getInputStream(), StandardCharsets.UTF_8))) {
        	String[] line;
            // Bỏ qua dòng tiêu đề
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                List<String> resultPerson = parseCsvLine(line[0].trim());
                Person newPerson = new Person();
//                System.out.println(resultPerson.get(1).trim());
                newPerson.setFullName(resultPerson.get(1).trim());
                System.out.println("Fullname: "+(resultPerson.get(1).trim()));
                newPerson.setGender(resultPerson.get(2).trim());
                newPerson.setGmail(resultPerson.get(3).trim());
                newPerson.setPhone(resultPerson.get(4).trim());
                newPerson.setNote("Chưa gửi");
                
                person.add(newPerson);
                PersonList.add(newPerson);
                System.out.println(">>> IOPerson: Read CSV method: ");
                showConsole(person);
//                person.add(new Person(resultPerson.get(0).trim(), resultPerson.get(1).trim(), resultPerson.get(2).trim(), resultPerson.get(3).trim(), resultPerson.get(4).trim()));
//                PersonList.add(new Person(resultPerson.get(0).trim(), resultPerson.get(1).trim(), resultPerson.get(2).trim(), resultPerson.get(3).trim(), resultPerson.get(4).trim()));
//                line = reader.readLine();
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }

        return person;
	}
	
	
	
	

//---------------------------------------------------------------------------------	
	
	
	
	/*
	 * Ghi đối tượng vào file
	 * */
	public void WriteFile (List<Person> persons, String opFilePath) {
		
	     FileWriter fileWriter = null;
	     
	        try {
	            fileWriter = new FileWriter(opFilePath);
	 
	            // Write the CSV file header
	            fileWriter.append(FILE_HEADER);
	 
	            // Add a new line separator after the header
	            fileWriter.append(NEW_LINE_SEPARATOR);
	 
	            // Write a new Country object list to the CSV file
	            for (Person person : persons) {
	                fileWriter.append(String.valueOf(person.getFullName()));
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(person.getGender());
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(person.getPhone());
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(person.getGmail());
	                fileWriter.append(COMMA_DELIMITER);
	                fileWriter.append(person.getNote());
	                fileWriter.append(NEW_LINE_SEPARATOR);
	            }
	 
	            System.out.println("CSV file was created successfully !!!");
	 
	        } catch (Exception e) {
	            System.out.println("Error in CsvFileWriter !!!");
	            e.printStackTrace();
	        } finally {
	            try {
	                fileWriter.flush();
	                fileWriter.close();
	            } catch (IOException e) {
	                System.out.println("Error while flushing/closing fileWriter !!!");
	                e.printStackTrace();
	            }
	        }
	}
	
	
	
	
	
	/*
	 * Đọc đối tượng từ CSV
	 * */
	public void ReadFile(String file) {
		String path = file;
        List<Person> person = new ArrayList<>();      
		
        try (BufferedReader bir = new BufferedReader(new FileReader(path))) {
            String line = bir.readLine();
            while (line != null) {
//                List<String> result = getAddressInfor(line);
//                List<String> resultAddress = parseCsvLine(result.get(0).trim());
                List<String> resultPerson = parseCsvLine(line.trim());

				/*
				 * for(int i=0;i<resultAddress.size();i++) { String str =
				 * resultAddress.get(i).trim();
				 * 
				 * System.out.println(str); }
				 */
                person.add(new Person(resultPerson.get(0).trim(), resultPerson.get(1).trim(), resultPerson.get(2).trim(), resultPerson.get(3).trim(), resultPerson.get(4).trim()));
                PersonList.add(new Person(resultPerson.get(0).trim(), resultPerson.get(1).trim(), resultPerson.get(2).trim(), resultPerson.get(3).trim(), resultPerson.get(4).trim()));
                line = bir.readLine();
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
//----------------------------------------------------------------------------------------------------------	
    	/*
    	 * Chuyển String thành các phần tử mảng
     	* */
	    public static List<String> parseCsvLine(String csvLine) {
	        List<String> result = new ArrayList<String>();
	        if (csvLine != null) {
	            String[] splitData = csvLine.split(COMMA_DELIMITER);
	            for (int i = 0; i < splitData.length; i++) {
	                result.add(splitData[i]);
	            }
	        }
	        return result;
	    }
	    
	    
    	/*
    	 * Convert MultipartFile to File
     	* */
	    private static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
	        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
	        multipartFile.transferTo(convFile);
	        return convFile;
	    }
	    
	    
	    
	    /*
	     * Hiển thị ra Console
	     * */
	    public static void showConsole(List<Person> personObject) {
	    	 System.out.println("SHOW ALL");
	            System.out.println("----------------------------------------");
	            personObject.forEach(person -> {
	                System.out.println("Full name: " + person.getFullName());
	                System.out.println("Gender: " + person.getGender());
	                System.out.println("Phone: " + person.getPhone());
	                System.out.println("Gmail: " + person.getGmail());
	                System.out.println("Note: " + person.getNote());
	                System.out.println("----------------------------------------");
	            });
	    }
	

	    
//Khác--------------------------------------------------------------------------------------------------------------------------------------
	    public static List<String> getAddressInfor(String line) {
	        List<String> result = new ArrayList<>();
	        Stack<Character> stack = new Stack<>();
	        StringBuilder str = new StringBuilder();
	        for (int i = 0; i < line.length(); i++) {
	            char ch = line.charAt(i);
	            if (ch == '\"') {
	                if (str.length() > 0 && stack.size() % 2 == 0)
	                    str.append(ch);
	                stack.push(ch);
	            } else if (ch == ',' && stack.size() % 2 == 0) {
	                result.add(str.toString());
	                stack.clear();
	                str = new StringBuilder();
	            } else if (ch == ',' && stack.size() % 2 != 0) {
	                str.append(ch);
	            } else {
	                str.append(ch);
	            }
	        }
	        result.add(str.toString());
	        return result;

		}
}
