package ch.bbcag.NFController;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RequestHandler {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://nfcontroller-default-rtdb.europe-west1.firebasedatabase.app/");
    FirebaseApp firebaseApp = database.getApp();

    public void writeit() {
        FirebaseDatabase databaset = FirebaseDatabase.getInstance("https://nfcontroller-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference mDbRef = databaset.getReference("Donor/Name");
        mDbRef.setValue("Parinitha Krishna");
    }

    public void addThing(String user) throws IOException {
        String baseUrl = "https://nfcontroller-default-rtdb.europe-west1.firebasedatabase.app/";
        URL url = new URL(baseUrl + "fighters/" + user + ".json");
        HttpURLConnection con = this.initializeConnection(url, "PUT");
        con.setDoOutput(true);
        String jsonInputString = "{\n" +
                "\"wins\": 0\n" +
                "}";
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);
        }
    }



    private HttpURLConnection initializeConnection(URL url, String requestMethod) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(requestMethod);
        if (requestMethod.equals("GET")) {
            con.setRequestProperty("Content-Type", "application/json");
        } else {
            con.setRequestProperty("Accept", "application/json");
        }
        return con;
    }

}