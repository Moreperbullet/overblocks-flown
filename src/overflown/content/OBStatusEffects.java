package overflown.content;

import arc.graphics.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.type.*;
import overflown.graphics.*;
import overflown.type.*;

public class OBStatusEffects{

    public static StatusEffect dreadRust, hellburnt;

    public static void load(){

        dreadRust = new OBStatusEffect("dread-rust"){{
            color = OBPal.dreadRust;
            hideDetails = false;
            damagePercentage = 0.04f;
            healthMultiplier = 0.85f;
            reloadMultiplier = 0.85f;
            speedMultiplier = 0.75f;

            effect = OBFx.dreadRusting;
            transitionDamage = 8f;
        }};

        hellburnt = new OBStatusEffect("hellburnt"){{
            color = Color.valueOf("FFB7FD");
            damage = 0.167f;
            effect = OBFx.hellburnt;
            transitionDamage = 8f;

            init(() -> affinity(StatusEffects.tarred, (unit, result, time) -> {
                unit.damagePierce(transitionDamage);
                Fx.burning.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                result.set(hellburnt, Math.min(time + result.time, 300f));
            }));
        }};
    }
}
