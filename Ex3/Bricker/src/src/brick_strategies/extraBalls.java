package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;


public class extraBalls extends removeBrick{

    /**
     * num of puck balls to add when collision with brick
     */
    private static final int PUCK_BALLS = 3;
    /**
     * width of hearts and the balls
     */
    private static final int BALL_WIDTH = 22;
    /**
     * ball img
     */
    private static final String PUCK_BALL_PNG = "assets/mockBall.png";
    /**
     * ball sound on collision
     */
    private static final String BLOP_SOUND = "assets/blop_cut_silenced.wav";

    /**
     * ImageReader for ball\mock_ball pic
     */
    private final ImageReader imageReader;
    /**
     * SoundReader for sound on collision
     */
    private final SoundReader soundReader;

    /**
     * constructor
     * @param gameObjectCollection global game object collection
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     * @param soundReader Contains a single method: readSound, which reads a wav file from disk.
     */
    public extraBalls(GameObjectCollection gameObjectCollection,
                      ImageReader imageReader,
                      SoundReader soundReader){
        super(gameObjectCollection);
        this.imageReader = imageReader;
        this.soundReader = soundReader;

    }

    /**
     * Add puck ball to game on collision and delegate to held CollisionStrategy.
     * Made for adding 1 ball - but can also uncomment the part for 3 balls if wanted.
     * @param thisObj the object that was collided (the brick)
     * @param otherObj the object that collided with the brick (the ball).
     * @param bricksCounter bricks left in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter bricksCounter) {

        super.onCollision(thisObj, otherObj, bricksCounter);

        Renderable puckBallImage = this.imageReader.readImage(PUCK_BALL_PNG, true);
        Sound collisionSound = this.soundReader.readSound(BLOP_SOUND);

//        to be documented if 3 puck balls wanted
        Ball puckBall = new Ball(Vector2.ZERO,
                new Vector2(BALL_WIDTH, BALL_WIDTH),
                puckBallImage,
                collisionSound);
        puckBall.setRandomVelocity();
        puckBall.setCenter(thisObj.getCenter());
        super.getGameObjectCollection().addGameObject(puckBall);

////        use if 3 balls are wanted - NOT RECOMMENDED
//        Vector2 curCenter = new Vector2(thisObj.getCenter().x()-BALL_WIDTH, thisObj.getCenter().y()-BALL_WIDTH);
//        for (int i = 0; i < PUCK_BALLS; i++) {
//            Ball puckBall = new Ball(thisObj.getTopLeftCorner(),
//                    new Vector2(BALL_WIDTH, BALL_WIDTH),
//                    puckBallImage,
//                    collisionSound);
//            puckBall.setRandomVelocity();
//            puckBall.setCenter(curCenter);
//            curCenter = new Vector2(curCenter.x()+BALL_WIDTH, curCenter.y()+BALL_WIDTH);
//            super.getGameObjectCollection().addGameObject(puckBall);
//        }
    }

    /**
     * All collision strategy objects should hold a reference to the global game object collection
     * and be able to return it.
     * @return global game object collection whose reference is held in object.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return super.getGameObjectCollection();
    }
}
