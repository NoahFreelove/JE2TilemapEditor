package org.JE.JE2TilemapEditor;

import org.JE.JE2.IO.Logging.Logger;
import org.JE.JE2.Manager;
import org.JE.JE2.Window.WindowPreferences;
import org.joml.Vector2i;

public class Main {
    public static WindowPreferences wp = new WindowPreferences();

    public static void main(String[] args) {
        wp.vSync = true;
        wp.windowResizable = false;
        wp.windowSize = new Vector2i(1000,1000);
        wp.windowTitle = "JE2 Tilemap Editor";
        Logger.logThreshold = 2;

        Manager.start(wp);

        Manager.setScene(TileEditor.getInstance());
    }
}