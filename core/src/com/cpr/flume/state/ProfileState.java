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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cpr.flume.Flume;
import com.cpr.flume.sprites.Talk;
import com.cpr.flume.sprites.Human;

/**
 * Created by TEST on 2/16/2017.
 */

public class ProfileState extends State implements InputProcessor {

    private Texture backPress;
    private Texture backNotPress;
    private Texture donePress;
    private Texture doneNotPress;
    private Texture manSelect;
    private Texture manNotSelect;
    private Texture womanSelect;
    private Texture womanNotSelect;
    private Texture gender;
    private Texture chooseColor;
    private Texture changeName;
    private Texture characterName;
    private Texture left;
    private Texture leftPress;
    private Texture right;
    private Texture rightPress;
    private Texture clothSelect;
    private Texture clothNotSelect;
    private Texture eyeSelect;
    private Texture eyeNotSelect;
    private Texture hairSelect;
    private Texture hairNotSelect;
    private Texture skinSelect;
    private Texture skinNotSelect;
    private Texture wave;
    private ImageButton backIB;
    private ImageButton doneIB;
    private ImageButton manIB;
    private ImageButton womanIB;
    private ImageButton changeNameIB;
    private ImageButton leftIB;
    private ImageButton rightIB;
    private ImageButton clothIB;
    private ImageButton eyeIB;
    private ImageButton hairIB;
    private ImageButton skinIB;
    public Stage stage;
    private boolean isSelectCloth;
    private boolean isSelectEye;
    private boolean isSelectHair;
    private boolean isSelectSkin;
    private boolean isMan;
    private boolean isWoman;
    private int clothNo;
    private int eyeNo;
    private int hairNo;
    private int skinNo;
    private BitmapFont font;
    private String name;
    private Vector2 wavePos1;
    private Vector2 wavePos2;
    private float scrollSpeed;
    private Preferences info;
    private Human human;
    private float fontOffset;
    public static InputMultiplexer inputMultiplexer;
    private Talk talk;

    public ProfileState(final GameStateManager gsm) {
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

        /**Generate Texture**/
        wave = new Texture("menu/wave.png");
        backPress = new Texture("profile/backPress.png");
        backNotPress = new Texture("profile/backNotPress.png");
        donePress = new Texture("profile/donePress.png");
        doneNotPress = new Texture("profile/doneNotPress.png");
        manSelect = new Texture("profile/manSelect.png");
        manNotSelect = new Texture("profile/manNotSelect.png");
        womanSelect = new Texture("profile/womanSelect.png");
        womanNotSelect = new Texture("profile/womanNotSelect.png");
        gender = new Texture("profile/gender.png");
        chooseColor = new Texture("profile/chooseColor.png");
        changeName = new Texture("profile/changeName.png");
        characterName = new Texture("profile/characterName.png");
        left = new Texture("profile/left.png");
        leftPress = new Texture("profile/leftPress.png");
        right = new Texture("profile/right.png");
        rightPress = new Texture("profile/rightPress.png");
        clothSelect = new Texture("profile/clothSelect.png");
        clothNotSelect = new Texture("profile/clothNotSelect.png");
        eyeSelect = new Texture("profile/eyeSelect.png");
        eyeNotSelect = new Texture("profile/eyeNotSelect.png");
        hairSelect = new Texture("profile/hairSelect.png");
        hairNotSelect = new Texture("profile/hairNotSelect.png");
        skinSelect = new Texture("profile/skinSelect.png");
        skinNotSelect = new Texture("profile/skinNotSelect.png");

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
                gsm.set(new MenuState(gsm));
                dispose();
            }
        });

        doneIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(doneNotPress)),
                new TextureRegionDrawable(new TextureRegion(donePress)));
        doneIB.setPosition(Flume.WIDTH * (34f / 100f), -Flume.HEIGHT * (36.5f / 100f));
        doneIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (name.equals("Edit Name.."))
                    name = "Anonymous";
                info.putString("name", name);
                info.putInteger("skinNo", skinNo);
                info.putInteger("eyeNo", eyeNo);
                info.putInteger("hairNo", hairNo);
                info.putInteger("clothNo", clothNo);
                info.putBoolean("isMan", isMan);
                info.flush();
                gsm.push(new DifficultyState(gsm));
            }
        });

        manIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(manNotSelect)),
                new TextureRegionDrawable(new TextureRegion(manNotSelect)),
                new TextureRegionDrawable(new TextureRegion(manSelect)));
        manIB.setPosition(-Flume.WIDTH * (31f / 100f), Flume.HEIGHT * (15f / 100f));
        manIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMan) {
                    isMan = true;
                    isWoman = false;
                }
                manIB.setChecked(isMan);
                womanIB.setChecked(isWoman);
            }
        });

        womanIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(womanNotSelect)),
                new TextureRegionDrawable(new TextureRegion(womanNotSelect)),
                new TextureRegionDrawable(new TextureRegion(womanSelect)));
        womanIB.setPosition(-Flume.WIDTH * (15f / 100f), Flume.HEIGHT * (15f / 100f));
        womanIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isWoman) {
                    isWoman = true;
                    isMan = false;
                }
                manIB.setChecked(isMan);
                womanIB.setChecked(isWoman);
            }
        });

        changeNameIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(changeName)),
                new TextureRegionDrawable(new TextureRegion(changeName)));
        changeNameIB.setPosition(-Flume.WIDTH * (17f / 100f) - changeName.getWidth() / 2
                , Flume.HEIGHT * (32f / 100f) - changeName.getHeight() / 2);
        changeNameIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.getTextInput(new Input.TextInputListener() {
                                           @Override
                                           public void input(String text) {
                                               String trimText = text.trim();
                                               if (text.length() > 10) {
                                                   name = trimText.substring(0, 10);
                                               } else if (text.equals("")) {
                                                   name = "Edit Name..";
                                               } else {
                                                   name.trim();
                                                   name = trimText;
                                               }
                                           }

                                           @Override
                                           public void canceled() {
                                           }
                                       }, "Enter Your Name",
                        name.equals("Edit Name..") ? "" : name,
                        name.equals("Edit Name..") ? "Your Name (Not Over 10 Character):" : "");
            }
        });

        leftIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(left)),
                new TextureRegionDrawable(new TextureRegion(leftPress)));
        leftIB.setPosition(Flume.WIDTH * (9f / 100f) - left.getWidth() / 2, -left.getHeight() / 2);
        leftIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isSelectCloth) {
                    if (clothNo == 0) {
                        clothNo = Human.MAX_CLOTH_NO - 1;
                    } else
                        clothNo--;
                } else if (isSelectEye) {
                    if (eyeNo == 0) {
                        eyeNo = Human.MAX_EYE_NO - 1;
                    } else
                        eyeNo--;
                } else if (isSelectHair) {
                    if (hairNo == 0) {
                        hairNo = Human.MAX_HAIR_NO - 1;
                    } else
                        hairNo--;
                } else {
                    if (skinNo == 0) {
                        skinNo = Human.MAX_SKIN_NO - 2;
                    } else if (skinNo == 3) {
                        skinNo = 1;
                    } else
                        skinNo--;
                }
            }
        });

        rightIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(right)),
                new TextureRegionDrawable(new TextureRegion(rightPress)));
        rightIB.setPosition(Flume.WIDTH * (34f / 100f) - right.getWidth() / 2, -right.getHeight() / 2);
        rightIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isSelectCloth) {
                    if (clothNo == Human.MAX_CLOTH_NO - 1) {
                        clothNo = 0;
                    } else
                        clothNo++;
                } else if (isSelectEye) {
                    if (eyeNo == Human.MAX_EYE_NO - 1) {
                        eyeNo = 0;
                    } else
                        eyeNo++;
                } else if (isSelectHair) {
                    if (hairNo == Human.MAX_HAIR_NO - 1) {
                        hairNo = 0;
                    } else
                        hairNo++;
                } else {
                    if (skinNo == Human.MAX_SKIN_NO - 2) {
                        skinNo = 0;
                    } else if (skinNo == 1) {
                        skinNo = 3;
                    } else
                        skinNo++;
                }
            }
        });

        clothIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(clothNotSelect)),
                new TextureRegionDrawable(new TextureRegion(clothNotSelect)),
                new TextureRegionDrawable(new TextureRegion(clothSelect)));
        clothIB.setPosition(-Flume.WIDTH * (31f / 100f), -Flume.HEIGHT * (25f / 100f));
        clothIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isSelectCloth) {
                    isSelectCloth = true;
                    isSelectEye = false;
                    isSelectHair = false;
                    isSelectSkin = false;
                }
                clothIB.setChecked(isSelectCloth);
                eyeIB.setChecked(isSelectEye);
                hairIB.setChecked(isSelectHair);
                skinIB.setChecked(isSelectSkin);
            }
        });

        eyeIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(eyeNotSelect)),
                new TextureRegionDrawable(new TextureRegion(eyeNotSelect)),
                new TextureRegionDrawable(new TextureRegion(eyeSelect)));
        eyeIB.setPosition(-Flume.WIDTH * (31f / 100f), -Flume.HEIGHT * (5f / 100f));
        eyeIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isSelectEye) {
                    isSelectEye = true;
                    isSelectHair = false;
                    isSelectCloth = false;
                    isSelectSkin = false;
                }
                clothIB.setChecked(isSelectCloth);
                eyeIB.setChecked(isSelectEye);
                hairIB.setChecked(isSelectHair);
                skinIB.setChecked(isSelectSkin);
            }
        });

        hairIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(hairNotSelect)),
                new TextureRegionDrawable(new TextureRegion(hairNotSelect)),
                new TextureRegionDrawable(new TextureRegion(hairSelect)));
        hairIB.setPosition(-Flume.WIDTH * (31f / 100f), -Flume.HEIGHT * (15f / 100f));
        hairIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isSelectHair) {
                    isSelectHair = true;
                    isSelectEye = false;
                    isSelectCloth = false;
                    isSelectSkin = false;
                }
                clothIB.setChecked(isSelectCloth);
                eyeIB.setChecked(isSelectEye);
                hairIB.setChecked(isSelectHair);
                skinIB.setChecked(isSelectSkin);
            }
        });

        skinIB = new ImageButton(new TextureRegionDrawable(new TextureRegion(skinNotSelect)),
                new TextureRegionDrawable(new TextureRegion(skinNotSelect)),
                new TextureRegionDrawable(new TextureRegion(skinSelect)));
        skinIB.setPosition(-Flume.WIDTH * (31f / 100f), Flume.HEIGHT * (5f / 100f));
        skinIB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isSelectSkin) {
                    isSelectSkin = true;
                    isSelectEye = false;
                    isSelectCloth = false;
                    isSelectHair = false;
                }
                clothIB.setChecked(isSelectCloth);
                eyeIB.setChecked(isSelectEye);
                hairIB.setChecked(isSelectHair);
                skinIB.setChecked(isSelectSkin);
            }
        });

        /**Set Default Param**/
        talk = new Talk(0, 0, "profile/talk.png", 7, 250f);
        info = Gdx.app.getPreferences("My Preferences");
        isMan = info.getBoolean("isMan", true);
        clothNo = info.getInteger("clothNo", 0);
        eyeNo = info.getInteger("eyeNo", 0);
        hairNo = info.getInteger("hairNo", 0);
        skinNo = info.getInteger("skinNo", 0);
        if (!info.getString("name", "Anonymous").equals("")) {
            name = info.getString("name", "Anonymous");
        } else {
            name = "Edit Name..";
        }
        isSelectCloth = false;
        isSelectEye = false;
        isSelectHair = false;
        isSelectSkin = true;
        manIB.setChecked(isMan);
        womanIB.setChecked(!isMan);
        skinIB.setChecked(isSelectSkin);
        wavePos1 = new Vector2(-Flume.WIDTH / 2, -Flume.HEIGHT / 2 - wave.getHeight() * (91f / 100f));
        wavePos2 = new Vector2(Flume.WIDTH / 2, -Flume.HEIGHT / 2 - wave.getHeight() * (91f / 100f));

        if (Gdx.graphics.getDensity() == 1.5) {
            scrollSpeed =3f;
        } else {
            scrollSpeed = 2f;
        }

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "basetest");
        fontOffset = glyphLayout.height;

        /**Add to Stage**/
        stage.addActor(backIB);
        stage.addActor(doneIB);
        stage.addActor(manIB);
        stage.addActor(womanIB);
        stage.addActor(changeNameIB);
        stage.addActor(leftIB);
        stage.addActor(rightIB);
        stage.addActor(clothIB);
        stage.addActor(eyeIB);
        stage.addActor(hairIB);
        stage.addActor(skinIB);
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
    }

    @Override
    public void update(float dt) {
        mouse.x = Gdx.input.getX();
        mouse.y = Gdx.input.getY();
        talk.update();
        updateWave();
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(characterName, -Flume.WIDTH * (41.5f / 100f), Flume.HEIGHT * (30f / 100f));
        sb.draw(gender, -Flume.WIDTH * (41.5f / 100f), Flume.HEIGHT * (15f / 100f));
        sb.draw(chooseColor, -Flume.WIDTH * (41.5f / 100f), -Flume.HEIGHT * (5f / 100f));
        font.draw(sb, name, -Flume.WIDTH * (13f / 100f), Flume.HEIGHT * (30f / 100f) + fontOffset);
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
        }
        sb.draw(wave, wavePos1.x, wavePos1.y, Flume.WIDTH + Flume.WIDTH * 0.035f, wave.getHeight());
        sb.draw(wave, wavePos2.x, wavePos2.y, Flume.WIDTH + Flume.WIDTH * 0.035f, wave.getHeight());
        sb.draw(talk.getTexture(), Flume.WIDTH * (18f / 100f), -Flume.HEIGHT *(60f/100f));
        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        wave.dispose();
        backPress.dispose();
        backNotPress.dispose();
        donePress.dispose();
        doneNotPress.dispose();
        manSelect.dispose();
        manNotSelect.dispose();
        womanSelect.dispose();
        womanNotSelect.dispose();
        gender.dispose();
        chooseColor.dispose();
        changeName.dispose();
        characterName.dispose();
        left.dispose();
        right.dispose();
        clothSelect.dispose();
        clothNotSelect.dispose();
        eyeSelect.dispose();
        eyeNotSelect.dispose();
        hairSelect.dispose();
        hairNotSelect.dispose();
        skinSelect.dispose();
        skinNotSelect.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            gsm.set(new MenuState(gsm));
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
