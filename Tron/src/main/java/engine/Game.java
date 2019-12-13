package engine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Game extends Thread {
    @SerializedName(value = "GameID")
    @Expose
    private int gameID;
    @SerializedName(value = "Map")
    @Expose
    private GameMap gameMap;
    @SerializedName(value = "players")
    @Expose
    private ArrayList<Player> listOfPlayers;
    @Expose
    private double timeToUpdate;
    private Thread thread;
    private boolean gameStarted;

    @SerializedName(value = "gameFinished")
    @Expose
    private boolean gameFinished;

    @SerializedName(value = "turnCounter")
    @Expose
    private int turnCounter;

    private Runnable taskTurn;
    private ScheduledFuture<?> scheduledFuture;

    private Direction lastDirection;
    private boolean turboFlag;

    public Game(int gameID, GameMap gameMap, ArrayList<Player> listOfPlayers) {
        this.gameID = gameID;
        this.gameMap = gameMap;
        this.gameMap.setGame(this);
        this.listOfPlayers = listOfPlayers;
        for (Player p: this.listOfPlayers
        ) {
            p.setPlayingGame(this);
        }
        timeToUpdate = 3.00;
        gameFinished = false;
        thread = new Thread(this,Integer.toString(gameID));
        thread.start();
    }

    public void run(){
        try {
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

            taskTurn = () -> {
                turnCounter++;
                ArrayList<Player> temp= (ArrayList<Player>) listOfPlayers.clone();
                for (Player player : temp
                ) {
                    if(player.getNextDirectionToPlay()==null){
                        player.setNextDirectionToPlay(player.getCurrentDirection());
                    }
                    gameMap.move(player.getNextDirectionToPlay(), player, player.isTurboFlag());
                }
                System.out.println(gameMap.toString());
            };

            //Time for synchronization
            Thread.sleep(10000);
            gameStarted=true;

            //set start time for each player
            for (Player player: listOfPlayers
                 ) {
                player.setStartTime(System.currentTimeMillis());
            }

            long turnInterval=(long)gameMap.getTurnInterval()*1000;
            scheduledFuture = ses.scheduleAtFixedRate(taskTurn, turnInterval, turnInterval, TimeUnit.MILLISECONDS);
            while (!gameFinished) {
                timeToUpdate = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
            }
            scheduledFuture.cancel(false);
            Thread.sleep(120000);
        }
        catch (Exception ex){
        }
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }


    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public void setListOfPlayers(ArrayList<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public double getTimeToUpdate() {
        if(scheduledFuture!=null && !scheduledFuture.isCancelled())
            return scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
        else
            return 0;
    }

    public void setTimeToUpdate(double timeToUpdate) {
        this.timeToUpdate = timeToUpdate;
    }


    public void move(Direction direction, Player player, boolean turboFlag) {
        if(gameStarted) {
            player.setNextDirectionToPlay(direction);
            player.setTurboFlag(turboFlag);
        }
    }
}