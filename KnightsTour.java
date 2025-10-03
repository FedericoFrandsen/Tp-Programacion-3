import java.util.*;

public class KnightsTour {

    static int N = 6; // tamaño del tablero 
    static int[][] board; //tablero

    // Movimientos posibles del Caballo
    static int[] dx = {2, 1, -1, -2, -2, -1, 1, 2};
    static int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};

    // -------------------
    // Parte 1: Backtracking
    // -------------------
    static boolean isValid(int x, int y) {
        return (x >= 0 && x < N && y >= 0 && y < N && board[x][y] == -1);

// Comprueba que:
// x,y estén dentro del tablero y La casilla aún no haya sido visitada (-1).

    }

    static boolean solveKnightBT(int x, int y, int move) {
        if (move == N * N) return true;

//move: número de movimientos hechos (o nivel/orden de visita).
//Caso base: si ya hiciste N*N movimientos, significa que visitaste todas las casillas.


        for (int k = 0; k < 8; k++) {
            int nx = x + dx[k];
            int ny = y + dy[k];
            if (isValid(nx, ny)) {
                board[nx][ny] = move;
                if (solveKnightBT(nx, ny, move + 1)) {
                    return true;
                } else {
                    board[nx][ny] = -1; // backtrack
                }
            }
        }
        return false;
    }

//Intenta los 8 movimientos posibles.
//Si encuentra camino → sigue recursión.
//Si se queda sin solución → borra la marca (-1) y retrocede (backtracking)


    static void knightTourBT() {
        board = new int[N][N];
        for (int[] row : board) Arrays.fill(row, -1);  //Inicializa el tablero con todo en -1.

        board[0][0] = 0; //el caballo empieza en la casilla (0,0)
        if (solveKnightBT(0, 0, 1)) {
            printBoard(board);
        } else {
            System.out.println("No hay solución");
        }
    }

    // -------------------
    // Parte 2: Warnsdorff (Greedy)
    // -------------------

//Devuelve cuántas casillas libres hay desde (x,y).
//Cuanto menor el número, más “crítico” es moverse ahí.

    static int countOnwardMoves(int x, int y, boolean[][] visited) {
        int count = 0;
        for (int k = 0; k < 8; k++) {
            int nx = x + dx[k];
            int ny = y + dy[k];
            if (nx >= 0 && nx < N && ny >= 0 && ny < N && !visited[nx][ny]) {
                count++;
            }
        }
        return count;
    }


    static void knightTourWarnsdorff() {
        int[][] solution = new int[N][N];
        for (int[] row : solution) Arrays.fill(row, -1);

        boolean[][] visited = new boolean[N][N];
        int x = 0, y = 0;
        solution[x][y] = 0;
        visited[x][y] = true;

        for (int move = 1; move < N * N; move++) {
            int bestDeg = Integer.MAX_VALUE;
            int nextX = -1, nextY = -1;


//solution: matriz con el recorrido final.
//visited: para marcar casillas visitadas.

            for (int k = 0; k < 8; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];
                if (nx >= 0 && nx < N && ny >= 0 && ny < N && !visited[nx][ny]) {
                    int deg = countOnwardMoves(nx, ny, visited);
                    if (deg < bestDeg) {
                        bestDeg = deg;
                        nextX = nx;
                        nextY = ny;
                    }
                }
            }
//Busca entre las 8 jugadas posibles la que tenga menor grado de libertad posterior (deg).

            if (nextX == -1) {
                System.out.println("Tour interrumpido antes de cubrir todas las casillas.");
                return;
            }

            x = nextX; y = nextY;
            solution[x][y] = move;
            visited[x][y] = true;
        }
        printBoard(solution);
    }
//Si no encuentra próxima casilla (-1) → tour incompleto.
//Si encuentra → avanza y marca en la matriz.
//Al final imprime el recorrido.


    // -------------------
    // Parte 3: Programación Dinámica (knight + puntos)
    // -------------------
    static int[][] pointsBoard = {
            {1, 2, 3, 4, 5, 6},
            {7, 5, 3, 2, 4, 1},
            {8, 6, 4, 7, 5, 2},
            {3, 9, 2, 6, 8, 4},
            {5, 7, 3, 2, 1, 9},
            {4, 6, 5, 2, 8, 7}
    };

    static Map<String, Integer> memo = new HashMap<>();

//Guardamos en memoria resultados ya calculados para no recalcular.
//La clave es "x,y,k" (posición + movimientos restantes).


    static int dp(int x, int y, int k) {
        if (x < 0 || x >= N || y < 0 || y >= N) return Integer.MIN_VALUE / 2;
        if (k == 0) return pointsBoard[x][y];

//Si (x,y) está fuera del tablero → resultado inválido.
//Si k=0 (sin movimientos) → puntaje de la casilla final.

        String key = x + "," + y + "," + k;
        if (memo.containsKey(key)) return memo.get(key);

        int best = Integer.MIN_VALUE / 2;
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            best = Math.max(best, pointsBoard[x][y] + dp(nx, ny, k - 1));
        }

        memo.put(key, best);
        return best;
    }
//Para cada movimiento posible, calcula el mejor puntaje alcanzable.
//Guarda el resultado en memo antes de retornar.

    static void knightMaxScore(int k) {
        memo.clear();
        int result = dp(0, 0, k);
        System.out.println("Máxima suma en " + k + " movimientos: " + result);
    }
//Resuelve el problema desde (0,0) con k movimientos disponibles.
//Imprime el resultado.


    // -------------------
    // Utilidades
    // -------------------
    static void printBoard(int[][] b) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%3d ", b[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
//Imprime el tablero formateado.


    // -------------------
    // Main
    // -------------------
    public static void main(String[] args) {
        System.out.println("=== Knight's Tour - Backtracking ===");
        knightTourBT();

        System.out.println("\n=== Knight's Tour - Warnsdorff ===");
        knightTourWarnsdorff();

        System.out.println("\n=== Knight con DP (max puntaje) ===");
        knightMaxScore(4); // probar distintas k
    }
}
