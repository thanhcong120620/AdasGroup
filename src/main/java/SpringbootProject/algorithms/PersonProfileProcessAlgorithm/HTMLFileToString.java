package SpringbootProject.algorithms.PersonProfileProcessAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.thymeleaf.templateparser.markup.HTMLTemplateParser;

public class HTMLFileToString {

	public HTMLFileToString() {}
	 public String convertHTMLtoString(String filePath, Map<String, String> variables) throws IOException {
	        String template = new String(Files.readAllBytes(Paths.get(filePath)));
	        return replaceVariables(template, variables);
	    }

	    private String replaceVariables(String template, Map<String, String> variables) {
	        for (Map.Entry<String, String> entry : variables.entrySet()) {
	            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
	        }
	        return template;
	    }
	//-----------------------------------
//	public String convertHTMLtoString(String htmlPath,String headerName, String normalName, String caplockName,
//			String gender) {
//		String htmlContent ="Lỗi";
//		String HeaderName = headerName;
//		System.out.println(">> Name: "+ headerName);
//		String Gender = gender;
//		String htmlWithParameterHeaderName ="Xin chào Anh/Chị";
//			
//		try {
//             htmlContent = readFileToString(htmlPath);
//             htmlWithParameterHeaderName = htmlContent.replace("${HeaderName}", HeaderName);
////            System.out.println(htmlContent);
////            System.out.println(htmlWithParameters);
////            return htmlContent;
//            return htmlWithParameterHeaderName;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//		return htmlWithParameterHeaderName;
//    }
//	
//
//    private static String readFileToString(String filePath) throws IOException {
//        Path path = Paths.get(filePath);
//        byte[] bytes = Files.readAllBytes(path);
//        return new String(bytes);
//    }
	
	//---------------------------------------------------
}
