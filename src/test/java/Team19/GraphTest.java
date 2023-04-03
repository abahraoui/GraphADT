package Team19;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {
    private Game game;

    /**
     * This is the setUp function that will be called before each test, it creates a game and imports the graph data,
     * which are needed for multiple tests.
     */
    @Before
    public void setUp() {
        game = new Game();
        game.graph.importGraph("src/main/resources/sampleInput.txt");
    }

    /**
     * This will test the importGraph function.
     */
    @Test
    public void testImport() {
        assertTrue(game.graph.importGraph("src/main/resources/sampleInput.txt"));
    }

    /**
     * This will test the setters and getters functions of the game.
     */
    @Test
    public void testNodeKeys() {
        game.setStartNodeKey("0");
        assertEquals(game.getStartNodeKey(), "0");
        game.setEndNodeKey("10");
        assertEquals(game.getEndNodeKey(), "10");
    }

    /**
     * This helper will run setEndNodeKey until the correct length has changed,
     * as the random factor might give us the same correct length even though we changed the end node.
     * @param oldCorrectLength is the correct length that should not be equal to the game's correct length.
     */
    private void loopUntilCorrectLengthChanges(double oldCorrectLength){
        while(oldCorrectLength == game.correctLength){
            System.out.println(oldCorrectLength);
            System.out.println(game.correctLength);
            game.setEndNodeKey(game.generateEndNodeBasedOnDifficulty());
        }

    }
    /**
     * This will test random nodes setters based on difficulty and will also test the setDifficulty function of the game.
     * This is done by checking if the correct length has changed after changing difficulty.
     * The random factor might make the test fail,
     * so we use a helper function to change the end node key until the correct length changed.
     * If the correct length doesn't change,
     * it will not be a problem for the actual game but for the sake of testing we check that the correct length changes.
     */
    @Test
    public void testRandomNodeAndDifficultySetters() {
        game.setStartNodeKey(game.generateStartNodeBasedOnDifficulty());
        assertNotNull(game.getStartNodeKey());
        game.setDifficulty("easy");
        assertEquals(GameADT.Level.EASY, game.difficulty);
        game.setEndNodeKey(game.generateEndNodeBasedOnDifficulty());
        double oldCorrectLength = game.correctLength;
        game.setDifficulty("medium");
        assertEquals(GameADT.Level.MEDIUM, game.difficulty);
        loopUntilCorrectLengthChanges(oldCorrectLength);
        assertNotEquals(oldCorrectLength, game.correctLength);
        oldCorrectLength = game.correctLength;
        game.setDifficulty("hard");
        assertEquals(GameADT.Level.HARD, game.difficulty);
        loopUntilCorrectLengthChanges(oldCorrectLength);
        assertNotEquals(oldCorrectLength, game.correctLength);

    }

    /**
     * This will test the checkGuess function by guessing higher, right and lower. We put the thread to sleep as we need
     * the time played for calculating score and in real usage it's never going to be zero.
     * @throws InterruptedException can be thrown by Thread.sleep().
     */
    @Test
    public void testCheckGuess() throws InterruptedException {
        game.setStartNodeKey("0");
        game.setEndNodeKey("10");
        game.setDifficulty("easy");
        game.userPlayTime = System.currentTimeMillis();
        Thread.sleep(2000);
        assertEquals(game.checkGuess(game.correctLength+ 1), "LOWER");
        assertEquals(game.checkGuess(game.correctLength), "CORRECT your score was 1875");
        assertEquals(game.checkGuess(game.correctLength- 1), "HIGHER");
    }

    /**
     * This will test the getEdges function which returns the graph data in a data form adjusted to the Frontend/View.
     */
    @Test
    public void testGetEdges() {
        assertTrue(game.graph.getEdges().size() > 0);
    }

    /**
     *
     * This will test the calculateScore function which takes into account the number of guesses made before the player
     * guesses the correct answer and time taken to guess it. The difficulty gives a higher score.
     * @throws InterruptedException can be thrown by Thread.sleep().
     */
    @Test
    public void testCalculateScore() throws InterruptedException {
        game.userPlayTime = System.currentTimeMillis();
        Thread.sleep(3000);
        game.setDifficulty("easy");
        game.amountOfGuesses = 1;
        assertEquals( 2500 , (long) game.calculateScore());
        game.setDifficulty("medium");
        assertEquals( 5000 , (long) game.calculateScore());
        game.setDifficulty("hard");
        assertEquals( 10000 , (long) game.calculateScore());
    }

    /**
     * Here we test the createGraph functions of the Game class.
     */
    @Test
    public void testCreateGraph(){
        String response = game.createGraph("0", "10");
        assertEquals("0", game.getStartNodeKey());
        assertEquals("10", game.getEndNodeKey());
        assertNotNull(response);
        response = game.createGraph("1", "12", "easy");
        assertEquals("1", game.getStartNodeKey());
        assertEquals("12", game.getEndNodeKey());
        assertEquals(GameADT.Level.EASY, game.difficulty);
        assertNotNull(response);
    }

    /**
     * This will test the second graph constructor which we don't use for our game,
     * but can be useful for a graph implementation.
     */
    @Test
    public void testSecondGraphConstructor() {
          GraphADT<String, Double, EdgeDTO, Node> graphTest = new Graph(game.graph.getNodes());
          assertNotNull(graphTest.getNodes());
    }

    /**
     * This will test the Node class and its functions.
     */
    @Test
    public void testNode() {
       Node node = game.graph.getNodes().get(0);
       assertEquals(5, node.getEdgeWeight("11"), 0);
       assertEquals("Node: key: 0, edges: {11=5.0, 1=7.0, 35=4.0, 25=4.0, 36=9.0, 47=3.0, 28=1.0}",
               node.toString() );
    }

    /**
     * This will test the errors and exceptions thrown by functions throughout our code.
     */
    @Test
    public void testErrors() {
        try {
            game.graph.parseInput(null);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }

        try {
            game.graph.findShortestPath(null);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
        try {
            game.graph.importGraph("null");
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

}
