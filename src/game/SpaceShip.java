package game;

import java.util.Random;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class SpaceShip {

	public SpaceShip(GLAutoDrawable drawable, float X_DIR, float Y_DIR, float Z_DIR, float LENGTH, int angle) {
		// Pyramid
		GL2 gl = drawable.getGL().getGL2();
		// gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT); // Clear
		// color and depth buffer
		gl.glPushMatrix();
		// gl.glRotatef(angle, 0,10,0);
		gl.glBegin(gl.GL_TRIANGLE_FAN); // Begin drawing the color cube with 6
										// quads
		// Top face (y = 1.0f)
		// Define vertices in counter-clockwise (CCW) order with normal pointing
		// out

		// Front

		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex3f(X_DIR + LENGTH * 0, Y_DIR + LENGTH * 20, Z_DIR + 0);
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex3f(X_DIR - LENGTH * 8, Y_DIR - LENGTH * 8, Z_DIR + LENGTH * 8);
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(X_DIR - LENGTH * 0, Y_DIR - LENGTH * 0, Z_DIR + LENGTH * 8);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glVertex3f(X_DIR + LENGTH * 8, Y_DIR - LENGTH * 8, Z_DIR + LENGTH * 8);

		// right

		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex3f(X_DIR + LENGTH * 0, Y_DIR + LENGTH * 20, Z_DIR + 0);
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex3f(X_DIR + LENGTH * 8, Y_DIR - LENGTH * 8, Z_DIR + LENGTH * 8);
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(X_DIR + LENGTH * 8, Y_DIR - LENGTH * 0, Z_DIR + 0);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glVertex3f(X_DIR + LENGTH * 8, Y_DIR - LENGTH * 8, -1 * LENGTH * 8 + Z_DIR);

		// back

		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex3f(X_DIR + LENGTH * 0, Y_DIR + LENGTH * 20, Z_DIR + 0);
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex3f(X_DIR - LENGTH * 8, Y_DIR - LENGTH * 8, -1 * LENGTH * 8 + Z_DIR);
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(X_DIR - LENGTH * 0, Y_DIR - LENGTH * 0, -1 * LENGTH * 8 + Z_DIR);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glVertex3f(X_DIR + LENGTH * 8, Y_DIR - LENGTH * 8, -1 * LENGTH * 8 + Z_DIR);

		//

		// //LEFT

		gl.glColor3f(1.0f, 0.0f, 1.0f);
		gl.glVertex3f(X_DIR + LENGTH * 0, Y_DIR + LENGTH * 20, Z_DIR + 0);
		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glVertex3f(X_DIR - (LENGTH * 8), Y_DIR - LENGTH * 8, Z_DIR + LENGTH * 8);
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		gl.glVertex3f(X_DIR - (LENGTH * 8), Y_DIR - LENGTH * 0, Z_DIR + 0);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glVertex3f(X_DIR - (LENGTH * 8), Y_DIR - LENGTH * 8, -1 * LENGTH * 8 + Z_DIR);
		gl.glEnd();
		gl.glPopMatrix();
	}
}