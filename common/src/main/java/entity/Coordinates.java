package entity;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private int x;
    private int y; //Максимальное значение поля: 884

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
    public String toString(){
        return getX() +","+getY();
    }
}
