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
import com.cpr.flume.sprites.Human;

/**
 * Created by TEST on 2/16/2017.
 */

public class EndState extends State {

    private Texture wave;
    private Texture goldMedal;
    private Texture silverMedal;
    private Texture bronzeMedal;
    private Texture congratulations;
    private Texture gameOver;
    private Texture endGameText;
    private Texture backSelect;
    private Texture backNotSelect;
    private Texture fourth;
    private Texture fifth;
    private Stage stage;
    private ImageButton backIB;
    private Human human;
    private float fontOffset;
    private float fontWidth;
    private float fontTimeWidth;
    private BitmapFont font;
    private BitmapFont fontBig;
    private Preferences info;
    private int clothNo;
    private int eyeNo;
    private int hairNo;
    private int skinNo;
    private int time;
    private int rank;
    private String name;
    private String timeUsage;
    private boolean isMan;
    private float scrollSpeed;
    private Vector2 wavePos1;
    private Vector2 wavePos2;

    public EndState(final GameStateManager gsm) {
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
        goldMedal = new Texture("end/goldMedal.png");
        silverMedal = new Texture("end/silverMedal.png");
        bronzeMedal = new Texture("end/bronzeMedal.png");
        congratulations = new Texture("end/congratulations.png");
        gameOver = new Texture("end/gameOver.png");
        endGameText = new Texture("end/endGameText.png");
        backSelect = new Texture("end/backSelect.png");
        backNotSelect = new Texture("end/backNotSelect.png");
        fourth = new Texture("end/4th.png");
        fifth = new Texture("end/5th.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/TH KODCHASAL BOLD.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        parameter.color = new Color(0x07689fff);
        parameter.characters = "ABCDEFGHIJKLMNOPQURSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "!\"#$%&'()*+-*/0123456789:;<=>?@[\\]^_`{|}~ " +
                "กขฃคฅฆงจฉชซฌญฎฏฐฑฒณดตถทธนบปผฝพฟภมยรฤลฦวศษสหฬอฮฯะัาำิีึืฺุู฿เแโใไๅๆ็่้๊๋์ํ๎๏๐๑๒๓๔๕๖๗๘๙๚๛";
        font = generator.generateFont(parameter);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/TH KODCHASAL BOLD.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 96;
        parameter.color = new Color(0x07689fff);
        parameter.characters = "ABCDEFGHIJKLMNOPQURSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "!\"#$%&'()*+-*/0123456789:;<=>?@[\\]^_`{|}~ " +
                "กขฃคฅฆงจฉชซฌญฎฏฐฑฒณดตถทธนบปผฝพฟภมยรฤลฦวศษสหฬอฮฯะัาำิีึืฺุู฿เแโใไๅๆ็่้๊๋์ํ๎๏๐๑๒๓๔๕๖๗๘๙๚๛";
        fontBig = generator.generateFont(parameter);
        generator.dispose();

        /**Human Manager**/
        human = new Human();
        human.load();
        human.manager.finishLoading();

        /**Generate Button**/

        backIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(backNotSelect)),
                new TextureRegionDrawable(new TextureRegion(backSelect)));
        backIB.setPosition(Flume.WIDTH * (18f / 100f),
                -Flume.HEIGHT * (42f / 100f) - backIB.getHeight() / 2);
        backIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                info.putString("name","");
                info.flush();
                gsm.set(new MenuState(gsm));
            }
        });

        /**Set Default Param**/
        isMan = info.getBoolean("isMan", true);
        clothNo = info.getInteger("clothNo", 0);
        eyeNo = info.getInteger("eyeNo", 0);
        hairNo = info.getInteger("hairNo", 0);
        skinNo = info.getInteger("skinNo", 0);
        rank = info.getInteger("rank", -1);
        time = info.getInteger("time", 0);
        name = info.getString("name", "Anonymous");
        timeUsage = "ใช้เวลาทั้งสิ้น " + time + " วินาที";

        if (Gdx.graphics.getDensity() == 1.5) {
            scrollSpeed = 3f;
        } else {
            scrollSpeed = 2f;
        }

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "basetest");
        fontOffset = glyphLayout.height;

        glyphLayout.setText(fontBig, name);
        fontWidth = glyphLayout.width;

        glyphLayout.setText(font, timeUsage);
        fontTimeWidth = glyphLayout.width;

        wavePos1 = new Vector2(-Flume.WIDTH / 2, -Flume.HEIGHT / 2 - wave.getHeight() * (55f / 100f));
        wavePos2 = new Vector2(Flume.WIDTH/2, -Flume.HEIGHT / 2 - wave.getHeight() * (55f / 100f));

        if (!Flume.isMute) {
            Flume.music.play();
        }

        System.out.println("Name : " + name);
        System.out.println("Gender : " + (isMan?"Man":"Woman"));
        System.out.println("Skin : " + skinNo);
        System.out.println("Cloth : " + clothNo);
        System.out.println("Eye : " + eyeNo);
        System.out.println("Hair : " + hairNo);
        System.out.println("Rank : " + rank);
        System.out.println("Time : " + time);


        /**Add to Stage**/
        stage.addActor(backIB);
    }

    public void setViewport() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            gsm.set(new MenuState(gsm));
            dispose();
        }
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

        /** TEXT **/

        sb.draw(congratulations, -Flume.WIDTH * (8f / 100f) - congratulations.getWidth() / 2,
                Flume.HEIGHT * (37f / 100f) - congratulations.getHeight() / 2);
        fontBig.draw(sb, name, -Flume.WIDTH * (15f / 100f) - fontWidth / 2, Flume.HEIGHT * (12f / 100f) + fontOffset);
        font.draw(sb, timeUsage, -Flume.WIDTH * (15f / 100f) - fontTimeWidth / 2, -Flume.HEIGHT * (2f / 100f) + fontOffset);

        /** MEDAL **/
        if (rank == 0)
            sb.draw(goldMedal, Flume.WIDTH * (42f / 100f) - goldMedal.getWidth() / 2,
                    Flume.HEIGHT * (34f / 100f));
        else if (rank == 1)
            sb.draw(silverMedal, Flume.WIDTH * (42f / 100f) - silverMedal.getWidth() / 2,
                    Flume.HEIGHT * (34f / 100f));
        else if (rank == 2)
            sb.draw(bronzeMedal, Flume.WIDTH * (42f / 100f) - bronzeMedal.getWidth() / 2,
                    Flume.HEIGHT * (34f / 100f));
        else if(rank == 3)
            sb.draw(fourth, Flume.WIDTH * (42f / 100f) - fourth.getWidth() / 2,
                    Flume.HEIGHT * (34f / 100f));
        else if(rank == 4)
            sb.draw(fifth, Flume.WIDTH * (42f / 100f) - fifth.getWidth() / 2,
                    Flume.HEIGHT * (34f / 100f));

        /** HUMAN **/
        sb.draw(human.manager.get(human.getBody().get(skinNo)),
                Flume.WIDTH * (29f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
        sb.draw(human.manager.get(human.getEye().get(eyeNo)),
                Flume.WIDTH * (29f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
        if (isMan) {
            sb.draw(human.manager.get(human.getManCloth().get(clothNo)),
                    Flume.WIDTH * (29f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                    -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
            sb.draw(human.manager.get(human.getManHair().get(hairNo)),
                    Flume.WIDTH * (29f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                    -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
        } else {
            sb.draw(human.manager.get(human.getWomanCloth().get(clothNo)),
                    Flume.WIDTH * (29f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                    -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
            sb.draw(human.manager.get(human.getWomanHair().get(hairNo)),
                    Flume.WIDTH * (29f / 100f) - human.manager.get(human.getBody().get(skinNo)).getWidth() / 2,
                    -human.manager.get(human.getBody().get(skinNo)).getHeight() + Flume.HEIGHT * (34f / 100f));
        }

        /** WAVE **/

        sb.draw(wave, wavePos1.x, wavePos1.y, Flume.WIDTH + Flume.WIDTH * 0.035f, wave.getHeight());
        sb.draw(wave, wavePos2.x, wavePos2.y, Flume.WIDTH + Flume.WIDTH * 0.035f, wave.getHeight());

        sb.draw(endGameText, -Flume.WIDTH * (8f / 100f) - endGameText.getWidth() / 2, -Flume.HEIGHT * (42f / 100f));

        sb.end();
        stage.draw();
        stage.act();
    }

    @Override
    public void dispose() {
        congratulations.dispose();
        gameOver.dispose();
        endGameText.dispose();
        backSelect.dispose();
        backNotSelect.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

}
