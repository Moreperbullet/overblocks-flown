package overflown.entities;

import ent.anno.Annotations.*;
import mindustry.gen.*;
import overflown.gen.*;

@SuppressWarnings("unused")
final class EntityDefs<E>{

    @EntityDef({Unitc.class, Mechc.class, Dodgec.class}) E dodgeMechUnit;

    @EntityDef({Unitc.class, Mechc.class, Invisiblec.class}) E invisibleMechUnit;

    @EntityDef({Unitc.class, Phoenixc.class}) E phoenixUnit;
}
