package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Field implements Picture {

    Texture white_texture;
    Texture black_texture;

    final int numTilesX = 15;
    final int numTilesY = 15;

    float posY = Gdx.graphics.getHeight() * 0.25f;
    float posX = Gdx.graphics.getWidth() / 2f - Gdx.graphics.getHeight() * 0.25f;

    float tile_sizeX = (Gdx.graphics.getHeight() * 0.5f) / numTilesX;
    float tile_sizeY = (Gdx.graphics.getHeight() * 0.5f) / numTilesY;

    public Field() {
        white_texture = new Texture(Gdx.files.internal("white_square.jpg"));
        black_texture = new Texture(Gdx.files.internal("black_tile.jpg"));
    }

    @Override
    public void drawPicture() {

        boolean isBlackTexture;

        for(int y = 0; y < numTilesY; y++) {
            if (y % 2 == 0) {
                isBlackTexture = true;
            }
            else {
                isBlackTexture = false;
            }
            for (int x = 0; x < numTilesX; x++) {

                TextureRegion region;

                if (x > 0) {
                    isBlackTexture = !isBlackTexture;
                }
                if (isBlackTexture){
                    region = new TextureRegion(black_texture);
                }
                else {
                    region = new TextureRegion(white_texture);
                }
                Image actor = new Image(region);
                actor.setPosition(posX + x * tile_sizeX, posY + y * tile_sizeY);
                actor.setSize(tile_sizeX, tile_sizeY);
                MyGdxGame.stage.addActor(actor);
            }
        }
    }
}
