import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    public static void main(String[] args){
        String hostname = args[0];
        String ipAddress = args[1];
        String port = args[2];
        System.out.println("-----------Client started-----------");
        System.out.println("Server: " + ipAddress + " @Port: " + port);
        System.out.println("Client hostname: " + hostname);
        System.out.println("Commands: ");
        System.out.println("add: add this node to the naming server");
        System.out.println("delete: remove this node from the naming server");
        System.out.println("file \"filename\": get the ip where \"filename\" can be found");
        System.out.println("q: stop the client");
        System.out.println("-----------User input enabled-----------");

        Scanner myScanner = new Scanner(System.in);
        String cmd;
        String fileName;

        try{
            while(true){
                System.out.println("\nGive command: ");
                cmd = myScanner.nextLine();
                if(cmd.equals("add")){
                    addToNamingServer(hostname, ipAddress, port);
                } else if(cmd.equals("delete")) {
                    deleteFromNamingServer(hostname, ipAddress, port);
                } else if(cmd.equals("get")){
                    System.out.println("File name: ");
                    fileName = myScanner.nextLine();
                    getFile(fileName, ipAddress, port);
                }else if(cmd.equals("q")){
                    System.out.println("Client stopped");
                    break;
                }
            }
        } catch (IOException ioException) {
            System.out.println("Exception!");
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

    public static void deleteFromNamingServer(String hostname, String ipAddress, String port) throws IOException{
        String urlString = "http://" + ipAddress + ":" + port + "/removeNode" + "?hostName="+hostname;
        URL url = new URL(urlString);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("DELETE");

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
