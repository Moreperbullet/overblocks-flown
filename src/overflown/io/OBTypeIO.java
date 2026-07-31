package overflown.io;

import arc.util.*;
import arc.util.io.*;
import ent.anno.Annotations.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import overflown.entities.abilities.*;

@TypeIOHandler
public class OBTypeIO{
    
    public static void writeInvisibleA(Writes write, InvisibleAbility invisibleA){
	write.i(invisibleA.holder.id);
    }

    public static InvisibleAbility readInvisibleA(Reads read){
	byte holderId = read.i();
	Unit holder = Groups.unit.getByID(holderId);
	if(holder == null) return null;
	for(var a : holder.abilities) if(a instanceof InvisibleAbility i) return i;
    }
}
