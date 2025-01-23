//Amiran Dugiev 2020-09-20
//mergesort O(nlogn) space O(n)
//insertionsort Omega(n) to O(n^2) space O(1)
#include<stdio.h>
#include<stdlib.h>
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

// Merges two subarrays of arr[].
void merge(int arr[], int l, int m, int r)
{
    int i, j, k;
    int n1 = m - l + 1;
    int n2 =  r - m;
    int L[n1], R[n2];

    //Copy data to temporary arrays
    for (i = 0; i < n1; i++)
        L[i] = arr[l + i];
    for (j = 0; j < n2; j++)
        R[j] = arr[m + 1+ j];

    //Merge temporary arrays back
    i = 0;
    j = 0;
    k = l;
    while (i < n1 && j < n2)
    {
        if (L[i] <= R[j])
        {
            arr[k] = L[i];
            i++;
        }
        else
        {
            arr[k] = R[j];
            j++;
        }
        k++;
    }

    //Copy remaining elements to L
    while (i < n1)
    {
        arr[k] = L[i];
        i++;
        k++;
    }

    //Copy remaining elements to R
    while (j < n2)
    {
        arr[k] = R[j];
        j++;
        k++;
    }
}

//Merge sort algorithm
void mergeSort(int arr[], int l, int r)
{
    if(r <= 30)
    {
        insertionSort(arr, r);
        printArray(arr);
    }
    else if (l < r)
    {
        // Same as (l+r)/2, but avoids overflow for
        // large l and h
        int m = l+(r-l)/2;

        // Sort first and second halves
        mergeSort(arr, l, m);
        mergeSort(arr, m+1, r);
        merge(arr, l, m, r);
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
    int arr[16];
    srand(0);
    for (int i = 0; i < 16; i++) {
    arr[i] = rand() % 16;
    }
    int n = sizeof(arr)/sizeof(arr[0]);
    mergeSort(arr, 0, n+1);
    printf("Sorted array: ");
    printArray(arr, n);
    return 0;
}
