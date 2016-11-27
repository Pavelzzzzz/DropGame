package info.fandroid.drop.version3;

import com.badlogic.gdx.utils.TimeUtils;

import info.fandroid.drop.version3.model.DropArray;
import info.fandroid.drop.version3.model.DropsAction;

/**
 * Created by Pavel on 27.11.16.
 */

public class DropDeclining {

    public static void update (DropsAction dropsAction){

        if (dropsAction.getDoubleSpeed()){
            dropsAction.getDropArray().setTimeDoubleSpeed(TimeUtils.nanoTime() + 10000000000L);
            dropsAction.getDropArray().setPreviousDropSpeed(dropsAction.getDropArray().getDropSpeed());
            dropsAction.getDropArray().setDropSpeed(dropsAction.getDropArray().getDropSpeed()/4);
            dropsAction.setDoubleSpeed(false);
        }

        if ((dropsAction.getDropArray().getTimeDoubleSpeed() < TimeUtils.nanoTime()) && dropsAction.getDoubleSpeedAdd()){
            dropsAction.getDropArray().setDropSpeed(dropsAction.getDropArray().getPreviousDropSpeed());
            dropsAction.setDoubleSpeedAdd(false);
        }

        if (dropsAction.getDropArray().getChangeSpeedTime() + 1000000000 < TimeUtils.nanoTime()){
            dropsAction.getDropArray().setDropSpeed(dropsAction.getDropArray().getDropSpeed() - 100000000);
            dropsAction.getDropArray().setChangeSpeedTime(TimeUtils.nanoTime());
        }
    }
}
