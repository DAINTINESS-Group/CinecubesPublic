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
import org.apache.xmlbeans.XmlObject;

import StoryMgr.Act;
import StoryMgr.FinalResult;
import StoryMgr.PptxSlide;
import StoryMgr.Story;
import StoryMgr.Tabular;

public class PptxWrapUpMgr extends WrapUpMgr {

	XMLSlideShow slideShowPPTX;
	XSLFSlideMaster defaultMaster;
	private String[] SlideXml;	
	private ArrayList<String> fileList;
	private String Contenttype;
	private String ContenttypeNotesDef;
	public String UnZipZipTime;
	
	public PptxWrapUpMgr() {
		fileList=new ArrayList<>();
		ContenttypeNotesDef="";
		UnZipZipTime="";
	}
	
	@Override	
	public void doWrapUp(Story story){
        InputStream file = getClass().getClassLoader().getResourceAsStream("resources/notes.pptx");
        try {
			slideShowPPTX = new XMLSlideShow(file);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//defaultMaster = slideShowPPTX.getSlideMasters()[0];
        defaultMaster = slideShowPPTX.getSlideMasters()[0];
        
        int num_slide_create=0;
		for(Act actItem : story.getActs()) {
			if(actItem.getEpisodes().size()>1 || num_slide_create==0 || actItem.getId()==-1 || actItem.getId()==20 ) num_slide_create+=actItem.getEpisodes().size();
		}
		
        SlideXml=new String[num_slide_create];
        
		int slide_so_far_created=0;
		for(Act actItem : story.getActs()){
			if(actItem.getId()==0){
				SlideXml[slide_so_far_created]="";
				PptxSlide slide=(PptxSlide)actItem.getEpisodes().get(0);
				slide.timeCreationPutInPPTX=System.nanoTime()-slide.timeCreationPutInPPTX;
				XSLFcreateIntroSlide(slide);
				slide.timeCreationPutInPPTX=System.nanoTime()-slide.timeCreationPutInPPTX;
				slide_so_far_created+=actItem.getEpisodes().size();
			}
			else if(actItem.getId()==-1){
				PptxSlide slide=(PptxSlide)actItem.getEpisodes().get(0);
				slide.timeCreationPutInPPTX=System.nanoTime();
				XSLFcreateSummarySlide(slide,slide_so_far_created+2);
				slide.timeCreationPutInPPTX=System.nanoTime()-slide.timeCreationPutInPPTX;
				slide_so_far_created+=actItem.getEpisodes().size();	
			}
			else if(actItem.getEpisodes().size()>1 || actItem.getId()==20){
				for(int j=0;j<actItem.getEpisodes().size();j++){
					SlideXml[j+slide_so_far_created]="";
					PptxSlide slide=(PptxSlide)actItem.getEpisodes().get(j);
					
					slide.timeCreationPutInPPTX=System.nanoTime();
					
					if(slide.getTitle().contains("Act")) XSLFcreateSlide(null,null,slide.getAudio().getFileName(),slide.getTitle(),j+slide_so_far_created+2,null,null,slide.getSubTitle(),null,(actItem.getId()==3 ? 0 :1));
					else if(slide.getNotes().length()==0) {
						Tabular tmp_tbl=((Tabular)slide.getVisual());
						XSLFcreateSlide(slide.getVisual().getPivotTable(),tmp_tbl.colortable,null,slide.getTitle(),j+slide_so_far_created+2,null,null,slide.getSubTitle(),(Tabular)slide.getVisual(),(actItem.getId()==3 ? 0 :1));
					}
					else {
						Tabular tmp_tbl=((Tabular)slide.getVisual());
						XSLFcreateSlide(slide.getVisual().getPivotTable(),tmp_tbl.colortable,slide.getAudio().getFileName(),slide.getTitle(),j+slide_so_far_created+2,slide.getTitleColumn(),slide.getTitleRow(),slide.getSubTitle(),(Tabular)slide.getVisual(),(actItem.getId()==3 ? 0 :1));
					}
					
					slide.timeCreationPutInPPTX=System.nanoTime()-slide.timeCreationPutInPPTX;
				}
				slide_so_far_created+=actItem.getEpisodes().size();
			}
		}
		slideShowPPTX.removeSlide(0);
		
		FileOutputStream fout;
		try  {
            fout=new FileOutputStream(this.finalResult.getFilename());
            slideShowPPTX.write(fout);
            fout.close();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        } 
        
       // System.out.println(SlideXml.length);
        long StartUnzip=System.nanoTime();
        RenamePPTXtoZip();
        UnZipFiles();
        InitializeContentType();
        slide_so_far_created=0;
        //System.out.println("Unzip Time\t"+(System.nanoTime()-StartUnzip));
        UnZipZipTime="Unzip Time\t"+(System.nanoTime()-StartUnzip)+"\n";
        for(Act actItem : story.getActs()){
        	if(actItem.getEpisodes().size()>1 || actItem.getId()==0 || actItem.getId()==-1 || actItem.getId()==20){
				for(int j=0;j<actItem.getEpisodes().size();j++){
					PptxSlide slide=(PptxSlide)actItem.getEpisodes().get(j);
					
					long strTime=System.nanoTime();
					
					if(slide.getNotes().length()==0) AddAudiotoPPTX(j+slide_so_far_created+2,null,slide.getNotes());
					else AddAudiotoPPTX(j+slide_so_far_created+2,slide.getAudio().getFileName(),slide.getNotes());
					
					slide.timeCreationPutInPPTX+=System.nanoTime()-strTime;
				}
				slide_so_far_created+=actItem.getEpisodes().size();
        	}
        }
        long StartZip=System.nanoTime();
        writeContentType();
        GenerateFileList(new File("ppt/unzip"));
        ZipFiles();
        RenameZiptoPPTX();
        //System.out.println("Zip Time\t"+(System.nanoTime()-StartZip));
        UnZipZipTime="Zip Time\t"+(System.nanoTime()-StartZip)+"\n";
                
	}
	
	public void XSLFcreateIntroSlide(PptxSlide episode){
		XSLFSlide slide;
		slide=slideShowPPTX.createSlide(defaultMaster.getLayout(SlideLayout.TITLE)); 
		
		XSLFTextShape title1 = slide.getPlaceholder(0);
		title1.clearText();
    	XSLFTextParagraph p=title1.addNewTextParagraph();
    	XSLFTextRun tltTxtRun =p.addNewTextRun();
    	tltTxtRun.setText(episode.getTitle());
    	p.setTextAlign(TextAlign.LEFT);
    	
    	XSLFTextShape title2 = slide.getPlaceholder(1);
    	title2.clearText();
    	XSLFTextParagraph p2=title2.addNewTextParagraph();
    	XSLFTextRun tltTxtRun2 =p2.addNewTextRun();
    	
        tltTxtRun2.setBold(false);	    	
    	tltTxtRun2.setText(episode.getSubTitle());
    	tltTxtRun2.setFontSize(20);
    	p2.setTextAlign(TextAlign.JUSTIFY);
    	
    	setRelationshipForNotes(slide,2);
    	
    	CreateSlideWithXMlAudio(slide,episode.getAudio().getFileName(),2,1);
    	
	}
	
	public void XSLFcreateSummarySlide(PptxSlide episode,int slideId){
		XSLFSlide slide;
		slide=slideShowPPTX.createSlide(defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT)); 
		XSLFTextShape title1 = slide.getPlaceholder(0);
    	title1.setText(episode.getTitle());
    	
    	XSLFTextShape title2 = slide.getPlaceholder(1);
    	title2.clearText();
    	
    	String[] findings=episode.getNotes().split("@");
    	for(String finding:findings){  
    		String[] lines=finding.replace("~~\n", "~~").split("\n");
    		
    		int lvl1=1;
    		if(lines.length==1) continue;
    		for(int i=0;i<lines.length;i++){
    			XSLFTextParagraph p=title2.addNewTextParagraph();
    			//if(twolevel) lvl1=2;
    			if(lines[i].contains("~~")) {
    				lvl1=2;
    				lines[i]=lines[i].replace("~~","");
    			}
    			else if(lines[i].contains("##")) {
    				lvl1=1;
    				lines[i]=lines[i].replace("##","");
    			}
    	        p.setBullet(true);
    	        if(i==0) p.setLevel(0);
    	        else p.setLevel(lvl1);
    	        
    	        XSLFTextRun tltTxtRun =p.addNewTextRun();
    	        tltTxtRun.setBold(false);
    	        tltTxtRun.setText(lines[i]);
    	        tltTxtRun.setFontSize(14);
    	        
    		}
    	}
    	episode.setNotes(episode.getNotes().replace("@","\n").replace("~~", "").replace("##", ""));
    	
    	setRelationshipForNotes(slide,slideId);
    	CreateSlideWithXMlAudio(slide,episode.getAudio().getFileName(),slideId,1);
	}
	
	public void XSLFcreateSlide(String[][] table, 
								Color[][] colorTable,
								String AudioFilename,
								String Title,
								int slideid,
								String titleColumn, 
								String titleRow,							
								String subtitle, 
								Tabular tabular,
								int hide_slide){
		XSLFSlideLayout titleLayout = defaultMaster.getLayout(SlideLayout.TITLE_ONLY); 
        XSLFSlide slide;
        
        
        if(table!=null) {
        	slide=slideShowPPTX.createSlide(titleLayout);
        	slide.setFollowMasterGraphics(true);
        		           
	        setRelationshipForNotes(slide,slideid);
	        
        	CreateTableInSlide(slide, slideShowPPTX.getPageSize(),table,colorTable,titleColumn,titleRow,tabular);
        	this.setTitle(slide, Title, new Rectangle2D.Double(100, 25,slideShowPPTX.getPageSize().width-200,20),16.0,true);
        }
        else {
        	slide=slideShowPPTX.createSlide(defaultMaster.getLayout(SlideLayout.TITLE)); 
        	
        	setRelationshipForNotes(slide,slideid);
        	XSLFTextShape title1 = slide.getPlaceholder(0);
        	title1.setText(Title);
        	
        	XSLFTextShape title2 = slide.getPlaceholder(1);
        	title2.clearText();
        	XSLFTextParagraph p=title2.addNewTextParagraph();
        	
        	 XSLFTextRun tltTxtRun =p.addNewTextRun();
        	
	    	
	        tltTxtRun.setBold(false);	    	
	    	tltTxtRun.setText(subtitle);
	    	tltTxtRun.setFontSize(20);
        	p.setTextAlign(TextAlign.JUSTIFY);
        	
        }
        CreateSlideWithXMlAudio(slide,AudioFilename,slideid,hide_slide);
                
     }
     
     void setRelationshipForNotes(XSLFSlide slide,int slideid){
    	String NotesRelationShip="http://schemas.openxmlformats.org/officeDocument/2006/relationships/notesSlide";
    	URI uri = null;
        try {
            uri = new URI("../notesSlides/notesSlide"+slideid+".xml");
        } catch (URISyntaxException ex) {
        	System.out.println(ex.getMessage());
        }
        PackageRelationship addRelationship= slide.getPackagePart().addRelationship(uri, TargetMode.INTERNAL, NotesRelationShip);
        slide.addRelation(addRelationship.getId(), slide); 
     }
	
    /*  rId1 -->MEDIA relationship ID
     *  rId2 -->AUDIO relationship ID
     *  rId4 -->IMAGE relationship ID
     *
     */
     private XSLFTable CreateTableInSlide(XSLFSlide slide,java.awt.Dimension pgsize,String[][] table, Color[][] colorTable,String titleColumn, String titleRow, Tabular tabular){
         
       int page_width = pgsize.width; //slide width
       int toColorDarkGray=0;
       XSLFTable tbl=slide.createTable();
       Color other=Color.black;
       if(table[0][0].length()>0 && table[1][0].length()==0) toColorDarkGray=1; 
       
       for(int i=0;i<table.length;i++){
           XSLFTableRow addRow = tbl.addRow();
           for(int j=0;j<table[0].length;j++){
              XSLFTableCell cell = addRow.addCell();
              XSLFTextParagraph p = cell.addNewTextParagraph();
              XSLFTextRun r = p.addNewTextRun();
              p.setTextAlign(TextAlign.CENTER);
              //r.setFontFamily("Calibri");  
              //cell.set
              cell.setTopInset(0);
              cell.setLeftInset(0);
              cell.setRightInset(0);
              cell.setBottomInset(0);
              r.setFontSize(12);
              if(j==tabular.boldColumn) r.setBold(true);
              if(i==tabular.boldRow) r.setBold(true);
              try{
            	  r.setFontColor(colorTable[i][j]);
            	  r.setText(table[i][j]);
            	  if(table[i][j].equals("")) cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                  
                  if((i==0 || j==toColorDarkGray) && !(i==0 && j==toColorDarkGray)) {
                	  r.setFontColor(other);
                  }
                  if(toColorDarkGray==1 && j==0) {
                	 r.setItalic(true);
                	 r.setFontColor(Color.black);
                  }
                  if((j==0 && i>0 ) || ((j==0 || j==1) && toColorDarkGray==1)) p.setTextAlign(TextAlign.RIGHT);
                  if(j==0 || ((j==0 || j==1) && toColorDarkGray==1)) cell.setLeftInset(0.5);
              }
              catch (Exception e) {
            	  r.setText("");
              }
           }
       }
       double table_width=0;
       for(int k=0;k<tbl.getNumberOfColumns();k++){
    	   table_width+=tbl.getColumnWidth(k);
       }
       tbl.setAnchor(new Rectangle2D.Double(((page_width/2)-table_width/2), 100,100,100));
       
       /*for(int i=0;i<tbl.getNumberOfRows();i++){
    	   for(int j=0;j<tbl.getNumberOfColumns();j++){
    		   tbl.getRows().get(j).getCells().get(j).setBorderBottom(0);
    		   tbl.getRows().get(j).getCells().get(j).setBorderLeft(0);
    		   tbl.getRows().get(j).getCells().get(j).setBorderTop(0);
    	   }
       }*/
       
       return tbl;
     }
     
     private void CreateSlideWithXMlAudio(XSLFSlide slide,String AudioFilename,int slideid, int hide_slide){
       String AudioRelationShip="http://schemas.openxmlformats.org/officeDocument/2006/relationships/audio";
       String MediaRelationShip="http://schemas.microsoft.com/office/2007/relationships/media" ;
       String ImageRelationShip="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image";
       PackagePart packagePart = slide.getPackagePart();
       XmlObject xmlObject = slide.getXmlObject();
       try {
    	   if(AudioFilename!=null){
	            URI uri = null;
	            URI uri2=null;
	            try {
	            	String tmp=AudioFilename.replace("audio/", "");
	                uri = new URI("../media/"+tmp+".wav");
	                uri2 = new URI("../media/play.png");
	            } catch (URISyntaxException ex) {
	                System.out.println(ex.getMessage());
	            }
	            
	            PackageRelationship addRelationship2 = packagePart.addRelationship(uri, TargetMode.INTERNAL, MediaRelationShip);
	            PackageRelationship addRelationship1 = packagePart.addRelationship(uri, TargetMode.INTERNAL, AudioRelationShip);
	            PackageRelationship addRelationship3 = packagePart.addRelationship(uri2, TargetMode.INTERNAL, ImageRelationShip);
	            slide.addRelation(addRelationship3.getId(), slide);
	            slide.addRelation(addRelationship2.getId(), slide);
	            slide.addRelation(addRelationship1.getId(), slide);
	            	            
	            int id=4;
	            if(slideid-2==0){
	            	String SoundNode = this.SoundNodeString(addRelationship2.getId(), addRelationship1.getId(), addRelationship3.getId(),AudioFilename,id++);
	            	try {
	            		byte[] pictureData=new byte[getClass().getClassLoader().getResourceAsStream("resources/cube_dali.png").available()];
	            		getClass().getClassLoader().getResourceAsStream("resources/cube_dali.jpg").read(pictureData);
	            		int idx = slideShowPPTX.addPicture(pictureData, XSLFPictureData.PICTURE_TYPE_JPEG);
	                    XSLFPictureShape pic = slide.createPicture(idx);
	                    pic.setAnchor(new Rectangle2D.Double(slideShowPPTX.getPageSize().getWidth()-300, 0,300,300));
	                    xmlObject = slide.getXmlObject();
	        		} catch (IOException e) {
	        			System.err.print("In add picture:");
	        			e.printStackTrace();
	        		}
	            	String test="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+xmlObject.toString().replace("main1","a").replace("<main", "<p").replace("</main", "</p").replace(":main=",":p=").replace("rel=","r=").replace("rel:","r:").replace("xml-fragment", "p:sld").replace("</p:spTree>",SoundNode+"</p:spTree>");
	 	            test=test.replace("<p:cSld>","<p:cSld>"+setBackroundToSlide());
	 	            SlideXml[slideid-2]=test.replace("</p:sld>", AutoSlideShow()+TimingNode(id++)+"</p:sld>");
	            }
	            else{
	            	String SoundNode = this.SoundNodeString(addRelationship2.getId(), addRelationship1.getId(), addRelationship3.getId(),AudioFilename,id);
	            	String test="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+xmlObject.toString().replace("main1","a").replace("<main", "<p").replace("</main", "</p").replace(":main=",":p=").replace("rel=","r=").replace("rel:","r:").replace("xml-fragment", "p:sld").replace("</p:spTree>",SoundNode+"</p:spTree>");
	            	test=test.replace("<p:cSld>","<p:cSld>"+setBackroundToSlide());
	            	SlideXml[slideid-2]=test.replace("</p:sld>", AutoSlideShow()+TimingNode(id)+"</p:sld>").replace("<p:sld ", "<p:sld xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" show=\""+String.valueOf(hide_slide)+"\" ");
	           }
    	   }
    	   else{
    		   String test="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+xmlObject.toString().replace("main1","a").replace("<main", "<p").replace("</main", "</p").replace(":main=",":p=").replace("rel=","r=").replace("rel:","r:").replace("xml-fragment", "p:sld");
	           SlideXml[slideid-2]=test.replace("</p:sld>", AutoSlideShow()+"</p:sld>").replace("<p:sld ", "<p:sld show=\""+String.valueOf(hide_slide)+"\" ");;
    	   }
            
            
        } catch (Exception ex) {
        	System.err.print("In add audio:");
        	System.err.println(ex.getMessage());
        }
     }
     
     String setBackroundToSlide(){
    	 String color_in_hex="EBE9E9";
    	 String backgroundNode="	<p:bg>" +
    	 		"			<p:bgPr>\n" +
    	 		"				<a:solidFill>" +
    	 		"					<a:srgbClr val=\""+color_in_hex+"\"/>" +
    	 		"				</a:solidFill>" +
    	 		"				<a:effectLst/>" +
    	 		"			</p:bgPr>" +
    	 		"		</p:bg>";
    	 return backgroundNode;
     }
     
     private String SoundNodeString(String rId1,String rId2,String rId4,String AudioFilename,int id){
    	 
         String ret_value= "<p:pic>"
                 + "<p:nvPicPr>"
                 + "<p:cNvPr id=\""+String.valueOf(id)+"\" name=\""+AudioFilename.replace("audio/", "").concat(".wav") +"\">"
                 + "<a:hlinkClick r:id=\"\" action=\"ppaction://media\"/>"
                 + "</p:cNvPr>"
                 + "<p:cNvPicPr>"
                 + "<a:picLocks noChangeAspect=\"1\"/>"
                 + "</p:cNvPicPr>"
                 + "<p:nvPr>"
                 + "<a:audioFile r:link=\""+rId2+"\"/>"
                 + "<p:extLst>"
                 + "<p:ext uri=\"{DAA4B4D4-6D71-4841-9C94-3DE7FCFB9230}\">"
                 + "<p14:media xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" r:embed=\""+rId1+"\"/>"
                 + "</p:ext>"
                 + "</p:extLst>"
                 + "</p:nvPr>"
                 + "</p:nvPicPr>"
                 + "<p:blipFill>"
                 + "<a:blip r:embed=\""+rId4+"\"/>"
                 + "<a:stretch>"
                 + "<a:fillRect/>"
                 + "</a:stretch>"
                 + "</p:blipFill>"
                 + "<p:spPr>"
                 + "<a:xfrm>"
                 + "<a:off x=\"0\" y=\"0\"/>"
                 + "<a:ext cx=\"609600\" cy=\"609600\"/>"
                 + "    </a:xfrm>"
                 + "<a:prstGeom prst=\"rect\">"
                 + "<a:avLst/>"
                 + "</a:prstGeom>"
                 + "</p:spPr>"
                 + "</p:pic>";
         return ret_value;
     }
     
     private String TimingNode(int id){
         String ret_value="<p:timing>"
                    +"<p:tnLst>"
                    +"<p:par>"
                    +"<p:cTn id=\"1\" dur=\"indefinite\" restart=\"never\" nodeType=\"tmRoot\">"
                            +"<p:childTnLst>"
                    +"<p:seq concurrent=\"1\" nextAc=\"seek\">"
                            +"<p:cTn id=\"2\" dur=\"indefinite\" nodeType=\"mainSeq\">"
                    +"<p:childTnLst>"
                            +"<p:par>"
                    +"<p:cTn id=\"3\" fill=\"hold\">"
                            +"<p:stCondLst>"
                    +"<p:cond delay=\"indefinite\"/>"
                    +"<p:cond evt=\"onBegin\" delay=\"0\">"
                            +"<p:tn val=\"2\"/>"
                    +"</p:cond>"
                            +"</p:stCondLst>"
                            +"<p:childTnLst>"
                    +"<p:par>"
                            +"<p:cTn id=\"4\" fill=\"hold\">"
                    +"<p:stCondLst>"
                            +"<p:cond delay=\"0\"/>"
                    +"</p:stCondLst>"
                    +"<p:childTnLst>"
                            +"<p:par>"
                    +"<p:cTn id=\"5\" presetID=\"1\" presetClass=\"mediacall\" presetSubtype=\"0\" fill=\"hold\" nodeType=\"afterEffect\">"
                            +"<p:stCondLst>"
                    +"<p:cond delay=\"0\"/>"
                            +"</p:stCondLst>"
                            +"<p:childTnLst>"
                    +"<p:cmd type=\"call\" cmd=\"playFrom(0.0)\">"
                            +"<p:cBhvr>"
                    +"<p:cTn id=\"6\" dur=\"4169\" fill=\"hold\"/>"
                    +"<p:tgtEl>"
                            +"<p:spTgt spid=\""+String.valueOf(id)+"\"/>"
                    +"</p:tgtEl>"
                            +"</p:cBhvr>"
                    +"</p:cmd>"
                            +"</p:childTnLst>"
                    +"</p:cTn>"
                            +"</p:par>"
                    +"</p:childTnLst>"
                            +"</p:cTn>"
                    +"</p:par>"
                            +"</p:childTnLst>"
                    +"</p:cTn>"
                            +"</p:par>"
                    +"</p:childTnLst>"
                            +"</p:cTn>"
                            +"<p:prevCondLst>"
                    +"<p:cond evt=\"onPrev\" delay=\"0\">"
                            +"<p:tgtEl>"
                    +"<p:sldTgt/>"
                            +"</p:tgtEl>"
                    +"</p:cond>"
                            +"</p:prevCondLst>"
                            +"<p:nextCondLst>"
                    +"<p:cond evt=\"onNext\" delay=\"0\">"
                            +"<p:tgtEl>"
                    +"<p:sldTgt/>"
                            +"</p:tgtEl>"
                    +"</p:cond>"
                            +"</p:nextCondLst>"
                    +"</p:seq>"
                    +"<p:audio>"
                            +"<p:cMediaNode vol=\"100000\">"
                    +"<p:cTn id=\"7\" fill=\"hold\" display=\"0\">"
                            +"<p:stCondLst>"
                    +"<p:cond delay=\"indefinite\"/>"
                            +"</p:stCondLst>"
                            +"<p:endCondLst>"
                    +"<p:cond evt=\"onStopAudio\" delay=\"0\">"
                            +"<p:tgtEl>"
                    +"<p:sldTgt/>"
                            +"</p:tgtEl>"
                    +"</p:cond>"
                            +"</p:endCondLst>"
                    +"</p:cTn>"
                    +"<p:tgtEl>"
                            +"<p:spTgt spid=\""+String.valueOf(id)+"\"/>"
                    +"</p:tgtEl>"
                            +"</p:cMediaNode>"
                    +"</p:audio>"
                            +"</p:childTnLst>"
                    +"</p:cTn>"
                            +"</p:par>"
                    +"</p:tnLst>"
                   +"</p:timing>";
         return ret_value;
     }
     
     private String AutoSlideShow(){
         String ret_value="<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">"
		+"<mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" Requires=\"p14\">"
			+"<p:transition p14:dur=\"100\" advClick=\"0\" advTm=\"10000\">"
				+"<p:cut/>"
			+"</p:transition>"
		+"</mc:Choice>"
		+"<mc:Fallback xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"\">"
			+"<p:transition advClick=\"0\" advTm=\"10000\">"
				+"<p:cut/>"
			+"</p:transition>"
		+"</mc:Fallback>"
		+"</mc:AlternateContent>";
         return ret_value;
     }

	@Override
	public FinalResult getFinalResult() {
		return finalResult;
	}

	@Override
	public void setFinalResult(FinalResult finalresult) {
		finalResult=finalresult;
	}

	private void RenamePPTXtoZip(){
        File oldFile=new File(this.finalResult.getFilename());
        File delFile=new File(this.finalResult.getFilename()+".zip");
        if(delFile.exists()) delFile.delete();
        oldFile.renameTo(new File(this.finalResult.getFilename()+".zip"));       
    }
    
    private void RenameZiptoPPTX(){
    	try{
	        File oldFile=new File(this.finalResult.getFilename()+".zip");
	        File delFile=new File(this.finalResult.getFilename());
	        if(delFile.exists()) delFile.delete();	     
	        oldFile.renameTo(new File(this.finalResult.getFilename()));
    	}
    	catch(Exception ex){
    		System.out.println(ex.getMessage());
    	}
    }
    
    private void AddAudiotoPPTX(int slideId,String AudioFilename,String NotesTxt){  
        try {
        	byte[] noteRelsFrom=new byte[getClass().getClassLoader().getResourceAsStream("resources/notesSlide.xml.rels").available()];
            byte[] noteFrom=new byte[getClass().getClassLoader().getResourceAsStream("resources/notesSlide.xml").available()];
            getClass().getClassLoader().getResourceAsStream("resources/notesSlide.xml.rels").read(noteRelsFrom);
            getClass().getClassLoader().getResourceAsStream("resources/notesSlide.xml").read(noteFrom);         
            /*Copy audio and image to ppt/media folder*/
        	if(AudioFilename!=null && !NotesTxt.equals("")){
        		
	        	File folder_media = new File("ppt/unzip/ppt/media");
	            if(!folder_media.exists()){
	                        folder_media.mkdir();
	            }
	             
	            byte[] pngFrom=new byte[getClass().getClassLoader().getResourceAsStream("resources/play.png").available()]; 
	            getClass().getClassLoader().getResourceAsStream("resources/play.png").read(pngFrom);
	            File pngTo1=new File("ppt/unzip/ppt/media/play.png");
	            pngTo1.createNewFile();
	            FileOutputStream pngTo=new FileOutputStream("ppt/unzip/ppt/media/play.png");
	            pngTo.write(pngFrom);
	            pngTo.close();
	           
	            File wavFrom=new File(AudioFilename +".wav");
	            File wavTo=new File("ppt/unzip/ppt/media/"+AudioFilename.replace("audio/", "") +".wav");
	
	            
	            /*Start Copy*/
	            Files.copy(wavFrom.toPath(), wavTo.toPath(),StandardCopyOption.REPLACE_EXISTING );
	            wavFrom.deleteOnExit();
	            /*End of copy*/
	                
        	}
        	
            /*Write Notes */
            String relsNotesFilename="ppt/unzip/ppt/notesSlides/_rels/notesSlide"+String.valueOf(slideId) +".xml.rels";
            String NotesFilename="ppt/unzip/ppt/notesSlides/notesSlide"+String.valueOf(slideId) +".xml";
            (new File(relsNotesFilename)).createNewFile();
            (new File(NotesFilename)).createNewFile();
            FileOutputStream noteRelsTo=new FileOutputStream(relsNotesFilename);
            FileOutputStream noteTo=new FileOutputStream(NotesFilename);
            noteRelsTo.write(noteRelsFrom);
            noteRelsTo.close();
            noteTo.write(noteFrom);
            noteTo.close();
            
            this.Replace_papaki(relsNotesFilename,String.valueOf(slideId));
            this.Replace_papaki(NotesFilename, NotesTxt);
            ContenttypeNotesDef+=this.AppendContentTypeNotes(slideId);
            /*End of Write the Notes*/
            
            /* Write to Slide the audio */
            FileOutputStream slide=new FileOutputStream("ppt/unzip/ppt/slides/slide"+String.valueOf(slideId)+".xml");
            try {
            	slide.write(SlideXml[slideId-2].getBytes());
                this.AppendContentType(slideId);
                slide.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }        
            
        } catch (IOException ex) {
        	System.out.println(ex.getMessage());
        }   
        
    }
       
    private void InitializeContentType(){
        this.Contenttype="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
        +"<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">"
	+"<Default Extension=\"png\" ContentType=\"image/png\"/>"
	+"<Default Extension=\"jpeg\" ContentType=\"image/jpeg\"/>"
	+"<Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/>"
	+"<Default Extension=\"xml\" ContentType=\"application/xml\"/>"
	+"<Default Extension=\"wav\" ContentType=\"audio/wav\"/>"
	+"<Override PartName=\"/ppt/presentation.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.presentation.main+xml\"/>"
	+"<Override PartName=\"/ppt/slideMasters/slideMaster1.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideMaster+xml\"/>";
    }
    
    private void AppendContentType(int slidenumber){
        this.Contenttype=this.Contenttype.concat("<Override PartName=\"/ppt/slides/slide"+String.valueOf(slidenumber)+".xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slide+xml\"/>");
    }
    
    private String AppendContentTypeNotes(int slidenumber){
        return "<Override PartName=\"/ppt/notesSlides/notesSlide"+String.valueOf(slidenumber)+".xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.notesSlide+xml\"/>";
    }
            
    private void writeContentType(){
        this.Contenttype=this.Contenttype 
        		+"<Override PartName=\"/ppt/notesMasters/notesMaster1.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.notesMaster+xml\"/>"
                + "<Override PartName=\"/ppt/presProps.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.presProps+xml\"/>"
				+"<Override PartName=\"/ppt/viewProps.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.viewProps+xml\"/>"
				+"<Override PartName=\"/ppt/theme/theme1.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.theme+xml\"/>"
				+"<Override PartName=\"/ppt/tableStyles.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.tableStyles+xml\"/>"
				+"<Override PartName=\"/ppt/slideLayouts/slideLayout1.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml\"/>"
				+"<Override PartName=\"/ppt/slideLayouts/slideLayout2.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml\"/>"
				+"<Override PartName=\"/ppt/slideLayouts/slideLayout3.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml\"/>"
				+"<Override PartName=\"/ppt/slideLayouts/slideLayout4.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml\"/>"
				+"<Override PartName=\"/ppt/slideLayouts/slideLayout5.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml\"/>"
				+"<Override PartName=\"/ppt/slideLayouts/slideLayout6.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml\"/>"
				+"<Override PartName=\"/ppt/slideLayouts/slideLayout7.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml\"/>"
				+"<Override PartName=\"/ppt/slideLayouts/slideLayout8.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml\"/>"
				+"<Override PartName=\"/ppt/slideLayouts/slideLayout9.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml\"/>"
				+"<Override PartName=\"/ppt/slideLayouts/slideLayout10.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml\"/>"
				+"<Override PartName=\"/ppt/slideLayouts/slideLayout11.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml\"/>"
		        +"<Override PartName=\"/ppt/theme/theme2.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.theme+xml\"/>"
		        + this.ContenttypeNotesDef
		        +"<Override PartName=\"/docProps/core.xml\" ContentType=\"application/vnd.openxmlformats-package.core-properties+xml\"/>"
		        +"<Override PartName=\"/docProps/app.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.extended-properties+xml\"/>"
		        +"</Types>";
        try {
            FileOutputStream ConType=new FileOutputStream("ppt/unzip/[Content_Types].xml");
            ConType.write(this.Contenttype.getBytes());
            ConType.close();
        } catch (IOException ex) {
        	System.out.println(ex.getMessage());
        }
    }
    
    private void UnZipFiles(){
        
        byte[] buffer = new byte[1024];
        try{
        //create output directory is not exists
        	File folder = new File("ppt/unzip");
            if(folder.exists()) DeleteFolder(folder);
            folder.mkdir();
        	ZipInputStream zis = new ZipInputStream(new FileInputStream(this.finalResult.getFilename()+".zip"));
            ZipEntry ze = zis.getNextEntry();

            while(ze!=null){

                String fileName = ze.getName();
                File newFile = new File("ppt/unzip" + File.separator + fileName);

                //System.out.println("file unzip : "+ newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                //buffer=new byte[1024];
                while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                }
                fos.close();
                ze=zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        }catch(IOException ex){
            System.out.println(ex.getMessage()); 
        }
    }
    
    private void ZipFiles(){
        
        byte[] buffer = new byte[2048];
        File delFile=new File(this.finalResult.getFilename()+".zip");
        if(delFile.exists()) delFile.delete();
        try{
 
            FileOutputStream fos = new FileOutputStream(this.finalResult.getFilename()+".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

           // System.out.println("Output to Zip : " + this.finalResult.getFilename()+".zip");

            for(String file : this.fileList){
        		if(file.equals("slide1")==false && file.equals("Slide1")==false){
        			//System.out.println("File Added : " + file);
                    ZipEntry ze= new ZipEntry(file.replace("ppt\\unzip\\", "").replace("ppt/unzip/", ""));
                    zos.putNextEntry(ze);
                    FileInputStream in = new FileInputStream(file);

                    int len;
                    while ((len = in.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                    }
                    in.close();
                }
            }

            zos.closeEntry();
            //remember close it
            zos.close();
            fos.close();
        }catch(IOException ex){
            System.out.println(ex.getMessage());  
        }
    }
    
    /**
     * Traverse a directory and get all files,
     * and add the file into fileList  
     * @param node file or directory
     */
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
    
    private void Replace_papaki(String nameFile,String toReplace){
        try
             {
             File file = new File(nameFile);
             BufferedReader reader = new BufferedReader(new FileReader(file));
             String line = "", oldtext = "";
             while((line = reader.readLine()) != null)
                 {
                 oldtext += line + "\r\n";
             }
             reader.close();
             
             //To replace a line in a file
             String newtext = oldtext.replaceAll( "@",toReplace);
            
             FileWriter writer = new FileWriter(nameFile);
             writer.write(newtext);
             writer.close();
         }
         catch (IOException ex)
         {
             System.out.println(ex.getMessage());
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
