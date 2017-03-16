package com.cpr.flume.sprites;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TEST on 3/8/2017.
 */

public class Human {

    public AssetManager manager = new AssetManager();
    private List<AssetDescriptor<Texture>> body;
    private List<AssetDescriptor<Texture>> eye;
    private List<AssetDescriptor<Texture>> manHair;
    private List<AssetDescriptor<Texture>> manCloth;
    private List<AssetDescriptor<Texture>> womanHair;
    private List<AssetDescriptor<Texture>> womanCloth;
    public static final int MAX_CLOTH_NO = 3;
    public static final int MAX_EYE_NO = 3;
    public static final int MAX_HAIR_NO = 3;
    public static final int MAX_SKIN_NO = 5;

    public void load() {
        /** Initialize **/
        body = new ArrayList<AssetDescriptor<Texture>>();
        eye = new ArrayList<AssetDescriptor<Texture>>();
        manHair = new ArrayList<AssetDescriptor<Texture>>();
        manCloth = new ArrayList<AssetDescriptor<Texture>>();
        womanHair = new ArrayList<AssetDescriptor<Texture>>();
        womanCloth = new ArrayList<AssetDescriptor<Texture>>();

        /** LOAD **/
        for (int i = 1; i <= MAX_SKIN_NO; i++) {
            AssetDescriptor<Texture> skin = new AssetDescriptor<Texture>("model/body/b" + i + ".png", Texture.class);
            body.add(skin);
            manager.load(skin);
        }
        for (int i = 1; i <= MAX_EYE_NO; i++) {
            AssetDescriptor<Texture> face = new AssetDescriptor<Texture>("model/face/f" + i + ".png", Texture.class);
            eye.add(face);
            manager.load(face);
        }
        for (int i = 1; i <= MAX_HAIR_NO; i++) {
            AssetDescriptor<Texture> hairM = new AssetDescriptor<Texture>("model/boy_hair/bh" + i + ".png", Texture.class);
            manHair.add(hairM);
            manager.load(hairM);
        }
        for (int i = 1; i <= MAX_HAIR_NO; i++) {
            AssetDescriptor<Texture> hairW = new AssetDescriptor<Texture>("model/girl_hair/gh" + i + ".png", Texture.class);
            womanHair.add(hairW);
            manager.load(hairW);
        }
        for (int i = 1; i <= MAX_CLOTH_NO; i++) {
            AssetDescriptor<Texture> clothM = new AssetDescriptor<Texture>("model/boy_uniform/bu" + i + ".png", Texture.class);
            manCloth.add(clothM);
            manager.load(clothM);
        }
        for (int i = 1; i <= MAX_CLOTH_NO; i++) {
            AssetDescriptor<Texture> clothW = new AssetDescriptor<Texture>("model/girl_uniform/gu" + i + ".png", Texture.class);
            womanCloth.add(clothW);
            manager.load(clothW);
        }
    }

    public List<AssetDescriptor<Texture>> getBody() {
        return body;
    }

    public List<AssetDescriptor<Texture>> getEye() {
        return eye;
    }

    public List<AssetDescriptor<Texture>> getManHair() {
        return manHair;
    }

    public List<AssetDescriptor<Texture>> getManCloth() {
        return manCloth;
    }

    public List<AssetDescriptor<Texture>> getWomanHair() {
        return womanHair;
    }

    public List<AssetDescriptor<Texture>> getWomanCloth() {
        return womanCloth;
    }

    public void dispose() {
        manager.dispose();
    }
}

