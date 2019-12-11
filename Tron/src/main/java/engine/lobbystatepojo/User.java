
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
    @Expose
    private String userID;
    /**
     * state of user
     * 
     */
    @SerializedName("userState")
    @Expose
    private String userState;
    /**
     * name
     * 
     */
    @SerializedName("color")
    @Expose
    private Object color;

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
     * name
     * 
     */
    public Object getColor() {
        return color;
    }

    /**
     * name
     * 
     */
    public void setColor(Object color) {
        this.color = color;
    }

}
