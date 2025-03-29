Group 13 – Page Manager Interface Description

Overview:
The Page Manager is responsible for routing communication between the frontend (HTML/JavaScript via WebSockets) and the Java back-end subsystems. It receives JSON-formatted messages from the client, converts them into appropriate Java structures, and forwards them to the correct subsystem.

Interfaces:
Login:
Receive the username from the client and send it DATABASE. DATABASE validates the user and responds with a user ID. Both the username and user ID are needed for other subsystems.

Join Game to Pair Up:
Take inputs from the client to indicate whether the player is a bot or a human. It also keeps track of how many players are waiting to play. This data is sent to PAIR UP along with the username and user ID.

Pair Up
PAIR UP is responsible for matching players. PAGE MANAGER sends it the username, user ID, and player type input from the Join Game interaction so it can pair players.

Game Manager:
Send the paired player info to GAME MANAGER. During gameplay, it receives the move from the client and forwards it to the GAME MANAGER, which handles the game logic and returns the updated game state.

Game Display:
GAME DISPLAY needs to show the game board and the valid moves. PAGE MANAGER sends this data to GAME DISPLAY. It also forwards player moves from the client back to the GAME MANAGER.

Summary:
PAGE MANAGER sends the user data to the SUMMARY page, including user ID, username, win/loss ratio, and the leaderboard.

Database:
PAGE MANAGER sends login information to DATABASE, which validates the user and responds with user data. It also requests leaderboard info from DATABASE when needed.



Notes:
All communication between the client and Page Mgr is over WebSockets using JSON.
Page Mgr uses method calls to communicate with other Java subsystems.
Page Mgr only routes and manages data flow.
