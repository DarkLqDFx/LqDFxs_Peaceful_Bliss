package work.lqdfxnet.lqdfxspeacefulbliss.Utility;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import work.lqdfxnet.lqdfxspeacefulbliss.Config;

public class MobHostilityOverride {

    public static Boolean getOverride(Mob mob) {
        EntityType<?> type = mob.getType();
        Identifier id = EntityType.getKey(type);

        // Just in case!
        if (id == null) return null;

        // Passive override
        if (Config.exclude_animals.get().contains(id.toString())) {
            return Boolean.FALSE;
        }

        // Hostile override
        if (Config.exclude_monsters.get().contains(id.toString())) {
            return Boolean.TRUE;
        }

        if (Config.exclude_bosses.get().contains(id.toString())) {
            return Boolean.TRUE;
        }

        return null; // No override
    }
}
