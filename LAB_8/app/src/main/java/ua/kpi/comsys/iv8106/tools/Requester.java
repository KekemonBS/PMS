package ua.kpi.comsys.iv8106.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Queue;

public class Requester implements Runnable {
    private final String formattedUrlString;
    private final String[] parameters;
    private Queue<String> queue;

    public Requester(Queue<String> queue, String formattedUrlString, String... parameters) {
        this.queue = queue;
        this.formattedUrlString = formattedUrlString;
        this.parameters = parameters;
    }

    @Override
    public void run() {
        String res = sendRequest(formattedUrlString, parameters);
        queue.add(res);
    }

    private String sendRequest(String formattedUrlString, String[] parameters) {
        try {
            URL url = new URL(String.format(formattedUrlString,parameters));
            System.out.println(String.format(formattedUrlString,parameters));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();

            InputStreamReader isReader = new InputStreamReader(responseStream);
            BufferedReader reader = new BufferedReader(isReader);
            StringBuilder textBuilder = new StringBuilder();
            String line;
            while((line = reader.readLine())!= null){
                textBuilder.append(line);
            }
            connection.disconnect();
            return textBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
