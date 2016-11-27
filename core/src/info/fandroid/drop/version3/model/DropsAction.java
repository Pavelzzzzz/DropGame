package info.fandroid.drop.version3.model;

/**
 * Created by Pavel on 27.11.16.
 */

public class DropsAction {

    DropArray dropArray;
    Basket basket;
    boolean doubleSpeed;
    boolean doubleSpeedAdd;

    public DropsAction(DropArray inputDropArray, Basket inputbasket){
        dropArray = inputDropArray;
        doubleSpeed = false;
        basket = inputbasket;
    }

    public void setDoubleSpeed(boolean bool){
        doubleSpeed = bool;
    }

    public void setDoubleSpeedAdd(boolean bool){
        doubleSpeedAdd = bool;
    }

    public DropArray getDropArray(){
        return dropArray;
    }

    public boolean getDoubleSpeed(){
        return doubleSpeed;
    }

    public boolean getDoubleSpeedAdd(){
        return doubleSpeedAdd;
    }

    public void basin(){
        basket.setBasinTime();
    }
}
