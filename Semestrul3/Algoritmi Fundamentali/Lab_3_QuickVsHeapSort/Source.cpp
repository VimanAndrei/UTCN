#include<stdio.h>
#include"Profiler.h"
#define MAX_SIZE 10000
#define STEP_SIZE 100
#define NR_TEST 10

Profiler h("QickSortVsHeapsort");

void schimba(int* a, int* b)
{
    int temp = *a;
    *a = *b;
    *b = temp;
}

//HEAPSORT
void heapify(int arry[], int n, int i,int nn)
{
    Operation opC = h.createOperation("heapSort-comp", nn);
    Operation opA = h.createOperation("heapSort-attr", nn);
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

        heapify(arry, n, largest,nn);
    }
}

void buildHeap_bottom_up(int arry[], int n,int nn)
{
    int indexStart = (n / 2) - 1;

    for (int i = indexStart; i >= 0; i--) {
        heapify(arry, n, i,nn);
    }
}

void printARRY(int arry[], int n)
{
    

    for (int i = 0; i < n; ++i)
        printf("%d ", arry[i]);
    printf("\n");
}
void heapSort_bottom_up(int arr[], int n,int nn)
{
    Operation opA = h.createOperation("heapSort-attr", nn);
    buildHeap_bottom_up(arr, n, nn);

    for (int i = n - 1; i > 0; i--)
    {
        opA.count(3);
        schimba(&arr[0], &arr[i]);
        heapify(arr, i, 0,nn);
    }
}

///QUICKSORT

int partition(int arr[], int p, int r,int nn) {
    Operation opC = h.createOperation("quickSort-comp", nn);
    Operation opA = h.createOperation("quickSort-attr", nn);
    opA.count(3);
    int x = arr[p], i = p - 1, j = r + 1;

    while (i <= j) {
        opC.count();

        do {
            opA.count();
            j = j - 1;
            opC.count();
        } while (arr[j] >= x && j > p);

        do {
            opA.count();
            i = i + 1;
            opC.count();
        } while (arr[i] <= x && i < r);

        opC.count();
        opA.count(3);
        if (i < j) {
            
            schimba(&arr[i], &arr[j]);
        }
        else {
            schimba(&arr[p], &arr[j]);
            return j;
        }

    }

}

void quickSort(int arr[], int p, int r,int nn) {
    Operation opC = h.createOperation("quickSort-comp", nn);
    Operation opA = h.createOperation("quickSort-attr", nn);
    opC.count();
    if (p < r) {
        opA.count();
        int q = partition(arr, p, r, nn);
        quickSort(arr, p, q,nn);
        quickSort(arr, q + 1, r,nn);
        
    }
}
///
///quickSelect
/// 
int RandomizedPartition(int arr[], int p, int r, int nn) {
    int i = p +( rand() % (r - p + 1));
        schimba(&arr[r], &arr[i]);
        return partition(arr, p, r, nn);
}
int quickSelect(int arr[], int p, int r, int i, int nn) {
    if (p == r) return arr[p];
    int q = RandomizedPartition(arr, p, r, nn);
    int k = q - p +1 ;
    if (i == k) return arr[q];
    else if (i < k) return quickSelect(arr, p, q - 1, 4, nn);
    else  quickSelect(arr, q + 1, r, i - k,nn);

}

/// 
/// teste
/// 
void demo() {

    int a1[] = { 193, 27, 105, 158, 56, 174, 169, 98, 118, 113, 9, 146, 69, 55, 86, 185, 167, 46, 172, 107 };
    int a2[] = { 193, 27, 105, 158, 56, 174, 169, 98, 118, 113, 9, 146, 69, 55, 86, 185, 167, 46, 172, 107 };
    int a3[] = { 193, 27, 105, 158, 56, 174, 169, 98, 118, 113, 9, 146, 69, 55, 86, 185, 167, 46, 172, 107 };
    int n = sizeof(a1) / sizeof(a1[0]);
    printf("Initial array is:\n");
    printARRY(a1, n);
    printf("\nQuickSelect min is:%d \n", quickSelect(a3, 0, n-1, n , n));
    printf("QuickSelect array is:\n");
    printARRY(a3, n);
    heapSort_bottom_up(a1, n, n);
    quickSort(a2, 0, n-1 ,n);
    printf("\n HeapSort array is:\n");
    printARRY(a1, n);
    printf("QuickSort array is:\n");
    printARRY(a2, n);


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
            FillRandomArray(v1, n, 10, 50000,false,2);
            copiere(v1, v2, n);
            heapSort_bottom_up(v1, n, n);
            quickSort(v2,0, n - 1, n);

        }
    }
    h.divideValues("heapSort-attr", NR_TEST);
    h.divideValues("heapSort-comp", NR_TEST);
    h.addSeries("heapSort", "heapSort-attr", "heapSort-comp");

    h.divideValues("quickSort-attr", NR_TEST);
    h.divideValues("quickSort-comp", NR_TEST);
    h.addSeries("quickSort", "quickSort-attr", "quickSort-comp");

    h.createGroup("TotalAtribuiri", "heapSort-attr", "quickSort-attr");
    h.createGroup("TotalComparatii", "heapSort-comp", "quickSort-comp");
    h.createGroup("TotalOperatii", "heapSort" , "quickSort");
    h.showReport();

}

int main() {
    demo();
//  perf();
    return 0;
}
