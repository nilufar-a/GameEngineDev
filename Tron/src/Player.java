import java.util.ArrayList;
import java.util.List;

enum  Direction{
    LEFT,RIGHT,UP,DOWN
}

public class Player {
    private String color;
    private final int ID;  //Can be string and parsed to be int, will decide later
    private Point headPosition;
    private List<Point> tracer; //Will save the tracers on map, this will be used to check if the player killed somebody
    private int turboAmount;
    private int numberOfKills;
    private double timeElapsed; //Survival time
    private int length;
    private double score;
    private Direction currentDirection;


    public Player(String color, int ID) {
        this.color = color;
        this.ID = ID;
        this.tracer=new ArrayList<>();
        this.turboAmount=3;
        this.numberOfKills=0;
        this.timeElapsed=0;
        this.length=0;
        this.score=0;
    }

    public String getColor() {
        return color;
    }

    public int getID() {
        return ID;
    }

    public Point getHeadPosition() {
        return headPosition;
    }

    public List<Point> getTracer() {
        return tracer;
    }

    public int getTurboAmount() {
        return turboAmount;
    }

    public int getNumberOfKills() {
        return numberOfKills;
    }

    public double getTimeElapsed() {
        return timeElapsed;
    }

    public int getLength() {
        return length;
    }

    public double getScore() {
        return score;
    }
    void increaseTurbo(){
        turboAmount++;
    }

    boolean movePlayer(Direction direction, GameMap map){
        tracer.add(headPosition);
        if(this.currentDirection==Direction.UP){
             if(direction==Direction.UP || direction==Direction.DOWN){
               if(headPosition.getX()==0){

                  return false; // Player dies by intersecting with upper border

               }

                int nextX=headPosition.getX();
                headPosition.setX(nextX-1);
                length++;
                //Won't change the currentDirection
                return true;

             }
           if(direction==Direction.LEFT){
               if(headPosition.getY()==0){

                   map.die(this); //Player dies by intersecting with map border

               }

               int nextY=headPosition.getY();
               headPosition.setY(nextY-1);
               currentDirection=direction;
               length++;
               return true;

           }
           if (direction==Direction.RIGHT){
                    if(headPosition.getY()==map.getWidth()-1){

                        return false;  //Player dies by intersecting with map border

                    }

                    int nextY=headPosition.getY();
                    headPosition.setY(nextY+1);
                    currentDirection=direction;
                    length++;
                    return true;

           }

        }

        if(this.currentDirection==Direction.RIGHT){
            if (direction==Direction.RIGHT ||direction==Direction.LEFT){
                if(headPosition.getY()==map.getWidth()-1){

                    return false;   // Player dies by intersecting with right border

                }
                int nextY=headPosition.getY();
                headPosition.setY(nextY+1);
                //Won't change the currentDirection
                length++;
                return true;

            }

            if (direction== Direction.UP){
                if(headPosition.getX()==0){

                    return false; // Player dies by intersecting with upper border

                }

                int nextX=headPosition.getX();
                headPosition.setX(nextX-1);
                currentDirection=direction;
                length++;
                return true;


            }
            if(direction== Direction.DOWN){
                if(headPosition.getX()==map.getHeight()-1){

                    return false;      // Player dies by intersecting with upper border

                }

                int nextX=headPosition.getX();
                headPosition.setX(nextX+1);
                currentDirection=direction;
                length++;
                return true;


            }

        }
        if(currentDirection==Direction.LEFT){
              if (direction==Direction.LEFT ||direction==Direction.RIGHT){
                  if(headPosition.getY()==0){

                      return false;    //Player dies intersecting the left border

                  }

              }
              if(direction== Direction.UP){
                  if(headPosition.getX()==0){

                      return false;  // Player dies by intersecting with upper border

                  }

                  int nextX=headPosition.getX();
                  headPosition.setX(nextX-1);
                  currentDirection=direction;
                  length++;
                  return true;


              }
              if(direction== Direction.DOWN){
                  if(headPosition.getX()==map.getHeight()-1){

                      return false;   // Player dies by intersecting with upper border

                  }

                  int nextX=headPosition.getX();
                  headPosition.setX(nextX+1);
                  currentDirection=direction;
                  length++;
                  return true;
              }
        }

        if(currentDirection== Direction.DOWN){
            if (direction==Direction.DOWN || direction==Direction.UP){
                if(headPosition.getX()==map.getHeight()){

                    return false;   // Player dies by intersecting with upper border

                }

                int nextX=headPosition.getX();
                headPosition.setX(nextX+1);
                //Won't change the currentDirection
                length++;
                return true;
            }
            if (direction== Direction.RIGHT)
            {
                if(headPosition.getY()==map.getWidth()){

                    return false;  //Player dies by intersecting with map border

                }

                int nextY=headPosition.getY();
                headPosition.setY(nextY+1);
                currentDirection=direction;
                length++;
                return true;
            }
            if (direction== Direction.LEFT)
            {
                if(headPosition.getY()==0){

                    return false;     //Player dies by intersecting with map border

                }

                int nextY=headPosition.getY();
                headPosition.setY(nextY-1);
                currentDirection=direction;
                length++;
                return true;

            }
        }
         return false;
    }

    public List<Point> die(){
        tracer.add(headPosition);
        return tracer;
    }
   public boolean intersect(Point point){
        if (point.getState()==State.OBSTACLE || point.getState()== State.TRACER) {
            return true;
        }
        return false;
   }
}






