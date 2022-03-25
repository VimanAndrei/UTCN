#include <stdio.h>
#include <fcntl.h>
#include<stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <semaphore.h>

#include "a2_helper.h"

typedef struct {
    int id;
} TH_STRUCT;

sem_t* sem8;


void *th8_fn( void* arg){

	
	TH_STRUCT *s = (TH_STRUCT*)arg;
	
	if(s->id!=5){
		sem_wait(&sem8[s->id]);
	}
	
			
	info(BEGIN, 8, s->id);
	sem_post(&sem8[1]);
	
	if(s->id==5){
		sem_wait(&sem8[5]);
	}

	info(END, 8, s->id);
	if(s->id==1){
		sem_post(&sem8[5]);
		sem_post(&sem8[4]);
		sem_post(&sem8[3]);
		sem_post(&sem8[2]);
		
	}
	
	return NULL;
	
}

sem_t sem7_1,sem7_2;
int ruleaza=0;
void *th7_fn( void* arg){

	
	TH_STRUCT *s = (TH_STRUCT*)arg;
	sem_wait(&sem7_1);

	info(BEGIN, 7, s->id);
	sem_wait(&sem7_2);
	ruleaza++;
	
	sem_post(&sem7_2);		
	
	
	info(END, 7, s->id);
	sem_wait(&sem7_2);
	ruleaza--;
	sem_post(&sem7_2);
	sem_post(&sem7_1);
	sem_destroy(&sem7_1);	
	
	
	
	return NULL;
	
}



void *th5_fn( void* unused){

	
	TH_STRUCT *s = (TH_STRUCT*)unused;


      	info(BEGIN, 5, s->id);


    	info(END, 5, s->id);

	return NULL;
	
}

int main(int argc, char **argv)
{

	init();
	
	info(BEGIN, 1, 0);
	pid_t pid2=-1, pid3=-1;
		
	
	pid2=fork();
	if(pid2==-1){
		perror("Nu am putut creea procesul copil!");
        	return -1;
	}else if(pid2==0){
	      		info(BEGIN, 2, 0);
	      		pid_t pid4=-1, pid7=-1;
	      		pid4=fork();
	      		if(pid4==-1){
	      			perror("Nu am putut creea procesul copil!");
        			return -1;
	      		}else if(pid4==0){
	      				info(BEGIN, 4, 0);
	      				pid_t pid6=-1;
	      				pid6=fork();
	      				if(pid6==-1){
	      					perror("Nu am putut creea procesul copil!");
        					return -1;
	      				}else if(pid6==0){
	      						info(BEGIN, 6, 0);
							info(END, 6, 0);
	      					}else{
	      						waitpid(pid6,NULL,0);
	      						info(END, 4, 0);
	      					}
	      				
					
	      			}else{
	      				waitpid(pid4,NULL,0);
	      				pid7=fork();
	      				if(pid7==-1){
	      					perror("Nu am putut creea procesul copil!");
        					return -1;
	      				}else if(pid7==0){
	      						info(BEGIN, 7, 0);
	      						
	      						pthread_t tids7[38];
    							TH_STRUCT params7[38];
    							sem_init(&sem7_1,0,4);
    							sem_init(&sem7_2,0,1);
    							   							
    								 								
    							for(int i=0;i<38;i++){
    								params7[i].id=i+1;
    								pthread_create(&tids7[i], NULL, th7_fn, &params7[i]);
    							}
    							for(int i=0;i<38;i++){    										
    								pthread_join(tids7[i], NULL);
    							}
    							
    							
							info(END, 7, 0);
	      					}else{
	      						waitpid(pid7,NULL,0);
	      						info(END, 2, 0);
	      					}
	      				}	
	      		
			
	      }else{
	      		waitpid(pid2,NULL,0);
	      		pid3=fork();
	      		if(pid3==-1){
	      			perror("Nu am putut creea procesul copil!");
        			return -1;
	      		}else if(pid3==0){
					info(BEGIN, 3, 0);
					pid_t pid5=-1;
					pid5=fork();
					if(pid5==-1){
						perror("Nu am putut creea procesul copil!");
						return -1;
					}else if(pid5==0){
							info(BEGIN,5,0);
							
							pthread_t tids5[3];
    							TH_STRUCT params5[3];

    									 								
    							for(int i=0;i<4;i++){
    								params5[i].id=i+1;
    								pthread_create(&tids5[i], NULL, th5_fn, &params5[i]);
    							}
    							for(int i=0;i<4;i++){    										
    								pthread_join(tids5[i], NULL);
    							}
							
							pid_t pid8=-1, pid9=-1;
							
							pid8=fork();
							if(pid8==-1){
								perror("Nu am putut creea procesul copil!");
								return-1;
							}else if(pid8==0){
									info(BEGIN, 8, 0);
									
									pthread_t tids8[5];
    									TH_STRUCT params8[5];    
    									sem8 = (sem_t*)malloc(6*sizeof(sem_t));
    																	
    									for(int i=0;i<5;i++){
    										params8[i].id=i+1;
    										sem_init(&sem8[i],0,1);
								
    									 }
    									 sem_init(&sem8[5],0,1);
    									
    									for(int i=0;i<5;i++){
  										pthread_create(&tids8[i], NULL, th8_fn, &params8[i]);
  									
    									}
    									
    									
    									
    									for(int i=0;i<5;i++){									
    										pthread_join(tids8[i], NULL);
    									}
    									  									
    									free(sem8);
									
									info(END, 8, 0);
								}else{
									waitpid(pid8,NULL,0);
									pid9=fork();
									if(pid9==-1){
										perror("Nu am putut creea procesul copil!");
										return -1;
									}else if(pid9==0){
											info(BEGIN, 9, 0);
											info(END, 9, 0);
										}else{
											waitpid(pid9,NULL,0);
											info(END,5,0);
										}
								}
							
							
						}else{
							waitpid(pid5,NULL,0);
							info(END, 3, 0);
						}
					
				}else{
				
					waitpid(pid3,NULL,0);
					info(END, 1, 0);
				}
	      }
	
	
	
	
	
	return 0;
}
