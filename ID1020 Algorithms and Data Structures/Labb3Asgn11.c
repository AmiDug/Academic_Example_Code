#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>

int main(void)
{
    FILE *file;
    FILE *writeFile;
    file = fopen("D:\\BansheeBomb\\Memes\\98-0.txt", "r");
    writeFile = fopen("D:\\BansheeBomb\\Memes\\98-0-2.txt", "w");
    char chr;
    while((chr = fgetc(file)) != EOF)
    {
        if((isalpha(chr) != 0 || chr == '\n'))
            fputc(chr,writeFile);
        else
            fputc(32,writeFile);
    }

    fclose(file);
    fclose(writeFile);
    return 0;
}
