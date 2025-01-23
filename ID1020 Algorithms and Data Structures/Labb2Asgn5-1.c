//Amiran Dugiev 2019-09-21 Quicksort
// Executable time: 0.48s
#include<stdio.h>
#include <math.h>

void insertionSort(int arr[], int n)
{
    int i, key, j;
    for (i = 1; i < n; i++) {
        key = arr[i];
        j = i - 1;

        /* Move elements in array that are greater than key one step forward*/
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j = j - 1;
        }
        arr[j + 1] = key;
    }
}

void printArray(int arr[], int size)
{
    int i;
    for (i=0; i < size; i++)
        printf("%d ", arr[i]);
}

// Main program with testing
int main()
{
    int arr[100000];
    srand(0);
    for (int i = 0; i < 100000; i++) {
    arr[i] = rand() % 100000;
    }
    int n = sizeof(arr)/sizeof(arr[0]);
    insertionSort(arr, n+1);
    printf("Sorted array: ");
    printArray(arr, n);
    return 0;
}
