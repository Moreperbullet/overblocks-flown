package overflown.content;

import mindustry.maps.*;
import mindustry.type.*;

import static mindustry.content.Planets.*;

public class OBSectorPresets{
   public static SectorPreset lifelessCanyon, diseaseAmmoFactory;

   public static void load(){
      lifelessCanyon = new SectorPreset("lifelessCanyon", serpulo, 256){{
         captureWave = 30;
         difficulty = 3;
      }};

      diseaseAmmoFactory = new SectorPreset("diseaseAmmoFactory", serpulo, 257){{
         captureWave = 17;
         difficulty = 4;
      }};

      //disease vector fallout site

      //aquamarine meteorites
   }
}