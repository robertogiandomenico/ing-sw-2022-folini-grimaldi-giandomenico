# Eryantis Protocol Documentation

Alessandro Folini, Francesca Grimaldi, Roberto Giandomenico

Group 45

## Messages

### NicknameRequest
Server asks the client for a valid nickname between 3 and 10 alphanumeric characters. It is used to uniquely identify the user.

#### Response
- NicknameReply(_nickname_).


### GameNameRequest
Server asks the client for a valid game name between 3 and 10 alphanumeric characters. It is used to uniquely identify a game.

#### Response
- GameNameReply(_gameName_).
  


### GameModeRequest
Server asks the client for the game mode they want to play.

#### Response
- GameModeReply(_gameMode_).  
  mode can be one of the two: **easy** or **expert**.


### PlayerNumberRequest
Server asks the client for the number of players of the just created game.

#### Response
- PlayerNumberReply(_numPlayers_).

### TextMessage
It is used by the server to communicate a message to the client.

#### Arguments
- message: a String representing the message.

### WizardRequest
Server asks the client to select one of the available wizards.

#### Arguments
- availableWizards: list of the available wizards.

#### Response
- WizardReply(_wizard_).

### ChooseAssistantRequest
Server asks the client to select one of the available assistants.

#### Arguments
- availableAssistants: list of the available assistants.
- discardedAssistants: list of the assistants chosen by the other players in that round.
- lightBoard: a light representation of the Board.

#### Response
- ChooseAssistantReply(_assistant_).

### BoardData
Server sends the client a light version of the board to be displayed by their view.

#### Arguments
- lightBoard: a light representation of the Board.

### ActionRequest
Server asks the client to select one of the possible actions.

#### Arguments
- possibleActions: list of the possible actions.

#### Response
- ActionReply(_actionIndex_). 
  Depending on the current game phase, it can be one of the possible action type:  
  - `MOVE_STUDENT_ACTION`
  - `MOVE_MN_ACTION`
  - `BUY_CHARACTER_ACTION`
  - `NEXT_PHASE_ACTION`


### StudentRequest
Server asks the client to select one of the students to be moved.

#### Arguments
- availableColors: possible colors to be chosen. It is used to check whether the selected color is valid or not.

#### Response
- StudentReply(_studColor_).  
  _studentColor_ is the color of the chosen student.


### PlaceRequest
Server asks the client to select the place where they want to move the student.

#### Response
- PlaceReply(_place_).  
  _place_ is where the student will be moved.


### SelectCharacterRequest
Server asks the client to select one of three the available characters.

#### Arguments
- lightBoard: a light representation of the Board containing a light version of the characters.

#### Response
###### this reply contains all possible parameters that can be chosen, but only the necessary ones will be set by the user
- SelectCharacterReply(_character_,_archiIndex_,_studentNumber_,_studColors_).


### MNStepsRequest
Server asks the client to select the number of steps mother nature will do.

#### Arguments
- maxMNSteps: maximum steps can do based on the selected assistant.

#### Response
- MNStepsReply(_mnSteps_).


### CloudRequest
Server asks the client to select the cloud from which to take the students.

#### Arguments
- indexesAvailableClouds: list of the indexes of available (not empty) clouds.

#### Response
- CloudReply(_cloudIndex_).


### ResultMessage
Server communicates to the client their game result.

#### Arguments
- isWinner: boolean value that is `true` if the player is the winner or `false` in the opposite case.



## Scenarios

### Access to the game
<img src="resources/1-AccessToTheGame.png" width=400px>

1. Client accesses the server with IP address and server port;
2. once connection is established, server asks the client for their nickname until it is valid (not already taken, only 3 to 10 alphanumeric characters);
3. server asks for the name of the game they want to join:
   1. if the name corresponds to that of one existing game, the client joins the lobby;
   2. otherwise a new game with that name is created. The server asks the game mode (**easy**/**expert**) and the number of players that will join;
4. once all the players joined a lobby, the game starts and asks every client, following the connection order, to select a wizard from the list of the available ones.



### Planning Phase
<img src="resources/2-PlanningPhase.png" width=350px>

1. server asks the current player's client to play an assistant card from their deck of available assistants. The server sends only the available assistants which have not already been chosen by the other players in this round.
2. the other(s) non-current player's client(s) receive a BoardData message in order to display the board.



### Action Phase 1: Students Movement

<img src="resources/3-ActionPhase1.png" width="450"/>

1. server asks the client to do one of the following actions:
   1. select a student from the entrance and, after the reply, the place (**archipelago**/**dining room**) where they want to put it;
   2. select a character from the available ones to use their special effect. This action may only be chosen once in the whole turn.
2. The operation 2 is repeated until all the actions are executed or the "Go to next phase" action is selected (the player need to move all 3 students and not select a character to make it appear).



### Action Phase 2: Mother Nature Movement

<img src="resources/4-ActionPhase2.png" width="350"/>

Server asks the client to do one of the following actions:
    1. select a character from the available ones to use their special effect, if not chosen during the action phase 1;
    2. select the number of steps mother nature will take between 1 and maxMNSteps.
If the player does not want to play a character, the "Go to next phase" action will appear after moving Mother Nature.


### Action Phase 3: Draw from cloud
<img src="resources/5-ActionPhase3.png" width="300"/>

1. Server asks the client to choose a cloud from the list of the still full ones;
2. after the client reply, another player's turn begin.


### End of the Game
<img src="resources/6-EndOfTheGame.png" width="400"/>

When the winning conditions are met, server sends the clients their result. The message parameter will be `true` to the winner player, `false` to the others.
This message will also notify the client that the game is ending and the process will close.