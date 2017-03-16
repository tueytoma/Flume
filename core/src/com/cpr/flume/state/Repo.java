package com.cpr.flume.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
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

public class Repo extends State {

    private Texture background;
    private Texture wave;
    private Texture playBtn;
    private Texture highScoreBtn;
    private Texture soundBtn;
    private Texture soundMuteBtn;
    private Texture howToPlayBtn;
    private Texture questionBtn;
    private Texture infoBtn;
    private Texture flume;
    private Texture title;
    private Rectangle playBtnBound;
    private Rectangle highScoreBtnBound;
    private Rectangle soundBtnBound;
    private Rectangle howToPlayBtnBound;
    private Rectangle questionBtnBound;
    private Rectangle infoBtnBound;
    private Vector2 wavePos1;
    private Vector2 wavePos2;
    private Music music;
    private boolean isMute;
    private TextField textField;
    private StretchViewport svp;
    private final int SCREEN_LIMIT = 200;
    private Stage stage;

    public Repo(final GameStateManager gsm) {
        super(gsm);
        Gdx.input.setCatchBackKey(false);
        //cam.setToOrtho(false, Flume.WIDTH / 2, Flume.HEIGHT / 2);
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        Viewport viewport = new StretchViewport(Flume.WIDTH, Flume.HEIGHT, cam);
        viewport.apply();

        background = new Texture("badlogic.jpg");
        playBtn = new Texture("playBtn.png");
        highScoreBtn = new Texture("highScoreBtn.png");
        soundBtn = new Texture("soundBtn.png");
        soundMuteBtn = new Texture("soundMuteBtn.png");
        howToPlayBtn = new Texture("howToPlayBtn.png");
        questionBtn = new Texture("questionBtn.png");
        infoBtn = new Texture("infoBtn.png");
        flume = new Texture("flume.png");
        title = new Texture("appTitle.png");
        wave = new Texture("wave.png");

        isMute = false;
        music = Gdx.audio.newMusic(Gdx.files.internal("a.mp3"));
        music.setLooping(true);
        //music.play();


        stage = new Stage();
        stage.setViewport(viewport);
        System.out.println(stage.getViewport().getCamera().viewportWidth + " " +
                stage.getViewport().getCamera().viewportHeight);

        //stage.getViewport().getCamera().position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        setViewport();
        Gdx.input.setInputProcessor(stage);

        System.out.println(Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());
        System.out.println(stage.getWidth() + " " + stage.getHeight());
        System.out.println(cam.viewportWidth + " " + cam.viewportHeight);


        ImageButton playIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(playBtn)),
                new TextureRegionDrawable(new TextureRegion(playBtn)));
        if (Gdx.graphics.getWidth() < SCREEN_LIMIT) {
            playIB.getImage().setScale(0.75f, 0.75f);
            playIB.setPosition( - playBtn.getWidth() * 0.75f / 2,
                    - playBtn.getHeight() * 0.75f / 2);
        } else {
            playIB.setPosition( - playBtn.getWidth() / 2,
                    - playBtn.getHeight() / 2);
        }
        //System.out.println(svp.getCamera().projection.)
        playIB.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                gsm.set(new PlayState(gsm));
                dispose();
            }
        });

        ImageButton highScoreIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(highScoreBtn)),
                new TextureRegionDrawable(new TextureRegion(highScoreBtn)));
        if (Gdx.graphics.getWidth() < SCREEN_LIMIT) {
            highScoreIB.getImage().setScale(0.75f, 0.75f);
            highScoreIB.setPosition(-Flume.WIDTH * (18f / 100f) - highScoreBtn.getWidth() * 0.75f / 2,
                    - highScoreBtn.getHeight() * 0.75f / 2);
        } else {
            highScoreIB.setPosition(-Flume.WIDTH * (18f / 100f) - highScoreBtn.getWidth() / 2,
                   - highScoreBtn.getHeight() / 2);
        }

        highScoreIB.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                System.out.println("tapped");
            }
        });

        ImageButton soundIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(soundBtn)),
                new TextureRegionDrawable(new TextureRegion(soundBtn)));
        if (Gdx.graphics.getWidth() < SCREEN_LIMIT) {
            soundIB.getImage().setScale(0.75f, 0.75f);
            soundIB.setPosition(Flume.WIDTH * (18f / 100f) - soundBtn.getWidth() * 0.75f / 2,
                     - soundBtn.getHeight() / 2);
        } else {
            soundIB.setPosition(Flume.WIDTH * (18f / 100f) - soundBtn.getWidth() * 0.75f / 2,
                    - soundBtn.getHeight() / 2);
        }
        soundIB.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                System.out.println("tapped");
            }
        });

        ImageButton howToPlayIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(howToPlayBtn)),
                new TextureRegionDrawable(new TextureRegion(howToPlayBtn)));
        if (Gdx.graphics.getWidth() < SCREEN_LIMIT) {
            howToPlayIB.getImage().setScale(0.6f,0.6f);
            howToPlayIB.setPosition(Flume.WIDTH * (18f / 100f),
                    -Flume.HEIGHT * (40f / 100f) - howToPlayBtn.getHeight()*0.6f / 2);
        } else{
            howToPlayIB.setPosition(Flume.WIDTH* (18f / 100f),
                    -Flume.HEIGHT * (40f / 100f) - howToPlayBtn.getHeight() / 2);
        }
        howToPlayIB.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                System.out.println("tapped");
            }
        });

        ImageButton questionIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("questionBtn.png")))),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("questionBtn.png")))));
        if (Gdx.graphics.getWidth() < SCREEN_LIMIT) {
            questionIB.getImage().setScale(0.75f,0.75f);
            questionIB.setPosition(Flume.WIDTH * (14f / 100f) - questionBtn.getWidth()*0.75f / 2,
                    Flume.HEIGHT * (42f / 100f) - questionBtn.getHeight() / 2);
        }else{
            questionIB.setPosition(Flume.WIDTH * (14f / 100f) - questionBtn.getWidth() / 2,
                    Flume.HEIGHT* (42f / 100f) - questionBtn.getHeight() / 2);
        }
        questionIB.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String text) {

                    }

                    @Override
                    public void canceled() {

                    }
                }, "Congratulations", "", "Your Name");
            }
        });

        ImageButton infoIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("infoBtn.png")))),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("infoBtn.png")))));
        if (Gdx.graphics.getWidth() < SCREEN_LIMIT) {
            infoIB.getImage().setScale(0.75f,0.75f);
            infoIB.setPosition(Flume.WIDTH * (14f / 100f) - infoBtn.getWidth()*0.75f / 2,
                    -Flume.HEIGHT * (40f / 100f) - infoBtn.getHeight()*0.75f / 2);
        }else{
            infoIB.setPosition(Flume.WIDTH * (14f / 100f) - infoBtn.getWidth() / 2,
                    -Flume.HEIGHT * (40f / 100f) - infoBtn.getHeight() / 2);
        }
        infoIB.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                gsm.set(new AboutState(gsm));
                dispose();
            }
        });


        stage.addActor(playIB);
        stage.addActor(highScoreIB);
        stage.addActor(soundIB);
        stage.addActor(howToPlayIB);
        stage.addActor(questionIB);
        stage.addActor(infoIB);

        wavePos1 = new Vector2(0, -Flume.HEIGHT / 2);
        wavePos2 = new Vector2(-Flume.WIDTH, -Flume.HEIGHT / 2);
    }

    public void setViewport() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    public void updateWave(float dt) {

        if (wavePos1.x >= Flume.WIDTH / 2) {
            wavePos1.x = -3 * Flume.WIDTH / 2;
        }
        if (wavePos2.x >= Flume.WIDTH / 2) {
            wavePos2.x = -3 * Flume.WIDTH / 2;
        }

        wavePos1.scl(dt);
        wavePos2.scl(dt);
        wavePos1.add(0.05f, 0);
        wavePos2.add(0.05f, 0);
        wavePos1.scl(1 / dt);
        wavePos2.scl(1 / dt);
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
        mouse.x = Gdx.input.getX();
        mouse.y = Gdx.input.getY();
        updateWave(dt);
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(wave, wavePos1.x, wavePos1.y, Flume.WIDTH + Flume.WIDTH * 0.035f, Flume.HEIGHT * (30f / 100f));
        sb.draw(wave, wavePos2.x, wavePos2.y, Flume.WIDTH + Flume.WIDTH * 0.035f, Flume.HEIGHT * (30f / 100f));
        sb.draw(title, -(title.getWidth() / 2),
                Flume.HEIGHT * (30f / 100f) - title.getHeight() / 2);
        sb.draw(flume, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        highScoreBtn.dispose();
        soundBtn.dispose();
        soundMuteBtn.dispose();
        howToPlayBtn.dispose();
        questionBtn.dispose();
        infoBtn.dispose();
        flume.dispose();
        title.dispose();
        wave.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

}
