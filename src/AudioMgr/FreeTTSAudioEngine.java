/**
 * 
 */
package AudioMgr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.poi.hwpf.model.BytePropertyNode;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.util.data.audio.AudioPlayer;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;
/**
 * @author Asterix
 *
 */
public class FreeTTSAudioEngine extends AudioEngine {

    private String voiceName;
    private VoiceManager voiceManager;
    private Voice voice;
    private SingleFileAudioPlayer sfap;
    
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    final java.util.Random rand = new java.util.Random();
    
	/**
	 * 
	 */
	public FreeTTSAudioEngine() {
		super();
	}

	/* (non-Javadoc)
	 * @see AudioMgr.AudioEngine#InitializeVoiceEngine()
	 */
	@Override
	public void InitializeVoiceEngine() {
        voiceName = "kevin16"; // the only usable general purpose voice
        
       //System.setProperty("mbrola.base", "D:/workspace/Master/libs/mbrola/Voices2/") ;
        //listAllVoices();
        voiceManager= VoiceManager.getInstance();
        voice = voiceManager.getVoice(voiceName);
	}
	
	public static void listAllVoices() {  
        System.out.println();  
        System.out.println("All voices available:");  
        VoiceManager voiceManager = VoiceManager.getInstance();  
        Voice[] voices = voiceManager.getVoices();  
        for (int i = 0; i < voices.length; i++) {  
            System.out.println("    " + voices[i].getName()  
                               + " (" + voices[i].getDomain() + " domain)");  
        }  
    }  
	
	/* (non-Javadoc)
	 * @see AudioMgr.AudioEngine#CreateSound(java.lang.String, java.lang.String)
	 */
	@Override
	public void CreateSound(String textTobeSound, String FileNameOfSound) {
		try
        {
           System.setProperty("com.sun.speech.freetts.voice.defaultAudioPlayer", "com.sun.speech.freetts.audio.SingleFileAudioPlayer");
           //this.AudioFileName=AudioFN;
           voice.allocate();
           sfap=new SingleFileAudioPlayer(FileNameOfSound,javax.sound.sampled.AudioFileFormat.Type.WAVE);
           voice.setAudioPlayer(sfap);
           voice.speak(textTobeSound);
           voice.deallocate();
           sfap.close();
			/*MaryInterface marytts = new LocalMaryInterface();
			Set<String> voices = marytts.getAvailableVoices();
			marytts.setVoice(voices.iterator().next());
			AudioInputStream audio = marytts.generateAudio("Hello Mister are Sunday!");
			AudioInputStream pcm = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, audio);
			AudioInputStream ulaw = AudioSystem.getAudioInputStream(AudioFormat.Encoding.ULAW, pcm);
			File tempFile = new File("tmp.wav");
			AudioSystem.write(ulaw, AudioFileFormat.Type.WAVE, tempFile);
			/*AudioPlayer player = new AudioPlayer(audio);
			player.start();
			player.join();*/
			
			System.exit(0);
           
        }
        catch(Exception e)
        {   
            System.out.println(e.getMessage());
        }
	}

	/**
	 * @return the voiceName
	 */
	public String getVoiceName() {
		return voiceName;
	}

	/**
	 * @param voiceName the voiceName to set
	 */
	public void setVoiceName(String voiceName) {
		this.voiceName = voiceName;
	}

	/**
	 * @return the voiceManager
	 */
	public VoiceManager getVoiceManager() {
		return voiceManager;
	}

	/**
	 * @param voiceManager the voiceManager to set
	 */
	public void setVoiceManager(VoiceManager voiceManager) {
		this.voiceManager = voiceManager;
	}

	/**
	 * @return the voice
	 */
	public Voice getVoice() {
		return voice;
	}

	/**
	 * @param voice the voice to set
	 */
	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	/**
	 * @return the sfap
	 */
	public SingleFileAudioPlayer getSfap() {
		return sfap;
	}

	/**
	 * @param sfap the sfap to set
	 */
	public void setSfap(SingleFileAudioPlayer sfap) {
		this.sfap = sfap;
	}
	
	public String randomIdentifier() {
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
	

}
