package overflown.content;

import arc.func.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.content.TechTree.*;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;

import static mindustry.content.Items.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.SectorPresets.*;
import static mindustry.content.UnitTypes.*;
import static overflown.content.OBItems.*;
import static overflown.content.OBSectorPresets.*;
import static overflown.content.OBBlocks.*;
import static overflown.content.OBUnitTypes.*;

@SuppressWarnings({"unused", "CodeBlock2Expr"})
public class OBTechTree{
    static TechTree.TechNode context = null;

    public static void load(){

        vanillaNode(plastaniumCompressor, () -> {
            node(plastaniumCrusher);
        });

        vanillaNode(pyratite, () -> {
            node(diseaseFragments, Seq.with(
                new Produce(diseaseFragments)
            ), () -> {
                node(diseaseVector, Seq.with(
                    new Produce(diseaseVector)
                ));
            });
        });

        vanillaNode(pyratiteMixer, () -> {
            node(diseaseExtractor, Seq.with(
                new SectorComplete(lifelessCanyon)
            ), () -> {
                node(diseaseMixer);
            });
        });

        vanillaNode(phaseWall, () -> {
            node(plastaniumDeflectWall, Seq.with(
                new Research(plastaniumWallLarge), 
                new Research(segment)
            ));
        });
    
        vanillaNode(massDriver, () -> {
            node(payloadPropulsionTower, Seq.with(
                new Research(payloadConveyor)
            ));
        });
 
        vanillaNode(payloadConveyor, () -> {
            node(largePayloadConveyor, () -> {
                node(payloadBuilder, Seq.with(
                    new Research(mega)
                ), () -> {
                    node(payloadBreaker);
                });
            });
        });

        vanillaNode(meltdown, () -> {
            node(devastation, Seq.with(new SectorComplete(overgrowth), new Research(plastaniumCrusher)));
        });

        vanillaNode(scorch, () -> {
            node(vampirism, Seq.with(
                new OnSector(lifelessCanyon)
            ));
        });

        vanillaNode(nova, () -> {
            node(relayer, Seq.with(
                new SectorComplete(lifelessCanyon)
            ), () -> {
                node(announcer, () -> {
                    node(agent, () -> {
                        node(attorney);
                    });
                });
            });
        });

        vanillaNode(mono, () -> {
            node(aphid, Seq.with(
                new SectorComplete(lifelessCanyon)
            ), () -> {
                node(acyrtho, () -> {
                    node(mindarus);
                });
            });
        });

        vanillaNode(biomassFacility, () -> {
            node(lifelessCanyon, Seq.with(
                new Research(titanium),
                new SectorComplete(biomassFacility)
            ));
        });
    }

    private static void vanillaNode(UnlockableContent parent, Runnable children){
        vanillaNode("serpulo", parent, children);
    }

    private static void vanillaNode(String tree, UnlockableContent parent, Runnable children){
        context = findNode(TechTree.roots.find(r -> r.name.equals(tree)), n -> n.content == parent);
        children.run();
    }

    private static TechNode findNode(TechNode root, Boolf<TechNode> filter){
        if(filter.get(root)) return root;
        for(TechNode node : root.children){
            TechNode search = findNode(node, filter);
            if(search != null) return search;
        }
        return null;
    }

    private static void rebaseNode(Content next){
        rebaseNode("serpulo", next);
    }

    /** Moves a node from its parent to the context node. */
    private static void rebaseNode(String tree, Content next){
        TechNode oldNode = findNode(TechTree.roots.find(r -> r.name.equals(tree)), n -> n.content == next);
        oldNode.parent.children.remove(oldNode);
        context.children.add(oldNode);
        oldNode.parent = context;

        if(oldNode.researchCostMultipliers != context.researchCostMultipliers){
            //Reset multipliers
            ItemStack[] req = ItemStack.copy(oldNode.requirements);
            if(oldNode.researchCostMultipliers.size > 0){
                for(ItemStack itemStack : req){
                    itemStack.amount /= (int) oldNode.researchCostMultipliers.get(itemStack.item, 1f);
                }
            }

            //Apply new multipliers
            if(context.researchCostMultipliers.size > 0){
                for(ItemStack itemStack : req){
                    itemStack.amount *= (int) context.researchCostMultipliers.get(itemStack.item, 1f);
                }
            }
            oldNode.requirements = req;
        }
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, Runnable children){
        TechTree.TechNode node = new TechTree.TechNode(context, content, requirements);
        if(objectives != null) node.objectives = objectives;

        TechTree.TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives){
        node(content, requirements, objectives, () -> {});
    }

    private static void node(UnlockableContent content, Seq<Objective> objectives){
        node(content, content.researchRequirements(), objectives, () -> {});
    }

    private static void node(UnlockableContent content, ItemStack[] requirements){
        node(content, requirements, Seq.with(), () -> {});
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children){
        node(content, requirements, null, children);
    }

    private static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives, children);
    }

    private static void node(UnlockableContent content, Runnable children){
        node(content, content.researchRequirements(), children);
    }

    private static void node(UnlockableContent block){
        node(block, () -> {});
    }
}
