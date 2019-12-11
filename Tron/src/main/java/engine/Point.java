package engine;

import com.google.gson.annotations.Expose;

enum State {
    EMPTY, POWERUP, OBSTACLE, TRACER
}
//0 empty , -1 powerup, -2 obstacle, -3 tracer


public class Point {

    @Expose
    private int x;
    @Expose
    private int y;
    @Expose
    private State state;

    public Point(int x, int y, String state) {
        this.x = x;
        this.y = y;
        switch (state) {
            case "POWERUP":
                setState(State.POWERUP);
                break;
            case "OBSTACLE":
                setState(State.OBSTACLE);
                break;
            case "SPAWN":
                setState(State.TRACER);
                break;
            default:
                setState(State.EMPTY);
                break;
        }
    }


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point getPoint() {

        return this;

    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
