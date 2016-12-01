package info.fandroid.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import info.fandroid.drop.menuButton.ButtonPlay;
import info.fandroid.drop.menuButton.ButtonSound;

/**
 * Created by Pavel on 29.11.16.
 */

public class MainMenuScreen2 implements Screen {

    final Drop game;

    private OrthographicCamera camera;
    private ButtonPlay buttonPlay;
    private Vector3 touchPoint;
    private boolean playRainMusic;
    private boolean playDropSound;
    private ButtonSound buttonSound;
    boolean stateClick;

    public MainMenuScreen2 (final Drop gam){
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        buttonPlay = new ButtonPlay(240, 350);
        buttonSound = new ButtonSound(10, 10);
        touchPoint = new Vector3();
        stateClick = false;
        playRainMusic = true;
        playDropSound = true;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Welcome to GameScreen!", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
//        game.font.draw(game.batch, "Touch  " + touchPoint.x + "  " + touchPoint.y, 100, 300);
//        game.font.draw(game.batch, "Button play  " + buttonPlay.getArrayData()[0] + "  "
//                + buttonPlay.getArrayData()[1] + "  "
//                + buttonPlay.getArrayData()[2] + "  "
//                + buttonPlay.getArrayData()[3] + "  ", 100, 250);
//        game.font.draw(game.batch, "Button sound  " + buttonSound.getArrayData()[0]  + "  "
//                + buttonSound.getArrayData()[1]  + "  "
//                + buttonSound.getArrayData()[2]  + "  "
//                + buttonSound.getArrayData()[3]  + "  ", 100, 200);
        game.batch.draw(buttonPlay.getButtonImage(), buttonPlay.getArrayData()[0], buttonPlay.getArrayData()[1]);
        game.batch.draw(buttonSound.getButtonImage(), buttonSound.getArrayData()[0], buttonSound.getArrayData()[1]);
        game.batch.end();

        if (handlerForClickingTheButton()) {
            if (buttonPlay.contains(touchPoint.x, touchPoint.y)){
                this.dispose();
                game.setScreen(new info.fandroid.drop.version3.GameScreen3(game, playRainMusic, playDropSound));
            }

            if (buttonSound.contains(touchPoint.x, touchPoint.y)){
                playRainMusic = !playRainMusic;
                buttonSound.setSound(playRainMusic);
            }
        }
    }

    private boolean handlerForClickingTheButton(){
        if (Gdx.input.isTouched()){
            touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPoint);
            stateClick = true;
        } else {
            if (stateClick){
                stateClick = false;
                return true;
            }
        }
        return false;
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
    public void dispose() {
        buttonPlay.dispose();
        buttonSound.dispose();
    }
}