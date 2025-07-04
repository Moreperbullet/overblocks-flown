package overflown.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import overflown.gen.*;
import overflown.type.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;

@SuppressWarnings("unused")
public class OBUnitTypes{

    public static UnitType relayer, announcer, agent, spy, undercover;

    public static void load(){

        //region spook
        relayer = new OBUnitType("relayer", DodgeMechUnit.class){{
            dodge = 0.5f;
            speed = 0.5f;
            hitSize = 8f;
            health = 100;
            ammoType = new PowerAmmoType(1000);

            weapons.add(new Weapon(name + "-weapon"){{
                top = false;
                shootY = 2f;
                reload = 36f;
                x = 4f;
                y = 0.5f;
                alternate = false;
                ejectEffect = Fx.none;
                recoil = 2f;
                shootSound = Sounds.lasershoot;

                bullet = new LaserBoltBulletType(2.5f, 12){{
                    smokeEffect = Fx.hitLaserColor;
                    hitEffect = Fx.hitLaserColor;
                    despawnEffect = Fx.hitLaserColor;

                    lifetime = 90f;
                    backColor = lightColor = hitColor = Pal.lancerLaser;
                    frontColor = Color.white;
                }};
            }});
        }};

        announcer = new OBUnitType("announcer", DodgeMechUnit.class){{
            speed = 0.5f;
            hitSize = 10f;
            health = 350;
            armor = 4f;
            dodge = 2f / 3;
            ammoType = new ItemAmmoType(Items.graphite);

            immunities.add(StatusEffects.shocked);

            weapons.add(new Weapon(name + "-weapon"){{
                top = false;
                shootSound = Sounds.bolt;
                shootY = 2f;
                reload = 36f;
                x = 6f;
                recoil = 1f;
                rotate = true;
                rotationLimit = 50;
                rotateSpeed = 1f;
                shootCone = 60f;
                ejectEffect = Fx.none;

                bullet = new RailBulletType(){{
                    length = 130f;
                    damage = 28;
                    hitColor = Pal.lancerLaser;
                    hitEffect = endEffect = Fx.hitBulletColor;

                    smokeEffect = Fx.colorSpark;
                    status = StatusEffects.shocked;
                    statusDuration = 10f;

                    endEffect = new Effect(14f, e -> {
                        color(e.color);
                        Drawf.tri(e.x, e.y, e.fout() * 1.5f, 5f, e.rotation);
                    });

                    shootEffect = new Effect(10, e -> {
                        color(e.color);
                        float w = 1.2f + 7 * e.fout();

                        Drawf.tri(e.x, e.y, w, 30f * e.fout(), e.rotation);
                        color(e.color);

                        for(int i : Mathf.signs){
                            Drawf.tri(e.x, e.y, w * 0.9f, 18f * e.fout(), e.rotation + i * 90f);
                        }

                        Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
                    });

                    lineEffect = new Effect(20f, e -> {
                        if(!(e.data instanceof Vec2 v)) return;

                        color(e.color);
                        stroke(e.fout() * 0.9f + 0.6f);

                        Fx.rand.setSeed(e.id);
                        for(int i = 0; i < 7; i++){
                            Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                            Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
                        }

                        e.scaled(14f, b -> {
                            stroke(b.fout() * 1.5f);
                            color(e.color);
                            Lines.line(e.x, e.y, v.x, v.y);
                        });
                    });
                }};
            }});
        }};


        agent = new OBUnitType("agent", InvisibleMechUnit.class){{
            speed = 0.43f;
            hitSize = 13f;
            rotateSpeed = 3f;
            targetAir = false;
            health = 560;
            armor = 9f;
            mechFrontSway = 0.55f;
            ammoType = new PowerAmmoType(1500);

            weapons.add(new Weapon(name + "-weapon"){{
                top = false;
                y = 1f;
                x = 9f;
                reload = 20f;
                inaccuracy = 15;
                recoil = 2f;
                shake = 2f;
                ejectEffect = Fx.none;
                shootSound = Sounds.spark;
                shoot.shots = 4;
                shoot.shotDelay = 0.5f;

                bullet = new LightningBulletType(){{
                    lightningColor = hitColor = Pal.lancerLaser;
                    damage = 14f;
                    lightningLength = 8;
                    lightningLengthRand = 7;

                    lightningType = new BulletType(0.0001f, 0f){{
                        lifetime = Fx.lightning.lifetime;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        status = StatusEffects.shocked;
                        hittable = false;
                        statusDuration = 10f;
                    }};
                }};
            }});
        }};
        //endregion
    }
}
