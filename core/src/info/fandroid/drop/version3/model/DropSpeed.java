package info.fandroid.drop.version3.model;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Pavel on 26.11.16.
 */

public class DropSpeed extends DropSimple {

    public DropSpeed(int i, Texture texture, DropsAction inputDropsAction) {
        super(i, texture, inputDropsAction);
    }

    @Override
    public void action(){
        dropsAction.setDoubleSpeed(true);
        dropsAction.setDoubleSpeedAdd(true);
    }
}
