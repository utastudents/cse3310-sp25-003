// var connection = null;
// let entity1;
// let entity2;

// var serverUrl;
// serverUrl = "ws://" + window.location.hostname + ":" + (parseInt(location.port) + 100);
// connection = new WebSocket(serverUrl);

// connection.onopen = function (evt) {
//     console.log("open");
// }

// connection.onclose = function (evt) {
//     console.log("close");
// }

// connection.onmessage = function (evt) {
//     var msg;
//     msg = evt.data;

//     console.log("Message received: " + msg);
//     document.getElementById("tbox").innerHTML = msg + '\n' + document.getElementById("tbox").innerHTML;
//     //const obj = JSON.parse(msg);
// }

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

let selections = [];

const buttons = document.querySelectorAll('.selection-button');
const joinButton = document.querySelector('.join-game');

buttons.forEach(button => {
    button.addEventListener('click', () => {
        const value = button.getAttribute('data-value');
        
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
            }
        }
        
        // Update the display
        updateSelectionDisplay();
    });
});

// Update the selection display
function updateSelectionDisplay() {
    // Update entity displays
    const entity1Element = document.getElementById("player1");
    const entity2Element = document.getElementById("player2");
    const selectionCountElement = document.getElementById("selectionCount");

    if (entity1Element) entity1Element.textContent = selections[0] || 'None';
    if (entity2Element) entity2Element.textContent = selections[1] || 'None';
    if (selectionCountElement) selectionCountElement.textContent = selections.length;

    // Enable/disable join button
    if (joinButton) joinButton.disabled = selections.length !== 2;
}

window.addEventListener("load", requestPlayersUserName);

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

function toggleEntitySelection(entityId) {
    // This method selects or deselects a player or bot

    let request = {
        eventType: "toggleEntitySelection",
        entityId: entityId
    };

    msg(request);
}

function joinLobby(entity1, entity2, lobbyId) {
    // This method allows the player to join a specified lobby
    // Tells page manager which two entity joined which lobby

    let request = {
        eventType: "joinLobby",
        PlayerHandle: entity1,
        opponentType: entity2,
        LobbyId: lobbyId.toString()
    };

    msg(request);
}

function displayLobbies() {
    // This method shows a list of all available lobbies

    let request = {
        eventType: "displayLobbies"
    };

    msg(request);
}

function selectPlayer(playerId) {
    // This method selects a player by their ID

    let request = {
        eventType: "selectPlayer",
        playerId: playerId
    };

    msg(request);
}
