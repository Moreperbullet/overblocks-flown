package overflown.content;

import arc.audio.*;

import static mindustry.Vars.*;

public class OBSounds{
    public static Sound shootTera = new Sound();

    public static void load(){
        shootTera = tree.loadSound("shootTera");
    }
}
