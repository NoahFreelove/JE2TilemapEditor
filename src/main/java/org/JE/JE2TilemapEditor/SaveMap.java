package org.JE.JE2TilemapEditor;

import org.JE.JE2.IO.Filepath;
import org.JE.JE2.IO.Logging.Errors.JE2Error;
import org.JE.JE2.IO.Logging.Logger;
import org.JE.JE2.Objects.GameObject;
import org.JE.JE2.Utility.Encryption;

import java.io.File;
import java.io.FileWriter;

public class SaveMap {

    public static boolean saveToFile(Filepath dest, TileEditor editor)
    {
        return saveToFile(dest,editor,"");
    }

    public static boolean saveToFile(Filepath destination, TileEditor editor, String password){
        File destFile = new File(destination.getPath(false));


        StringBuilder sb = new StringBuilder();
        for (TileDefinition td : TileEditor.getPaletteInstance().tileList) {
            sb.append(td.id).append(":").append(td.filepath.getPath(td.filepath.isClassLoaderPath)).append(":").append(td.filepath.isClassLoaderPath).append("\n");
        }
        sb.append("END-DEF").append("\n");
        for (GameObject o : editor.tiles) {
            // Tile ID
            sb.append(o.tag).append(":");
            // Pos
            sb.append((int)o.getTransform().position().x()).append(":").append((int)o.getTransform().position().y()).append(":");
            // layer
            sb.append(o.getLayer()).append(":");
            // rot
            sb.append((int)o.getTransform().rotation().z());
            sb.append("\n");
        }

        try {
            if(!destFile.exists())
                destFile.createNewFile();
            String finalString = sb.toString();
            if(!password.equals(""))
            {
                if(password.length()<24){
                    Logger.log(new JE2Error("Encryption password must be at least 24 characters long!"));
                    return false;
                }
                finalString = Encryption.encrypt(finalString,password);
            }
            FileWriter fw = new FileWriter(destFile);
            fw.append(finalString);
            fw.close();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
