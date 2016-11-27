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
import info.fandroid.drop.version3.model.DropArray;
import info.fandroid.drop.version3.model.Basket;
import info.fandroid.drop.version3.model.DropCreator;
import info.fandroid.drop.version3.model.DropsAction;

public class GameScreen3 implements Screen {

    final Drop game;
    OrthographicCamera camera;
    Sound dropSound;
    Music rainMusic;
    Vector3 touchPos;
    long lastDropTime;
    int dropsGathered = 0;
    int dropsScore = 0;
    Basket basket;
    DropsAction dropsAction;
    DropArray dropArray;
    DropCreator creator;


    public GameScreen3 (final Drop gam) {
        this.game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        touchPos = new Vector3();

        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

        rainMusic.setLooping(true);
        rainMusic.play();

        basket = new Basket();
        dropArray = new DropArray();
        dropsAction = new DropsAction(dropArray, basket);
        creator = new DropCreator(dropsAction);
        creator.createDrop(dropArray);
    }

    @Override
    public void show() {
        rainMusic.play();
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
        game.batch.draw(basket.getImage(), basket.getRectangleX(), basket.getRectangleY(), basket.rectangleWidth(), basket.rectangleHeight());
        for (int i = 0; i < dropArray.size(); i++){
            game.batch.draw(dropArray.getType(i).getImage(),
                    dropArray.getRectangle(i).x,
                    dropArray.getRectangle(i).y);
        }
        game.batch.end();

        basket.update();

        if (Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            basket.setRectangleX((int) (touchPos.x - 64 / 2));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            basket.setRectangleX(basket.getRectangleX() - (300* Gdx.graphics.getDeltaTime()));
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            basket.setRectangleX(basket.getRectangleX() + (300* Gdx.graphics.getDeltaTime()));

        if (basket.getRectangleX() < 0)
            basket.setRectangleX(0);
        if (basket.getRectangleX() > 800 - basket.rectangleWidth())
            basket.setRectangleX(800 - basket.rectangleWidth());

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000){
            lastDropTime = TimeUtils.nanoTime();
            creator.createDrop(dropArray);
        }

        ArrayList<Integer> delArray = new ArrayList<Integer>();

        for (int i = 0; i < dropArray.size(); i++){
            dropArray.setY(i, (dropArray.getRectangle(i).y - 200 * Gdx.graphics.getDeltaTime()));
            if (dropArray.getRectangle(i).y + 64 < 0){
                delArray.add(i);
            }
            if (basket.overlaps(dropArray.getRectangle(i))){
                dropsGathered++;
                dropsScore += dropArray.getType(i).getScore();
                dropSound.play();
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