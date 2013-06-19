package AudioMgr;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

public class FreeTTSAudioEngine extends AudioEngine  {

    private String voiceName;
    private VoiceManager voiceManager;
    private Voice voice;
    private SingleFileAudioPlayer sfap;
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    final java.util.Random rand = new java.util.Random();
    MaryInterface marytts;
    
    public FreeTTSAudioEngine() {
		super();
	}

	/* (non-Javadoc)
	 * @see AudioMgr.AudioEngine#InitializeVoiceEngine()
	 */
	@Override
	public void InitializeVoiceEngine() {
        voiceName = "kevin16"; // the only usable general purpose voice
        
        System.setProperty("com.sun.speech.freetts.voice.defaultAudioPlayer", "com.sun.speech.freetts.audio.SingleFileAudioPlayer");
        try {
			marytts = new LocalMaryInterface();
			marytts.setVoice("cmu-slt-hsmm");
        }catch ( MaryConfigurationException   e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //voice.setRate(150);
        //voice.setDurationStretch((float) 1.5);
       
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
			File output=new File(FileNameOfSound+".wav");
	        marytts.setOutputType("AUDIO");
	        marytts.setOutputTypeParams("WAVE");
	        marytts.setStreamingAudio(true);
	        AudioInputStream audio = marytts.generateAudio(textTobeSound);
	        AudioSystem.write(audio, javax.sound.sampled.AudioFileFormat.Type.WAVE,output );
        }
        catch(Exception e)
        {   
            //System.out.println(e.getMessage());
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
