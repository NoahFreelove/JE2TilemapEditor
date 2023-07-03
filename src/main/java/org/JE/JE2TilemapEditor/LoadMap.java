package org.JE.JE2TilemapEditor;

import org.JE.JE2.IO.Filepath;
import org.JE.JE2.Objects.GameObject;
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
            TileEditor.getPaletteInstance().addTileFromFile(new TileDefinition(Integer.parseInt(line[0]), new Filepath(line[1], Boolean.parseBoolean(line[2]))));
            i++;
        }
        i++;

        for (; i < data.length; i++) {
            if(data[i].length() == 0)
                break;
            String[] line = data[i].split(":");
            System.out.println(Arrays.toString(line));
            TileDefinition td = TileEditor.getPaletteInstance().tileList.get(Integer.parseInt(line[0]));
            float x = Float.parseFloat(line[1]);
            float y = Float.parseFloat(line[2]);
            int layer = Integer.parseInt(line[3]);
            float rot = Float.parseFloat(line[4]);
            GameObject go = editor.createTile(td, new Vector2f(x,y),td.id,layer);
            go.setRotation(rot);
            editor.addActualTile(go);
        }

    }
}
