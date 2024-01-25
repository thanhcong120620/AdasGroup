package SpringbootProject.algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import SpringbootProject.entity.Person;

public class IOPerson {
	private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_HEADER = "Full_Name,Gender,Phone,Gmail,Note";
    
	public static List<Person> PersonList = new ArrayList<>();      
	
	public IOPerson() {}
	
//    public List<Address> getAddresses(List<Address> addresses){
//    	
//    } 
	
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
	     * Hiển thị ra Console
	     * */
	    public void showConsole(List<Person> personObject) {
	    	 System.out.println("SHOW ALL");
	            System.out.println("----------------------------------------");
	            personObject.forEach(person -> {
	                System.out.println("Last name: " + person.getFullName());
	                System.out.println("First name: " + person.getGender());
	                System.out.println("Street: " + person.getPhone());
	                System.out.println("District: " + person.getGmail());
	                System.out.println("City: " + person.getNote());
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
