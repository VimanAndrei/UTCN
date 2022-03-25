#include <stdlib.h>
#include <string.h>
#include "bfs.h"
#include "coada.h";


int get_neighbors(const Grid *grid, Point p, Point neighb[])
{
    //Point are linia si coloana
    //grid are numarul de linii si numarul de coloane


    // TODO: fill the array neighb with the neighbors of the point p and return the number of neighbors
    // the point p will have at most 4 neighbors (up, down, left, right)
    // avoid the neighbors that are outside the grid limits or fall into a wall
    // note: the size of the array neighb is guaranteed to be at least 4
    int nofneigh = 0;
    int nrlinii = grid->rows;
    typedef struct dir {
        int xaxis;
        int yaxis;
    };
    dir* directie = (dir*)malloc(4 * sizeof(dir));
    directie[0].xaxis = -1;
    directie[0].yaxis = 0;
    directie[1].xaxis = 1;
    directie[1].yaxis = 0;
    directie[2].xaxis = 0;
    directie[2].yaxis = -1;
    directie[3].xaxis = 0;
    directie[3].yaxis = 1;


    dir* pozcurenta = (dir*)malloc(sizeof(dir));
    for (int i = 0; i <= 3; i++) {
        
        pozcurenta->xaxis = p.row + directie[i].xaxis;
        pozcurenta->yaxis = p.col + directie[i].yaxis;

        
            if (pozcurenta->xaxis > 0 && pozcurenta->yaxis > 0) {
                if (pozcurenta->yaxis < grid->cols && pozcurenta->xaxis < grid->rows) {
                    if (grid->mat[pozcurenta->xaxis][pozcurenta->yaxis] == 0) {
                        neighb[nofneigh].col = pozcurenta->yaxis;
                        neighb[nofneigh].row = pozcurenta->xaxis;
                        nofneigh++;
                    }
                }

            
        }
    }

    return nofneigh;
}

void grid_to_graph(const Grid *grid, Graph *graph)
{
    //we need to keep the nodes in a matrix, so we can easily refer to a position in the grid
    Node *nodes[MAX_ROWS][MAX_COLS];
    int i, j, k;
    Point neighb[4];

    //compute how many nodes we have and allocate each node
    graph->nrNodes = 0;
    for(i=0; i<grid->rows; ++i){
        for(j=0; j<grid->cols; ++j){
            if(grid->mat[i][j] == 0){
                nodes[i][j] = (Node*)malloc(sizeof(Node));
                memset(nodes[i][j], 0, sizeof(Node)); //initialize all fields with 0/NULL
                nodes[i][j]->position.row = i;
                nodes[i][j]->position.col = j;
                ++graph->nrNodes;
            }else{
                nodes[i][j] = NULL;
            }
        }
    }
    graph->v = (Node**)malloc(graph->nrNodes * sizeof(Node*));
    k = 0;
    for(i=0; i<grid->rows; ++i){
        for(j=0; j<grid->cols; ++j){
            if(nodes[i][j] != NULL){
                graph->v[k++] = nodes[i][j];
            }
        }
    }

    //compute the adjacency list for each node
    for(i=0; i<graph->nrNodes; ++i){
        graph->v[i]->adjSize = get_neighbors(grid, graph->v[i]->position, neighb);
        if(graph->v[i]->adjSize != 0){
            graph->v[i]->adj = (Node**)malloc(graph->v[i]->adjSize * sizeof(Node*));
            k = 0;
            for(j=0; j<graph->v[i]->adjSize; ++j){
                if( neighb[j].row >= 0 && neighb[j].row < grid->rows &&
                    neighb[j].col >= 0 && neighb[j].col < grid->cols &&
                    grid->mat[neighb[j].row][neighb[j].col] == 0){
                        graph->v[i]->adj[k++] = nodes[neighb[j].row][neighb[j].col];
                }
            }
            if(k < graph->v[i]->adjSize){
                //get_neighbors returned some invalid neighbors
                graph->v[i]->adjSize = k;
                graph->v[i]->adj = (Node**)realloc(graph->v[i]->adj, k * sizeof(Node*));
            }
        }
    }
}

void free_graph(Graph *graph)
{
    if(graph->v != NULL){
        for(int i=0; i<graph->nrNodes; ++i){
            if(graph->v[i] != NULL){
                if(graph->v[i]->adj != NULL){
                    free(graph->v[i]->adj);
                    graph->v[i]->adj = NULL;
                }
                graph->v[i]->adjSize = 0;
                free(graph->v[i]);
                graph->v[i] = NULL;
            }
        }
        free(graph->v);
        graph->v = NULL;
    }
    graph->nrNodes = 0;
}

void bfs(Graph *graph, Node *s, Operation *op)
{
    // TOOD: implement the BFS algorithm on the graph, starting from the node s
    // at the end of the algorithm, every node reachable from s should have the color BLACK
    // for all the visited nodes, the minimum distance from s (dist) and the parent in the BFS tree should be set
    // for counting the number of operations, the optional op parameter is received
    // since op can be NULL (when we are calling the bfs for display purposes), you should check it before counting:
    // if(op != NULL) op->count();
    queue* q =(queue*)malloc(sizeof(queue));
    q->capacity = 0;
    q->head = NULL;
    q->tail = NULL;
    if (op != NULL) op->count();
    enqueue(q, s);
    while (!isEmpty(q)) {
        if (op != NULL) op->count();
        Node* nod = dequeue(q)->n;
        if (op != NULL) op->count();
        
        for (int i = 0; i < nod->adjSize; i++) {
            if (op != NULL) op->count();
            Node* urm = nod->adj[i];
            if (op != NULL) op->count();
            if(urm->color == COLOR_WHITE){
                if (op != NULL) op->count();
                urm->parent = nod;
                urm->dist = nod->dist + 1;
                urm->color = COLOR_GRAY;
                if (op != NULL) op->count();
                enqueue(q, urm);
            }

        }
        nod->color = COLOR_BLACK;
    }
}
void Afisare1(Point repr[],int arry[], int viztat[], int n, int k, int treeLevel)
{

    if (viztat[k] == 0) {

        viztat[k] = 1;

        for (int i = 0; i < treeLevel; i++)
            printf("        ");
        printf("( %d, %d )\n\n", repr[k].row,repr[k].col);

        for (int i = 0; i <= n; i++)
            if (arry[i] == k )
                Afisare1(repr,arry, viztat, n, i, treeLevel + 1);
    }
}

void print_bfs_tree(Graph *graph)
{
    //first, we will represent the BFS tree as a parent array
    int n = 0; //the number of nodes
    int *p = NULL; //the parent array
    Point *repr = NULL; //the representation for each element in p

    //some of the nodes in graph->v may not have been reached by BFS
    //p and repr will contain only the reachable nodes
    int *transf = (int*)malloc(graph->nrNodes * sizeof(int));
    for(int i=0; i<graph->nrNodes; ++i){
        if(graph->v[i]->color == COLOR_BLACK){
            transf[i] = n;
            ++n;
        }else{
            transf[i] = -1;
        }
    }
    if(n == 0){
        //no BFS tree
        free(transf);
        return;
    }

    int err = 0;
    p = (int*)malloc(n * sizeof(int));
    repr = (Point*)malloc(n * sizeof(Node));
    for(int i=0; i<graph->nrNodes && !err; ++i){
        if(graph->v[i]->color == COLOR_BLACK){
            if(transf[i] < 0 || transf[i] >= n){
                err = 1;
            }else{
                repr[transf[i]] = graph->v[i]->position;
                if(graph->v[i]->parent == NULL){
                    p[transf[i]] = -1;
                }else{
                    err = 1;
                    for(int j=0; j<graph->nrNodes; ++j){
                        if(graph->v[i]->parent == graph->v[j]){
                            if(transf[j] >= 0 && transf[j] < n){
                                p[transf[i]] = transf[j];
                                err = 0;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
    free(transf);
    transf = NULL;

    if(!err){

       
        // TODO: pretty print the BFS tree
        // the parrent array is p (p[k] is the parent for node k or -1 if k is the root)
        // when printing the node k, print repr[k] (it contains the row and column for that point)
        // you can adapt the code for transforming and printing multi-way trees from the previous labs
       
        ///*
        printf("Sunt %d noduri vizitate.\n", n);

        int *vizite=(int*)malloc(n+1*sizeof(int));
        for (int i = 0; i < n; i++) {
            vizite[i] = 0;
        }
        int start=-1;

        for (int i = 0; i <n; i++) {
            if (p[i] == -1)start = i;
        }
       
       Afisare1(repr ,p, vizite, n, start, 0);
        // */
        /*
        printf("%d\n", sizeof(repr));
        for (int i = 0; i <= sizeof(p); i++) {
            printf("(%d,%d)  ",repr[i].row, repr[i].col);
        }
        printf("\n");
        for (int i = 0; i <= sizeof(p); i++) {
            printf("%d      ", p[i]);

        }
        printf("\n");
        //*/

    }

    if(p != NULL){
        free(p);
        p = NULL;
    }
    if(repr != NULL){
        free(repr);
        repr = NULL;
    }
}

int shortest_path(Graph *graph, Node *start, Node *end, Node *path[])
{
    // TODO: compute the shortest path between the nodes start and end in the given graph
    // the nodes from the path, should be filled, in order, in the array path
    // the number of nodes filled in the path array should be returned
    // if end is not reachable from start, return -1
    // note: the size of the array path is guaranteed to be at least 1000
 
    bfs(graph, start);
  
    if (end->parent==NULL) {
        return -1;
    }
   
    queue* q = (queue*)malloc(sizeof(queue));
    q->capacity = 0;
    q->head = NULL;
    q->tail = NULL;

    Node* nod=end->parent;

    while (nod->parent) {
        enqueue(q, nod);
        nod = nod->parent;
    }
   
    int size = q->capacity;

    while (q->capacity) {
        path[q->capacity] = dequeue(q)->n;
    }
    

    return size ;

}
void edgeConnect(Node* x, Node* y)
{
    if (x->adjSize == 0){
        x->adj = (Node**)malloc(sizeof(Node*));    
    }
    else{
        x->adj = (Node**)realloc(x->adj, (x->adjSize+1) * sizeof(Node*));   
    }

    x->adj[x->adjSize] = y;
    x->adjSize++;
    
}

void performance()
{
    int n, i;
    Profiler p("bfs");
    bool adiacenta[201][201];

    // vary the number of edges
    for(n=1000; n<=4500; n+=100){
        Operation op = p.createOperation("bfs-edges", n);
        Graph graph;
        graph.nrNodes = 100;
        for (int i = 0; i < graph.nrNodes; ++i)
            for (int j = 0; j < graph.nrNodes; ++j)
                adiacenta[i][j] = false;
        //initialize the nodes of the graph
        graph.v = (Node**)malloc(graph.nrNodes * sizeof(Node*));
        for(i=0; i<graph.nrNodes; ++i){
            graph.v[i] = (Node*)malloc(sizeof(Node));
            memset(graph.v[i], 0, sizeof(Node));
        }
       
        
        // TODO: generate n random edges
        // make sure the generated graph is connected

        for (int i = 1; i < graph.nrNodes; i++){
            
            adiacenta[0][i] = adiacenta[0][i]= true;
            edgeConnect(graph.v[0], graph.v[i]);
            edgeConnect(graph.v[i], graph.v[0]);
       
        }

        for (int i = graph.nrNodes; i < n; i++) {            
            
            int a = rand() % graph.nrNodes,b = rand() % graph.nrNodes;
            while (a == b || adiacenta[a][b] == true || adiacenta[b][a] == true) {
                a = rand() % graph.nrNodes;
                b = rand() % graph.nrNodes;

            }
            adiacenta[a][b] = adiacenta[b][a] = true;
            edgeConnect(graph.v[a], graph.v[b]);
            edgeConnect(graph.v[b], graph.v[a]);

        }


        bfs(&graph, graph.v[0], &op);
        free_graph(&graph);
    }

    // vary the number of vertices
    for(n=100; n<=200; n+=10){
        Operation op = p.createOperation("bfs-vertices", n);
        Graph graph;
        graph.nrNodes = n;
        for (int i = 0; i < graph.nrNodes; ++i)
            for (int j = 0; j < graph.nrNodes; ++j)
                adiacenta[i][j] = false;
        //initialize the nodes of the graph
        graph.v = (Node**)malloc(graph.nrNodes * sizeof(Node*));
        for(i=0; i<graph.nrNodes; ++i){
            graph.v[i] = (Node*)malloc(sizeof(Node));
            memset(graph.v[i], 0, sizeof(Node));
        }
        // TODO: generate 4500 random edges
        // make sure the generated graph is connected
        for (int i = 1; i < graph.nrNodes; i++) {

            adiacenta[0][i] = adiacenta[0][i] = true;
            edgeConnect(graph.v[0], graph.v[i]);
            edgeConnect(graph.v[i], graph.v[0]);

        }

        for (int i = graph.nrNodes; i < 4500; i++) {

            int a = rand() % graph.nrNodes, b = rand() % graph.nrNodes;
            while (a == b || adiacenta[a][b] == true || adiacenta[b][a] == true) {
                a = rand() % graph.nrNodes;
                b = rand() % graph.nrNodes;

            }
            adiacenta[a][b] = adiacenta[b][a] = true;
            edgeConnect(graph.v[a], graph.v[b]);
            edgeConnect(graph.v[b], graph.v[a]);

        }

        bfs(&graph, graph.v[0], &op);
        free_graph(&graph);
    }

    p.showReport();
}
