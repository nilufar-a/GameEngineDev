import java.util.ArrayList;
import java.util.List;

public class GameMap {
 private int   width;
 private int   height;
 private int[][] mapMatrix;
 private List<Point>   obstacleLocations;
 private List<Point> powerUpLocations;
 private List<Player> playerList;
 private List<Point> tracerLocations;
 private List<Point> allPointsOnMap;
 private List<Player> deadPlayers;


 public GameMap(int[][] mapMatrix,List<Player> Player,List<Point> obstacleLocations,List<Point>powerUpLocations) {
  allPointsOnMap=new ArrayList<>();
  this.width = mapMatrix[0].length;
  this.height = mapMatrix.length;
  for(int i=0;i<width;i++){
   System.arraycopy(mapMatrix[i], 0, this.mapMatrix[i], 0, height); //handle null later
  }
  this.obstacleLocations=obstacleLocations;
  this.powerUpLocations=powerUpLocations;
  for(int i=0;i<width;i++){
   for (int j=0;j<height;j++){
    allPointsOnMap.add(new Point(i,j));
   }
  }
  for (Point point: allPointsOnMap
       ) {
   if(powerUpLocations.contains(point)){
    point.setState(State.POWERUP);
   }
   else if(obstacleLocations.contains(point)){
    point.setState(State.OBSTACLE);
   }
   else point.setState(State.EMPTY);
  }
  deadPlayers=new ArrayList<>();
 }



 public int getWidth() {
  return width;
 }

 public void setWidth(int width) {
  this.width = width;
 }

 public int getHeight() {
  return height;
 }

 public void setHeight(int height) {
  this.height = height;
 }

 public int[][] getMapMatrix() {
  return mapMatrix;
 }

 public void setMapMatrix(int[][] mapMatrix) {
  this.mapMatrix = mapMatrix;
 }

 public List<Point> getObstacleLocations() {
  return obstacleLocations;
 }

 public void setObstacleLocations(List<Point> obstacleLocations) {
  this.obstacleLocations = obstacleLocations;
 }

 public List<Point> getPowerUpLocations() {
  return powerUpLocations;
 }

 public void setPowerUpLocations(List<Point> powerUpLocations) {
  this.powerUpLocations = powerUpLocations;
 }

 public List<Player> getPlayerList() {
  return playerList;
 }

 public void setPlayerList(List<Player> playerList) {
  this.playerList = playerList;
 }

 public List<Point> getTracerLocations() {
  return tracerLocations;
 }

 public void setTracerLocations(List<Point> tracerLocations) {
  this.tracerLocations = tracerLocations;
 }

 public List<Point> getAllPointsOnMap() {
  return allPointsOnMap;
 }

 public void setAllPointsOnMap(List<Point> allPointsOnMap) {
  this.allPointsOnMap = allPointsOnMap;
 }

 public List<Player> getDeadPlayers() {
  return deadPlayers;
 }

 public void setDeadPlayers(List<Player> deadPlayers) {
  this.deadPlayers = deadPlayers;
 }

 public void move(Direction direction, Player player){
  tracerLocations.add(player.getHeadPosition());// check later
  if(!player.movePlayer(direction,this)){ //this function will move the player regardless of true or false
   die(player);
   return;
  }

  if(player.getHeadPosition().getState()==State.EMPTY){
   mapMatrix[player.getHeadPosition().getX()][player.getHeadPosition().getY()]=player.getID();
   player.getHeadPosition().setState(State.TRACER);
   return;
  }
  if(player.getHeadPosition().getState()==State.POWERUP){
   if(player.getTurboAmount()<=3){
    player.increaseTurbo();
   }
   mapMatrix[player.getHeadPosition().getX()][player.getHeadPosition().getY()]=player.getID();
   player.getHeadPosition().setState(State.TRACER);
   return;
  }
  if(player.getHeadPosition().getState()==State.OBSTACLE){
   die(player);
   return;
  }
  if(player.getHeadPosition().getState()==State.TRACER){
   die(player);
  }


 }

 public void die(Player player){ //Update it with turns so player will be out of the game
  List<Point> deadTracer=player.die();
  for (Point p:deadTracer
       ) {
   mapMatrix[p.getX()][p.getY()]=0;
   tracerLocations.remove(p);
   playerList.remove(player);
   deadPlayers.add(player);//Will use to get  who killed who
   //Need to implement score stuff and return here
  }
 }




}







