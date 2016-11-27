package info.fandroid.drop.version3.model;

import info.fandroid.drop.version3.DropDeclining;

/**
 * Created by Pavel on 27.11.16.
 */

public class DropsAction {

    DropDeclining dropDeclining;
    Basket basket;

    public DropsAction(Basket inputbasket, DropDeclining inoutDropDeclining){
        basket = inputbasket;
        dropDeclining = inoutDropDeclining;
    }

    public void speedAdd(){
        dropDeclining.setAddDropSpeed();
    }

    public void basin(){
        basket.setBasinTime();
    }
}