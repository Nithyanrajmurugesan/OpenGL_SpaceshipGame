package game;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Shadow {

	public Shadow(GLAutoDrawable drawable, float X_DIR, float Y_DIR, float Z_DIR) {

		GL2 gl = drawable.getGL().getGL2();

		// gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT); // Clear
		// color and depth buffer

		gl.glPushMatrix();
		gl.glBegin(gl.GL_QUADS);

		gl.glColor3f(0.0f, 0.15f, 0.0f); // Green
		gl.glVertex3f(1.0f + X_DIR, -1.0f + Y_DIR, 1.0f + Z_DIR);
		gl.glVertex3f(-1.0f + X_DIR, -1.0f + Y_DIR, 1.0f + Z_DIR);
		gl.glVertex3f(-1.0f + X_DIR, -1.0f + Y_DIR, -1.0f + Z_DIR);
		gl.glVertex3f(1.0f + X_DIR, -1.0f + Y_DIR, -1.0f + Z_DIR);
		gl.glEnd();
		gl.glPopMatrix();

	}

}
