package overflown.content;

import mindustry.maps.*;
import mindustry.type.*;

import static mindustry.content.Planets.*;

public class OBSectorPresets{
   public static SectorPreset lifelessCanyon;

   public static void load(){
      lifelessCanyon = new SectorPreset("lifelessCanyon", serpulo, 256){{
         captureWave = 30;
         difficulty = 3;
      }};
   }
}