package Case;

import javax.vecmath.*;

import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;

import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;

public class Case extends Applet {
	public static void main(String[] args) {
		new MainFrame(new Case(), 800, 800);
	}

	public void init() {
		// create canvas
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);
		setLayout(new BorderLayout());
		add(cv, BorderLayout.CENTER);
		BranchGroup bg = createSceneGraph();
		bg.compile();
		SimpleUniverse su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();
		//Skjermbevegelse
		OrbitBehavior orbit = new OrbitBehavior(cv);
	    orbit.setSchedulingBounds(new BoundingSphere());
	    su.getViewingPlatform().setViewPlatformBehavior(orbit);
		
	    su.addBranchGraph(bg);
	}
	
	Shape3D[]         shape;
	TransformGroup[]  shapeScale;
	TransformGroup[]  shapeMove;

	private BranchGroup createSceneGraph() {
		int n = 11;
		
		/* root */
		BranchGroup root = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere();
		
		/* testTransform */
		Transform3D tr = new Transform3D();
		tr.setTranslation(new Vector3f(0.1f, 0.1f, 0.1f));
		TransformGroup testTransform = new TransformGroup(tr);
		testTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		root.addChild(testTransform);
		
		/* background and lights */
		Background background = new Background(1.0f, 1.0f, 1.0f);
		background.setApplicationBounds(bounds);
		root.addChild(background);
		AmbientLight light = new AmbientLight(true, new Color3f(Color.white));
		light.setInfluencingBounds(bounds);
		root.addChild(light);
		PointLight ptlight = new PointLight(new Color3f(Color.BLUE),
				new Point3f(3f, 3f, 3f), new Point3f(1f, 0f, 0f));
		ptlight.setInfluencingBounds(bounds);
		root.addChild(ptlight);

		SharedGroup sg = new SharedGroup();
		// object
		for (int i = 0; i < n; i++) {
			Transform3D tr1 = new Transform3D();
			tr1.setRotation(new AxisAngle4d(0, 1, 0, 2 * Math.PI
					* ((double) i / n)));
			TransformGroup tgNew = new TransformGroup(tr1);
			Link link = new Link();
			link.setSharedGroup(sg);
			tgNew.addChild(link);
			tg.addChild(tgNew);

		}

		Shape3D torus1 = new Torus(0.1, 0.7);
		Appearance ap = new Appearance();
		ap.setMaterial(new Material());
		torus1.setAppearance(ap);
		tg.addChild(torus1);

		Shape3D torus2 = new Torus(0.1, 0.4);
		ap = new Appearance();
		ap.setMaterial(new Material());
		ap.setTransparencyAttributes(new TransparencyAttributes(
				TransparencyAttributes.BLENDED, 0.0f));
		torus2.setAppearance(ap);
		Transform3D tr2 = new Transform3D();
		tr2.setRotation(new AxisAngle4d(1, 0, 0, Math.PI / 2));
		tr2.setTranslation(new Vector3d(0.8, 0, 0));

		TransformGroup tg2 = new TransformGroup(tr2);
		tg2.addChild(torus2);

		// SharedGroup
		sg.addChild(tg2);

		Alpha alpha = new Alpha(0, 8000);
		RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
		rotator.setSchedulingBounds(bounds);
		spin.addChild(rotator);

		
		return root;
	}
}