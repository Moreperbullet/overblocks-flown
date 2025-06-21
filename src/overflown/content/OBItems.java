package overflown.content;

import arc.graphics.*;
import mindustry.type.*;
import overflown.graphics.*;

public class OBItems{

    public static Item rosingAlloy, diseaseFragments, diseaseVector;

    public static void load(){

        rosingAlloy = new Item("rosing-alloy", Color.valueOf("AF6356")){{
            cost = 1.2f;
        }};

        diseaseFragments = new Item("disease-fragments", OBPal.darkDreadRust);

        diseaseVector = new Item("disease-vector", OBPal.dreadRust);
    }
}
