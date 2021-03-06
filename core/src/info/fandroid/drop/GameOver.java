package info.fandroid.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * Created by Pavel on 30.11.16.
 */

public class GameOver implements Screen {

    final Drop game;
    int resultArray[];
    private Vector3 touchPoint;
    boolean stateClick;
    private Button buttonPlay;
    private Button buttonArrow;

    OrthographicCamera camera;

    public GameOver (final Drop gam, int inputResultArray[]){
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        resultArray = inputResultArray;
        buttonPlay = new Button("button/buttonPlay.png",240, 350, 320, 100);
        buttonArrow = new Button("button/arrow.png", 10, 10, 64, 64);
        touchPoint = new Vector3();
        Score score = new Score();
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        sb.append(Integer.toString(inputResultArray[0]));
        sb.append("  ");
        sb.append(Integer.toString(inputResultArray[2]));
        sb.append("  ");
        sb.append(Integer.toString(inputResultArray[3]));
        sb.append('\n');
        score.setScore(new String[]{Integer.toString(inputResultArray[1]), sb.toString()});
        score.writeToFile();
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
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.font.draw(game.batch, "Drops Collected: " + resultArray[0], 100, 400);
        game.font.draw(game.batch, "Score: " + resultArray[1], 100, 380);
        game.font.draw(game.batch, "Create time: " + resultArray[2], 100, 360);
        game.font.draw(game.batch, "Speed: " + resultArray[3], 100, 340);
        game.batch.draw(buttonPlay.getButtonImage(), buttonPlay.getArrayData()[0], buttonPlay.getArrayData()[1]);
        game.batch.draw(buttonArrow.getButtonImage(), buttonArrow.getArrayData()[0], buttonArrow.getArrayData()[1],
                buttonArrow.getArrayData()[2], buttonArrow.getArrayData()[3]);
        game.batch.end();

        if (handlerForClickingTheButton()) {
            if (buttonPlay.contains(touchPoint.x, touchPoint.y)){
                this.dispose();
                game.setScreen(new info.fandroid.drop.version3.GameScreen3(game, true, true));
            }
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
        buttonPlay.dispose();
        buttonArrow.dispose();
    }


}
