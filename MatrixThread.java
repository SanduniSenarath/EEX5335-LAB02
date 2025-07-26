import java.util.Scanner;  // Import Scanner for user input

// Worker class extends Thread to perform row-wise multiplication
class Worker extends Thread {
    int row;                // The row this thread is responsible for
    int[][] A, B, result;   // Matrices: A (input), B (input), result (output)
    int N;                  // Size of the matrices

    // Constructor to initialize variables
    Worker(int row, int[][] A, int[][] B, int[][] result, int N) {
        this.row = row;
        this.A = A;
        this.B = B;
        this.result = result;
        this.N = N;
    }

    // This method runs when the thread is started
    public void run() {
        // For each column in the result matrix
        for (int i = 0; i < N; i++) {
            result[row][i] = 0;  // Initialize cell
            // Compute dot product of A[row] and B column
            for (int j = 0; j < N; j++) {
                result[row][i] += A[row][j] * B[j][i];
            }
        }
    }
}

// Main class to execute matrix multiplication using threads
public class MatrixThread {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  // Scanner object for input

        // Input matrix size
        System.out.print("Enter matrix size (N): ");
        int N = sc.nextInt();

        // Declare and initialize matrices
        int[][] A = new int[N][N];
        int[][] B = new int[N][N];
        int[][] result = new int[N][N];

        // Input Matrix A
        System.out.println("Enter Matrix A:");
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                A[i][j] = sc.nextInt();

        // Input Matrix B
        System.out.println("Enter Matrix B:");
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                B[i][j] = sc.nextInt();

        // Create an array of threads, one for each row
        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            threads[i] = new Worker(i, A, B, result, N);  // Create thread
            threads[i].start();  // Start thread
        }

        // Wait for all threads to complete
        for (int i = 0; i < N; i++) {
            try {
                threads[i].join();  // Ensure main waits for this thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print the resulting matrix
        System.out.println("Result Matrix:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(result[i][j] + " ");
            System.out.println();
        }
    }
}
