import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// tic-tac-toe modifycation to work as connect four 
public class TTTGraphics extends JFrame {

    public static final int ROWS = 6;
    public static final int  COLS = 7;

    // constante definidas para dibujar los graficos
    public static final int CELL_SIZE =120; // cell ancho/ alto (cuadro)
    public static final int BOARD_WIDTH = CELL_SIZE * COLS;
    public static final int BOARD_HEIGHT = CELL_SIZE* ROWS;
    public static final int GRID_WIDTH =10;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH /2;
    
    public static final int CELL_PADDING = CELL_SIZE / 5;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216,216,216);
    public static final Color COLOR_GRID = Color.LIGHT_GRAY;
    public static final Color COLOR_CROSS = new Color(211,45,65);
    public static final Color COLOR_NOUGHT = new Color(76,181,245);
    public static final Font FONT_STATUS = new Font("OCR A Extended",Font.PLAIN,14);

    public enum State{
        PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }

    private State currentState;

    public enum Seed{
        CROSS, NOUGHT, NO_SEED
    }

    private Seed currentPlayer;
    private Seed[][] board;
    
    // UI Components
    private GamePanel gamePanel;
    private JLabel statusBar;


    public TTTGraphics(){

        // iniciale los objetos del juego
        initGame();

        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(BOARD_WIDTH,BOARD_HEIGHT)); // hasta aca bien

        // el canvas jpanel dispara sobre un clickeo del mouse
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int mouseX = e.getX();
                int mouseY = e.getY();

                int row = mouseY/ CELL_SIZE;
                int col = mouseX / CELL_SIZE;

                if(currentState == State.PLAYING){
                    if(col >=0 && col < COLS){

                        for(int row2 =ROWS -1; row2>=0; row2--){
                            if(board[row2][col]== Seed.NO_SEED){
                              board[row2][col] = currentPlayer;
                              stepGame(currentPlayer,row2,col);
                              if(hasWon(currentPlayer, row2, col) == true){
                                currentState =(currentPlayer == Seed.CROSS) ?  State.CROSS_WON: State.NOUGHT_WON;
                              } 
                              currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT: Seed.CROSS;
                              break;
                            }
                        }

                        // actualize la tabla y devuelva el nuevo jugador despues de la movida
                        //currentState= stepGame(currentPlayer,row,col);
                        // Switch player
                        
                       // currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT: Seed.CROSS;
                    }
                } else { // game over
                   newGame();
                }
                repaint(); // Callback paintCompontent
            }
        });

        // Congigurar la barra de status(jlabal al display menssage)

        statusBar = new JLabel("       ");
        statusBar.setFont(FONT_STATUS);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));
        statusBar.setOpaque(true);
        statusBar.setBackground(COLOR_BG_STATUS);

        // configurar el content pane 
        Container cp = getContentPane();

        cp.setLayout(new BorderLayout());
        cp.add(gamePanel,BorderLayout.CENTER);
        cp.add(statusBar,BorderLayout.PAGE_END); // lo mismo que south

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Connect 4");
        setVisible(true);

        newGame();
    }

    public void initGame() {
        board = new Seed[ROWS][COLS]; // asigne el array 
    }

    public void newGame() {
        for(int row = 0;row < ROWS;++row){
            for(int col=0; col <COLS;++col){
                board[row][col] = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
    }

    public State stepGame(Seed player,int selectedRow,int selectedCol) {


        board[selectedRow][selectedCol] = player;

        // compute  y devuelta el estado del juego
        if(board[selectedRow][0] == player // 3 en fila
           && board[selectedRow][1] == player
           && board[selectedRow][2] == player 
           || board[0][selectedCol] == player // 3 en columna
           && board[1][selectedCol] ==player 
           && board[2][selectedCol] ==player 
           || selectedRow == selectedCol // 3 en diagonal 
           && board[0][0] == player 
           && board[1][1] == player 
           && board[2][2] == player 
           || selectedRow + selectedCol == 2
           && board[0][2] == player 
           && board[1][1] == player 
           && board[2][0] == player) { 
            return (player == Seed.CROSS) ? State.CROSS_WON :State.NOUGHT_WON;
           } else {
              // nadie  gana chequee por empate (Todas las columnas ocupadas o jugando)
              for(int row = 0;row < ROWS;row++){
                 for(int col = 0; col< COLS;col++){
                    if(board[row][col]== Seed.NO_SEED){
                    return State.PLAYING; // todavia hay casillas vacias
                    }
                 }
              }
              return State.DRAW;
           }
    }

    public boolean hasWon(Seed theseed, int rowSelected, int colSelected){
        // checquea si 4 en linea sobre la fila seleccionada
        int count = 0;
        for(int col = 0; col<COLS;++col){
            if(board[rowSelected][col] ==theseed){
                count++;
                if(count == 4) return true; //  found
            }else {
                count = 0;
            }
        }

        for (int row = 0; row<ROWS;row++){
            if(board[row][colSelected]==  theseed){
                count++;
                if(count == 4) return true;
            } else{
                count = 0;
            }
        }

        // for diagonals use the same strategy as tic tac toe
        
        if(board[0][0] == theseed 
          && board[1][1] == theseed
          && board[2][2] == theseed
          && board[3][3] == theseed ||
          board[1][0]== theseed
          && board[2][1]== theseed
          && board[3][2]== theseed
          && board[4][3]== theseed ||
          board[2][0] == theseed
          && board[3][1] == theseed
          && board[4][2] == theseed
          && board[5][3] == theseed ||
          // next diagonal contigous to 1,1
          board[0][1] == theseed 
          && board[1][2] == theseed
          && board[2][3] == theseed
          && board[3][4] == theseed ||
          // next diagonal
          board[0][2] == theseed
          && board[1][3] == theseed
          && board[2][4] == theseed
          && board[3][5] == theseed ||
          // last diagonal in that direction
          board[0][3] == theseed
          && board[1][4] == theseed
          && board[2][5] == theseed
          && board[3][6] == theseed ||
          //
          board[2][1] == theseed 
          && board[3][2] == theseed 
          && board[4][3] == theseed
          && board[5][4] == theseed ||
          board[1][1] == theseed
          && board[2][2] == theseed
          && board[3][3] == theseed
          && board[4][4] == theseed ||
          board[2][2] == theseed 
          && board[3][3] == theseed
          && board[4][4] == theseed
          && board[5][5] == theseed ||
          board[1][2] == theseed 
          && board[2][3] == theseed 
          && board[3][4] == theseed
          && board[4][5] == theseed ||
          board[2][3] == theseed
          && board[3][4] == theseed
          && board[4][5] == theseed 
          && board[5][6] == theseed ||
          board[1][3] == theseed 
          && board[2][4] == theseed
          && board[3][5] == theseed
          && board[4][6] == theseed
          ){ return true;}
         // diagonals going  up to right
          if(board[3][1]== theseed
             && board[2][1] == theseed 
             && board[1][2] == theseed
             && board[0][3] == theseed||
             board[4][0] == theseed
             && board[3][1] == theseed
             && board[2][2] == theseed
             && board[1][3] == theseed ||
             board[5][0] == theseed
             && board[4][1] == theseed
             && board[3][2] == theseed
             && board[2][3] == theseed ||
             board[5][1] == theseed
             && board[4][2] == theseed
             && board[3][3] == theseed
             && board[2][4] == theseed ||
             board[5][2] == theseed
             && board[4][3] == theseed
             && board[3][4] == theseed
             && board[2][5] == theseed ||
             board[5][3] == theseed
             && board[4][4] == theseed
             && board[3][5] == theseed
             && board[2][6] == theseed ||
             board[4][2] == theseed
             && board[3][3] == theseed
             && board[2][4] == theseed
             && board[1][5] == theseed ||
             board[4][1] == theseed 
             && board[3][2] == theseed 
             && board[2][3] == theseed                         
             && board[1][4] == theseed ||
             board[3][2] == theseed 
             && board[2][3] == theseed 
             && board[1][4] == theseed 
             && board[0][5] == theseed ||
             board[3][1] == theseed 
             && board[2][2]== theseed
             && board[1][3]== theseed
             && board[0][4]== theseed ||
             board[3][0] == theseed 
             && board[2][1] == theseed 
             && board[1][2] == theseed 
             && board[0][3] == theseed 
             ){return true;}


        //
        return false;
    }

    

    class GamePanel extends JPanel {

        @Override
        public void paintComponent(Graphics g){ // callback via repaint
            super.paintComponent(g);
            setBackground(COLOR_BG);

            // dibuje las lineas de grilla
            g.setColor(COLOR_GRID);
            for(int row =1; row < ROWS; ++row){
                g.fillRoundRect(0, CELL_SIZE*row - GRID_WIDTH_HALF,
                 BOARD_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);

            }
            for (int col=1; col <COLS;++col){
                g.fillRoundRect(CELL_SIZE*col - GRID_WIDTH_HALF,0,
                GRID_WIDTH,BOARD_HEIGHT -1,GRID_WIDTH,GRID_WIDTH);
            }

            // draw seeds of all cells if they are not empy
            // usando graphics 2d Dlo cual permite usar el pen's stroke
            Graphics2D g2d= (Graphics2D)g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH,
            BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));

            for(int row = 0; row <ROWS;++row){
                for(int col=0; col< COLS;col++){
                    int x1 = col*CELL_SIZE +CELL_PADDING;
                    int y1 = row *CELL_SIZE+CELL_PADDING;
                    if(board[row][col] == Seed.CROSS){ // dibuje 2 lineas crus
                        g2d.setColor(COLOR_CROSS);
                        int x2 = (col+1)*CELL_SIZE - CELL_PADDING;
                        int y2=  (row+1)*CELL_SIZE - CELL_PADDING;
                        g2d.fillOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }else if(board[row][col]== Seed.NOUGHT){
                        g2d.setColor(COLOR_NOUGHT);
                        g2d.fillOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }
                }
            }


        

        // print status message
        if(currentState == State.PLAYING){
            statusBar.setForeground(Color.BLACK);
            statusBar.setText(currentPlayer== Seed.CROSS ? "red's Turn" :"Yellow'S Turn");
        }else if(currentState == State.DRAW){
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again");
        } else if (currentState == State.CROSS_WON){
            statusBar.setForeground(Color.RED);
            statusBar.setText("Red circle' WOn! click to play again");
        } else if (currentState == State.NOUGHT_WON){
            statusBar.setForeground(Color.RED);
            statusBar.setText("Blue Circle' WOn! click to play again");
        }

        
    }
}

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override 
            public void run(){
                new TTTGraphics();
            }
        });
    }


}
