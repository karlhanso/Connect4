/*
 *  la clase board modela un Tictactoe game de 3x3
 * 
 * 
 */

 public class Board{

    public static final int ROWS = 3;
    public static final int COLS =3;

    Cell[][] cells;

    // constructor para inicializar el tablero (board)
    public Board(){
        initGame();
    }

    public void initGame(){
        cells =new Cell[ROWS][COLS];
        for(int row =0;row<ROWS;++row){
            for(int col=0;col<COLS;++col){
                cells[row][col] = new Cell(row,col);
            }
        }
    }

    // resetear los contenidos del tablero para un juego nuevo 

    public void newGame(){
        for(int row =0;row<ROWS;++row){
            for(int col= 0;col<COLS;++col){
                cells[row][col].newGame(); // la casilla se inicializa ella misma
            }
        }
    }


    public State stepGame(Seed player,int selectedRow,int selectedCol){
        //update game board
        cells[selectedRow][selectedCol].content = player;
        // compute el resultado y devuelva el nuevo estado del nuevo juego 

        if(cells[selectedRow][0].content == player // 3 en fila
            && cells[selectedRow][1].content == player
            && cells[selectedRow][2].content == player
            || cells[0][selectedCol].content == player // 3 en columna
            && cells[1][selectedCol].content == player
            && cells[2][selectedCol].content == player
            || selectedRow == selectedCol // 3 en diagonal
            && cells[0][0].content ==  player 
            && cells[1][1].content == player 
            && cells[2][2].content == player 
            || selectedRow + selectedCol == 2 //3 en diagonal opuesta
            && cells[0][2].content == player 
            && cells[1][1].content == player 
            && cells[2][0].content == player){
           return (player == Seed.CROSS ? State.CROSS_WON: State.NOUGHT_WON);
        }else {
            // nadie gana, chequee por empate(todas las casillas ocupadas)
            for(int row = 0;row<ROWS;row++){
                for(int col = 0;col<COLS;++col){
                    if(cells[row][col].content == Seed.NO_SEED){
                        return State.PLAYING;
                    }
                }
            }
            return State.DRAW;
        }
    }

    public void paint(){
        for(int row =0;row<ROWS;++row){
            for(int col=0;col<COLS;++col){
                System.out.print(" ");
                cells[row][col].paint(); //cada casilla se pinta ella misma
                System.out.print(" ");
                if(col< COLS -1) System.out.print("|"); // separador de columna
            }
            System.out.println();
            if(row<ROWS-1){
                System.out.println("-----------"); // separador de filas
            }
        }
        System.out.println();
    }
 }
