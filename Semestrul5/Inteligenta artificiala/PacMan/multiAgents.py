# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class RandomAgent(Agent):
    def getAction(self, gameState):
        legalMoves = gameState.getLegalActions()
        #pick randomly among the legal
        chosenIndex = random.choice(range(0, len(legalMoves)))
        return legalMoves[chosenIndex]


class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and child states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed child
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        childGameState = currentGameState.getPacmanNextState(action)
        newPos = childGameState.getPacmanPosition()
        newFood = childGameState.getFood()
        newGhostStates = childGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"
        listFood = newFood.asList() # All remaining food as list
        ghostPos = childGameState.getGhostPositions()  # Get the ghost position

        mFoodDist = []
        # cautam distanta intre toata mancarea si pacman
        for food in listFood:
            mFoodDist.append(manhattanDistance(food, newPos))

        mGhostDist = []
        # gasim distanta intre toate fantomele si pacman
        for ghost in ghostPos:
            mGhostDist.append(manhattanDistance(ghost, newPos))
        # incercam sa il facem sa se miste
        if currentGameState.getPacmanPosition() == newPos:
            return -10000
        # daca fantoma se apropie returna un numar foarte mic
        for ghostDistance in mGhostDist:
            if ghostDistance < 2:
                return -10000
        #daca nu mai avem mancare returnam un numar foarte mare
        if len(mFoodDist) == 0:
            return 10000

        return  1000 / sum(mFoodDist) + 1000 / len(mFoodDist) + childGameState.getScore()



def scoreEvaluationFunction(currentGameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.getNextState(agentIndex, action):
        Returns the child game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"
        # Format of result = [score, action]
        # Return the action from result
        return self.get_value(gameState, 0, 0)[1]

    def get_value(self, gameState, index, depth):
        "Returneaza o tupla de compusa din scor si directie "

        "Verificam daca jocul a ajuns la final"
        if gameState.isWin() or gameState.isLose() or depth == self.depth:
            return self.evaluationFunction(gameState), ""
        if index > 0:
            "Un index mai mare ca 0 inseamna o fantoma"
            return self.min_value(gameState, index, depth)
        else:
            "Un index egal cu 0 inseamna ca e pacman"
            return self.max_value(gameState, index, depth)

    def max_value(self, gameState, index, depth):
        "Returneaza tupla cu valoarea maxima"
        maxv = -1000
        maxa = ""
        "miscarile legale care se pot face"
        legalMoves = gameState.getLegalActions(index)
        for a in legalMoves:
            successor = gameState.getNextState(index, a)
            indexS = index + 1
            depthS = depth
            "Daca este pacman actualizam adncimea si indexul"
            if indexS == gameState.getNumAgents():
                indexS = 0
                depthS += 1
            valCurenta = self.get_value(successor, indexS, depthS)
            if valCurenta[0] > maxv:
                maxv = valCurenta[0]
                maxa = a
        return maxv, maxa

    def min_value(self, gameState, index, depth):
        "Returneaza tupla cu valoarea minima"
        minv = 1000
        maxv = ""
        "miscarile legale care se pot face"
        legalMoves = gameState.getLegalActions(index)
        for a in legalMoves:
            successor = gameState.getNextState(index, a)
            indexS = index + 1
            depthS = depth
            "Daca este pacman actualizam adncimea si indexul"
            if indexS == gameState.getNumAgents():
                indexS = 0
                depthS += 1
            valCurenta = self.get_value(successor, indexS, depthS)
            if valCurenta[0] < minv:
                minv = valCurenta[0]
                maxv = a
        return minv, maxv





class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        util.raiseNotDefined()

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** YOUR CODE HERE ***"
        util.raiseNotDefined()

def betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()    

# Abbreviation
better = betterEvaluationFunction
