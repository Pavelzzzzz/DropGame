package info.fandroid.drop.version2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import info.fandroid.drop.version2.model.DropArray;
import info.fandroid.drop.version2.model.SimpleDrop;

/**
 * Created by Pavel on 26.11.16.
 */

public class OperationUnderDropArray {

    public static void createDrop(DropArray dropArray) {

        Rectangle rainDrop = new Rectangle();
        rainDrop.x = MathUtils.random(0, 800 - 64);
        rainDrop.y = 480;
        rainDrop.width = 64;
        rainDrop.height = 64;

        if (dropArray.DropCount % 20 == 0) {
            dropArray.set(rainDrop, new SimpleDrop(50, new Texture("dropTransparentGreen.png")));
        } else if (dropArray.DropCount % 15 == 0) {
            dropArray.set(rainDrop, new SimpleDrop(25, new Texture("dropLightGreen.png")));
        } else if (dropArray.DropCount % 10 == 0) {
            dropArray.set(rainDrop, new SimpleDrop(20, new Texture("dropPurple.png")));
        } else if (dropArray.DropCount % 5 == 0) {
            dropArray.set(rainDrop, new SimpleDrop(10, new Texture("dropOrange.png")));
        } else {
            dropArray.set(rainDrop, new SimpleDrop(1, new Texture("dropBlue.png")));
        }
        dropArray.DropCount++;
    }
}




