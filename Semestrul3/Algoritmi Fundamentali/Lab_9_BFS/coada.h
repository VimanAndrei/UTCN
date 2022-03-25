#pragma once
# include"bfs.h"

 typedef struct NodeT {
    Node* n;
    NodeT* next;
};

typedef struct queue {
    int capacity;
    NodeT* head;
    NodeT* tail;
};
NodeT* makenod(Node* k) {
    NodeT* nod = (NodeT*)malloc(sizeof(NodeT));
    nod->n = k;
    nod->next = NULL;
    return nod;
}
void enqueue(queue* my_queue, Node* nod)
{
    NodeT* q = makenod(nod);
    if (my_queue->tail == NULL) {
        my_queue->head = my_queue->tail = q;
        my_queue->capacity++;
        return;
    }
    my_queue->tail->next = q;
    my_queue->tail = q;
    my_queue->capacity++;
}
NodeT* dequeue(struct queue* my_queue) {
    NodeT* q = NULL;
    if (my_queue->capacity > 0 && my_queue->head != NULL) {
        my_queue->capacity--;
        q = my_queue->head;
        my_queue->head = my_queue->head->next;

    }
    if (my_queue->head == NULL)
        my_queue->tail = NULL;
    return q;
}
int isEmpty(struct queue* my_queue)
{
    return (my_queue->capacity == 0);
}

