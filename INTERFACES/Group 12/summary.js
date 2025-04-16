Reference: https://www.geeksforgeeks.org/how-to-create-a-websocket-connection-in-javascript/

// Generate a unique ID for the client (RQ-08)
const clientID = "client_" + Math.floor(Math.random() * 10000);

// Connect to WebSocket server (RQ-03, RQ-05)
const socket = new WebSocket("ws://localhost:8080");

// Handle incoming messages from the server (RQ-02, RQ-04, RQ-09, RQ-10)
socket.onmessage = function(event) {
    try {
        const data = JSON.parse(event.data); // RQ-05: JSON used

        if (!Array.isArray(data)) return;

        const leaderboard = document.querySelector("#leaderboard tbody");
        leaderboard.innerHTML = "";

        data.forEach(entry => {
            const row = document.createElement("tr");

            const id = document.createElement("td");
            id.textContent = entry.userID;

            const username = document.createElement("td");
            username.textContent = entry.username;

            const wins = document.createElement("td");
            wins.textContent = entry.wins;

            row.appendChild(id);
            row.appendChild(username);
            row.appendChild(wins);

            leaderboard.appendChild(row);
        });
    } catch (error) {
        console.error("Unable to process leaderboard:", error);
    }
};
