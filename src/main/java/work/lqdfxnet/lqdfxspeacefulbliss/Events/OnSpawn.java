package work.lqdfxnet.lqdfxspeacefulbliss.Events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;


import work.lqdfxnet.lqdfxspeacefulbliss.Config;
import work.lqdfxnet.lqdfxspeacefulbliss.Init.AttributeAggression;

@EventBusSubscriber
public class OnSpawn {
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        EntityType<?> entityType = entity.getType();
        Level level = event.getLevel();
        BlockPos pos = event.getEntity().blockPosition();
        if (!(entity instanceof Mob mob)) return;
        EntitySpawnReason spawnReason = mob.getSpawnType();

        // No Slimes! Used for DEV SUPERFLAT WORLD TESTING
        //if (entity instanceof Slime) event.setCanceled(true);

        // Only block monsters in Overworld
        if (Config.no_surface_spawns.get()) {
            boolean worldDim = level.getBiome(pos).is(TagKey.create(Registries.BIOME, Identifier.parse("minecraft:is_overworld")));
            if (entity instanceof Skeleton || entity instanceof Zombie && worldDim && level.canSeeSky(pos) && !level.isBrightOutside()) {
                if (spawnReason == EntitySpawnReason.NATURAL) {
                    event.setCanceled(true);
                    return;
                }
            }
        }

        AttributeInstance inst = mob.getAttribute(AttributeAggression.AGGRESSION);
        if (inst == null) return;
        inst.setBaseValue(0.0); // default passive
    }

}
