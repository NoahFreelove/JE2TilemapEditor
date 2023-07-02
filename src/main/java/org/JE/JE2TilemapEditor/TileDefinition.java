package org.JE.JE2TilemapEditor;

import org.JE.JE2.IO.Filepath;
import org.JE.JE2.Rendering.Texture;
import org.JE.JE2.UI.UIElements.Buttons.ImageButton;
import org.joml.Vector2f;

public class TileDefinition extends ImageButton {
    public final int id;
    public final Filepath filepath;
    public final Vector2f size;


    public TileDefinition(int id, Filepath filepath) {
        super(Texture.checkExistElseCreate(String.valueOf(id), -1, filepath, false).resource, () -> TileEditor.getPaletteInstance().setSelected(id));
        this.id = id;
        this.filepath = filepath;
        this.size = new Vector2f(64,64);
    }

    public TileDefinition(int id, Filepath filepath, Vector2f size) {
        super(Texture.checkExistElseCreate(String.valueOf(id), -1, filepath, false).resource, () -> TileEditor.getPaletteInstance().setSelected(id));
        this.id = id;
        this.filepath = filepath;
        this.size = size;
    }
}
