package Utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.seedim.mayaHub.MayaHub;
import org.bukkit.Bukkit;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UpdateManager {

    public static void checkUpdates() {

        // Try to open connection to the Modrinth API
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.modrinth.com/v2/project/Q77rwCjT/version"))
                    .timeout(java.time.Duration.ofSeconds(10))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response
            JsonArray jsonResponse = JsonParser.parseString(response.body()).getAsJsonArray();

            if (!jsonResponse.isEmpty()) {
                // Get the latest version from the first object in the array
                JsonObject latestVersionObj = jsonResponse.get(0).getAsJsonObject();
                String latestVersion = latestVersionObj.get("version_number").getAsString();

                // Compare with the current plugin version
                if (!MayaHub.getInstance().getPluginMeta().getVersion().equals(latestVersion)) {
                    Bukkit.getLogger().info("A new version of MAYA: Hub is available: " + latestVersion + ".");

                    // Try to get the first download URL
                    JsonArray filesArray = (latestVersionObj.getAsJsonArray("files"));

                    // Get the download URL
                    String downloadURL = filesArray.get(0).getAsJsonObject().get("url").getAsString();

                    if (downloadURL != null) {
                        Bukkit.getLogger().info("You can download it here: " + downloadURL);
                    } else {
                        Bukkit.getLogger().info("No download URL or files available for the latest version.");
                        Bukkit.getLogger().info("Please, check manually for updates.");
                    }

                } else {
                    Bukkit.getLogger().info("You are using the latest version of MAYA: Hub.");
                }

            } else {
                Bukkit.getLogger().info("No version data found for MAYA: Hub on Modrinth.");
                Bukkit.getLogger().info("Please, check manually for updates.");
            }

        } catch (Exception e) {
            Bukkit.getLogger().warning("Could not check for update: " + e.getMessage());
        }
    }
}
