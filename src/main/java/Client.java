

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args){
        String hostname = args[0];
        String ipAddress = args[1];
        String port = args[2];

        try{
            //addToNamingServer(hostname, ipAddress, port);
            getFile("test", ipAddress, port);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void addToNamingServer(String hostname,String ipAddress, String port) throws IOException {
        String urlString = "http://" + ipAddress + ":" + port + "/addNode" + "?hostName="+hostname;
        URL url = new URL(urlString);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");

        printResponse(httpCon);

        httpCon.disconnect();
    }

    public static void getFile(String fileName, String ipAddress, String port) throws IOException {
        String urlString = "http://" + ipAddress + ":" + port + "/getFile" + "?filename=" + fileName;
        URL url = new URL(urlString);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("GET");

        printResponse(httpCon);

        httpCon.disconnect();
    }

    private static void printResponse(HttpURLConnection httpCon) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();

        String responseLine = null;

        while((responseLine = br.readLine()) != null){
            response.append(responseLine.trim());
        }

        br.close();

        System.out.println(response.toString());
    }
}
