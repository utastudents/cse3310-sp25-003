class PageManager {
    
    // initializing
    constructor(websocketUrl) {
        this.websocketUrl = websocketUrl;
        this.socket = null;
        this.clientId = null;
        this.eventHandlers = {};
        this.connected = false;
    }

    // Cconnect to the WebSocket server
    connect() {
        return new Promise((resolve, reject) => {
            this.socket = new WebSocket(this.websocketUrl);
            
            this.socket.onopen = () => {
                console.log("WebSocket connection established");
                this.connected = true;
                resolve();
            };
            
            this.socket.onclose = () => {
                console.log("WebSocket connection closed");
                this.connected = false;
            };
            
            this.socket.onerror = (error) => {
                console.error("WebSocket error:", error);
                reject(error);
            };
            
            this.socket.onmessage = (event) => {
                this.handleServerMessage(event.data);
            };
        });
    }
    
    // handle messages from the server
    handleServerMessage(messageData) {
        try {
            const message = JSON.parse(messageData);
            
            // handle initial connection message with client ID
            if (message.clientId !== undefined) {
                this.clientId = message.clientId;
                console.log("Client ID assigned:", this.clientId);
                return;
            }
            
            // route message to appropriate handler based on type
            if (message.type && this.eventHandlers[message.type]) {
                this.eventHandlers[message.type](message);
            } else {
                console.warn("Unhandled message type:", message.type);
            }
        } catch (e) {
            console.error("Error processing message:", e);
        }
    }
    
    // register an event handler for a specific message type
    on(eventType, handler) {
        this.eventHandlers[eventType] = handler;
    }
    
    // send a message to the server
    send(type, data = {}) {
        if (!this.connected) {
            console.error("Cannot send message: WebSocket not connected");
            return;
        }
        
        const message = {
            id: this.clientId,
            type: type,
            ...data
        };
        
        this.socket.send(JSON.stringify(message));
    }
    
    // login with username
    login(username) {
        this.send("login", { username });
    }
    
    // join a game
    joinGame(playerType) {
        this.send("joinGame", { 
            playerType,
            username: this.username
        });
    }
    
    // send a game move
    sendMove(gameId, moveData) {
        this.send("gameMove", { 
            moveData: JSON.stringify({
                gameId,
                ...moveData
            })
        });
    }
    
    // Request the leaderboard/summary
    requestSummary() {
        this.send("summaryRequest");
    }
    
    // disconnect from the WebSocket server
    disconnect() {
        if (this.socket) {
            this.socket.close();
        }
    }
}

