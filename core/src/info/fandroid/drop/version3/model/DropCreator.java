package info.fandroid.drop.version3.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import info.fandroid.drop.version3.model.entity.DropSimple;
import info.fandroid.drop.version3.model.entity.DropBasket;
import info.fandroid.drop.version3.model.entity.DropNumber;
import info.fandroid.drop.version3.model.entity.DropSpeed;


/**
 * Created by Pavel on 26.11.16.
 */

public class DropCreator {

    private DropSimple array[];



    public DropCreator (DropsAction dropsAction){
        array = new DropSimple[]{new DropSimple(1, new Texture("dropBlue.png"), dropsAction),
                new DropSpeed(25, new Texture("dropPurple.png"), dropsAction),
                new DropBasket(10, new Texture("dropTransparentGreen.png"), dropsAction),
                new DropNumber(25, new Texture("dropOrange.png"), dropsAction)};
    }

    public void createDrop(DropArray dropArray) {

        Rectangle rainDrop = new Rectangle();
        rainDrop.x = MathUtils.random(0, 800 - 64);
        rainDrop.y = 480;
        rainDrop.width = 64;
        rainDrop.height = 64;

        if (dropArray.DropCount % 15 == 0){
            int randomX = MathUtils.random.nextInt(3);
            dropArray.set(rainDrop, array[randomX + 1]);
        } else {dropArray.set(rainDrop, array[0]);}

        dropArray.DropCount++;
    }

    public void dispose(){
        for (DropSimple current: array) {
            current.dispose();
        }
    }
    }
