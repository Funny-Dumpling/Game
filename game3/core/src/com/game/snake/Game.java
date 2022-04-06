package com.game.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends com.badlogic.gdx.Game {
    SpriteBatch batch;
    Texture img;
    Character characterLeft;
    Character characterRight;
    private Music music;

    ShapeRenderer shapeRenderer;
    float stateTime;
    float time;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //музыка фона
        music = Gdx.audio.newMusic(Gdx.files.internal("fon.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
       //конец
        characterLeft = new Character("FireKnight", 50, 30);
        characterLeft.setControlButtons(
                Input.Keys.A,
                Input.Keys.D,
                Input.Keys.CONTROL_LEFT
        );
        characterLeft.runRightAnimation = Controller.createAnimation("FireKnight/run_right_atlas.png", 2, 4);
        characterLeft.runLeftAnimation = Controller.createAnimation("FireKnight/run_left_atlas.png", 2, 4);
        characterLeft.idleAnimation = Controller.createAnimation("FireKnight/idle_atlas.png", 2, 4);
        characterLeft.attackAnimation = Controller.createAnimation("FireKnight/attack_atlas.png", 2, 5);

        characterRight = new Character("WaterPrincess", 200, 30);
        characterRight.setControlButtons(
                Input.Keys.LEFT,
                Input.Keys.RIGHT,
                Input.Keys.CONTROL_RIGHT
        );
        characterRight.runLeftAnimation = Controller.createAnimation("WaterPrincess/run_left_atlas.png", 2, 3);
        characterRight.runRightAnimation = Controller.createAnimation("WaterPrincess/run_right_atlas.png", 2, 3);
        characterRight.idleAnimation = Controller.createAnimation("WaterPrincess/idle_atlas.png", 2, 4);
        characterRight.attackAnimation = Controller.createAnimation("WaterPrincess/attack_atlas.png", 2, 3);

        stateTime = 0f;
        time = 0f;

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.RED);
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (characterLeft.health <=0){
            BitmapFont font = new BitmapFont();
            font.setColor(Color.RED);
            String str = characterRight.name + " победил";
            batch.begin();

            batch.end();  font.draw(batch, str, 180, 180);
        } else if (characterRight.health <=0){
            BitmapFont font = new BitmapFont();
            font.setColor(Color.RED);
            batch.begin();
            font.draw(batch, (characterLeft.name + " победил"), 180, 180);
            batch.end();
        } else {
            stateTime += Gdx.graphics.getDeltaTime();

            int marginTop = Gdx.graphics.getHeight() - 10;
            Controller.drawHP(5, marginTop, characterLeft.health);
            int offsetX = Gdx.graphics.getWidth() - (int)(characterRight.health * 2.5) - 5;
            Controller.drawHP(offsetX, marginTop, characterRight.health);
            //------------------------------ ИГРОК СЛЕВА ---------------------------------
            Controller.setControl(batch, characterLeft, stateTime);
            if (characterLeft.personRectangle.x < 0) {
                characterLeft.personRectangle.x = 0;
            }
            if (characterLeft.personRectangle.x > characterRight.personRectangle.x - 40) {
                characterLeft.personRectangle.x -= 10;
            }

            //-------------------- ----- ИГРОК СПРАВА ---------------------------------
            Controller.setControl(batch, characterRight, stateTime);

            if (characterRight.personRectangle.x > Gdx.graphics.getWidth() - 150) {
                characterRight.personRectangle.x -= 200 * Gdx.graphics.getDeltaTime();
            }
            if (characterRight.personRectangle.x < characterLeft.personRectangle.x + 40) {
                characterRight.personRectangle.x += 10;
            }

            float distance = characterRight.personRectangle.x - characterLeft.personRectangle.x;
            if (Gdx.input.isKeyPressed(characterLeft.buttonAttack) && distance < 110){
                time += Gdx.graphics.getDeltaTime();
                if (time > 0.5f){
                    time = 0;
                    characterRight.health -= characterLeft.damage;
                }
            }
            if (Gdx.input.isKeyPressed(characterRight.buttonAttack) && distance < 80){
                time += Gdx.graphics.getDeltaTime();
                if (time > 0.5f){
                    time = 0;
                    characterLeft.health -= characterRight.damage;
                }
            }
            if (!Gdx.input.isKeyPressed(characterLeft.buttonAttack) && !Gdx.input.isKeyPressed(characterRight.buttonAttack)){
                time = 0;
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();

    }

}
