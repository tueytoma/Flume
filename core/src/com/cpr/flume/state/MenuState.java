package com.cpr.flume.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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

public class MenuState extends State {

    private Texture wave;
    private Texture playBtn;
    private Texture playBtnPress;
    private Texture highScoreBtn;
    private Texture soundBtn;
    private Texture soundMuteBtn;
    private Texture howToPlayBtn;
    private Texture howToPlayBtnPress;
    private Texture questionBtn;
    private Texture infoBtn;
    private Texture flume;
    private Texture title;
    private Vector2 wavePos1;
    private Vector2 wavePos2;
    private Stage stage;
    private float scrollSpeed;
    private ImageButton playIB;
    private ImageButton highScoreIB;
    private ImageButton soundIB;
    private ImageButton howToPlayIB;
    private ImageButton questionIB;
    private ImageButton infoIB;

    public MenuState(final GameStateManager gsm) {
        super(gsm);

        /**Generate Viewport & Stage**/
        Gdx.input.setCatchBackKey(false);
        Viewport viewport = new StretchViewport(Flume.WIDTH, Flume.HEIGHT, cam);
        viewport.apply();
        stage = new Stage();
        stage.setViewport(viewport);
        setViewport();
        Gdx.input.setInputProcessor(stage);

        /**Generate Texture**/
        playBtn = new Texture("menu/playBtn.png");
        playBtnPress = new Texture("menu/playBtnPress.png");
        highScoreBtn = new Texture("menu/highScoreBtn.png");
        soundBtn = new Texture("menu/soundBtn.png");
        soundMuteBtn = new Texture("menu/soundMuteBtn.png");
        howToPlayBtn = new Texture("menu/howToPlayBtn.png");
        howToPlayBtnPress = new Texture("menu/howToPlayBtnPress.png");
        questionBtn = new Texture("menu/questionBtn.png");
        infoBtn = new Texture("menu/infoBtn.png");
        flume = new Texture("menu/flume.png");
        title = new Texture("menu/appTitle.png");
        wave = new Texture("menu/wave.png");

        /**Generate Button**/
        playIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(playBtn)),
                new TextureRegionDrawable(new TextureRegion(playBtnPress)));
        playIB.setPosition(-playBtn.getWidth() / 2,
                -playBtn.getHeight() / 2);
        playIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new ProfileState(gsm));
                dispose();
            }
        });

        highScoreIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(highScoreBtn)),
                new TextureRegionDrawable(new TextureRegion(highScoreBtn)));
        highScoreIB.setPosition(-Flume.WIDTH * (18f / 100f) - highScoreBtn.getWidth() / 2,
                -highScoreBtn.getHeight() / 2);
        highScoreIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new HighScoreState(gsm));
                dispose();
            }
        });

        soundIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(soundBtn)),
                new TextureRegionDrawable(new TextureRegion(soundBtn)),
                new TextureRegionDrawable(new TextureRegion(soundMuteBtn)));
        soundIB.setPosition(Flume.WIDTH * (18f / 100f) - soundBtn.getWidth() * 0.75f / 2,
                -soundBtn.getHeight() / 2);
        soundIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Flume.isMute = !Flume.isMute;
                if (Flume.isMute) {
                    Flume.music.pause();
                    soundIB.setChecked(true);
                } else {
                    Flume.music.play();
                    soundIB.setChecked(false);
                }
            }
        });

        questionIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(questionBtn)),
                new TextureRegionDrawable(new TextureRegion(questionBtn)));
        questionIB.setPosition(Flume.WIDTH * (14f / 100f) - questionBtn.getWidth() / 2,
                Flume.HEIGHT * (42f / 100f) - questionBtn.getHeight() / 2);
        questionIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new QuestionState(gsm));
                dispose();
            }
        });

        infoIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(infoBtn)),
                new TextureRegionDrawable(new TextureRegion(infoBtn)));
        infoIB.setPosition(-Flume.WIDTH * (10f / 100f) - infoBtn.getWidth() / 2,
                -Flume.HEIGHT * (40f / 100f) - infoBtn.getHeight() / 2);
        infoIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new AboutState(gsm));
                dispose();
            }
        });

        howToPlayIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(howToPlayBtn)),
                new TextureRegionDrawable(new TextureRegion(howToPlayBtnPress)));
        howToPlayIB.setPosition(-Flume.WIDTH * (45f / 100f),
                -Flume.HEIGHT * (40f / 100f) - howToPlayBtn.getHeight() / 2);
        howToPlayIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new HowToPlayState(gsm));
                dispose();
            }
        });

        /**Set Default Param**/
        soundIB.setChecked(Flume.isMute);
        wavePos1 = new Vector2(-Flume.WIDTH / 2, -Flume.HEIGHT / 2 - wave.getHeight() * (65f / 100f));
        wavePos2 = new Vector2(Flume.WIDTH / 2, -Flume.HEIGHT / 2 - wave.getHeight() * (65f / 100f));

        if (Gdx.graphics.getDensity() == 1.5) {
            scrollSpeed = 3f;
        } else {
            scrollSpeed = 2f;
        }

        /**Add to Stage**/
        stage.addActor(playIB);
        stage.addActor(highScoreIB);
        stage.addActor(soundIB);
        stage.addActor(howToPlayIB);
        stage.addActor(questionIB);
        stage.addActor(infoIB);
    }

    public void setViewport() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
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
    }

    @Override
    public void update(float dt) {
        mouse.x = Gdx.input.getX();
        mouse.y = Gdx.input.getY();
        updateWave();
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(wave, wavePos1.x, wavePos1.y, Flume.WIDTH + Flume.WIDTH * 0.035f, wave.getHeight());
        sb.draw(wave, wavePos2.x, wavePos2.y, Flume.WIDTH + Flume.WIDTH * 0.035f, wave.getHeight());
        sb.draw(title, -(title.getWidth() / 2),
                Flume.HEIGHT * (30f / 100f) - title.getHeight() / 2);
        sb.draw(flume, Flume.WIDTH / 2 - flume.getWidth(), -Flume.HEIGHT / 2);
        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        wave.dispose();
        playBtn.dispose();
        highScoreBtn.dispose();
        soundBtn.dispose();
        soundMuteBtn.dispose();
        howToPlayBtn.dispose();
        questionBtn.dispose();
        infoBtn.dispose();
        flume.dispose();
        title.dispose();
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

}
