package src;

import src.brick_strategies.BrickStrategyFactory;
import src.brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.camChange;
import src.brick_strategies.extraPaddle;
import src.gameobjects.*;

import java.awt.event.KeyEvent;

public class BrickerGameManager extends GameManager {

    //windows
    /**
     * window width
     */
    private static final int WINDOW_WIDTH = 700;
    /**
     * window height
     */
    private static final int WINDOW_HEIGHT = 500;
    /**
     * window title
     */
    private static final String WINDOW_TITLE = "Bricker";

    //borders
    /**
     * Default border (wall) width: left, right and up
     */
    private static final int BORDER_WIDTH = 4;
    /**
     * representation of borders (null)
     */
    private static final Renderable BORDER_RENDERABLE = null;

    //ball
    /**
     * speed of ball playing in the game
     */
    private static final float BALL_SPEED = 300;

    //paddle
    /**
     * paddle width
     */
    private static final int PADDLE_WIDTH = 100;
    /**
     * paddle height
     */
    private static final int PADDLE_HEIGHT = 15;

    //bricks
    /**
     * lines of bricks
     */
    private static final int BRICKS_LINES = 8;
    /**
     * num of bricks in line
     */
    private static final int BRICKS_IN_LINE = 7;
    /**
     * height of bricks
     */
    private static final int BRICKS_HEIGHT = 15;
    /**
     * space of all bricks from borders
     */
    private static final int EDGES_SPACE = 30;

    //hearts
    /**
     * number of lives starting the game
     */
    private static final int LIVES_IN_GAME = 3;
    /**
     * width of hearts and the balls
     */
    private static final int HEART_WIDTH = 20;
    /**
     * to use when placing the  hearts
     */
    private static final int SPACING_HEART = 6;
    //assets paths
    /**
     * all paths for pics and sounds of objects. all in form (obj)_PIC\(obj)_SOUND
     */
    private static final String BALL_PIC = "assets/ball.png";
    private static final String BLOP_SOUND = "assets/blop_cut_silenced.wav";
    private static final String PADDLE_PIC = "assets/paddle.png";
    private static final String BRICK_PIC = "assets/brick.png";
    private static final String HEART_PIC = "assets/heart.png";
    private static final String BACKGROUND_PIC = "assets/DARK_BG2_small.jpeg";

    //messages
    /**
     * when winning
     */
    private static final String WIN_MSG = "You Win! ";
    /**
     * when loosing
     */
    private static final String LOSE_MSG = "You Lose! ";
    /**
     * Q if to play again
     */
    private static final String PLAY_AGAIN_MSG = "Play Again?";

    /**
     * main ball
     */
    private Ball ball;
    /**
     * window dimensions
     */
    private Vector2 windowDimensions;
    /**
     * window controller
     */
    private WindowController windowController;
    /**
     * bricks left in the game
     */
    private Counter brickCount;
    /**
     * lives of user left in the game
     */
    public Counter livesCounter;
    /**
     * user input listener
     */
    private UserInputListener inputListener;
    /**
     * image reader
     */
    private ImageReader imageReader;
    /**
     * sound reader
     */
    private SoundReader soundReader;
    /**
     * global game object collection
     */
    private GameObjectCollection gameObjectCollection;


    /**
     *This is the constructor of a brick game, which calls its super (GameManager)'s constructor
     *
     * @param windowTitle the title of the window
     * @param windowDimensions a 2d vector representing the height and width of the window
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * creates borders (walls) of the game screen according to instructions
     */
    private void createBorders() {
        gameObjects().addGameObject(new GameObject(
                Vector2.ZERO,
                new Vector2(BORDER_WIDTH, this.windowDimensions.y()),
                BORDER_RENDERABLE));
        gameObjects().addGameObject(new GameObject(
                new Vector2(this.windowDimensions.x() - BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, this.windowDimensions.y()),
                BORDER_RENDERABLE));
        gameObjects().addGameObject(new GameObject(
                Vector2.ZERO,
                new Vector2(this.windowDimensions.x(), BORDER_WIDTH),
                BORDER_RENDERABLE));
    }

    /**
     *  creates ball of the game according to instructions
     */
    private void createBall(){
        Renderable ballImage = this.imageReader.readImage(BALL_PIC, true);
        Sound collisionSound = this.soundReader.readSound(BLOP_SOUND);
        Ball ball = new Ball(Vector2.ZERO, new Vector2(HEART_WIDTH, HEART_WIDTH), ballImage, collisionSound);

        ball.setRandomVelocity();
        ball.setCenter(this.windowDimensions.mult(0.5F));
        this.gameObjects().addGameObject(ball);
        this.ball = ball;
    }

    public Ball getBall(){
        return this.ball;
    }

    /**
     * creates paddle of the game according to instructions
     */
    private void createPaddle(){
        Renderable paddleImage = this.imageReader.readImage(PADDLE_PIC, true);
        Paddle paddle = new Paddle(Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, this.inputListener, this.windowDimensions, BORDER_WIDTH);

        paddle.setCenter(new Vector2(this.windowDimensions.x() / 2, this.windowDimensions.y() - 30));
        this.gameObjects().addGameObject(paddle);
    }

    /**
     * creates bricks wall of the game according to instructions and inputs (bricks num and lines num)
     */
    private void createBricks(){

        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(gameObjectCollection,
                this,
                imageReader,
                soundReader,
                inputListener,
                windowController,
                windowDimensions);
        Renderable brickImage = this.imageReader.readImage(BRICK_PIC, false);

        int rowStart;
        int colStart= BRICKS_HEIGHT *2;
        int brickWidth = (int) ((this.windowDimensions.x()-EDGES_SPACE)/ BRICKS_IN_LINE);
        for (int i = 0; i < BRICKS_LINES; i++) {
            rowStart = EDGES_SPACE/2;
            for (int j = 0; j < BRICKS_IN_LINE; j++) {
                Brick brick = new Brick(new Vector2(rowStart, BRICKS_HEIGHT),
                        new Vector2(brickWidth, BRICKS_HEIGHT),
                        brickImage, brickStrategyFactory.getStrategy(), brickCount);
                brick.setTopLeftCorner(new Vector2(rowStart,  colStart));
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                rowStart += brickWidth+1;

            }
            colStart += BRICKS_HEIGHT +1;
        }

    }

    /**
     * creates graphic lives (in form of hearts) and numeric lives of the game according to instructions
     */
    private void createLives(){

        Renderable heartImage = this.imageReader.readImage(HEART_PIC, true);

        //Graphic Lives
        int rowStart = 0;
        int colStart= (int) this.windowDimensions.y()-HEART_WIDTH;
        GraphicLifeCounter hearts = new GraphicLifeCounter(new Vector2(0, colStart),
                new Vector2(HEART_WIDTH, this.windowDimensions.y()),
                livesCounter, heartImage, this.gameObjectCollection, livesCounter.value());
        gameObjects().addGameObject(hearts, Layer.BACKGROUND);


        //Numeric Lives
        NumericLifeCounter numHeart = new NumericLifeCounter(livesCounter, Vector2.ZERO,
                new Vector2(HEART_WIDTH, HEART_WIDTH), gameObjectCollection);
        numHeart.setTopLeftCorner(new Vector2(rowStart+(HEART_WIDTH*SPACING_HEART),
                colStart-SPACING_HEART));
        this.gameObjects().addGameObject(numHeart, Layer.BACKGROUND);

    }

    /**
     * adds the background of the game according to instructions
     */
    private void addBackground(){
        GameObject background = new GameObject(
                Vector2.ZERO,
                this.windowDimensions,
                this.imageReader.readImage(BACKGROUND_PIC, true));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * searches for current winning condition
     * @return true is currently there's a winning condition, else false
     */
    private boolean isWin() {

        return this.brickCount.value()<=0 || inputListener.isKeyPressed(KeyEvent.VK_W);
    }

    /**
     * checks for ending game condition: win/lose condition.
     * if ending condition applied - continues as the user decides
     */
    private void checkForGameEnd() {
        String msg="";
        double ballHeight = ball.getCenter().y();
        if (isWin()){
            msg = WIN_MSG;
        }
        if (windowDimensions.y()<ballHeight){
            this.livesCounter.decrement();
            gameObjectCollection.removeGameObject(this.ball);
            createBall();
            if (this.livesCounter.value()==0){
                msg = LOSE_MSG;
            }
        }
        if(!msg.isEmpty()){
            msg+= PLAY_AGAIN_MSG;
            if(windowController.openYesNoDialog(msg)){
                windowController.resetGame();
                extraPaddle.setFalseIsOn();
                this.setCamera(null);
            }
            else {
                windowController.closeWindow();
            }
        }
    }


    /**
     *
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.brickCount=new Counter();
        this.livesCounter =new Counter(LIVES_IN_GAME);
        this.gameObjectCollection = gameObjects();
//        GameObjectCollection gameObjectCollection = src.gameObjects();

        // creating wall
        createBorders();

        // creating ball
        createBall();

        // creating paddle
        createPaddle();

        // adding background
        addBackground();

        // creating bricks
        createBricks();

        // creating graphic lives
        createLives();

    }

    /**
     * gets the counter of hits to return to normal camera state
     * if there are no more hits to get it removed - sets the camera back to normal.
     */
    private void updateCam() {
        if (camChange.getHisCount()<=0){
            this.setCamera(null);
        }
    }

    /**
     * This method overrides the GameManager update method. It checks for game status,
     * and triggers a new game popup.
     *
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
        updateCam();
    }

    public static void main(String[] args) {
        new BrickerGameManager(WINDOW_TITLE, new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT)).run();
    }
}
