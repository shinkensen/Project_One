package hello;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class LeetCodeDaily {
        public static String DLP() {
        String endpoint = "https://leetcode.com/graphql";
        String query = "{ \"query\": \"query questionOfToday { activeDailyCodingChallengeQuestion { question { title difficulty } } }\" }";

        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = query.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }
            }

            JSONObject json = new JSONObject(response.toString());
            JSONObject question = json
                    .getJSONObject("data")
                    .getJSONObject("activeDailyCodingChallengeQuestion")
                    .getJSONObject("question");

            String title = question.getString("title");
            String difficulty = question.getString("difficulty");
            
            return title + " [" + difficulty + "]";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching daily problem.";
        }
    }

    public static void main(String[] args) {
        String title = DLP();
        System.out.println("📘 Today's LeetCode Daily Challenge: " + title);
    }
}
