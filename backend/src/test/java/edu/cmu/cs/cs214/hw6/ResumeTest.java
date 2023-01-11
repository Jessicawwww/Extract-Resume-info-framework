package edu.cmu.cs.cs214.hw6;

import static org.junit.Assert.assertTrue;


import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import edu.cmu.cs.cs214.hw6.framework.Experience;
import edu.cmu.cs.cs214.hw6.framework.Resume;

public class ResumeTest {
    @Test
    public void testResumeToString() {
        List<String> d = new ArrayList<>();
        d.add("6");
        d.add("7");
        Experience e1 = new Experience("1", "2", "3", "4", "5", d);
        Experience e2 = new Experience("2", "2", "3", "4", "5", d);
        List<Experience> l = new ArrayList<>();
        l.add(e1);
        l.add(e2);

        Resume r = new Resume(); 

        List<String> s = new ArrayList<>();
        s.add("1");
        s.add("2");

        HashMap<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);

        r.setAddress("1")
        .setEmail("2")
        .setFirstName("3")
        .setLastName("4")
        .setPhoneNumber("5")
        .setUrl("6")
        .setExperiences(l)
        .setSkills(s)
        .setWordCount(map)
        .buildJsonString();

        System.out.println(r.toString());
        System.out.println(r.toString().length());
        assertTrue(r.toString().length() == "{\"skills\":[\"1\",\"2\"],\"firstName\":\"3\",\"lastName\":\"4\",\"address\":\"1\",\"phoneNumber\":\"5\",\"wordCount\":{\"1\":1,\"2\":2},\"experiences\":[{\"plainText\":\"1.2.3.[6, 7]\",\"description\":[\"6\",\"7\"],\"startTime\":\"4\",\"location\":\"3\",\"endTime\":\"5\",\"position\":\"2\",\"title\":\"1\"},{\"plainText\":\"2.2.3.[6, 7]\",\"description\":[\"6\",\"7\"],\"startTime\":\"4\",\"location\":\"3\",\"endTime\":\"5\",\"position\":\"2\",\"title\":\"2\"}],\"email\":\"2\",\"url\":\"6\"}".length() - 4);
    }
}