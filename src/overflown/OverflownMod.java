package overflown;

import arc.*;
import arc.graphics.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.units.*;
import mindustry.world.blocks.units.UnitFactory.*;
import mindustry.world.meta.*;
import overflown.content.*;
import overflown.gen.*;
import overflown.graphics.*;

import static mindustry.type.ItemStack.with;

@SuppressWarnings("unused")
public class OverflownMod extends Mod{

    public OverflownMod(){
        Events.on(ClientLoadEvent.class, e -> Time.runTask(10f, () -> {
            assignColor(OBBlocks.hotCarbonStone, Color.valueOf("5f433d"));
            assignColor(OBBlocks.magmaCarbonStone, Color.valueOf("855443"));
            assignColor(OBBlocks.shallowSlag, Color.valueOf("cc5035"));
            assignColor(OBBlocks.ceriseStoneWall, Color.valueOf("DB959F"));
        }));

    }
    @Override
    public void loadContent(){
        EntityRegistry.register();
        OBStatusEffects.load();
        OBItems.load();
        OBUnitTypes.load();
        OBBlocks.load();
        OBSectorPresets.load();
        loadOther();
        progMatsCompat();
        OBTechTree.load();
    }

    public void loadOther(){
        Blocks.crystallineStoneWall.attributes.set(Attribute.sand, 0.7f);
        Blocks.arkyicWall.attributes.set(Attribute.sand, 1.1f);
        Blocks.redStone.asFloor().attributes.set(Attribute.water, -0.1f);
        Blocks.denseRedStone.asFloor().attributes.set(Attribute.water, -0.1f);
        Blocks.carbonStone.asFloor().attributes.set(Attribute.water, -0.1f);

        BulletType diseaseSpectreBullet = new BasicBulletType(7f, 60){{
            hitSize = 5;
            width = 15f;
            height = 21f;
            reloadMultiplier = 0.5f;
            frontColor = OBPal.dreadRust;
            backColor = OBPal.darkDreadRust;
            status = OBStatusEffects.dreadRust;
            shootEffect = Fx.shootBig;
            statusDuration = 270;
            pierceCap = 2;
            pierceBuilding = true;
            knockback = 0.6f;
            ammoMultiplier = 4;
        }};

        BulletType diseaseFuseBullet = new ShrapnelBulletType(){{
            length = 100;
            damage = 66f;
            ammoMultiplier = 5f;
            width = 17f;
            reloadMultiplier = 0.5f;
            toColor = OBPal.dreadRust;
            shootEffect = smokeEffect = OBFx.dreadShoot;
            status = OBStatusEffects.dreadRust;
            statusDuration = 270;
        }};

        ((ItemTurret)Blocks.spectre).ammoTypes.put(OBItems.diseaseVector, diseaseSpectreBullet);

        ((ItemTurret)Blocks.fuse).ammoTypes.put(OBItems.diseaseVector, diseaseFuseBullet);

        ((UnitFactory)Blocks.groundFactory).plans.add(
            new UnitPlan(OBUnitTypes.relayer, 60f * 30, with(Items.silicon, 20, Items.copper, 30))
        );

        ((UnitFactory)Blocks.airFactory).plans.add(new UnitPlan(
            OBUnitTypes.aphid, 60f * 30, with(Items.silicon, 30, Items.metaglass, 30))
        );

        ((Reconstructor)Blocks.additiveReconstructor).upgrades.add(
            new UnitType[]{OBUnitTypes.relayer, OBUnitTypes.announcer},
            new UnitType[]{OBUnitTypes.aphid, OBUnitTypes.acyrtho}
        );

        ((Reconstructor)Blocks.multiplicativeReconstructor).upgrades.add(
            new UnitType[]{OBUnitTypes.announcer, OBUnitTypes.agent},
            new UnitType[]{OBUnitTypes.acyrtho, OBUnitTypes.mindarus}
        );
    
        ((Reconstructor)Blocks.exponentialReconstructor).upgrades.add(
            new UnitType[]{OBUnitTypes.agent, OBUnitTypes.attorney},
            new UnitType[]{OBUnitTypes.mindarus, OBUnitTypes.rhophalo}
        );
    }
    
    public void progMatsCompat(){
        if(Vars.mods.locateMod("prog-mats") == null) return;
        Log.info("[OverflownMod] Meepscellaneous Concepts detected. Loading compatibility...");
    }

    public static void assignColor(Block block, Color color){
        if(block != null && color != null){
            block.mapColor = color;
        }
    }
}
