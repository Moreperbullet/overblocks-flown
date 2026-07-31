package overflown.io;

import arc.util.*;
import arc.util.io.*;
import ent.anno.Annotations.*;
import mindustry.entities.abilities.*;
import overflown.entities.abilities.*;

@TypeIOHandler
public class OBTypeIO{
    
    public static void writeInvisibleA(Ability[] abilities, Writes write, InvisibleAbility invisibleA){
        if(invisibleA != null && Structs.contains(abilities, invisibleA)){
	    write.b(1);
	}else{
	    write.b(0);
	}
    }

    public static InvisibleAbility readInvisibleA(Ability[] abilities, Reads read){
	byte existence = read.b();
	if(existence == 1){
	    for(var a : abilities) if(a instanceof InvisibleAbility i) return i;
	}
	return null;
    }
}
