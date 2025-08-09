package overflown.world.blocks;

import mindustry.gen.*;
import mindustry.world.blocks.defense.*;

public class ReflectionWall extends Wall{
    public float damageMultiplier = 0.2f;

    public ReflectionWall(String name){
        super(name);
    }

    @Override
    public void setStats(){
        //TODO stats?
        super.setStats();
    }

    public class ReflectionWallBuild extends WallBuild{
        @Override
        public boolean collision(Bullet bullet){
            super.collision(bullet);

            if(damageMultiplier > 0f && bullet.owner instanceof Healthc c) {
                c.damage(bullet.damage() * damageMultiplier);
            }
            return true;
        }
    }
}
