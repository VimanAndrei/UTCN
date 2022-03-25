#include <stdio.h>
#include <stdlib.h>
#include "Profiler.h"

#define NR_TESTS 5
#define MAX_SIZE 10000
#define STEP_SIZE 100

Profiler p("ArboreBinarDeCautare");

typedef struct NodeB {
    int key;
    int size = 0;
    struct NodeB* left;
    struct NodeB* right;
    struct NodeB* parent;
} NodeB;

NodeB* newNode(int key, int size)
{
     NodeB* node = (struct NodeB*)malloc(sizeof(struct NodeB));
    node->size = size;
    node->key = key;
    node->left = NULL;
    node->right = NULL;
    node->parent = NULL;
    return node;
}
NodeB* BuildBTS(int Tablou[], int st, int dr,Operation op) {
    op.count();
    if (st > dr) {

        return NULL;
    }
    else {
        int mid;

        mid = (st + dr) / 2;
        op.count();
        NodeB* root = newNode(Tablou[mid], dr - st + 1);
        op.count();
        root->left = BuildBTS(Tablou, st, mid - 1,op);
        op.count();
        root->right = BuildBTS(Tablou, mid + 1, dr,op);
        op.count();
        if (root->left != NULL && root != NULL) {
            op.count();
            root->left->parent = root;

        }
        op.count();
        if (root->right != NULL && root != NULL) {
            op.count();
            root->right->parent = root;

        }

        return root;
    }

}
int size_nod(NodeB* n) {
    if (n != NULL && n->left!=NULL) {
        return n->left->size;
    }
    return 0;
}
NodeB* OS_Select(NodeB* root, int i,Operation op) {
    int r = size_nod(root)+1;
    op.count();
    if (i == r) return root;
    op.count();
    if (i < r)  return OS_Select(root->left, i,op);
    op.count();
    if (i > r) return OS_Select(root->right, i - r,op);


}
NodeB* findMin(NodeB* root, Operation op) {
    struct NodeB* p = root;
    op.count();
    while (p->left != NULL) {
        p = p->left;
        op.count();
    }
    return p;

}


NodeB* succesor(NodeB* root, NodeB* node,Operation op) {
    op.count();
    if (node == NULL)return NULL;
    op.count();
    if (root == NULL)return NULL;
    op.count();
    if (node->right != NULL)return findMin(node->right,op);
    NodeB* succ = NULL;
    op.count();
    while (root != node) {
        op.count();
        if (node->key > root->key)root = root->right;
        else {
            succ = root;
            root = root->left;
        }
        op.count();
    }
    return succ;


}

void calibrare(NodeB* nod) {
    NodeB* p = nod;
    while (p->parent != NULL) {
        p = p->parent;
        (p->size)--;
    }
}


void transplant(NodeB* root, NodeB* u, NodeB* v, Operation op) {
    calibrare(u);
    op.count();
    if (u->parent == NULL) root = v;
    else {
        op.count();
        if (u == u->parent->left) u->parent->left = v;
        else u->parent->right = v;
    }
    op.count();
    if (v != NULL) {
        v->parent = u->parent;
    }
   
    free(u);
}

void stergere(NodeB* root, NodeB* nod, Operation op) {
    op.count();
    if (nod->left == NULL) transplant(root, nod, nod->right, op);
    else {
        op.count();
        if (nod->right == NULL)
            transplant(root, nod, nod->left, op);

        else {
             

            NodeB* y = succesor(root, nod, op);

            nod->key = y->key;
            stergere(root, y, op);

        }

    }
}

void inOrder(NodeB* root, int n) {
    if (root)
    {
        inOrder(root->right, n + 1);

        if (root->right == NULL && root->left == NULL) {
            for (int i = 0; i < n; i++)
                printf(" \t");
            printf("%d(%d)\n ", root->key, root->size);
        }else if (root->right == NULL && root->left) {
            for (int i = 0; i < n; i++)
                printf(" \t");
            printf("%d(%d)\n ", root->key, root->size);
            for (int i = 0; i < n; i++)
                printf(" \t");
            printf("      %c\n", 0x5c);
        }
        else if (root->left == NULL && root->right) {
            for (int i = 0; i < n; i++)
                printf(" \t");
            printf("      /\n");
            for (int i = 0; i < n; i++)
                printf(" \t");
            printf("%d(%d)\n ", root->key, root->size);
            
        }
             else {
            for (int i = 0; i < n; i++)
                printf(" \t");
            printf("      /\n");
            for (int i = 0; i < n; i++)
                printf(" \t");
            printf("%d(%d)\n ", root->key, root->size);
            for (int i = 0; i < n; i++)
                printf(" \t");
            printf("      %c\n", 0x5c);
             }
            
       

        inOrder(root->left, n + 1);

    }

}

void perf(){
    for (int k = 0; k < NR_TESTS; ++k)
    {
        for (int n = STEP_SIZE; n <= MAX_SIZE; n += STEP_SIZE)
        {   
            int cn = n;
            Operation opselect = p.createOperation("OS_Select", n);
            Operation opdelete = p.createOperation("STERGERE", n);
            Operation opbuildTree = p.createOperation("BuildBTS", n);
            int arr[MAX_SIZE];
            for (int i = 0; i < n; ++i)
                FillRandomArray(arr, MAX_SIZE, 10, 50000, true, ASCENDING);
           
            NodeB* arb = BuildBTS(arr,0, n-1, opbuildTree);

            for (int i = 1; i < n; i++) {
            
                    int ci = (rand() % cn) + 1;
                  
                    NodeB* desters = NULL;
                    desters = OS_Select(arb, ci, opselect);
                    stergere(arb, desters, opdelete);
                    cn--;
                
            }

        }
    }
    p.divideValues("OS_Select", NR_TESTS);
    p.divideValues("STERGERE", NR_TESTS);
    p.divideValues("BuildBTS", NR_TESTS);
    p.addSeries("OS_Stergeri", "STERGERE", "OS_Select");
    p.createGroup("OPERATII","BuildBTS", "OS_Select", "OS_Stergeri");
    p.showReport();
}

void demo() {
    int n = 11;
    int cn = n;
    int A[] = { 1, 4, 7, 8, 9, 10, 14, 17, 20, 21, 22 };
    for (int i = 0; i < n; i++)
        printf("%d ", A[i]);
    printf("\n\n");
    Operation demo = p.createOperation("demo", 0);
    NodeB* radacina = BuildBTS(A, 0, n - 1,demo);
    inOrder(radacina, 0);
    printf("\n\n\n\n\n");
    NodeB* desters =NULL;

    for (int i = 1; i < 11; i++) {
        printf("\n\n\n\n\n\n\n");
        int ci = (rand() % cn)+1;
        desters = OS_Select(radacina, ci, demo);
        stergere(radacina, desters, demo);
        cn--;
        inOrder(radacina, 0);
       
    }
     

}

int main() {

    demo();
    //perf();
    return 0;


}