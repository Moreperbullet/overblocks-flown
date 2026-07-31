package overflown.io;

import arc.util.*;
import arc.util.io.*;
import ent.anno.Annotations.*;
import mindustry.entities.abilities.*;
import overflown.entities.abilities.*;

@TypeIOHandler
public class OBTypeIo{
    
    public static void writeInvisibleA(Writes write
, Ability[] abilities, InvisibleAbility invisibleA){
        if(invisibleA != null && Structs.contains(abilities, invisibleA)){
	    write.b(1);
	}else{
	    write.b(0);
	}
    }

    public static InvisibleAbility readInvisibleA(Reads read, Ability[] abilities){
	byte existence = read.b();
	if(existence = 1){
	    for(var a : abilities) if(a instanceof InvisibleAbility i) return i;
	}
    }
}
