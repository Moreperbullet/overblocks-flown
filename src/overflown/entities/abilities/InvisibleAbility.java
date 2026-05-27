package overflown.entities.abilities;

import arc.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import overflown.gen.*;

public class InvisibleAbility extends Ability{
   public float invisibleSpeed;

   public InvisibleAbility(float invisibleSpeed){
      this.invisibleSpeed = invisibleSpeed;
   }
   
   @Override
   public void created(Unit unit){
      if(unit instanceof Invisiblec n){
         boolean found = false;
         if (n.invisibleA() != null) return;

         for(var ab : unit.abilities){
            if(ab == this){
               found = true;
               n.invisibleA(this);
               //Log.info("Invisible ability is found");
               break;
            }
         }
         if(!found) throw new RuntimeException("Invisible ability isn't found");
      }else{
         Log.warn("This unit does not extend Invisiblec");
      }
   }
}