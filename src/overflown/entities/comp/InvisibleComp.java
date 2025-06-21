package overflown.entities.comp;

import arc.math.*;
import arc.util.*;
import ent.anno.Annotations.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.*;

@EntityComponent
abstract class InvisibleComp implements Unitc{
    boolean invisible;
    @ReadOnly transient float disabledTime = 120f;
    @ReadOnly transient Interval scanInterval = new Interval(2);
    @SyncLocal @SyncField(true) float alphaLerp;

    @Import UnitType type;
    @Import Team team;
    @Import float x, y, hitSize, health, maxHealth;
    @Import boolean isShooting;

    @Override
    public void update(){
        disabledTime = Math.max(disabledTime - Time.delta, 0f);

        if(scanInterval.get(0, 5f) && invisible){
            hitbox(Tmp.r1);
            Groups.bullet.intersect(Tmp.r1.x, Tmp.r1.y, Tmp.r1.width, Tmp.r1.height, b -> {
                if(b.team != team) disabledTime = 1.2f * 60;
            });
        }
        if(scanInterval.get(1, 30f)){
            float size = hitSize * 2.5f;
            Tmp.r1.setCentered(x, y, size * 2f);
            Groups.unit.intersect(Tmp.r1.x, Tmp.r1.y, Tmp.r1.width, Tmp.r1.height, u -> {
                if(u.team != team && Mathf.within(x, y, u.x, u.y, size)){
                    disabledTime = 1.2f * 60;
                }
            });
        }

        updateInvisibility(!isShooting && health > maxHealth / 2f && disabledTime <= 0f);
    }

    void updateInvisibility(boolean visible){
        alphaLerp = Mathf.approachDelta(alphaLerp, visible ? 1f : 0f, 0.01f);
        invisible = alphaLerp >= 0.5f;
    }

    @Replace(10)
    @Override
    public boolean targetable(Team targeter){
        return !invisible && type.targetable(self(), targeter);
    }

    @Replace(10)
    @Override
    public boolean hittable(){
        return !invisible && type.hittable(self());
    }
}
