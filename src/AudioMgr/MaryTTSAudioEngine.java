package AudioMgr;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.client.MaryClient.Voice;
import marytts.exceptions.MaryConfigurationException;
import marytts.signalproc.effects.BaseAudioEffect;
import marytts.signalproc.effects.HMMF0ScaleEffect;
import marytts.signalproc.effects.VolumeEffect;

public class MaryTTSAudioEngine extends AudioEngine  {

    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    final java.util.Random rand = new java.util.Random();
    MaryInterface marytts;
    
    public MaryTTSAudioEngine() {
		super();
	}

	@Override
	public void InitializeVoiceEngine() {       
        try {
        	
			marytts = new LocalMaryInterface();
			marytts.setVoice("cmu-slt-hsmm");
        }catch ( MaryConfigurationException   e) {
			e.printStackTrace();
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
	       // System.out.println(HMMF0ScaleEffect.chEffectParamStart+"f0Scale"+HMMF0ScaleEffect.chParamEquals+String.valueOf(HMMF0ScaleEffect.MAX_F0_SCALE)+HMMF0ScaleEffect.chEffectParamEnd);
	       	marytts.setAudioEffects(HMMF0ScaleEffect.chEffectParamStart+HMMF0ScaleEffect.chParamEquals+String.valueOf(HMMF0ScaleEffect.MAX_F0_SCALE)+HMMF0ScaleEffect.chEffectParamEnd);
	        
	        AudioInputStream audio = marytts.generateAudio("To change to Notes. Tomorrow I will play basketball.");
	        AudioSystem.write(audio, javax.sound.sampled.AudioFileFormat.Type.WAVE,output );
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
