package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.StreamUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pokemon extends Actor {

    Sprite sprite;
    int ID;

    public Pokemon(int ID) {
        this.ID = ID;

        try {
            sprite = new Sprite(getImage((getRandomPokemon(ID))));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setWidth(sprite.getWidth());
        setHeight(sprite.getHeight());
        setBounds(0, 0, getWidth(), getHeight());
        setX(0);
        setY(0);

        System.out.println(sprite.getWidth());
    }

    String getRandomPokemon(int ID) throws Exception {
        String url = "https://pokeapi.co/api/v2/pokemon/" + ID + "/";
        JsonReader jsonReader = new JsonReader();
        JsonValue data = jsonReader.parse(getHTML(url));
        String spriteURL = data.get("sprites").getString("front_default");
        System.out.println(spriteURL);
        return spriteURL;
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

    private int download (byte[] out, String url) {
        InputStream in = null;
        try {
            HttpURLConnection conn = null;
            conn = (HttpURLConnection)new URL(url).openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setUseCaches(true);
            conn.connect();
            in = conn.getInputStream();
            int readBytes = 0;
            while (true) {
                int length = in.read(out, readBytes, out.length - readBytes);
                if (length == -1) break;
                readBytes += length;
            }
            return readBytes;
        } catch (Exception ex) {
            return 0;
        } finally {
            StreamUtils.closeQuietly(in);
        }
    }

    Texture getImage(String url) {

        Texture texture = null;

        byte[] bytes = new byte[200 * 1024]; // assuming the content is not bigger than 200kb.
        int numBytes = 0;
        try {
            numBytes = download(bytes, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (numBytes != 0) {
            // load the pixmap, make it a power of two if necessary (not needed for GL ES 2.0!)
            Pixmap pixmap = new Pixmap(bytes, 0, numBytes);
            final int originalWidth = pixmap.getWidth();
            final int originalHeight = pixmap.getHeight();
            int width = MathUtils.nextPowerOfTwo(pixmap.getWidth());
            int height = MathUtils.nextPowerOfTwo(pixmap.getHeight());
            final Pixmap potPixmap = new Pixmap(width, height, pixmap.getFormat());
            potPixmap.setBlending(Pixmap.Blending.None);
            potPixmap.drawPixmap(pixmap, 0, 0, 0, 0, pixmap.getWidth(), pixmap.getHeight());
            pixmap.dispose();
            texture = new Texture(potPixmap);
        }

        return texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(sprite, getX(), getY());
    }
}
