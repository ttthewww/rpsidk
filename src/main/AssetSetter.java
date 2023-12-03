package main;

import entity.SpawnPoints;
import object.*;

public class AssetSetter implements SpawnPoints {
    public Game game;
    public ObjectDrawerThread drawerThread;
    public AssetSetter(Game game, ObjectDrawerThread drawerThread) {
        this.game = game;
        this.drawerThread = drawerThread;
    }

    public void setObject() {
        drawerThread.obj[0] = new OBJ_Background(game);

        // drawerThread.obj[1] = new OBJ_Orb();
        // drawerThread.obj[1].worldX = 0;
        // drawerThread.obj[1].worldY = 0;
    }
}
