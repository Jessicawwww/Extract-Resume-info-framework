package edu.cmu.cs.cs214.hw6;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.json.JSONObject;

import edu.cmu.cs.cs214.hw6.framework.DataPlugin;
import edu.cmu.cs.cs214.hw6.framework.ResumeFramework;
import fi.iki.elonen.NanoHTTPD;

public class APP extends NanoHTTPD {
    static final int PORT = 8080;
    private ResumeFramework framework;
    private HashMap<String, DataPlugin> plugins;
    public static void main(String[] args) {
        try {
            new APP();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    /**
     * Start the server at :8080 port.
     * @throws IOException
     */
    public APP() throws IOException {
        super(PORT);
        framework = new ResumeFramework();
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
        plugins = loadPlugins();
        
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        JSONObject res = new JSONObject();
        if (uri.equals("/getDataPlugins")) {
            System.out.println("Request Received: /getDataPlugins");
            res.put("plugins", plugins.keySet().toArray());
        } else if (uri.equals("/parseResume")) {
            System.out.println("Request Received: /parseResume");
            if (!params.containsKey("pluginName") || !params.containsKey("resumePath")) {
                res.put("status", 0);
                res.put("resume", new JSONObject());
                res.put("message", "Require two parameters: pluginName and resumePath");
                System.out.println("Wrong Request: there is no such data plugin: " + params.get("pluginName"));
            } else if(!this.plugins.containsKey(params.get("pluginName"))) {
                res.put("status", 0);
                res.put("resume", new JSONObject());
                res.put("message", "there is no such data plugin: " + params.get("pluginName"));
                System.out.println("Wrong Request: there is no such data plugin: " + params.get("pluginName"));
            } else {
                framework.setNewPlugin(plugins.get(params.get("pluginName")));
                try {
                    framework.setResumeDataSrc(params.get("resumePath"));
                    res.put("status", 1);
                    res.put("resume", framework.getResumeDataInJsonObject());
                    res.put("message", "success");
                } catch (IOException e) {
                    res.put("message", e.getMessage());
                    res.put("status", 0);
                    res.put("resume", new JSONObject());
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    res.put("status", 0);
                    res.put("resume", new JSONObject());
                    res.put("message", "Failed to parse your resume. Please refer to the examples." + e.getMessage());
                    System.out.println(e.getMessage());
                } 
    
            }
        }

        // /getDataPlugins  {"plugins": ["xx" , "xx"]}
        // /parseResume  POST  para1 = resumePath para2 = pluginName  Return  Json {"status": 0/1, "resume":{}, message:""} (0 failed, 1 success)
        return newFixedLengthResponse(res.toString());

    }


    /**
     * Load plugins listed in META-INF/services/...
     * @return List of instantiated plugins
     */
    private HashMap<String, DataPlugin> loadPlugins() {
        ServiceLoader<DataPlugin> plugins = ServiceLoader.load(DataPlugin.class);
        HashMap<String, DataPlugin> result = new HashMap<>();
        for (DataPlugin plugin : plugins) {
            System.out.println("Loaded plugin " + plugin.getPluginName());
            result.put(plugin.getPluginName(), plugin);
        }
        return result;
    }
}


