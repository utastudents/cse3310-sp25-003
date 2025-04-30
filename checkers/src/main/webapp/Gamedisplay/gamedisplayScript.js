// Sound Manager Implementation
const soundManager = {
enabled: true,
sounds: {
    move: new Audio('../assets/sounds/piece_placed.mp3'),
    king: new Audio('../assets/sounds/player1_move.mp3'),
    capture: new Audio('../assets/sounds/piece_jumped.mp3'),
    gameStart: new Audio('../assets/sounds/player2_move.mp3'),
    gameWin: new Audio('../assets/sounds/game_won_v2.mp3'),
    gameLoss: new Audio('../assets/sounds/game_lost_v2.mp3'),
    gameDraw: new Audio('../assets/sounds/declare_draw_v2.mp3'),
    resign: new Audio('../assets/sounds/player_resigns.mp3')
},

init: function() {
    // Preload sounds
    Object.values(this.sounds).forEach(sound => {
    sound.load();
    sound.volume = 0.5; // Set volume to 50%
    });
    
    // Play game start sound when page loads
    window.addEventListener('load', () => {
    this.playSound('gameStart');
    });
    
    // Set up sound toggle button
    const soundToggle = document.getElementById('soundToggle');
    soundToggle.addEventListener('click', () => {
    this.enabled = !this.enabled;
    soundToggle.textContent = this.enabled ? '🔊' : '🔈';
    });
},

playSound: function(soundName) {
    if (this.enabled && this.sounds[soundName]) {
    this.sounds[soundName].currentTime = 0;
    this.sounds[soundName].play().catch(err => console.warn('Error playing sound:', err));
    }
}
};

// Initialize sound manager
soundManager.init();

const board = document.getElementById("board");
let currentBoardState;
let pendingMoveData = null;

function getPlayerdata(player1name, player2name, player1value, player2value, isplayer1bot, isplayer2bot){
document.getElementById("player1username").textContent = player1name;
document.getElementById("player2username").textContent = player2name;
document.getElementById("player1elo").textContent = isplayer1bot ? "BOT" : player1value;
document.getElementById("player2elo").textContent = isplayer2bot ? "BOT" : player2value;
}


function displayPlayerTurn(color, turnCheck) 
{
    while (Boolean(1)) 
    {
    if (Boolean (turnCheck)) {
    document.getElementById("player1username").style.color = color;
    document.getElementbyId("player1elo").style.color = color;
    }
    else {
    document.getElementById("player2username").style.color = color;
    document.getElementById("player2elo").style.color = color;
    }
}
}

function displayEndState(endOfGameState) {
if (endOfGameState == 0) {
    document.getElementById("endgamestate").textContent = "Draw";

}
if (endOfGameState == 1) {
    document.getElementById("endgamestate").textContent = "Loss";
}
if (endOfGameState == 2) {
    document.getElementById("endgamestate").textContent = "Win";
}
}


let selectedPiece = null;
// TODO: Having it set up this way may break if 2 or more games/lobbies are simultaneously
// running. Revisit this later.
let gameId = null;

//2d board array to pass through page manager
function getBoardStateAsCharArray() {
const boardState = Array(8).fill().map(() => Array(8).fill(' '));

for (let row = 0; row < 8; row++) {
    for (let col = 0; col < 8; col++) {
    const cell = document.querySelector(`[data-row="${row}"][data-col="${col}"]`);
    if (cell && cell.firstChild) {
        const piece = cell.firstChild;
        const isRed = piece.classList.contains('red');
        const isKing = piece.classList.contains('king');
        
        // 'o' for player 1 (red), 'x' for player 2 (blue)
        if (isRed) {
        boardState[row][col] = isKing ? 'O' : 'o';
        } else {
        boardState[row][col] = isKing ? 'X' : 'x';
        }
    }
    }
}

return boardState;
}

function createBoard() 
{
for (let row = 0; row < 8; row++) 
{
    for (let col = 0; col < 8; col++)
    {
    const cell = document.createElement("div");
    cell.classList.add("cell");
    cell.dataset.row = row;
    cell.dataset.col = col;

    const isDark = (row + col) % 2 !== 0;
    cell.classList.add(isDark ? "dark" : "light");

    // Add pieces on dark squares
    if (isDark) 
    {
        if (row < 3) {
        const piece = createPiece("red");
        cell.appendChild(piece);
        } else if (row > 4) {
        const piece = createPiece("blue");
        cell.appendChild(piece);
        }
    }

    cell.addEventListener("click", () => {
        if (selectedPiece && cell.childNodes.length === 0 && cell.classList.contains("dark")) {
        const oldRow = parseInt(selectedPiece.parentNode.dataset.row);
        const oldCol = parseInt(selectedPiece.parentNode.dataset.col);
        const newRow = parseInt(cell.dataset.row);
        const newCol = parseInt(cell.dataset.col);
        
        //current board state
        const boardState = getBoardStateAsCharArray();
        
        //this is the move data that gets passed into page manager
        pendingMoveData = {
            oldRow: oldRow,
            oldCol: oldCol,
            newRow: newRow,
            newCol: newCol,
        };
        
        //send move data to page manager
        if (socket && socket.readyState === WebSocket.OPEN) {
            let details = {
                gameId: gameId,
                board: boardState,
                fromRow: oldRow,
                fromCol: oldCol,
                toRow: newRow,
                toCol: newCol,
                // from: { row: oldRow, col: oldCol },
                // to: { row: newRow, col: newCol }
            };
            let request = {
                eventType: 'gameMove',
                msg: JSON.stringify(details)
            };
            console.log("Sending Message: "+JSON.stringify(request));
            socket.send(JSON.stringify(request));
        } else {
            console.error('WebSocket is not connected');
            cancelMove();
        }
        }
    });

    board.appendChild(cell);
    }
}

//store the initial board state, incase if the sent data is not valid
currentBoardState = getBoardStateAsCharArray();
}

function executeValidMove() {
if (!pendingMoveData) return;

const {oldRow, oldCol, newRow, newCol} = pendingMoveData;

//need to find source and target cells using pendingmove coordinates
const sourceCell = document.querySelector(`[data-row="${oldRow}"][data-col="${oldCol}"]`);
const targetCell = document.querySelector(`[data-row="${newRow}"][data-col="${newCol}"]`);
        
// Check if this is a capture move
const isCapture = Math.abs(newRow - oldRow) === 2 && Math.abs(newCol - oldCol) === 2;

if (isCapture) {
    const capturedRow = (oldRow + newRow) / 2;
    const capturedCol = (oldCol + newCol) / 2;
    const capturedCell = document.querySelector(`[data-row="${capturedRow}"][data-col="${capturedCol}"]`);
    
    if (capturedCell && capturedCell.firstChild) {
    capturedCell.removeChild(capturedCell.firstChild);
    // Play capture sound
    soundManager.playSound('capture');
    }
}

// Move the piece
targetCell.appendChild(selectedPiece);
// Highlight the piece temporarily
selectedPiece.classList.add("last-move");
setTimeout(() => {
    selectedPiece.classList.remove("last-move");
}, 2000);

selectedPiece.classList.remove("selected");

// Play move sound if not a capture
if (!isCapture) {
    soundManager.playSound('move');
}

// Check if the piece reached the opposite end to become a king
const pieceColor = selectedPiece.classList.contains("red") ? "red" : "blue";

// Red pieces become kings at row 7 (bottom), blue at row 0 (top)
if ((pieceColor === "red" && newRow === 7) || (pieceColor === "blue" && newRow === 0)) {
    selectedPiece.classList.add("king");
    // Play king sound
    soundManager.playSound('king');
}

currentBoardState = getBoardStateAsCharArray();

// reset selectedPiece and pendingMoveData
selectedPiece = null;
pendingMoveData = null;
}

//if a move is invalid, cancel it
function cancelMove() {
if (selectedPiece) {
    selectedPiece.classList.remove("selected");
    selectedPiece = null;
}
pendingMoveData = null;
}

function createPiece(color) 
{
const piece = document.createElement("div");
piece.classList.add("piece", color);

piece.addEventListener("click", (e) => {
    e.stopPropagation(); // prevent cell click
    if (selectedPiece) {
    selectedPiece.classList.remove("selected");
    }

    if (selectedPiece === piece) {
    selectedPiece = null;
    } else {
    selectedPiece = piece;
    piece.classList.add("selected");
    }
});

return piece;
}

createBoard();

// Resign button with sound
document.getElementById("resignBtn").addEventListener("click", () => {
const confirmResign = confirm("Are you sure you want to resign?");
if (confirmResign) {
    soundManager.playSound('resign');
    alert("You have resigned from the game.");
    location.reload(); // Restart the game
}
});

// Draw button with sound
document.getElementById("drawBtn").addEventListener("click", () => {
const confirmProposal = confirm("Are you sure you want to propose a draw?");
if (confirmProposal) { 
    document.getElementById("drawNotification").style.display = "block";
    document.getElementById("drawNotification").textContent = "Draw proposed! Waiting for opponent to accept...";

    const acceptBtn = document.getElementById("acceptDrawBtn");
    const timerDisplay = document.getElementById("drawTimer");

    acceptBtn.style.display = "inline-block";
    acceptBtn.disabled = false;
    timerDisplay.style.display = "block";

    let timeLeft = 10;
    timerDisplay.textContent = `Time left to accept: ${timeLeft}s`;

    const countdown = setInterval(() => {
    timeLeft--;
    timerDisplay.textContent = `Time left to accept: ${timeLeft}s`;

    if (timeLeft <= 0) {
        clearInterval(countdown);
        timerDisplay.textContent = "Draw offer expired.";
        acceptBtn.disabled = true;
    }
    }, 1000);
}
});

// Accept draw button
document.getElementById("acceptDrawBtn").addEventListener("click", () => {
const confirmAccept = confirm("Are you sure you want to accept the draw?");
if (confirmAccept) {
soundManager.playSound('gameDraw');
alert("Draw accepted! The game is a draw.");
location.reload();
}
});

document.getElementById("toggleTheme").addEventListener("click", () => {
document.body.classList.toggle("dark-theme");
});