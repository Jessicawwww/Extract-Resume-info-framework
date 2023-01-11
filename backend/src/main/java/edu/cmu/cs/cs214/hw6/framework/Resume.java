package edu.cmu.cs.cs214.hw6.framework;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

public class Resume {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private String url;
    private List<String> skills;
    private List<Experience> experiences;
    private HashMap<String, Integer> wordCount;

    /**
     * Set first name.
     * @param firstName the first name to set to.
     * @return this object.
     */
    public Resume setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Set last name.
     * @param lastName the last name to set to.
     * @return this object.
     */
    public Resume setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Set Email.
     * @param email the email address to set to.
     * @return this object.
     */
    public Resume setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Set address.
     * @param address to address to set to.
     * @return this object.
     */
    public Resume setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Set phone number.
     * @param phoneNumber the phone number to set to.
     * @return this object.
     */
    public Resume setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    /**
     * Set url.
     * @param url the url to set to.
     * @return this object.
     */
    public Resume setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Set skills.
     * @param skills the skills to set to.
     * @return this object.
     */
    public Resume setSkills(List<String> skills) {
        this.skills = new ArrayList<>(skills);
        return this;
    }


    /**
     * Set experiences.
     * @param experiences The experience to set to.
     * @return this object.
     */
    public Resume setExperiences(List<Experience> experiences) {
        this.experiences = new ArrayList<>(experiences);
        Collections.sort(this.experiences);
        return this;
    }

    /**
     * Set word Count.
     * @param wordCount the word count map to set to.
     * @return this object.
     */
    public Resume setWordCount(HashMap<String, Integer> wordCount) {
        this.wordCount = wordCount;
        return this;
    }

    /**
     * Return a string by joining all the texts in this object together.
     * @return a string by joining all the texts in this object together.
     */
    public String buildPlainText() {
        if (this.experiences == null || this.skills == null) {
            throw new IllegalAccessError("Must set skills and experiences before build plain text");
        }
        StringBuilder sb = new StringBuilder();
        for (String s : this.skills) {
            sb.append(s).append(" ");
        }

        for (Experience e : this.experiences) {
            sb.append(e.getPlainText()).append(" ");
        }
        return sb.toString();
    }

    /**
     * Return a json string containing all fields in this object.
     * @return a json string containing all fields in this object.
     */
    public String buildJsonString() {
        return buildJsonObject().toString();
    }

    @Override
    public String toString() {
        return buildJsonObject().toString();
    }

    /**
     * Return a json object containing all fields in this object.
     * @return a json object containing all fields in this object.
     */
    public JSONObject buildJsonObject() {
        JSONObject json = new JSONObject();
        json.put("firstName", this.firstName);
        json.put("lastName", this.lastName);
        json.put("email", this.email);
        json.put("address", this.address);
        json.put("phoneNumber", this.phoneNumber);
        json.put("url", this.url);
        json.put("skills", this.skills);
        json.put("experiences", this.experiences);
        json.put("wordCount", new JSONObject(this.wordCount));
        return json;
    }

}
