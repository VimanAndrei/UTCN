#include<stdlib.h>
#include"Profiler.h"

#define MAX_SIZE_N 10000
#define MAX_K 500
#define STEP_SIZE_N 100
#define STEP_SIZE_K 10
#define NR_TEST 10

using namespace std;

Profiler m("merge");

//DEFINIRE LISTA simplu inlantuita si operatiile pe aceasta
typedef struct node {
    int val;
    struct node* next;
} Node;

typedef struct list {
    Node* first;
    Node* last;
} List;

Node* newNode(int key){
    Node* n = (Node*)malloc(sizeof(Node));
    if (n != NULL)
    {
        n->val = key;
        n->next = NULL;
    }
    return n;
}

List* newList(){
    List* L = (List*)malloc(sizeof(List));
    if (L != NULL){
        L->first = NULL;
        L->last = NULL;
    }
    return L;
}

void InsertLast(List* L, Node* n){
    if (L->first == NULL){
        L->first = n;
        L->last = n;
    }
    else {
        L->last->next = n;
        L->last = n;
    }
}

void PrintForward(List* L) {
    Node* p = L->first;
    while (p != NULL) {
        printf("%d ", p->val);
        p = p->next;
    }
    printf("\n");
}

void FreeList(List* L){
    Node* p = L->first;
    Node* trash;
    while (p != NULL){
        trash = p;
        p = p->next;
        free(trash);
    }
    free(L);
}

//min_heapify si build heap

void heapify(Node* arry[], int n, int i, int k,char name[]){
    Operation total = m.createOperation(name, k);
    int smallest = i;
    int st = 2 * i;
    int dr = 2 * i + 1;

    total.count();
    if (st <= n && arry[st]->val < arry[smallest]->val){
                 smallest = st;
    }

    total.count();
    if (dr <= n && arry[dr]->val < arry[smallest]->val){
                  smallest = dr;
    }

    total.count();
    if (smallest != i){
                        total.count(3);
                        swap (arry[i], arry[smallest]);
                        heapify(arry, n, smallest, k,name);
    }
}

void buildHeap(Node* arry[], int n, int k ,char name[]){
      int indexStart = (n / 2);
      for (int i = indexStart; i >= 1; --i) {
          heapify(arry, n, i, k, name);
      }
}

// interclasarea a k liste de dimensiune n

List* interclasare(List* L[MAX_K],int n, int k,int kk, char name[]){
    Operation total = m.createOperation(name, kk);
    List* out = newList();
    Node* heap[MAX_K];

    for (int i = 0; i < k; ++i) 
        heap[i + 1] = L[i]->first;

    buildHeap(heap, k, kk, name);
    while (k > 0){ 
        total.count();
        InsertLast(out, heap[1]);
        total.count();
        heap[1] = heap[1]->next;

        if (heap[1] == NULL){
            total.count(3);
            swap(heap[1], heap[k]);
            k--;
        }
        heapify(heap, k, 1, kk,name);
    }
    return out;
}

void demo(){
    List* L_demo[MAX_K];   
    int arry[10000];
    int kk = 3, k = 3,n=9;
    char nume[] = "demo";
        
 
    for (int i = 0; i < k; ++i) {
        L_demo[i] = newList();
        FillRandomArray(arry, n, 0, 100, false, ASCENDING);
        for (int j = 0; j < n; j++)
            InsertLast(L_demo[i], newNode(arry[j]));
    }

    printf("Listele sunt: \n");
    for (int i = 0; i < k; ++i) {
        PrintForward(L_demo[i]);
    }

    printf("Lista ordonata este: \n");
    List* lista_rezultat = interclasare(&(*L_demo),n, k, kk, nume);
    PrintForward(lista_rezultat);
}


void pref(List* L[MAX_K],int k,char name[]){
    int arry[MAX_SIZE_N];

    for (int i = 1; i <= NR_TEST; i++){
        for (int n = STEP_SIZE_N ; n <= MAX_SIZE_N; n += STEP_SIZE_N){
            for (int j = 0; j < k; j++){
                L[j] = newList();
                FillRandomArray(arry, n, 10, 50000, false, ASCENDING);
                for (int l = 0; l< n; l++)
                    InsertLast(L[j], newNode(arry[l]));
            }
            List* result = interclasare(&(*L), n, k, n, name);
            
            FreeList(result);
            for (int i = 0; i < k; ++i)
            {
                L[i]->first = NULL;
                L[i]->last = NULL;
            }
        }
    }

}

void pref1(List* L[MAX_K],char name[]){
    int arry[MAX_SIZE_N];

    for (int i = 1; i <= NR_TEST; i++) {
        for (int kk = STEP_SIZE_K; kk <= MAX_K; kk += STEP_SIZE_K) {
            for (int j = 0; j < kk; j++) {
                L[j] = newList();
                FillRandomArray(arry, MAX_SIZE_N, 10, 50000, false, ASCENDING);
                for (int l = 0; l < MAX_SIZE_N; l++)
                    InsertLast(L[j], newNode(arry[l]));
            }
            List* result = interclasare(&(*L), MAX_SIZE_N, kk, kk, name);

            FreeList(result);
            for (int i = 0; i < kk; ++i)
            {
                L[i]->first = NULL;
                L[i]->last = NULL;
            }
        }
    }

}

void pref_all() {

    List* lists[MAX_K];
    char nume1[] = "k=5";
    char nume2[] = "k=10";
    char nume3[] = "k=100";
    char nume4[] = "k_variabil";

    pref(&(*lists),5,nume1);
    pref(&(*lists),10,nume2);
    pref(&(*lists),100,nume3);
    m.divideValues(nume1, NR_TEST);
    m.divideValues(nume2, NR_TEST);
    m.divideValues(nume3, NR_TEST);
    m.createGroup("Interclasare cu k constant", nume1, nume2, nume3);

    pref1(&(*lists), nume4);
    m.divideValues(nume4, NR_TEST);
    m.createGroup("Interclasare cu k variabil",nume4);

    m.showReport();
}


int main() {
    demo();
    //pref_all();
}
