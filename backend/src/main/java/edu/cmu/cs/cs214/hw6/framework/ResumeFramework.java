package edu.cmu.cs.cs214.hw6.framework;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;

import edu.cmu.cs.cs214.hw6.framework.WordFinder.TokenFinder;

public class ResumeFramework {
    private DataPlugin currentPlugin;
    private Resume currentResume;
    private static final HashSet<String> SIGNS = new HashSet<>(Arrays.asList(".", ",", ":", "%", "(", ")", 
    "[", "]", "?", "!", "\\", "-", "&", "*", "@", "+", "=", "-", ";"));

    /**
     * Set current plugin to the given plugin object.
     * @param plugin The plugin to set to.
     */
    public void setNewPlugin(DataPlugin plugin) {
        this.currentPlugin = plugin;
        this.currentResume = new Resume();
        
        this.currentPlugin.onRegister(this);
    }

    /**
     * Conduct initial parsing to the data source if needed, such as reading a local file or 
     * set up connection to an remote API.
     * @param srcPath path to the data source, it can be local file path or remote api uri. if Needed.
     * @throws IOException indicate failure during reading or setting up connection
     * @throws IllegalCallerException when this method is called before plugin being set.
     */
    public void setResumeDataSrc(String srcPath) throws IOException, IllegalCallerException{
        if (currentPlugin == null) {
            throw new IllegalCallerException("You much call addNewPlugin() firstly");
        }
        currentPlugin.parseDataResource(srcPath);
    }

    /**
     * Get processed Resume data in String format.
     * @return precessed resume data in string format.
     */
    public String getResumeData() {
        if (currentPlugin == null) {
            throw new IllegalCallerException("You much call addNewPlugin() firstly");
        }

        this.buildPrimaryResume();

        this.calculateWordCount();

        return this.currentResume.buildJsonString();
        
    }

    /**
     * Get processed resume data in json format.
     * @return processed resume data in json object format.
     */
    public JSONObject getResumeDataInJsonObject() {
        if (currentPlugin == null) {
            throw new IllegalCallerException("You much call addNewPlugin() firstly");
        }

        this.buildPrimaryResume();

        this.calculateWordCount();

        return this.currentResume.buildJsonObject();
    }

    private void buildPrimaryResume() {
        this.currentResume
        .setAddress(this.currentPlugin.getAddress())
        .setEmail(this.currentPlugin.getEmail())
        .setFirstName(this.currentPlugin.getFirstName())
        .setLastName(this.currentPlugin.getLastName())
        .setPhoneNumber(this.currentPlugin.getPhoneNumber())
        .setUrl(this.currentPlugin.getURL())
        .setExperiences(this.currentPlugin.getExperience())
        .setSkills(this.currentPlugin.getSkills());
    }

    private void calculateWordCount() {
        String plainText = this.currentResume.buildPlainText();

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        try {
            List<String> tokens = new TokenFinder().findToken(plainText);   

            for (String s: tokens) {
                if (ResumeFramework.SIGNS.contains(s)) {
                    continue;
                }
                map.put(s, map.getOrDefault(s, 0) + 1);
            }

            this.currentResume.setWordCount(map);
        } catch (Exception e) {
            System.out.println("Error: "  + e);
        }

    }
}


