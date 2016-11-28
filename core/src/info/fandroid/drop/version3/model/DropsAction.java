package info.fandroid.drop.version3.model;

import info.fandroid.drop.version3.DropDeclining;
import info.fandroid.drop.version3.DropsCreateSpeed;

/**
 * Created by Pavel on 27.11.16.
 */

public class DropsAction {

    DropDeclining dropDeclining;
    Basket basket;
    DropsCreateSpeed dropsCreateSpeed;

    public DropsAction(Basket inputbasket, DropDeclining inputDropDeclining, DropsCreateSpeed inputDropsCreateSpeed){
        basket = inputbasket;
        dropDeclining = inputDropDeclining;
        dropsCreateSpeed = inputDropsCreateSpeed;
    }

    public void speedAdd(){
        dropDeclining.setAddDropSpeed();
    }

    public void changeCreateSpeed(){
        dropsCreateSpeed.setQuicklyCreateSpeedTime();
    }

    public void basin(){
        basket.setBasinTime();
    }
}