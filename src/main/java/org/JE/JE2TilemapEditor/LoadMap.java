package org.JE.JE2TilemapEditor;

import org.JE.JE2.IO.Filepath;
import org.JE.JE2.Resources.DataLoader;
import org.JE.JE2.Resources.ResourceManager;
import org.joml.Vector2f;

import java.util.Arrays;

public class LoadMap {

    public static void loadToEditor(Filepath worldPath, TileEditor editor){
        String[] data = DataLoader.readTextFile(worldPath.getPath(false));

        ResourceManager.clearTextureCache();
        TileEditor.getPaletteInstance().reset();
        TileEditor.getInstance().reset();
        int i = 0;

        while (!data[i].strip().equals("END-DEF")){
            String[] line = data[i].split(":");
            TileEditor.getPaletteInstance().addTileFromFile(new TileDefinition(Integer.valueOf(line[0]), new Filepath(line[1], Boolean.valueOf(line[2]))));
            i++;
        }
        i++;

        for (; i < data.length; i++) {
            if(data[i].length() == 0)
                break;
            String[] line = data[i].split(":");
            System.out.println(Arrays.toString(line));
            TileDefinition td = TileEditor.getPaletteInstance().tileList.get(Integer.valueOf(line[0]));
            editor.addActualTile(editor.createTile(td, new Vector2f(Float.valueOf(line[1]),Float.valueOf(line[2])),td.id));
        }

    }
}
