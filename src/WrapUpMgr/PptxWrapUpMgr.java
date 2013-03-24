/**
 * 
 */
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.xslf.usermodel.VerticalAlignment;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.apache.poi.xslf.usermodel.XSLFTableRow;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.xmlbeans.XmlObject;

import pptxExtraction.DBtoPPTX;
import pptxExtraction.SlideElement;
import StoryMgr.Act;
import StoryMgr.FinalResult;
import StoryMgr.Story;
import StoryMgr.pptxSlide;

/**
 * @author Asterix
 *
 */
public class PptxWrapUpMgr extends WrapUpMgr {

	XMLSlideShow slideShowPPTX;
	XSLFSlideMaster defaultMaster;
	private String[] SlideXml;
	private ArrayList<String> fileList;
	private String Contenttype;
	private String ContenttypeNotesDef;
	/**
	 * 
	 */
	public PptxWrapUpMgr() {
		fileList=new ArrayList<String>();
		ContenttypeNotesDef="";
	}
	
	@Override	
	public void doWrapUp(Story story){
        InputStream file = getClass().getClassLoader().getResourceAsStream("helpfiles/notes.pptx");
        try {
			slideShowPPTX = new XMLSlideShow(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		defaultMaster = slideShowPPTX.getSlideMasters()[0];
		
		for(Act actItem : story.getActs()){
			SlideXml=new String[actItem.getEpisodes().size()];
			for(int j=0;j<actItem.getEpisodes().size();j++){
				SlideXml[j]="";
				pptxSlide slide=(pptxSlide)actItem.getEpisodes().get(j);
				XSLFcreateSlide(slide.getVisual().getPivotTable(),slide.getAudio().getFileName(),j+2);
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
        
        
        RenamePPTXtoZip();
        UnZipFiles();
        InitializeContentType();
        for(Act actItem : story.getActs()){
			for(int j=0;j<actItem.getEpisodes().size();j++){
				pptxSlide slide=(pptxSlide)actItem.getEpisodes().get(j);
				AddAudiotoPPTX(j+2,slide.getAudio().getFileName(),slide.Notes);
			}
        }
        writeContentType();
        GenerateFileList(new File("ppt/unzip"));
        ZipFiles();
        RenameZiptoPPTX();
	}
	
	public void XSLFcreateSlide(String[][] table,String AudioFilename,int slideid){
		 
        XSLFSlide slide=slideShowPPTX.createSlide();
        XSLFSlide [] slides = slideShowPPTX.getSlides();
        String NotesRelationShip="http://schemas.openxmlformats.org/officeDocument/2006/relationships/notesSlide";
        URI uri = null;
        try {
            uri = new URI("../notesSlides/notesSlide"+slideid+".xml");
        } catch (URISyntaxException ex) {
            Logger.getLogger(SlideElement.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        PackageRelationship addRelationship= slide.getPackagePart().addRelationship(uri, TargetMode.INTERNAL, NotesRelationShip);
        slide.addRelation(addRelationship.getId(), slide); 
        CreateTableInSlide(slide, slideShowPPTX.getPageSize(),table);
        CreateSlideWithXMlAudio(slide,AudioFilename,slideid);
     }
     
     
    /*  rId1 -->MEDIA relationship ID
     *  rId2 -->AUDIO relationship ID
     *  rId4 -->IMAGE relationship ID
     *
     */
     private void CreateTableInSlide(XSLFSlide slide,java.awt.Dimension pgsize,String[][] table){
         
       int pgx = pgsize.width; //slide width
       XSLFTable tbl=slide.createTable();
       for(int i=0;i<table.length;i++){
            XSLFTableRow addRow = tbl.addRow();
           for(int j=0;j<table[i].length;j++){
              XSLFTableCell cell = addRow.addCell();
              
              XSLFTextParagraph p = cell.addNewTextParagraph();
              XSLFTextRun r = p.addNewTextRun();
              r.setFontFamily("Arial");
              r.setFontSize(12);
              
              r.setText(table[i][j]);
              if(i % 2 == 0)
                    cell.setFillColor(new Color(208, 216, 232));
              else
                    cell.setFillColor(new Color(233, 247, 244));
              cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
              
              if(i==0) tbl.setColumnWidth(j, 80);
           }
       }
       int tst=table[0].length*80;
       tbl.setAnchor(new Rectangle2D.Double(((pgx-tst)/2), 100,100,100));
     }
     
     private void CreateSlideWithXMlAudio(XSLFSlide slide,String AudioFilename,int slideid){
       String AudioRelationShip="http://schemas.openxmlformats.org/officeDocument/2006/relationships/audio";
       String MediaRelationShip="http://schemas.microsoft.com/office/2007/relationships/media" ;
       String ImageRelationShip="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image";
       PackagePart packagePart = slide.getPackagePart();
       try {
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
            
            String SoundNode = this.SoundNodeString(addRelationship2.getId(), addRelationship1.getId(), addRelationship3.getId(),AudioFilename);
            XmlObject xmlObject = slide.getXmlObject();
            
            String test="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+xmlObject.toString().replace("main1","a").replace("<main", "<p").replace("</main", "</p").replace(":main=",":p=").replace("rel=","r=").replace("rel:","r:").replace("xml-fragment", "p:sld").replace("</p:spTree>",SoundNode+"</p:spTree>");
            SlideXml[slideid-2]=test.replace("</p:sld>", AutoSlideShow()+TimingNode()+"</p:sld>").replace("<p:sld ", "<p:sld xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" ");
            
            
        } catch (Exception ex) {
            Logger.getLogger(SlideElement.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     private String SoundNodeString(String rId1,String rId2,String rId4,String AudioFilename){
         String ret_value= "<p:pic>"
                 + "<p:nvPicPr>"
                 + "<p:cNvPr id=\"4\" name=\""+AudioFilename.replace("audio/", "").concat(".wav") +"\">"
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
     
     private String TimingNode(){
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
                            +"<p:spTgt spid=\"4\"/>"
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
                            +"<p:spTgt spid=\"4\"/>"
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
		+"<mc:Fallback>"
			+"<p:transition advClick=\"0\" advTm=\"10000\">"
				+"<p:cut/>"
			+"</p:transition>"
		+"</mc:Fallback>"
		+"</mc:AlternateContent>";
         return ret_value;
     }

	@Override
	public FinalResult getFinalResult() {
		// TODO Auto-generated method stub
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
        File oldFile=new File(this.finalResult.getFilename()+".zip");
        File delFile=new File(this.finalResult.getFilename());
        if(delFile.exists()) delFile.delete();
        oldFile.renameTo(new File(this.finalResult.getFilename()));
    }
    
    private void AddAudiotoPPTX(int slideId,String AudioFilename,String NotesTxt){ 
        
        try {
                     
            /*Copy audio and image to ppt/media folder*/
            File folder_media = new File("ppt/unzip/ppt/media");
            if(!folder_media.exists()){
                        folder_media.mkdir();
            }
            
            byte[] noteRelsFrom=new byte[getClass().getClassLoader().getResourceAsStream("helpfiles/notesSlide.xml.rels").available()];
            byte[] noteFrom=new byte[getClass().getClassLoader().getResourceAsStream("helpfiles/notesSlide.xml").available()]; 
            byte[] pngFrom=new byte[getClass().getClassLoader().getResourceAsStream("helpfiles/play.png").available()]; 
            getClass().getClassLoader().getResourceAsStream("helpfiles/notesSlide.xml.rels").read(noteRelsFrom);
            getClass().getClassLoader().getResourceAsStream("helpfiles/notesSlide.xml").read(noteFrom);
            getClass().getClassLoader().getResourceAsStream("helpfiles/play.png").read(pngFrom);
            File pngTo1=new File("ppt/unzip/ppt/media/play.png");
            pngTo1.createNewFile();
            FileOutputStream pngTo=new FileOutputStream("ppt/unzip/ppt/media/play.png");
            pngTo.write(pngFrom);pngTo.close();
            //AppendContentType(1);
           
            File wavFrom=new File(AudioFilename +".wav");
            File wavTo=new File("ppt/unzip/ppt/media/"+AudioFilename.replace("audio/", "") +".wav");

            String relsNotesFilename="ppt/unzip/ppt/notesSlides/_rels/notesSlide"+String.valueOf(slideId) +".xml.rels";
            String NotesFilename="ppt/unzip/ppt/notesSlides/notesSlide"+String.valueOf(slideId) +".xml";
            (new File(relsNotesFilename)).createNewFile();
            (new File(NotesFilename)).createNewFile();
            FileOutputStream noteRelsTo=new FileOutputStream(relsNotesFilename);
            FileOutputStream noteTo=new FileOutputStream(NotesFilename);
            /*Start Copy*/
            Files.copy(wavFrom.toPath(), wavTo.toPath(),StandardCopyOption.REPLACE_EXISTING );
            
            noteRelsTo.write(noteRelsFrom);noteRelsTo.close();
            noteTo.write(noteFrom);noteTo.close();
            /*End of copy*/
            
            /*Write Notes */
            this.Replace_papaki(relsNotesFilename,String.valueOf(slideId));
            this.Replace_papaki(NotesFilename, NotesTxt);
            ContenttypeNotesDef+=this.AppendContentTypeNotes(slideId);
            /*End of Write the Notes*/
            
            /* Write to Slide the audio */
            FileOutputStream slide1=new FileOutputStream("ppt/unzip/ppt/slides/slide"+String.valueOf(slideId)+".xml");
            try {
                slide1.write(SlideXml[slideId-2].getBytes());
                this.AppendContentType(slideId);
                slide1.close();
            } catch (IOException ex) {
                Logger.getLogger(DBtoPPTX.class.getName()).log(Level.SEVERE, null, ex);
            }            
        } catch (IOException ex) {
            Logger.getLogger(DBtoPPTX.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
    }
    
    
    private void UnZipFiles(){
        
        byte[] buffer = new byte[1024];
        try{
        //create output directory is not exists
            File folder = new File("ppt/unzip");
            if(folder.exists()) DeleteFolder(folder);
            
            folder.mkdir();
            

            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(this.finalResult.getFilename()+".zip"))) {
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
            }
            
            //System.out.println("Done");
        }catch(IOException ex){
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
        this.Contenttype=this.Contenttype +"<Override PartName=\"/ppt/notesMasters/notesMaster1.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.presentationml.notesMaster+xml\"/>"
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
            Logger.getLogger(DBtoPPTX.class.getName()).log(Level.SEVERE, null, ex);
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

                    //System.out.println("File Added : " + file);
                    ZipEntry ze= new ZipEntry(file.replace("ppt\\unzip\\", ""));
                    zos.putNextEntry(ze);
                    FileInputStream in = new FileInputStream(file);

                    int len;
                    while ((len = in.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                    }
                    in.close();
            }

            zos.closeEntry();
            //remember close it
            zos.close();

            //System.out.println("Done");
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
             // replace a word in a file
             //String newtext = oldtext.replaceAll("drink", "Love");
            
             //To replace a line in a file
             String newtext = oldtext.replaceAll( "@",toReplace);
            
             FileWriter writer = new FileWriter(nameFile);
             writer.write(newtext);writer.close();
         }
         catch (IOException ex)
         {
             System.out.println(ex.getMessage());
         }
    }

}
