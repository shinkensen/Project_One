import java.util.ArrayList;
import java.util.Scanner;
public class _2048_ {
    public static int[][] rando(int[][] board){
        ArrayList<Integer> nums=new ArrayList<>();
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                if (board[i][j]==0){
                    int num=i*4+j;
                    nums.add(num);
                }
            }
        }
        int randomNum = (int)(Math.random() * ((nums.size()-1) - 0 + 1));
        int m=nums.get(randomNum);
        int i=0,j;
        while (m>3){
            m-=4;
            i++;
        }
        j=m;
        board[i][j]=2;
        return board;

    }
    public static int[][] merger(int direct,int[][] board){
        if (direct==3){
            for (int[] board1 : board) {
                for (int j = board1.length - 1; j>0; j--) {
                    if (board1[j] == 0) {
                        for (int k = j-1; k>=0; k--) {
                            board1[k+1] = board1[k];
                        }
                        board1[0] = 0;
                    }
                }
            }
        }
        if (direct == 4) {
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 3; i++) {
                    if (board[i][j] == 0) {
                        for (int k = i + 1; k < 4; k++) {
                            board[k - 1][j] = board[k][j];
                        }
                        board[3][j] = 0;
                    }
                }
            }
        }
        if (direct == 2) {
            for (int j = 0; j < 4; j++) {
                for (int i = 3; i > 0; i--) {
                    if (board[i][j] == 0) {
                        for (int k = i - 1; k >= 0; k--) {
                            board[k + 1][j] = board[k][j];
                        }
                        board[0][j] = 0;
                    }
                }
            }
        }
        if (direct==1){
            for (int[] board1 : board) {
                for (int j = 0; j<board[0].length; j++) {
                    if (board1[j] == 0) {
                        for (int k = j+1; k<board.length; k++) {
                            board1[k-1] = board1[k];
                        }
                        board1[board[0].length-1] = 0;
                    }
                }
            }
        }
        return board;
    }
    public static int[][] gravity(int direct,int[][] board){
        //my approach: first, push the actual numbers toghether, in the direction indicated so that then i can check if numbers next to eachother are equal so that i can merge them.
        //part 2; how the mergeing would work is that i will consolidate the values into the one that makes sense for the direction, then i will set the other one to zero, do it for the rest of them and then pass it through the merge meathod again.
        board=merger(direct,board);
        if (direct==3){
            for (int i=0;i<4;i++){
                for (int j=board[0].length-1;j>0;j--){
                    if (board[i][j]==board[i][j-1]){
                        board[i][j]*=2;
                        board[i][j-1]=0;
                    }
                }
            }
            board=merger(direct,board);
        }
        if (direct==1){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == board[i][j + 1]) {
                        board[i][j] *= 2;
                        board[i][j + 1] = 0;
                    }
                }
            }
            board=merger(direct,board);
        }
        
        if (direct == 2) { // down
            for (int j = 0; j < 4; j++) {
                for (int i = 3; i > 0; i--) {
                    if (board[i][j] == board[i - 1][j]) {
                        board[i][j] *= 2;
                        board[i - 1][j] = 0;
                    }
                }
            }
            board = merger(direct, board);
        }

        if (direct == 4) { // up
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 3; i++) {
                    if (board[i][j] == board[i + 1][j]) {
                        board[i][j] *= 2;
                        board[i + 1][j] = 0;
                    }
                }
            }
            board = merger(direct, board);
        }
        return board;
    }
    public static void main(String[] args) {
        int[][] board=new int[4][4];
        board=rando(rando(board));
        Scanner sc=new Scanner(System.in);
        boolean game=true;
        System.out.println("_______________      _____   ______  \r\n" + //
                        "\\_____  \\   _  \\    /  |  | /  __  \\ \r\n" + //
                        " /  ____/  /_\\  \\  /   |  |_>      < \r\n" + //
                        "/       \\  \\_/   \\/    ^   /   --   \\\r\n" + //
                        "\\_______ \\_____  /\\____   |\\______  /\r\n" + //
                        "        \\/     \\/      |__|       \\/");
        while(game){
            for (int[] board1 : board) {
                for (int j = 0; j < 4; j++) {
                    if (board1[j] != 0) {
                        System.out.print(board1[j] + " ");
                    } else {
                        System.out.print("0 ");
                    }
                }
                System.out.println("");
            }
            System.out.println("Choose your direction( 1(left) , 2(down) , 3(right) , 4(up) ): ");
            int direct=sc.nextInt();
            board=gravity(direct,board);
            int min=2048;
            for (int[] board1 : board) {
                for (int j = 0; j<board.length; j++) {
                    if (board1[j] < min) {
                        min = board1[j];
                    }
                }
            }
            if (min>0){
                game=false;
            }
            else{
                board=rando(board);
            }
        }
        System.out.println("   _____                        ____                 \r\n" + //
                        "  / ____|                      / __ \\                \r\n" + //
                        " | |  __  __ _ _ __ ___   ___ | |  | |_   _____ _ __ \r\n" + //
                        " | | |_ |/ _` | '_ ` _ \\ / _ \\| |  | \\ \\ / / _ \\ '__|\r\n" + //
                        " | |__| | (_| | | | | | | (_) | |__| |\\ V /  __/ |   \r\n" + //
                        "  \\_____|\\__,_|_| |_| |_|\\___/ \\____/  \\_/ \\___|_|   \r\n" + //
                        "");
    }
}
