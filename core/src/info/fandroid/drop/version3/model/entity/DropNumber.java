package info.fandroid.drop.version3.model.entity;

import com.badlogic.gdx.graphics.Texture;

import info.fandroid.drop.version3.model.*;

/**
 * Created by Pavel on 28.11.16.
 */

public class DropNumber extends DropSimple {


    public DropNumber(int i, Texture texture, DropsAction inputDropsAction) {
        super(i, texture, inputDropsAction);
    }

    @Override
    public void action() {
        dropsAction.changeCreateSpeed();
    }
}

