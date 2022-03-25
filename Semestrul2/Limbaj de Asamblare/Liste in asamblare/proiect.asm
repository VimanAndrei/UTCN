.386
.model flat, stdcall
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;includem biblioteci, si declaram ce functii vrem sa importam
includelib msvcrt.lib
extern exit: proc
extern scanf: proc
extern printf: proc
extern strstr: proc
extern calloc: proc
extern free:proc
extern fopen:proc
extern fprintf:proc
extern fscanf:proc
extern fclose:proc
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;declaram simbolul start ca public - de acolo incepe executia
public start

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
add_de_noduri macro head, nr_elem , n, m_eax,m_ebx

local  verificare,verificare1,aici
    mov eax,m_eax
	mov ebx,m_ebx
	push n
	call create_node ; alocam memorie pentru un nod
	add esp, 4
	
	mov ecx,nr_elem
	cmp ecx,0; daca nu e nici un element
	jne verificare; ma uit daca mai sunt elemente in lista
	mov ebx, eax ; ebx se foloseste pentru a indica ultimul element din lista (folosit pentru a insera nodul urmator in lista)
	mov head, eax ; head - pointer catre inceputul listei
	jmp aici
	verificare:
	mov ecx,nr_elem
	cmp ecx,1;daca e un element
	jne verificare1;ma uit daca e al doilea elem
	mov dword ptr[ebx+4], eax ; se face legatura intre primul nod si nodul al doilea, adica node.next = adresa nodului nou
	jmp aici
	
	verificare1:;daca sunt mai multe elemente
	mov ebx,[ebx+4] ; mergem la urm nod (care in acest moment e ultimul nod din lista)
	mov dword ptr[ebx+4], eax ; se face legatura intre primul nod si nodul al doilea, adica node.next = adresa nodului nou
	
	aici:	
	mov m_ebx,ebx
	mov m_eax,eax
	
	
	mov ecx,nr_elem
	inc ecx
	mov nr_elem,ecx
	
endm

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
stergere_elemente_din_lista macro head,index,nr_elem 
  
  local sterg_primul,caut_element,sterg_ultimul,stergere_propriu_zisa,finall_stergere
	
	mov ecx,index
	cmp ecx,1
	je sterg_primul

	
	mov ecx,2
	mov esi,head
	caut_element:
	cmp ecx,index
	je stergere_propriu_zisa
	mov esi,[esi+4]
	inc ecx
	jmp caut_element
	
	stergere_propriu_zisa:
	cmp ecx,nr_elem
	je sterg_ultimul
	mov edx,dword ptr [esi+4]
	mov ecx,dword ptr [edx+4]
	mov dword ptr [esi+4],ecx
	mov edx,index
	cmp edx,nr_elem
	
	 push edx
	 call free
	 add esp,4
	 mov ecx,index
	 
	 jmp finall_stergere
	 
	 sterg_ultimul:
	
	 mov dword ptr [esi+4],-1
	
	 
	 jmp finall_stergere
	sterg_primul:
	
	mov esi,head
	mov esi,[esi+4]
	push head
	call free
	add esp,4
	mov head,esi

	finall_stergere:
	mov eax,nr_elem
	dec eax
	mov nr_elem,eax
endm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;sectiunile programului, date, respectiv cod
.data
;aici declaram date
exi db "exit",0
adaugare db "in",0
stergere db "stergere",0
afis db "afisare",0
save db "save.txt",0
load db "load.txt",0
array db "save_array",0
tab db "afiseaza_array",0

formats db "%s",0
formatd db "%d",0
formatda db "%d ",0

mesaj1 db 10,"Introduceti o operatie:",0
mesaj2 db 10,"Introduceti un numar intreg:",0
mesaj3 db 10,"Introduceti un numar index: ",0
eroare db 10,"Nu mai exista elemente in lista",0
operatie db 50 dup(0);ce operatie dorim sa facem
n dd ? ;elementul care dorim sa il inseram 
       ;sau al catelea element dori sa il stergem
nr_elem dd 0;numarul de elemente din lista
nr_elem_t dd 0
index dd 0
node struct
	key dd 0
	next dd 0
node ends
head dd 0 ; adresa primului nod din lista
sir dd 100 dup (0)
fisier dd ?
fisier2 dd ?
mode_r db "r", 0
mode_w db "w", 0
filename1 db "fisier.txt", 0
filename2 db "fisier2.txt", 0
m_eax dd 0
m_ebx dd 0

.code
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
citeste proc ;msg,format,var
push ebp
mov ebp,esp
push [ebp+8]
call printf
add esp,4
push [ebp+16]
push [ebp+12]
call scanf
add esp,8
pop ebp
ret
citeste endp 
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
create_node proc ; node* create_node(int key)
	; functia aloca spatiu de memorie pentru un nod,
	; atribuie pe campurile key o valoare intreaga (cheia nodului), iar pe next valoarea NULL (aici NULL = -1)

	push ebp
	mov ebp, esp
	
	sub esp, 4
	mov [ebp-4], ebx ; salvam valoarea veche a lui ebx
	
	push type node ; alocam memorie pentru un node
	push 1
	call calloc 
	add esp, 8
	
	mov ebx,[ebp+8] ; citim cheia transmisa ca si parametru pe stiva
	mov dword ptr [eax], ebx	; aici salvam cheia in node.key
	mov dword ptr [eax+4], -1	; in node.next se pune NULL
	
	mov ebx, [ebp-4] ; refacem valoarea veche a lui ebx
	
	mov esp, ebp
	pop ebp
	ret
create_node endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
stergere_lista proc


push ebp
mov ebp,esp
push eax
push ebx
ste:
    mov eax, [ebp+16]
	cmp eax, -1 ;
    je sf
	mov ebx,[eax+4]
	push eax
	call free
	add esp,4
	mov [ebp+16],ebx
	jmp ste
	sf:


pop ebx
pop eax
pop ebp
ret
stergere_lista endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
afisare_array proc


	push ebp
	mov ebp,esp
	push eax
	push ecx
	mov eax,[ebp+16]
	mov ecx,[ebp+12]
	afisez_tablou:
	
	push eax
	push ecx
	push [eax]
	push [ebp+8]
	call printf
	add esp,8
	pop ecx;apeland o fct stdcall eax,ebx,ecx,edx,se modifica
	pop eax
	add eax,4
	
	loop afisez_tablou
pop ecx
pop eax
pop ebp
ret
afisare_array endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
create_array proc
	push ebp
	mov ebp,esp
	push eax
	push ecx
	push esi
	push edx

	mov eax,[ebp+12]
	mov esi,[ebp+16]
	mov ecx,[ebp+8]
	transform:	
	mov edx,dword ptr [esi]
	mov dword ptr [eax],edx;in eax, am adresa de la tablou iar in edx mut numarul in sine 
	add eax,4
	mov esi,[esi+4]
	loop transform
	
	pop edx
	pop esi
	pop ecx
	pop eax
	
	pop ebp
	ret

create_array endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

afisare_de_lista proc
	push ebp
	mov ebp,esp
	push esi
	
	
	mov esi, [ebp+12]; pentru afisare incepem de la primul nod (adresa a fost salvata in head)
	afisa:
		push dword ptr [esi] ; cheia nodului se pune pe stiva pentru afisare
		push [ebp+8]
		call printf
		add esp, 8
		
		mov esi, [esi+4] ; node = node.next, adica mergem la nodul urmator
		cmp esi, -1 ; daca nodul urmator e NULL, am ajuns la sfarsitul listei, deci ne oprim
		
		
	jne afisa
	
	pop esi 
	
	pop ebp
	ret

afisare_de_lista endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
salvare_din_lista_in_fisier proc ;head,fisier,formatda

	push ebp
	mov ebp,esp
	push esi

		mov esi, [ebp+8] ; pentru afisare incepem de la primul nod (adresa a fost salvata in head)
	afisare_fisier:
		push dword ptr [esi] ; cheia nodului se pune pe stiva pentru afisare
		push [ebp+16]
		push [ebp+12]
		call fprintf
		add esp, 12
		
		mov esi, [esi+4] ; node = node.next, adica mergem la nodul urmator
		cmp esi, -1 ; daca nodul urmator e NULL, am ajuns la sfarsitul listei, deci ne oprim
		
		
	jne afisare_fisier
	
	pop esi
	pop ebp
	ret

salvare_din_lista_in_fisier endp
;;;;;;;;;;;;;;;;;;;;;;





start:


Bucla_principala:
	push offset operatie
	push offset formats
	push offset mesaj1
	call citeste
	add esp,12
	
	push offset exi
	push offset operatie
	call strstr
	add esp,8

    cmp eax,offset operatie
	je sfarsit;--sfarsit de program
	
	push offset adaugare
	push offset operatie
	call strstr
	add esp,8
	
	cmp eax,offset operatie
	je adaug;-adaug nod
	
	push offset stergere
	push offset operatie
	call strstr
	add esp,8
	
	cmp eax,offset operatie
	je sterg;-sterg nod
	
	push offset afis
	push offset operatie
	call strstr
	add esp,8
	
	cmp eax,offset operatie
	je afisez;-afisez lista
	
	
	push offset array
	push offset operatie
	call strstr
	add esp,8
	
	cmp eax,offset operatie
	je salvez_in_tablou;-salvez lista in tablou
	
	push offset save
	push offset operatie
	call strstr
	add esp,8
	
	cmp eax,offset operatie
	je salvez_in_fisier;-salvez in fisier
	
	push offset load
	push offset operatie
	call strstr
	add esp,8
	
	cmp eax,offset operatie
	je incarc_din_fisier;-incarc  din fisier
	
	push offset tab
	push offset operatie
	call strstr
	add esp,8

    cmp eax,offset operatie
	je afisare_tablou;--sfarsit de program
	
	jmp salt ;fac salt daca nu s-a facut deja
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
adaug:
	
	push offset n
	push offset formatd
	push offset mesaj2
	call citeste
	add esp,12
	
	add_de_noduri head,nr_elem,n,m_eax,m_ebx

	
	jmp salt
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
sterg:
	
    push offset index
	push offset formatd
	push offset mesaj3
	call citeste
	add esp,12
	
	stergere_elemente_din_lista  head,index,nr_elem
	
	jmp salt
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
afisez:
	push head
	push offset formatda
	call afisare_de_lista
	add esp,8
	
	jmp salt
	
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
salvez_in_tablou:
    
	mov edx,nr_elem
	mov nr_elem_t,edx
	
	push head 
	push offset sir
	push nr_elem_t
	call create_array
	add esp,12

	
	jmp salt
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
afisare_tablou:
	
	
	push offset sir
	push nr_elem_t
	push offset formatda
	call afisare_array
	add esp ,12
	
	jmp salt
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
salvez_in_fisier:


	push offset mode_w
	push offset filename1
	call fopen
	add esp, 8
	mov fisier, eax ;salvam pointer-ul la fisier
	
		
	
	push offset formatda
	push fisier
	push head
	call salvare_din_lista_in_fisier
	add esp,12
	
	push head
	call stergere_lista
	add esp,4
	
	mov ecx,0
	mov nr_elem,ecx
	
	push fisier
	call fclose
	add esp,4

	jmp salt
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
incarc_din_fisier:

    push offset mode_r
	push offset filename2
	call fopen
	add esp, 8
	mov fisier2, eax ;salvam pointer-ul la fisier
	
	citiredinfisier:
	
	push offset n    ;read from file  
	push offset formatd ;  
	push fisier2                    ;  
	call fscanf   
	add esp, 12              ;  
	cmp eax,-1
	je stop
	
	push eax
	add_de_noduri head, nr_elem, n, m_eax, m_ebx
	pop eax
	
	jmp citiredinfisier
   
    stop:

	jmp salt
	
 salt:
jmp Bucla_principala
	;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
	
 sfarsit:
	push head
	call stergere_lista
	add esp,4	
	
	
	
    ;terminarea programului
	push 0
	call exit
end start
