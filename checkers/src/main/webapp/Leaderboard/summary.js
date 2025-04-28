function showContent(){
    document.querySelector(".hide").style.display = "block";
    document.querySelector(".show").style.display = "none";
}

function hideContent(){
    document.querySelector(".hide").style.display = "none";
    document.querySelector(".show").style.display = "flex";
}

function handleLeaderboardData(){
    
    const playerData = [
        
        {name: "Jane", id: "#193", score: 439},
        {name: "Christi", id: "#294", score: 284},
        {name: "Zach", id: "#352", score: 412},
        {name: "Debra", id: "#692", score: 391},
        {name: "Adam", id: "#572", score: 390},
        {name: "Shane", id: "#572", score: 100},
        {name: "Heidi", id: "#482", score: 201},
        {name: "Johnny", id: "#204", score: 129},
        {name: "Dave", id: "#483", score: 122},
        {name: "John", id: "#239", score: 583},
    ]

    const leaderboardDiv = document.getElementById('leaderboard');
    leaderboardDiv.innerHTML = "";

    const title = document.createElement('h1');
    title.id = 'title';

    title.innerHTML = 'Leaderboard';

    const heading = document.createElement('div');
    heading.className ='container'
    heading.id = 'board';
    heading.innerHTML = `
  <div>
    <div class="rank"><h3>Rank</h3></div>
    <div class="name"><h3>Name</h3></div>
    <div class="userId"><h3>#userId</h3></div>
    <div class="score"><h3>Wins</h3></div>
  </div>`;


    leaderboardDiv.appendChild(title);
    leaderboardDiv.appendChild(heading);


    playerData.sort((aPlayer, bPlayer) => {return bPlayer.score - aPlayer.score});

    for(let count = 1; count < 11; count++){
        
        const entry = document.createElement('div')

        entry.className = 'row seen';
        entry.id = `number${count}`;
         
        entry.innerHTML = `
        <div class="rank">${count}</div>
        <div class="name">${playerData[count - 1].name}</div>
        <div class="userId">${playerData[count - 1].id}</div>
        <div class="score">${playerData[count - 1].score}</div>`;

        heading.appendChild(entry);
    }

    const button = document.createElement('button');
    button.innerHTML = 'Return';
    button.id = 'Hidden'
    leaderboardDiv.appendChild(button);
    button.onclick = hideContent;
}

window.onload = handleLeaderboardData;

// Generate a unique ID for the client (RQ-08)
//  (not a good way to do this.  ask the server, it can give you a good id)
// const clientID = "client_" + Math.floor(Math.random() * 10000);

fetch('http://localhost:9080')
    .then(response => {
        if(!response.ok){
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.text();
    })
    .then(clientID => {
        console.log('Client ID: ', clientID);
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation: ', error);
    })

// Connect to WebSocket server (RQ-03, RQ-05)
//const socket = new WebSocket("ws://localhost:8080");

// Handle incoming messages from the server (RQ-02, RQ-04, RQ-09, RQ-10)
socket.onmessage = function(event) {
    try {
        const data = JSON.parse(event.data); // RQ-05: JSON used

        
        window.onload = handleLeaderboardData(playerData=data);

    } catch (error) {
        console.error("Unable to process leaderboard:", error);
    }
};


