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
    U.msg=msg;
    connection.send(JSON.stringify(U));
    console.log(JSON.stringify(U))
}

// while(!(connection.readyState === WebSocket.OPEN))    
// {};

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
        // Split the string by commas to get an array
    const playerList = usernames.status.playersList;

    // Display or assign players based on how many usernames are received
    if (usernameArray.length >= 2) {
        entity1 = playerList[0];
        entity2 = playerList[1];
        console.log("Player 1:", entity1);
        console.log("Player 2:", entity2);

        displayPlayers(2);
    }
    else if (usernameArray.length === 1) {
        const entity1 = playerList[0];
        console.log("Only one player connected. Player 1:", entity1);
        displayPlayers(1);
    }
    else {
        console.log("No players connected.");
        displayPlayers(0);
    }

}



function displayPlayers(num) {

    // This method displays the list of players that are active and logged in
    if (num === 2)
    {
        document.getElementById("player1").innerHTML = entity1;
        document.getElementById("player1").innerHTML = entity2;
    }
    else if(num === 1)
    {
        document.getElementById("player1").innerHTML = entity1;
    }
    else
    {}
    
}


function requestAllUsername() {

    // This method sends a request to get all usernames


}




function displayUsers(usernames) {
    // Displays users in the particular game session

}

function addPlayerToLobby() {

    // This method adds a player to the current lobby
    // One addButton is clicked it initites the fuction

    let request = {
        eventType: "addPlayerToLobby"
    };

    msg(request);

}

function handleBackButton() {
    //Button that takes user to previous page

    let request = {
        eventType: "goToLoginPage"
    };

    msg(request);
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


