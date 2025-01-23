//Amiran Dugiev 2019-09-03
//This function reverses the char array iteratively

#include <stdio.h>
#include <stdlib.h>

int main()
{
    reverse_char();
    return 0;
}

void reverse_char()
{
    char c[10] = "";
    for(int i = 0; i < 10; i++)
    {
        c[i] = getchar();
        if(c[i] == '\n')
        {
            break;
        }
    }
    for(int i = 9; i >= 0; i--)
    {
        putchar(c[i]);
    }
    return;
}
