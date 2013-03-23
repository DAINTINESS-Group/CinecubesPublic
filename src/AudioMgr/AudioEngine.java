/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioMgr;

/**
 *
 * @author Asterix
 */
public abstract class AudioEngine {
    
       
	public AudioEngine(){};
    
    abstract public void InitializeVoiceEngine();
    abstract public void CreateSound(String textTobeSound,String FileNameOfSound);
    abstract public String randomIdentifier();
    
}
