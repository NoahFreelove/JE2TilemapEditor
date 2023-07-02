package org.JE.JE2TilemapEditor;

import org.JE.JE2.IO.Filepath;
import org.JE.JE2.UI.UIElements.Buttons.Button;
import org.JE.JE2.UI.UIObjects.UIWindow;
import org.JE.JE2.Utility.FileDialogs;
import org.JE.JE2.Window.Window;
import org.joml.Vector2f;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class TilePalette extends UIWindow {

    ArrayList<TileDefinition> tileList;
    int selected = -1;
    public TilePalette(){
        super("Tile Palette",0);

        tileList = new ArrayList<>();
        Button newTileButton = new Button("Add New Tile");
        newTileButton.onClickEvent = this::addNewTile;
        addElement(newTileButton);
        Button saveButton = new Button("Save To File");
        saveButton.onClickEvent = this::saveToFile;
        addElement(saveButton);

        addTileFromFile(new Filepath("texture1.png",true));
        addTileFromFile(new Filepath("texture2.png",true));
        selected = 0;
    }

    public void setSelected(TileDefinition td){
        selected = tileList.indexOf(td);
    }
    public void setSelected(int id){
        selected = id;
    }

    public int getSelected() {
        return selected;
    }

    private void addNewTile(){
        File file = FileDialogs.getFile("Select Image", "", new String[]{"png","jpg","bmp","*"});
        if(file.exists())
        {
            String fp = file.getAbsolutePath();

            String[] split = fp.split("resources\\\\");

            Filepath filepath;
            if(split.length>1) {
                filepath = new Filepath(split[1],true);
            }
            else
                filepath = new Filepath(fp,false);
            addTileFromFile(filepath);
        }
        else {
            System.out.println("selected file was non-existent");
        }
    }

    private void saveToFile(){
        SaveMap.saveToFile(new Filepath("world.txt",true), TileEditor.getInstance());
    }

    private void addTileFromFile(Filepath fp){
        TileDefinition td = new TileDefinition(tileList.size(),fp);
        tileList.add(td);
        addElement(td);
    }

    public TileDefinition currentTile(){
        if(selected == -1)
            return null;
        return tileList.get(selected);
    }
}
