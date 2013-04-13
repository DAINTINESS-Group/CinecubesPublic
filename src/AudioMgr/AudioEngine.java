package AudioMgr;

public abstract class AudioEngine {
    
       
	public AudioEngine(){};
    
    abstract public void InitializeVoiceEngine();
    abstract public void CreateSound(String textTobeSound,String FileNameOfSound);
    abstract public String randomIdentifier();
    
}
