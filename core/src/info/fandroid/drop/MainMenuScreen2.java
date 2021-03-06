package info.fandroid.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Pavel on 29.11.16.
 */

public class MainMenuScreen2 implements Screen {

    final Drop game;

    private OrthographicCamera camera;
    private Button buttonPlay;
    private Button buttonResults;
    private Button buttonHelp;
    private Vector3 touchPoint;
    private boolean playRainMusic;
    private boolean playDropSound;
    private ButtonWithTwoPosition buttonSound;
    private ButtonWithTwoPosition buttonMusic;
    boolean stateClick;

    public MainMenuScreen2 (final Drop gam){
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        buttonPlay = new Button("button/buttonPlay.png", 240, 350, 320, 100);
        buttonResults = new Button("button/buttonResults.png", 240, 250, 320, 100);
        buttonHelp = new Button("button/buttonHelp.png", 240, 150, 320, 100);
        buttonSound = new ButtonWithTwoPosition("button/buttonSoundOn.png", "button/buttonSoundOff.png", 10, 10, 64, 64);
        buttonMusic = new ButtonWithTwoPosition("button/buttonMusicOn.png", "button/buttonMusicOff.png", 10, 94, 64, 64);
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
        game.batch.draw(buttonPlay.getButtonImage(), buttonPlay.getArrayData()[0], buttonPlay.getArrayData()[1]);
        game.batch.draw(buttonResults.getButtonImage(), buttonResults.getArrayData()[0], buttonResults.getArrayData()[1]);
        game.batch.draw(buttonHelp.getButtonImage(), buttonHelp.getArrayData()[0], buttonHelp.getArrayData()[1]);
        game.batch.draw(buttonSound.getButtonImage(), buttonSound.getArrayData()[0], buttonSound.getArrayData()[1],
                        buttonSound.getArrayData()[2], buttonSound.getArrayData()[3]);
        game.batch.draw(buttonMusic.getButtonImage(), buttonMusic.getArrayData()[0], buttonMusic.getArrayData()[1],
                        buttonMusic.getArrayData()[2], buttonMusic.getArrayData()[3]);
        game.batch.end();

        if (handlerForClickingTheButton()) {
            if (buttonPlay.contains(touchPoint.x, touchPoint.y)){
                this.dispose();
                game.setScreen(new info.fandroid.drop.version3.GameScreen3(game, playRainMusic, playDropSound));
            }

            if (buttonResults.contains(touchPoint.x, touchPoint.y)){
                this.dispose();
                game.setScreen(new info.fandroid.drop.ResultsScreen(game));
            }

            if (buttonSound.contains(touchPoint.x, touchPoint.y)){
                playDropSound = !playDropSound;
                buttonSound.setStatus(playDropSound);
            }

            if (buttonMusic.contains(touchPoint.x, touchPoint.y)){
                playRainMusic = !playRainMusic;
                buttonMusic.setStatus(playRainMusic);
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
        buttonResults.dispose();
        buttonHelp.dispose();
        buttonMusic.dispose();
        buttonSound.dispose();
    }
}