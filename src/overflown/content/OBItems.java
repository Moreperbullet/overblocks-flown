package overflown.content;

import arc.graphics.*;
import mindustry.type.*;
import overflown.graphics.*;

public class OBItems{

    public static Item rosingAlloy, diseaseFragments, diseaseVector, aquamarine;

    public static void load(){

        rosingAlloy = new Item("rosing-alloy", Color.valueOf("AF6356")){{
            cost = 1.2f;
        }};

        diseaseFragments = new Item("disease-fragments", OBPal.darkDreadRust){{
            hardness = 4;
            buildable = false;
        }};

        diseaseVector = new Item("disease-vector", OBPal.dreadRust);
        
        aquamarine = new Item("aquamarine", Color.valueOf("68ACF5")){{
            hardness = 5;
        }};
    }
}
