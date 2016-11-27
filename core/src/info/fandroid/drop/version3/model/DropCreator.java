package info.fandroid.drop.version3.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by Pavel on 26.11.16.
 */

public class DropCreator {

    DropSimple dropSimple;
    DropSpeed dropSpeed;
    DropBasket dropBasket;

    public DropCreator (DropsAction dropsAction){
        dropSimple = new DropSimple(1, new Texture("dropBlue.png"), dropsAction);
        dropSpeed = new DropSpeed(10, new Texture("dropPurple.png"), dropsAction);
        dropBasket = new DropBasket(10, new Texture("dropTransparentGreen.png"), dropsAction);
    }

    public void createDrop(DropArray dropArray) {

        Rectangle rainDrop = new Rectangle();
        rainDrop.x = MathUtils.random(0, 800 - 64);
        rainDrop.y = 480;
        rainDrop.width = 64;
        rainDrop.height = 64;

        if (dropArray.DropCount % 20 == 0) {
            dropArray.set(rainDrop, dropSpeed);
        } else if (dropArray.DropCount % 15 == 0) {
            dropArray.set(rainDrop, dropBasket);
        } else {
            dropArray.set(rainDrop, dropSimple);
        }
        dropArray.DropCount++;
    }

    public void dispose(){
        dropSimple.dispose();
        dropSpeed.dispose();
        dropBasket.dispose();
    }
    }
