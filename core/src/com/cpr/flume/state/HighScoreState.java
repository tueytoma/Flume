package com.cpr.flume.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cpr.flume.Flume;

/**
 * Created by TEST on 2/16/2017.
 */

public class HighScoreState extends State {

    private Texture wave;
    private Texture goldMedal;
    private Texture silverMedal;
    private Texture bronzeMedal;
    private Texture highScoreText;
    private Texture okPress;
    private Texture okNotPress;
    private Texture characterNameText;
    private Texture characterSecondText;
    private Texture firstText;
    private Texture secondText;
    private Texture thirdText;
    private Texture fourthText;
    private Texture fifthText;
    private Texture reset;
    private Texture resetPress;
    private Vector2 wavePos1;
    private Vector2 wavePos2;
    private Stage stage;
    private float scrollSpeed;
    private BitmapFont font;
    private Preferences info;
    private ImageButton okIB;
    private ImageButton resetIB;
    private int scoreArray[];
    private String nameArray[];
    private float fontOffset;


    public HighScoreState(final GameStateManager gsm) {
        super(gsm);

        /**Generate Viewport & Stage**/
        Gdx.input.setCatchBackKey(true);
        Viewport viewport = new StretchViewport(Flume.WIDTH, Flume.HEIGHT, cam);
        viewport.apply();
        stage = new Stage();
        stage.setViewport(viewport);
        setViewport();
        Gdx.input.setInputProcessor(stage);

        info = Gdx.app.getPreferences("My Preferences");

        /**Generate Texture**/
        wave = new Texture("menu/wave.png");
        goldMedal = new Texture("highScore/goldMedal.png");
        silverMedal = new Texture("highScore/silverMedal.png");
        bronzeMedal = new Texture("highScore/bronzeMedal.png");
        highScoreText = new Texture("highScore/highScoreText.png");
        okPress = new Texture("highScore/okPress.png");
        okNotPress = new Texture("highScore/okNotPress.png");
        characterNameText = new Texture("highScore/characterNameText.png");
        characterSecondText = new Texture("highScore/characterSecondText.png");
        firstText = new Texture("highScore/firstText.png");
        secondText = new Texture("highScore/secondText.png");
        thirdText = new Texture("highScore/thirdText.png");
        fourthText = new Texture("highScore/fourthText.png");
        fifthText = new Texture("highScore/fifthText.png");
        reset = new Texture("highScore/reset.png");
        resetPress = new Texture("highScore/resetPress.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/TH KODCHASAL BOLD.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        parameter.color = Color.WHITE;
        parameter.characters = "ABCDEFGHIJKLMNOPQURSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "!\"#$%&'()*+-*/0123456789:;<=>?@[\\]^_`{|}~ " +
                "กขฃคฅฆงจฉชซฌญฎฏฐฑฒณดตถทธนบปผฝพฟภมยรฤลฦวศษสหฬอฮฯะัาำิีึืฺุู฿เแโใไๅๆ็่้๊๋์ํ๎๏๐๑๒๓๔๕๖๗๘๙๚๛";
        font = generator.generateFont(parameter);
        generator.dispose();

        /**Generate Button**/
        okIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(okNotPress)),
                new TextureRegionDrawable(new TextureRegion(okPress)));
        okIB.setPosition(-okNotPress.getWidth() / 2,
                -Flume.HEIGHT * (35f / 100f) - okNotPress.getHeight());
        okIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new MenuState(gsm));
                dispose();
            }
        });

        resetIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(reset)),
                new TextureRegionDrawable(new TextureRegion(resetPress)));
        resetIB.setPosition(Flume.WIDTH * (40f / 100f),
                -Flume.HEIGHT * (40f / 100f) - resetIB.getHeight() / 2);
        resetIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                info.putBoolean("hsDefault", true);
                info.putString("5", "30 Stepboom");
                info.putString("4", "25 Tueytoma");
                info.putString("3", "20 Destroyer");
                info.putString("2", "15 Punisher");
                info.putString("1", "10 Echolax");
                info.flush();
                for (int i = 0; i < 5; i++) {
                    String row = info.getString((i + 1) + "");
                    nameArray[i] = row.substring(row.indexOf(" ") + 1);
                    scoreArray[i] = Integer.parseInt(row.substring(0, row.indexOf(" ")));
                }
            }
        });

        /**Set Default Param**/
        scoreArray = new int[5];
        nameArray = new String[5];
        wavePos1 = new Vector2(-Flume.WIDTH / 2, Flume.HEIGHT * (27f / 100f) - wave.getHeight());
        wavePos2 = new Vector2(Flume.WIDTH / 2, Flume.HEIGHT * (27f / 100f) - wave.getHeight());

        for (int i = 0; i < 5; i++) {
            String row = info.getString((i + 1) + "");
            nameArray[i] = row.substring(row.indexOf(" ") + 1);
            scoreArray[i] = Integer.parseInt(row.substring(0, row.indexOf(" ")));
        }

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "basetest");
        fontOffset = glyphLayout.height;

        if (Gdx.graphics.getDensity() == 1.5) {
            scrollSpeed = 3f;
        } else {
            scrollSpeed = 2f;
        }

        /**Add to Stage**/
        stage.addActor(okIB);
        stage.addActor(resetIB);
    }

    public void setViewport() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    public void updateWave() {

        if (wavePos1.x <= -Flume.WIDTH) {
            wavePos1.x = -Flume.WIDTH / 2;
        }
        if (wavePos2.x <= -Flume.WIDTH / 2) {
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
        }
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
        sb.draw(highScoreText, -highScoreText.getWidth() / 2,
                Flume.HEIGHT * (39f / 100f) - highScoreText.getHeight() / 2);
        sb.draw(characterNameText, -characterNameText.getWidth() / 2,
                Flume.HEIGHT * (16f / 100f));
        sb.draw(characterSecondText, Flume.WIDTH * (33f / 100f) - characterSecondText.getWidth() / 2,
                Flume.HEIGHT * (16f / 100f));
        sb.draw(goldMedal, -Flume.WIDTH * (37f / 100f) - goldMedal.getWidth() / 2,
                Flume.HEIGHT * (8f / 100f));
        sb.draw(silverMedal, -Flume.WIDTH * (37f / 100f) - silverMedal.getWidth() / 2,
                -Flume.HEIGHT * (2f / 100f));
        sb.draw(bronzeMedal, -Flume.WIDTH * (37f / 100f) - bronzeMedal.getWidth() / 2,
                -Flume.HEIGHT * (12f / 100f));
        sb.draw(firstText, -Flume.WIDTH * (30f / 100f) - firstText.getWidth() / 2,
                Flume.HEIGHT * (8f / 100f));
        sb.draw(secondText, -Flume.WIDTH * (30f / 100f) - secondText.getWidth() / 2,
                -Flume.HEIGHT * (2f / 100f));
        sb.draw(thirdText, -Flume.WIDTH * (30f / 100f) - thirdText.getWidth() / 2,
                -Flume.HEIGHT * (12f / 100f));
        sb.draw(fourthText, -Flume.WIDTH * (30f / 100f) - fourthText.getWidth() / 2,
                -Flume.HEIGHT * (22f / 100f));
        sb.draw(fifthText, -Flume.WIDTH * (30f / 100f) - fifthText.getWidth() / 2,
                -Flume.HEIGHT * (32f / 100f));
        /** Draw Name and Second **/
        font.draw(sb, nameArray[0], -Flume.WIDTH * (10f / 100f),
                Flume.HEIGHT * (8f / 100f) + fontOffset);
        font.draw(sb, nameArray[1], -Flume.WIDTH * (10f / 100f),
                -Flume.HEIGHT * (2f / 100f) + fontOffset);
        font.draw(sb, nameArray[2], -Flume.WIDTH * (10f / 100f),
                -Flume.HEIGHT * (12f / 100f) + fontOffset);
        font.draw(sb, nameArray[3], -Flume.WIDTH * (10f / 100f),
                -Flume.HEIGHT * (22f / 100f) + fontOffset);
        font.draw(sb, nameArray[4], -Flume.WIDTH * (10f / 100f),
                -Flume.HEIGHT * (32f / 100f) + fontOffset);
        font.draw(sb, scoreArray[0] + "", Flume.WIDTH * (33f / 100f) - 20f,
                Flume.HEIGHT * (8f / 100f) + fontOffset);
        font.draw(sb, scoreArray[1] + "", Flume.WIDTH * (33f / 100f) - 20f,
                -Flume.HEIGHT * (2f / 100f) + fontOffset);
        font.draw(sb, scoreArray[2] + "", Flume.WIDTH * (33f / 100f) - 20f,
                -Flume.HEIGHT * (12f / 100f) + fontOffset);
        font.draw(sb, scoreArray[3] + "", Flume.WIDTH * (33f / 100f) - 20f,
                -Flume.HEIGHT * (22f / 100f) + fontOffset);
        font.draw(sb, scoreArray[4] + "", Flume.WIDTH * (33f / 100f) - 20f,
                -Flume.HEIGHT * (32f / 100f) + fontOffset);
        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        wave.dispose();
        goldMedal.dispose();
        silverMedal.dispose();
        bronzeMedal.dispose();
        highScoreText.dispose();
        okPress.dispose();
        okNotPress.dispose();
        characterNameText.dispose();
        characterSecondText.dispose();
        firstText.dispose();
        secondText.dispose();
        thirdText.dispose();
        fourthText.dispose();
        fifthText.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

}
