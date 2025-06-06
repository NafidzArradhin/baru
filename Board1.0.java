public class Board {
    private int[][] data;   // menyimpan nilai 0 / 1 / -1
    private int turn;       // menyimpan nilai 1 / -1
    
    public Board(int turn) {
        this.data = new int[3][3];
        this.turn = turn;
    }
    
    // Copy constructor untuk AI
    public Board(Board other) {
        this.data = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(other.data[i], 0, this.data[i], 0, 3);
        }
        this.turn = other.turn;
    }
    
    public void disp() {
        System.out.println("\nCurrent Board:");
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (this.data[i][j]) {
                    case 0  -> System.out.print("|   |");
                    case -1 -> System.out.print("| X |");
                    case 1  -> System.out.print("| O |");
                }
            }
            System.out.println("\n-------------");
        }
        System.out.println("Current turn: " + (turn == -1 ? "X" : "O"));
    }
    
    public boolean setBoard(int brs, int kol) {
        if (brs < 0 || brs > 2 || kol < 0 || kol > 2) {
            return false;
        }
        if (this.data[brs][kol] == 0) {
            this.data[brs][kol] = turn;
            turn = -turn;
            return true;
        } else {
            return false;
        }
    }
    
    public int winner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (data[i][0] != 0 && data[i][0] == data[i][1] && data[i][0] == data[i][2]) {
                return data[i][0];
            }
        }
        
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (data[0][j] != 0 && data[0][j] == data[1][j] && data[0][j] == data[2][j]) {
                return data[0][j];
            }
        }
        
        // Check diagonals
        if (data[0][0] != 0 && data[0][0] == data[1][1] && data[0][0] == data[2][2]) {
            return data[0][0];
        }
        if (data[0][2] != 0 && data[0][2] == data[1][1] && data[0][2] == data[2][0]) {
            return data[0][2];
        }
        
        return 0;
    }
    
    public boolean gameOver() {
        if (winner() != 0) {
            return true;
        }
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (data[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.data[i][j] = 0;
            }
        }
    }
    
    public int getTurn() {
        return turn;
    }
    
    public void setTurn(int turn) {
        this.turn = turn;
    }
    
    // Method untuk AI
    public int[] getAvailableMoves() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (data[i][j] == 0) count++;
            }
        }
        
        int[] moves = new int[count * 2];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (data[i][j] == 0) {
                    moves[index++] = i;
                    moves[index++] = j;
                }
            }
        }
        return moves;
    }
}