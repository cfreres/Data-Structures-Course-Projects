public class FloydWarshall {

    public static void main(String[] args){

        int m = 10000000;
        int[][] matrix = {{0,2,4,m,m,m,m,m},
                {2,0,m,5,m,2,m,m},
                {4,m,0,m,m,3,m,1},
                {m,5,m,0,4,m,m,m},
                {m,m,m,4,0,3,2,m},
                {m,2,3,m,3,0,m,2},
                {m,m,m,m,2,m,0,1},
                {m,m,1,m,m,2,1,0}};
        int curLength = 0;
        int posLength = 0;
        for (int i = 0; i < 8; i++){
            for(int to = 0; to < 8; to++){
                for(int from = 0; from < 8; from++)
                {
                    curLength = matrix[from][to];
                    posLength = matrix[from][i] + matrix[i][to];
                    if (posLength < curLength){
                        matrix[from][to] = posLength;
                    }
                }
            }
            for (int k = 0; k < 8; k++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(matrix[k][j] + " ");
                }
                System.out.println();
            }
            System.out.println();

        }
    }
}
