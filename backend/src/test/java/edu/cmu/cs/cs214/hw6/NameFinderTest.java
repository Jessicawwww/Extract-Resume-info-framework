package edu.cmu.cs.cs214.hw6;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.cmu.cs.cs214.hw6.framework.WordFinder.NameFinder;

public class NameFinderTest {
    
    @Test
    public void testNameFinder() throws Exception{
        NameFinder n = new NameFinder();
        assertTrue(n.findToken("Tom likes Jack").get(0).equals("Tom"));
        assertTrue(n.findToken("Tom likes Jack").get(1).equals("Jack"));
    }
}