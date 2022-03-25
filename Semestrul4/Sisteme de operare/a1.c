#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <dirent.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>

//////////////////////////////////////////////////////////////////////
int afisareNerecursiva(char*path)
{
    DIR* dir=NULL;
    dir=opendir(path);
    struct dirent *entry;
    
    if (dir!=NULL){
        printf("SUCCESS\n");
        
        while((entry=readdir(dir))!=NULL){
            if(strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) 
                printf("%s/%s\n", path, entry->d_name);
        }
        
    	free(path);    
    	closedir(dir);
    	return 0;
    }
    else{
        printf("ERROR\n invalid directory path");
        return -1;
    }
}
/////////////////////////////////////////////////////////////////////////
void afisareRecursiva(char*path, int maiMultDeOData)
{
    DIR *dir = NULL;
    struct dirent *entry = NULL;
    char fullPath[512];
    struct stat statbuf;

    dir = opendir(path);
    if(dir == NULL) {
        perror("ERROR\n invalid directory path");
        return;
    }
    else{
    
    	if(maiMultDeOData == -1){
    		printf("SUCCESS\n");
    		maiMultDeOData=1;
    	}
    	
    	
    	while((entry = readdir(dir)) != NULL) {
        if(strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) {
            snprintf(fullPath, 512, "%s/%s", path, entry->d_name);
            if(lstat(fullPath, &statbuf) == 0) {
                printf("%s\n", fullPath);
                if(S_ISDIR(statbuf.st_mode)) {
                     afisareRecursiva(fullPath, maiMultDeOData);
                }
            }
        }
    }
      
   
   }
   closedir(dir); 
}
////////////////////////////////////////////////////////////////////////////////////////
int afisareNerecursivaSize(char*path,int size)
{
    DIR* dir=NULL;
    dir=opendir(path);
    struct dirent *entry;
    struct stat statbuf;
    char fullPath[512];
    
    if (dir!=NULL){
        printf("SUCCESS\n");
        
        while((entry=readdir(dir))!=NULL){
            if(strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0){
                snprintf(fullPath, 512, "%s/%s", path, entry->d_name); 
            	if(lstat(fullPath, &statbuf) == 0) { 
            	
            	       if(S_ISDIR(statbuf.st_mode)) {
            	          ;
            	       }else{
                    		 if(statbuf.st_size < size){
                		printf("%s/%s\n", path, entry->d_name);
                		}
                        }	             
                      		
                      	
               }
            }       
        }
        
    	free(path);    
    	closedir(dir);
    	return 0;
    }
    else{
        printf("ERROR\n invalid directory path");
        return -1;
    }
}
//////////////////////////////////////////////////////////////////////////////
void afisareRecursivaSize(char*path,int maiMultDeOData,int size)
{
    DIR *dir = NULL;
    struct dirent *entry = NULL;
    char fullPath[1024];
    struct stat statbuf;

    dir = opendir(path);
    if(dir == NULL) {
        perror("ERROR\n invalid directory path");
        return;
    }
    else{
    
    	if(maiMultDeOData == -1){
    		printf("SUCCESS\n");
    		maiMultDeOData=1;
    	}
    	
    	
    	while((entry = readdir(dir)) != NULL) {
        if(strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) {
            snprintf(fullPath, 1024, "%s/%s", path, entry->d_name);
            if(lstat(fullPath, &statbuf) == 0) {           
                           
                if(S_ISDIR(statbuf.st_mode)) {
                     afisareRecursivaSize(fullPath, maiMultDeOData,size);
                }else{
                	if(statbuf.st_size < size){
                		  printf("%s\n", fullPath); 
                		}
                  }
            }
        }
    }
      
   
   }
   closedir(dir); 
}
/////////////////////////////////////////////////////////////////////////////////////////////
int startsWith(char* path, char*start)
{
    DIR* dir=opendir(path);
    struct dirent *entry;
    if (dir!=NULL){
        printf("SUCCESS\n");
        
        while((entry=readdir(dir))!=NULL){
            if(strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) 
                if (strstr(entry->d_name, start) == entry->d_name)
                    printf("%s/%s\n", path, entry->d_name);
        }
    //free(path);
    closedir(dir);
    return 0;
    }
    else
        return -1;
}        
/////////////////////////////////////////////////////////////////
void parsare( char* path ){
	
	int fd;
	fd = open (path, O_RDONLY);
	
	if(fd == -1){
		perror("ERROR\nCould not open the file!");
	}
	
	int header_size=0, no_of_sections=0, version=0;
	char magic;
	
	read(fd, &magic, 1);
	if(magic!='6') {
           printf("ERROR\nwrong magic");
           
           return;
    	}
    	
    	read(fd, &header_size, 2);
    	read(fd, &version, 2);
    	if(version < 123 || version > 169){
    	   printf("ERROR\nwrong version");
           
           return;
    	}
    	
    	read(fd, &no_of_sections, 1);
	if (no_of_sections < 8 || no_of_sections > 20) {
	    printf("ERROR\nwrong sect_nr");
	    
	    return;
	}
	
	char name[7];
	int type=0, offset=0, size=0;
	
	for(int i=0; i<no_of_sections; i++){
		read(fd, name, 7);
		read(fd, &type, 4);
		read(fd, &offset, 4);
		read(fd, &size, 4);
		if(type != 99 && type != 59 && type != 96){
			printf("ERROR\nwrong sect_types");
			
			return;
		}
	}
	

	printf("SUCCESS\n");
	printf("version=%d\n", version);
	printf("nr_sections=%d\n", no_of_sections);
	lseek(fd, 0, SEEK_SET);
	lseek(fd, 6, SEEK_CUR);
       for (int i = 0; i < no_of_sections; i++){   
		read (fd, &name, 7);
	        name[7]='\0';
        	read (fd, &type, 4);
        	read (fd, &offset, 4);
        	read (fd, &size, 4);
		printf("section%d: %s %d %d\n", i + 1, name, type, size);
	}

	
}
/////////////////////////////////////////////////////////////////
int getLine(int fd, int lineNr, char *line, int offset,int size){

	char *buffer = NULL;
	int  lineIndex=0, bufferPoz=0, charIndex=0;
	

	buffer = (char *) malloc( size * sizeof(char) );
	if(buffer == NULL){
		printf("Nu am reusit sa alocam destula memorie!\n");
		return -1;
	}
	
	if(lseek(fd, offset, SEEK_SET)==-1){
		printf("Nu am reusit sa ne pozitionam la inceput!\n");
		free(buffer);
		return -1;
	}
	
	if(read(fd, buffer, size)!= size){
		printf("Nu am reusit sa citim tot fisierul!\n");
		free(buffer);
		return -1;
	}
	
	
	int returnValue=-1;
	
	while(bufferPoz < size){
		if(lineIndex+1 == lineNr){
			
				if(buffer[bufferPoz] == '\n'){
					line[charIndex]=0;
					returnValue = 0;
					break;
				}
				
				line[charIndex]=buffer[bufferPoz];
				charIndex++;
			 
		}else{
			if(buffer[bufferPoz] == '\n'){
				lineIndex++;
			}
		 }
		 
		 bufferPoz++;
	}
	
	free(buffer);
	return returnValue;

}

int nrLine(int fd, int offset,int size){

	char *buffer = NULL;
	int bufferPoz=0;
	int nrlinii = 1;
	
	

	
	buffer = (char *) malloc( size * sizeof(char) );
	if(buffer == NULL){
		printf("Nu am reusit sa alocam destula memorie!\n");
		return -1;
	}
	
	if(lseek(fd, offset, SEEK_SET)==-1){
		printf("Nu am reusit sa ne pozitionam la inceput!\n");
		free(buffer);
		return -1;
	}
	
	if(read(fd, buffer, size)!= size){
		printf("Nu am reusit sa citim tot fisierul!\n");
		free(buffer);
		return -1;
	}
	
	
	
	
	while(bufferPoz < size){
		if(buffer[bufferPoz]=='\n'){
		nrlinii++;
		}
		bufferPoz++;
	}
	
	free(buffer);
	return nrlinii+1;

}

void cautareInFisier (char*path, int section, int linie){

        int fd;
	fd = open (path, O_RDONLY);
	
	
	if(fd == -1){
		perror("ERROR\nCould not open the file!");
	}
	
	int header_size=0, no_of_sections=0, version=0;
	char magic;
	
	read(fd, &magic, 1);
	if(magic!='6') {
           printf("ERROR\ninvalid file");
           
           return;
    	}
    	
    	read(fd, &header_size, 2);
    	read(fd, &version, 2);
    	if(version < 123 || version > 169){
    	   printf("ERROR\ninvalid file");
           
           return;
    	}
    	
    	read(fd, &no_of_sections, 1);
	if (no_of_sections < 8 || no_of_sections > 20) {
	    printf("ERROR\ninvalid file");
	    
	    return;
	}
	
	char name[7];
	int type=0, offset=0, size=0;
	
	for(int i=0; i<no_of_sections; i++){
		read(fd, name, 7);
		read(fd, &type, 4);
		read(fd, &offset, 4);
		read(fd, &size, 4);
		if(type != 99 && type != 59 && type != 96){
			printf("ERROR\ninvalid file");
			
			return;
		}
		
		if(i == section - 1){
			char *linia=(char *) malloc( size * sizeof(char) );
			int nrlinii=nrLine(fd,offset,size);
			linie = nrlinii - linie;
			if(getLine(fd,linie,linia,offset,size)!=0){
    
      				printf("ERROR\ninvalid line");
      				free(linia);
      				return;
   			 }else{
    				printf("SUCCESS\n");
    				for(int j=strlen(linia)-1; j>=0; j--)
                                printf("%c", linia[j]);
                               free(linia);
                               return; 
   			 }	
		
		} 
	}
	
	printf("ERROR\nnvalid section");

}
////////////////////////////////////////////////////////////////////////////////////////////////////////
int ok(char* path){
	int fd;
	fd = open (path, O_RDONLY);
	if(fd == -1){
		perror("ERROR\nCould not open the file!");
	}
	
	int header_size=0, no_of_sections=0, version=0;
	char magic;
	
	read(fd, &magic, 1);
	if(magic!='6') {
           return-1;
    	}
    	
    	read(fd, &header_size, 2);
    	read(fd, &version, 2);
    	if(version < 123 || version > 169){
    		return-1;
    	}
    	
    	read(fd, &no_of_sections, 1);
	if (no_of_sections < 8 || no_of_sections > 20) {
	    return-1;
	}
	
	char name[7];
	int type=0, offset=0, size=0;
	int numar = 0;
	for(int i=0; i<no_of_sections; i++){
		read(fd, name, 7);
		read(fd, &type, 4);
		read(fd, &offset, 4);
		read(fd, &size, 4);
		if(type != 99 && type != 59 && type != 96){
			return-1;
		}
		
		if(size <= 1175){
		 numar++;
		}
	}
	
	if(no_of_sections == numar){
		return 0;
	}
	
	return -1;
		
}

void findall(char*path, int maiMultDeOData)
{
    DIR *dir = NULL;
    struct dirent *entry = NULL;
    char fullPath[512];
    struct stat statbuf;
    

    dir = opendir(path);
    if(dir == NULL) {
        perror("ERROR\n invalid directory path");
        return;
    }
    else{
    
    	if(maiMultDeOData == -1){
    		printf("SUCCESS\n");
    		maiMultDeOData=1;
    	}
    	
    	
    	while((entry = readdir(dir)) != NULL) {
        if(strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) {
            snprintf(fullPath, 512, "%s/%s", path, entry->d_name);
            if(lstat(fullPath, &statbuf) == 0) {
            	if (S_ISREG(statbuf.st_mode)){
            		 
            		if(ok(fullPath)==0){
                		printf("%s\n", fullPath);	
                	}	
                	
                }	
                if(S_ISDIR(statbuf.st_mode)) {
                     findall(fullPath, maiMultDeOData);
                }
            }
        }
    }
      
   
   }
   closedir(dir); 
}




int main(int argc, char **argv){
    
    
    if(argc >= 2){
        if(strcmp(argv[1], "variant") == 0){
            printf("79531\n");
        }       

        if(strcmp(argv[1], "list") == 0){ 
        	if (strstr(argv[2], "path=")){
            		char*path= (char*)malloc(sizeof(char)*strlen(argv[2])-5);
                	strcpy(path, argv[2]+5);                
                	afisareNerecursiva(path);                   
               }
               
               if(strcmp(argv[2], "recursive") == 0 ){  
               	if( strstr(argv[3], "path=")){            
            	        	char*path= (char*)malloc(sizeof(char)*strlen(argv[3])-5);            
               		strcpy(path, argv[3]+5);                
                		afisareRecursiva(path,-1);   
                		free(path); 
               	}
               	
               	if (strstr(argv[3], "size_smaller=")){   
               		char *fsize = (char*)malloc(sizeof(char)*strlen(argv[3])-13);   
             			int size;
             			strcpy(fsize, argv[3]+13);
             			size=atoi(fsize);
             			
             	
             			if (strstr(argv[4], "path=")){
             	 			char*path= (char*)malloc(sizeof(char)*strlen(argv[4])-5);            
                			strcpy(path, argv[4]+5);
                			
                			afisareRecursivaSize(path,-1,size); 
                			free(path);                   
                
                		} 
                		free(fsize);       	
           		
           		}
           	
           		if (strstr(argv[3], "name_starts_with=")){
           			char *start = (char*)malloc(sizeof(char)*strlen(argv[3])-17);   
             			strcpy(start, argv[3]+17);
             	
             			if (strstr(argv[4], "path=")){
             	 			char*path= (char*)malloc(sizeof(char)*strlen(argv[4])-5);            
                			strcpy(path, argv[4]+5);
                
                			startsWith(path,start);
                	                
                	                free(path);                   
                
                		}
                		free(start);
           		}     
           	}
           	
           	if (strstr(argv[2],"size_smaller=")){
           		char *fsize = (char*)malloc(sizeof(char)*strlen(argv[2])-13);   
             		int size;
             		strcpy(fsize, argv[2]+13);
             		size=atoi(fsize);
             		
             		if (strstr(argv[3], "path=")){
            				char*path= (char*)malloc(sizeof(char)*strlen(argv[3])-5);
                			strcpy(path, argv[3]+5);                
                			afisareNerecursivaSize(path,size);
                			                   
               	}          	
           		if(strcmp(argv[3], "recursive") == 0 ){
           			if (strstr(argv[4], "path=")){
            				char*path= (char*)malloc(sizeof(char)*strlen(argv[4])-4);
                			strcpy(path, argv[4]+5);                
                			afisareRecursivaSize(path,-1,size);
                			free(path);                  
               		}
           		}
           		free(fsize);            		
           			
           	}
           	
           	if (strstr(argv[2], "name_starts_with=")){
           		char *start = (char*)malloc(sizeof(char)*strlen(argv[2])-17);   
             		strcpy(start, argv[2]+17);
             	
             		if (strstr(argv[3], "path=")){
             	 		char*path= (char*)malloc(sizeof(char)*strlen(argv[3])-4);            
                		strcpy(path, argv[3]+5);                
                		startsWith(path,start);
                		free(start); 
                		free(path);
                	                          
                
                	}
                	if(strcmp(argv[3], "recursive") == 0 ){
           			if (strstr(argv[4], "path=")){
            				char*path= (char*)malloc(sizeof(char)*strlen(argv[4])-5);
                			strcpy(path, argv[4]+5);                
                			startsWith(path,start);
                			free(start); 
                			free(path); 
                	                           
               		}
           		}
           		
           		
           	}
      
       }
       
       if(strcmp(argv[1], "parse") == 0){
       	if (strstr(argv[2], "path=")){
            		char*path= (char*)malloc(sizeof(char)*strlen(argv[2])-5);
                	strcpy(path, argv[2]+5);                
                	parsare(path);
                	free(path);                   
               }
       }
       
       if(strstr(argv[1], "path=")){
       	if(strcmp(argv[2], "parse") == 0){
       		char*path= (char*)malloc(sizeof(char)*strlen(argv[1])-5);
                	strcpy(path, argv[1]+5);                
                	parsare(path);
                	free(path); 
       	}
       }
       
       if(strcmp(argv[1], "extract") == 0){
       	if (strstr(argv[2], "path=")){
       		char*path= (char*)malloc(sizeof(char)*strlen(argv[2])-4);
       		strcpy(path, argv[2]+5);
       		if(strstr(argv[3], "section=")){
       			char*sec= (char*)malloc(sizeof(char)*strlen(argv[3])-8);
       			strcpy(sec, argv[3]+8);
       			int section = atoi(sec);
       			free(sec);
       			if(strstr(argv[4], "line=")){
       				char*lin= (char*)malloc(sizeof(char)*strlen(argv[4])-5);
       				strcpy(lin, argv[4]+5);
       				int line = atoi(lin);
       				free(lin);
       				
       				cautareInFisier (path, section, line);
       			}
       		
       		} 
       		free(path);      		
       	}
       }
       
       if(strcmp(argv[1], "findall") == 0){
       	if (strstr(argv[2], "path=")){
       		char*path= (char*)malloc(sizeof(char)*strlen(argv[2])-4);
       		strcpy(path, argv[2]+5);
       		findall(path,-1);
       		free(path);
       	}	
       }
          

   }

    return 0;
}
