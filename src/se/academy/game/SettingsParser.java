package se.academy.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

/**
 * Created by mohed on 2016-08-20.
 */
public class SettingsParser implements Settings {
    private String jsonData = "";
    private BufferedReader br = null;
    private String line;
    private JSONObject settingsJson;

    public SettingsParser() throws IOException {
        br = new BufferedReader(new FileReader("settings.txt"));
        while ((line = br.readLine()) != null) {
            jsonData += line + "\n";
        }
        settingsJson = new JSONObject(jsonData);
    }

    @Override
    public String getKeySettingsForPlayer(String player, String key) {
        return settingsJson.getJSONObject("Players").getJSONObject(player).getString(key);
    }
}
