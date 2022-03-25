/*
		BubbleSort este o metoda de sortare usor de inteles ,el fiind un algoritm 
 foarte simplu.El si daca este optimizat si daca nu are o complexitate patratica
 O(n^2), nefiind cea mai buna metoda de sortare. In medie, eficienta acestui 
 algoritm reprezinta 70% din caea a sortarii prin selectie. ESTE STABIL.
		Selection sort este un algoritm de  sortare care atat in caz mediu
statisti cat si in caz defavorabil are complexitate O(n^2).Eu consider ca el face 
foarte multe atribuiri si comparatii, in majoritatea cazurilor dand rezultate mai 
slabe ca si insertion sort si ca bubble sort.ESTE STABIL
		La insertion sort depinzand de modul i care este implementat el poate fi 
sau nu poate fi stabil. Este stabil cand insertia o facem fara cautare  binara
iar cand facem cu cautare binara el nu mai este stabil . Complexitatea lui variaza
in functie de modul de implementare daca este implementat cu cautare binara 
are o complexitate de O(n*logn), iar daca nu are O(n^2) in caz defavorabil

*/
#include<stdio.h>
#include"Profiler.h"
#define MAX_SIZE 10000
#define STEP_SIZE 100
#define NR_TEST 10

Profiler p ("Sortari");

void schimba(int* a, int* b)
{
	int temp = *a;
	*a = *b;
	*b = temp;
}

void BubbleSort(int a[], int n) {
	Operation opComp = p.createOperation("bubbleSort-comp", n);
	Operation opAttr = p.createOperation("bubbleSort-attr", n);
	int cont;
	do {
		cont = 0;
		for (int j = 0; j < n - 1; j++) {
			
		   opComp.count();
			if (a[j] > a[j + 1]) {
				cont = 1;
				opAttr.count(3);
				schimba(&a[j], &a[j + 1]);
				
				}
			}
		
	} while (cont==1);
}
void InsertionSort(int a[], int n) {
	Operation opComp = p.createOperation("insertionSort-comp", n);
	Operation opAttr = p.createOperation("insertionSort-attr", n);
	int x, i, j;
	for ( i = 1; i < n; i++) {
		opAttr.count();
		 x = a[i];
		 j = i - 1;
		 opComp.count();
		 while (j >= 0 && a[j] > x) {
			    opAttr.count();
				 a[j + 1] = a[j];
				 j --;
			 }
		 opAttr.count();
		 a[j + 1] = x;
	}
}
void FindMin(int i, int a[],int n, int* min, int* comp) {
	*min = i;
	for (int j = i + 1; j < n; j++) {
		(*comp)++;
		if (a[*min] > a[j]) *min = j;
	}
}
void SelectionSort(int a[], int n) {
	Operation opComp = p.createOperation("selectionSort-comp", n);
	Operation opAttr = p.createOperation("selectionSort-attr", n);
	int i=0,min;
	while (i < n) {
		int comp = 0;
		FindMin(i, a, n, &min ,&comp);
		opComp.count(comp);
		if (min != i) {
			schimba(&a[i], &a[min]);
			opAttr.count(3);
		}
		i++;
	}
}
void demo()
{
	int v1[] = { 7,2,8,5,9,1,6 };
	int n = sizeof(v1) / sizeof(v1[0]);
	int v2[] = { 7,2,8,5,9,1,6 };
	int v3[] = { 7,2,8,5,9,1,6 };
	
	printf("Sirul nesortat: \n");
	for (int i = 0; i < n; i++)
		printf("%d ", v1[i]);
	printf("\n");


	BubbleSort(v1, n);
	InsertionSort(v2, n);
	SelectionSort(v3, n);

	printf("BubbleSort: \n");	
	for (int i=0;i<n;i++)
	printf("%d ",v1[i]);
	printf("\n");

	printf("InsertionSort: \n");
	for (int i = 0; i < n; i++)
		printf("%d ", v2[i]);
	printf("\n");
	printf("SelectionSort: \n");
	for (int i = 0; i < n; i++)
		printf("%d ", v3[i]);
	printf("\n");

}
void copiere(int a[], int b[], int n) {
	for (int i = 0; i < n; i++) {
		b[i] = a[i];
	}
}
void perf(int order)
{
	int v1[MAX_SIZE], v3[MAX_SIZE], v2[MAX_SIZE];
	int n;
	int min1 = 0, min2 = 0;
	for (n = STEP_SIZE; n <= MAX_SIZE; n += STEP_SIZE)
	{
		for (int test = 0; test < NR_TEST; test++) {
			FillRandomArray(v1, n, 10, 50000, false, order);
			copiere(v1, v2, n);
			copiere(v1, v3, n);
			BubbleSort(v1, n);
			InsertionSort(v2, n);
			SelectionSort(v3, n);
		}
	}
	p.divideValues("bubbleSort-attr", NR_TEST);
	p.divideValues("bubbleSort-comp", NR_TEST);
	p.addSeries("bubbleSort", "bubbleSort-attr", "bubbleSort-comp");
	p.divideValues("insertionSort-attr", NR_TEST);
	p.divideValues("insertionSort-comp", NR_TEST);
	p.addSeries("insertionSort", "insertionSort-attr", "insertionSort-comp");
	p.divideValues("selectionSort-attr", NR_TEST);
	p.divideValues("selectionSort-comp", NR_TEST);
	p.addSeries("selectionSort", "selectionSort-attr", "selectionSort-comp");

	/*p.createGroup("atribuiri ", "bubbleSort-attr", "insertionSort-attr", "selectionSort-attr");
	p.createGroup("comparatii", "bubbleSort-comp", "insertionSort-comp", "selectionSort-comp");
	p.createGroup("total", "bubbleSort", "insertionSort", "selectionSort");
	*/
	p.createGroup("atribuiri_bubbleSort ", "bubbleSort-attr");
	p.createGroup("atribuiri_insertionSort ", "insertionSort-attr");
	p.createGroup("atribuiri_selectionSort",  "selectionSort-attr");
	p.createGroup("comparatii_bubbleSort ", "bubbleSort-comp");
	p.createGroup("comparatii_insertionSort ", "insertionSort-comp");
	p.createGroup("comparatii_selectionSort", "selectionSort-comp");
	p.createGroup("total_bubbleSort ", "bubbleSort");
	p.createGroup("total_insertionSort ", "insertionSort");
	p.createGroup("total_selectionSort", "selectionSort");

}
void perf_all() {
	perf(UNSORTED);
	p.reset("best");
	perf(ASCENDING);
	p.reset("worst");
	perf(DESCENDING);
	p.showReport();


}
int main() {
	demo();
	//perf_all();
	return 0;
}