package game;
//External imports
import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;



//Local imports
//None

/**
* Example application that demonstrates how to put together a single-threaded
* rendering system.
*
* @author Justin Couch
* @version $Revision: 1.5 $
*/
public class ThreeDStarter extends Frame
implements GLEventListener, KeyListener
{
	int Z=0;
	int Ztranslate=0;
	int zoff=0;
	int angle=0;
	int inc=1;
	static int WIDTH = 800;
	static int HEIGHT = 400;
	
	int shipPositionX = 0;
	int shipPositionY = 0;
	
	
	int shipPositionZ=0;
	
	
	
	boolean fired=false;
	
	int fireDistance=1;
	
	public ThreeDStarter()
	{

		super("3D Demo");

		setLayout(new BorderLayout());

		setSize(WIDTH, HEIGHT);
		setLocation(40, 40);


		//Close the window when told to by the OS
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

	//---------------------------------------------------------------
	// Methods defined by GLEventListener
	//---------------------------------------------------------------

	/**
	 * Called by the drawable immediately after the OpenGL context is
	 * initialized; the GLContext has already been made current when
	 * this method is called.
	 *
	 * @param drawable The display context to render to
	 */
	public void init(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();     
		gl.glClearColor(0, 0, 0, 0);     
		gl.glMatrixMode(gl.GL_PROJECTION);
		//gl.glFrustum( -1, 1, -1, 1, -50, 50);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glClearDepth(1.0f);                   // Set background depth to farthest
		gl.glEnable(gl.GL_DEPTH_TEST);   // Enable depth testing for z-culling
		gl.glDepthFunc(gl.GL_LEQUAL);    // Set the type of depth-test
		gl.glShadeModel(gl.GL_SMOOTH);   // Enable smooth shading
		gl.glLoadIdentity();
		// gl.glOrtho(0, 1, 0, 1, -1, 1);
	}

	/**
	 * Called by the drawable when the surface resizes itself. Used to
	 * reset the viewport dimensions.
	 *
	 * @param drawable The display context to render to
	 */
	public void reshape(GLAutoDrawable drawable,
			int x,
			int y,
			int width,
			int height)
	{
		//GLU glu = new GLU();
		GL2 gl = drawable.getGL().getGL2();
		GLU glu=GLU.createGLU(gl);
		// Compute aspect ratio of the new window
		if (height == 0) height = 1;                // To prevent divide by 0
		float aspect = (float)width / (float)height;
		
		WIDTH = width;
		HEIGHT = height;

		// Set the viewport to cover the new window


		// Set the aspect ratio of the clipping volume to match the viewport
		gl.glMatrixMode(gl.GL_PROJECTION);  // To operate on the Projection matrix
		gl.glLoadIdentity(); 
		gl.glViewport(0, 0, width, height);

		glu.gluPerspective(45, aspect, 20f, 160);
		gl.glPushMatrix();
		glu.gluLookAt( 10,-5,40,  0,0,0,  0,1,0 ); 
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glLoadIdentity();          
	}

	/**
	 * Called by the drawable when the display mode or the display device
	 * associated with the GLDrawable has changed
	 */
	public void displayChanged(GLAutoDrawable drawable,
			boolean modeChanged,
			boolean deviceChanged)
	{
	}

	/**
	 * Called by the drawable to perform rendering by the client.
	 *
	 * @param drawable The display context to render to
	 */

	@Override
	public void display(GLAutoDrawable drawable) {   	
		update(drawable);
		render(drawable);
	}

	// argument added to allow updating the perspective
	public void update (GLAutoDrawable drawable){
		angle=(angle+inc) % 360;
		GL2 gl = drawable.getGL().getGL2();
		GLU glu=GLU.createGLU(gl);

		gl.glMatrixMode(gl.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glPushMatrix();
		//Move the eye in relation to the scene
		//Z+=zoff;zoff=0;
		//glu.gluLookAt( 10.0,3,40,  0,0,0,  0,1,0 );
		glu.gluLookAt( 0,0,42,  0,0,0,  0,1,0 ); 
	}

	public void render(GLAutoDrawable drawable)
	{

		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT); // Clear color and depth buffer
		float X_CENTRE=0, LENGTH=5,Y_CENTRE=0;
		
		
		gl.glPushMatrix();
		gl.glScalef(2f, 1f, 3f); 
		gl.glBegin(gl.GL_QUADS);
		
		//gl.glBegin(gl.GL_TEXTURE_2D);
		
		gl.glColor3f(.8f, .8f, .8f);
		gl.glVertex3f(-20.0f, 0.0f, -20.0f);		
		gl.glVertex3f(-20.0f, -10.0f, 0.0f);		
		gl.glVertex3f(20.0f, -10.0f, 0.0f);		
		gl.glVertex3f(20.0f, 0.0f, -20.0f);	
		
//		gl.glVertex3f(-50.0f, 0.0f, -50.0f);		
//		gl.glVertex3f(-50.0f, -50.0f, 0.0f);		
//		gl.glVertex3f(50.0f, -50.0f, 0.0f);		
//		gl.glVertex3f(50.0f, 0.0f, -50.0f);	
		
	
		
		
		
		gl.glEnd();
		gl.glPopMatrix();
	
		///////////////////////
		////////////////////////
		gl.glPushMatrix(); 	
		gl.glScalef(.1f, .1f, .1f);
		gl.glTranslated(shipPositionX, shipPositionY, shipPositionZ);
		zoff=0;    
		
		
		gl.glBegin(gl.GL_TRIANGLE_FAN);
		//specify the vertices to draw Ship in 2d space
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex2f( X_CENTRE + LENGTH * 0, Y_CENTRE + LENGTH * 12); 
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex2f( X_CENTRE - LENGTH * 8, Y_CENTRE - LENGTH * 8);
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex2f( X_CENTRE - LENGTH  * 0, Y_CENTRE - LENGTH * 0);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glVertex2f( X_CENTRE + LENGTH * 8, Y_CENTRE - LENGTH * 8);
		gl.glEnd();
		
		
		
		
		
		
		
		
//		//gl.glRotatef(angle,0,-1,0); // circle the y axis
//		gl.glBegin(gl.GL_TRIANGLE_FAN);
//		//specify the vertices to draw Ship in 3d `space
//		
//		//front
//		gl.glColor3f(1.0f, 0.0f, 1.0f);
//		gl.glVertex3f( X_CENTRE + LENGTH * 0, Y_CENTRE + LENGTH * 12 , 0 ); 
//		gl.glColor3f(1.0f, 1.0f, 0.0f);
//		gl.glVertex3f( X_CENTRE - LENGTH * 8, Y_CENTRE - LENGTH * 8, LENGTH * 8);
//		gl.glColor3f(0.0f, 1.0f, 1.0f);
//		gl.glVertex3f( X_CENTRE - LENGTH  * 0, Y_CENTRE - LENGTH * 0,LENGTH * 8);
//		gl.glColor3f(1.0f, 0.0f, 0.0f);
//		gl.glVertex3f( X_CENTRE + LENGTH * 8, Y_CENTRE - LENGTH * 8,LENGTH * 8);
//		
//		//right
//		gl.glColor3f(1.0f, 0.0f, 1.0f);
//		gl.glVertex3f( X_CENTRE + LENGTH * 0, Y_CENTRE + LENGTH * 12, 0); 
//		gl.glColor3f(1.0f, 1.0f, 0.0f);
//		gl.glVertex3f( X_CENTRE + LENGTH * 8, Y_CENTRE - LENGTH * 8,LENGTH * 8);
//		gl.glColor3f(0.0f, 1.0f, 1.0f);
//		gl.glVertex3f( X_CENTRE + LENGTH * 8, Y_CENTRE - LENGTH * 0, 0);
//		gl.glColor3f(1.0f, 0.0f, 0.0f);
//		gl.glVertex3f( X_CENTRE + LENGTH * 8, Y_CENTRE - LENGTH * 8, -1 * LENGTH * 8);
//		
//		//back
//		gl.glColor3f(1.0f, 0.0f, 1.0f);
//		gl.glVertex3f( X_CENTRE + LENGTH * 0, Y_CENTRE + LENGTH * 12, 0);
//		gl.glColor3f(1.0f, 1.0f, 0.0f);
//		gl.glVertex3f( X_CENTRE - LENGTH * 8, Y_CENTRE - LENGTH * 8, -1 * LENGTH * 8);
//		gl.glColor3f(0.0f, 1.0f, 1.0f);
//		gl.glVertex3f( X_CENTRE - LENGTH  * 0, Y_CENTRE - LENGTH * 0,-1 * LENGTH * 8);
//		gl.glColor3f(1.0f, 0.0f, 0.0f);
//		gl.glVertex3f( X_CENTRE + LENGTH * 8, Y_CENTRE - LENGTH * 8, -1 * LENGTH * 8);
////		
////		//LEFT
//		gl.glColor3f(1.0f, 0.0f, 1.0f);
//		gl.glVertex3f( X_CENTRE + LENGTH * 0, Y_CENTRE + LENGTH * 12, 0);
//		gl.glColor3f(1.0f, 1.0f, 0.0f);
//		gl.glVertex3f( -1 * (X_CENTRE + LENGTH * 8), Y_CENTRE - LENGTH * 8, -1 * LENGTH * 8);
//		gl.glColor3f(0.0f, 1.0f, 1.0f);
//		gl.glVertex3f( -1 * (X_CENTRE + LENGTH * 8), Y_CENTRE - LENGTH * 0, 0);
//		gl.glColor3f(1.0f, 0.0f, 0.0f);
//		gl.glVertex3f( -1 * (X_CENTRE + LENGTH * 8), Y_CENTRE - LENGTH * 8, LENGTH * 8);
//		
//		gl.glEnd();
		
		if(fireDistance>72)fired=false;
		if(fired){	
			GLU glu=GLU.createGLU(gl);
			GLUquadric quadratic = glu.gluNewQuadric();
			//gl.glTranslatef(20, 120, -20);
			gl.glTranslatef( X_CENTRE + LENGTH * 0, Y_CENTRE + LENGTH * 12+(fireDistance+=2),0); 
			//flip the cylinder to point up along the Y instead of the Z
			gl.glRotatef(90.0f, 1f, 0.0f, 0f);
			glu.gluCylinder(quadratic,1f,5f,10f,32,32);
			glu.gluDeleteQuadric( quadratic );
		}
		
	
		
		gl.glPopMatrix();
		////////////////////////
		////////////////////////
		gl.glPushMatrix();
		//gl.glRotatef(angle,0,-1,0); // circle the y axis
		gl.glRotatef(angle,0,-1,0);
		GLUT glut = new GLUT();
		float textPosx = -0.4f;
		float textPosy = -2.1f;

		gl.glColor3f(1.0f, 0.0f, 1.0f);
		textPosx = 0f;
		textPosy = 4f;
		// Move to rastering position
		gl.glRasterPos3d(-10, 2, 2);
		//gl.glRasterPos2f(textPosx, textPosy);
		// convert text to bitmap and tell what string to put
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Comp Sci");
		gl.glTranslatef(10f, 6f, 10f);   
		gl.glBegin(gl.GL_QUADS);                // Begin drawing the color cube with 6 quads
		// Top face (y = 1.0f)
		// Define vertices in counter-clockwise (CCW) order with normal pointing out
		gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
		gl.glVertex3f( 1.0f, 1.0f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glVertex3f(-1.0f, 1.0f,  1.0f);
		gl.glVertex3f( 1.0f, 1.0f,  1.0f);

		// Bottom face (y = -1.0f)
		gl.glColor3f(1.0f, 0.5f, 0.0f);     // Orange
		gl.glVertex3f( 1.0f, -1.0f,  1.0f);
		gl.glVertex3f(-1.0f, -1.0f,  1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f( 1.0f, -1.0f, -1.0f);

		// Front face  (z = 1.0f)
		gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
		gl.glVertex3f( 1.0f,  1.0f, 1.0f);
		gl.glVertex3f(-1.0f,  1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glVertex3f( 1.0f, -1.0f, 1.0f);

		// Back face (z = -1.0f)
		gl.glColor3f(1.0f, 1.0f, 0.0f);     // Yellow
		gl.glVertex3f( 1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f,  1.0f, -1.0f);
		gl.glVertex3f( 1.0f,  1.0f, -1.0f);

		// Left face (x = -1.0f)
		gl.glColor3f(0.0f, 0.0f, 1.0f);     // Blue
		gl.glVertex3f(-1.0f,  1.0f,  1.0f);
		gl.glVertex3f(-1.0f,  1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glVertex3f(-1.0f, -1.0f,  1.0f);

		// Right face (x = 1.0f)
		gl.glColor3f(1.0f, 0.0f, 1.0f);     // Magenta
		gl.glVertex3f(1.0f,  1.0f, -1.0f);
		gl.glVertex3f(1.0f,  1.0f,  1.0f);
		gl.glVertex3f(1.0f, -1.0f,  1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glEnd(); 
		gl.glPopMatrix();


		// Render a pyramid consists of 4 triangles 
		gl.glPushMatrix();
		gl.glTranslatef(10f,6f, 10f); 
		gl.glRotatef(angle,1,-1,1);
		gl.glBegin(gl.GL_TRIANGLES);           // Begin drawing the pyramid with 4 triangles
		// Front
		gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
		gl.glVertex3f( 0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);     // Blue
		gl.glVertex3f(1.0f, -1.0f, 1.0f);

		// Right
		gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);     // Blue
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
		gl.glVertex3f(1.0f, -1.0f, -1.0f);

		// Back
		gl.glColor3f(1.0f, 0.0f, 0.0f);     // Red
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f, 1.0f, 0.0f);     // Green
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glColor3f(0.0f, 0.0f, 1.0f);     // Blue
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);

		// Left
		gl.glColor3f(1.0f,0.0f,0.0f);       // Red
		gl.glVertex3f( 0.0f, 1.0f, 0.0f);
		gl.glColor3f(0.0f,0.0f,1.0f);       // Blue
		gl.glVertex3f(-1.0f,-1.0f,-1.0f);
		gl.glColor3f(0.0f,1.0f,0.0f);       // Green
		gl.glVertex3f(-1.0f,-1.0f, 1.0f);
		gl.glEnd();   // Done drawing the pyramid
		gl.glPopMatrix();






	}


	//---------------------------------------------------------------
	// Local methods
	//---------------------------------------------------------------

	/**
	 * Create the basics of the JOGL screen details.
	 */
	private void setupJOGL()
	{
		GLProfile glp = GLProfile.getDefault();  	
		GLCapabilities caps = new GLCapabilities(glp);
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);

		GLCanvas canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		canvas.addKeyListener(this);

		add(canvas, BorderLayout.CENTER);
		FPSAnimator animator = new FPSAnimator(canvas, 60);
		animator.start();

	}

	public static void main(String[] args)
	{
		ThreeDStarter demo = new ThreeDStarter();
		demo.setVisible(true);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {

	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();  // Tells which key was pressed.
		
//		
//		if(key== KeyEvent.VK_LEFT)
//		{
//			if (e.isShiftDown())
//				
//				rotation=(roation+1.0/(2.0*Math.PI));
//		}
//		
		
		if ( key == KeyEvent.VK_HOME )
			System.exit(0); 
		else if (key== KeyEvent.VK_SPACE && !fired){
			playSound("blaster.wav");
			fired=true;fireDistance=1;
		}
		else if (key == KeyEvent.VK_LEFT)
		{
			//40
			if (shipPositionX > (-1 * WIDTH / 2 + 180))
				shipPositionX = shipPositionX - 3;
		}
		else if (key == KeyEvent.VK_RIGHT)
		{
			//-40-8
			if (shipPositionX + 3 < (WIDTH / 2 - 180))
				shipPositionX = shipPositionX + 3;
		}
		
		
		
//		else if (key == KeyEvent.VK_UP)
//		{
//			if (shipPositionY + 3 < ((HEIGHT / 2) - 62))
//				shipPositionY = shipPositionY + 3;
//		}
//		else if (key == KeyEvent.VK_DOWN)
//		{
//			if (shipPositionY - 3 > ((-1* HEIGHT / 2) + 38))
//				shipPositionY = shipPositionY - 3;
//		}
		
		
		else if (key == KeyEvent.VK_UP)
		{			
			if (shipPositionZ - 3 > ((-1* HEIGHT / 2) ))
				shipPositionZ = shipPositionZ - 3;
						
		}
		else if (key == KeyEvent.VK_DOWN)
		{
			
			if (shipPositionZ + 3 < ((HEIGHT / 2) ))
				shipPositionZ= shipPositionZ + 3;
			
		}
		
		
		
		
		
		repaint();
	}
	
	

	/**
	 * Called when the user types a character.
	 */
	public void keyTyped(KeyEvent e) { 
		char ch = e.getKeyChar();  // Which character was typed.
	}

	public void keyReleased(KeyEvent e) { 
		char ch = e.getKeyChar();  // Which character was typed.
	}

	public static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							getClass().getResourceAsStream("resources/" + url));
					clip.open(inputStream);
					clip.start(); 
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

}


