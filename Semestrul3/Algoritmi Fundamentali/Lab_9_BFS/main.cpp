#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>
#include <string.h>
#include <vector>
#include <string>
#include<algorithm>
#include <wchar.h>

#ifdef _MSC_VER
#   include <windows.h>
#   define _CRT_SECURE_NO_WARNINGS
#   define sleep(x) Sleep(x*1000)
#else
#   include <unistd.h>
#endif

#include "bfs.h"

enum{
    COMMAND_EXIT,
    COMMAND_CLEAR,
    COMMAND_NEIGHB,
    COMMAND_BFS,
    COMMAND_BFS_STEP,
    COMMAND_BFS_TREE,
    COMMAND_PATH,
    COMMAND_PERF
};

typedef struct{
    int command;
    Point p1;
    Point p2;
}COMMAND;

#define MASK_WALL   0x01
#define MASK_START  0x10
#define MASK_FILL   0x20
#define MASK_END    0x40
#define MASK_PARENT 0xF00
#define MASK_UP     0x100
#define MASK_DOWN   0x200
#define MASK_LEFT   0x400
#define MASK_RIGHT  0x800

#ifndef _MSC_VER
//translations from Windows colors
//order:                       black, blue, green, cyan, red, magenta, yellow, white, gray, lblue, lgreen, lcyan, lred, lmagenta, lyellow, bwhite
const unsigned char CONS_COLORS_FG[] = {   30,   34,    32,   36,  31,      35,     33,    37,   90,    94,     92,    94,   91,       95,      93,     97};
const unsigned char CONS_COLORS_BG[] = {   49,   44,    42,   46,  41,      45,     43,    47,  100,   104,    102,   106,  101,      105,     103,    107};
#endif

int set_text_color(unsigned char foreground, unsigned char  background=0){
        if(background >= 16 || foreground >= 16){
            return -1;
        }
#ifdef _MSC_VER
        HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
        if(hConsole == NULL){
            return -2;
        }
        SetConsoleTextAttribute(hConsole, foreground+(background<<4));
#else
        if(!isatty(fileno(stdout))) return 0;
        printf("\e[%d;%dm", CONS_COLORS_FG[foreground], CONS_COLORS_BG[background]);
#endif
        return 0;
}

int reset_text_color(){
#ifdef _MSC_VER
        set_text_color(7,0);
#else
        if(!isatty(fileno(stdout))) return 0;
        printf("\e[0m");
#endif
        return 0;
}

int readGrid(Grid *grid)
{
    FILE *fin = fopen ("grid.txt", "r");
    int i, j;

   
    fscanf(fin, "%d%d", &(grid->rows), &(grid->cols));
    if(grid->rows >= MAX_ROWS || grid->cols >= MAX_COLS){
        return -1;
    }
    for(i=0; i<grid->rows; ++i){
        for(j=0; j<grid->cols; ++j){
            fscanf(fin, "%d", &(grid->mat[i][j]));
        }
    }
    return 0;
}

const char *get_parent_dir(int x)
{
    if((x & MASK_PARENT) == MASK_UP){
        return "/\\";
    }else if((x & MASK_PARENT) == MASK_DOWN){
        return "\\/";
    }else if((x & MASK_PARENT) == MASK_LEFT){
        return "< ";
    }else if((x & MASK_PARENT) == MASK_RIGHT){
        return " >";
    }else{
        return "  ";
    }
}

void displayGrid(const Grid *grid, int lastCommand)
{
    int i, j;
#ifdef _MSC_VER
    system("cls");
#else
    system("clear");
#endif
    for(i=0; i<grid->rows; ++i){
        if(i == 0){
            //draw the upper line
            printf("  |");
            for(j=0; j<grid->cols; ++j){
                printf("%2d|", j);
            }
            printf("\n--+");
            for(j=0; j<grid->cols; ++j){
                printf("--+");
            }
            printf("\n");
        }
        printf("%2d|", i);
        for(j=0; j<grid->cols; ++j){
            if(grid->mat[i][j] & MASK_START){
                if(grid->mat[i][j] & MASK_WALL){
                    set_text_color(2, 15);
                    printf("##");
                }else{
                    set_text_color(2, 2);
                    printf("  ");
                }
            }else if(grid->mat[i][j] & MASK_END){
                if(grid->mat[i][j] & MASK_WALL){
                    set_text_color(4, 15);
                    printf("##");
                }else{
                    set_text_color(4, 4);
                    printf("  ");
                }
            }else if(grid->mat[i][j] & MASK_FILL){
                if(grid->mat[i][j] & MASK_WALL){
                    set_text_color(1, 15);
                    printf("##");
                }else{
                    set_text_color(11, 1);
                    printf("%s", get_parent_dir(grid->mat[i][j]));
                }
            }else if(grid->mat[i][j] & MASK_WALL){
                set_text_color(0, 15);
                printf("  ");
            }else{
                printf("  ");
            }
            reset_text_color();
            printf("|");
        }
        printf("\n--+");
        for(j=0; j<grid->cols; ++j){
            printf("--+");
        }
        printf("\n");
    }
}

void readCommand(COMMAND *c, const Grid *grid)
{
    char command[100];
    char *token;
    int validCommand = 0;

    do{
        printf("> ");
        fgets(command, 100, stdin);
        memset(c, 0, sizeof(COMMAND));
        token = strtok(command, " \n");
        if(strcmp(token, "exit") == 0 || strcmp(token, "quit") == 0 || strcmp(token, "q") == 0){
            c->command = COMMAND_EXIT;
            validCommand = 1;
        }else if(strcmp(token, "clear") == 0){
            c->command = COMMAND_CLEAR;
            validCommand = 1;
        }else if(strcmp(token, "perf") == 0 || strcmp(token, "performance") == 0){
            c->command = COMMAND_PERF;
            validCommand = 1;
        }else if(strcmp(token, "neighb") == 0 || strcmp(token, "neighbors") == 0 ||
                strcmp(token, "bfs") == 0 || strcmp(token, "bfs_step") == 0 || strcmp(token, "bfs_tree") == 0 || 
                strcmp(token, "path") == 0){
            if(strcmp(token, "neighb") == 0 || strcmp(token, "neighbors") == 0){
                c->command = COMMAND_NEIGHB;
            }else if(strcmp(token, "bfs") == 0){
                c->command = COMMAND_BFS;
            }else if(strcmp(token, "bfs_step") == 0){
                c->command = COMMAND_BFS_STEP;
            }else if(strcmp(token, "bfs_tree") == 0){
                c->command = COMMAND_BFS_TREE;
            }else if(strcmp(token, "path") == 0){
                c->command = COMMAND_PATH;
            }
            c->p1.row = -1;
            c->p1.col = -1;
            token = strtok(NULL, " \n");
            if(token != NULL){
                sscanf(token, "%d", &(c->p1.row));
                token = strtok(NULL, " \n");
                if(token != NULL){
                    sscanf(token, "%d", &(c->p1.col));
                }
            }
            if(c->p1.row >=0 && c->p1.col >= 0 && c->p1.row < grid->rows && c->p1.col < grid->cols){
                if(c->command == COMMAND_PATH){
                    c->p2.row = -1;
                    c->p2.col = -1;
                    token = strtok(NULL, " \n");
                    if(token != NULL){
                        sscanf(token, "%d", &(c->p2.row));
                        token = strtok(NULL, " \n");
                        if(token != NULL){
                            sscanf(token, "%d", &(c->p2.col));
                        }
                    }
                    if(c->p2.row >=0 && c->p2.col >= 0 && c->p2.row < grid->rows && c->p2.col < grid->cols){
                        validCommand = 1;
                    }
                }else{
                    validCommand = 1;
                }
            }
        }
        if(validCommand == 0){
            set_text_color(12, 0);
            printf("Invalid command");
            reset_text_color();
            printf("\n");
        }
    }while(validCommand == 0);
}

void showErrors(std::vector<std::string> &errors)
{
    for(auto it = errors.begin(); it != errors.end(); ++it){
        set_text_color(12, 0);
        printf("%s", it->c_str());
        reset_text_color();
        printf("\n");
    }
    errors.clear();
}

int main()
{
    Grid grid, grid2;
    Graph graph = {0};
    COMMAND cmd = {0};
    char err[100];
    std::vector<std::string> errors;

    readGrid(&grid);
    grid2 = grid;
    memset(&graph, 0, sizeof(Graph));
    for(;;){
        displayGrid(&grid2, cmd.command);
        showErrors(errors);
        if(cmd.command == COMMAND_BFS_TREE){
            print_bfs_tree(&graph);
        }
        readCommand(&cmd, &grid);
        if(cmd.command == COMMAND_EXIT){
            break;
        }else if(cmd.command == COMMAND_NEIGHB){
            grid2 = grid;
            Point neighb[4];
            int nrNeighb = get_neighbors(&grid, cmd.p1, neighb);
            grid2.mat[cmd.p1.row][cmd.p1.col] |= MASK_START;
            if(grid.mat[cmd.p1.row][cmd.p1.col] & MASK_WALL){
                snprintf(err, 100, "Starting position (%d, %d) is inside a wall.", cmd.p1.row, cmd.p1.col);
                errors.push_back(err);
            }
            for(int i=0; i<nrNeighb; ++i){
                if( neighb[i].row >= 0 && neighb[i].row < grid.rows &&
                    neighb[i].col >= 0 && neighb[i].col < grid.cols){
                        grid2.mat[neighb[i].row][neighb[i].col] |= MASK_FILL;
                        if(grid.mat[neighb[i].row][neighb[i].col] & MASK_WALL){
                            snprintf(err, 100, "Position (%d, %d) is inside a wall.", neighb[i].row, neighb[i].col);
                            errors.push_back(err);
                        }
                }else{
                    snprintf(err, 100, "Position (%d, %d) is outside the grid.", neighb[i].row, neighb[i].col);
                    errors.push_back(err);
                }
            }
        }else if(cmd.command == COMMAND_BFS || cmd.command == COMMAND_BFS_STEP || cmd.command == COMMAND_BFS_TREE){
            grid2 = grid;
            grid_to_graph(&grid, &graph);
            //find the starting node
            int i;
            Node *s = NULL;
            for(i=0; i<graph.nrNodes; ++i){
                if(graph.v[i]->position.row == cmd.p1.row && graph.v[i]->position.col == cmd.p1.col){
                    s = graph.v[i];
                    break;
                }
            }
            if(s == NULL){
                snprintf(err, 100, "Invalid starting position (%d, %d).", cmd.p1.row, cmd.p1.col);
                errors.push_back(err);
            }else{
                bfs(&graph, s);
                grid2.mat[cmd.p1.row][cmd.p1.col] |= MASK_START;
                for(i=0; i<graph.nrNodes; ++i){
                    if(graph.v[i]->color == COLOR_BLACK){
                        Point p1, p2;
                        p1 = graph.v[i]->position;
                        if(graph.v[i]->parent != NULL){
                            p2 = graph.v[i]->parent->position;
                            if(p1.row < p2.row){
                                grid2.mat[p1.row][p1.col] |= MASK_DOWN;
                            }else if(p1.row > p2.row){
                                grid2.mat[p1.row][p1.col] |= MASK_UP;
                            }else if(p1.col > p2.col){
                                grid2.mat[p1.row][p1.col] |= MASK_LEFT;
                            }else if(p1.col < p2.col){
                                grid2.mat[p1.row][p1.col] |= MASK_RIGHT;
                            }
                        }
                    }
                }
                if(cmd.command == COMMAND_BFS || cmd.command == COMMAND_BFS_TREE){
                    for(i=0; i<graph.nrNodes; ++i){
                        if(graph.v[i]->color == COLOR_BLACK){
                            Point p1;
                            p1 = graph.v[i]->position;
                            grid2.mat[p1.row][p1.col] |= MASK_FILL;
                        }
                    }
                }else if(cmd.command == COMMAND_BFS_STEP){
                    int newLayer = 1;
                    int crtDist = 0;
                    displayGrid(&grid2, cmd.command);
                    while(newLayer){
                        newLayer = 0;
                        ++crtDist;
                        for(i=0; i<graph.nrNodes; ++i){
                            if(graph.v[i]->color == COLOR_BLACK && graph.v[i]->dist == crtDist){
                                newLayer = 1;
                                Point p1;
                                p1 = graph.v[i]->position;
                                grid2.mat[p1.row][p1.col] |= MASK_FILL;
                            }
                        }if(newLayer){
                            sleep(1);
                            displayGrid(&grid2, cmd.command);
                        }
                    }
                }
            }
        }else if(cmd.command == COMMAND_PATH){
            grid2 = grid;
            grid_to_graph(&grid, &graph);
            //find the starting and the end node
            int i;
            Node *start = NULL, *end = NULL;
            for(i=0; i<graph.nrNodes; ++i){
                if(graph.v[i]->position.row == cmd.p1.row && graph.v[i]->position.col == cmd.p1.col){
                    start = graph.v[i];
                }
                if(graph.v[i]->position.row == cmd.p2.row && graph.v[i]->position.col == cmd.p2.col){
                    end = graph.v[i];
                }
                if(start != NULL && end != NULL){
                    break;
                }
            }
            if(start == NULL || end == NULL || start == end){
                snprintf(err, 100, "Invalid starting and/or end position (%d, %d) -> (%d, %d).", 
                    cmd.p1.row, cmd.p1.col, cmd.p2.row, cmd.p2.col);
                errors.push_back(err);
            }else{
                Node *path[1000];
                int pathLen = shortest_path(&graph, start, end, path);
                grid2.mat[cmd.p1.row][cmd.p1.col] |= MASK_START;
                grid2.mat[cmd.p2.row][cmd.p2.col] |= MASK_END;
                if(pathLen > 0){
                    for(int i=0; i<pathLen; ++i){
                        Point p1 = path[i]->position;
                        Point p2;
                        grid2.mat[p1.row][p1.col] |= MASK_FILL;
                        if(i < pathLen - 1){
                            p2 = path[i+1]->position;
                        }else{
                            p2 = end->position;
                        }
                        if(p1.row < p2.row){
                            grid2.mat[p1.row][p1.col] |= MASK_DOWN;
                        }else if(p1.row > p2.row){
                            grid2.mat[p1.row][p1.col] |= MASK_UP;
                        }else if(p1.col > p2.col){
                            grid2.mat[p1.row][p1.col] |= MASK_LEFT;
                        }else if(p1.col < p2.col){
                            grid2.mat[p1.row][p1.col] |= MASK_RIGHT;
                        }
                    }
                }
            }
        }else if(cmd.command == COMMAND_PERF){
            performance();
        }else{
            grid2 = grid;
        }
    }
    free_graph(&graph);
    
    return 0;
}
