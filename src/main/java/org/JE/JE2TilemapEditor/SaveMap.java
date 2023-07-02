package org.JE.JE2TilemapEditor;

import org.JE.JE2.IO.Filepath;
import org.JE.JE2.Objects.GameObject;

import java.io.File;
import java.io.FileWriter;

public class SaveMap {

    public static void saveToFile(Filepath destination, TileEditor editor){
        File destFile = new File(destination.getPath(false));

        StringBuilder sb = new StringBuilder();
        for (TileDefinition td : TileEditor.getPaletteInstance().tileList) {
            sb.append(td.id).append(":").append(td.filepath.getPath(td.filepath.isClassLoaderPath)).append(":").append(td.filepath.isClassLoaderPath).append("\n");
        }
        sb.append("END-DEF").append("\n");
        for (GameObject o : editor.tiles) {
            sb.append(o.tag).append(":").append((int)o.getTransform().position().x()).append(":").append((int)o.getTransform().position().y());
            sb.append("\n");
        }

        try {
            if(!destFile.exists())
                destFile.createNewFile();
            FileWriter fw = new FileWriter(destFile);
            fw.append(sb);
            fw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
