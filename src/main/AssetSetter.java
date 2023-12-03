package main;

import entity.SpawnPoints;
import object.*;

public class AssetSetter implements SpawnPoints {
    public Game gp;
    public ObjectDrawerThread drawerThread;
    public AssetSetter(Game gp, ObjectDrawerThread drawerThread) {
        this.gp = gp;
        this.drawerThread = drawerThread;
    }

    public void setObject() {
        drawerThread.obj[0] = new OBJ_Map();
        drawerThread.obj[0].worldX = 0;
        drawerThread.obj[0].worldY = 0;

        drawerThread.obj[1] = new OBJ_Orb();
        drawerThread.obj[1].worldX = 0;
        drawerThread.obj[1].worldY = 0;
    }
}
