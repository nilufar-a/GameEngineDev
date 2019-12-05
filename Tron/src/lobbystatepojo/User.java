
package engine.lobbystatepojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * User
 * <p>
 * stores the user data
 * 
 */
public class User {

    /**
     * ID number of user
     * 
     */
    @SerializedName("userID")

    private String userID;
    /**
     * state of user
     * 
     */
    @SerializedName("userState")

    private String userState;
    /**
     * color of user's tracer
     * 
     */
    @SerializedName("color")

    private String color;

    /**
     * ID number of user
     * 
     */
    public String getUserID() {
        return userID;
    }

    /**
     * ID number of user
     * 
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * state of user
     * 
     */
    public String getUserState() {
        return userState;
    }

    /**
     * state of user
     * 
     */
    public void setUserState(String userState) {
        this.userState = userState;
    }

    /**
     * color of user's tracer
     * 
     */
    public String getColor() {
        return color;
    }

    /**
     * color of user's tracer
     * 
     */
    public void setColor(String color) {
        this.color = color;
    }

}
