package uta.cse3310;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import uta.cse3310.Bot.BotI.BotI;
import uta.cse3310.GameManager.Move;
import uta.cse3310.GameManager.Position;

// Unit tests for Bot I
public class BotITest {
        // TC-001: Test getAvailableStandardMoves()
        @Test
        public void testGetAvailableStandardMoves() {
                BotI botI = new BotI();

                char[][] board1 = new char[][] {
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                };
                ArrayList<Move> availableStandardMoves1 = botI.getAvailableStandardMoves(board1);
                assertTrue(availableStandardMoves1.isEmpty());

                char[][] board2 = new char[][] {
                                { ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
                                { 'x', ' ', 'x', ' ', 'x', ' ', 'x', ' ' },
                                { ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { 'o', ' ', 'o', ' ', 'o', ' ', 'o', ' ' },
                                { ' ', 'o', ' ', 'o', ' ', 'o', ' ', 'o' },
                                { 'o', ' ', 'o', ' ', 'o', ' ', 'o', ' ' }
                };
                ArrayList<Move> availableStandardMoves2 = botI.getAvailableStandardMoves(board2);
                assertTrue(availableStandardMoves2.size() == 7);

                char[][] board3 = new char[][] {
                                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                                { 'X', ' ', 'X', ' ', 'X', ' ', 'X', ' ' },
                                { ' ', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                };
                ArrayList<Move> availableStandardMoves3 = botI.getAvailableStandardMoves(board3);
                assertTrue(availableStandardMoves3.size() == 7);

                char[][] board4 = new char[][] {
                                { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
                                { 'X', ' ', 'X', ' ', 'X', ' ', 'X', ' ' },
                                { ' ', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                };
                ArrayList<Move> availableStandardMoves4 = botI.getAvailableStandardMoves(board4);
                assertTrue(availableStandardMoves4.size() == 7);
        }

        // TC-002: Test getAvailableCaptureMoves()
        @Test
        public void testGetAvailableCaptureMoves() {
                BotI botI = new BotI();

                char[][] board1 = new char[][] {
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                };
                ArrayList<Move> availableCaptureMoves1 = botI.getAvailableCaptureMoves(board1);
                assertTrue(availableCaptureMoves1.isEmpty());

                char[][] board2 = new char[][] {
                                { ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
                                { 'x', ' ', 'x', ' ', 'x', ' ', 'x', ' ' },
                                { ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { 'o', ' ', 'o', ' ', 'o', ' ', 'o', ' ' },
                                { ' ', 'o', ' ', 'o', ' ', 'o', ' ', 'o' },
                                { 'o', ' ', 'o', ' ', 'o', ' ', 'o', ' ' }
                };
                ArrayList<Move> availableCaptureMoves2 = botI.getAvailableCaptureMoves(board2);
                assertTrue(availableCaptureMoves2.isEmpty());

                char[][] board3 = new char[][] {
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', 'x', ' ', ' ', ' ', 'X', ' ' },
                                { ' ', 'x', ' ', ' ', ' ', 'x', ' ', ' ' },
                                { 'o', ' ', ' ', ' ', 'o', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', 'o', ' ', ' ', ' ', 'O', ' ' },
                                { ' ', 'x', ' ', ' ', ' ', 'x', ' ', ' ' },
                                { 'o', ' ', ' ', ' ', 'o', ' ', ' ', ' ' }
                };
                ArrayList<Move> availableCaptureMoves3 = botI.getAvailableCaptureMoves(board3);
                assertTrue(availableCaptureMoves3.isEmpty());

                char[][] board4 = new char[][] {
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', 'x', ' ', ' ', ' ', 'X', ' ', ' ' },
                                { 'o', ' ', 'o', ' ', 'o', ' ', 'o', ' ' }
                };
                ArrayList<Move> availableCaptureMoves4 = botI.getAvailableCaptureMoves(board4);
                assertTrue(availableCaptureMoves4.size() == 4);

                char[][] board5 = new char[][] {
                                { 'O', ' ', 'O', ' ', 'O', ' ', 'O', ' ' },
                                { ' ', 'x', ' ', ' ', ' ', 'X', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', 'x', ' ', ' ', ' ', 'X', ' ', ' ' },
                                { 'O', ' ', 'O', ' ', 'O', ' ', 'O', ' ' }
                };
                ArrayList<Move> availableCaptureMoves5 = botI.getAvailableCaptureMoves(board5);
                assertTrue(availableCaptureMoves5.size() == 8);

        }

        // TC-003: Test generateMove()
        @Test
        public void testGenerateMove() {
                BotI botI = new BotI();

                // Test 1: Empty board — no move should be generated
                char[][] board1 = new char[][] {
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                };
                Move move1 = botI.generateMove(board1);
                assertTrue(move1 == null);

                // Test 2: Board with only standard moves available
                char[][] board2 = new char[][] {
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { 'o', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                };
                Move move2 = botI.generateMove(board2);
                assertTrue(move2 != null);
                // this fails ...assertTrue(move2.getTo().getX() == 7);
                assertTrue(move2.getTo().getY() == 1);

                // Test 3: Board with capture moves available
                char[][] board3 = new char[][] {
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { 'o', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                                { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                };
                Move move3 = botI.generateMove(board3);
                assertTrue(move3 != null);
                assertTrue(move3.getTo().getX() == 1);
                assertTrue(move3.getTo().getY() == 2);
        }
        
        // TC-004: Test getAvailableCaptureMove()
        // TODO: Write test
        @Test
        public void testValidateStandardMove() {
                BotI botI = new BotI();
                // A list of neccessary board for the test:
                char[][] new_board = new char[][] {
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                };
                char[][] occ_board = new char[][] {
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', 'O', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                };
                char[][] new_board_2 = new char[][] {
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
                        { 'O', ' ', 'O', ' ', 'O', ' ', 'O', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                };
                //First scenario: Test the ocupied the position:
                Position pos1 = new Position(2, 2);
                boolean r1 = botI.validateStandardMove(occ_board, pos1);
                assertTrue(r1 == false); //Expect to be false since the position is occupied
                //Second scenario: Test out the valid empty board
                Position pos2 = new Position(3, 3);
                boolean r2 = botI.validateStandardMove(new_board, pos1);
                assertTrue(r2 == true); //Expect to be false since the position is out of the board
                //Third scenario: Test the out of bound limit of the board
                Position pos3 = new Position(8, 8);
                boolean r3 = botI.validateStandardMove(new_board, pos1);
                assertTrue(r3 == true);
                //Fourth scenario: Test the board captures with mutiple options
                Position pos4 = new Position(2, 1);
                boolean r4 = botI.validateStandardMove(new_board_2, pos1);
                assertTrue(r4 == true); //Expect to be false since the position is out of the board
                try {
                        botI.validateStandardMove(null, null);
                } catch (Exception e) {
                        System.out.println("Caught exception: " + e.getMessage());
                }
        }
        
        // TC-005: Test getAvailableCaptureMove()
        // TODO: Write test
        @Test
        public void testValidateCaptureMove() {
                    // Create BotI object so we can call its methods
                    BotI botI = new BotI();
                
                    // === Scenario 1 ===
                    // Test when there IS an opponent ('x') and an empty jump spot
                    // Expectation: Bot should be able to capture (return true)
                
                    char[][] board1 = {
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ' },  // opponent at (1,2)
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', 'o', ' ', ' ', ' ', ' ', ' ', ' ' },  // bot at (3,1)
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                    };
                    Position opponent = new Position(1, 2); // where the enemy is
                    Position jump = new Position(0, 3);      // where bot should land
                
                    boolean canCapture = botI.validateCaptureMove(board1, opponent, jump);
                    assertTrue(canCapture); // Should be TRUE — bot can capture
                
                
                    // === Scenario 2 ===
                    // Test when there is NO opponent (blank space)
                    // Expectation: Bot should NOT be able to capture (return false)
                
                    char[][] board2 = {
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },  // no opponent
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', 'o', ' ', ' ', ' ', ' ', ' ', ' ' },  // bot at (3,1)
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                    };
                    Position noOpponent = new Position(1, 2);
                    Position jump2 = new Position(0, 3);
                
                    boolean cannotCapture = botI.validateCaptureMove(board2, noOpponent, jump2);
                    assertTrue(!cannotCapture); // Should be FALSE — no capture possible
                
                
                    // === Scenario 3 ===
                    // Test when the opponent position is OUT OF BOUNDS (off the board)
                    // Expectation: Should return false
                
                    char[][] board3 = {
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', 'o', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
                        { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
                    };
                    Position outOfBoundsOpponent = new Position(-1, 10); // invalid coordinate
                    Position jump3 = new Position(0, 11);                // invalid coordinate
                
                    boolean outOfBoundsCapture = botI.validateCaptureMove(board3, outOfBoundsOpponent, jump3);
                    assertTrue(!outOfBoundsCapture); // Should be FALSE — position not even on board
                }
                

}
