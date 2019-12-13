package engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import engine.lobbystatepojo.LobbyState;
import engine.mappojo.HeadPosition;
import engine.mappojo.MapObject;
import engine.mappojo.Obstacle;
import engine.mappojo.PowerUp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GamesContainer {
    private static GamesContainer instance;

    public static GamesContainer getInstance() {
        if (instance == null) {
            return new GamesContainer();
        } else {
            return instance;
        }
    }

    private static ArrayList<Game> games = new ArrayList<>();
    private static ArrayList<Player> allPlayers = new ArrayList<>();


    public boolean createGame(int gameID) throws IOException {
        boolean flag = true;
        try {
            URL matchMakingURL = new URL("https://match-making-dot-trainingprojectlab2019.appspot.com//MatchMaking/GetLobbyState?GameID=" + gameID);
            HttpURLConnection matchMakingConnection = (HttpURLConnection) matchMakingURL.openConnection();
            matchMakingConnection.setRequestMethod("GET");
            matchMakingConnection.connect();
            int matchMakingURLResponse = matchMakingConnection.getResponseCode();
            if (matchMakingURLResponse != 200) {
                throw new RuntimeException("HTTPResponseCode:" + matchMakingURLResponse);
            }

            BufferedReader lobbyStateJson = new BufferedReader(new InputStreamReader(matchMakingConnection.getInputStream()));
            LobbyState lobbyState = new Gson().fromJson(lobbyStateJson, LobbyState.class);


            URL mapEditorURL = new URL("https://mapeditor-dot-trainingprojectlab2019.appspot.com/getMap?name=" + lobbyState.getMapID());
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
                Player player = new Player(lobbyState.getUsers().get(i).getUserID(),"."); //lobbyState.getUsers().get(i).getColor().toString());
                players.add(player);
            }

            // initializing map matrix
            String[][] mapMatrix = new String[mapObject.getMap().getHeight()][mapObject.getMap().getWidth()];
            for (int i = 0; i < mapMatrix.length; i++) {
                for (int j = 0; j < mapMatrix[0].length; j++) {
                    mapMatrix[i][j] = "EMPTY";
                }
            }

            //placing obstacles
            List<Obstacle> obstacles = mapObject.getMap().getObstacles();
            for (Obstacle obstacle : obstacles
            ) {
                mapMatrix[obstacle.getX()][obstacle.getY()] = "OBSTACLE";
            }

            //placing power-ups
            List<PowerUp> powerUps = mapObject.getMap().getPowerUps();
            for (PowerUp powerup : powerUps
            ) {
                mapMatrix[powerup.getX()][powerup.getY()] = "POWERUP";
            }

            //placing head positions
            List<HeadPosition> headPositions = new ArrayList<>();
            for (engine.mappojo.Player player : mapObject.getPlayers()
            ) {
                headPositions.add(player.getHeadPosition());
            }
            for (HeadPosition headPosition : headPositions
            ) {
                mapMatrix[headPosition.getX()][headPosition.getY()] = "SPAWN";
            }
            double turnInterval = lobbyState.getTurnInterval();
            boolean cycleBehaviour = lobbyState.getCycleBehaviour();
            //initial directions because someone messed up with map editor
            players.get(0).setCurrentDirection(Direction.LEFT);
            players.get(1).setCurrentDirection(Direction.DOWN);
            players.get(2).setCurrentDirection(Direction.UP);
            players.get(3).setCurrentDirection(Direction.RIGHT);
            GameMap gameMap = new GameMap(mapMatrix, players, turnInterval, cycleBehaviour);
            Game createdGame = new Game(gameID, gameMap, players);


            for (Player player : players
            ) {
                player.setPlayingGame(createdGame);
            }
            allPlayers.addAll(players);
            games.add(createdGame);
        } catch (Exception ex) {
            flag = false;
        }
        return flag;
    }


    public String getCurrentStateOfModel(int GameID) {
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


    public boolean postMove(Direction direction, String userID, boolean turboFlag) {
        boolean flag = true;
        Game game = null;
        Player player = null;
        for (Player p : allPlayers
        ) {
            if (p.getID().equals(userID)) {
                player = p;
                game = player.getPlayingGame();
                break;
            } else {
                flag = false;
            }
        }
        if (game != null) {
            game.move(direction, player, turboFlag);
        } else {
            flag = false;
        }


        return flag;
    }

    public boolean createGameMock() throws IOException {
        boolean flag = true;
        try {
            String json = "{\"map\":{\"width\":\"20\",\n" +
                    "\t\"height\":\"20\",\n" +
                    "\t\"name\":\"New Map\",\n" +
                    "\t\"obstacles\":[{\"x\":16,\"y\":3},{\"x\":15,\"y\":4},{\"x\":14,\"y\":5},{\"x\":13,\"y\":6},{\"x\":12,\"y\":7},{\"x\":11,\"y\":8},{\"x\":8,\"y\":11},{\"x\":7,\"y\":12},{\"x\":6,\"y\":13},{\"x\":5,\"y\":14},{\"x\":4,\"y\":15},{\"x\":3,\"y\":16}],\n" +
                    "\t\"power-ups\":[{\"x\":3,\"y\":3},{\"x\":16,\"y\":16}]},\n" +
                    "\t\"players\":[{\"headPosition\":{\"x\":9,\"y\":9},\"lookDirection\":\"LEFT\"},{\"headPosition\":{\"x\":10,\"y\":9},\"lookDirection\":\"UP\"},{\"headPosition\":{\"x\":9,\"y\":10},\"lookDirection\":\"DOWN\"},{\"headPosition\":{\"x\":10,\"y\":10},\"lookDirection\":\"RIGHT\"}]";
            Gson gson = new Gson();

            MapObject mapObject = gson.fromJson(json, MapObject.class);

            String[][] mapMatrix = new String[mapObject.getMap().getHeight()][mapObject.getMap().getWidth()];
            for (int i = 0; i < mapMatrix.length; i++) {
                for (int j = 0; j < mapMatrix[0].length; j++) {
                    mapMatrix[i][j] = "EMPTY";
                }
            }

            //placing obstacles
            List<Obstacle> obstacles = mapObject.getMap().getObstacles();
            for (Obstacle obstacle : obstacles
            ) {
                mapMatrix[obstacle.getX()][obstacle.getY()] = "OBSTACLE";
            }

            //placing power-ups
            List<PowerUp> powerUps = mapObject.getMap().getPowerUps();
            for (PowerUp powerup : powerUps
            ) {
                mapMatrix[powerup.getX()][powerup.getY()] = "POWERUP";
            }

            //placing head positions
            List<HeadPosition> headPositions = new ArrayList<>();
            for (engine.mappojo.Player player : mapObject.getPlayers()
            ) {
                headPositions.add(player.getHeadPosition());
            }
            for (HeadPosition headPosition : headPositions
            ) {
                mapMatrix[headPosition.getX()][headPosition.getY()] = "SPAWN";
            }


            URL matchMakingURL = new URL("https://match-making-dot-trainingprojectlab2019.appspot.com//MatchMaking/GetLobbyState?GameID=-1");
            HttpURLConnection matchMakingConnection = (HttpURLConnection) matchMakingURL.openConnection();
            matchMakingConnection.setRequestMethod("GET");
            matchMakingConnection.connect();
            int matchMakingURLResponse = matchMakingConnection.getResponseCode();
            if (matchMakingURLResponse != 200) {
                throw new RuntimeException("HTTPResponseCode:" + matchMakingURLResponse);
            }
            BufferedReader lobbyStateJson = new BufferedReader(new InputStreamReader(matchMakingConnection.getInputStream()));
            LobbyState lobbyState = new Gson().fromJson(lobbyStateJson, LobbyState.class);

            ArrayList<Player> players = new ArrayList<>();
            for (int i = 0; i < lobbyState.getNumberOfPlayers(); i++) {
                Player player = new Player(lobbyState.getUsers().get(i).getUserID(), lobbyState.getUsers().get(i).getColor().toString());
                players.add(player);
            }

            //setting the Initial directions
            players.get(0).setCurrentDirection(Direction.LEFT);
            players.get(1).setCurrentDirection(Direction.DOWN);
            players.get(2).setCurrentDirection(Direction.UP);
            players.get(3).setCurrentDirection(Direction.RIGHT);
            double turnInterval = lobbyState.getTurnInterval();
            boolean cycleBehaviour = lobbyState.getCycleBehaviour();
            GameMap gameMap = new GameMap(mapMatrix, players, turnInterval, cycleBehaviour);
            Game createdGame = new Game(-1, gameMap, players);

            for (Player player : players
            ) {
                player.setPlayingGame(createdGame);
            }
            allPlayers.addAll(players);
            games.add(createdGame);
        }catch (Exception ex){
            flag=false;
        }
        return flag;
    }

}

