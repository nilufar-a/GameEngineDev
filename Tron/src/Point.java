
enum State{
    EMPTY, POWERUP , OBSTACLE, TRACER
}
//0 empty , -1 powerup, -2 obstacle, -3 tracer


public class Point {

  private  int x;
  private  int y;
  State state;

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

    public Point getPoint(){

        return this;

    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
