package overflown.world.blocks;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.*;

public class DensityProjector extends ForceProjector{
   public int phases = 4;
   //TODO armor

   @Override
   public void setBars(){
      super.setBars();

      addBar("shieldphase", (DensityBuild entity) ->
      new Bar(() ->
      Core.bundle.format("bar.shield-phases", entity.currentPhase), () -> Color.scarlet, () -> entity.currentPhase / phases));
   }

   public class DensityBuild extends ForceBuild{
      public int currentPhase;

      @Override
      public void updateTile(){
         super.updateTile();

         float buildupFraction = Mathf.clamp(1f - buildup / shieldHealth + phaseShieldBoost * phaseHeat);

         currentPhase = Mathf.clamp((int)(buildupFraction * phases), 0, phases - 1);
      }
   }
}