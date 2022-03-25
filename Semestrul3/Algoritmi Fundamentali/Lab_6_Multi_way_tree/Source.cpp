#include <stdio.h>
#include <stdlib.h>

#define dimPI 11
int  PI[] = {0,2,7,5,2,7,7,-1,5,2};


void Afisare1(int arry[], int viztat[], int n, int k, int treeLevel)
{

	if (viztat[k] == 0){

		viztat[k] = 1;

		for (int i = 0; i < treeLevel; i++)
			printf("   ");
		printf("%d \n \n ",k);

		for (int i = 1; i <= n; i++)
			if (arry[i] == k)
				Afisare1(arry, viztat, n, i, treeLevel + 1);
	}
}

///////////////////////////////ARBORE MULTICAI///////////////////////////////////////
struct Node
{
	int key;
	int nrCopii;
	Node* children[dimPI];
};




Node* newNode(int k) {

	Node* nod = (Node*)malloc(sizeof(Node));
	nod->key = k;
	nod->nrCopii = 0;
	for (int i = 0; i < dimPI; i++) {
		nod->children[i] = NULL;
	}
	return nod;
}

Node* trnsformareVectorParintiInMulticai()
{
	Node* adrese[dimPI];
	Node* head = NULL;

	for (int i = 0; i < dimPI; i++) {
		adrese[i] = newNode(i);
	}

	for (int i = 0; i < dimPI; i++) {
		if (PI[i] == -1) {
			head = adrese[i];
		}
		else {
			int poz = adrese[PI[i]]->nrCopii;
			adrese[PI[i]]->children[poz] = adrese[i];
			adrese[PI[i]]->nrCopii++;
		}
	}
	return head;
}

void Afisare2(Node* arbore, int treeLevel)
{
	if (arbore != NULL) {

		for (int i = 0; i < treeLevel; i++)
			printf("   ");
		printf("%d \n \n", arbore->key);

		for (int i = 0; i < arbore->nrCopii; i++)
			Afisare2(arbore->children[i], treeLevel + 1);
	}

}
/////////////////////////////ARBORE BINAR////////////////////////////////////////////
typedef struct NodB {
	int key;
	NodB * left, * right;
};

NodB* newNodB()
{
	NodB* node = (NodB*)malloc(sizeof(NodB));
	node->left = NULL;
	node->right = NULL;
	return node;
}

void construireArboreBinar(NodB* binary, Node* multicai) {

	if (multicai->nrCopii == 0) {
		binary->left = NULL;
	}
	else {
		
		NodB** adrese = (NodB**)calloc(multicai->nrCopii , sizeof(NodB*));
		adrese[0] = newNodB();
		for (int i = 0; i < multicai->nrCopii; i++) {	

			adrese[i]->key = multicai->children[i]->key;

			if (i + 1 == multicai->nrCopii) {
				adrese[i]->right = NULL;
			}
			else {
				adrese[i + 1] = newNodB();
				adrese[i]->right = adrese[i + 1];
			}

			construireArboreBinar(adrese[i], multicai->children[i]);

			
			
		}
		binary->left = adrese[0];
	}
}

NodB* trnsformareMulticaiInArboreBinar(Node* head) {

	NodB* headBin = newNodB();
	headBin->key = head->key;
	construireArboreBinar(headBin, head);
	return headBin;

}

void  Afisare3(NodB* arbore, int treeLevel)
{
	if (arbore != NULL) {		
				
		Afisare3(arbore->right, treeLevel + 1);
		Afisare3(arbore->left, treeLevel + 1);

		for (int i = 0; i < treeLevel; i++)
			printf("    ");
		printf("%d\n\n", arbore->key);

	
		
	}

	
}
	

//////////////////////////////////DEMO//////////////////////////////////////////////////////

void demo() {
	int vizite[dimPI] = { 0 };
	printf("Afisare pentru vectorul de tati : \n");
	Afisare1(PI, vizite, dimPI, 7, 0);
	printf("\n");

	Node* arboreMulticai = trnsformareVectorParintiInMulticai();
	printf("Afisare pentru arborele multicai : \n");
	Afisare2(arboreMulticai,0);
	printf("\n");

	printf("Afisare pentru arborele binar : \n");
	NodB* binary = trnsformareMulticaiInArboreBinar(arboreMulticai);
	Afisare3(binary,0);
}

int main() {
    
	demo();
	return 0;
}
