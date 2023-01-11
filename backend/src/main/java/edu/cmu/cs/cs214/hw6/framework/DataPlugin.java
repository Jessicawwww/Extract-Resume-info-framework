package edu.cmu.cs.cs214.hw6.framework;

import java.io.IOException;
import java.util.List;

public interface DataPlugin {

    /**
     * Called (only once) when the plug-in is first registered with the
     * framework, giving the plug-in a chance to perform any initial set-up
     * before the data loading has begun (if necessary).
     *
     * @param framework The {@link DataFramework} instance with which the plug-in
     *                  was registered.
     */
    void onRegister(ResumeFramework framework);


    /**
     * Conduct initial parsing to the data source if needed, such as reading a local file or 
     * set up connection to an remote API.
     * @param path path to the data source, it can be local file path or remote api uri. if Needed.
     * @throws IOException indicate failure during reading or setting up connection
     */
    void parseDataResource(String path) throws IOException;

    /**
     * Get the name of the plugin.
     * @return name of the plugin.
     */
    String getPluginName();

    /**
     * Get the first name of the owner of the profile.
     * @return the first name of the owner of the profile.
     */
    String getFirstName();


    /**
     * Get the last name of the owner of the profile.
     * @return the last name of the owner of the profile.
     */
    String getLastName();

    /**
     * Get the Email address of the owner of the profile.
     * @return the Email address of the owner of the profile.
     */
    String getEmail();

    /**
     * Get the phone number of the owner of the profile.
     * @return the phone number of the owner of the profile.
     */
    String getPhoneNumber();

    /**
     * Get the address of the owner of the profile.
     * @return the address of the owner of the profile.
     */
    String getAddress();

    /**
     * Get the url of the owner's personal webpage(gitHub/linkedin).
     * @return the url of the owner's personal webpage(gitHub/linkedin).
     */
    String getURL();

    /**
     * Get a list of string. Each String represents a skill in the profile.
     * @return  a list of string. Each String represents a skill in the profile.
     */
    List<String> getSkills();

    /**
     * Get a list of {@link Experience}.
     * @return a list of {@link Experience}.
     */
    List<Experience> getExperience();

}