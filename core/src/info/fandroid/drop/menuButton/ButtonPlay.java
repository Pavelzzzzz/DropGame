package info.fandroid.drop.menuButton;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Pavel on 29.11.16.
 */

public class ButtonPlay {

    Texture buttonImage;
    Rectangle rectangle;

    public ButtonPlay(int x, int y){
        buttonImage = new Texture("button/buttonPlay.png");
        rectangle = new Rectangle(x , y, 320, 100);
    }

    public Texture getButtonImage(){
        return buttonImage;
    }

    public float[] getArrayData(){
        return new float []{rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight()};
    }

    public boolean contains (float x, float y){
        if (rectangle.contains(x, y)){
            return true;
        }else {
            return false;
        }
    }

    public void dispose() {
        buttonImage.dispose();
    }
}
