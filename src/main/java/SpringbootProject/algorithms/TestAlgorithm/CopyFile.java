package SpringbootProject.algorithms.TestAlgorithm;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {
	
	public CopyFile() {}
	
	public void coppyFile ()throws IOException {
	FileInputStream in = null;
    FileOutputStream out = null;

    try {
       in = new FileInputStream("D:\\Desktop\\Diary\\Working daily\\input.txt");
       out = new FileOutputStream("D:\\Desktop\\Diary\\Working daily\\output.txt");

       int c;
       while ((c = in.read()) != -1) {
          out.write(c);
       }
    }finally {
       if (in != null) {
          in.close();
       }
       if (out != null) {
          out.close();
       }
    }
	}
}

