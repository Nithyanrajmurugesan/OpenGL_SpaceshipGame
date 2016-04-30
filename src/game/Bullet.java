package game;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Bullet {
	public Bullet(GLAutoDrawable drawable, float X_DIR, float Y_DIR, float Z_DIR, int angle) {
		// Pyramid
		GL2 gl = drawable.getGL().getGL2();
		// gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT); // Clear
		// color and depth buffer
		gl.glPushMatrix();
		// gl.glRotatef(angle, 0,10,0);
		gl.glBegin(gl.GL_POLYGON); // Begin drawing the color cube with 6 quads
		// Top face (y = 1.0f)
		// Define vertices in counter-clockwise (CCW) order with normal pointing
		// out
		gl.glColor3f(1.0f, 1.0f, 1.0f); // Red
		gl.glVertex3f(0.1f + X_DIR, 0.1f + Y_DIR, -0.1f + Z_DIR);
		gl.glVertex3f(-0.1f + X_DIR, 0.1f + Y_DIR, -0.1f + Z_DIR);
		gl.glVertex3f(-0.1f + X_DIR, 0.1f + Y_DIR, 0.1f + Z_DIR);
		gl.glVertex3f(0.1f + X_DIR, 0.1f + Y_DIR, 0.1f + Z_DIR);

		// Bottom face (y = -0.1f)
		gl.glColor3f(1.0f, 1.0f, 0.0f); // Red
		gl.glVertex3f(0.1f + X_DIR, -0.1f + Y_DIR, 0.1f + Z_DIR);
		gl.glVertex3f(-0.1f + X_DIR, -0.1f + Y_DIR, 0.1f + Z_DIR);
		gl.glVertex3f(-0.1f + X_DIR, -0.1f + Y_DIR, -0.1f + Z_DIR);
		gl.glVertex3f(0.1f + X_DIR, -0.1f + Y_DIR, -0.1f + Z_DIR);

		// Front face (z = 0.1f)
		gl.glColor3f(1.0f, 1.0f, 1.0f); // Red
		gl.glVertex3f(0.1f + X_DIR, 0.1f + Y_DIR, 0.1f + Z_DIR);
		gl.glVertex3f(-0.1f + X_DIR, 0.1f + Y_DIR, 0.1f + Z_DIR);
		gl.glVertex3f(-0.1f + X_DIR, -0.1f + Y_DIR, 0.1f + Z_DIR);
		gl.glVertex3f(0.1f + X_DIR, -0.1f + Y_DIR, 0.1f + Z_DIR);

		// Back face (z = -0.1f)
		gl.glColor3f(1.0f, 1.0f, 1.0f); // Red
		gl.glVertex3f(0.1f + X_DIR, -0.1f + Y_DIR, -0.1f + Z_DIR);
		gl.glVertex3f(-0.1f + X_DIR, -0.1f + Y_DIR, -0.1f + Z_DIR);
		gl.glVertex3f(-0.1f + X_DIR, 0.1f + Y_DIR, -0.1f + Z_DIR);
		gl.glVertex3f(0.1f + X_DIR, 0.1f + Y_DIR, -0.1f + Z_DIR);

		// Left face (x = -0.1f)
		gl.glColor3f(1.0f, 1.0f, 1.0f); // Red
		gl.glVertex3f(-0.1f + X_DIR, 0.1f + Y_DIR, 0.1f + Z_DIR);
		gl.glVertex3f(-0.1f + X_DIR, 0.1f + Y_DIR, -0.1f + Z_DIR);
		gl.glVertex3f(-0.1f + X_DIR, -0.1f + Y_DIR, -0.1f + Z_DIR);
		gl.glVertex3f(-0.1f + X_DIR, -0.1f + Y_DIR, 0.1f + Z_DIR);

		// Right face (x = 0.1f)
		gl.glColor3f(1.0f, 1.0f, 1.0f); // Red
		gl.glVertex3f(0.1f + X_DIR, 0.1f + Y_DIR, -0.1f + Z_DIR);
		gl.glVertex3f(0.1f + X_DIR, 0.1f + Y_DIR, 0.1f + Z_DIR);
		gl.glVertex3f(0.1f + X_DIR, -0.1f + Y_DIR, 0.1f + Z_DIR);
		gl.glVertex3f(0.1f + X_DIR, -0.1f + Y_DIR, -0.1f + Z_DIR);
		gl.glEnd();
		gl.glPopMatrix();
	}
}
