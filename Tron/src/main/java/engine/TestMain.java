//package engine;
//
//public class TestMain {
//    public static void main(String[] args) throws InterruptedException {
//        String[][] matrix = new String[10][10];
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++){
//                matrix[i][j]="EMPTY";
//            }
//        }
//
//        matrix[7][3]="SPAWN";
//        matrix[7][1]="SPAWN";
//
//        Player player1=new Player("adad","xezer");
//        Player player2=new Player("adsd","murad");
//        //Player player3=new Player("addsad","3");
//        //Player player4=new Player("ad","4");
//        Player player5=new Player("ada","5");
//        Player player6=new Player("dsad","6");
//        Player player7=new Player("da","7");
//        Player player8=new Player("R","8");
//        ArrayList<Player> playerArrayList1= new ArrayList<>();
//        playerArrayList1.add(player1);
//        playerArrayList1.add(player2);
//        //playerArrayList1.add(player3);
//        //playerArrayList1.add(player4);
//        ArrayList<Player> playerArrayList2= new ArrayList<>();
//        playerArrayList2.add(player5);
//        playerArrayList2.add(player6);
//        playerArrayList2.add(player7);
//        playerArrayList2.add(player8);
//        GameMap gameMap1= new GameMap(matrix,playerArrayList1);
//        GameMap gameMap2= new GameMap(matrix,playerArrayList2);
//        Game game1 = new Game(1,gameMap1,playerArrayList1);
//        //Game game2 = new Game(2,gameMap2,playerArrayList2);
//
//       // game1.move(Direction.UP, player1, false);
//       // game1.move(Direction.DOWN, player1, false);
//       // game1.move(Direction.RIGHT, player1, false);
//        //game2.move(Direction.DOWN,player5,false);
//        //game1.move(Direction.LEFT, player1, false);
//        Thread.sleep(10000);
//        game1.move(Direction.RIGHT,player1,false);
//        game1.move(Direction.LEFT, player2, false);
//        Thread.sleep(500);
//        //System.out.println(game1.getTimeToUpdate());
//        Thread.sleep(1500);
//        //game1.move(Direction.RIGHT, player3, false);
//
//    }
//}
