package com.cpr.flume.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cpr.flume.Flume;

/**
 * Created by TEST on 2/16/2017.
 */

public class AboutState extends State {

    private Texture wave;
    private Texture image;
    private Vector2 wavePos1;
    private Vector2 wavePos2;
    private Vector2 imagePos;
    private Stage stage;
    private float scrollSpeedUser;
    private float scrollSpeed;
    private BitmapFont font;


    public AboutState(final GameStateManager gsm) {
        super(gsm);

        /**Generate Viewport & Stage**/
        Gdx.input.setCatchBackKey(true);
        Viewport viewport = new StretchViewport(Flume.WIDTH, Flume.HEIGHT, cam);
        viewport.apply();
        stage = new Stage();
        stage.setViewport(viewport);
        Gdx.input.setInputProcessor(stage);

        /**Generate Texture**/
        wave = new Texture("menu/wave.png");
        image = new Texture("about/aboutText.png");

        /**Set Default Param**/
        wavePos1 = new Vector2(-Flume.WIDTH / 2, -Flume.HEIGHT / 2 - wave.getHeight() * (91f / 100f));
        wavePos2 = new Vector2(Flume.WIDTH/2, -Flume.HEIGHT / 2 - wave.getHeight() * (91f / 100f));
        imagePos = new Vector2(-image.getWidth() / 2, -image.getHeight());

        if(Gdx.graphics.getDensity()==1.5){
            scrollSpeed = 3f;
            scrollSpeedUser = 2f;
        } else{
            scrollSpeed = 2f;
            scrollSpeedUser = 1f;
        }
    }

    public void updateImage() {
        if (imagePos.y >= Flume.HEIGHT / 2) {
            gsm.set(new MenuState(gsm));
            dispose();
        }
        imagePos.add(0, scrollSpeedUser);
    }

    public void updateWave() {

        if (wavePos1.x <= -Flume.WIDTH) {
            wavePos1.x = -Flume.WIDTH/2;
        }
        if (wavePos2.x <= -Flume.WIDTH/2 ) {
            wavePos2.x = 0;
        }

        wavePos1.add(-scrollSpeed, 0);
        wavePos2.add(-scrollSpeed, 0);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            gsm.set(new MenuState(gsm));
            dispose();
        } else if(Gdx.input.isTouched()){
            if(Gdx.graphics.getDensity()==1.5){
                scrollSpeedUser = 10f;
            } else{
                scrollSpeedUser = 4f;
            }
        } else {
            if(Gdx.graphics.getDensity()==1.5){
                scrollSpeedUser = 5f;
            } else{
                scrollSpeedUser = 2f;
            }
        }
    }

    @Override
    public void update(float dt) {
        mouse.x = Gdx.input.getX();
        mouse.y = Gdx.input.getY();
        updateWave();
        updateImage();
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(image, imagePos.x, imagePos.y);
        sb.draw(wave, wavePos1.x, wavePos1.y, Flume.WIDTH + Flume.WIDTH * 0.035f, wave.getHeight());
        sb.draw(wave, wavePos2.x, wavePos2.y, Flume.WIDTH + Flume.WIDTH * 0.035f, wave.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        wave.dispose();
        image.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

}
