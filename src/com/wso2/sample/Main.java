package com.wso2.sample;

import com.google.gson.*;
import jdk.nashorn.internal.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Scanner;

public class Main {

    private static String DCR_ENDPOINT = "/client-registration/v0.14/register";
    private static String TOKEN_ENDPOINT = "/oauth2/token";
    private static String API_LIST = "/api/am/publisher/v0.14/apis";
    private static String REPUBLISH_ENDPOINT = "/api/am/publisher/v0.14/apis/change-lifecycle";
    private static String USERNAME = "admin";
    private static String PASSWORD = "admin";
    private static String APP_NAME = "rest_api_publisher_test_app";

    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        String AccessToken = null;
        API[] APIList = null;
        int NumberOfAPIs = 0;

        System.out.println("==============================================");
        System.out.println("============ Starting Java Client ============");
        System.out.println("==============================================");

        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter management URL (Ex:localhost:9443) : ");
        String mgtHostname= scanner.nextLine();
        //System.out.print("Enter Gateway URL (Ex:localhost:8243) : ");
        //String gwHostname= scanner.nextLine();
        System.out.print("Enter Tenant Admin Username (Ex:admin@tenant.com) : ");
        String username= scanner.nextLine();
        System.out.print("Enter Tenant Admin Password : ");
        String password= scanner.nextLine();
        System.out.print("Enter SP Name (Ex:rest_api_publisher_client) : ");
        String appName= scanner.nextLine();
        System.out.print("List of APIs From Config File (yes/no) : ");
        String ListOfAPIs= scanner.nextLine();
        if(ListOfAPIs.equals("yes")){
            System.out.print("Number of APIs in the list : ");
            NumberOfAPIs= scanner.nextInt();
        }

        //for testing
        //String mgtHostname = "localhost:9443";
        //String username = "niran@test.com";
        //String password = "admin";
        //String appName = "rest_api_publisher_client";
        //String ListOfAPIs = "yes";
        //NumberOfAPIs = 3;


        DCR_ENDPOINT = "https://" + mgtHostname +DCR_ENDPOINT;
        TOKEN_ENDPOINT = "https://" + mgtHostname +TOKEN_ENDPOINT;
        API_LIST= "https://" + mgtHostname +API_LIST;
        REPUBLISH_ENDPOINT= "https://" + mgtHostname +REPUBLISH_ENDPOINT;
        USERNAME= username;
        PASSWORD= password;
        APP_NAME= appName;

        // Register the Client Application
        String ClientCredentials = ClientRegistration(USERNAME,PASSWORD,APP_NAME);

        try {
            Thread.sleep(500);
        } catch(InterruptedException e) {
            System.out.println("An Exception Occurred: " + e);
        }

        // Get the Access Token
        if( ClientCredentials != null){
            AccessToken = GettingAccessToken(USERNAME,PASSWORD,ClientCredentials);
        }

        try {
            Thread.sleep(500);
        } catch(InterruptedException e) {
            System.out.println("An Exception Occurred: " + e);
        }

        if( AccessToken != null){
            // Check whether the API list is pre-defined or not
            if(ListOfAPIs.equals("yes")){
                try
                {
                    File file=new File("APIList.txt");
                    FileReader fr=new FileReader(file);
                    BufferedReader br=new BufferedReader(fr);
                    String line;

                    API TempAPIList[] = new API[NumberOfAPIs];
                    int i =0;
                    while((line=br.readLine())!=null)
                    {
                        String[] APIDetails=line.split("\\s");
                        API api = GetTenantAPI(AccessToken, APIDetails[0], APIDetails[1]);
                        TempAPIList[i] = api;
                        i++;
                    }
                    fr.close();
                    APIList =TempAPIList;
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            else{
                APIList = GetListOfTenantAPIs(AccessToken);
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    System.out.println("An Exception Occurred: " + e);
                }
            }

            // Republish the APIs
            if(APIList != null){

                for(API API : APIList) {
                    if(API.getStatus().equals("PUBLISHED")){
                        APIStateChange(AccessToken, API.getId(), API.getName() , "Demote%20to%20Created");
                        try {
                            Thread.sleep(500); // sleep/stop a thread for 1 second
                        } catch(InterruptedException e) {
                            System.out.println("An Exception Occurred: " + e);
                        }
                        APIStateChange(AccessToken, API.getId(), API.getName() , "Publish");
                    }
                }

            }

        }

        System.out.println("==============================================");
        System.out.println("============ Java Client Completed ===========");
        System.out.println("==============================================");

    }

    public static void APIStateChange(String AccessToken, String Id, String Name , String State) throws IOException {

        URL obj = new URL(REPUBLISH_ENDPOINT +"?apiId="+Id+"&action="+State );
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Authorization", "Bearer "+AccessToken);
        httpURLConnection.setDoOutput(true);

        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            if(State.equals("Demote%20to%20Created")){
                System.out.println("============ API : "+Name+"("+Id+") : Successfully Changed the State to Created State ============");
            }else {
                System.out.println("============ API : "+Name+"("+Id+") : Successfully Changed the State to "+State+" State ============");
            }

        } else {
            System.out.println(responseCode + "Error Changing API :"+Name+" : State ....");
        }
    }

    public static API GetTenantAPI(String AccessToken, String Name, String Version) throws IOException {
        System.out.println("============ Retrieving "+ Name+"-"+Version +": API Details ============");

        API API = new API();
        URL obj = new URL(API_LIST+"?query=name:"+Name+"%20version:"+Version);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Authorization", "Bearer "+AccessToken);

        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("API_DETAILS Call Response Code :" + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            System.out.println("API_DETAILS Endpoint Response: " + response.toString());
            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();

            API.setName(jsonObject.get("list").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
            API.setId(jsonObject.get("list").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString());
            API.setStatus(jsonObject.get("list").getAsJsonArray().get(0).getAsJsonObject().get("status").getAsString());

        } else {
            System.out.println("Error connecting to API_DETAILS Endpoint ....");
        }

        System.out.println("============ API Details Received ============");
        return API;
    }

    public static API[] GetListOfTenantAPIs(String AccessToken) throws IOException {
        System.out.println("============ Retrieving List of APIs ============");

        API[] APIList =null;

        URL obj = new URL(API_LIST);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Authorization", "Bearer "+AccessToken);

        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("API_LIST Call Response Code :" + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            System.out.println("API_LIST Endpoint Response: " + response.toString());
            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
            Gson gson = new Gson();
            APIList = gson.fromJson(jsonObject.get("list").toString(), API[].class);
        } else {
            System.out.println("Error connecting to API_LIST Endpoint ....");
        }

        System.out.println("============ API List Received ============");

        return APIList;
    }

    public static String GettingAccessToken(String Username, String Password, String ClientCredentials) throws IOException {

        System.out.println("============ Generating Access Token ============");

        String access_token =null;

        URL obj = new URL(TOKEN_ENDPOINT );
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Authorization", "Basic "+ClientCredentials);
        httpURLConnection.setDoOutput(true);
        OutputStream os = httpURLConnection.getOutputStream();
        os.write(("grant_type=password&username="+Username+"&password="+Password+"&scope=apim:api_view apim:api_publish").getBytes());
        os.flush();
        os.close();

        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("Token Call Response Code :" + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            System.out.println("Token Endpoint Response: " + response.toString());
            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
            access_token = jsonObject.get("access_token").getAsString();
            System.out.println("Access Token : " + access_token);

        } else {
            System.out.println("Error connecting to Token Endpoint ....");
        }

        System.out.println("============ Access Token Generated ============");
        return access_token;
    }

    public static String ClientRegistration(String Username, String Password, String AppName) throws IOException {

        System.out.println("============ Starting Client Registration ============");

        String CLIENT_ID = null;
        String CLIENT_SECRET = null;
        String ENCODED_VALUE = null;

        String encodedCredentials = Base64.getEncoder().encodeToString((Username +":"+ Password).getBytes());
        System.out.println( "Base64 Encoded Credentials: " + encodedCredentials);
        String jsonInputString = "{\"callbackUrl\": \"www.google.lk\", \"clientName\": \""+ AppName +"\", \"owner\": \""+ Username + "\", \"grantType\": \"password refresh_token\", \"saasApp\": true}";

        URL obj = new URL(DCR_ENDPOINT);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("Authorization", "Basic "+encodedCredentials);
        httpURLConnection.setDoOutput(true);

        try(OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("Client Registration Call Response Code :" + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in .readLine()) != null) {
                response.append(inputLine);
            } in .close();

            System.out.println("DCR Endpoint Response: " + response.toString());
            JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
            CLIENT_ID = jsonObject.get("clientId").getAsString();
            CLIENT_SECRET = jsonObject.get("clientSecret").getAsString();
            ENCODED_VALUE= Base64.getEncoder().encodeToString((CLIENT_ID +":"+ CLIENT_SECRET).getBytes());
            System.out.println("Client ID : " +CLIENT_ID );
            System.out.println("Client Secret : " +CLIENT_SECRET );
            System.out.println("Client Credentials : " +ENCODED_VALUE );
        } else {
            System.out.println("Error connecting to DCR Endpoint ....");
        }

        System.out.println("============ Client Registration Completed ============");
        return ENCODED_VALUE;
    }

    static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
    } };
}
