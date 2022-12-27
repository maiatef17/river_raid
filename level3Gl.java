package river_raid;

//import static River.Win.level;
//import static River.level1Gl.Score;
//import static river_raid.Win.level;
import com.sun.opengl.util.GLUT;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import javax.media.opengl.*;

import java.util.BitSet;
import static javax.media.opengl.GL.GL_CURRENT_BIT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import static river_raid.level1Gl.Score;

public class level3Gl extends AnimListener {

    ArrayList<bullet> b = new ArrayList<>();
    int counterBullet = 0;
    Boolean Drawenemy = false;
    int f = 0;
    static int Score = 0;
    static boolean GameOver = false;
    int counter = 3;
    int BatIndex = 9;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth, y = 100;
    int xplane = 50;
    int yplane = 2;
    int maxWidthS = 100;
    int maxHeightS = 100;
    int yBat1 = 100;
    int yBat2 = 105;
    int yBat3 = 107;
    int yBat4 = 110;
    int speed = 2;
    boolean pause = false;
    static boolean sDied = true;
    private int Xgenerate1 = generateX(0);
    private int Xgenerate2 = generateX(0);
    private int Xgenerate3 = generateX(0);
    private int Xgenerate4 = generateX(0);

    boolean Pause = false;
    String textureNames[] = {"plain.png", "plain.png", "plain.png", "plain.png",
        "backk2.jpg", "ARM1.png", "helicopter3.png", "helicopter3.png", "helicopter3.png", "helicopter3.png", "press.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    /*
     5 means gun in array pos
     x and y coordinate for gun
     */
    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer

        gl.glLoadIdentity();
        DrawBackground(gl);
        updateScoreAndLevel(gl);
        handleKeyPress();
        if (pause) {
            DrawBackground(gl);
            DrawSpriteS(gl, xplane, yplane, animationIndex, 1);
            DrawSprite(gl, Xgenerate1, yBat1, BatIndex, 1f);
            DrawSprite(gl, Xgenerate2, yBat2, BatIndex, 1f);
            DrawSprite(gl, Xgenerate3, yBat3, BatIndex, 1f);
            DrawSprite(gl, Xgenerate4, yBat4, BatIndex, 1f);
            updateScoreAndLevel(gl);
            for (int i = 0; i < b.size(); i++) {
                DrawBullet(gl, b.get(i).bx, b.get(i).by);
            }

        } else {
            if (counterBullet == 0) {
                DrawBackground1(gl);

            } else {

//        updateScoreAndLevel(gl);
                System.out.println("(" + Score + " , " + counter + ")");
                if (Drawenemy == true) {
                    if (yBat1 < -10) {
                        yBat1 = maxHeight;
                        Xgenerate1 = generateX(0);
                        counter--;
                    }
                    if (yBat2 < -10) {
                        yBat2 = maxHeight;
                        Xgenerate2 = generateX(0);
                        counter--;
                    }
                    if (yBat3 < -10) {
                        yBat3 = maxHeight;
                        Xgenerate3 = generateX(0);
                        counter--;
                    }
                    if (yBat4 < -10) {
                        yBat4 = maxHeight;
                        Xgenerate4 = generateX(0);
                        counter--;
                    }
                    yBat1 -= speed;
                    yBat2 -= speed;
                    yBat3 -= speed;
                    yBat4 -= speed;

                    animationIndex = animationIndex % 4;
                    DrawSpriteS(gl, xplane, yplane, animationIndex, 1);

                    //if(!GameOver){
                    //create enemy
                    DrawSprite(gl, Xgenerate1, yBat1, BatIndex, 1f);
                    DrawSprite(gl, Xgenerate2, yBat2, BatIndex, 1f);
                    DrawSprite(gl, Xgenerate3, yBat3, BatIndex, 1f);
                    DrawSprite(gl, Xgenerate4, yBat4, BatIndex, 1f);

                    BatIndex--;
                    if (BatIndex == 6) {

                       BatIndex = 9;
                    }
                    //paint bullet
                    for (int i = 0; i < b.size(); i++) {

                        DrawBullet(gl, b.get(i).bx, b.get(i).by);
                        b.get(i).by++;
//
                    }

                    //}
                    //bullet died
                    for (int i = 0; i < b.size(); i++) {
                        if (killenemy(b.get(i).bx, Xgenerate1, b.get(i).by, yBat1)) {

                            yBat1 = maxHeight;
                            Xgenerate1 = generateX(0);
                            Score++;
                        }
                        if (killenemy(b.get(i).bx, Xgenerate2, b.get(i).by, yBat2)) {
                            yBat2 = maxHeight;
                            Xgenerate2 = generateX(0);
                            Score++;
                        }
                        if (killenemy(b.get(i).bx, Xgenerate3, b.get(i).by, yBat3)) {
                            yBat3 = maxHeight;
                            Xgenerate3 = generateX(0);
                            Score++;
                        }
                        if (killenemy(b.get(i).bx, Xgenerate4, b.get(i).by, yBat4)) {
                            yBat4 = maxHeight;
                            Xgenerate4 = generateX(0);
                            Score++;
                        }
// Win The Game
                        if (Score == 20) {
                            JOptionPane.showMessageDialog(null, "Congratulation You Win  .\nGame finished .\nPress OK to exit",
                                    "Congratulation",
                                    JOptionPane.WARNING_MESSAGE);
                            System.exit(0);
                        }

//Soldier Die
                        if (KillSoldier(xplane, Xgenerate1, yplane, yBat1) ) {
                            JOptionPane.showMessageDialog(null, "GameOver You Lose .\nScore: " + Score, "GameOver",
                                    JOptionPane.WARNING_MESSAGE);
                            System.exit(0);
                        }

                        if (KillSoldier(xplane, Xgenerate2, yplane, yBat2) ) {
                            JOptionPane.showMessageDialog(null, "GameOver You Lose.\nScore: " + Score, "GameOver",
                                    JOptionPane.WARNING_MESSAGE);
                            System.exit(0);
                        }

                        if (KillSoldier(xplane, Xgenerate3, yplane, yBat3) ) {
                            JOptionPane.showMessageDialog(null, "GameOver You Lose.\nScore: " + Score, "GameOver",
                                    JOptionPane.WARNING_MESSAGE);
                            System.exit(0);

                        }

                        if (KillSoldier(xplane, Xgenerate4, yplane, yBat4)) {
                            JOptionPane.showMessageDialog(null, "GameOver You Lose.\nScore: " + Score, "GameOver",
                                    JOptionPane.WARNING_MESSAGE);
                            System.exit(0);

                        }

                    }
                }
                f = (f + 1) % 4;
            }
        }
    }

    private boolean KillSoldier(int xSolider, int xgenerate1, int ySolider, int yEnemy1) {
        if (Math.sqrt((xSolider - xgenerate1) * (xSolider - xgenerate1) + (ySolider - yEnemy1) * (ySolider - yEnemy1)) < 7) {

            return true;
        }
        return false;

    }

    private void DrawBackground1(GL gl) {

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[10]);	// Turn Blending On
        gl.glPushMatrix();
        gl.glScaled(0.5 * 1, 0.1 * 1, 1);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);

    }

    private void DrawSpriteS(GL gl, int xSolider, int ySolider, int index, int scale) {

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(xSolider / (maxWidthS / 2.0) - 0.9, ySolider / (maxHeightS / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);

    }

    private int generateX(int i) {
        x = 5 + (int) (Math.random() * 75);
        return x;
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public boolean killenemy(double x1, double x2, double y1, double y2) {
//        System.out.println( "The Space Is " +Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
        if (Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) < 5) {

            return true;
        }
        return false;
    }

    public void DrawSprite(GL gl, int x, int y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);

        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawBullet(GL gl, int xSolider, int ySolider) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[5]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(xSolider / (maxWidthS / 2.0) - 0.9, ySolider / (maxHeightS / 2.0) - 0.9, 0);

        if (counterBullet == 1) {

            gl.glScaled(0, 0, 1);
        } else {
            gl.glScaled(0.01 * 7, 0.01 * 7, 1);
        }      //System.out.println(x +" " + y);

        //System.out.println(x +" " + y);
        //System.out.println(x +" " + y);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[4]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }
//
//    public void win() {
//        if (Score == 10) {
//            Win = true;
//            Score = 0;
//        }
//        if (Win) {
//            Win = false;
//            Win w = new Win();
//            w.setVisible(true);
//
//
//
//        }
//    }

    //    static void gameOver() {
//        if (GameOver) {
//            GameOver = false;
//            sDied = false;
//
////      new Anim().dispose();
//            Guns.GameOver.setScore(Score);
//            Guns.Anim.setV(false);
//        GameOver go = new GameOver();
//            go.setVisible(true);
//
//        }
//    }
    public void updateScoreAndLevel(GL gl) {
     //   int lev = level - 1;
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glDisable(GL_TEXTURE_2D);
        gl.glPushAttrib(GL_CURRENT_BIT);
        gl.glColor4f(0.f, 0.0f, 0.0f, 1.0f);
        gl.glPushMatrix();
        GLUT glut = new GLUT();
        gl.glRasterPos2d(0.7, 0.9);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Score : " + Score);
        gl.glRasterPos2d(-0.9, 0.9);
//        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Hearts: " + counter);
//        gl.glRasterPos2d(0.7, 0.7);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Level: 3");
        gl.glPopMatrix();
        gl.glPopAttrib();
        gl.glEnable(GL_TEXTURE_2D);

    }


    /*
     * KeyListener
     */
    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (xplane > 0) {
                xplane--;
            }
            animationIndex++;
        }

        if (isKeyPressed(KeyEvent.VK_SPACE)) {
            if (f % 2 == 0) {
                b.add(new bullet(xplane + 1, yplane + 4));
            }
            Drawenemy = true;
            counterBullet++;
            System.out.println("Counter Bullet is " + counterBullet);
        }

        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (xplane < maxWidthS - 27) {
                xplane++;
            }
            animationIndex++;
            
        }
        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (xplane < maxWidthS -83) {
                xplane++;
            }
            animationIndex++;}
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (yplane > 0) {
                yplane--;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (yplane < maxHeightS - 10) {
                yplane++;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_P)) {
            pause = !pause;
        }

    
    }

    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
        // don't care
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

}