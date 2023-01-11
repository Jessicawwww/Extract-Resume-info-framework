package edu.cmu.cs.cs214.hw6.plugins;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import edu.cmu.cs.cs214.hw6.framework.DataPlugin;
import edu.cmu.cs.cs214.hw6.framework.Experience;
import edu.cmu.cs.cs214.hw6.framework.ResumeFramework;

public class JSONPlugin implements DataPlugin{

    private JSONObject inputJSON; 

    @Override
    public void parseDataResource(String path) throws IOException {
        File myObj = new File(path);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
            inputJSON = new JSONObject(data);
        }
        myReader.close();
    }

    @Override
    public void onRegister(ResumeFramework framework) {

    }

    @Override
    public String getPluginName() {
        return "JSON";
    }

    @Override
    public String getFirstName() {
        return inputJSON.getString("firstName");
    }

    @Override
    public String getLastName() {
        return inputJSON.getString("lastName");
    }

    @Override
    public String getEmail() {
        return inputJSON.getString("email");
    }

    @Override
    public String getPhoneNumber() {
        return inputJSON.getString("phoneNumber");
    }

    @Override
    public String getAddress() {
        return inputJSON.getString("address");
    }

    @Override
    public String getURL() {
        return inputJSON.getString("url");
    }

    @Override
    public List<String> getSkills() {
        String skills = inputJSON.getString("skills");
        skills = skills.substring(1, skills.length() - 1);
        List<String> skillsList = Arrays.asList(skills.split(", "));
        return skillsList;
    }

    @Override 
    public List<Experience> getExperience() {
        String jsonExperiences = inputJSON.getString("experiences");
        jsonExperiences = jsonExperiences.substring(1, jsonExperiences.length() - 1);
        List<String> jsonExpList = Arrays.asList(jsonExperiences.split("\\}, \\{"));
        List<Experience> experiences = new ArrayList<>();

        for (int i = 0; i < jsonExpList.size(); i++) {
            if (i == 0) {
                jsonExpList.set(i, jsonExpList.get(i) + "}");
            } else if (i == jsonExpList.size() - 1) {
                jsonExpList.set(i, "{" + jsonExpList.get(i));
            } else {
                jsonExpList.set(i, "{" + jsonExpList.get(i) + "}");
            }
        }

        for (String jsonExp : jsonExpList) {
            JSONObject exp = new JSONObject(jsonExp);
            experiences.add(creatExperience(exp));
        }

        return experiences;
    }

    private Experience creatExperience(JSONObject exp) {
        String title = exp.getString("title");
        String location = exp.getString("location");
        String position = exp.getString("position");
        String startTime = exp.getString("startTime");
        String endTime = exp.getString("endTime");

        String descriptions = exp.getString("description");
        descriptions = descriptions.substring(1, descriptions.length() - 1);
        List<String> descriptionList = Arrays.asList(descriptions.split("\\., "));
        for (int i = 0; i < descriptionList.size(); i++) {
            descriptionList.set(i, descriptionList.get(i) + ".");
        }

        return new Experience(title, position, location, startTime, endTime, descriptionList);
    }
    
} 
