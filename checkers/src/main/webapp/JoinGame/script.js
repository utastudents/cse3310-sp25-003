// var connection = null;
<script src="/js/PageManager.js"></script>
const pageManager = new PageManager('ws://localhost:9180');

// UNCOMMENT THIS WHEN ADDED SOCKET SUCCESSFULLY

socket.addEventListener('message', (event) => {
    const jsonData = JSON.parse(event.data);
    switch (event.status.message){
        case "Players list retrieved successfully":
            handleUsernames(event);
            break;

        default:
            console.log("Done");
    }
});



let selections = [];
let selectionsNum = [];

// Handling entities selection
document.addEventListener('DOMContentLoaded', function() {

    const buttons = document.querySelectorAll('.selection-button');
    // const joinButton = document.querySelector('#join-game'); 

    // Verify buttons are found
    console.log("Found selection buttons:", buttons.length);
    
    buttons.forEach(button => {
        button.addEventListener('click', function() {
            console.log("Button clicked:", this.innerHTML);
            
            const value = this.innerHTML;
            const dataValue = this.getAttribute('data-value');
            
            if (this.classList.contains('selected')) {
                // Deselect the button
                this.classList.remove('selected');
                const index = selections.indexOf(value);
                if (index > -1) {
                    selections.splice(index, 1);
                    selectionsNum.splice(index, 1);
                }
            } else {
                // Only allow selection if fewer than 2 are already selected
                if (selections.length < 2) {
                    this.classList.add('selected');
                    selections.push(value);
                    selectionsNum.push(dataValue);
                }
            }
            
            // Update the display
            updateSelection();
        });
    });
});


// Update the selection display
function updateSelection() {
    // Update entity displays
    entity1 = selections[0] || 'None';
    entity2 = selections[1] || 'None';
    opponent1 = selectionsNum [0] || null;
    opponent2 = selectionsNum [1] || null;

    // Enable/disable join button
    // if (joinButton) joinButton.disabled = selections.length !== 2;
}

// window.addEventListener("load", requestPlayersUserName);

function requestPlayersUserName() {
    // This method requests the name of a specific player    
    pageManager.send('getPlayersUsername', msg)
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

function requestAllUsernames() {
    // This method sends a request to get all usernames
    pageManager.send('requestAllUsernames', msg)
}

function displayUsers(usernames) {
    // Displays users in the particular game session
    console.log("Active users in session:", usernames);
}

function addPlayerToLobby() {
    // This method adds a player to the current lobby
    // One addButton is clicked it initiates the function

    pageManager.send('addPlayerToLobby', null)
}

function handleBackButton() {
    // Button that takes user to previous page

    pageManager.send('handleBackButton', null)
}

function refereshLobbies() {
    // This method refreshes the list of game lobbies
    pageManager.send('refereshLobbies', null)
}

function joinLobby(lobbyId) {
    // This method allows the player to join a specified lobby
    // Tells page manager which two entity joined which lobby

    let action = "join";
    let opponentType1 = selectionsNum[0];
    let opponentType2 = selectionsNum[1];

    let details = {
        entity1: entity1,
        entity2: entity2,
        opponentType1: opponentType1, // "bot" (0) or "human" (1)
        opponentType2: opponentType2, // "bot" (0) or "human" (1)
        lobbyId: lobbyId,
        action: action
    };

    //     //Test if works
    // console.log("Entity1: ", entity1);
    // console.log("Entity2: ", entity2);
    // console.log("opponentType1: ", opponentType1);
    // console.log("opponentType2: ", opponentType2);
    // console.log("ACTION: ", action);
    // console.log("ID: ", lobbyId);

    pageManager.send('handleJoinGame', msg)

}

function waitLobby(lobbyId) {
    // This method allows the player to join a specified lobby
    // Tells page manager which two entity joined which lobby
    let action = "wait";
    let opponentType1 = selectionsNum[0];
    let opponentType2 = selectionsNum[1];

    let details = {
        entity1: entity1,
        entity2: entity2,
        opponentType1: opponentType1,
        opponentType2: opponentType2,
        lobbyId: lobbyId,
        action: action
    };

    let request = {
        eventType: "handleJoinGame",
        msg: JSON.stringify(details)
    };

        //Test if works
    // console.log("Entity1: ", entity1);
    // console.log("Entity2: ", entity2);
    // console.log("opponentType1: ", opponentType1);
    // console.log("opponentType2: ", opponentType2);
    // console.log("ACTION: ", action);
    // console.log("ID: ", lobbyId);

    pageManager.send('handleJoinGame', msg)
}

function displayLobbies() {
    // This method shows a list of all available lobbies

    let request = {
        eventType: "displayLobbies"
    };

    msg(request);
}
