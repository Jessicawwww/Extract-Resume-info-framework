package edu.cmu.cs.cs214.hw6.framework;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

public class Experience implements Comparable{
    private String title;
    public String getTitle() {
        return title;
    }

    private String position;
    public String getPosition() {
        return position;
    }

    private String location;
    public String getLocation() {
        return location;
    }

    private String startTime;
    public String getStartTime() {
        return startTime;
    }

    private String endTime;
    public String getEndTime() {
        return endTime;
    }

    private List<String> description;
    public List<String> getDescription() {
        return description;
    }

    private String plainText;
    public String getPlainText() {
        return plainText;
    }

    public Experience(String title, String position, String location, String startTime, String endTime,
            List<String> description) {
        if (title.isEmpty() || position.isEmpty() || location.isEmpty() || 
        startTime.isEmpty() || endTime.isEmpty() || description.isEmpty()) {
            throw new IllegalArgumentException("Empty parameter!");
        }
        this.title = title;
        this.position = position;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.plainText = title + " " + position + " " + location + " " + description.toString().replace("[", "").replace("]", "");
    }

    @Override
    public String toString() {    
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("position", this.position);
        json.put("location", this.location);
        json.put("startTime", this.startTime);
        json.put("endTime", this.endTime);
        json.put("description", this.description);
        return json.toString();
    }
    
    /**
     * Return a json string represent this object.
     * @return Return a json string represent this object.
     */
    public String buildJsonString() {
        return this.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Experience e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy", Locale.ENGLISH);
            try {
                LocalDate d2 = LocalDate.parse(e.startTime, formatter);    
            } catch (Exception ex) {
                return 1;
            }

            try {
                LocalDate d1 = LocalDate.parse(this.startTime, formatter);    
            } catch (Exception ex) {
                return -1;
            }
            
            LocalDate d2 = LocalDate.parse(e.startTime, formatter);
            LocalDate d1 = LocalDate.parse(this.startTime, formatter);
            return d1.compareTo(d2);


        } else {
            throw new IllegalArgumentException();
        }
    }
}