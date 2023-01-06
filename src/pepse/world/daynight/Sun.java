package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.Color;

public class Sun {
    private static final int SUN_SIZE = 100;
    private static final float FINAL_ANGLE = 360;
    private static final float SUN_Y_RELATIVE_LOCATION = (float) 1.5;
    private static final String TAG_NAME = "sun";

    private static Vector2 calcSunPosition(Vector2 windowDimensions, float angleInSky){
        Vector2 rotatedVector = Vector2.UP.mult(windowDimensions.y() / 2).rotated(angleInSky);
        float x = (rotatedVector.x() * windowDimensions.x() / windowDimensions.y()) +
                (windowDimensions.x() / 2);
        float y = rotatedVector.y() + windowDimensions.y() / SUN_Y_RELATIVE_LOCATION;
        return new Vector2(x, y);
    }

    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer,
            Vector2 windowDimensions,
            float cycleLength){
        Renderable sunCircle = new OvalRenderable(ColorSupplier.approximateColor(Color.YELLOW));
        GameObject sun = new GameObject(
                Vector2.ZERO,
                new Vector2(SUN_SIZE, SUN_SIZE),
                sunCircle
        );
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(sun, layer);
        sun.setTag(TAG_NAME);
        Transition<Float> transition = new Transition<>(
                sun,
                angle -> sun.setCenter(calcSunPosition(windowDimensions, angle)),
                (float) 0,
                FINAL_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength * 2,
                Transition.TransitionType.TRANSITION_LOOP,
                null);
        return sun;
    }
}
