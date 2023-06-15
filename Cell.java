
 *  La clase Cell modela cada casilla individualmente del tictactoe 3x3 grid
 * 
 *
 */
public class Cell {
    /** Contenido de esta casilla (CROSS, NOUGHT, NO_SEED) */
    Seed content;
    /* fila  y columna de esta casilla, no estada en este programa */
    int row, col;

    public Cell(int row,int col){
        this.row = row;
        this.col = col;
        this.content = Seed.NO_SEED;
    }

    public void newGame() {
        this.content = Seed.NO_SEED;
    }

    // la casilla se pinta a si misma
    public void paint(){
        String icon = this.content.getIcon();
        System.out.print(icon);
    }
