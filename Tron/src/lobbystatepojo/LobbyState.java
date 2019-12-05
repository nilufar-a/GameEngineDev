
package engine.lobbystatepojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * LobbyState
 * <p>
 * Lobby of the game
 * 
 */
public class LobbyState {

    @SerializedName("Users")

    private List<User> users = null;
    /**
     * map of the game
     * 
     */
    @SerializedName("mapID")

    private String mapID;
    /**
     * number of all players in the game
     * 
     */
    @SerializedName("numberOfPlayers")

    private Integer numberOfPlayers;
    /**
     * number of AI1 players in the game
     * 
     */
    @SerializedName("numberOfAI1")
    private Integer numberOfAI1;
    /**
     * number of AI2 players in the game
     * 
     */
    @SerializedName("numberOfAI2")

    private Integer numberOfAI2;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * map of the game
     * 
     */
    public String getMapID() {
        return mapID;
    }

    /**
     * map of the game
     * 
     */
    public void setMapID(String mapID) {
        this.mapID = mapID;
    }

    /**
     * number of all players in the game
     * 
     */
    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * number of all players in the game
     * 
     */
    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * number of AI1 players in the game
     * 
     */
    public Integer getNumberOfAI1() {
        return numberOfAI1;
    }

    /**
     * number of AI1 players in the game
     * 
     */
    public void setNumberOfAI1(Integer numberOfAI1) {
        this.numberOfAI1 = numberOfAI1;
    }

    /**
     * number of AI2 players in the game
     * 
     */
    public Integer getNumberOfAI2() {
        return numberOfAI2;
    }

    /**
     * number of AI2 players in the game
     * 
     */
    public void setNumberOfAI2(Integer numberOfAI2) {
        this.numberOfAI2 = numberOfAI2;
    }

}
