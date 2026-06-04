package overflown.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import overflown.entities.abilities.*;
import overflown.gen.*;
import overflown.type.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;

@SuppressWarnings("unused")
public class OBUnitTypes{

    public static UnitType testUnit,

    relayer, announcer, agent, attorney, undercover,

    aphid, acyrtho, mindarus, rhophalo, toxoptera;

    public static void load(){

        if(true) testUnit = new OBUnitType("test-unit", PhoenixUnit.class){{
            health = 500;
            hidden = true;
            flying = true;
        }};

        //region ground spook
        relayer = new OBUnitType("relayer", DodgeMechUnit.class){{
            dodge = 0.5f;
            speed = 0.5f;
            hitSize = 8f;
            health = 100;

            weapons.add(new Weapon(name + "-weapon"){{
                top = false;
                shootY = 2f;
                reload = 36f;
                x = 4f;
                y = 0.5f;
                alternate = false;
                ejectEffect = Fx.none;
                recoil = 2f;
                shootSound = Sounds.shootLaser;

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

            immunities.add(StatusEffects.shocked);

            weapons.add(new Weapon(name + "-weapon"){{
                top = false;
                shootSound = Sounds.shootLocus;
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
                    pierceDamageFactor = 0.4f;

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
            abilities.add(new InvisibleAbility(0.01f));

            weapons.add(new Weapon(name + "-weapon"){{
                top = false;
                y = 1f;
                x = 9f;
                reload = 20f;
                inaccuracy = 15;
                recoil = 2f;
                shake = 2f;
                ejectEffect = Fx.none;
                shootSound = Sounds.shootPulsar;
                shoot.shots = 4;
                shoot.shotDelay = 0.5f;

                bullet = new LightningBulletType(){{
                    lightningColor = hitColor = Pal.lancerLaser;
                    damage = 24f;
                    lightningLength = 19;
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
        
        attorney = new OBUnitType("attorney", InvisibleMechUnit.class){{
            speed = 0.4f;
            hitSize = 21f;
            rotateSpeed = 2.1f;
            health = 7900f;
            armor = 11f;
            mechFrontSway = 1f;
            mechStepParticles = true;
            stepShake = 0.15f;
            drownTimeMultiplier = 1.4f;
            
            singleTarget = true;
            stepSound = Sounds.mechStep;
            stepSoundPitch = 0.9f;
            stepSoundVolume = 0.25f;
            abilities.add(new InvisibleAbility(0.01f));
            
            immunities = ObjectSet.with(StatusEffects.shocked, StatusEffects.electrified);
            
            weapons.add(new Weapon(name + "-weapon"){{
                top = false;
                y = 1f;
                x = 16f;
                shootY = 8f;
                reload = 45f;
                recoil = 3f;
                shake = 2f;
                ejectEffect = Fx.casing3;
                shootSound = Sounds.shootDiffuse;
                shootCone = 20f;
                inaccuracy = 0.2f;

                shoot = new ShootSpread(13, 4f);
                velocityRnd = 0.17f;

                bullet = new BasicBulletType(8f, 71){{
                    lifetime = 15f;
                    knockback = 2f;
                    width = 25f;
                    hitSize = 7f;
                    height = 20f;
                    shootEffect = Fx.shootBigColor;
                    smokeEffect = Fx.shootSmokeSquareSparse;
                    hitColor = backColor = trailColor = Pal.techBlue;
                    frontColor = Pal.lancerLaser;
                    trailWidth = 6f;
                    trailLength = 3;
                    hitEffect = despawnEffect = Fx.hitSquaresColor;

                    status = StatusEffects.shocked;
                    statusDuration = 10f;
                    lightning = 2;
                    lightningLength = 5;
                    lightningColor = Pal.lancerLaser;
                    lightningDamage = 15;
                    despawnSound = Sounds.shockBullet;
                }};
            }});
        }};
        //endregion
        
        //region insectoid air
        aphid = new OBUnitType("aphid", UnitEntity.class){{
            speed = 3f;
            accel = 0.06f;
            drag = 0.01f;
            flying = true;
            health = 120;
            engineOffset = 5.75f;
            targetFlags = new BlockFlag[]{BlockFlag.generator, BlockFlag.battery, null};
            hitSize = 9;
            itemCapacity = 15;
            rotateSpeed = 5f;
            wreckSoundVolume = 0.7f;

            moveSound = Sounds.loopThruster;
            moveSoundPitchMin = 0.3f;
            moveSoundPitchMax = 1.5f;
            moveSoundVolume = 0.2f;

            weapons.add(new Weapon(){{
                shootSound = Sounds.shootElude;
                y = 1.5f;
                x = 0f;
                mirror = false;
                reload = 50f;

                shootCone = 30f;

                shoot = new ShootSpread(3, 11f);

                bullet = new BasicBulletType(7f, 9){{
                    recoil = 0.1f;
                    homingPower = 0.2f;
                    width = 7f;
                    height = 12f;
                    lifetime = 20f;
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootBigSmoke;
                    hitColor = backColor = trailColor = Pal.sap;
                    frontColor = Color.white;
                    trailWidth = 1.5f;
                    trailLength = 5;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                }};
            }});
        }};

        acyrtho = new OBUnitType("acyrtho", UnitEntity.class){{
            armor = 3f;
            speed = 2.7f;
            accel = 0.08f;
            drag = 0.03f;
            flying = true;
            health = 350;
            engineOffset = 6f;
    
            targetFlags = new BlockFlag[]{BlockFlag.generator, BlockFlag.battery, null};
            hitSize = 11;
            itemCapacity = 15;
    
            weapons.add(new Weapon(name + "-weapon"){{
                reload = 30f;
                x = 3f;
                y = 2f;
                rotate = true;
                shake = 1f;
                inaccuracy = 2f;
                velocityRnd = 0.2f;
                shootSound = Sounds.shootMissile;

                bullet = new MissileBulletType(3f, 7){{
                    splashDamage = 21f;
                    splashDamageRadius = 18f;
                    width = 6f;
                    height = 8f;
                    shrinkY = 0f;
                    drag = -0.003f;
                    homingRange = 60f;
                    scaleKeepVelocity = true;
                    lifetime = 50f;
                    trailColor = backColor = Pal.sapBulletBack;
                    frontColor = Pal.sapBullet;
                    weaveScale = 6f;
                    weaveMag = 1f;
                }};
            }});
        }};

        mindarus = new OBUnitType("mindarus", UnitEntity.class){{
            armor = 7f;
            speed = 1.9f;
            accel = 0.09f;
            drag = 0.04f;
            flying = true;
            health = 690;
            engineOffset = 8f;
    
            targetFlags = new BlockFlag[]{BlockFlag.generator, BlockFlag.battery, null};
            hitSize = 20;
            itemCapacity = 30;
    
            weapons.add(
                new Weapon(acyrtho.name + "-weapon"){{
                reload = 70f;
                x = 7f;
                y = -2f;
                rotate = true;
                shake = 1f;
                inaccuracy = 2f;
                velocityRnd = 0.2f;
                shootSound = Sounds.shootMissile;

                bullet = new MissileBulletType(3f, 3){{
                    splashDamage = 26f;
                    splashDamageRadius = 25f;
                    width = 7f;
                    height = 9f;
                    shrinkY = 0f;
                    drag = -0.003f;
                    homingRange = 60f;
                    scaleKeepVelocity = true;
                    lifetime = 60f;
                    status = StatusEffects.sapped;
                    statusDuration = 90f;
                    trailColor = backColor = Pal.sapBulletBack;
                    frontColor = Pal.sapBullet;
                    weaveScale = 6f;
                    weaveMag = 1f;
                }};
            }},
                new Weapon(acyrtho.name + "-weapon"){{
                reload = 30f;
                x = 2f;
                y = 2f;
                rotate = true;
                shake = 1f;
                inaccuracy = 2f;
                velocityRnd = 0.2f;
                shootSound = Sounds.shootMissile;

                bullet = new MissileBulletType(5f, 3){{
                    splashDamage = 18f;
                    splashDamageRadius = 18f;
                    width = 6f;
                    height = 8f;
                    shrinkY = 0f;
                    drag = -0.003f;
                    homingRange = 60f;
                    scaleKeepVelocity = true;
                    lifetime = 37f;
                    status = StatusEffects.sapped;
                    statusDuration = 90f;
                    trailColor = backColor = Pal.sapBulletBack;
                    frontColor = Pal.sapBullet;
                    weaveScale = 6f;
                    weaveMag = 1f;
                }};
            }}
            );
        }};

        rhophalo = new OBUnitType("rhophalo", UnitEntity.class){{
            speed = 0.9f;
            accel = 0.03f;
            drag = 0.03f;
            rotateSpeed = 1.9f;
            flying = true;
            lowAltitude = true;
            health = 7300;
            armor = 8f;
            engineOffset = 19;
            engineSize = 5.3f;
            hitSize = 46f;
            targetFlags = new BlockFlag[]{BlockFlag.reactor, BlockFlag.generator, BlockFlag.core, null};

            loopSound = Sounds.loopHover;

            weapons.add(
            new Weapon(name + "-salvo"){{
                y = 3f;
                x = 18f;
                reload = 30f;
                shoot.shots = 4;
                shoot.shotDelay = 3f;
                ejectEffect = Fx.casing1;
                rotateSpeed = 8f;
                bullet = new BasicBulletType(3.5f, 23){{
                    width = 9f;
                    height = 12f;
                    lifetime = 60f;

                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    hitColor = backColor = trailColor = Pal.sapBulletBack;
                    frontColor = Pal.sapBullet;
                    status = StatusEffects.sapped;
                    statusDuration = 30f;
                }};
                shootSound = Sounds.shootSalvo;
                rotate = true;
                shadow = 6f;
            }}
            );
        }};
        //endregion
    }
}
