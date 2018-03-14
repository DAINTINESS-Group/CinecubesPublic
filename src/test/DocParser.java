package test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DocParser {

	public DocParser() { }
	
	public static void main(final String[] args) {
		DocParser parser = new DocParser();
		String[] fileName = parser.getFilesName();
		for (int i = 0; i < fileName.length; i++) {
			if (fileName[i].isEmpty()) {
				continue;
			}
			String oldText = parser.readDocxFile("TestFiles/" + fileName[i].
					replace(".docx",  "A.docx"));
			String newText = parser.readDocxFile("OutputFiles/" + fileName[i]);
			System.out.println("Compare " + fileName[i].replace(".docx",
					"A.docx") + " with " + fileName[i]);
			parser.compareWordFile(oldText, newText);
			System.out.println("End\n");
		}
	}

    private  String readDocxFile(final String fileName) {
    	String text = "";
    	try {
            FileInputStream fis = new FileInputStream(fileName);
            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph para : paragraphs) {
            	text += para.getText() + "\n";
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    private  void compareWordFile(final String oldText, 
    		final String newText) {
    	if (newText.equals(oldText)) {
			System.out.println("Text is same");
		} else {
			System.out.println("Text is not same");
		}
    }
    
    private String[] getFilesName() {
	      File dir = new File("OutputFiles/");
	      String[] files = dir.list();
	      if (files == null) {
	          return null;
	      }
	      for (int i = 0; i < files.length; i++) {
	          if (files[i].contains(".pptx")) {
	        	  files[i] = "";
	          }
	      }
	      return files;
	  }
    
}