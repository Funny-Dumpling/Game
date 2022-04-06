package com.game.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Controller {
    public static void draw(SpriteBatch batch, Animation animation, Character character, float time){
        //Определяет прямоугольную область текстуры. Используемая система координат имеет начало координат в верхнем левом углу,
        //ось x направлена вправо, а ось y направлена вниз.
        TextureRegion currentFrame = (TextureRegion) animation.getKeyFrame(time, true);//Возвращает кадр, основанный на так называемом времени состояния. Это количество секунд, которое объект провел в состоянии
        // , которое представляет этот экземпляр анимации, например, бег, прыжки и так далее. Режим определяет, является ли анимация
        //циклической или нет. looping - является ли анимация зацикленной или нет.
        batch.begin();
        batch.draw(
                currentFrame,//Текущий кадр
                character.personRectangle.x,
                character.personRectangle.y,
                0, 0,
                300, 250,
                1f, 1f, //масштаб,
                0
        );
        batch.end();
    }

    public static void drawHP(int x, int y, float hp){
        ShapeRenderer shapeRenderer = new ShapeRenderer(); //Визуализирует точки, линии, контуры фигур и заполненные фигуры.
        //По умолчанию используется двумерная ортогональная проекция с началом координат в левом нижнем углу, а единицы измерения указаны
        //в пикселях экрана.
        shapeRenderer.setAutoShapeType(true); //метод для маскировки //Если значение true, то при невозможности выполнения рисования фигуры с текущим типом фигуры пакет сбрасывается и
        //тип фигуры изменяется автоматически.
        shapeRenderer.setColor(Color.RED); //изменение цвета

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); //Запускает новую партию фигур. Фигуры, нарисованные в пакете, будут пытаться использовать указанный тип. Вызов
        //этого метода должен быть сопряжен с вызовом end()
        shapeRenderer.rect(x, y, hp * 2.5f, 8);//Рисует прямоугольник в плоскости x/y с помощью ShapeRenderer. Шейп-тип. Линия или
        //ШейпеРендерер.Шейп-тип.Заполненный.
        shapeRenderer.end();
    }

    public static Animation createAnimation(String path, int cols, int rows){
        int frames = cols*rows;
        //Класс Texture получает изображение из файла, и загружает его в GPU и уже оттуда оно - текстура
        Texture walkSheet = new Texture(Gdx.files.internal(path)); // Создает обычное двумерное изображение из animation_sheet.png файла,
        // находящегося в assets директории проекта (смотрите настройку проекта) #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/cols, walkSheet.getHeight()/rows); // #10
        //Используя TextureRegion.split() метод и текстуру, получаем двумерный массив кадров из текстуры.
        // Имейте в виду, что это работает только, если кадры имеют одинаковый размер.
        // С помощью временной переменной tmp происходит наполнение walkFrames массива.
        // Это необходимо, так как анимация работает только с одномерными массивами.
        TextureRegion[] walkFrames = new TextureRegion[frames];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        Animation animation = new Animation(1f/frames, walkFrames); // #11
        return animation;
    }

    public static void setControl(SpriteBatch batch, Character character, float time){
        //влево
        if (Gdx.input.isKeyPressed(character.buttonLeft)) {
            //анимация
            Controller.draw(batch, character.runLeftAnimation, character, time);
            //скорость
            character.personRectangle.x -= 100 * Gdx.graphics.getDeltaTime();
        }
        //вправо
        else if (Gdx.input.isKeyPressed(character.buttonRight)) {
            //анимация
            Controller.draw(batch, character.runRightAnimation, character, time);
            //скорость
            character.personRectangle.x += 100 * Gdx.graphics.getDeltaTime(); //getDeltaTime - Возвращается:
            //промежуток времени между текущим кадром и последним кадром в секундах.

        }
        else if (Gdx.input.isKeyPressed(character.buttonAttack)) {
            playMusicOnce("sound.mp3");
            Controller.draw(batch, character.attackAnimation, character, time);
        } else {
            Controller.draw(batch, character.idleAnimation, character, time);
        }
    }

    private static void playMusicOnce(String sound){
        Music udar = Gdx.audio.newMusic(Gdx.files.internal(sound));
        udar.setVolume(0.5f);
        udar.play();
    }

}
