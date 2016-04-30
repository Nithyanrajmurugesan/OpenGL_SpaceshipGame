package game;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Target {
	public Target(GLAutoDrawable drawable, float X_DIR, float Y_DIR, float Z_DIR, int angle) {
		// Pyramid
		GL2 gl = drawable.getGL().getGL2();
		// GL2 gl = gl2;
		// gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT); // Clear
		// color and depth buffer
		gl.glPushMatrix();
		//gl.glRotatef(angle, 1.0f, 1.0f, 1.0f);	
		// gl.glRotatef(angle, 0,10,0);
		gl.glBegin(gl.GL_QUADS); // Begin drawing the Quads with 4 triangles
		// Front
		gl.glColor3f(0.5f, 0.0f, 1.0f); // Purple
		gl.glVertex3f(0.0f + X_DIR, 1.0f + Y_DIR, 0.0f + Z_DIR);
		gl.glVertex3f(X_DIR - 1.0f, Y_DIR - 1.0f, 1.0f + Z_DIR);
		gl.glVertex3f(1.0f + X_DIR, Y_DIR - 1.0f, 1.0f + Z_DIR);

		// Right
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex3f(0.0f + X_DIR, 1.0f + Y_DIR, 0.0f + Z_DIR);
		gl.glVertex3f(1.0f + X_DIR, Y_DIR - 1.0f, 1.0f + Z_DIR);
		gl.glVertex3f(1.0f + X_DIR, Y_DIR - 1.0f, Z_DIR - 1.0f);

		// Back
		gl.glColor3f(0.0f, 1.0f, 0.0f); // green
		gl.glVertex3f(0.0f + X_DIR, 1.0f + Y_DIR, 0.0f + Z_DIR);
		gl.glVertex3f(1.0f + X_DIR, Y_DIR - 1.0f, Z_DIR - 1.0f);
		gl.glVertex3f(X_DIR - 1.0f, Y_DIR - 1.0f, Z_DIR - 1.0f);

		// Left
		gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow
		gl.glVertex3f(0.0f + X_DIR, 1.0f + Y_DIR, 0.0f + Z_DIR);
		gl.glVertex3f( X_DIR- 1.0f, Y_DIR - 1.0f, Z_DIR - 1.0f);
		gl.glVertex3f( X_DIR- 1.0f, Y_DIR - 1.0f, 1.0f + Z_DIR);
		gl.glEnd(); // Done drawing the pyramid
		gl.glPopMatrix();

	}

}
