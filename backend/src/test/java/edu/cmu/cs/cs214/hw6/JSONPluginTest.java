package edu.cmu.cs.cs214.hw6;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import edu.cmu.cs.cs214.hw6.framework.ResumeFramework;
import edu.cmu.cs.cs214.hw6.plugins.JSONPlugin;

public class JSONPluginTest {

    @Test
    public void testJSONPlugin() throws IOException{
        ResumeFramework f = new ResumeFramework();
        JSONPlugin p = new JSONPlugin();
        f.setNewPlugin(p);
        f.setResumeDataSrc("src/test/resources/testForJSON.txt");
        assertEquals("John", p.getFirstName());
        assertEquals("JohnDoe@andrew.cmu.edu", p.getEmail());
        System.out.println(p.getSkills());
        assertEquals("[Python, C/C++, Java, Typescript, React.js]", p.getSkills().toString());
    }
    
}
