//Labb2Asgn5 Asmiran Dugiev 2019-09-18
//This program puts all the negative elements of an array to the left and prints it
//Positions the negative elements to the left in an array

// Invariant: arr[0..j-1] contains the same elements as
// the original subarray arr[0..j-1], but with negative elements first
void rearrange(int arr[], int n)
{
    int j = 0, temp = 0;
    for (int i = 0; i < n; i++) {
        if (arr[i] < 0) {
            if (i != j)
            {
                temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            }
            j++;
        }
    }
}

// Function that prints the Array
void printArray(int arr[], int n)
{
    for (int i = 0; i < n; i++)
        printf("%d ", arr[i]);
}

// Main Method
int main()
{
    int arr[] = { -1, 2, -3, 4, 5, 6, -7, 8, 9 };
    int n = sizeof(arr) / sizeof(arr[0]);
    rearrange(arr, n);
    printArray(arr, n);
    return 0;
}
