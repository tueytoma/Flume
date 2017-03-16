package com.cpr.flume.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by TEST on 2/17/2017.
 */

public class Talk {

    private float GRAVITY = -0.1f;
    private float MAX_VELOCITY = 2f;
    private float updateVelocity = 1f;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation personAnimation;
    private Texture texture;

    public Talk(int x, int y, String fileName, int frameCount, float cycleTime) {
        position = new Vector3(x, y, 0);
        texture = new Texture(fileName);
        personAnimation = new Animation(new TextureRegion(texture), frameCount, cycleTime);
        bounds = new Rectangle(x, y, texture.getWidth() / (frameCount), texture.getHeight());
    }

    public void update() {
        personAnimation.update();
        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return personAnimation.getFrame();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }

}