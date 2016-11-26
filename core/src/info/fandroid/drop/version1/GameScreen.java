package info.fandroid.drop.version1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

import info.fandroid.drop.Drop;

public class GameScreen implements Screen {

	final Drop game;
	OrthographicCamera camera;
	Texture dropImage;
	Texture basketImage;
	Sound dropSound;
	Music rainMusic;
	Rectangle basket;
	Vector3 touchPos;
	Array<Rectangle> raindrops;
	long lastDropTime;
	int dropsGathered;

	public GameScreen (final Drop gam) {
		this.game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		touchPos = new Vector3();

		dropImage = new Texture("dropBlue.png");
		basketImage = new Texture("bucket.png");

		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		basket = new Rectangle();
		basket.x = 800 / 2 - 64 / 2;
		basket.y = 2;
		basket.width = 64;
		basket.height = 64;

		raindrops = new Array<Rectangle>();
		spawnRainDrop();
	}

	private void spawnRainDrop(){
		Rectangle rainDrop = new Rectangle();
		rainDrop.x = MathUtils.random(0, 800 - 64);
		rainDrop.y = 480;
		rainDrop.width = 64;
		rainDrop.height = 64;
		raindrops.add(rainDrop);
		lastDropTime = TimeUtils.nanoTime();
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
		game.batch.draw(basketImage, basket.x, basket.y);
		for (Rectangle rainFrop: raindrops){
			game.batch.draw(dropImage, rainFrop.x, rainFrop.y);
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

		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRainDrop();

		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()){
			Rectangle rainDrop = iter.next();
			rainDrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (rainDrop.y + 64 < 0)
				iter.remove();
			if (rainDrop.overlaps(basket)){
				dropsGathered++;
				dropSound.play();
				iter.remove();
			}
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
		dropImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		basketImage.dispose();
	}
}