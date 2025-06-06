import java.util.Scanner;

public class TicTacToe {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Selamat datang di Tic Tac Toe super susah!");
        System.out.println("Anda akan bermain melawan AI Kuat!");
        System.out.println("Pemain: X");
        System.out.println("AI: O");
        
        // Memilih siapa yang mulai pertama
        int mulaiPertama = pilihMulaiPertama();
        Board board = new Board(mulaiPertama);
        
        boolean mainLagi = true;
        while (mainLagi) {
            mainGame(board);
            mainLagi = tanyaMainLagi();
            if (mainLagi) {
                board.resetBoard();
                mulaiPertama = pilihMulaiPertama();
                board.setTurn(mulaiPertama);
            }
        }
        
        System.out.println("Terima kasih telah bermain!");
        scanner.close();
    }
    
    private static int pilihMulaiPertama() {
        System.out.println("\nPilih siapa yang mulai pertama:");
        System.out.println("1. Saya (Pemain)");
        System.out.println("2. AI");
        System.out.print("Masukkan pilihan (1-2): ");
        
        int pilihan;
        while (true) {
            try {
                pilihan = scanner.nextInt();
                if (pilihan == 1) {
                    return -1; // Pemain mulai pertama
                } else if (pilihan == 2) {
                    return 1; // AI mulai pertama
                } else {
                    System.out.print("Pilihan tidak valid. Masukkan 1 atau 2: ");
                }
            } catch (Exception e) {
                System.out.print("Input tidak valid. Masukkan angka 1 atau 2: ");
                scanner.next(); // Bersihkan buffer
            }
        }
    }
    
    private static void mainGame(Board board) {
        while (!board.gameOver()) {
            board.disp();
            
            if (board.getTurn() == -1) { // Giliran pemain
                buatGerakanPemain(board);
            } else { // Giliran AI
                buatGerakanAI(board);
            }
        }
        
        board.disp();
        int pemenang = board.winner();
        if (pemenang != 0) {
            System.out.println(pemenang == -1 ? "Anda menang! (Eh, bentar kyknya ada yang aneh)" : "AI menang!");
        } else {
            System.out.println("Seri! (Hehe Kamu gak akan menang)");
        }
    }
    
    private static void buatGerakanPemain(Board board) {
        int baris, kolom;
        boolean gerakanValid = false;
        
        while (!gerakanValid) {
            System.out.print("Masukkan baris (0-2): ");
            baris = scanner.nextInt();
            System.out.print("Masukkan kolom (0-2): ");
            kolom = scanner.nextInt();
            
            gerakanValid = board.setBoard(baris, kolom);
            if (!gerakanValid) {
                System.out.println("Gerakan tidak valid! Silakan coba lagi.");
            }
        }
    }
    
    private static void buatGerakanAI(Board board) {
        System.out.println("AI sedang berpikir...");
        
        // Gunakan algoritma minimax untuk menemukan langkah terbaik
        int[] gerakanTerbaik = temukanGerakanTerbaik(board);
        board.setBoard(gerakanTerbaik[0], gerakanTerbaik[1]);
        
        System.out.println("AI bermain di (" + gerakanTerbaik[0] + ", " + gerakanTerbaik[1] + ")");
    }
    
    private static int[] temukanGerakanTerbaik(Board board) {
        int[] gerakanTerbaik = new int[]{-1, -1};
        int skorTerbaik = Integer.MIN_VALUE;
        
        int[] gerakanTersedia = board.getAvailableMoves();
        
        for (int i = 0; i < gerakanTersedia.length; i += 2) {
            int baris = gerakanTersedia[i];
            int kolom = gerakanTersedia[i+1];
            
            Board papanBaru = new Board(board);
            papanBaru.setBoard(baris, kolom);
            
            int skor = minimax(papanBaru, false);
            
            if (skor > skorTerbaik) {
                skorTerbaik = skor;
                gerakanTerbaik[0] = baris;
                gerakanTerbaik[1] = kolom;
            }
        }
        
        return gerakanTerbaik;
    }
    
    private static int minimax(Board board, boolean isMaximizing) {
        // Kasus dasar
        if (board.gameOver()) {
            int pemenang = board.winner();
            if (pemenang == 1) return 1;   // AI menang
            if (pemenang == -1) return -1;  // Pemain menang
            return 0; // Seri
        }
        
        if (isMaximizing) {
            int skorTerbaik = Integer.MIN_VALUE;
            int[] gerakanTersedia = board.getAvailableMoves();
            
            for (int i = 0; i < gerakanTersedia.length; i += 2) {
                int baris = gerakanTersedia[i];
                int kolom = gerakanTersedia[i+1];
                
                Board papanBaru = new Board(board);
                papanBaru.setBoard(baris, kolom);
                
                int skor = minimax(papanBaru, false);
                skorTerbaik = Math.max(skor, skorTerbaik);
            }
            return skorTerbaik;
        } else {
            int skorTerbaik = Integer.MAX_VALUE;
            int[] gerakanTersedia = board.getAvailableMoves();
            
            for (int i = 0; i < gerakanTersedia.length; i += 2) {
                int baris = gerakanTersedia[i];
                int kolom = gerakanTersedia[i+1];
                
                Board papanBaru = new Board(board);
                papanBaru.setBoard(baris, kolom);
                
                int skor = minimax(papanBaru, true);
                skorTerbaik = Math.min(skor, skorTerbaik);
            }
            return skorTerbaik;
        }
    }
    
    private static boolean tanyaMainLagi() {
        System.out.print("Apakah Anda ingin bermain lagi? (y/n): ");
        String jawaban = scanner.next().toLowerCase();
        while (!jawaban.equals("y") && !jawaban.equals("n")) {
            System.out.print("Masukkan 'y' atau 'n': ");
            jawaban = scanner.next().toLowerCase();
        }
        return jawaban.equals("y");
    }
}