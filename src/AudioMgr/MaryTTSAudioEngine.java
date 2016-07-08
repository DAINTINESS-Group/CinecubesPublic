package AudioMgr;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;

public class MaryTTSAudioEngine extends AudioEngine  {

    /**
	 * @uml.property  name="lexicon"
	 */
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    /**
	 * @uml.property  name="rand"
	 */
    final java.util.Random rand = new java.util.Random();
    /**
	 * @uml.property  name="mTTS"
	 * @uml.associationEnd  
	 */
    MaryInterface MTTS;
    
    public MaryTTSAudioEngine() {
		super();
	}

	@Override
	public void InitializeVoiceEngine() {       
        try {
			MTTS = new LocalMaryInterface();
			MTTS.setVoice("cmu-slt-hsmm");
        }catch ( MaryConfigurationException   e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see AudioMgr.AudioEngine#CreateSound(java.lang.String, java.lang.String)
	 */
	@Override
	public void CreateAudio(String textTobeSound, String FileNameOfSound) {
		try
        {
		
			File output=new File(FileNameOfSound+".wav");
	        MTTS.setOutputType("AUDIO");
	        MTTS.setOutputTypeParams("WAVE");
	        MTTS.setStreamingAudio(true);
	       // System.out.println(HMMF0ScaleEffect.chEffectParamStart+"f0Scale"+HMMF0ScaleEffect.chParamEquals+String.valueOf(HMMF0ScaleEffect.MAX_F0_SCALE)+HMMF0ScaleEffect.chEffectParamEnd);
	       	//MTTS.setAudioEffects(HMMF0ScaleEffect.chEffectParamStart+HMMF0ScaleEffect.chParamEquals+String.valueOf(HMMF0ScaleEffect.MAX_F0_SCALE)+HMMF0ScaleEffect.chEffectParamEnd);
	        
	        AudioInputStream audio = MTTS.generateAudio(textTobeSound);
	        AudioSystem.write(audio, AudioFileFormat.Type.WAVE,output );
        }
        catch(Exception e)
        {   
            //System.out.println(e.getMessage());
        }
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
