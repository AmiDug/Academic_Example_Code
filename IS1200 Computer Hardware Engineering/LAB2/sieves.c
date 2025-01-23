#include <stdio.h>
#include <stdlib.h>
#include <math.h>

// function to check for prime numbers
int is_prime(int n){
    int i = 0;
    for (i = 2; i <= (n/2); i++){
        if (n % i == 0) {
            return 0;
        }
    }
    return 1;
}

typedef struct prime{
    int pnumber;            // is a prime number
    struct prime* next;     // ponts to next prime number
}prime;

prime* head;

// returns a linked list with prime numbers between 2 and n
prime* gen_primelist(int n){
   
    prime* nullPointer = NULL;
    prime* current = head;
    prime* temp;
    
    for (int i = 2; i < (n/2); ++i){
        
        if (is_prime(i)){
            
            if (i == 2) {
                head -> pnumber = 2;
                head -> next = nullPointer;
            }
            
            while (current -> next != NULL) {
                current = current -> next;
            }
            
            temp -> pnumber = i;
            temp -> next = nullPointer;
            current -> next = temp;
            
        }
    }
    return head;
}

void print_list(prime* head){
    
    while(head -> next != NULL){
        printf("%d\n", head -> pnumber);
        head = head -> next;
    }
}


int main(){
    
    int n = 105;
    prime* wanted = gen_primelist(n);
    print_list(wanted);
    return 0;
}

//
//int main(int argc, char *argv[]){
//    if(argc == 2)
//        print_list(atoi(argv[1]));
//    print_list(head);
//    else
//        printf("Please state an interger number.\n");
//    return 0;
//}
