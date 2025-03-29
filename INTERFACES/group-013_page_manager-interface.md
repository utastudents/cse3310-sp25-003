Group 13 – Page Manager Interface Description

Overview:
The Page Mgr is responsible for routing communication between the frontend (HTML/JavaScript via WebSockets) and the Java back-end subsystems. It receives JSON-formatted messages from the client, converts them into appropriate Java structures, and forwards them to the correct subsystem.

Interfaces:
Login
Join Game to Pair Up
Game Manager
Game Display
Summary
Database

Notes:
All communication between the client and Page Mgr is over WebSockets using JSON.
Page Mgr uses method calls to communicate with other Java subsystems.
Page Mgr only routes and manages data flow.
