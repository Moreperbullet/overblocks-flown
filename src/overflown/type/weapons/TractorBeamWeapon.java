package overflown.type.weapons;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;

import static mindustry.Vars.*;

public class TractorBeamWeapon extends Weapon{
   public float laserWidth = 0.6f;
   public float actualDamage = 10f;
   public float force = 0.3f;
   public float scaledForce = 0f;
   public boolean targetAir = true, targetGround = true, targetBuildings = false;

   public TextureRegion laser, laserEnd;

   public TractorBeamWeapon(String name){
      super(name);
   }

   public TractorBeamWeapon(){
   }

   {
      //must be >0 to prevent various bugs
      reload = 1f;
      predictTarget = false;
      autoTarget = true;
      controllable = false;
      rotate = true;
      recoil = 0f;
      useAttackRange = false;
      activeSound = Sounds.beamParallax;
      activeSoundVolume = 0.9f;
    }

   @Override
   public void load(){
      super.load();

      laser = Core.atlas.find(name + "-laser");
      laserEnd = Core.atlas.find(name + "-laser-end");
   }

   @Override
   protected Teamc findTarget(Unit unit, float x, float y, float range, boolean air, boolean ground){
      return Units.closestTarget(unit.team, x, y, range + Math.abs(shootY), u -> u.checkTarget(targetAir, targetGround), t -> targetBuildings);
   }

   @Override
   protected void shoot(Unit unit, WeaponMount mount, float shootX, float shootY, float rotation){
      //so it does not actually shoot
   }

   @Override
   public void update(Unit unit, WeaponMount mount){
      super.update(unit, mount);
      
      float
      weaponRotation = unit.rotation - 90,
      wx = unit.x + Angles.trnsx(weaponRotation, x, y),
      wy = unit.y + Angles.trnsy(weaponRotation, x, y);

      boolean canShoot = mount.shoot;

      if(canShoot && mount.target instanceof Unit u){
         if(actualDamage > 0){
            u.damageContinuousPierce(actualDamage * state.rules.unitDamage(unit.team));
         }

         if(bullet.status != StatusEffects.none){
            u.apply(bullet.status, bullet.statusDuration);
         }

         u.impulseNet(Tmp.v1.set(this).sub(u).limit((force + (1f - u.dst(unit) / bullet.maxRange) * scaledForce)));
      }
   }

   @Override
   public void init(){
      super.init();
      bullet.damage = this.actualDamage;
   }
}