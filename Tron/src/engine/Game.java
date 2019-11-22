package engine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
    private boolean gameFinished;
    private Player currentPlayer;

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
        currentPlayer = listOfPlayers.get(0);
        thread = new Thread(this,Integer.toString(gameID));
        thread.start();
        System.out.println(this.gameMap.toString());
    }

    public void run(){
        System.out.println(gameID+" is created......");
        AtomicInteger playerCounter= new AtomicInteger(0);
        currentPlayer = listOfPlayers.get(playerCounter.get());
        System.out.println(gameID + ": Turn of "+currentPlayer.getID());
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        taskTurn = () -> {
            gameMap.move(currentPlayer.getCurrentDirection(),currentPlayer, turboFlag);
            //System.out.println(gameID +": "+ currentPlayer.getID()+" moved in the direction of "+currentPlayer.getCurrentDirection().toString());
            System.out.println(this.gameMap.toString());
            playerCounter.getAndIncrement();
            if(playerCounter.get()>=listOfPlayers.size()){
                playerCounter.set(0);
            }
            currentPlayer = listOfPlayers.get(playerCounter.get());
            //System.out.println("Turn of "+currentPlayer.getID());
        };

        scheduledFuture = ses.scheduleAtFixedRate(taskTurn,1500,1500, TimeUnit.MILLISECONDS);
        while(!gameFinished){
            timeToUpdate=scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
        }
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
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
        if(player.getID().equals(currentPlayer.getID())){
            player.setCurrentDirection(direction);
            player.setTurboFlag(turboFlag);
        }
    }
}
