package dbtopptx;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.poi.hslf.model.SlideMaster;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.docx4j.openpackaging.packages.PresentationMLPackage;

/**
 *
 * @author Asterix
 */
public class DBtoPPTX {

    
    private Connection connect = null;
    private String dburl=null;
    private String dbClass=null;
    private String FolderName=null;
    private static List<String> fileList;
    //the above must be in list
    private SlideElement first; 
    private static SlideElement []slides; 
    private String Contenttype;
    private String ContenttypeNotesDef;
    /*private SlideElement second;
    private SlideElement third;*/
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBtoPPTX dbtopptx=new DBtoPPTX();
        dbtopptx.ContenttypeNotesDef="";
        fileList = new ArrayList<>();
        slides=new SlideElement[3];
        dbtopptx.CreateConnectionWithDB();
        dbtopptx.CreateSlideShow();
        dbtopptx.RenamePPTXtoZip();
        dbtopptx.UnZipFiles();
        dbtopptx.InitializeContentType();
        dbtopptx.AddAudiotoPPTX();
        dbtopptx.writeContentType();
        dbtopptx.GenerateFileList(new File("ppt/unzip"));
        dbtopptx.ZipFiles();
        dbtopptx.RenameZiptoPPTX();
    }
    
    private void CreateConnectionWithDB(){
        dburl="jdbc:mysql://localhost/adult_no_dublic";
        dbClass="com.mysql.jdbc.Driver";
        try{
            Class.forName(dbClass);
            connect=DriverManager.getConnection(dburl,"root","");
        } catch (SQLException | ClassNotFoundException ex) {
           Logger.getLogger(DBtoPPTX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void CreateSlideShow(){
        
        FolderName="audio";
        //Create folder audio
        if((new File("audio")).exists()==false) {boolean folder=(new File("audio")).mkdir();}
        if((new File("ppt")).exists()==false) {boolean folder=(new File("ppt")).mkdir();}
        if((new File("ppt/notes.pptx")).exists()==false) {
            try {
                boolean folder=(new File("ppt/notes2.pptx")).createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DBtoPPTX.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        XMLSlideShow slideShowPPTX=null;
        PresentationMLPackage pMLPackage = null;
        try {
            
            InputStream file = getClass().getClassLoader().getResourceAsStream("helpfiles/notes.pptx");
            slideShowPPTX = new XMLSlideShow(file);
//            pMLPackage=(PresentationMLPackage)OpcPackage.load(new File("ppt/notes2.pptx"));
            
        } catch ( IOException ex) {
            Logger.getLogger(DBtoPPTX.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        XSLFSlideMaster defaultMaster1 = slideShowPPTX.getSlideMasters()[0];
        
        SlideShow slideShowPPT = new SlideShow();
        SlideMaster defaultMaster = slideShowPPT.getSlidesMasters()[0];       
        
        
        slides[0]=new SlideElement("SELECT A.occupation, A.work_class, avg(hours_per_week) as AVG "
                + "FROM ADULT A, OCCUPATION O, WORK_CLASS W "
                + "WHERE A.occupation = O.level0 AND O.level1 = 'Blue-collar' AND W.level0 = A.work_class AND W.level2 = 'With-Pay' "
                + "GROUP BY  A.occupation, A.work_class;");
        slides[0].create_Result(connect);
        slides[0].createTextSound("The biggest value in the column @ is",FolderName);
        slides[0].CreatePivotTable();
        slides[0].XSLFcreateSlide(slideShowPPTX,defaultMaster1);
        slides[0].HSLFcreateSlide(slideShowPPT);
        
        slides[1]=new SlideElement("SELECT A.occupation, A.work_class, AVG( hours_per_week ) as AVG"
        + "FROM ADULT A, OCCUPATION O, WORK_CLASS W"
        + "WHERE A.occupation = O.level0AND O.level1 = 'Blue-collar'AND W.level0 = A.work_classAND W.level2 = 'without-Pay'"
        + "GROUP BY A.occupation, A.work_class");
        slides[1].create_Result(connect);
        slides[1].createTextSound("The biggest value in the column @ is",FolderName);
        slides[1].CreatePivotTable();
        slides[1].XSLFcreateSlide(slideShowPPTX,defaultMaster1);
        slides[1].HSLFcreateSlide(slideShowPPT);
        
        
        slides[2]=new SlideElement("SELECT A.occupation, A.work_class, AVG( hours_per_week ) as AVG"
        + "FROM ADULT A, OCCUPATION O, WORK_CLASS W"
        + "WHERE A.occupation = O.level0AND (O.level1 = 'white-collar' OR O.level1 = 'other')AND W.level0 = A.work_classAND W.level2 = 'With-Pay'"
        + "GROUP BY A.occupation, A.work_class");
        slides[2].create_Result(connect);
        slides[2].createTextSound("The biggest value in the column @ is",FolderName);
        slides[2].CreatePivotTable();
        slides[2].XSLFcreateSlide(slideShowPPTX,defaultMaster1);
        slides[2].HSLFcreateSlide(slideShowPPT);
        
        slideShowPPTX.removeSlide(0);
        FileOutputStream fout;
        FileOutputStream fout1 ;
        try  {
            fout=new FileOutputStream("ppt/slideshow.ppt");
            fout1=new FileOutputStream("ppt/slideshow.pptx");
            slideShowPPT.write(fout);
            fout.close();
            slideShowPPTX.write(fout1);
            fout1.close();
            
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }  
    }
    
    private void RenamePPTXtoZip(){
        File oldFile=new File("ppt/slideshow.pptx");
        File delFile=new File("ppt/slideshow.zip");
        if(delFile.exists()) delFile.delete();
        boolean renameTo = oldFile.renameTo(new File("ppt/slideshow.zip"));
    }
    
    private void RenameZiptoPPTX(){
        File oldFile=new File("ppt/slideshow.zip");
        File delFile=new File("ppt/slideshow.pptx");
        if(delFile.exists()) delFile.delete();
        boolean renameTo = oldFile.renameTo(new File("ppt/slideshow.pptx"));
    }
    
    
    
    private void AddAudiotoPPTX(){ 
                        
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
            for(int i=0;i<this.slides.length;i++){
                File wavFrom=new File(slides[i].getSoundFilePath() +".wav");
                File wavTo=new File("ppt/unzip/ppt/media/"+slides[i].getSoundFilePath().replace("audio/", "") +".wav");

                String relsNotesFilename="ppt/unzip/ppt/notesSlides/_rels/notesSlide"+String.valueOf(i+2) +".xml.rels";
                String NotesFilename="ppt/unzip/ppt/notesSlides/notesSlide"+String.valueOf(i+2) +".xml";
                if((new File(relsNotesFilename)).createNewFile()) System.out.println("Goal");
                (new File(NotesFilename)).createNewFile();
                FileOutputStream noteRelsTo=new FileOutputStream(relsNotesFilename);
                FileOutputStream noteTo=new FileOutputStream(NotesFilename);
                /*Start Copy*/
                Files.copy(wavFrom.toPath(), wavTo.toPath(),StandardCopyOption.REPLACE_EXISTING );
                
                noteRelsTo.write(noteRelsFrom);noteRelsTo.close();
                noteTo.write(noteFrom);noteTo.close();
                /*End of copy*/
                
                /*Write Notes */
                this.Replace_papaki(relsNotesFilename,String.valueOf(i+2));
                this.Replace_papaki(NotesFilename, slides[i].getMessage());
                this.ContenttypeNotesDef+=this.AppendContentTypeNotes(i+2);
                /*End of Write the Notes*/
                
                /* Write to Slide the audio */
                FileOutputStream slide1=new FileOutputStream("ppt/unzip/ppt/slides/slide"+String.valueOf(i+2)+".xml");
                try {
                    slide1.write(slides[i].getSlideXML().getBytes());
                    this.AppendContentType(i+2);
                    slide1.close();
                } catch (IOException ex) {
                    Logger.getLogger(DBtoPPTX.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            

            try (ZipInputStream zis = new ZipInputStream(new FileInputStream("ppt/slideshow.zip"))) {
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
        File delFile=new File("ppt/slideshow.zip");
        if(delFile.exists()) delFile.delete();
        try{
 
            FileOutputStream fos = new FileOutputStream("ppt/slideshow.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + "ppt/slideshow.zip");

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
