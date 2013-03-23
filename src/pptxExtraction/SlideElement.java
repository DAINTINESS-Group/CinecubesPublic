package pptxExtraction;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hslf.model.*;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.xslf.usermodel.*;
import org.apache.xmlbeans.XmlObject;
/**
 *
 * @author Asterix
 */
public class SlideElement {
    
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    final java.util.Random rand = new java.util.Random();
    
    private String Query=null;
    private String[][] result=null;
    private String[][] PivotTable=null; 
    private String text=null;
    private String SlideXml=null;
    private SoundElement sound;
    private float maxValue=0;
    private HashSet<String> ColPivot=null;
    private HashSet<String> RowPivot=null;
    
    SlideElement(){
        //do nothing
    }
    
    SlideElement(String Q){
        Query=Q;
    }
    
    public void create_Result(Connection con,String Q){
        
        Query=Q;
        create_Result(con);
    }
    
    public void create_Result(Connection con){
        int columns;
        int rows;
        try {
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(Query);
            columns=rs.getMetaData().getColumnCount();
            this.ColPivot=new HashSet<>();
            this.RowPivot=new HashSet<>();
            //find how match rows 
            rs.last();
            rows=rs.getRow();
            
            //back to first line
            rs.first();
            rs.beforeFirst();
            result=new String[rows+2][columns];
            for(int i=1;i<=columns;i++) {
                result[0][i-1]=rs.getMetaData().getColumnName(i);
                result[1][i-1]=rs.getMetaData().getColumnLabel(i);
            }
            while(rs.next()){
               for(int i=0;i<columns;i++){
                   result[rs.getRow()+1][i]=rs.getString(i+1);
                   this.ColPivot.add(rs.getString(2));
                   this.RowPivot.add(rs.getString(1));
               }
               
               if(maxValue<rs.getFloat(columns)){
                   maxValue=rs.getFloat(columns);
               }
            }
            
        } catch (Exception ex) {
            //Logger.getLogger(sqlQueriesRes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
    }
    
    public void printResult(){
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result[i].length;j++) System.out.print(this.result[i][j]+"|");
            System.out.println();
        }
    }
    
    public void createTextSound(String txt,String folderName){
        this.text=txt.replace("@", this.result[0][result[0].length-1])+" "+String.valueOf(maxValue);
        
        sound=new SoundElement();
        sound.SetMessage(this.text);
        sound.CreateAudio(folderName+"/"+randomIdentifier());
    }
    
    private String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while(builder.toString().length() == 0) {
            int length = rand.nextInt(5)+5;
            for(int i = 0; i < length; i++)
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            if((new File("audio/"+builder.toString()+".wav")).exists()) 
                builder = new StringBuilder();
        }
        return builder.toString();
    }
    
    public String getSlideXML(){
        return this.SlideXml;
    }
    public String getMessage(){
        return text;
    }
    
    public String[][] getTable(){
        return this.result;
    }
    
    public String getSoundFilePath(){
        return sound.getSoundFile() ;
    }
    
    public int getNumRowsPivot(){
        return this.RowPivot.size()+1;
    }
    
    public int getNumColumsPivot(){
        return this.ColPivot.size()+1;
    }
    
    public void CreatePivotTable(){
        this.PivotTable=new String[RowPivot.size()+1][ColPivot.size()+1];
        this.PivotTable[0][0]="";
        int i=0;
        int j=0;
        for(String x : RowPivot) {
            this.PivotTable[i+1][0]=x;
            i++;
        }
        
        for(String y:ColPivot){
            this.PivotTable[0][j+1]=y;
            j++;
        }
        i=1;
        for(String x:RowPivot){
            j=1;
            for(String y:ColPivot){
                for (int r=0;r<result.length;r++){
                        if(this.result[r][0].equals(x) && this.result[r][1].equals(y)){
                            this.PivotTable[i][j]=this.result[r][2];
                        }
                }
                j++;
            }
            i++;
        }
        
    }
    
    public String getCellOfPivot(int i,int j) {
        return (this.PivotTable[i][j]!=null ? this.PivotTable[i][j] : "");
    }
    
    public void HSLFcreateSlide(SlideShow slideShow){
       Slide slide = slideShow.createSlide();
       
       java.awt.Dimension pgsize = slideShow.getPageSize();
       int pgx = pgsize.width; //slide width
       //int pgy = pgsize.height; //slide height
       
       slide.setFollowMasterScheme(false);
       TextBox title=slide.addTitle();
       title.setText(getMessage());
       
       RichTextRun rt1=title.getTextRun().getRichTextRuns()[0];
       rt1.setFontName("Arial");
       rt1.setFontSize(20);
       
       Table tbl=new Table(getNumRowsPivot(),getNumColumsPivot());
       for(int i=0;i<this.getNumRowsPivot();i++){
           for(int j=0;j<this.getNumColumsPivot();j++){
              TableCell cell = tbl.getCell(i, j);
              
              cell.setText(getCellOfPivot(i, j));

              RichTextRun rt = cell.getTextRun().getRichTextRuns()[0];
              rt.setFontName("Arial");
              rt.setFontSize(10);
              cell.setVerticalAlignment(TextBox.AnchorMiddle);
              cell.setHorizontalAlignment(TextBox.AlignCenter);
              if(i==0) tbl.setColumnWidth(j, 70);
           }
       }
       Line border = tbl.createBorder();
       border.setLineColor(Color.black);
       border.setLineWidth(1.0);
       tbl.setAllBorders(border);
       
       int tst=this.getNumColumsPivot()*70;
       slide.addShape(tbl);
       tbl.moveTo((pgx-tst)/2, 100);
       File soundPath=new File(sound.getSoundFile()+".wav");
       byte[] data=new byte[1024];
       
        try {
            getClass().getClassLoader().getResourceAsStream("helpfiles/play.png").read(data);
        } catch (IOException ex) {
            Logger.getLogger(SlideElement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int movieIdx = slideShow.addMovie(soundPath.getAbsolutePath(), MovieShape.MOVIE_AVI);
        int thumbnailIdx = 0;
        try {
            thumbnailIdx = slideShow.addPicture(data, Picture.PNG);
        } catch (IOException ex) {
            Logger.getLogger(SlideElement.class.getName()).log(Level.SEVERE, null, ex);
        }
        MovieShape  sn=new MovieShape (movieIdx,thumbnailIdx);
        sn.setAutoPlay(true);
        slide.addShape(sn);
    }
    
     public void XSLFcreateSlide(XMLSlideShow slideShow,XSLFSlideMaster defaultMaster){
 
        XSLFSlide slide=slideShow.createSlide();
        XSLFSlide [] slides = slideShow.getSlides();
        String NotesRelationShip="http://schemas.openxmlformats.org/officeDocument/2006/relationships/notesSlide";
        URI uri = null;
        try {
            uri = new URI("../notesSlides/notesSlide"+String.valueOf(slides.length)+".xml");
        } catch (URISyntaxException ex) {
            Logger.getLogger(SlideElement.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        PackageRelationship addRelationship= slide.getPackagePart().addRelationship(uri, TargetMode.INTERNAL, NotesRelationShip);
        slide.addRelation(addRelationship.getId(), slide); 
        this.CreateTableInSlide(slide, slideShow.getPageSize());
        this.CreateSlideWithXMlAudio(slide);
     }
     
     
    /*  rId1 -->MEDIA relationship ID
     *  rId2 -->AUDIO relationship ID
     *  rId4 -->IMAGE relationship ID
     *
     */
     private void CreateTableInSlide(XSLFSlide slide,java.awt.Dimension pgsize){
         
       int pgx = pgsize.width; //slide width
       //int pgy = pgsize.height; //slide height
                  
       
       XSLFTable tbl=slide.createTable();
       for(int i=0;i<this.getNumRowsPivot();i++){
            XSLFTableRow addRow = tbl.addRow();
           for(int j=0;j<this.getNumColumsPivot();j++){
              XSLFTableCell cell = addRow.addCell();
              
              XSLFTextParagraph p = cell.addNewTextParagraph();
              XSLFTextRun r = p.addNewTextRun();
              r.setFontFamily("Arial");
              r.setFontSize(12);
              
              r.setText(this.getCellOfPivot(i, j));
              if(i % 2 == 0)
                    cell.setFillColor(new Color(208, 216, 232));
              else
                    cell.setFillColor(new Color(233, 247, 244));
              cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
              
              if(i==0) tbl.setColumnWidth(j, 80);
           }
       }
       int tst=this.getNumColumsPivot()*80;
       tbl.setAnchor(new Rectangle2D.Double(((pgx-tst)/2), 100,100,100));
     }
     
     private void CreateSlideWithXMlAudio(XSLFSlide slide){
       String AudioRelationShip="http://schemas.openxmlformats.org/officeDocument/2006/relationships/audio";
       String MediaRelationShip="http://schemas.microsoft.com/office/2007/relationships/media" ;
       String ImageRelationShip="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image";
       PackagePart packagePart = slide.getPackagePart();
       try {
            URI uri = null;
            URI uri2=null;
            try {
                uri = new URI("../media/"+sound.getSoundFile().replace("audio/", "") +".wav");
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
            
            String SoundNode = this.SoundNodeString(addRelationship2.getId(), addRelationship1.getId(), addRelationship3.getId());
            XmlObject xmlObject = slide.getXmlObject();
            
            String test="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+xmlObject.toString().replace("main1","a").replace("<main", "<p").replace("</main", "</p").replace(":main=",":p=").replace("rel=","r=").replace("rel:","r:").replace("xml-fragment", "p:sld").replace("</p:spTree>",SoundNode+"</p:spTree>");
            this.SlideXml=test.replace("</p:sld>", AutoSlideShow()+TimingNode()+"</p:sld>").replace("<p:sld ", "<p:sld xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" ");
            
            
        } catch (Exception ex) {
            Logger.getLogger(SlideElement.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     private String SoundNodeString(String rId1,String rId2,String rId4){
         String ret_value= "<p:pic>"
                 + "<p:nvPicPr>"
                 + "<p:cNvPr id=\"4\" name=\""+sound.getSoundFile().replace("audio/", "").concat(".wav") +"\">"
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
}
