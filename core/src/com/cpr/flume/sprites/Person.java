package com.cpr.flume.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.cpr.flume.state.PlayState;

/**
 * Created by TEST on 2/17/2017.
 */

public class Person {

    private float GRAVITY = -0.1f;
    private float MAX_VELOCITY = 2f;
    private float updateVelocity = 1f;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation personAnimation;
    private Texture texture;

    public Person(int x, int y, String fileName, int frameCount, float cycleTime, float gravity) {
        System.out.println(Gdx.graphics.getDensity());
        if(Gdx.graphics.getDensity() == 1.5){
            GRAVITY = -0.15f;
            MAX_VELOCITY = 3f;
            updateVelocity = 1.5f;
        }
        GRAVITY += gravity;
        updateVelocity += gravity;
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture(fileName);
        personAnimation = new Animation(new TextureRegion(texture), frameCount, cycleTime);
        bounds = new Rectangle(x, y, texture.getWidth() / (frameCount), texture.getHeight());
    }

    public void update() {
        personAnimation.update();
        if (position.x > 0) {
            if (velocity.x >= -MAX_VELOCITY - 5f)
                velocity.add(GRAVITY, 0, 0);
        }
        position.add(velocity.x, 0, 0);
        if (position.x < 0) {
            position.x = 0;
        }

        bounds.setPosition(position.x, position.y);
        //System.out.println(position.x);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return personAnimation.getFrame();
    }

    public void walk() {
        if (velocity.x <= MAX_VELOCITY)
            velocity.x += updateVelocity;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }

    public Vector3 getSpeed() {
        return velocity;
    }

    public int getFrame(){
        return personAnimation.getFrameNumber();
    }
}