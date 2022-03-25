#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>

char FILE_WRITE[] = "RESP_PIPE_79531";
char FILE_READ[] =  "REQ_PIPE_79531";
char con[]="CONNECT\0";
int sizecon = 7;
int nr_meu = 79531;
char mesaj[200]="";
int dim_cuv = 0;
int shared_mem;
char *sharedMem = NULL;
//2.6
int fd1=-1;
char *data = NULL;
off_t dim_fisier;

int ok(char* data,int noS,int of,int noOfB){
	
	int  header_size=0, version=0;
	char magic = 0,no_of_sections=0;
	
	memcpy(&magic,data+0,1);
	
	if(magic!='6') {
           return-1;
    	}
    	
    	
    	memcpy(&header_size,data+1,2);   	
    	memcpy(&version,data+3,2); 
    	
    	
    	if(version < 123 || version > 169){
    		return-1;
    	}
    	
    	memcpy(&no_of_sections,data+5,1); 
    	
	if (no_of_sections < 10 || no_of_sections > 22) {
	    return-1;
	}
	
	if(no_of_sections < noS){
		return -1;
	}
	
	int offsetReturn=-1;
	int offsetSize=-1;
	char name[7];
	int type=0, offset=0, size=0;
	for(int i=0; i<no_of_sections; i++){
		memcpy(name,data+6+19*i,7); 
		memcpy(&type,data+13+19*i,4);
		memcpy(&offset,data+17+19*i,4);
		memcpy(&size,data+21+19*i,4);
		if(type != 99 && type != 59 && type != 96){
			return-1;
		}
		if(i+1 == noS){
			offsetReturn=offset;
			offsetSize=size;
		}
		
	}
	
	if(offsetReturn==-1){
		return -1;
	}else{  
		if(of+ noOfB>offsetSize){
			return -1;
		}
	}
	
	return offsetReturn;
		
}
int getDimHead(char *data){
	int  header_size=0, version=0;
	char magic = 0,no_of_sections=0;
	int total = -1;
	
	memcpy(&magic,data+0,1);
	
	if(magic!='6') {
           return-1;
    	}
    	
    	
    	memcpy(&header_size,data+1,2);   	
    	memcpy(&version,data+3,2); 
    	
    	
    	if(version < 123 || version > 169){
    		return-1;
    	}
    	
    	memcpy(&no_of_sections,data+5,1); 
    	
	if (no_of_sections < 10 || no_of_sections > 22) {
	    return-1;
	}
	
	total = 1+2+2+1;
	total = total + (19 *no_of_sections);
	
	return total;
}

int main(int argc, char **argv){ 

    int fdr, fdw; 
    if(mkfifo(FILE_WRITE, 0600) != 0) {
        printf("ERROR\ncannot create the response pipe | cannot open the request pipe\n");
    } 
    
    fdr = open(FILE_READ, O_RDONLY);
    if(fdr == -1) {
        printf("ERROR\ncannot create the response pipe | cannot open the request pipe\n");
    }
    
    fdw = open(FILE_WRITE, O_WRONLY);   
    if(fdr == -1) {
        printf("ERROR\ncannot create the response pipe | cannot open the request pipe\n");
    }


    if(write(fdw, &sizecon, 1) == 1) {
        if(write(fdw, &con, sizecon)==sizecon){
            printf("SUCCESS\n");
        }
    }
    
    for(;;){
        read(fdr,&dim_cuv,1);
        read(fdr, mesaj, dim_cuv);
        
        if(strstr(mesaj, "PING")) {
            write(fdw, &dim_cuv, 1);
            write(fdw, "PING", 4);
            write(fdw, &dim_cuv, 1);
            write(fdw, "PONG", 4);
            write(fdw, &nr_meu, 4);
        }
        
        if(strstr(mesaj, "CREATE_SHM")){
        	int dim_size_mem=0;
        	int size = 0;
        	read(fdr,&size,1); 
        	read(fdr,&dim_size_mem,4); 
        	shared_mem = shm_open("/dURY42tR", O_CREAT | O_RDWR , 0664);
        	if(shared_mem < 0) {
        		int nrE=5,nrC=10;
        		write(fdw, &nrC, 1);
               	write(fdw, "CREATE_SHM", nrC);
                	write(fdw, &nrE, 1);
                	write(fdw, "ERROR", nrE); 
                	break;                       
               }
               
               ftruncate(shared_mem, 2878785);
    	       sharedMem = (char *)mmap(0, dim_size_mem, PROT_EXEC | PROT_READ | PROT_WRITE, MAP_SHARED, shared_mem, 0); 
    	       if(sharedMem == (void*)-1){
        		int nrE=5,nrC=10;
        		write(fdw, &nrC, 1);
               	write(fdw, "CREATE_SHM", nrC);
                	write(fdw, &nrE, 1);
                	write(fdw, "ERROR", nrE);
                	break;
   	       }
   	      
   	       int nrS=7,nrC=10;
   	       write(fdw, &nrC, 1);
               write(fdw, "CREATE_SHM", nrC);
               write(fdw, &nrS, 1);
               write(fdw, "SUCCESS", nrS);
                
        }
        
        if(strstr(mesaj, "WRITE_TO_SHM")) {
        	unsigned int value = 0, offset = 0;
        	read(fdr,&offset,4);
        	read(fdr,&value,4);
        	if(offset<=0 || (offset+sizeof(unsigned int))>=2878785){
        		int nrE=5,nrW=12;
        		write(fdw, &nrW, 1);
               	write(fdw, "WRITE_TO_SHM", nrW);
                	write(fdw, &nrE, 1);
                	write(fdw, "ERROR", nrE);
                	break;
        	}
        	
               shared_mem = shm_open("/dURY42tR",   O_CREAT | O_RDWR , 0664);       	
               ftruncate(shared_mem, 2878785);
    	       sharedMem = (char *)mmap(0, 2878785,  PROT_EXEC | PROT_READ | PROT_WRITE, MAP_SHARED, shared_mem, 0); 
    	       
               
               memcpy(sharedMem+offset,&value,4);          
		
	       int nrS=7,nrW=12;
   	       write(fdw, &nrW, 1);
               write(fdw, "WRITE_TO_SHM", nrW);
               write(fdw, &nrS, 1);
               write(fdw, "SUCCESS", nrS);          
	
        }
        
        if(strstr(mesaj, "MAP_FILE")){
        	int size = 0;
        	read(fdr,&size,1);
        	char *cale = (char*)malloc((size+1)*sizeof(char));
        	read(fdr,cale,size);
        	
        	fd1 = open(cale,O_RDONLY);
		if(fd1 == -1){
			free(cale);
			int nrE=5,nrM=8;
        		write(fdw, &nrM, 1);
               	write(fdw, "MAP_FILE", nrM);
                	write(fdw, &nrE, 1);
                	write(fdw, "ERROR", nrE);
                	break;
		}
		
		dim_fisier=lseek(fd1, 0, SEEK_END);
		lseek(fd1, 0, SEEK_SET);		
		data = (char*)mmap(NULL, dim_fisier, PROT_READ, MAP_SHARED, fd1, 0);
				
		if(data == (void*)-1){
        		close(fd1);
        		free(cale);
        		int nrE=5,nrM=8;
        		write(fdw, &nrM, 1);
               	write(fdw, "MAP_FILE", nrM);
                	write(fdw, &nrE, 1);
                	write(fdw, "ERROR", nrE);
                	break;
		}
		free(cale);
		int nrS=7,nrM=8;
        	write(fdw, &nrM, 1);
                write(fdw, "MAP_FILE", nrM);
                write(fdw, &nrS, 1);
                write(fdw, "SUCCESS", nrS);
        }
        
                
        if(strstr(mesaj,"READ_FROM_FILE_OFFSET")){
        	unsigned int offset = 0, noOfB = 0;
        	read(fdr,&offset,4);
        	read(fdr,&noOfB,4);        	
        	
        	if( (offset+noOfB) > dim_fisier || data == (void*)-1 || sharedMem == (void*)-1){
        		close(fd1);
        		int nrE=5,nrM=21;
        		write(fdw, &nrM, 1);
               	write(fdw, "READ_FROM_FILE_OFFSET", nrM);
                	write(fdw, &nrE, 1);
                	write(fdw, "ERROR", nrE);
                	
        	}else{
        	
        		memcpy(sharedMem+0,data+offset,noOfB);
        		
        		int nrE=7,nrM=21;
        		write(fdw, &nrM, 1);
                	write(fdw, "READ_FROM_FILE_OFFSET", nrM);
                	write(fdw, &nrE, 1);
                	write(fdw, "SUCCESS", nrE);
                }

        }
        
        if(strstr(mesaj,"READ_FROM_FILE_SECTION")){
        	unsigned int noSection = 0,offset = 0, noOfB = 0;
        	read(fdr,&noSection,4);
        	read(fdr,&offset,4);
        	read(fdr,&noOfB,4);
        	int ofR=ok(data,noSection,offset,noOfB);
        	if(ofR==-1 || ofR+noOfB > dim_fisier){
			int nrR=22,nrS=5;
			write(fdw, &nrR, 1);
			write(fdw, "READ_FROM_FILE_SECTION", nrR);
			write(fdw, &nrS, 1);
			write(fdw, "ERROR", nrS);
				
        	}else{
        		
        		memcpy(sharedMem,data+ofR+offset,noOfB);
        		int nrR=22,nrS=7;
        		write(fdw, &nrR, 1);
                	write(fdw, "READ_FROM_FILE_SECTION", nrR);
                	write(fdw, &nrS, 1);
                	write(fdw, "SUCCESS", nrS);
                }

        }
        
        if(strstr(mesaj,"READ_FROM_LOGICAL_SPACE_OFFSET")){	
        	unsigned int lOF = 0, noOfB = 0;
        	read(fdr,&lOF,4);
        	read(fdr,&noOfB,4);
        	int sizeH = getDimHead(data);
        	 
        	if(lOF+noOfB > dim_fisier-sizeH  || data == (void*)-1 || sharedMem == (void*)-1){
        		close(fd1);
        		int nrE=5,nrM=30;
        		write(fdw, &nrM, 1);
               	write(fdw, "READ_FROM_LOGICAL_SPACE_OFFSET", nrM);
                	write(fdw, &nrE, 1);
                	write(fdw, "ERROR", nrE);
                	
        	}else{
        	
			int nrR=30,nrS=7;
			write(fdw, &nrR, 1);
			write(fdw, "READ_FROM_LOGICAL_SPACE_OFFSET", nrR);
			write(fdw, &nrS, 1);
			write(fdw, "SUCCESS", nrS);
                }

        }
        
        if(strstr(mesaj, "EXIT")) {		
            munmap(&shared_mem, 2878785);
    	    munmap(data, dim_fisier);
            shm_unlink("/dURY42tR");
            close(fd1);		           
            close(fdr);
            close(fdw);
            unlink(FILE_WRITE);
            break;
        }
    }
    
    return 0;
}

