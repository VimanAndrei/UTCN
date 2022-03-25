#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Profiler.h"
#define NR_TESTS 10
#define MAX_SIZE 10000
#define STEP_SIZE 100

Profiler p("Multimi Disjuncte");


struct Edge {
    int nod1;
    int nod2;
    int weight;
};

struct Graph {
    int nrVarfuri, nrMuchii;

     Edge* edge;
};

struct subset {
    int parinte;
    int rank;
};
Graph* newGraph(int V, int M) {

    Graph* graf = new Graph;
    graf->nrVarfuri = V;
    graf->nrMuchii= M;
    graf->edge = new struct Edge[M];

    return graf;
}
subset* MakeSets(int nrVarfuri, Operation opm) {
    subset* subseturi = ( subset*)malloc(nrVarfuri * sizeof( subset));
   
    for (int i = 0; i < nrVarfuri; i++) {
        opm.count(3);
        subseturi[i].parinte = i;
        subseturi[i].rank= 0;
    }
    return subseturi;
}
int FindSet(subset subseturi[],int x,Operation opf) {
    opf.count();
    if (subseturi[x].parinte != x) {
        opf.count();
        subseturi[x].parinte = FindSet(subseturi, subseturi[x].parinte,opf);
    }
    return  subseturi[x].parinte;

}
void Link(subset subseturi[], int x, int y, Operation opf) {
    opf.count();
    if (subseturi[x].rank > subseturi[y].rank) {
        opf.count();
        subseturi[y].parinte = x;
    }
    else {
        opf.count();
        subseturi[x].parinte = y;
        opf.count();
       if (subseturi[x].rank == subseturi[y].rank) 
        {
            subseturi[y].rank++;
        }
    }
}

void Union(subset subseturi[], int x, int y, Operation opu) {

    Link(subseturi,FindSet(subseturi,x,opu),FindSet(subseturi,y,opu),opu);
}

void schimba(Edge* a, Edge* b)
{
    Edge temp = *a;
    *a = *b;
    *b = temp;
}

int partition(Edge arr[], int p, int r) {
    
  
    int x = arr[p].weight, i = p - 1, j = r + 1;

    while (i <= j) {
        

        do {
           
            j = j - 1;
            
        } while (arr[j].weight >= x && j > p);

        do {
            
            i = i + 1;
           
        } while (arr[i].weight <= x && i < r);

    
        if (i < j) {

            schimba(&arr[i], &arr[j]);
        }
        else {
            schimba(&arr[p], &arr[j]);

            return j;
        }

    }

}

void quickSort(Edge arr[], int p, int r) {
   
    if (p < r) {
        int q = partition(arr, p, r);
        quickSort(arr, p, q);
        quickSort(arr, q + 1, r);

    }
}

Edge* Kruscal(Graph* graf, Operation opu, Operation opf, Operation opm) {

    subset* set = MakeSets(graf->nrVarfuri,opm);  
  
    Edge* rezultat = (Edge*)malloc(graf->nrVarfuri * sizeof(Edge));//declaram sirul final de muchii,stim clar ca o sa fie n-1
  
    quickSort(graf->edge, 0, graf->nrMuchii-1);
         
    
   int poz = 0;
    for (int i = 0; i < graf->nrMuchii && poz < graf->nrVarfuri-1 ; i++) {
        int u = graf->edge[i].nod1;
        int v = graf->edge[i].nod2;
        if (FindSet(set,u,opf ) !=  FindSet(set,v,opf )) {
            rezultat[poz] = graf->edge[i];
            Union(set, u, v,opu);
            poz++;
        }
    }
    

    return rezultat;
}
void demo() {

   

    int nodDePlecare[] =  { 0,0,0,0,1,1,1,2,2,3 };
    int nodDestinatie[] = { 1,2,3,4,2,3,4,3,4,4 };
    int weight[] =        { 9,7,2,1,4,8,3,5,6,1 };
    int nrNoduri = 5;
    int nrMuchii = 10;
    
    
   

    Operation demounion = p.createOperation("demounion", 0);
    Operation demomakeset = p.createOperation("demomakeset", 0);
    Operation demofindset = p.createOperation("demofindset", 0);

    subset* subseturi = MakeSets(nrNoduri, demomakeset);
  
    for (int i = 0; i < nrNoduri; i++) {
        printf("nodul %d  parinte %d rank %d\n",i, subseturi[i].parinte, subseturi[i].rank);
    }
    
    Union(subseturi, 0, 1,demounion);
    Union(subseturi, 0, 2, demounion);
    Union(subseturi, 3, 4, demounion);
       printf("\n");
    for (int i = 0; i < nrNoduri; i++) {
        printf("nodul %d  parinte %d rank %d\n", i, subseturi[i].parinte, subseturi[i].rank);
    }

    Union(subseturi, 0, 4, demounion);
       printf("\n");
    for (int i = 0; i < nrNoduri; i++) {
        printf("nodul %d  parinte %d rank %d\n", i, subseturi[i].parinte, subseturi[i].rank);
    }
    

    Graph* graf = newGraph(nrNoduri, nrMuchii);

    for (int i = 0; i < nrMuchii ; i++) {
        graf->edge[i].nod1 = nodDePlecare[i];
        graf->edge[i].nod2 = nodDestinatie[i];
        graf->edge[i].weight = weight[i];
    }
   
   
    printf("\n\n");
    Edge* drum_cost_min = Kruscal(graf, demounion, demofindset,demomakeset);
   for (int i = 0; i < graf->nrVarfuri - 1; i++) {
       printf("nodul %d  nodul %d cost %d\n", drum_cost_min[i].nod1, drum_cost_min[i].nod2, drum_cost_min[i].weight);
   }

}
void performnce(){
    for (int k = 0; k < NR_TESTS; ++k)
    {
        for (int n = STEP_SIZE; n <= MAX_SIZE; n += STEP_SIZE)
        {
       
        Operation opm = p.createOperation("MakeSet", n);
        Operation opf = p.createOperation("FindSet", n);
        Operation opu = p.createOperation("Union", n);
        int nrNoduri = n, nrMuchii = 4 * n;
        Graph* graf = newGraph(nrNoduri, nrMuchii);

        for (int i = 0; i < nrNoduri; i++) {
            graf->edge[i].nod1 = 0;
            graf->edge[i].nod2 = i;
            graf->edge[i].weight = rand()%50;
        }
        for (int i = nrNoduri; i < nrMuchii; i++) {
            graf->edge[i].nod1 = rand()%nrNoduri;
            graf->edge[i].nod2 = rand() % nrNoduri;
            graf->edge[i].weight = rand() % 50;
        }
        Edge* drum_cost_min = Kruscal(graf, opu, opf ,opm);
         }
    }
p.divideValues("MakeSet", NR_TESTS);
p.divideValues("FindSet", NR_TESTS);
p.divideValues("Union", NR_TESTS);
p.createGroup("OPERATII", "MakeSet", "FindSet", "Union");
p.showReport();
}
int main() {
   
    demo();
   // performnce();

}