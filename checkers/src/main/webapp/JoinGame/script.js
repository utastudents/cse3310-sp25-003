// var connection = null;

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

// class UserEvent {
//     msg;
// }

// function msg() {
//     console.log("button clicked");
//     U = new UserEvent();
//     U.msg="i pushed a button";
//     connection.send(JSON.stringify(U));
//     console.log(JSON.stringify(U))
// }

while(!(connection.readyState === WebSocket.OPEN))    
{};

window.addEventListener("load", requestPlayersUserName);

let entity1;
let entity2;

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


}



function displayPlayers() {

    // This method displays the list of players that are active and logged in

}


function requestAllUsername() {

    // This method sends a request to get all usernames


}





function displayUsers(usernames) {
    // Displays users in the particular game session
}

function addPlayer(player) {

    // This method adds a player to the current lobby
    // One addButton is clicked it initites the fuction

}

function handleBackButton() {
    //Button that takes user to previous page
}

function refereshLobbies() {

    // This method refreshes the list of game lobbies

}


function toggleEntitySelection(entityId) {

    // This method selects or deselects a player or bot

}


function joinLobby(entity1, entity2, lobbyId) {

    // This method allows the player to join a specified lobby
    // Tells page manager which two entity joined which lobby

}


function displayLobbies() {

    // This method shows a list of all available lobbies

}

function selectPlayer(playerId) {

    // This method selects a player by their ID

} 


