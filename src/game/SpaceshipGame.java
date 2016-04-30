package game;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.gl2.GLUT;

// nithin's code
public class SpaceshipGame extends Frame implements GLEventListener, KeyListener {

	private static final Component Frame = null;
	private Texture texture = null;
	GLU glu = null;
	public Timer timer = new Timer();
	public SpaceshipGame() {
		super("Game");
		setLayout(new BorderLayout());
		setSize(800, 600);
		setLocation(40, 40);
		JOptionPane.showMessageDialog(Frame, "LETS START THE GAME");
		// Close the window when told to by the OS
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// Need to set visible first before starting the rendering thread due
		// to a bug in JOGL. See JOGL Issue #54 for more information on this.
		// http://jogl.dev.java.net
		setVisible(true);
		setupJOGL();
	}

	/**
	 * Create the basics of the JOGL screen details.
	 */
	private void setupJOGL() {
		GLCapabilities caps = new GLCapabilities(GLProfile.getMaxFixedFunc(true));
		// GLCapabilities caps = new
		// GLCapabilities(GLProfile.get(GLProfile.GL2ES2));
		// GLProfile glp = GLProfile.getDefault();
		// GLCapabilities caps = new GLCapabilities(glp);
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);

		GLCanvas canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		canvas.addKeyListener(this);

		add(canvas, BorderLayout.CENTER);
		FPSAnimator animator = new FPSAnimator(canvas, 60);
		animator.start();
	}

	public void init(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

	

		// gl.glClearColor(0, 0, 0, 0);
		// gl.glMatrixMode(gl.GL_PROJECTION);
		// //gl.glFrustum( -1, 1, -1, 1, -50, 50);
		//
		// gl.glMatrixMode(GL2.GL_MODELVIEW);
		// gl.glClearDepth(1.0f); // Set background depth to farthest
		// gl.glEnable(gl.GL_DEPTH_TEST); // Enable depth testing for z-culling
		// gl.glDepthFunc(gl.GL_LEQUAL); // Set the type of depth-test
		// gl.glShadeModel(gl.GL_SMOOTH); // Enable smooth shading
		// gl.glLoadIdentity();
		// //gl.glOrtho(0, 1, 0, 1, -1, 1);
		//
		// -----------------------------------------

		glu = GLU.createGLU(gl);

		try {
			URL textureURL;
			textureURL = getClass().getClassLoader().getResource("resources/wall.png");
			if (textureURL != null) {
				// textures[i] = TextureIO.newTexture(textureURL, true, null);
				// // Alternative loader, gives upside down textures!
				BufferedImage img = ImageIO.read(textureURL);
				ImageUtil.flipImageVertically(img);
				texture = AWTTextureIO.newTexture(GLProfile.getDefault(), img, true);
				texture.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
				texture.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
				texture.setTexParameterf(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dispose();
			System.exit(0);
		}
		gl.glClearColor(0, 0, 0, 0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glClearDepth(1.0f); // Set background depth to farthest
		gl.glEnable(GL2.GL_DEPTH_TEST); // Enable depth testing for z-culling
		gl.glDepthFunc(GL2.GL_LEQUAL); // Set the type of depth-test
		gl.glShadeModel(GL2.GL_SMOOTH); // Enable smooth shading
		gl.glLoadIdentity();
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// GLU glu = new GLU();
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = GLU.createGLU(gl);
		// Compute aspect ratio of the new window
		if (height == 0)
			height = 1; // To prevent divide by 0
		float aspect = (float) width / (float) height;

		// Set the viewport to cover the new window
		// Set the aspect ratio of the clipping volume to match the viewport
		gl.glMatrixMode(gl.GL_PROJECTION); // To operate on the Projection
											// matrix
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);

		glu.gluPerspective(45, aspect, 20f, 160);
		gl.glPushMatrix();
		glu.gluLookAt(10, -5, 40, 0, 0, 0, 0, 1, 0);
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	// global variables
	int angle = 0;
	int inc = 1;

	// Cam View
	double eyeX = 0;
	double eyeY = 10;
	double eyeZ = 30;

	// X,Y,Z movement
	float X_DIR = 0.0f;
	float Y_DIR = 0.8f;
	float Z_DIR = 0.0f;
	float x_DIR = 0.6f;
	float y_DIR = 0.1f;
	float z_DIR = 0.0f;
	float LENGTH = 0.1f;

	float ALIEN_Y_POSITION = 5.0f;
	float Shadow_y_POSITION = 0.2f;
	float BULLET_FLY_DISTANCE = 10.0f;

	static int MAX_WIDTH = 10;

	static float ALIEN_X_POS;
	static float ALIEN_Z_POS;
	static float ALIEN_y_POSITION;

	static int NumOfHits = 0;
	static int NumOfGunshots = 0;
	static int NumOfAliens = 0;

	boolean generateAlien = true;
	boolean fireMissile = false;
	boolean gameOver = false;

	Bullet M;
	Target alien;
	Shadow S;

	// argument added to allow updating the perspective
	public void update(GLAutoDrawable drawable) {
		angle = (angle + inc) % 360;
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = GLU.createGLU(gl);

		gl.glMatrixMode(gl.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glPushMatrix();
		// glu.gluPerspective(45, 10, 20f, 160);
		glu.gluLookAt(eyeX, eyeY, eyeZ, 0, 0, 0, 0, 1, 0);
	}

	/**
	 * Called by the drawable to perform rendering by the client.
	 *
	 * @param drawable
	 *            The display context to render to
	 */

	@Override
	public void display(GLAutoDrawable drawable) {
		update(drawable);
		Plane(drawable);
		Text(drawable);

		SpaceShip ML = new SpaceShip(drawable, X_DIR, 0.0f, Z_DIR, LENGTH, angle);
		// System.out.printf("spaceship",+Z_DIR);
		M = new Bullet(drawable, X_DIR, Y_DIR, Z_DIR, angle);
		// System.out.println(Z_DIR);

		alien = new Target(drawable, ALIEN_X_POS, ALIEN_Y_POSITION, ALIEN_Z_POS, angle);
		S = new Shadow(drawable, ALIEN_X_POS, Shadow_y_POSITION, ALIEN_Z_POS);
		
		if (generateAlien) {
			NumOfAliens++;
			GenerateAlienPosition();
			generateAlien = false;
		}

		if (fireMissile) {
			Y_DIR++;
			M = new Bullet(drawable, X_DIR, Y_DIR, Z_DIR, angle);
			if (Y_DIR > BULLET_FLY_DISTANCE) {
				Y_DIR = 0.5f;
				M = new Bullet(drawable, X_DIR, Y_DIR, Z_DIR, angle);
				fireMissile = false;

			}
		}

	}
	
	
	
	

	/**
	 * Called by the drawable when the display mode or the display device
	 * associated with the GLDrawable has changed
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
	}

	public void Plane(GLAutoDrawable drawable) {
		// Plane
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT); // Clear
																		// color
																		// and
																		// depth
																		// buffer
		gl.glPushMatrix();
		// gl.glBegin(gl.GL_QUADS);

		// texture quad

		// gl.glPushMatrix();
		texture.bind(gl);
		texture.enable(gl);
		gl.glBegin(gl.GL_QUADS);
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glTexCoord2f(20, 20);
		gl.glVertex3f(-1.0f * MAX_WIDTH, -1.0f, -1.0f * MAX_WIDTH);
		gl.glTexCoord2f(20, 0);
		gl.glVertex3f(-1.0f * MAX_WIDTH, -1.0f, 1.0f * MAX_WIDTH);
		gl.glTexCoord2f(0, 0);
		gl.glVertex3f(1.0f * MAX_WIDTH, -1.0f, 1.0f * MAX_WIDTH);
		gl.glTexCoord2f(0, 20);
		gl.glVertex3f(1.0f * MAX_WIDTH, -1.0f, -1.0f * MAX_WIDTH);
		gl.glEnd();
		texture.disable(gl);
		gl.glPopMatrix();
	}

	public void Text(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLUT points = new GLUT();
		gl.glRasterPos3d(10, 10, 2);
		points.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "KILLED: " + NumOfHits);

		GLUT points2 = new GLUT();
		gl.glRasterPos3d(-2, 10, 2);
		points2.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "SHOT: " + NumOfGunshots);

		GLUT points3 = new GLUT();
		gl.glRasterPos3d(-15, 10, 2);
		points3.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "NUMBER OF ALIENS: " + NumOfAliens);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {

	}

	public void keyPressed(KeyEvent e) {

		if (NumOfHits == 3) {
			// System.out.println("Gameover");
			playSound("Game Win.wav");
			timer.cancel();// Alien position stops
			JOptionPane.showMessageDialog(Frame, "GAME WIN");
			System.exit(0);
			
			
		} else if (NumOfGunshots - NumOfAliens == 3) {
			playSound("Game Lost.wav");
			timer.cancel();// alien position stops
			JOptionPane.showMessageDialog(Frame, "GAME LOST");
			System.exit(0);
		} 
		
		else {
			int key = e.getKeyCode(); // Tells which key was pressed.
			if (key == KeyEvent.VK_HOME)
				System.exit(0);
			else if (key == KeyEvent.VK_LEFT && X_DIR > -(MAX_WIDTH - 1))
				X_DIR -= 1;
			else if (key == KeyEvent.VK_RIGHT && X_DIR < (MAX_WIDTH - 1))
				X_DIR += 1;
			else if (key == KeyEvent.VK_UP && Z_DIR > -(MAX_WIDTH - 1))
				Z_DIR -= 1;
			else if (key == KeyEvent.VK_DOWN && Z_DIR < (MAX_WIDTH - 1))
				Z_DIR += 1;
			else if (key == KeyEvent.VK_Q)
				eyeX++;
			else if (key == KeyEvent.VK_A)
				eyeX--;
			else if (key == KeyEvent.VK_W)
				eyeY++;
			else if (key == KeyEvent.VK_S)
				eyeY--;
			else if (key == KeyEvent.VK_E)
				eyeZ++;
			else if (key == KeyEvent.VK_D)
				eyeZ--;
			else if (key == KeyEvent.VK_SPACE) {
				playSound("Blaster.wav");

				fireMissile = true;
				NumOfGunshots++;
				if (isAlienHit()) {
					NumOfHits++;
					generateAlien = true;
					playSound("Explode.wav");// if bullet hits alien 

				}
			}
			repaint();
		}
	}

	public boolean isAlienHit() {
		if ((X_DIR == ALIEN_X_POS) && (Z_DIR == ALIEN_Z_POS)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called when the user types a character.
	 */
	public void keyTyped(KeyEvent e) {
		char ch = e.getKeyChar(); // Which character was typed.
	}

	public void keyReleased(KeyEvent e) {
		char ch = e.getKeyChar(); // Which character was typed.
	}

	public static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(getClass().getResourceAsStream("../resources/" + url));
					clip.open(inputStream);
					clip.start();
					// System.out.println("m");
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

	public static void main(String[] args) {
		SpaceshipGame alienGame = new SpaceshipGame();
		alienGame.setVisible(true);

		class RunInterval extends TimerTask {
			public void run() {
				GenerateAlienPosition();
			}
		}
		
		// And From your main() method or any other method
//		Timer timer = new Timer();
		alienGame.timer.schedule(new RunInterval(), 0, 5000);
	}

	public static void GenerateAlienPosition() {
		Random random = new Random();
		int max = (MAX_WIDTH - 1);
		int min = -(MAX_WIDTH - 1);
		ALIEN_X_POS = (float) random.nextInt(max - min) + min;
		ALIEN_Z_POS = (float) random.nextInt(max - min) + min;
	}

}
