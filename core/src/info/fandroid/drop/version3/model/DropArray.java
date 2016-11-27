package info.fandroid.drop.version3.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Pavel on 26.11.16.
 */

public class DropArray {

    public int DropCount;
    private Array<Rectangle> rectDrops;
    private Array<DropSimple> typeDrops;
    private long dropSpeed;
    private long previousDropSpeed;
    private long changeSpeedTime;
    private long timeDoubleSpeed;

    public DropArray(){
        typeDrops = new Array<DropSimple>();
        rectDrops = new Array<Rectangle>();
        dropSpeed = 1000000000;
        changeSpeedTime = TimeUtils.nanoTime();
    }

    public int size(){
        return rectDrops.size > typeDrops.size ? typeDrops.size : rectDrops.size;
    }

    public void set(Rectangle rect, DropSimple drop){
        rectDrops.add(rect);
        typeDrops.add(drop);
    }

    public Rectangle getRectangle(int ind){
        return rectDrops.get(ind);
    }

    public DropSimple getType(int ind){
        return typeDrops.get(ind);
    }

    public void setY(int ind, float y){
        rectDrops.get(ind).setY(y);
    }

    public long getDropSpeed(){
        return dropSpeed;
    }

    public long getPreviousDropSpeed(){
        return previousDropSpeed;
    }

    public long getChangeSpeedTime() {
        return changeSpeedTime;
    }

    public long getTimeDoubleSpeed() {
        return timeDoubleSpeed;
    }

    public void setTimeDoubleSpeed(long time) {
        timeDoubleSpeed = time;
    }

    public void setDropSpeed(long speed){
        dropSpeed = speed;
    }

    public void setChangeSpeedTime(long time){
        changeSpeedTime = time;
    }

    public void setPreviousDropSpeed(long speed){
        previousDropSpeed = speed;
    }

    public void remove(int ind){
        typeDrops.removeIndex(ind);
        rectDrops.removeIndex(ind);
    }

}
