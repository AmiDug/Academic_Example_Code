/*
 sieves-heap.c
 By David Broman.
 Last modified: 2015-09-15
 This file is in the public domain.
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define COLUMNS 6

// print prime numbers in table format
int counter = 0;
void print_number(int n){
    printf("%10d",n);       // 10 gives spaces that can or can not be filled by numbers
    counter++;
    if (counter % COLUMNS == 0) {
        printf("\n");
    }
}

void print_sieves(int n){
    int *allNumbers = (int*)malloc(sizeof(int) * n);       // size of int is equal to 4
    for (int i = 2; i < n; i++) {
        allNumbers[i] = 1;
    }
    
    for (int i = 2; i < sqrt(n); i++) {
        if (allNumbers[i] == 1) {
            for (int j = i + 1; j < n; j++) {
                if (j % i == 0) {
                    allNumbers[j] = 0;
                }
            }
        }
    }
    for (int i = 2; i < n; i++) {
        if (allNumbers[i] == 1) {
            print_number(i);
        }
    }
    printf("\n");
    free(allNumbers);                                      //  freeing memory, similar to POP
}

// 'argc' contains the number of program arguments, and
// 'argv' is an array of char pointers, where each
// char pointer points to a null-terminated string.
int main(int argc, char *argv[]){
    if(argc == 2)
        print_sieves(atoi(argv[1]));
    else
        printf("Please state an interger number.\n");
    return 0;
}