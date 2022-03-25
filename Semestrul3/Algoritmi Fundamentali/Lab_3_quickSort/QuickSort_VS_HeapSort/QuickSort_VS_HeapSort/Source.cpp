#include<stdio.h>
#include"Profiler.h"
#define MAX_SIZE 10000
#define STEP_SIZE 100
#define NR_TEST 10
Profiler h ("QickSortVsHeapsort");

void schimba(int* a, int* b)
{
    int temp = *a;
    *a = *b;
    *b = temp;
}

//HEAPSORT
void heapify(int arry[], int n, int i)
{
    Operation opC = h.createOperation("bottomupcomp", n);
    Operation opA = h.createOperation("bottomupattr", n);
    int largest = i;
    int st = 2 * i + 1;
    int dr = 2 * i + 2;
    opC.count();
    if (st <n && arry[st] > arry[largest]) {
        opA.count();
        largest = st;
    }
    opC.count();
    if (dr < n && arry[dr] > arry[largest]) {
        opA.count();
        largest = dr;
    }
    opC.count();
    if (largest != i) {
        schimba(&arry[i], &arry[largest]);
        opA.count(3);

        heapify(arry, n, largest);
    }
}

void buildHeap_bottom_up(int arry[], int n)
{
    int indexStart = (n / 2) - 1;

    for (int i = indexStart; i >= 0; i--) {
        heapify(arry, n, i);
    }
}

void printHeap(int arry[], int n)
{
    printf("Array is:\n");

    for (int i = 0; i < n; ++i)
        printf("%d ", arry[i]);
    printf("\n");
}
void heapSort_bottom_up(int arr[], int n)
{

    buildHeap_bottom_up(arr, n);

    for (int i = n - 1; i > 0; i--)
    {

        schimba(&arr[0], &arr[i]);
        heapify(arr, i, 0);
    }
}

///QUICKSORT

int partition(int arr[], int p, int r) {
    int x = arr[p], i = p - 1, j = r + 1;
    while (i <= j) {

        do {
            j = j - 1;
        } while (arr[j] <= x);

        do {
                i = i + 1;
        } while (arr[i] >= x);

        if (i < j) {
            schimba(&arr[i], &arr[j]);
        }
         else return j;
        
    }
}

void quickSort(int arr[], int p, int r) {
    if (p < r) {
        int q = partition(arr, p, r);
        quickSort(arr, p, q);
        quickSort(arr, q + 1, r);
    }
}

/// 
/// teste
/// 
void demo() {
    int a1[] = { 4,8,4,0,12,1,100,90,4,7,8, };
    int a2[] = { 4,8,4,0,12,1,100,90,4,7,8, };
    int n = sizeof(a1) / sizeof(a1[1]);
    heapSort_bottom_up(a1, n);
    quickSort(a2, 0,n-1);
    printHeap(a1,n);
    printHeap(a1, n);


}
void copiere(int a[], int b[], int n) {
    for (int i = 0; i < n; i++) {
        b[i] = a[i];
    }
}
void perf()
{
    int v1[MAX_SIZE], v2[MAX_SIZE];
    int n;
    int min1 = 0, min2 = 0;
    for (n = STEP_SIZE; n <= MAX_SIZE; n += STEP_SIZE)
    {
        for (int test = 0; test < NR_TEST; test++) {
            FillRandomArray(v1, n, 10, 50000);
            copiere(v1, v2, n);
            
            
        }
    }
    h.divideValues("bottomupattr", NR_TEST);
    h.divideValues("bottomupcomp", NR_TEST);
    h.addSeries("bottomuptotal", "bottomupattr", "bottomupcomp");

    h.divideValues("topdownattr", NR_TEST);
    h.divideValues("topdowncomp", NR_TEST);
    h.addSeries("topdowntotal", "topdownattr", "topdowncomp");

    h.createGroup("Total Atribuiri", "bottomupattr", "topdownattr");
    h.createGroup("Total Comparatii", "bottomupcomp", "topdowncomp");
    h.createGroup("Total Operatii", "bottomuptotal", "topdowntotal");
    h.showReport();

}


int main() {
    demo();
    //perf();
    return 0;
}
