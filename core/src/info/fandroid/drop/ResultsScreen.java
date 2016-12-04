package info.fandroid.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;


/**
 * Created by Pavel on 04.12.16.
 */

public class ResultsScreen implements Screen {

    final Drop game;
    private Button buttonArrow;
    private Vector3 touchPoint;
    private boolean stateClick;
    private String results;

    OrthographicCamera camera;

    public ResultsScreen(Drop game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        touchPoint = new Vector3();
        buttonArrow = new Button("button/arrow.png", 10, 10, 64, 64);
        Score score = new Score();
        results = score.getString();
    }

    @Override
    public void show() {

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
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, results, 240, 350);
        game.batch.draw(buttonArrow.getButtonImage(), buttonArrow.getArrayData()[0], buttonArrow.getArrayData()[1],
                        buttonArrow.getArrayData()[2], buttonArrow.getArrayData()[3]);
        game.batch.end();

        if (handlerForClickingTheButton()) {
            if (buttonArrow.contains(touchPoint.x, touchPoint.y)){
                this.dispose();
                game.setScreen(new info.fandroid.drop.MainMenuScreen2(game));
            }}
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
        buttonArrow.dispose();
    }
}
