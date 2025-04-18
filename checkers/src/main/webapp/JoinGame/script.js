// var connection = null;
let entity1;
let entity2;

class UserEvent {
    msg;
}

function msg(msg) {
    console.log("button clicked");
    U = new UserEvent();
    U.msg = msg;
    connection.send(JSON.stringify(U));
    console.log(JSON.stringify(U));
}

// while(!(connection.readyState === WebSocket.OPEN))    
// {};

// Handling entities selection

let selections = [];
let selectionsNum = [];

const buttons = document.querySelectorAll('.selection-button');
const joinButton = document.querySelector('.join-game');

buttons.forEach(button => {
    button.addEventListener('click', () => {
        const value = button.innerHTML;
        
        if (button.classList.contains('selected')) {
            // Deselect the button
            button.classList.remove('selected');
            const index = selections.indexOf(value);
            if (index > -1) {
                selections.splice(index, 1);
            }
        } else {
            // Only allow selection if fewer than 2 are already selected
            if (selections.length < 2) {
                button.classList.add('selected');
                selections.push(value);
                selectionsNum.push(button.getAttribute('data-value'))
            }
        }
        
        // Update the display
        updateSelection();
    });
});

// Update the selection display
function updateSelection() {
    // Update entity displays
    entity1 = selections[0] || 'None';
    entity2 = selections[1] || 'None';
    opponent1 = selectionsNum [0] || null;
    opponent2 = selectionsNum [1] || null;
    const selectionCountElement = document.getElementById("selectionCount");

    // Enable/disable join button
    if (joinButton) joinButton.disabled = selections.length !== 2;
}

// window.addEventListener("load", requestPlayersUserName);

function requestPlayersUserName() {
    // This method requests the name of a specific player    
    let request = {
        eventType: "getPlayersUsername"
    };

    msg(request);
}

function handleUsernames(usernames) {
    // This method processes the list of usernames
    // If both player's username are present then they are assigned player
    // 1 and 2 respectively else, just player 1

    const playerList = usernames.status.playersList;

    // Display or assign players based on how many usernames are received
    if (playerList.length >= 2) {
        entity1 = playerList[0];
        entity2 = playerList[1];
        console.log("Player 1:", entity1);
        console.log("Player 2:", entity2);
        displayPlayers(2);
    } else if (playerList.length === 1) {
        entity1 = playerList[0];
        console.log("Only one player connected. Player 1:", entity1);
        displayPlayers(1);
    } else {
        console.log("No players connected.");
        displayPlayers(0);
    }
}

function displayPlayers(num) {
    // This method displays the list of players that are active and logged in
    if (num === 2) {
        document.getElementById("player1").innerHTML = entity1;
        document.getElementById("player2").innerHTML = entity2;
    } else if (num === 1) {
        document.getElementById("player1").innerHTML = entity1;
        document.getElementById("player2").innerHTML = "Waiting...";
    } else {
        document.getElementById("player1").innerHTML = "None";
        document.getElementById("player2").innerHTML = "None";
    }
}

function requestAllUsername() {
    // This method sends a request to get all usernames
    let request = {
        eventType: "getAllUsernames"
    };

    msg(request);
}

function displayUsers(usernames) {
    // Displays users in the particular game session
    console.log("Active users in session:", usernames);
}

function addPlayerToLobby() {
    // This method adds a player to the current lobby
    // One addButton is clicked it initiates the function

    let request = {
        eventType: "addPlayerToLobby"
    };

    msg(request);
}

function handleBackButton() {
    // Button that takes user to previous page

    let request = {
        eventType: "goToLoginPage"
    };

    msg(request);
}

function refereshLobbies() {
    // This method refreshes the list of game lobbies

    let request = {
        eventType: "refreshLobbies"
    };

    msg(request);
}

function joinLobby(lobbyId) {
    // This method allows the player to join a specified lobby
    // Tells page manager which two entity joined which lobby

    let action = "join";
    let opponentType1 = selectionsNum[0];
    let opponentType2 = selectionsNum[1];

    // const joinGamePayload = {
    //     entity1: 'Player1',
    //     entity2: 'Player2',
    //     opponentType1: true, // "bot" (false) or "human" (true)
    //     opponentType2: false, // "bot" (false) or "human" (true)
    //     lobbyId: 'Lobby123',
    //     action: 'join' // or "wait"
    // };

    // msg(joinGamePayload);


    console.log("Entity1: ", entity1);
    console.log("Entity2: ", entity2);
    console.log("LobbyID: ", lobbyId);
    console.log("OpponentType1: ", opponentType1);
    console.log("OpponentType2: ", opponentType2);
    console.log("Action: ", action);

}

function waitLobby(lobbyId) {
    // This method allows the player to join a specified lobby
    // Tells page manager which two entity joined which lobby
    let action = "wait";
    let opponentType1 = selectionsNum[0];
    let opponentType2 = selectionsNum[1];


    // const waitGamePayLoad = {
    //     entity1: 'Player1',
    //     entity2: 'Player2',
    //     opponentType1: true, // "bot" (false) or "human" (true)
    //     opponentType2: false, // "bot" (false) or "human" (true)
    //     lobbyId: 'Lobby123',
    //     action: 'join' // or "wait"
    // };

    // msg(joinGamePayload);


    console.log("Entity1: ", entity1);
    console.log("Entity2: ", entity2);
    console.log("LobbyID: ", lobbyId);
    console.log("OpponentType1: ", opponentType1);
    console.log("OpponentType2: ", opponentType2);
    console.log("Action: ", action);

}

function displayLobbies() {
    // This method shows a list of all available lobbies

    let request = {
        eventType: "displayLobbies"
    };

    msg(request);
}
