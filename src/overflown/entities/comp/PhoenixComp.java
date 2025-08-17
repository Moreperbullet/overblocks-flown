package overflown.entities.comp;

import arc.util.*;
import ent.anno.Annotations.*;
import mindustry.gen.*;
import mindustry.type.*;

import static mindustry.Vars.*;

@EntityComponent
abstract class PhoenixComp implements Shieldc, Unitc{
    @Import boolean dead;
    @Import UnitType type;
    @Import int id;
    @Import float health;

    transient float iframes;
    transient boolean revived;

    @Override
    public void update(){
        iframes -= Time.delta;
    }

    @Override
    @Replace(10)
    public void kill(){
        if(!revived){
            iframes = 10;
            revived = true;
            if(health < 1) health = 1;
        }
        if((dead || net.client() || !type.killable) && iframes > 0) return;

        //deaths are synced; this calls killed()
        Call.unitDeath(id);
    }

    @Wrap("damage(float)")
    public boolean canBeDamaged(){
        return iframes < 1;
    }

    @Wrap("damagePierce(float, boolean)")
    public boolean canBeDamagePierced(){
        return iframes < 1;
    }
}
