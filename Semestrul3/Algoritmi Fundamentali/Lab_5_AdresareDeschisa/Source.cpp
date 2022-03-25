#include <stdio.h>
#include <stdlib.h>
#include<math.h>
#include "Profiler.h"
Profiler table("TabeldeDeDispersie");
int a = 5, b = 7;
#define TABLE_LENGTH 10007 
#define NR_TESTS 10

typedef struct cell
{
    int key;
    int status;
} Cell;

enum { FREE, OCCUPIED };

void afisare(Cell *T, int m)
{
    printf("\nTabela de dispersie este \n");
    for (int i = 0; i < m; i++)
    {
        if(T[i].status == OCCUPIED)
            printf("T[%d]=%d\n",i,T[i]);
        else
            printf("T[%d]= --\n",i);
    }
}
void insert_key1(int k, Cell* T, int m, int (*hash_func)(int k, int m, int i))
{
    int i;
    for (i = 0; i < m; i++) {
        int h = hash_func(k, m, i);
        if (T[h].status == FREE) {
            T[h].key = k;
            T[h].status = OCCUPIED;
            return;
        }
    }
     printf("Nu putem insera cheia %d!\n",k);
}

int insert_key(int k, Cell *T, int m, int (*hash_func)(int k, int m, int i))
{
    int i;
    for(i = 0; i < m; i++) {
        int h = hash_func(k, m, i);
        if (T[h].status== FREE) {
            T[h].key = k;
            T[h].status = OCCUPIED;
            return 1;
        }
    }
    return-1;
  
}

int Search (int k, Cell* T, int m, int (*hash_func)(int k, int m, int i),Operation &op)
{
    int h;
    int i;
    for (i = 0; i < m; i++) {
        h = hash_func(k, m, i);
        op.count();
        if (T[h].key== k ) {
            return h;
        }
        if (T[h].status == FREE) {
            return -1;
        }
       
    }
    return -1;
}
int h_prime(int k, int m)
{
  return k%m;
}

//returneaza pozitia la care se va verifica tabela de dispersie folosind verificarea liniara

int quadratic_probing(int k, int m, int i)
{
  return (h_prime(k,m)+a*i+b*(i*i))%m;
}

void set_table_free(Cell *T, int m)
{
    //initializam tabela
    int i;
    for (i = 0; i<m; i++)
    {
        T[i].status = FREE;
    }
}
void demo() {
    int m = 23;
    Cell* T = (Cell*)malloc(m * sizeof(Cell)); //tabela de dispersie - se aloca

   
    int vals[100];
    
    FillRandomArray(vals, m, 0, 500, true, ASCENDING);

    printf("Tabloul sortat random este:\n");
    for (int i = 0; i <m-2; i++)
        printf("%d ", vals[i]);
    printf("\n");

    set_table_free(T, m);
    for (int i = 0; i < m-2 ; i++)
        insert_key1(vals[i], T, m, quadratic_probing);
    afisare(T, m);
    //(k + 5*i + 7*i*i) mod m
    free(T);
   
}


int maximgasite[5] = { 0 };
int maximnegasite[5] = { 0 };

void caz() {
    printf("  FactorDeUmplere    EfortMediuGasite   EfortMaximGasite  EfortMediu ne-gasite   EfortMaxim ne-gasite \n");
}
void cazUnu() {
    Cell* T = (Cell*)malloc(TABLE_LENGTH * sizeof(Cell));
    int randSir[TABLE_LENGTH];
    Operation opFound = table.createOperation("Efort mediu gasite", 80);
    Operation opNotFound = table.createOperation("Efort mediu negasite", 80);
    int NumereInserate[TABLE_LENGTH] = { 0 };

    int poz;
    for (int i = 0; i < NR_TESTS; i++) {
        FillRandomArray(randSir, TABLE_LENGTH, 100, 50000, true, ASCENDING);
        set_table_free(T, TABLE_LENGTH);
        poz = 0;
        for (int j = 0; j < 8000; j++)
        {

            if (insert_key(randSir[j], T, TABLE_LENGTH, quadratic_probing) == 1) {
                NumereInserate[poz] = randSir[j];
                poz++;
            }
        }
     
        int last = opFound.get();
        if (poz > 1500) {
            for (int j = 0; j < 1500; j++)
            {

                int idToSearch = NumereInserate[rand() % (poz)];
                Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opFound);
                int diff = opFound.get() - last;
                last = opFound.get();
                maximgasite[0] = max(maximgasite[0], diff);
            }
            table.divideValues("Efort mediu gasite", 1500);

        }
        else {
            for (int j = 0; j < poz; j++)
            {

                int idToSearch = NumereInserate[rand() % (poz)];
                Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opFound);
                int diff = opFound.get() - last;
                last = opFound.get();
                maximgasite[0] = max(maximgasite[0], diff);
            }
            table.divideValues("Efort mediu gasite", poz);
        }
        
        last = opNotFound.get();
        for (int j = 1; j <= 1500; ++j)
        {
            int idToSearch = j + 50000;
            Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opNotFound);
            int diff = opNotFound.get() - last;
            last = opNotFound.get();
            maximnegasite[0] = max(maximnegasite[0], diff);
        }

        table.divideValues("Efort mediu negasite", 1500);


    }

    printf("      0.80:                 %d               %d                   %d                       %d           \n",  opFound.get(), maximgasite[0], opNotFound.get(), maximnegasite[0]);
}

void cazDoi(){
    Cell* T = (Cell*)malloc(TABLE_LENGTH * sizeof(Cell));
    int randSir[TABLE_LENGTH];
    Operation opFound = table.createOperation("Efort mediu gasite", 85);
    Operation opNotFound = table.createOperation("Efort mediu negasite", 85);
    int NumereInserate[TABLE_LENGTH] = { 0 };
    int poz;

    for (int i = 0; i < NR_TESTS; i++) {
        FillRandomArray(randSir, TABLE_LENGTH, 100, 50000, true, ASCENDING);
        set_table_free(T, TABLE_LENGTH);
        poz = 0;
        for (int j = 0; j < 8500; j++)
        {

            if (insert_key(randSir[j], T, TABLE_LENGTH, quadratic_probing) == 1) {
                NumereInserate[poz] = randSir[j];
                poz++;
            }
        }
        Operation opFound = table.createOperation("Efort mediu gasite", 85);
        int last = opFound.get();
        if (poz > 1500) {
            for (int j = 0; j < 1500; j++)
            {

                int idToSearch = NumereInserate[rand() % (poz)];
                Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opFound);
                int diff = opFound.get() - last;
                last = opFound.get();
                maximgasite[1] = max(maximgasite[1], diff);
            }
            table.divideValues("Efort mediu gasite", 1500);

        }
        else
        {
            for (int j = 0; j < poz; j++)
            {

                int idToSearch = NumereInserate[rand() % (poz)];
                Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opFound);
                int diff = opFound.get() - last;
                last = opFound.get();
                maximgasite[1] = max(maximgasite[1], diff);
            }
            table.divideValues("Efort mediu gasite", poz);
        }

        Operation opNotFound = table.createOperation("Efort mediu negasite", 85);
        last = opNotFound.get();
        for (int j = 1; j <= 1500; ++j)
        {
            int idToSearch = j + 50000;
            Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opNotFound);
            int diff = opNotFound.get() - last;
            last = opNotFound.get();
            maximnegasite[1] = max(maximnegasite[1], diff);
        }

        table.divideValues("Efort mediu negasite", 1500);


    }
    printf("      0.85:                 %d               %d                   %d                       %d           \n", opFound.get(), maximgasite[1], opNotFound.get(), maximnegasite[1]);
}
void cazTrei() {
    Cell* T = (Cell*)malloc(TABLE_LENGTH * sizeof(Cell));
    int randSir[TABLE_LENGTH];
    Operation opFound = table.createOperation("Efort mediu gasite", 90);
    Operation opNotFound = table.createOperation("Efort mediu negasite", 90);
    int NumereInserate[TABLE_LENGTH] = { 0 };
    int poz;
    for (int i = 0; i < NR_TESTS; i++) {
        FillRandomArray(randSir, TABLE_LENGTH, 100, 50000, true, ASCENDING);
        set_table_free(T, TABLE_LENGTH);
        poz = 0;
        for (int j = 0; j < 9000; j++)
        {

            if (insert_key(randSir[j], T, TABLE_LENGTH, quadratic_probing) == 1) {
                NumereInserate[poz] = randSir[j];
                poz++;
            }
        }
        Operation opFound = table.createOperation("Efort mediu gasite", 90);
        int last = opFound.get();
        if (poz > 1500) {
            for (int j = 0; j < 1500; j++)
            {

                int idToSearch = NumereInserate[rand() % (poz)];
                Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opFound);
                int diff = opFound.get() - last;
                last = opFound.get();
                maximgasite[2] = max(maximgasite[2], diff);
            }
            table.divideValues("Efort mediu gasite", 1500);

        }
        else
        {
            for (int j = 0; j < poz; j++)
            {

                int idToSearch = NumereInserate[rand() % (poz)];
                Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opFound);
                int diff = opFound.get() - last;
                last = opFound.get();
                maximgasite[2] = max(maximgasite[2], diff);
            }
            table.divideValues("Efort mediu gasite", poz);
        }

        Operation opNotFound = table.createOperation("Efort mediu negasite", 90);
        last = opNotFound.get();
        for (int j = 1; j <= 1500; ++j)
        {
            int idToSearch = j + 50000;
            Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opNotFound);
            int diff = opNotFound.get() - last;
            last = opNotFound.get();
            maximnegasite[2] = max(maximnegasite[2], diff);
        }

        table.divideValues("Efort mediu negasite", 1500);


    }
    printf("      0.90:                 %d               %d                   %d                       %d           \n", opFound.get(), maximgasite[2], opNotFound.get(), maximnegasite[2]);
}
void cazPatru() {
    Cell* T = (Cell*)malloc(TABLE_LENGTH * sizeof(Cell));
    int randSir[TABLE_LENGTH];
    Operation opFound = table.createOperation("Efort mediu gasite", 95);
    Operation opNotFound = table.createOperation("Efort mediu negasite", 95);
    int NumereInserate[TABLE_LENGTH] = { 0 };
    int poz;
    for (int i = 0; i < NR_TESTS; i++) {
        FillRandomArray(randSir, TABLE_LENGTH, 100, 50000, true, ASCENDING);
        set_table_free(T, TABLE_LENGTH);
        poz = 0;
        for (int j = 0; j < 9500; j++)
        {

            if (insert_key(randSir[j], T, TABLE_LENGTH, quadratic_probing) == 1) {
                NumereInserate[poz] = randSir[j];
                poz++;
            }
        }
        Operation opFound = table.createOperation("Efort mediu gasite", 95);
        int last = opFound.get();
        if (poz > 1500) {
            for (int j = 0; j < 1500; j++)
            {

                int idToSearch = NumereInserate[rand() % (poz)];
                Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opFound);
                int diff = opFound.get() - last;
                last = opFound.get();
                maximgasite[3] = max(maximgasite[3], diff);
            }
            table.divideValues("Efort mediu gasite", 1500);

        }
        else
        {
            for (int j = 0; j < poz; j++)
            {

                int idToSearch = NumereInserate[rand() % (poz)];
                Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opFound);
                int diff = opFound.get() - last;
                last = opFound.get();
                maximgasite[3] = max(maximgasite[3], diff);
            }
            table.divideValues("Efort mediu gasite", poz);
        }

        Operation opNotFound = table.createOperation("Efort mediu negasite", 95);
        last = opNotFound.get();
        for (int j = 1; j <= 1500; ++j)
        {
            int idToSearch = j + 50000;
            Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opNotFound);
            int diff = opNotFound.get() - last;
            last = opNotFound.get();
            maximnegasite[3] = max(maximnegasite[3], diff);
        }

        table.divideValues("Efort mediu negasite", 1500);


    }
    printf("      0.95:                 %d               %d                   %d                       %d           \n", opFound.get(), maximgasite[3], opNotFound.get(), maximnegasite[3]);
}
void cazCinci() {
    Cell* T = (Cell*)malloc(TABLE_LENGTH * sizeof(Cell));
    int randSir[TABLE_LENGTH];
    Operation opFound = table.createOperation("Efort mediu gasite", 99);
    Operation opNotFound = table.createOperation("Efort mediu negasite", 99);
    int NumereInserate[TABLE_LENGTH] = { 0 };
    int poz;
    for (int i = 0; i < NR_TESTS; i++) {
        FillRandomArray(randSir, TABLE_LENGTH, 100, 50000, true, ASCENDING);
        set_table_free(T, TABLE_LENGTH);
        poz = 0;
        for (int j = 0; j < 9990; j++)
        {

            if (insert_key(randSir[j], T, TABLE_LENGTH, quadratic_probing) == 1) {
                NumereInserate[poz] = randSir[j];
                poz++;
            }
        }
        Operation opFound = table.createOperation("Efort mediu gasite", 99);
        int last = opFound.get();
        if (poz > 1500) {
            for (int j = 0; j < 1500; j++)
            {

                int idToSearch = NumereInserate[rand() % (poz)];
                Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opFound);
                int diff = opFound.get() - last;
                last = opFound.get();
                maximgasite[4] = max(maximgasite[4], diff);
            }
            table.divideValues("Efort mediu gasite", 1500);

        }
        else
        {
            for (int j = 0; j < poz; j++)
            {

                int idToSearch = NumereInserate[rand() % (poz)];
                Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opFound);
                int diff = opFound.get() - last;
                last = opFound.get();
                maximgasite[4] = max(maximgasite[4], diff);
            }
            table.divideValues("Efort mediu gasite", poz);
        }

        Operation opNotFound = table.createOperation("Efort mediu negasite", 99);
        last = opNotFound.get();
        for (int j = 1; j <= 1500; ++j)
        {
            int idToSearch = j + 50000;
            Search(idToSearch, T, TABLE_LENGTH, quadratic_probing, opNotFound);
            int diff = opNotFound.get() - last;
            last = opNotFound.get();
            maximnegasite[4] = max(maximnegasite[4], diff);
        }

        table.divideValues("Efort mediu negasite", 1500);


    }
    printf("      0.99:                 %d               %d                 %d                     %d           \n", opFound.get(), maximgasite[4], opNotFound.get(), maximnegasite[4]);
}

void perf() { 
   
    caz();
    cazUnu();
    cazDoi();
    cazTrei();
    cazPatru();
    cazCinci();



    
}

int main()
{
    demo();
   // perf();
    return 0;
}