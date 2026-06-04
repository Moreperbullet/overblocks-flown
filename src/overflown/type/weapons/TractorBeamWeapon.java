package overflown.type.weapons;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;

public class TractorBeamWeapon extends Weapon{
   public float actualDamage = 10f;
   public float force = 0.3f;
   public float scaledForce = 0f;
   public boolean targetAir = true, targetGround = true, targetBuildings = true;

   public TextureRegion laser, laserEnd;

   public StatusEffect status = StatusEffects.none;
   public float statusDuration = 150f;

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
   }

   @Override
   public void init(){
      super.init();
      bullet.damage = this.actualDamage;
      if(this.status != StatusEffects.none) bullet.status = this.status;
      bullet.statusDuration = this.statusDuration;
   }
}