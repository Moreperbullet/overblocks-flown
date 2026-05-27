package overblocks.entities.abilities;

import arc.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import overblocks.gen.*;

public class InvisibleAbility extends Ability{
   public float invisibleSpeed;

   public InvisibleAbility(float invisibleSpeed){
      this.invisibleSpeed = invisibleSpeed;
   }
   
   @Override
   public void created(Unit unit){
      if(unit instanceof Invisiblec n){
         boolean found;
         for(var ab : unit.abilities){
            if(ab == this){
               found = true;
               Log.info("Invisible ability is found");
               break;
            }
         }
         if(!found) Log.warn("Invisible ability not found");
      }else{
         Log.warn("This unit does not extend Invisiblec");
      }
   }
}