package edu.cmu.cs.cs214.hw6.plugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import edu.cmu.cs.cs214.hw6.framework.DataPlugin;
import edu.cmu.cs.cs214.hw6.framework.Experience;
import edu.cmu.cs.cs214.hw6.framework.ResumeFramework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths; 

public class DOCXPlugin implements DataPlugin{
    private final int num3 = 3;
    private String[] splitDocumentText;

    @Override
    public void parseDataResource(String path) throws IOException {
        String fileName = path;

        try (XWPFDocument doc = new XWPFDocument(
                Files.newInputStream(Paths.get(fileName)))) {

            XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
            String docText = xwpfWordExtractor.getText();
            splitDocumentText = docText.split("\\R");
            xwpfWordExtractor.close();
        }
    }

    @Override
    public void onRegister(ResumeFramework framework) {
        
    }

    @Override
    public String getPluginName() {
        return "DOCX";
    }
    
    @Override
    public String getFirstName() {
        String name = splitDocumentText[0];
        String[] splitName = name.split(" ");
        return splitName[0];
    }

    @Override
    public String getLastName() {
        String name = splitDocumentText[0];
        String[] splitName = name.split(" ");
        return splitName[1];
    }

    @Override
    public String getEmail() {
        String info = splitDocumentText[1];
        String[] splitInfo = info.split(" +\\| +");
        return splitInfo[1];
    }

    @Override
    public String getPhoneNumber() {
        String info = splitDocumentText[1];
        String[] splitInfo = info.split(" +\\| +");
        return splitInfo[0];
    }

    @Override
    public String getAddress() {
        String info = splitDocumentText[1];
        String[] splitInfo = info.split(" +\\| +");
        if (splitInfo.length > num3) {
            return splitInfo[num3];
        }
        return null;
    }

    @Override
    public String getURL() {
        String info = splitDocumentText[1];
        String[] splitInfo = info.split(" +\\| +");
        return splitInfo[2];
    }

    @Override
    public List<String> getSkills() {
        int startIdx = -1; 
        List<String> skills = new ArrayList<>();
        for (int i = 0; i < splitDocumentText.length; i++) {
            if (splitDocumentText[i].trim().equals("SKILLS")) {
                startIdx = i; 
                break; 
            }
        }
        if (startIdx == -1) {
            return null;
        }
        startIdx += 1;

        while (splitDocumentText[startIdx].trim() != "") {
            String skillLine = splitDocumentText[startIdx];
            String[] splitSkillLine = skillLine.split("[:,]");
            for (int j = 1; j < splitSkillLine.length; j++) {
                skills.add(splitSkillLine[j].trim());
            }
            startIdx++;
        }

        return skills;
    } 

    @Override
    public List<Experience> getExperience() {
        List<Integer> startIdxs = new ArrayList<>();
        List<Experience> experiences = new ArrayList<>();

        for (int i = 0; i < splitDocumentText.length; i++) {
            if (splitDocumentText[i].trim().equals("EDUCATION") ||
                splitDocumentText[i].trim().equals("EXPERIENCE") ||
                splitDocumentText[i].trim().equals("")) {
                    if (splitDocumentText[i + 1].split(" ").length > 1) {
                        startIdxs.add(i + 1);
                    }
            }
        }
        for (int idx : startIdxs) {
            experiences.add(createExperience(idx));
        }
        return experiences;
    }

    private Experience createExperience(int startIdx) {
        String[] splitTitle = splitDocumentText[startIdx].split(" "); 
        String[] splitDate = splitDocumentText[startIdx + 2].split(" - ");
        String title = String.join(" ", Arrays.copyOfRange(splitTitle, 0, splitTitle.length - 1)).trim();
        title = title.substring(0, title.length()-1);
        String location = String.join(" ", Arrays.copyOfRange(splitTitle, splitTitle.length - 2, splitTitle.length)).trim();
        String position = splitDocumentText[startIdx + 1].trim(); 
        String startDate = splitDate[0].trim();
        String endDate = splitDate[splitDate.length - 1].trim();

        List<String> descriptions = new ArrayList<>(); 
        startIdx = startIdx + num3;

        while (startIdx < splitDocumentText.length && splitDocumentText[startIdx].trim() != "") {
            String cleanText = splitDocumentText[startIdx].replace("\u2022 ", "");
            descriptions.add(cleanText.trim());
            startIdx++;
        }
        if (title.isEmpty())
            title = " ";
        if (position.isEmpty())
            position = " ";
        if (location.isEmpty())
            location = " ";
        if (startDate.isEmpty())
            startDate = " ";
        if (endDate.isEmpty())
            endDate = " ";
        if (descriptions.isEmpty())
            descriptions.add(" ");
        

        return new Experience(title, position, location, startDate, endDate, descriptions);
    }
    
}
