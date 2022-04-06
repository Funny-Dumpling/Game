package com.game.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class End implements Screen {
    Character winner;
    Game game;

    SpriteBatch batch;
    BitmapFont font;
    public End(Game game, Character winner){
        this.winner = winner;
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
    }


    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
//Очищение экрана
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //очищение экрана

        //Отрисовка текстур
        game.batch.begin();
        font.draw(game.batch, winner.name + " победил!", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        game.batch.end();
//        batch.draw(batch, winner.name + "победил", 0, 0);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
