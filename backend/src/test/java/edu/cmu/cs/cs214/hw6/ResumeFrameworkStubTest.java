package edu.cmu.cs.cs214.hw6;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw6.framework.DataPlugin;
import edu.cmu.cs.cs214.hw6.framework.Experience;

import edu.cmu.cs.cs214.hw6.framework.ResumeFramework;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


public class ResumeFrameworkStubTest {

    DataPlugin dataPlugin;

    @Before
    public void setUp() {
        dataPlugin = mock(DataPlugin.class);
    }

    @Test
    public void stubTestGetResumeData() {
        ResumeFramework f = new ResumeFramework();
        List<String> d = new ArrayList<>();
        d.add("6");
        d.add("7");
        Experience e1 = new Experience("1", "2", "3", "4", "5", d);
        Experience e2 = new Experience("2", "2", "3", "4", "5", d);
        List<Experience> l = new ArrayList<>();
        l.add(e1);
        l.add(e2);

        List<String> s = new ArrayList<>();
        s.add("1");
        s.add("2");

        when(dataPlugin.getAddress()).thenReturn("1");
        when(dataPlugin.getFirstName()).thenReturn("2");
        when(dataPlugin.getLastName()).thenReturn("3");
        when(dataPlugin.getEmail()).thenReturn("4");
        when(dataPlugin.getPhoneNumber()).thenReturn("5");
        when(dataPlugin.getURL()).thenReturn("6");
        when(dataPlugin.getSkills()).thenReturn(s);
        when(dataPlugin.getExperience()).thenReturn(l);

        f.setNewPlugin(dataPlugin);
        System.out.println(f.getResumeData());
        assertTrue(f.getResumeData().length()== "{\"skills\":[\"1\",\"2\"],\"firstName\":\"2\",\"lastName\":\"3\",\"address\":\"1\",\"phoneNumber\":\"5\",\"wordCount\":{\"1\":2,\"2\":4,\"3\":2,\"6\":2,\"7\":2},\"experiences\":[{\"description\":[\"6\",\"7\"],\"plainText\":\"1 2 3 6, 7\",\"startTime\":\"4\",\"location\":\"3\",\"endTime\":\"5\",\"position\":\"2\",\"title\":\"1\"},{\"description\":[\"6\",\"7\"],\"plainText\":\"2 2 3 6, 7\",\"startTime\":\"4\",\"location\":\"3\",\"endTime\":\"5\",\"position\":\"2\",\"title\":\"2\"}],\"email\":\"4\",\"url\":\"6\"}".length());
    }
}