package com.cpr.flume.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cpr.flume.Flume;
import com.cpr.flume.sprites.Person;

/**
 * Created by TEST on 2/17/2017.
 */

public class PlayState extends State {

    public static boolean isFinish;
    public boolean isCounting;
    public boolean isOver;
    public boolean canTouch;
    public boolean changeMotion;
    private Texture gameOver;
    private Texture gameWin;
    private Texture line;
    private Texture howToPlay;
    private Texture wave;
    private Texture timeUsageBox;
    private Texture timeText;
    private Texture flume;
    private Texture three;
    private Texture two;
    private Texture one;
    private Texture zero;
    private Person person;
    private Person personStatic;
    private Stage stage;
    private Preferences info;
    private int scoreArray[];
    private String nameArray[];
    private float level;
    private int maximumTimeUsage;
    private float additiveSpeed;
    private BitmapFont fontBlue;
    private BitmapFont fontOrange;
    private int time;
    private int timeUsage;
    private int delayCount;
    private Timer t1;
    private Timer t2;
    private Timer delay;
    private Vector2 wavePos1;
    private Vector2 wavePos2;
    private float scrollSpeed;
    private float fontOffsetBlue;
    private float fontOffsetOrange;
    private float fontTimeWidth;
    private float fontTimeUsageWidth;
    private GlyphLayout glyphLayout;

    public PlayState(final GameStateManager gsm) {
        super(gsm);

        /**Generate Viewport & Stage**/
        Gdx.input.setCatchBackKey(true);
        Viewport viewport = new StretchViewport(Flume.WIDTH, Flume.HEIGHT, cam);
        viewport.apply();
        stage = new Stage();
        stage.setViewport(viewport);
        Gdx.input.setInputProcessor(stage);

        info = Gdx.app.getPreferences("My Preferences");

        level = info.getInteger("level", 1);
        if (level == 1) {
            maximumTimeUsage = 10;
            additiveSpeed = 0f;
        } else if (level == 2) {
            maximumTimeUsage = 7;
            additiveSpeed = -0.025f;
        } else {
            maximumTimeUsage = 5;
            additiveSpeed = -0.05f;
        }


        /**Generate Texture**/
        gameOver = new Texture("play/gameOver.png");
        gameWin = new Texture("play/gameWin.png");
        line = new Texture("play/line.png");
        howToPlay = new Texture("play/howToPlay.png");
        wave = new Texture("play/wave.png");
        timeUsageBox = new Texture("play/timeUsageBox.png");
        timeText = new Texture("play/timeText.png");
        flume = new Texture("play/flume.png");
        three = new Texture("play/three.png");
        two = new Texture("play/two.png");
        one = new Texture("play/one.png");
        zero = new Texture("play/zero.png");
        String personFile = "play/" + (info.getBoolean("isMan") ? "man/" : "woman/")
                + (info.getInteger("skinNo") + 1)
                + (info.getInteger("eyeNo", 0) + 1)
                + (info.getInteger("clothNo", 0) + 1) + ".png";
        String personStaticFile = "play/" + (info.getBoolean("isMan") ? "man/w" : "woman/w")
                + (info.getInteger("skinNo") + 1)
                + (info.getInteger("eyeNo", 0) + 1)
                + (info.getInteger("clothNo", 0) + 1) + ".png";
        person = new Person(400, -70, personFile, 9, 5f, additiveSpeed);
        personStatic = new Person(400, -70, personStaticFile, 4, 20f, additiveSpeed);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Jelly Crazies.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.color = new Color(0x07689fff);
        parameter.characters = "0123456789";
        fontBlue = generator.generateFont(parameter);
        generator.dispose();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Jelly Crazies.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        parameter.color = new Color(0xf7941dff);
        parameter.characters = "0123456789";
        fontOrange = generator.generateFont(parameter);
        generator.dispose();

        /**Set Default Param**/

        level = info.getInteger("level", 1);
        time = 0;
        timeUsage = maximumTimeUsage;
        isFinish = false;
        isCounting = false;
        isOver = false;
        canTouch = true;
        changeMotion = false;
        delayCount = 3;
        Flume.music.stop();
        if (!Flume.isMute) {
            Flume.gameMusic.play();
        }
        scoreArray = new int[5];
        nameArray = new String[5];

        for (int i = 0; i < 5; i++) {
            String row = info.getString((i + 1) + "");
            nameArray[i] = row.substring(row.indexOf(" ") + 1);
            scoreArray[i] = Integer.parseInt(row.substring(0, row.indexOf(" ")));
        }

        wavePos1 = new Vector2(-Flume.WIDTH / 2, -Flume.HEIGHT * (33f / 100f));
        wavePos2 = new Vector2(Flume.WIDTH / 2, -Flume.HEIGHT * (33f / 100f));

        if (Gdx.graphics.getDensity() == 1.5) {
            scrollSpeed = 4f - additiveSpeed * 2;
        } else {
            scrollSpeed = 2f - additiveSpeed * 2;
        }

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(fontOrange, time + "");
        fontOffsetOrange = glyphLayout.height;

        glyphLayout = new GlyphLayout();
        glyphLayout.setText(fontBlue, timeUsage + "");
        fontOffsetBlue = glyphLayout.height;

        t1 = new Timer();
        t2 = new Timer();
        delay = new Timer();
        delay.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                delayCount--;
                if (delayCount == -1) {
                    delay.stop();
                    delay.clear();
                    t1.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            time++;
                        }
                    }, 1f, 1f);
                }
            }
        }, 1f, 1f);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() && canTouch) {
            {
                person.walk();
                personStatic.walk();

                if (isOver) {
                    Flume.gameMusic.stop();
                    if (!Flume.isMute) {
                        Flume.music.play();
                    }
                    info.putString("name", "");
                    info.flush();
                    gsm.set(new MenuState(gsm));
                    dispose();
                }

                if (isFinish) {
                    Flume.gameMusic.stop();
                    gsm.set(new EndState(gsm));
                    dispose();
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Flume.gameMusic.stop();
            if (!Flume.isMute) {
                Flume.music.play();
            }
            gsm.set(new MenuState(gsm));
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            person.walk();
            personStatic.walk();
        }
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
    public void update(float dt) {
        Vector3 position = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        mouse.x = position.x;
        mouse.y = position.y;
        handleInput();
        if (isFinish == isOver && delayCount <= -1) {
            person.update();
            personStatic.update();
            updateWave();
        }

        if (person.getPosition().x == 0 || person.getPosition().x >= 650) {
            canTouch = false;
            isOver = true;
            t1.stop();
            t2.stop();
            t1.clear();
            t2.clear();
            Timer t3 = new Timer();
            t3.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    canTouch = true;
                }
            }, 1f);
        }

        if (person.getPosition().x >= 300 && person.getPosition().x <= 415 && delayCount <= -1) {
            if (!isCounting) {
                isCounting = true;
                t2 = new Timer();
                t2.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        if (timeUsage == 0)
                            timeUsage = 0;
                        else
                            timeUsage--;
                    }
                }, 1, 1);
            }
        } else {
            t2.clear();
            timeUsage = maximumTimeUsage;
            isCounting = false;
        }

        if (timeUsage == 0 && !isFinish) {
            isFinish = true;
            canTouch = false;
            t1.stop();
            t2.stop();
            t1.clear();
            t2.clear();
            updateScore(info.getString("name", ""), time, checkScorePos(time));
            if (checkScorePos(time) < 5)
                info.putInteger("rank", checkScorePos(time));
            else
                info.putInteger("rank", -1);
            info.putInteger("time", time);
            info.flush();
            Timer t3 = new Timer();
            t3.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    canTouch = true;
                }
            }, 1f);
        }

        glyphLayout = new GlyphLayout();
        glyphLayout.setText(fontOrange, time + "");
        fontTimeWidth = glyphLayout.width;
        glyphLayout = new GlyphLayout();
        glyphLayout.setText(fontBlue, timeUsage + "");
        fontTimeUsageWidth = glyphLayout.width;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(wave, wavePos1.x, wavePos1.y, Flume.WIDTH + Flume.WIDTH * 0.01f, wave.getHeight());
        sb.draw(wave, wavePos2.x, wavePos2.y, Flume.WIDTH + Flume.WIDTH * 0.01f, wave.getHeight());

        sb.draw(line, -Flume.WIDTH * (19.5f / 100f), -Flume.HEIGHT * (33f / 100f));
        sb.draw(line, -Flume.WIDTH * (10f / 100f), -Flume.HEIGHT * (33f / 100f));

        sb.draw(flume, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        sb.draw(timeUsageBox, Flume.WIDTH * (35f / 100f) - timeUsageBox.getWidth() / 2,
                Flume.HEIGHT * (3f / 100f));
        sb.draw(timeText, -Flume.WIDTH * (9f / 100f) - timeText.getWidth() / 2,
                Flume.HEIGHT * (38f / 100f));
        sb.draw(howToPlay, -howToPlay.getWidth() / 2, -Flume.HEIGHT * (45f / 100f));


        if (person.getSpeed().x > -1) {
            sb.draw(person.getTexture(), person.getPosition().x - Flume.WIDTH / 2 - 58f,
                    person.getPosition().y - person.getBounds().getHeight() / 2,
                    person.getBounds().getWidth() * 1.25f, person.getBounds().getHeight() * 1.25f);
        } else {
            if (person.getFrame() == 8) {
                changeMotion = true;
            } else {
                if (!changeMotion) {
                    sb.draw(person.getTexture(), person.getPosition().x - Flume.WIDTH / 2 - 58f,
                            person.getPosition().y - person.getBounds().getHeight() / 2,
                            person.getBounds().getWidth() * 1.25f, person.getBounds().getHeight() * 1.25f);
                }
            }
            if (changeMotion) {
                sb.draw(personStatic.getTexture(), personStatic.getPosition().x - Flume.WIDTH / 2 - 58f,
                        personStatic.getPosition().y - person.getBounds().getHeight() / 2,
                        personStatic.getBounds().getWidth() * 1.25f, personStatic.getBounds().getHeight() * 1.25f);
            }
        }

        fontBlue.draw(sb, timeUsage + "", Flume.WIDTH * (35f / 100f) - fontTimeUsageWidth / 2,
                Flume.HEIGHT * (16f / 100f) + fontOffsetBlue);
        fontOrange.draw(sb, time + "", -Flume.WIDTH * (9f / 100f) - fontTimeWidth / 2,
                Flume.HEIGHT * (24f / 100f) + fontOffsetOrange);

        if (delayCount == 3)
            sb.draw(three, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        else if (delayCount == 2)
            sb.draw(two, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        else if (delayCount == 1)
            sb.draw(one, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        else if (delayCount == 0)
            sb.draw(zero, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);

        if (isOver)
            sb.draw(gameOver, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        if (isFinish)
            sb.draw(gameWin, -Flume.WIDTH / 2, -Flume.HEIGHT / 2);
        sb.end();
    }

    @Override
    public void dispose() {
        person.dispose();
        personStatic.dispose();
        gameOver.dispose();
        gameWin.dispose();
        line.dispose();
        howToPlay.dispose();
        wave.dispose();
        timeUsageBox.dispose();
        timeText.dispose();
        flume.dispose();
        t1.clear();
        t2.clear();
        delay.clear();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    public int checkScorePos(int s) {
        int pos = -1;
        int start = 0;
        int end = 4;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (s == scoreArray[mid]) {
                pos = mid;
                break;
            } else if (s < scoreArray[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        if (pos == -1)
            pos = start;
        return pos;
    }

    //Update new score
    public void updateScore(String name, int score, int pos) {

        if (pos < 5) {
            for (int i = 4; i > pos; i--) {
                scoreArray[i] = scoreArray[i - 1];
                nameArray[i] = nameArray[i - 1];
            }
            scoreArray[pos] = score;
            nameArray[pos] = name;
        }

        for (int i = 0; i < 5; i++) {
            info.putString((i + 1) + "", scoreArray[i] + " " + nameArray[i]);
            info.flush();
        }
    }
}
