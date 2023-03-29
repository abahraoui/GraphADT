package Team19;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GraphTest {
    private GraphADT graph;

    @Before
    public void setUp() {

        graph = new Graph();
        graph.importGraph("src/main/resources/sampleInput.txt");

    }

    @Test
    public void testImport() {
        assertTrue(graph.importGraph("src/main/resources/sampleInput.txt"));
    }

    @Test
    public void testNodeKeys() {
        graph.setStartNodeKey("0");
        assertEquals(graph.getStartNodeKey(), "0");
        graph.setEndNodeKey("10");
        assertEquals(graph.getEndNodeKey(), "10");

    }

    @Test
    public void testRandomKeys() {
        graph.setRandomStartNode();
        assertNotNull(graph.getStartNodeKey());
        graph.setRandomEndNode("easy");
        graph.setRandomEndNode("medium");
        graph.setRandomEndNode("hard");
        assertNotNull(graph.getEndNodeKey());
    }

    @Test
    public void testCheckGuess() {
        graph.setStartNodeKey("0");
        graph.setEndNodeKey("10");
       // assertTrue(graph.checkGuess(graph.correctLength), ((int) graph.correctLength));
    }

    @Test
    public void testGetEdges() {
        assertTrue(graph.getEdges().size() > 0);
    }

    @Test
    public void testSecondGraphConstructor() {
         GraphADT graphTest = new Graph(graph.getNodes());
         assertNotNull(graphTest.getNodes());
         graphTest = new Graph(graph.getNodes(), "0", "10");
         assertNotNull(graphTest.getNodes());
         assertNotNull(graphTest.getStartNodeKey());
         assertNotNull(graphTest.getEndNodeKey());


    }

    @Test
    public void testNode() {
        Node node = graph.getNodes().get(0);
        assertEquals(node.getEdgeWeight("11"), 5);
        assertEquals(node.toString(), "Node: key: 0, edges: {11=5, 1=7, 35=4, 25=4, 36=9, 47=3, 28=1}");
    }


    @Test
    public void testErrors() {
        try {
            graph.parseInput(null);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }

        try {
            graph.findShortestPath();
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
        try {
            graph.importGraph("null");
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

}
