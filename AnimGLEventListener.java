package river_raid;

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

public class AnimGLEventListener extends AnimListener {

    ArrayList<bullet> b = new ArrayList<>();
//    enemy []e = new enemy[10];
//    int [] exist = new int[10];

    int f = 0;
    static int Score = 0;
    static boolean GameOver = false;
    int counter = 4;
    int EnemyIndex = 9;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth, y = 100;
    int xplane = 50;
    int yplane = 2;
    int maxWidthS = 100;
    int maxHeightS = 100;
    int yEnemy1 = 100;
    int yEnemy2 = 100;
    int yEnemy3 = 100;
    int yEnemy4 = 100;
    int speed = 1;
    boolean Gameover = false;
    boolean Win = false;
    static boolean sDied = true;
    private int Xgenerate1 = generateX(0);
    private int Xgenerate2 = generateX(0);
    private int Xgenerate3 = generateX(0);
    private int Xgenerate4 = generateX(0);

    String textureNames[] = {"Man1.png", "Man2.png", "Man3.png", "Man4.png",
        "Back3.jpg", "ARM11.png", "Man6.png", "Man7.png", "Man8.png", "Man9.png"};
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
        handleKeyPress();
        updateScoreAndLevel(gl);
        gameOver();
        //win();
        System.out.println("(" + Score + " , " + counter + ")");
        if (yEnemy1 < -10) {
            yEnemy1 = maxHeight;
            Xgenerate1 = generateX(0);
          
        }
        if (yEnemy2 < -10) {
            yEnemy2 = maxHeight;
            Xgenerate2 = generateX(0);
          
        }
        if (yEnemy3 < -10) {
            yEnemy3 = maxHeight;
            Xgenerate3 = generateX(0);
            counter--;
        }
        if (yEnemy4 < -10) {
            yEnemy4 = maxHeight;
            Xgenerate4 = generateX(0);
          
        }
        yEnemy1 -= speed;
        yEnemy2 -= speed;
        yEnemy3 -= speed;
        yEnemy4 -= speed;

        animationIndex = animationIndex % 4;
        DrawSpriteS(gl, xplane, yplane, animationIndex, 1);

        //if(!GameOver){
        //create enemy
        DrawSprite(gl, Xgenerate1, yEnemy1, EnemyIndex, 1);
        DrawSprite(gl, Xgenerate2, yEnemy2, EnemyIndex, 1);
        DrawSprite(gl, Xgenerate3, yEnemy3, EnemyIndex, 1);
        DrawSprite(gl, Xgenerate4, yEnemy4, EnemyIndex, 1);

        EnemyIndex--;
        if (EnemyIndex == 6) {

            EnemyIndex = 9;
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
            if (killenemy(b.get(i).bx, Xgenerate1, b.get(i).by, yEnemy1)) {

                yEnemy1 = maxHeight;
                Xgenerate1 = generateX(0);
                Score++;
            }
            if (killenemy(b.get(i).bx, Xgenerate2, b.get(i).by, yEnemy2)) {
                yEnemy2 = maxHeight;
                Xgenerate2 = generateX(0);
                Score++;
            }
            if (killenemy(b.get(i).bx, Xgenerate3, b.get(i).by, yEnemy3)) {
                yEnemy3 = maxHeight;
                Xgenerate3 = generateX(0);
                Score++;
            }
            if (killenemy(b.get(i).bx, Xgenerate4, b.get(i).by, yEnemy4)) {
                yEnemy4 = maxHeight;
                Xgenerate4 = generateX(0);
                Score++;
            }

//Soldier Die
            if (sDied) {
                if (KillSoldier(xplane, Xgenerate1, yplane, yEnemy1) || counter == 0) {
                    GameOver = true;
                }

                if (KillSoldier(xplane, Xgenerate2, yplane, yEnemy2) || counter == 0) {
                    GameOver = true;
                }

                if (KillSoldier(xplane, Xgenerate3, yplane, yEnemy3) || counter == 0) {
                    GameOver = true;
                }

                if (KillSoldier(xplane, Xgenerate4, yplane, yEnemy4) || counter == 0) {
                    GameOver = true;
                }

            }
        }
        f = (f + 1) % 4;
    }

    private boolean KillSoldier(int xSolider, int xgenerate1, int ySolider, int yEnemy1) {
        if (Math.sqrt((xSolider - xgenerate1) * (xSolider - xgenerate1) + (ySolider - yEnemy1) * (ySolider - yEnemy1)) < 5) {

            return true;
        }
        return false;

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
        x = 1 + (int) (Math.random() * 90);
        return x;
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public boolean killenemy(double x1, double x2, double y1, double y2) {
//        System.out.println( "The Space Is " +Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
        if (Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) < 3) {

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

        gl.glScaled(0.01 * 5, 0.01 * 5, 1);
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
//        }
//    }

    static void gameOver() {
        if (GameOver) {
            GameOver = false;
            sDied = false;
            GameOver go = new GameOver();
            go.setVisible(true);
    //       river_raid.GameOver.setScore(Score);
            river_raid.Anim2.setV(false);
        }
    }
    public void updateScoreAndLevel(GL gl) {

        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glDisable(GL_TEXTURE_2D);
        gl.glPushAttrib(GL_CURRENT_BIT);
        gl.glColor4f(0.9f, 0.9f, 0.9f, 1.0f);
        gl.glPushMatrix();
        GLUT glut = new GLUT();
        gl.glRasterPos2d(-0.1, 0.9);
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "Score : " + Score);
        gl.glRasterPos2d(-0.9, 0.9);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "LV: " + counter);
        gl.glPopMatrix();
        gl.glPopAttrib();
        gl.glEnable(GL_TEXTURE_2D);

    }

    /*
     * KeyListener
     */
    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_SPACE)) {
            if (f % 2 == 0) {
                b.add(new bullet(xplane + 1, yplane + 4));
            }
        }

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (xplane > 0) {
                xplane--;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (xplane < maxWidthS - 10) {
                xplane++;
            }
            animationIndex++;
        }
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
