package Team19;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GraphTest {
    private Game game;
    //TODO checkGuess testing, and what we will change.
    @Before
    public void setUp() {

        game = new Game();
        game.graph.importGraph("src/main/resources/sampleInput.txt");

    }

    @Test
    public void testImport() {
        assertTrue(game.graph.importGraph("src/main/resources/sampleInput.txt"));
    }

    @Test
    public void testNodeKeys() {
        game.setStartNodeKey("0");
        assertEquals(game.getStartNodeKey(), "0");
        game.setEndNodeKey("10");
        assertEquals(game.getEndNodeKey(), "10");

    }

    @Test
    public void testRandomKeys() {
        game.setRandomStartNode();
        assertNotNull(game.getStartNodeKey());
        game.setDifficulty("easy");
        game.setRandomEndNode();
        game.setDifficulty("medium");
        game.setRandomEndNode();
        game.setDifficulty("hard");
        game.setRandomEndNode();
        assertNotNull(game.getEndNodeKey());
    }

    @Test
    public void testCheckGuess() {
        game.setStartNodeKey("0");
        game.setEndNodeKey("10");
       // assertTrue(graph.checkGuess(graph.correctLength), ((int) graph.correctLength));
    }

    @Test
    public void testGetEdges() {
        assertTrue(game.graph.getEdges().size() > 0);
    }

    @Test
    public void testSecondGraphConstructor() {
//         GraphADT graphTest = new Graph(game.graph.getNodes());
//         assertNotNull(graphTest.getNodes());
//         graphTest = new Graph(game.graph.getNodes(), "0", "10");
//         assertNotNull(graphTest.getNodes());
//         assertNotNull(graphTest.getStartNodeKey());
//         assertNotNull(graphTest.getEndNodeKey());


    }

    @Test
    public void testNode() {
//        Node node = graph.getNodes().get(0);
//        assertEquals(node.getEdgeWeight("11"), 5);
//        assertEquals(node.toString(), "Node: key: 0, edges: {11=5, 1=7, 35=4, 25=4, 36=9, 47=3, 28=1}");
    }


    @Test
    public void testErrors() {
//        try {
//            graph.parseInput(null);
//            fail("Expected exception was not thrown");
//        } catch (Exception e) {
//            assertNotNull(e);
//        }
//
//        try {
//            graph.findShortestPath();
//            fail("Expected exception was not thrown");
//        } catch (Exception e) {
//            assertNotNull(e);
//        }
//        try {
//            graph.importGraph("null");
//            fail("Expected exception was not thrown");
//        } catch (Exception e) {
//            assertNotNull(e);
//        }
    }

}
