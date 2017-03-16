package com.cpr.flume.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cpr.flume.Flume;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Flume.WIDTH;
		config.height = Flume.HEIGHT;
		config.title = Flume.TITLE;
		new LwjglApplication(new Flume(), config);
	}
}
