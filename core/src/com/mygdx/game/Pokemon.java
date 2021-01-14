package com.mygdx.game;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pokemon {

    public Pokemon(int ID) {
        try {
            getRandomPokemon(ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getRandomPokemon(int ID) throws Exception {
        String url = "https://pokeapi.co/api/v2/pokemon/" + ID + "/";
        JsonReader jsonReader = new JsonReader();
        JsonValue data = jsonReader.parse(getHTML(url));
        JsonValue forms = data.get("forms");
        for (JsonValue component : forms)
        {
            System.out.println(component.getString("name"));
        }
    }

    String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
