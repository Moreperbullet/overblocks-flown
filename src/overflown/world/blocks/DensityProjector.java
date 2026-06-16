package overflown.world.blocks;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;


public class DensityProjector extends ForceProjector{
   public int phases = 4;
   //TODO armor

   public ForceProjector(String name){
      super(name);
      update = true;
      solid = true;
      group = BlockGroup.projectors;
      hasPower = true;
      hasLiquids = true;
      hasItems = true;
      envEnabled |= Env.space;
      ambientSound = Sounds.loopShield;
      ambientSoundVolume = 0.1f;
      flags = EnumSet.of(BlockFlag.shield);

      if(consumeCoolant){
         consume(coolantConsumer = new ConsumeCoolant(coolantConsumption)).boost().update(false);
      }
   }

   @Override
   public void setBars(){
      super.setBars();

      addBar("shieldphase", (DensityBuild entity) ->
      new Bar(() ->
      Core.bundle.format("bar.shield-phases", entity.currentPhase), () -> Pal.darkMetal, () -> entity.currentPhase / phases));
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