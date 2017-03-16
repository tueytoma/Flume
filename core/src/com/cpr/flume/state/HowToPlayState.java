package com.cpr.flume.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cpr.flume.Flume;

/**
 * Created by TEST on 2/16/2017.
 */

public class HowToPlayState extends State {

    private Texture p1;
    private Texture p2;
    private Texture p3;
    private Texture p4;
    private Stage stage;
    private int page;


    public HowToPlayState(final GameStateManager gsm) {
        super(gsm);

        /**Generate Viewport & Stage**/
        Gdx.input.setCatchBackKey(true);
        Viewport viewport = new StretchViewport(Flume.WIDTH, Flume.HEIGHT, cam);
        viewport.apply();
        stage = new Stage();
        stage.setViewport(viewport);
        setViewport();
        Gdx.input.setInputProcessor(stage);

        /**Generate Texture**/
        p1 = new Texture("howToPlay/p1.png");
        p2 = new Texture("howToPlay/p2.png");
        p3 = new Texture("howToPlay/p3.png");
        p4 = new Texture("howToPlay/p4.png");

        /**Set Default Param**/
        page = 0;
    }

    public void setViewport() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            gsm.set(new MenuState(gsm));
            dispose();
        } else if (Gdx.input.justTouched()) {
            if (page == 3) {
                gsm.set(new MenuState(gsm));
            }
            page++;
        }
    }

    @Override
    public void update(float dt) {
        mouse.x = Gdx.input.getX();
        mouse.y = Gdx.input.getY();
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        if (page == 0) {
            sb.draw(p1, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        } else if (page == 1) {
            sb.draw(p2, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        } else if (page == 2) {
            sb.draw(p3, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        } else {
            sb.draw(p4, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        }
        sb.end();
        stage.draw();
        stage.act();
    }

    @Override
    public void dispose() {
        p1.dispose();
        p2.dispose();
        p3.dispose();
        p4.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

}
