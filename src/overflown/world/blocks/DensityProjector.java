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
import mindustry.world.blocks.defense.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;


public class DensityProjector extends ForceProjector{
   public int phases = 4;
   public float baseForceArmor;

   //TODO actually calculate armor for enemy bullets
   protected static DensityProjector dBlock;
   protected static DensityBuild dEntity;
   protected static final Cons<Bullet> dConsumer = bullet -> {
       if(bullet.team != dEntity.team && bullet.type.absorbable && !bullet.absorbed &&
         Intersector.isInRegularPolygon(paramBlock.sides, paramEntity.x, paramEntity.y, paramEntity.realRadius(), paramBlock.shieldRotation, bullet.x, bullet.y)){

         bullet.absorb();
         dBlock.hitSound.at(bullet.x, bullet.y, 1f + Mathf.range(0.1f), paramBlock.hitSoundVolume);
         dBlock.absorbEffect.at(bullet);
         dEntity.hit = 1f;
         if(!bullet.type.pierceArmor){
            dEntity.buildup += Damage.applyArmor(bullet.type.shieldDamage(bullet), dEntity.resultArmor);
         }else{
            dEntity.buildup += bullet.type.shieldDamage(bullet);
         }
      }
   };

   public DensityProjector(String name){
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
      Core.bundle.format("bar.shield-phases", entity.currentPhase),
      () -> Pal.darkMetal,
      () -> entity.currentPhase / phases));
   }

   public class DensityBuild extends ForceBuild{
      public int currentPhase;
      public float resultArmor;

      @Override
      public void updateTile(){
         super.updateTile();

         float buildupFraction = Mathf.clamp(1f - buildup / shieldHealth + phaseShieldBoost * phaseHeat);

         currentPhase = Mathf.clamp((int)(buildupFraction * phases), 0, phases - 1) + 1;
         resultArmor = baseForceArmor * (currentPhase / phases);
      }

      @Override
      public void deflectBullets(){
         float realRadius = realRadius();

         if(realRadius > 0 && !broken){
            dBlock = DensityProjector.this;
            dEntity = this;
            Groups.bullet.intersect(x - realRadius, y - realRadius, realRadius * 2f, realRadius * 2f, dConsumer);
         }
      }
   }
}