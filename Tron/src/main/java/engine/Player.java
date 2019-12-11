package engine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;
import java.util.List;



public class Player {
    //   @SerializedName(value = "color")
    @Expose
    private String color;
    //  @SerializedName(value = "id")
    @Expose
    private final String ID;  //Can be string and parsed to be int, will decide later
    //  @SerializedName(value = "headPosition")
    @Expose
    private Point headPosition;
    //  @SerializedName(value = "tracer")
    @Expose
    private List<Point> tracer; //Will save the tracers on map, this will be used to check if the player killed somebody
    // @SerializedName(value = "turboAmount")
    @Expose
    private int turboAmount;
    //   @SerializedName(value = "numberOfKills")
    @Expose
    private int numberOfKills;
    //    @SerializedName(value = "timeElapsed")
    @Expose
    private double timeElapsed; //Survival time
    //    @SerializedName(value = "length")
    @Expose
    private int length;
    //    @SerializedName(value = "score")
    @Expose
    private double score;
    @SerializedName(value = "direction")
    @Expose
    private Direction currentDirection;
    @SerializedName(value = "isAlive")
    @Expose
    private boolean isAlive = true;

    private boolean turboFlag;
    private Game playingGame;

    private long startTime;

    public Player(String ID, String color) {
        this.color = color;
        this.ID = ID;
        this.tracer = new ArrayList<>();
        this.turboAmount = 3;
        this.numberOfKills = 0;
        this.timeElapsed = 0;
        this.length = 0;
        this.score = 0;
        this.currentDirection = Direction.UP;
    }

    public String getColor() {
        return color;
    }

    public String getID() {
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

    public void setTimeElapsed(double timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public int getLength() {
        return length;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Game getPlayingGame() {
        return playingGame;
    }

    public void setPlayingGame(Game playingGame) {
        this.playingGame = playingGame;
    }

    void setHeadPosition(Point position) {
        headPosition = position;
    }

    void increaseTurbo() {
        turboAmount++;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public boolean isTurboFlag() {
        return turboFlag;
    }

    public void setTurboFlag(boolean turboFlag) {
        this.turboFlag = turboFlag;
    }

    boolean movePlayer(Direction direction, GameMap map) {
        tracer.add(headPosition);
        if (this.currentDirection == Direction.UP) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                if (headPosition.getX() == 0) {


                    return false; // engine.Player dies by intersecting with upper border

                }

                int nextX = headPosition.getX() - 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(nextX, headPosition.getY());
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }
                //Won't change the currentDirection
                return true;

            }
            if (direction == Direction.LEFT) {
                if (headPosition.getY() == 0) {

                    // map.die(this); //engine.Player dies by intersecting with map border
                    return false;
                }

                int nextY = headPosition.getY() - 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(headPosition.getX(), nextY);
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }
                currentDirection = direction;
                return true;

            }
            if (direction == Direction.RIGHT) {
                if (headPosition.getY() == map.getWidth() - 1) {

                    return false;  //engine.Player dies by intersecting with map border

                }

                int nextY = headPosition.getY() + 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(headPosition.getX(), nextY);
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }
                currentDirection = direction;
                return true;

            }

        }

        if (this.currentDirection == Direction.RIGHT) {
            if (direction == Direction.RIGHT || direction == Direction.LEFT) {
                if (headPosition.getY() == map.getWidth() - 1) {

                    return false;   // engine.Player dies by intersecting with right border

                }
                int nextY = headPosition.getY() + 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(headPosition.getX(), nextY);
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }
                //Won't change the currentDirection
                return true;

            }

            if (direction == Direction.UP) {
                if (headPosition.getX() == 0) {

                    return false; // engine.Player dies by intersecting with upper border

                }

                int nextX = headPosition.getX() - 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(nextX, headPosition.getY());
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }
                currentDirection = direction;
                return true;


            }
            if (direction == Direction.DOWN) {
                if (headPosition.getX() == map.getHeight() - 1) {

                    return false;      // engine.Player dies by intersecting with upper border

                }

                int nextX = headPosition.getX() + 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(nextX, headPosition.getY());
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }
                currentDirection = direction;
                return true;


            }

        }
        if (currentDirection == Direction.LEFT) {
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                if (headPosition.getY() == 0) {

                    return false;    //engine.Player dies intersecting the left border

                }
                int nextY = headPosition.getY() - 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(headPosition.getX(), nextY);
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }

                return true;
            }
            if (direction == Direction.UP) {
                if (headPosition.getX() == 0) {

                    return false;  // engine.Player dies by intersecting with upper border

                }

                int nextX = headPosition.getX() - 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(nextX, headPosition.getY());
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }
                currentDirection = direction;
                return true;


            }
            if (direction == Direction.DOWN) {
                if (headPosition.getX() == map.getHeight() - 1) {

                    return false;   // engine.Player dies by intersecting with upper border

                }

                int nextX = headPosition.getX() + 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(nextX, headPosition.getY());
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }
                currentDirection = direction;
                return true;
            }
        }

        if (currentDirection == Direction.DOWN) {
            if (direction == Direction.DOWN || direction == Direction.UP) {
                if (headPosition.getX() == map.getHeight()) {

                    return false;   // engine.Player dies by intersecting with upper border

                }

                int nextX = headPosition.getX() + 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(nextX, headPosition.getY());
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }
                //Won't change the currentDirection
                return true;
            }
            if (direction == Direction.RIGHT) {
                if (headPosition.getY() == map.getWidth()) {

                    return false;  //engine.Player dies by intersecting with map border

                }

                int nextY = headPosition.getY() + 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(headPosition.getX(), nextY);
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }
                currentDirection = direction;
                return true;
            }
            if (direction == Direction.LEFT) {
                if (headPosition.getY() == 0) {

                    return false;     //engine.Player dies by intersecting with map border

                }

                int nextY = headPosition.getY() - 1;
                Point nextPoint = playingGame.getGameMap().getPointOnXY(headPosition.getX(), nextY);
                if (nextPoint != null) {
                    headPosition = nextPoint;
                    length++;
                }
                currentDirection = direction;
                return true;

            }
        }
        return false;
    }

    public List<Point> die() {
        isAlive = false;
        timeElapsed = (System.currentTimeMillis() - startTime) / 1000.0;
        return tracer;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


    public void kill() {
        numberOfKills++;

    }

}