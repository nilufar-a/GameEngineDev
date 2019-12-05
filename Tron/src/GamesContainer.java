/*package engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engine.Direction;
import engine.Game;
import engine.GameMap;
import engine.Player;
import engine.lobbystatepojo.LobbyState;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Path("/")
public class GamesContainer {
    private static GamesContainer instance;

    public static GamesContainer getInstance() {
        if (instance == null) {
            return new GamesContainer();
        } else {
            return instance;
        }
    }

    private ArrayList<Game> games = new ArrayList<>();
    private ArrayList<Player> allPlayers = new ArrayList<>();

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/CreateGame")
    public void createGame(@QueryParam("GameID") int gameID) throws IOException {
        URL matchMakingURL = new URL("https://match-making-dot-trainingprojectlab2019.appspot.com/GetLobbyState?GameID=" + gameID);
        HttpURLConnection matchMakingConnection = (HttpURLConnection) matchMakingURL.openConnection();
        matchMakingConnection.setRequestMethod("GET");
        matchMakingConnection.connect();
        int matchMakingURLResponse = matchMakingConnection.getResponseCode();
        if (matchMakingURLResponse != 200) {
            throw new RuntimeException("HTTPResponseCode:" + matchMakingURLResponse);
        }

        BufferedReader lobbyStateJson = new BufferedReader(new InputStreamReader(matchMakingConnection.getInputStream()));
        LobbyState lobbyState = new Gson().fromJson(lobbyStateJson, LobbyState.class);


        URL mapEditorURL = new URL("https://mapeditor-dot-trainingprojectlab2019.appspot.com/GetMap?MapID=" + lobbyState.getMapID());
        HttpURLConnection mapEditorConnection = (HttpURLConnection) mapEditorURL.openConnection();
        mapEditorConnection.setRequestMethod("GET");
        mapEditorConnection.connect();
        int mapEditorURLResponse = mapEditorConnection.getResponseCode();
        if (mapEditorURLResponse != 200) {
            throw new RuntimeException("HTTPResponseCode:" + mapEditorURLResponse);
        }
        BufferedReader mapObjectJson = new BufferedReader(new InputStreamReader(mapEditorConnection.getInputStream()));
        MapObject mapObject = new Gson().fromJson(mapObjectJson, MapObject.class);

        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < lobbyState.getNumberOfPlayers(); i++) {
            Player player = new Player(lobbyState.getUsers().get(i).getUserID(), lobbyState.getUsers().get(i).getColor());
            players.add(player);
        }
        GameMap gameMap = new GameMap(mapObject.mapMatrix, players);
        Game createdGame = new Game(gameID, gameMap, players);

        for (Player player : players
        ) {
            player.setPlayingGame(createdGame);
        }
        allPlayers.addAll(players);
        games.add(createdGame);
    }

    @GET
    @Path("/getCurrentStateOfModel")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCurrentStateOfModel(@QueryParam("GameID") int GameID) {
        Game currentGame = null;
        String currentStateJSON;
        for (Game game : games
        ) {
            if (game.getGameID() == GameID) {
                currentGame = game;
                break;
            }
        }
        if (currentGame != null) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            currentStateJSON = gson.toJson(currentGame);
            return currentStateJSON;
        }
        return null;
    }

    @POST
    @Path("/postMove")
    @Produces(MediaType.TEXT_HTML)
    public void postMove(@QueryParam("Direction") Direction direction, @QueryParam("UserID") String userID, @QueryParam("TurboFlag") boolean turboFlag) {
        Game game = null;
        Player player = null;
        for (Player p : allPlayers
        ) {
            if (p.getID().equals(userID)) {
                player = p;
                game = player.getPlayingGame();
                break;
            }
        }
        if(game!=null){
            game.move(direction,player,turboFlag);
        }
    }
}
*/
