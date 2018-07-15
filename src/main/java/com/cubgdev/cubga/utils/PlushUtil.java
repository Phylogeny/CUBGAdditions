package com.cubgdev.cubga.utils;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

public class PlushUtil
{
    private static final Map<String, String> USERNAME_CACHE = Maps.<String, String>newHashMap();

    /**
     * Gets the username of a player based on the uuid given.
     *
     * @param uuid
     *            The uuid of the player.
     * @return The name of the player if the uuid is valid
     */
    public static String getPlayerName(String uuid) {
        String name = USERNAME_CACHE.get(uuid);
        if (name == null) {
            try {
                URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                JsonParser parser = new JsonParser();
                JsonElement obj = parser.parse(sb.toString().trim());
                name = obj.getAsJsonObject().get("name").getAsString();
                USERNAME_CACHE.put(uuid, name);
                reader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                name = "Invalid UUID";
            }
        }
        return name;
    }
}
