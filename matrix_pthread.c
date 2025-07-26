#include <stdio.h>      // For input/output functions
#include <stdlib.h>     // For dynamic memory and general utilities
#include <pthread.h>    // For multithreading support

#define MAX 10          // Maximum size of matrix (N x N)

// Global matrices and size variable
int matA[MAX][MAX];     // First input matrix
int matB[MAX][MAX];     // Second input matrix
int result[MAX][MAX];   // Resultant matrix after multiplication
int N;                  // Size of the matrices (NxN)

// Thread function to compute one row of the result matrix
void* multiply(void* arg) {
    int row = *(int*)arg;  // Get the row index passed to the thread

    // Iterate over each column in the result matrix
    for (int i = 0; i < N; i++) {
        result[row][i] = 0;  // Initialize result cell

        // Perform the dot product of row of A and column of B
        for (int j = 0; j < N; j++) {
            result[row][i] += matA[row][j] * matB[j][i];
        }
    }

    pthread_exit(0);  // Exit the thread
}

int main() {
    // Input matrix size
    printf("Enter matrix size (N x N): ");
    scanf("%d", &N);

    // Input matrix A
    printf("Enter elements of Matrix A:\n");
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            scanf("%d", &matA[i][j]);

    // Input matrix B
    printf("Enter elements of Matrix B:\n");
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            scanf("%d", &matB[i][j]);

    // Create an array to hold thread identifiers
    pthread_t threads[N];

    // Array to hold row indices for each thread
    int thread_args[N];

    // Create one thread for each row of the result matrix
    for (int i = 0; i < N; i++) {
        thread_args[i] = i;  // Assign row number
        pthread_create(&threads[i], NULL, multiply, &thread_args[i]);  // Create thread
    }

    // Wait for all threads to finish (join)
    for (int i = 0; i < N; i++)
        pthread_join(threads[i], NULL);

    // Display the result matrix
    printf("Result Matrix:\n");
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++)
            printf("%d ", result[i][j]);
        printf("\n");
    }

    return 0;  // Exit program
}
