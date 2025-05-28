#include <stdio.h>

int main()
{
    FILE *input_file, *output_file;
    char ch, next_ch;
    int inside_single_line_comment = 0;
    int inside_multi_line_comment = 0;


    input_file = fopen("input.c", "r");
    if (input_file == NULL)
    {
        printf("Could not open the input file.\n");
        return 1;
    }


    output_file = fopen("output.txt", "w");
    if (output_file == NULL)
    {
        printf("Could not open the output file.\n");
        fclose(input_file);
        return 1;
    }


    while ((ch = fgetc(input_file)) != EOF)
    {

        if (ch == '/' && !inside_multi_line_comment)
        {
            next_ch = fgetc(input_file);
            if (next_ch == '/')
            {
                 inside_single_line_comment = 1;
            }
            else if (next_ch == '*')
            {
                inside_multi_line_comment = 1;
            }
            else
            {
                fputc(ch, output_file);
                fputc(next_ch, output_file);
            }
        }

        else if (inside_single_line_comment)
        {
            if (ch == '\n')
            {
                inside_single_line_comment = 0;
                fputc(ch, output_file);
            }
        }

        else if (inside_multi_line_comment)
        {
            if (ch == '*' && (next_ch = fgetc(input_file)) == '/')
            {
                inside_multi_line_comment = 0;
            }
        }

        else
        {
            fputc(ch, output_file);
        }
    }

    printf("Comments removed successfully.\n");


    fclose(input_file);
    fclose(output_file);

    return 0;
}

