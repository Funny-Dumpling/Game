package com.game.snake;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;

public class Character {
    String name = "";
    int health = 100;
    int damage = 20;

    Rectangle personRectangle;

    Animation runRightAnimation;
    Animation runLeftAnimation;
    Animation idleAnimation;
    Animation attackAnimation;

    int buttonLeft;
    int buttonRight;
    int buttonAttack;

    Character(String name, float x, float y){
        this.name = name;

        personRectangle = new Rectangle();
        personRectangle.x = x;
        personRectangle.y = y;
    }

    public void setControlButtons(int left, int right, int attack){
        this.buttonLeft = left;
        this.buttonRight = right;
        this.buttonAttack = attack;
    }

}
