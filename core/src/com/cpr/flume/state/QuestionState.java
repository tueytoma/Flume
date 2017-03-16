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

public class QuestionState extends State {

    private Texture q1;
    private Texture q2;
    private Texture q3;
    private Stage stage;
    private int page;


    public QuestionState(final GameStateManager gsm) {
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
        q1 = new Texture("question/q1.png");
        q2 = new Texture("question/q2.png");
        q3 = new Texture("question/q3.png");

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
            if (page == 2) {
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
            sb.draw(q1, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        } else if (page == 1) {
            sb.draw(q2, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        } else {
            sb.draw(q3, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        }

        sb.end();
        stage.draw();
        stage.act();
    }

    @Override
    public void dispose() {
        q1.dispose();
        q2.dispose();
        q3.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

}
