package org.JE.JE2TilemapEditor;

import org.JE.JE2.IO.Filepath;
import org.JE.JE2.UI.UIElements.Buttons.Button;
import org.JE.JE2.UI.UIElements.Buttons.ImageButton;
import org.JE.JE2.UI.UIElements.ElementEventChanged;
import org.JE.JE2.UI.UIElements.Label;
import org.JE.JE2.UI.UIElements.Sliders.Slider;
import org.JE.JE2.UI.UIElements.TextField;
import org.JE.JE2.UI.UIElements.UIElement;
import org.JE.JE2.UI.UIObjects.UIWindow;
import org.JE.JE2.Utility.WindowDialogs;

import java.io.File;
import java.util.ArrayList;

public class TilePalette extends UIWindow {

    ArrayList<TileDefinition> tileList;
    int selected = -1;

    private String worldName = "world";
    public TilePalette(){
        super("Tile Palette",0);

        tileList = new ArrayList<>();
        Button newTileButton = new Button("Add New Tile");
        newTileButton.onClickEvent = this::addNewTile;
        addElement(newTileButton);
        Button saveButton = new Button("Save To File");
        saveButton.onClickEvent = this::saveToFile;
        addElement(saveButton);

        TextField field = new TextField(32,32,"world","Set World Name");
        field.eventChanged = value -> worldName = value;
        addElement(field);

        Button loadButton = new Button("Load From File");
        loadButton.onClickEvent = this::loadFromFile;
        addElement(loadButton);

        Label rot = new Label("Rotation: 0");
        Slider rotationSlider = new Slider(0,0,360,5);
        rotationSlider.onChange = value -> {
            TileEditor.getInstance().rotation = value;
            rot.setText("Rotation: " + value.intValue());
        };
        addElement(rot,rotationSlider);

        addTileFromFile(new TileDefinition(tileList.size(),new Filepath("texture1.png",true)));
        addTileFromFile(new TileDefinition(tileList.size(),new Filepath("texture2.png",true)));
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

    public void addNewTile(){
        File file = WindowDialogs.getFile("Select Image", "", new String[]{"png","jpg","bmp","*"});
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
            addTileFromFile(new TileDefinition(tileList.size(),filepath));

        }
        else {
            System.out.println("selected file was non-existent");
        }
    }

    private void saveToFile(){
        if(worldName.equals(""))
            worldName = "world";
        var result = SaveMap.saveToFile(new Filepath(worldName + ".txt",true), TileEditor.getInstance(),"");
        if(!result){
            WindowDialogs.infoBox("Error","Failed to save map");
        }
    }

    private void loadFromFile(){
        File file = WindowDialogs.getFile("Select Image", "", new String[]{"png","jpg","bmp","*"});
        if(file.exists())
        {
            LoadMap.loadToEditor(new Filepath(file.getAbsolutePath(),false), TileEditor.getInstance());
        }
        else {
            System.out.println("selected file was non-existent");
        }
    }

    public void addTileFromFile(TileDefinition td){
        tileList.add(td);
        addElement(td);
    }

    public TileDefinition currentTile(){
        if(selected == -1)
            return null;
        return tileList.get(selected);
    }
    public void reset(){
        tileList.clear();
        selected = -1;
        for (UIElement element : getChildren()) {
            if(element instanceof ImageButton ib)
                getChildren().remove(ib);
        }
    }
}
