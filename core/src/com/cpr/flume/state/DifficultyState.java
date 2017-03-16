package com.cpr.flume.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cpr.flume.Flume;
import com.cpr.flume.sprites.Human;

import java.util.Map;

/**
 * Created by TEST on 2/16/2017.
 */

public class DifficultyState extends State implements InputProcessor {

    private Texture wave;
    private Texture backPress;
    private Texture backNotPress;
    private Texture playPress;
    private Texture playNotPress;
    private Texture easySelect;
    private Texture easyNotSelect;
    private Texture mediumSelect;
    private Texture mediumNotSelect;
    private Texture hardSelect;
    private Texture hardNotSelect;
    private Texture difficultyText;
    private Texture youAreText;
    private Texture easySpeak;
    private Texture mediumSpeak;
    private Texture hardSpeak;
    private ImageButton backIB;
    private ImageButton playIB;
    private ImageButton easyIB;
    private ImageButton mediumIB;
    private ImageButton hardIB;
    private Stage stage;
    private int clothNo;
    private int eyeNo;
    private int hairNo;
    private int skinNo;
    private boolean isMan;
    private boolean isEasy;
    private boolean isMedium;
    private boolean isHard;
    private BitmapFont font;
    private String name;
    private Vector2 wavePos1;
    private Vector2 wavePos2;
    private float scrollSpeed;
    private Preferences info;
    private Human human;
    private float fontOffset;
    private InputMultiplexer inputMultiplexer;

    public DifficultyState(final GameStateManager gsm) {
        super(gsm);

        /**Generate Viewport & Stage**/
        Gdx.input.setCatchBackKey(true);
        Viewport viewport = new StretchViewport(Flume.WIDTH, Flume.HEIGHT, cam);
        viewport.apply();
        stage = new Stage();
        stage.setViewport(viewport);
        setViewport();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        info = Gdx.app.getPreferences("My Preferences");

        /**Generate Texture**/
        wave = new Texture("menu/wave.png");
        backPress = new Texture("profile/backPress.png");
        backNotPress = new Texture("profile/backNotPress.png");
        playPress = new Texture("difficulty/playPress.png");
        playNotPress = new Texture("difficulty/playNotPress.png");
        easySelect = new Texture("difficulty/easySelect.png");
        easyNotSelect = new Texture("difficulty/easyNotSelect.png");
        mediumSelect = new Texture("difficulty/mediumSelect.png");
        mediumNotSelect = new Texture("difficulty/mediumNotSelect.png");
        hardSelect = new Texture("difficulty/hardSelect.png");
        hardNotSelect = new Texture("difficulty/hardNotSelect.png");
        difficultyText = new Texture("difficulty/difficultyText.png");
        youAreText = new Texture("difficulty/youAreText.png");
        easySpeak = new Texture("difficulty/easySpeak.png");
        mediumSpeak = new Texture("difficulty/mediumSpeak.png");
        hardSpeak = new Texture("difficulty/hardSpeak.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/TH KODCHASAL BOLD.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        parameter.color = Color.BLACK;
        parameter.characters = "ABCDEFGHIJKLMNOPQURSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "!\"#$%&'()*+-*/0123456789:;<=>?@[\\]^_`{|}~ " +
                "กขฃคฅฆงจฉชซฌญฎฏฐฑฒณดตถทธนบปผฝพฟภมยรฤลฦวศษสหฬอฮฯะัาำิีึืฺุู฿เแโใไๅๆ็่้๊๋์ํ๎๏๐๑๒๓๔๕๖๗๘๙๚๛";
        font = generator.generateFont(parameter);
        generator.dispose();

        /**Human Manager**/
        human = new Human();
        human.load();
        human.manager.finishLoading();

        /**Generate Button**/
        backIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(backNotPress)),
                new TextureRegionDrawable(new TextureRegion(backPress)));
        backIB.setPosition(-Flume.WIDTH * (48f / 100f), Flume.HEIGHT * (41f / 100f));
        backIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new ProfileState(gsm));
                dispose();
            }
        });

        playIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(playNotPress)),
                new TextureRegionDrawable(new TextureRegion(playPress)));
        playIB.setPosition(Flume.WIDTH * (34f / 100f), -Flume.HEIGHT * (36.5f / 100f));
        playIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int level = 1;
                if(isEasy) level = 1;
                else if(isMedium) level = 2;
                else if(isHard) level = 3;
                info.putInteger("level",level);
                info.flush();
                gsm.set(new PlayState(gsm));
                dispose();
            }
        });

        easyIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(easyNotSelect)),
                new TextureRegionDrawable(new TextureRegion(easyNotSelect)),
                new TextureRegionDrawable(new TextureRegion(easySelect)));
        easyIB.setPosition(-Flume.WIDTH * (37f / 100f),
                Flume.HEIGHT * (15f / 100f) - easyNotSelect.getHeight());
        easyIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isEasy) {
                    isEasy = true;
                    isHard = false;
                    isMedium = false;
                }
                easyIB.setChecked(isEasy);
                mediumIB.setChecked(isMedium);
                hardIB.setChecked(isHard);
            }
        });

        mediumIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(mediumNotSelect)),
                new TextureRegionDrawable(new TextureRegion(mediumNotSelect)),
                new TextureRegionDrawable(new TextureRegion(mediumSelect)));
        mediumIB.setPosition(-Flume.WIDTH * (37f / 100f),
                -Flume.HEIGHT * (2f / 100f) - mediumNotSelect.getHeight());
        mediumIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMedium) {
                    isMedium = true;
                    isEasy = false;
                    isHard = false;
                }
                easyIB.setChecked(isEasy);
                mediumIB.setChecked(isMedium);
                hardIB.setChecked(isHard);
            }
        });

        hardIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(hardNotSelect)),
                new TextureRegionDrawable(new TextureRegion(hardNotSelect)),
                new TextureRegionDrawable(new TextureRegion(hardSelect)));
        hardIB.setPosition(-Flume.WIDTH * (37f / 100f),
                -Flume.HEIGHT * (19f / 100f) - hardNotSelect.getHeight());
        hardIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isHard) {
                    isHard = true;
                    isEasy = false;
                    isMedium = false;
                }
                easyIB.setChecked(isEasy);
                mediumIB.setChecked(isMedium);
                hardIB.setChecked(isHard);
            }
        });


        /**Set Default Param**/
        isEasy = true;
        isMedium = false;
        isHard = false;
        easyIB.setChecked(isEasy);
        wavePos1 = new Vector2(-Flume.WIDTH / 2, -Flume.HEIGHT / 2 - wave.getHeight() * (91f / 100f));
        wavePos2 = new Vector2(Flume.WIDTH/2, -Flume.HEIGHT / 2 - wave.getHeight() * (91f / 100f));
        isMan = info.getBoolean("isMan", true);
        clothNo = info.getInteger("clothNo", 0);
        eyeNo = info.getInteger("eyeNo", 0);
        hairNo = info.getInteger("hairNo", 0);
        skinNo = info.getInteger("skinNo", 0);
        name = info.getString("name", "Anonymous");

        if (Gdx.graphics.getDensity() == 1.5) {
            scrollSpeed = 3f;
        } else {
            scrollSpeed = 2f;
        }

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "basetest");
        fontOffset = glyphLayout.height;

        /**Add to Stage**/
        stage.addActor(backIB);
        stage.addActor(playIB);
        stage.addActor(easyIB);
        stage.addActor(mediumIB);
        stage.addActor(hardIB);
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
        sb.draw(difficultyText, -Flume.WIDTH * (41.5f / 100f),
                Flume.HEIGHT * (20f / 100f) - difficultyText.getHeight() / 2);
        sb.draw(youAreText, -Flume.WIDTH * (41.5f / 100f),
                Flume.HEIGHT * (30f / 100f) - youAreText.getHeight() / 2);
        font.draw(sb, name, -Flume.WIDTH * (25.5f / 100f), Flume.HEIGHT * (30f / 100f) + fontOffset - difficultyText.getHeight() / 2);
        sb.draw(human.manager.get(human.getBody().get(skinNo)),
                Flume.WIDTH * (22f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
        sb.draw(human.manager.get(human.getEye().get(eyeNo)),
                Flume.WIDTH * (22f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
        if (isMan) {
            sb.draw(human.manager.get(human.getManCloth().get(clothNo)),
                    Flume.WIDTH * (22f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                    -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
            sb.draw(human.manager.get(human.getManHair().get(hairNo)),
                    Flume.WIDTH * (22f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                    -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
        } else {
            sb.draw(human.manager.get(human.getWomanCloth().get(clothNo)),
                    Flume.WIDTH * (22f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                    -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
            sb.draw(human.manager.get(human.getWomanHair().get(hairNo)),
                    Flume.WIDTH * (22f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                    -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
        } if(isEasy){
            sb.draw(easySpeak, Flume.WIDTH * (32f / 100f) - easySpeak.getWidth() / 2,
                    -easySpeak.getHeight() + Flume.HEIGHT * (44f / 100f));
        } else if(isMedium){
            sb.draw(mediumSpeak, Flume.WIDTH * (32f / 100f) - mediumSpeak.getWidth() / 2,
                    -mediumSpeak.getHeight() + Flume.HEIGHT * (44f / 100f));
        }else{
            sb.draw(hardSpeak, Flume.WIDTH * (32f / 100f) - hardSpeak.getWidth() / 2,
                    -hardSpeak.getHeight() + Flume.HEIGHT * (44f / 100f));
        }
        sb.draw(wave, wavePos1.x, wavePos1.y, Flume.WIDTH + Flume.WIDTH * 0.035f, wave.getHeight());
        sb.draw(wave, wavePos2.x, wavePos2.y, Flume.WIDTH + Flume.WIDTH * 0.035f, wave.getHeight());
        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        wave.dispose();
        backPress.dispose();
        backNotPress.dispose();
        playPress.dispose();
        playNotPress.dispose();
        easySelect.dispose();
        easyNotSelect.dispose();
        mediumSelect.dispose();
        mediumNotSelect.dispose();
        hardSelect.dispose();
        hardNotSelect.dispose();
        difficultyText.dispose();
        youAreText.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }


    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            gsm.set(new ProfileState(gsm));
            dispose();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
