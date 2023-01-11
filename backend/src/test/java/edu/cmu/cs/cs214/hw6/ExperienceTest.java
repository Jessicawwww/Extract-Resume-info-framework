package edu.cmu.cs.cs214.hw6;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.cmu.cs.cs214.hw6.framework.Experience;

public class ExperienceTest {
    
    @Test
    public void testToString() {
        List<String> d = new ArrayList<>();
        d.add("6");
        d.add("7");
        Experience e = new Experience("1", "2", "3", "4", "5", d);
        System.out.println(e.toString());
        assertTrue(e.toString().equals("{\"description\":[\"6\",\"7\"],\"location\":\"3\",\"startTime\":\"4\",\"position\":\"2\",\"endTime\":\"5\",\"title\":\"1\"}")); 
    }
    
}
