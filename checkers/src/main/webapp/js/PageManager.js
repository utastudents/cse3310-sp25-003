class PageManager {
    constructor(websocketUrl) {
      this.websocketUrl = websocketUrl;
      this.socket = null;
      this.clientId = null;
      this.connected = false;
      this.connect();
    }
 
    connection() {
        return this.socket;
    }

    connect() {
      this.socket = new WebSocket(this.websocketUrl);
  
      this.socket.onopen = () => {
        this.connected = true;
        console.log("✅ WebSocket connected");
      };
  
      this.socket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        console.log("📩 Message from server:", message);
      
        if (message.message === "Login successful" && message.success === true) {
          console.log("🎉 Login success, redirecting to lobby");
          localStorage.setItem('user', JSON.stringify({
              username: message.username
          }));
          window.location.href = '/JoinGame/index.html';
        } else if (message.message && !message.success) {
          const errorField = document.getElementById("loginUsernameError");
          if (errorField) {
            errorField.innerText = message.message;
          }
        }
      };
      
  
      this.socket.onerror = (err) => {
        console.error("❌ WebSocket error:", err);
      };

      this.socket.onclose = () => {
        console.log("🔌 WebSocket connection closed");
        this.connected = false;
      };
    }

    send(eventType, data) {
      if (!this.connected) {
        console.error("❌ Not connected to WebSocket");
        return;
      }

      const message = {
        eventType: eventType,
        msg: data
      };

      console.log("📤 Sending message:", message);
      this.socket.send(JSON.stringify(message));
    }

    validateLogin() {
      const username = document.getElementById('loginUsername')?.value;
      const password = document.getElementById('loginPassword')?.value;

      if (!username || !password) {
        console.error("❌ Username and password are required");
        return;
      }

      this.send('login', {
        username: username,
        password: password
      });
    }
}
  
 // window.pageManager = new PageManager("ws://" + window.location.hostname + ":9180");
  
