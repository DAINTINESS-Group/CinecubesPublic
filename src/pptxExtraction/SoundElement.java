/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pptxExtraction;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
/**
 *
 * @author Asterix
 */
public class SoundElement {
    
    private String msg;
    private String voiceName;
    private VoiceManager voiceManager;
    private Voice voice;
    private SingleFileAudioPlayer sfap;
    private String AudioFileName;
    
    SoundElement(){
        msg="Nothing to spoke";
        voiceName = "kevin16"; // the only usable general purpose voice
        
        byte b[]=msg.getBytes();
        voiceManager= VoiceManager.getInstance();
        voice = voiceManager.getVoice(voiceName);
        
        //System.setProperty("com.sun.speech.freetts.voice.defaultAudioPlayer", "com.sun.speech.freetts.audio.SingleFileAudioPlayer");
        //this.CreateAudio("demo");
    }
    
    public void SetMessage(String message){
        msg=message;
    }
    
    public void CreateAudio(String AudioFN){
        try
        {
           System.setProperty("com.sun.speech.freetts.voice.defaultAudioPlayer", "com.sun.speech.freetts.audio.SingleFileAudioPlayer");
           this.AudioFileName=AudioFN;
           voice.allocate();
           sfap=new SingleFileAudioPlayer(AudioFileName,javax.sound.sampled.AudioFileFormat.Type.WAVE);
           voice.setAudioPlayer(sfap);
           voice.speak(msg);
           voice.deallocate();
           sfap.close();
           
        }
        catch(Exception e)
        {   
            System.out.println(e.getMessage());
        }
    }
    
    public String getSoundFile(){
        return AudioFileName;
    }
    
    
}
