package overflown.type;

import arc.graphics.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import overflown.graphics.*;
import overflown.world.meta.*;

public class OBStatusEffect extends StatusEffect{

    /** Damage percentage per second. Heals if negative.*/
    public float damagePercentage;

    public OBStatusEffect(String name) {
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        if(damagePercentage > 0) stats.addPercent(OBStats.damagePercent, damagePercentage);
        if(damagePercentage < 0) stats.addPercent(OBStats.healPercent, -damagePercentage);
    }

    @Override
    public void update(Unit unit, StatusEntry entry){
        super.update(unit, entry);
        if(damagePercentage > 0) {
            unit.damageContinuousPierce(unit.maxHealth * damagePercentage / 100);
        }
    }
}
