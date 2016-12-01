package info.fandroid.drop.version3;

/**
 * Created by Pavel on 26.11.16.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

import info.fandroid.drop.Drop;
import info.fandroid.drop.GameOver;
import info.fandroid.drop.MainMenuScreen;
import info.fandroid.drop.version3.model.DropArray;
import info.fandroid.drop.version3.model.Basket;
import info.fandroid.drop.version3.model.DropCreator;
import info.fandroid.drop.version3.model.DropsAction;

public class GameScreen3 implements Screen {

    final Drop game;
    private OrthographicCamera camera;
    private Sound dropSound;
    private Music rainMusic;
    private Vector3 touchPos;
    private long lastDropTime;
    private long dropsGathered;
    private long dropsScore;
    private int life;
    private Basket basket;
    private DropsAction dropsAction;
    private DropArray dropArray;
    private DropCreator creator;
    private DropDeclining dropDeclining;
    private DropsCreateSpeed dropsCreateSpeed;
    private boolean playDropSound;

    public GameScreen3 (final Drop gam, boolean inputPlayRainMusic, boolean inputPlayDropSound) {
        this.game = gam;
        life = 10;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        touchPos = new Vector3();
        playDropSound = inputPlayDropSound;

        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));

        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

        if (inputPlayRainMusic) {
            rainMusic.setLooping(true);
            rainMusic.play();
        }

        basket = new Basket();

        dropArray = new DropArray();
        dropDeclining = new DropDeclining();
        dropsCreateSpeed = new DropsCreateSpeed();
        dropsAction = new DropsAction(basket, dropDeclining, dropsCreateSpeed);

        creator = new DropCreator(dropsAction);
    }

    @Override
    public void show() {
        //rainMusic.play();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 10, 470);
        game.font.draw(game.batch, "Score: " + dropsScore, 10, 450);
        game.font.draw(game.batch, "Create time: " + dropsCreateSpeed.getCreateSpeedTime()/100000, 10, 430);
        game.font.draw(game.batch, "Speed: " + dropDeclining.getDropSpeed(), 10, 410);
        game.font.draw(game.batch, "Life: " + life, 10, 390);
        //game.font.draw(game.batch, "Touch  " + touchPos.x + "  " + touchPos.y, 10, 370);

        game.batch.draw(basket.getImage(), basket.getRectangleX(), basket.getRectangleY(), basket.rectangleWidth(), basket.rectangleHeight());
        for (int i = 0; i < dropArray.size(); i++){
            game.batch.draw(dropArray.getType(i).getImage(),
                    dropArray.getRectangle(i).x,
                    dropArray.getRectangle(i).y);
        }

        game.batch.end();
        dropDeclining.update();
        basket.update();
        dropsCreateSpeed.update();


        if (Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            basket.setRectangleX((int) (touchPos.x - basket.rectangleWidth() / 2));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            basket.setRectangleX(basket.getRectangleX() - (300* Gdx.graphics.getDeltaTime()));
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            basket.setRectangleX(basket.getRectangleX() + (300* Gdx.graphics.getDeltaTime()));

        if (basket.getRectangleX() < 0)
            basket.setRectangleX(0);
        if (basket.getRectangleX() > 800 - basket.rectangleWidth())
            basket.setRectangleX(800 - basket.rectangleWidth());

        if (TimeUtils.nanoTime() - lastDropTime > dropsCreateSpeed.getCreateSpeedTime()){
            lastDropTime = TimeUtils.nanoTime();
            creator.createDrop(dropArray);
        }

        ArrayList<Integer> delArray = new ArrayList<Integer>();

        for (int i = 0; i < dropArray.size(); i++){
            dropArray.setY(i, (dropArray.getRectangle(i).y - dropDeclining.getDropSpeed() * Gdx.graphics.getDeltaTime()));
            if (dropArray.getRectangle(i).y + 64 < 0){
                if (life-- < 1){
                    this.dispose();
                    long speed =new Long(dropsCreateSpeed.getCreateSpeedTime()/100000);
                    game.setScreen(new GameOver(game, new int []{(int)dropsGathered, (int)dropsScore, (int)speed,  dropDeclining.getDropSpeed()}));
                }
                delArray.add(i);
            }
            if (basket.overlaps(dropArray.getRectangle(i))){
                dropsGathered++;
                dropsScore += dropArray.getType(i).getScore();
                if (playDropSound){
                    dropSound.play();
                }
                dropArray.getType(i).action();
                delArray.add(i);
            }
        }
        Iterator<Integer> iter = delArray.iterator();
        while (iter.hasNext()){
            dropArray.remove(iter.next());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        dropSound.dispose();
        rainMusic.dispose();
        basket.dispose();
        creator.dispose();

    }
}