class PageManager {
    constructor(websocketUrl) {
      this.websocketUrl = websocketUrl;
      this.socket = null;
      this.clientId = null;
      this.connected = false;
      this.connect();
    }
  
    connect() {
      this.socket = new WebSocket(this.websocketUrl);
  
      this.socket.onopen = () => {
        this.connected = true;
        console.log("✅ WebSocket connected");
      };
  
      this.socket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        console.log("📩 Server response:", message);
  
        if (message.clientId !== undefined) {
          this.clientId = message.clientId;
          return;
        }
  
        if (message.type === "loginResult") {
            if (message.status.success) {
              const loginSection = document.getElementById("login-section");
              const joinSection = document.getElementById("joingame-section");
          
              if (loginSection && joinSection) {
                loginSection.style.display = "none";
                joinSection.style.display = "block";
              } else {
                console.error("❗ Sections not found in HTML.");
              }
            } else {
              const errorField = document.getElementById("loginUsernameError");
              if (errorField) {
                errorField.innerText = message.status.message;
              }
            }
          }
          
      };
  
      this.socket.onerror = (err) => {
        console.error("❌ WebSocket error:", err);
      };
    }
  
    send(eventType, data) {
      if (!this.connected) {
        console.error("WebSocket not connected!");
        return;
      }
  
      const payload = {
        eventType: eventType,
        msg: JSON.stringify(data)
      };
  
      console.log("🚀 Sending:", payload);
      this.socket.send(JSON.stringify(payload));
    }
  
    validateLogin() {
      const username = document.getElementById("loginUsername").value;
      const password = document.getElementById("loginPassword").value;
  
      this.send("login", {
        username: username,
        password: password
      });
    }
  }
  
  // 🔌 Attach to global scope
  window.pageManager = new PageManager("ws://" + window.location.hostname + ":9180");
  