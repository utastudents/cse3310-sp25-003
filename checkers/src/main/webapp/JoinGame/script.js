// var connection = null;
let entity1;
let entity2;



let selections = [];
let selectionsNum = [];

// Handling entities selection
document.addEventListener("DOMContentLoaded", function () {
  const buttons = document.querySelectorAll(".selection-button");
  // const joinButton = document.querySelector('#join-game');

  // Verify buttons are found
  console.log("Found selection buttons:", buttons.length);

  buttons.forEach((button) => {
    button.addEventListener("click", function () {
      console.log("Button clicked:", this.innerHTML);

      const value = this.innerHTML;
      const dataValue = this.getAttribute("data-value");

      if (this.classList.contains("selected")) {
        // Deselect the button
        this.classList.remove("selected");
        const index = selections.indexOf(value);
        if (index > -1) {
          selections.splice(index, 1);
          selectionsNum.splice(index, 1);
        }
      } else {
        // Only allow selection if fewer than 2 are already selected
        if (selections.length < 2) {
          this.classList.add("selected");
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
  entity1 = selections[0] || "None";
  entity2 = selections[1] || "None";
  opponent1 = selectionsNum[0] || null;
  opponent2 = selectionsNum[1] || null;

  // Enable/disable join button
  // if (joinButton) joinButton.disabled = selections.length !== 2;
}

// window.addEventListener("load", requestPlayersUserName);

function requestPlayersUserName() {
  // This method requests the name of a specific player
  let request = {
    eventType: "getPlayersUsername",
  };
  console.log("Requested UserNames");
  console.log("Message Sent: " + JSON.stringify(request));
  socket.send(JSON.stringify(request));
}

function handleUsernames(usernames) {
  // This method processes the list of usernames
  // If both player's username are present then they are assigned player
  // 1 and 2 respectively else, just player 1

  const playerList = usernames;
  if (playerList) {
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
    document.getElementById("player1").innerHTML = "Player1";
    document.getElementById("player2").innerHTML = "Player2";
  }
}

function requestAllUsername() {
  // This method sends a request to get all usernames
  let request = {
    eventType: "getAllUsernames",
  };

  socket.send(JSON.stringify(request));
}

function displayUsers(usernames) {
  // Displays users in the particular game session
  console.log("Active users in session:", usernames);
}

function addPlayerToLobby() {
  // This method adds a player to the current lobby
  // One addButton is clicked it initiates the function

  let request = {
    eventType: "addPlayerToLobby",
  };

  socket.send(JSON.stringify(request));
}

function handleBackButton() {
  // Button that takes user to previous page

  let request = {
    eventType: "goToLoginPage",
  };
  socket.send(JSON.stringify(request));
}

function refereshLobbies() {
  // This method refreshes the list of game lobbies

  let request = {
    eventType: "refreshLobbies",
  };

  socket.send(JSON.stringify(request));
}

function joinLobby(lobbyId) {
  // This method allows the player to join a specified lobby
  // Tells page manager which two entity joined which lobby

  let action = "join";
  let opponentType1 = selectionsNum[0];
  let opponentType2 = selectionsNum[1];

  let details = {
    entity1: entity1.trim(),
    entity2: entity2.trim(),
    opponentType1: opponentType1.trim(), // "bot" (0) or "human" (1)
    opponentType2: opponentType2.trim(), // "bot" (0) or "human" (1)
    lobbyId: lobbyId,
    action: action,
  };

  let request = {
    eventType: "joinGame",
    msg: JSON.stringify(details),
  };

  //Test if works
  console.log("Entity1: ", entity1);
  console.log("Entity2: ", entity2);
  console.log("opponentType1: ", opponentType1);
  console.log("opponentType2: ", opponentType2);
  console.log("ACTION: ", action);
  console.log("ID: ", lobbyId);

  console.log("Sending Message: " + JSON.stringify(request));
  socket.send(JSON.stringify(request));
}

function waitLobby(lobbyId) {
  // This method allows the player to join a specified lobby
  // Tells page manager which two entity joined which lobby
  let action = "wait";
  let opponentType1 = selectionsNum[0];
  let opponentType2 = selectionsNum[1];

  let details = {
    entity1: entity1.trim(),
    entity2: entity2.trim(),
    opponentType1: opponentType1.trim(),
    opponentType2: opponentType2.trim(),
    lobbyId: lobbyId,
    action: action,
  };

  let request = {
    eventType: "joinGame",
    msg: JSON.stringify(details),
  };

  //Test if works
  console.log("Entity1: ", entity1);
  console.log("Entity2: ", entity2);
  console.log("opponentType1: ", opponentType1);
  console.log("opponentType2: ", opponentType2);
  console.log("ACTION: ", action);
  console.log("ID: ", lobbyId);

  console.log("Sending Message: " + JSON.stringify(request));
  socket.send(JSON.stringify(request));
}

function displayLobbies() {
  // This method shows a list of all available lobbies

  let request = {
    eventType: "displayLobbies",
  };

  socket.send(JSON.stringify(request));
}
