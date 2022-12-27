package river_raid;

import com.sun.opengl.util.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import javax.media.opengl.*;
import javax.swing.*;

public class Anim2 extends JFrame {
    static boolean Visible = true;
    static boolean n = setV(Visible);
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Anim2().setVisible(n);
            }

        });
    }


    public Anim2() {
        GLCanvas glcanvas;
        Animator animator;

        AnimListener listener1 = new level2Gl();

        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener1);
        glcanvas.addKeyListener(listener1);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(15);
        animator.add(glcanvas);
        animator.start();

        setTitle("Anim Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(620, 700);
        setLocationRelativeTo(null);

        setVisible(n);

        setFocusable(true);
        glcanvas.requestFocus();

    }

    static boolean setV(boolean b) {
        Visible = b;
        return b;


    }

    void setVisibale(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
