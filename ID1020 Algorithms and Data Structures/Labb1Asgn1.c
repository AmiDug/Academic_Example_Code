//Amiran Dugiev 2019-09-03
//This function reverses the char array recursively

#include <stdio.h>
#include <stdlib.h>

int main()
{
    reverse_char();
    return 0;
}

void reverse_char()
{
    char c = getchar();
    if (c == '\n')
    {
        return;
    }
    else
    {
        reverse_char();
        putchar(c);
    }
}
