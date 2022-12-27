package river_raid;

import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import javax.media.opengl.GLEventListener;

/**
 *
 * @author Tyba
 */

public abstract class AnimListener implements GLEventListener, KeyListener {
 
    protected String assetsFolderName = "river_raid\\images";

    void windowClosing(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
