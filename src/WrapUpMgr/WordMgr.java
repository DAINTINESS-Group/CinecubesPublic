package WrapUpMgr;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.TextAlign;
import org.apache.poi.xslf.usermodel.VerticalAlignment;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.apache.poi.xslf.usermodel.XSLFTableRow;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlObject;

import StoryMgr.Act;
import StoryMgr.FinalResult;
import StoryMgr.PptxSlide;
import StoryMgr.Story;
import StoryMgr.Tabular;

public class WordMgr extends WrapUpMgr{

    XWPFDocument document = new XWPFDocument();	
	private ArrayList<String> fileList;
	private String Contenttype;
	private String ContenttypeNotesDef;
	public String UnZipZipTime;
	
	public 	WordMgr() {
		fileList=new ArrayList<>();
		ContenttypeNotesDef="";
		UnZipZipTime="";
	}
	
	@Override	
	public void doWrapUp(Story story){
       
        int num_slide_create=0;
		for(Act actItem : story.getActs()) {
			if(actItem.getEpisodes().size()>1 || num_slide_create==0 || actItem.getId()==-1 || actItem.getId()==20 ) num_slide_create+=actItem.getEpisodes().size();
		}
		
        
		int slide_so_far_created=0;
		for(Act actItem : story.getActs()){
			if(actItem.getId()==0){
				PptxSlide slide=(PptxSlide)actItem.getEpisodes().get(0);
				createIntroSenction(slide);
				slide_so_far_created+=actItem.getEpisodes().size();
			}
			else if(actItem.getId()==-1){
				PptxSlide slide=(PptxSlide)actItem.getEpisodes().get(0);
				createSummarySenction(slide);
				slide_so_far_created+=actItem.getEpisodes().size();	
			}
			else if(actItem.getEpisodes().size()>1 || actItem.getId()==20){
				for(int j=0;j<actItem.getEpisodes().size();j++){
					PptxSlide slide=(PptxSlide)actItem.getEpisodes().get(j);
										
					if(slide.getTitle().contains("Act")) createSenction(null,null,slide.getAudio().getFileName(),slide.getTitle(),j+slide_so_far_created+2,null,null,slide.getSubTitle(),null,(actItem.getId()==3 ? 0 :1));
					else if(slide.getNotes().length()==0) {
						Tabular tmp_tbl=((Tabular)slide.getVisual());
						createSenction(slide.getVisual().getPivotTable(),tmp_tbl.colortable,null,slide.getTitle(),j+slide_so_far_created+2,null,null,slide.getSubTitle(),(Tabular)slide.getVisual(),(actItem.getId()==3 ? 0 :1));
					}
					else {
						Tabular tmp_tbl=((Tabular)slide.getVisual());
						createSenction(slide.getVisual().getPivotTable(),tmp_tbl.colortable,slide.getAudio().getFileName(),slide.getTitle(),j+slide_so_far_created+2,slide.getTitleColumn(),slide.getTitleRow(),slide.getSubTitle(),(Tabular)slide.getVisual(),(actItem.getId()==3 ? 0 :1));
						
						XWPFParagraph paragraph = document.createParagraph();
					    XWPFRun run = paragraph.createRun();
					    run.setText(slide.getNotes());
					    run.addBreak();
					}
					
				}
				slide_so_far_created+=actItem.getEpisodes().size();
			}
		}
		
		FileOutputStream fout;
		try  {
			
            fout=new FileOutputStream(this.finalResult.getFilename());
            document.write(fout);
        	
            fout.close();
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        } 
                
	}
	
	public void createIntroSenction(PptxSlide episode){
		
		XWPFParagraph paragraph = document.createParagraph();
    	XWPFRun run = paragraph.createRun();
    	XWPFRun run2 = paragraph.createRun();
		
    	run.setBold(true);
    	run.setFontSize(24);
    	run.setText(episode.getTitle());
    	run.addBreak();
    	run2.setText(episode.getSubTitle());
		run2.addBreak();
    	
    	
	}
	
	public void createSummarySenction(PptxSlide episode){
		XWPFParagraph paragraph = document.createParagraph();
    	XWPFRun run = paragraph.createRun();
    	XWPFRun run2 = paragraph.createRun();
		
    	run.setBold(true);
    	run.setFontSize(24);
    	run.setText(episode.getTitle());
    	run.addBreak();
    	
    	String[] findings=episode.getNotes().split("@");
    	for(String finding:findings){    		
    		String[] lines=finding.replace("~~\n", "~~").split("\n");
    		
    		int lvl1=1;
    		if(lines.length==1) continue;
    		for(int i=0;i<lines.length;i++){
    			//if(twolevel) lvl1=2;
    			if(lines[i].contains("~~")) {
    				lvl1=2;
    				lines[i]=lines[i].replace("~~","");
    			}
    			else if(lines[i].contains("##")) {
    				lvl1=1;
    				lines[i]=lines[i].replace("##","");
    			}
    	     
    	        run2.setText(lines[i]);
    	        run2.setFontSize(14);
    	        run2.addBreak();
    	        
    		}
    	}
    	episode.setNotes(episode.getNotes().replace("@","\n").replace("~~", "").replace("##", ""));
    	
	}
	
	public void createSenction(String[][] table, 
								Color[][] colorTable,
								String AudioFilename,
								String Title,
								int slideid,
								String titleColumn, 
								String titleRow,							
								String subtitle, 
								Tabular tabular,
								int hide_slide){ 
        
        XWPFParagraph paragraph = document.createParagraph();
    	XWPFRun run = paragraph.createRun();
    	XWPFRun run2 = paragraph.createRun();
        
        if(table!=null) {
	        
        	CreateTable(table,colorTable,titleColumn,titleRow,tabular);   	
        	run.setBold(true);
        	run.setFontSize(12);
        	run.setText(Title);
        	run.addBreak();
        }
        else {
        	
        	
        	run.setBold(true);
        	run.setFontSize(24);
        	run.setText(Title);
        	run.addBreak();
        	
        	run2.setText(subtitle);
        	run2.addBreak();
        	
        }
                
     }
     
    
     private void CreateTable(String[][] table, Color[][] colorTable,String titleColumn, String titleRow, Tabular tabular){
    	 int toColorDarkGray=0;
         XWPFTable pin=document.createTable();
         if(table[0][0].length()>0 && table[1][0].length()==0) toColorDarkGray=1; 
         
         for(int i=0;i<table.length;i++){
             XWPFTableRow row = pin.createRow();
             for(int j=0;j<table[0].length;j++){
                XWPFTableCell cell = row.createCell();
                XWPFParagraph p = cell.addParagraph();
                XWPFRun r = p.createRun();
                r.setFontFamily("Calibri");  
                r.setFontSize(12);
              
                if(j==tabular.boldColumn) r.setBold(true); 
                if(i==tabular.boldRow) r.setBold(true); 
                try{
                  int color =Integer.valueOf(colorTable[i][j].getRGB());
                  if(color==-16776961){
                	    r.setColor("0000FF");//blue
                  }else if(color==-65536){
                	  r.setColor("FF0000");//red	 
                  }else{
                	  r.setColor("000000");//black  
                  }
                  r.setText(table[i][j]);
              	  
              	  if(table[i][j].equals(""))
                    
                   
                    if(toColorDarkGray==1 && j==0) {
                    r.setItalic(true);
                    r.setColor("000000");
             
                    }
                    
                }
                catch (Exception e) {
              	  r.setText("");
                }
             }
         }
       
     }
     
    
     
   

	@Override
	public FinalResult getFinalResult() {
		return finalResult;
	}

	@Override
	public void setFinalResult(FinalResult finalresult) {
		finalResult=finalresult;
	}

	
    
    
  
   
    public void GenerateFileList(File node){
 
    	//add file only
		if(node.isFile()){
			fileList.add(node.toString().replace("ppt/uzip", ""));
		}
	 
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				GenerateFileList(new File(node, filename));
			}
		}
 
    }
 
    public void DeleteFolder(File file){
        
        if(file.isDirectory()){
             //directory is empty, then delete it
            if(file.list().length==0){
                file.delete();
            }
            else{

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    DeleteFolder(fileDelete);
                }
                //check the directory again, if empty then delete it
                if(file.list().length==0){
                    file.delete();
                }
            }
        }
        else {
            //if file, then delete it
            file.delete();
        }
    }
    
    
    
    /* Set Title In Slide */
    void setTitle(XSLFSlide slide,String Title,Rectangle2D.Double Anchor,double fontSize,boolean bold){
    	  	XSLFTextShape title1 = slide.getPlaceholder(0);
	    	title1.clearText(); /*Clear The txt Type Here Title*/
	    	XSLFTextRun tltTxtRun =title1.addNewTextParagraph().addNewTextRun();
	    	tltTxtRun.setFontFamily("Arial");
	        tltTxtRun.setBold(bold);
	    	tltTxtRun.setFontSize(fontSize);
	    	tltTxtRun.setText(Title);
    }


}
