package info.fandroid.drop.version2;

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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;

import info.fandroid.drop.Drop;
import info.fandroid.drop.version2.model.DropArray;

public class GameScreen2 implements Screen {

    final Drop game;
    OrthographicCamera camera;
    Texture basketImage;
    Sound dropSound;
    Music rainMusic;
    Rectangle basket;
    Vector3 touchPos;
    long lastDropTime;
    int dropsGathered = 0;
    int dropsScore = 0;
    DropArray dropArray;

    public GameScreen2 (final Drop gam) {
        this.game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        touchPos = new Vector3();

        basketImage = new Texture("bucket_512.png");

        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

        rainMusic.setLooping(true);
        rainMusic.play();

        basket = new Rectangle();
        basket.x = 800 / 2 - 64 / 2;
        basket.y = 2;
        basket.width = 64;
        basket.height = 64;

        dropArray = new DropArray();
        OperationUnderDropArray.createDrop(dropArray);
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
        game.batch.draw(basketImage, basket.x, basket.y, 64, 64);
        for (int i = 0; i < dropArray.size(); i++){
            game.batch.draw(dropArray.getType(i).getImage(),
                    dropArray.getRectangle(i).x,
                    dropArray.getRectangle(i).y);
        }
        game.batch.end();

        if (Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            basket.x = (int) (touchPos.x - 64 / 2);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            basket.x -= 300* Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            basket.x += 300* Gdx.graphics.getDeltaTime();

        if (basket.x < 0)
            basket.x = 0;
        if (basket.x > 800 - 64)
            basket.x = 800 - 64;

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000){
            lastDropTime = TimeUtils.nanoTime();
            OperationUnderDropArray.createDrop(dropArray);
        }

        ArrayList<Integer> delArray = new ArrayList<Integer>();

        for (int i = 0; i < dropArray.size(); i++){
            dropArray.setY(i, (dropArray.getRectangle(i).y - 200 * Gdx.graphics.getDeltaTime()));
            if (dropArray.getRectangle(i).y + 64 < 0){
                delArray.add(i);
            }
            if (dropArray.getRectangle(i).overlaps(basket)){
                dropsGathered++;
                dropsScore += dropArray.getType(i).getScore();
                dropSound.play();
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
        basketImage.dispose();
    }
}