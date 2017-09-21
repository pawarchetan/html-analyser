package de.scout24.analyser.thread;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceValidation implements Callable<Map<String, Integer>> {

    private List<String> resourceList;
    private Map<String, Integer> resourceValidationMap = new ConcurrentHashMap<>();

    public ResourceValidation(List<String> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    public Map<String, Integer> call() throws Exception {
        for (String resource: resourceList) {
            int respCode;
            try {
                respCode = getResponseCode(resource);
                resourceValidationMap.put(resource, respCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resourceValidationMap;
    }

    private static int getResponseCode(String resource) throws IOException {

        URL url = new URL(resource);

        if (url.openConnection() instanceof HttpURLConnection) {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setInstanceFollowRedirects(false);
            HttpURLConnection.setFollowRedirects(false);
            connection.connect();
            return connection.getResponseCode();
        }
        return -1;
    }
}
