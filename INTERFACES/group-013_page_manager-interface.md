/*
 * ===============================================
 * Group 13 – Page Manager Interface Description
 * ===============================================
 *
 * Overview:
 * The Page Manager is responsible for routing communication 
 * between the frontend (HTML/JavaScript via WebSockets) and 
 * the Java back-end subsystems. It receives JSON-formatted 
 * messages from the client, converts them into appropriate 
 * Java structures, and forwards them to the correct subsystem.
 *
 * -----------------------------------------------
 * Interfaces:
 * -----------------------------------------------
 *
 * 1. Login 
 *
 * 2. Join Game to Pair Up
 * ---------------------
 *
 * 3. Game Manager
 * ---------------------
 *
 * 4. Game Display
 * ---------------------
 *
 * 5. Summary
 * ---------------------
 * 6. Database 
 * ---------------------
 *
 * -----------------------------------------------
 * Notes:
 * -----------------------------------------------
 * - All communication between client and PageManager is over WebSocket using JSON.
 * - PageManager uses method calls to communicate with other Java subsystems.
 * - Pg Mgr only routes and manages data flow.
 *
 *
 * -----------------------------------------------
 * :
 * -----------------------------------------------
 */
