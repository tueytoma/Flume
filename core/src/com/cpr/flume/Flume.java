package com.cpr.flume;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.cpr.flume.state.GameStateManager;
import com.cpr.flume.state.MenuState;
import com.cpr.flume.state.PlayState;

public class Flume extends ApplicationAdapter implements ApplicationListener {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Flume Application";

    private SpriteBatch batch;
    private GameStateManager gsm;
    public static Music music;
    public static Music gameMusic;
    public static boolean isMute;

    private Preferences info;


    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager();

        /** Initialize Default Preferences **/
        info = Gdx.app.getPreferences("My Preferences");
        info.putBoolean("isMan", true);
        info.putInteger("clothNo", 0);
        info.putInteger("eyeNo", 0);
        info.putInteger("hairNo", 0);
        info.putInteger("skinNo", 0);
        info.putString("name","");
        info.putInteger("level",1);
        if(!info.contains("hsDefault")) {
            info.putBoolean("hsDefault", true);
            info.putString("5", "30 Stepboom");
            info.putString("4", "25 Tueytoma");
            info.putString("3", "20 Destroyer");
            info.putString("2", "15 Punisher");
            info.putString("1", "10 Echolax");
        }
        info.flush();

        isMute = false;
        music = Gdx.audio.newMusic(Gdx.files.internal("bg.mp3"));
        music.setLooping(true);
        music.play();

        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("game.mp3"));
        gameMusic.setLooping(true);

        Gdx.gl.glClearColor(236f/255f, 253f/255f, 255f/255f,1);
        gsm.push(new MenuState(gsm));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void resume(){
        gsm.resume();
    }

    @Override
    public void pause(){
        gsm.pause();
    }

}
