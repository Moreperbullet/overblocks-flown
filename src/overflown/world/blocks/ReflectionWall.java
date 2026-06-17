package overflown.world.blocks;

import arc.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.*;
import overflown.world.meta.*;

public class ReflectionWall extends Wall{
    public float reflectMod = 0.2f, reflectCap = 280f;

    public ReflectionWall(String name){
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(OBStats.special, Core.bundle.get("special-reflection"));
        stats.addPercent(OBStats.reflectModNum, reflectMod);
        stats.add(OBStats.reflectModCap, reflectCap, StatUnit.none);
    }

    public class ReflectionWallBuild extends WallBuild{
        @Override
        public boolean collision(Bullet bullet){
            super.collision(bullet);
            float finalDamage = Math.min(bulletDamage() * reflectMod, reflectCap)

            if(damageMultiplier > 0f && bullet.owner instanceof Healthc c) {
                c.damage(finalDamage);
            }
            return true;
        }
    }
}
