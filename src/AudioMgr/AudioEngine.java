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
    
       
    AudioEngine(){};
    
    abstract void InitializeVoiceEngine();
    abstract void CreateSound(String textTobeSound,String FileNameOfSound);
    
}
