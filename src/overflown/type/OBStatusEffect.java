package overflown.type;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import overflown.graphics.*;
import overflown.world.meta.*;

public class OBStatusEffect extends StatusEffect{

    /** Damage percentage per second. Heals if negative.*/
    public float damagePercentage;
    /** Flag for interfered visual effect.*/
    public boolean interferedFlag;

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
    public void draw(Unit unit){
        if(interferedFlag) drawInterfered(unit);
    }

    public void drawInterfered(Unit unit){
        Draw.z(Layer.flyingUnit + 0.1f);
        Draw.blend(Blending.additive);
        float rand = Mathf.random() / 2;
        Draw.color(color, rand);
        Draw.rect(unit.icon(), unit.x, unit.y, unit.rotation - 90);
        Draw.blend();
        Draw.color();
    }

    @Override
    public void update(Unit unit, StatusEntry entry){
        super.update(unit, entry);
        if(damagePercentage > 0) {
            unit.damageContinuousPierce(unit.maxHealth * damagePercentage / 100);
        }
    }
}
