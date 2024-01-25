package SpringbootProject.algorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.thymeleaf.templateparser.markup.HTMLTemplateParser;

public class HTMLFileToString {

	public HTMLFileToString() {}
	
	public String convertHTMLtoString(String htmlPath) {
		String htmlContent ="Lỗi";
		String dynamicValue = "Nguyễn Thành Công!";
		String htmlWithParameters ="Lỗi Parameter";
			
		try {
             htmlContent = readFileToString(htmlPath);
             htmlWithParameters = htmlContent.replace("${dynamicValue}", dynamicValue);
//            System.out.println(htmlContent);
//            System.out.println(htmlWithParameters);
//            return htmlContent;
            return htmlWithParameters;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return htmlWithParameters;
    }

    private static String readFileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
    }
	
	
}
