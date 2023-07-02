package org.JE.JE2TilemapEditor;

import org.JE.JE2.IO.Logging.Logger;
import org.JE.JE2.IO.UserInput.Keyboard.Keyboard;
import org.JE.JE2.IO.UserInput.Mouse.Mouse;
import org.JE.JE2.IO.UserInput.Mouse.MouseButton;
import org.JE.JE2.Objects.GameObject;
import org.JE.JE2.Objects.Scripts.LambdaScript.ILambdaScript;
import org.JE.JE2.Rendering.Camera;
import org.JE.JE2.Rendering.Shaders.ShaderProgram;
import org.JE.JE2.Rendering.Texture;
import org.JE.JE2.Scene.Scene;
import org.JE.JE2.Utility.Time;
import org.joml.Vector2f;

import java.util.ArrayList;

public class TileEditor extends Scene {

    private static TileEditor instance;
    private static TilePalette tp;
    private final float cameraMoveSpeed = 5;
    public final ArrayList<GameObject> tiles = new ArrayList<>();

    public TileEditor() {
        Camera c = new Camera();
        GameObject camera = new GameObject();
        camera.addScript(new ILambdaScript() {
            @Override
            public void update(GameObject parent) {
                if(Keyboard.isKeyPressed(Keyboard.nameToCode("W"))){
                    parent.getTransform().translateY(cameraMoveSpeed * Time.deltaTime());
                }
                if(Keyboard.isKeyPressed(Keyboard.nameToCode("S"))){
                    parent.getTransform().translateY(-cameraMoveSpeed * Time.deltaTime());
                }
                if(Keyboard.isKeyPressed(Keyboard.nameToCode("A"))){
                    parent.getTransform().translateX(-cameraMoveSpeed * Time.deltaTime());
                }
                if(Keyboard.isKeyPressed(Keyboard.nameToCode("D"))){
                    parent.getTransform().translateX(cameraMoveSpeed * Time.deltaTime());
                }
            }
        });

        camera.addScript(c);
        add(camera);
        setCamera(c);

        tp = new TilePalette();
        addUI(tp);
        tp.setPos(new Vector2f(0,0));
        tp.setSize(new Vector2f(128, Main.wp.windowSize.y));
        Mouse.addMouseReleasedEvent((button, mods) -> {
            Vector2f windowPos = Mouse.getMousePosition();
            if(windowPos.x<=tp.getSize().x())
                return;

            if(button == MouseButton.LEFT){
                if(tp.currentTile() == null)
                    return;
                addTile(Mouse.getMouseWorldPosition2D(), tp.currentTile());
            }
            else if(button == MouseButton.RIGHT){
                removeTile(Mouse.getMouseWorldPosition2D());
            }
        });


    }

    public static TileEditor getInstance(){
        if(instance == null)
        {
            instance = new TileEditor();
        }
        return instance;
    }

    public static TilePalette getPaletteInstance(){
        return tp;
    }

    private void removeTile(Vector2f position){
        position.x-=0.5f;
        position.y-=0.5f;
        // round pos x and y to nearest non-decimal
        position.x = (float) Math.round(position.x);
        position.y = (float) Math.round(position.y);

        for(GameObject tile : tiles){
            if(tile.getTransform().position().equals(position)){
                remove(tile);
                tiles.remove(tile);
                break;
            }
        }
    }

    private void addTile(Vector2f position, TileDefinition tileDefinition){
        //Logger.log(position,3);
        position.x-=0.5f;
        position.y-=0.5f;
        // round pos x and y to nearest non-decimal
        position.x = (float) Math.round(position.x);
        position.y = (float) Math.round(position.y);

        boolean found = false;

        for(GameObject tile : tiles){
            if(tile.getTransform().position().equals(position)){
                found = true;
                break;
            }
        }

        if(found)
            return;

        Texture t = Texture.checkExistElseCreate(tp.getSelected() +"A",-1, tileDefinition.filepath);
        GameObject newTile = GameObject.Sprite(ShaderProgram.spriteShader(), t,t);
        newTile.setIdentity("tile: " + tp.getSelected(), String.valueOf(tp.getSelected()));
        newTile.getTransform().setPosition(position);
        //System.out.println("X: " + position.x + " Y: " + position.y);
        add(newTile);
        tiles.add(newTile);
    }
}
