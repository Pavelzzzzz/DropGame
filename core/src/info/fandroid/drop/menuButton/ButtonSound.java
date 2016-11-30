package info.fandroid.drop.menuButton;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Pavel on 29.11.16.
 */

public class ButtonSound {

    private Texture buttonImageOn;
    private Texture buttonImageOff;
    private boolean soundOn;
    private Rectangle rectangle;

    public ButtonSound(int x, int y){
        buttonImageOn = new Texture("button/buttonSoundOn.png");
        buttonImageOff = new Texture("button/buttonSoundOff.png");
        soundOn = true;
        rectangle = new Rectangle(x , y, 32, 32);
    }

    public boolean contains (float x, float y){
        if (rectangle.contains(x, y)){
            return true;
        }else {
            return false;
        }
    }

    public Texture getButtonImage(){
        if (soundOn){
            return buttonImageOn;
        }else {
            return buttonImageOff;
        }
    }

    public void setSound(boolean bool){
        soundOn = bool;
    }

    public float[] getArrayData(){
        return new float []{rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight()};
    }

    public void dispose() {
        buttonImageOn.dispose();
        buttonImageOn.dispose();
    }
}
