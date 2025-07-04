package overflown.content;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import overflown.graphics.*;
import overflown.world.blocks.*;

import static mindustry.type.ItemStack.*;

public class OBBlocks{
    public static Block
    //enviroment
    hotCarbonStone, magmaCarbonStone, graphiticFloor, ceriseStone, redIceStone, shallowSlag, sunkenCoreZone, carbonPebbles, beryllicVent,
    redIceVent, ceriseVent, ceriseStoneWall, redGraphiticWall, ceriseBoulder,
    //other
    diseaseMines, payloadPropulsionTower, plastaniumCrusher, diseaseMixer, plastaniumDeflectWall,
    //turrets
    vampirism, devastation;

    public static void load(){

        hotCarbonStone = new Floor("hotcarbon-stone"){{
            hasColor = true;
            mapColor = Color.valueOf("5f433d");
            attributes.set(Attribute.heat, 0.5f);
            attributes.set(Attribute.water, -0.5f);
            wall = Blocks.carbonWall;
            blendGroup = Blocks.carbonStone;
            decoration = Blocks.carbonBoulder;
            variants = 3;
            emitLight = true;
            lightRadius = 30f;
            lightColor = Color.orange.cpy().a(0.15f);
        }};

        magmaCarbonStone = new Floor("magmacarbon-stone"){{
            hasColor = true;
            mapColor = Color.valueOf("855443");
            attributes.set(Attribute.heat, 0.75f);
            attributes.set(Attribute.water, -0.75f);
            wall = Blocks.carbonWall;
            blendGroup = Blocks.carbonStone;
            decoration = Blocks.carbonBoulder;
            variants = 3;
            emitLight = true;
            lightRadius = 50f;
            lightColor = Color.orange.cpy().a(0.3f);
        }};

        graphiticFloor = new Floor("graphitic-floor"){{
            mapColor = Color.valueOf("444c67");
            itemDrop = Items.graphite;
            playerUnmineable = true;
            attributes.set(Attribute.water, -0.1f);
            wall = Blocks.carbonWall;
            blendGroup = Blocks.carbonStone;
            decoration = Blocks.carbonBoulder;
            variants = 3;
        }};
        ceriseStone = new Floor("cerise-stone");

        redIceStone = new Floor("red-ice-stone"){{
            dragMultiplier = 0.6f;
            variants = 3;
            attributes.set(Attribute.water, 0.3f);
            albedo = 0.6f;
            decoration = Blocks.redIceBoulder;
            wall = Blocks.redIceWall;
        }};

        shallowSlag = new Floor("shallow-slag"){{
            hasColor = true;
            mapColor = Color.valueOf("cc5035");
            status = StatusEffects.melting;
            statusDuration = 240f;
            speedMultiplier = 0.19f;
            variants = 0;
            liquidDrop = Liquids.slag;
            isLiquid = true;
            cacheLayer = CacheLayer.slag;
            liquidMultiplier = 0.5f;
            attributes.set(Attribute.heat, 0.8f);

            emitLight = true;
            lightRadius = 30f;
            lightColor = Color.orange.cpy().a(0.38f);
        }};

        sunkenCoreZone = new Floor("sunken-core-zone"){{
            speedMultiplier = 0.8f;
            statusDuration = 50f;
            albedo = 0.9f;
            supportsOverlay = true;

            status = StatusEffects.wet;
            cacheLayer = CacheLayer.water;
            isLiquid = true;
            liquidDrop = Liquids.water;
            variants = 0;
            allowCorePlacement = true;
        }};

        carbonPebbles = new OverlayFloor("carbon-pebbles");

        beryllicVent = new SteamVent("beryllic-vent"){{
            parent = blendGroup = Blocks.beryllicStone;
            attributes.set(Attribute.steam, 1f);
        }};

        redIceVent = new SteamVent("red-ice-vent"){{
            parent = blendGroup = Blocks.redIce;
            attributes.set(Attribute.steam, 1f);
        }};

        ceriseVent = new SteamVent("cerise-vent"){{
            parent = blendGroup = ceriseStone;
            attributes.set(Attribute.steam, 1f);
        }};

        ceriseStoneWall = new StaticWall("cerise-stone-wall"){{
            ceriseStone.asFloor().wall = this;
            attributes.set(Attribute.sand, 1.1f);
        }};

        redGraphiticWall = new StaticWall("red-graphitic-wall"){{
            itemDrop = Items.graphite;
            variants = 3;
        }};

        ceriseBoulder = new Prop("cerise-boulder"){{
            variants = 2;
            ceriseStone.asFloor().decoration = this;
        }};

        diseaseMines = new GenericCrafter("disease-mines"){{
            requirements(Category.production, with(Items.copper, 25, Items.lead, 25, Items.silicon, 10));
            outputItem = new ItemStack(OBItems.diseaseFragments, 1);
            craftTime = 120;
            size = 2;
            drawer = new DrawMulti(new DrawDefault(), new DrawRegion("-rotator"){{
                spinSprite = true;
                rotateSpeed = 6;
                layer = Layer.block + 0.2f;
            }}, new DrawRegion("-top"){{
                layer = Layer.block + 0.3f;
            }});
            hasLiquids = true;
            hasPower = true;
            hasItems = true;
            craftEffect = Fx.none;

            consumePower(130f / 60f);
            consumeLiquid(Liquids.water, 6f / 60f);
        }};

        payloadPropulsionTower = new PayloadMassDriver("payload-propulsion-tower"){{
            requirements(Category.units, with(Items.thorium, 300, Items.silicon, 200, Items.plastanium, 200, Items.phaseFabric, 50));
            size = 5;
            reload = 130f;
            chargeTime = 100f;
            range = 1000f;
            maxPayloadSize = 3.5f;
            consumePower(6f);
        }};

        plastaniumCrusher = new GenericCrafter("plastanium-crusher"){{
            requirements(Category.crafting, with(Items.silicon, 120, Items.metaglass, 150, Items.graphite, 100, Items.titanium, 100, Items.plastanium, 30));
            hasItems = true;
            itemCapacity = 20;
            liquidCapacity = 60f;
            craftTime = 60f * 1.5f;
            outputItem = new ItemStack(Items.plastanium, 4);
            size = 3;
            health = 400;
            hasPower = hasLiquids = true;
            craftEffect = Fx.formsmoke;
            updateEffect = Fx.plasticburn;
            drawer = new DrawMulti(new DrawDefault(), new DrawFade());

            consumeLiquid(Liquids.oil, 0.5f);
            consumePower(4f);
            consumeItem(Items.titanium, 6);
            consumeItem(Items.coal, 2);
        }};

        diseaseMixer = new GenericCrafter("disease-mixer"){{
            requirements(Category.crafting, with(Items.copper, 50, Items.lead, 25, Items.titanium, 20));
            hasItems = true;
            hasPower = true;
            outputItem = new ItemStack(OBItems.diseaseVector, 1);

            size = 2;
            craftTime = 45f;

            consumePower(0.3f);
            consumeItems(with(OBItems.diseaseFragments, 5, Items.sporePod, 2));
        }};

        plastaniumDeflectWall = new ReflectionWall("plastanium-deflect-wall"){{
            requirements(Category.defense, ItemStack.with(Items.phaseFabric, 24, Items.plastanium, 16, Items.metaglass, 8));
            health = 200 * 4 * 4;
            size = 2;
            insulated = true;
            absorbLasers = true;
            envDisabled |= Env.scorching;
        }};

        vampirism = new ItemTurret("vampirism"){{
            requirements(Category.turret, with(Items.lead, 100, Items.silicon, 70, Items.titanium, 80));
            ammo(
                Items.lead, new SapBulletType(){{
                    sapStrength = 0.3f;
                    length = 70f;
                    damage = 23;
                    shootEffect = Fx.shootSmall;
                    hitColor = color = Color.valueOf("bf92f9");
                    despawnEffect = Fx.none;
                    width = 0.54f;
                    lifetime = 35f;
                    knockback = -1.24f;
                }},
                Items.graphite, new SapBulletType(){{
                    sapStrength = 0.6f;
                    length = 70f;
                    damage = 60;
                    shootEffect = Fx.shootSmall;
                    hitColor = color = OBPal.graphitic;
                    despawnEffect = Fx.none;
                    width = 1f;
                    lifetime = 35f;
                    knockback = -1.24f;
                    reloadMultiplier = 0.6f;
                }},
                Items.thorium, new SapBulletType(){{
                    sapStrength = 0.8f;
                    length = 120f;
                    damage = 120;
                    rangeChange = 50f;
                    shootEffect = Fx.shootSmall;
                    hitColor = color = Items.thorium.color;
                    despawnEffect = Fx.none;
                    width = 1f;
                    lifetime = 35f;
                    knockback = -2f;
                    reloadMultiplier = 0.1f;
                 }}
            );
            size = 2;
            range = 70f;
            reload = 21f;
            recoil = 2f;
            shootSound = Sounds.sap;
            shootY = 9f;
            coolant = consumeCoolant(0.1f);
        }};

        devastation = new PowerTurret("devastation"){{
            requirements(Category.turret, with(Items.titanium, 750, Items.lead, 350, Items.metaglass, 250, Items.surgeAlloy, 325, Items.silicon, 275));
            recoil = 3.5f;
            reload = 420f;
            shake = 7f;
            range = 380f;

            shoot = new ShootSpread(3, 7.5f);
            shoot.firstShotDelay = OBFx.orangeLaserCharge.lifetime;

            shootSound = Sounds.laserblast;
            chargeSound = Sounds.lasercharge;
            scaledHealth = 200;
            size = 4;
            moveWhileCharging = false;
            shootY = 6f;

            float brange = range + 10f;

            shootType = new LaserBulletType(400){{
                length = brange;
                width = 50f;

                lifetime = 65f;
                lightColor = lightningColor = Pal.powerLight;
                largeHit = true;
                hitSize = 5;
                chargeEffect = OBFx.orangeLaserCharge;

                sideAngle = 15f;
                sideWidth = 0f;
                sideLength = 0f;
                colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
                incendChance = 0.7f;
                incendSpread = 5f;
                incendAmount = 1;
                pierceArmor = true;
                status = StatusEffects.burning;
            }};
            unitSort = UnitSorts.strongest;
            coolantMultiplier = 0.6f;
            coolant = consumeCoolant(0.8f);

            consumePower(11f);
        }};
    }
}
