package overflown.type.weapons;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;

import static mindustry.Vars.*;

public class TractorBeamWeapon extends Weapon{
   public float laserWidth = 0.6f;
   public float shootLength = 5f;
   public float actualDamage = 10f;
   public float force = 1.8f;
   public float scaledForce = 0f;

   public boolean targetAir = true, targetGround = true, targetBuildings = false;
   public Color laserColor = Color.white;
   
   public String laserSpriteName = "overflown-purple";

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
      mountType = TractorBeamMount::new;
      recoil = 0f;
      useAttackRange = false;
      activeSound = Sounds.beamParallax;
      activeSoundVolume = 0.9f;
    }

   @Override
   public void load(){
      super.load();

      laser = Core.atlas.find(laserSpriteName + "-laser");
      laserEnd = Core.atlas.find(laserSpriteName + "-laser-end");
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

      TractorBeamMount tractor = (TractorBeamMount)mount;
      boolean canShoot = mount.shoot;

      tractor.any = false;
      if(canShoot && mount.target instanceof Unit u){
         tractor.lastX = u.x;
         tractor.lastY = u.y;
         strength = Mathf.lerpDelta(strength, 1f, 0.1f);

         if(actualDamage > 0){
            u.damageContinuousPierce(actualDamage * state.rules.unitDamage(unit.team));
         }

         if(bullet.status != StatusEffects.none){
            u.apply(bullet.status, bullet.statusDuration);
         }

         tractor.any = true;
         u.impulseNet(Tmp.v1.set(unit).sub(u).limit((force + (1f - u.dst(unit) / bullet.maxRange) * scaledForce)));
      }

      if(canShoot && mount.target instanceof Building b){
         tractor.lastX = b.x;
         tractor.lastY = b.y;
         strength = Mathf.lerpDelta(strength, 1f, 0.1f);
   
         b.damageContinuousPierce(actualDamage * state.rules.unitDamage(unit.team))
         tractor.any = true;
      }

      if(!canShoot){
         strength = Mathf.lerpDelta(strength, 0f, 0.1f);
      }
   }

   @Override
   public void draw(Unit unit, WeaponMount mount){
      super.draw(unit, mount);
      TractorBeamMount tractor = (TractorBeamMount)mount;
      
      if(unit.canShoot()){
         float
            weaponRotation = unit.rotation - 90,
            wx = unit.x + Angles.trnsx(weaponRotation, x, y),
            wy = unit.y + Angles.trnsy(weaponRotation, x, y),
         Draw.z(Layer.bullet);
         float ang = angleTo(tractor.lastX, tractor.lastY);

         Draw.mixcol(laserColor, Mathf.absin(4f, 0.6f));

         Drawf.laser(laser, laserEnd, laserEnd,
         wx + Angles.trnsx(ang, shootLength), wy + Angles.trnsy(ang, shootLength),
         tractor.lastX, tractor.lastY, tractor.strength * laserWidth);

         Draw.mixcol();
      }
   }

   @Override
   public void init(){
      super.init();
      bullet.damage = this.actualDamage;
   }

   public static class TractorBeamMount extends WeaponMount{
      public float lastX, lastY, strength;
      public boolean any;
   }
}