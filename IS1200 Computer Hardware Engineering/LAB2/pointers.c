#include <stdio.h>
#include <stdlib.h>

char* text1 = "This is a string.";
char* text2 = "Yet another thing.";
int list1[20]; // length of the list (20 words/ 20 ints)
int list2[20]; // length of the list (20 words/ 20 ints)int list2 [80]; // length of the list (20 words/ 20 ints)
int count = 0;  // counter for number of chars worked with

void copycodes (int* list, char* text, int* count){
  *list = *text;
  while(*text != 0){
    list++;
    text++;
    *count = *count + 1;
    *list = *text;
  }
}

void work (){
    copycodes(list1, text1, &count);
    copycodes(list2, text2, &count);  
}

void printlist(const int* lst){
  printf("ASCII codes and corresponding characters.\n");
  while(*lst != 0){
    printf("0x%03X '%c' ", *lst, (char)*lst);
    lst++;
  }
  printf("\n");
}

void endian_proof(const char* c){
  printf("\nEndian experiment: 0x%02x,0x%02x,0x%02x,0x%02x\n", 
         (int)*c,(int)*(c+1), (int)*(c+2), (int)*(c+3));
  
}

int main(void){
  work();

  printf("\nlist1: ");
  printlist(list1);
  printf("\nlist2: ");
  printlist(list2);
  printf("\nCount = %d\n", count);

  endian_proof((char*) &count);
}