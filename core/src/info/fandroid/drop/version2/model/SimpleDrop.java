package info.fandroid.drop.version2.model;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Pavel on 26.11.16.
 */

public class SimpleDrop {

    int score;
    Texture dropImage;

    public SimpleDrop(int i, Texture texture) {
        score = i;
        dropImage = texture;
    }

    public void SimpleDrop(int scor, Texture Image){
        score = scor;
        dropImage = Image;
    }

    public int getScore (){
        return score;
    }

    public Texture getImage(){
        return dropImage;
    }

    public void dispose() {
        dropImage.dispose();
    }
}
