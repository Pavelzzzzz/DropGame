package info.fandroid.drop.version3.model;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Pavel on 27.11.16.
 */

public class DropBasket extends DropSimple {

    public DropBasket(int i, Texture texture, DropsAction inputDropsAction) {
        super(i, texture, inputDropsAction);
    }

    @Override
    public void action(){
        dropsAction.basin();
    }
}
