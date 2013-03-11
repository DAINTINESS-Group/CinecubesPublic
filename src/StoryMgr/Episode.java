/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StoryMgr;

import AudioMgr.Audio;
import TaskMgr.KeyFinding;
import TaskMgr.SubTask;
import TextMgr.Text;

/**
 *
 * @author Asterix
 */
public abstract class Episode {
    public SubTask TSK;
    public KeyFinding KeyFind;
    public Visual VS;
    public Audio Aud;
    public Text Txt;
}
