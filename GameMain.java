import java.util.Scanner;

import javax.swing.Icon;

public class GameMain {

   private Board board;
   private State currentState;
   private Seed currentPlayer;
   private static Scanner in =new Scanner(System.in);

   public GameMain(){
    // ejecuta las tareas de inicializacion
    initGame();
    // resetea al tablero
    newGame();

    do{

        // el jugador actual hace un movimiento 
        // actualiza la casillas y el estado actual
        stepGame();
        // refresta el display
        board.paint();
        if(currentState == State.CROSS_WON){
            System.out.println("'X' won!\nBye");
        }else if(currentState == State.NOUGHT_WON){
            System.out.println("'0' won!\nBye");
        }else if(currentState == State.DRAW){
            System.out.println("'It's Draw \nBye");
        }
        // switch currentplayer
        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT: Seed.CROSS;
    }while(currentState == State.PLAYING); // repita hasta el game over
   }

   public void initGame(){
    board = new Board(); // asigna el tablero
   }

   public void newGame(){
    board.newGame();
    currentPlayer = Seed.CROSS;
    currentState = State.PLAYING;
   }


   public void stepGame() {
    boolean validINput = false; // para validar la entrada
    do{
        String icon = currentPlayer.getIcon();
        System.out.print("Player '"+icon+"', enter your move[1-3] colum [1-3]");
        int row = in.nextInt() -1;
        int col = in.nextInt() -1;
        if(row>= 0 && row<Board.ROWS && col >= 0 && col<Board.COLS
        && board.cells[row][col].content == Seed.NO_SEED){
            // actualizas las casillas y devuelve el estado del jugador despues de la movida
            currentState = board.stepGame(currentPlayer, row, col);
            validINput = true;
        } else {
            System.out.println("This move  at("+(row+1)+","+(col=1)+") is not valid. Try agin ..");

        }


    } while(!validINput); // repita hasta que el input sea valido
   }

   public static void main(String[] args){
    new GameMain();
   }

}
