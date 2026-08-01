package work.lqdfxnet.lqdfxspeacefulbliss.Events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Map;
import java.util.UUID;

import static work.lqdfxnet.lqdfxspeacefulbliss.Init.AttributeAggression.getAggressionMap;
import static work.lqdfxnet.lqdfxspeacefulbliss.Init.AttributeAggression.getCooldownMap;

@EventBusSubscriber
public class onMobTick {

    @SubscribeEvent
    public static void onMobTickEvent(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof Mob mob)) return;

        long now = mob.level().getGameTime();

        Map<UUID, Integer> aggrMap = getAggressionMap(mob);
        Map<UUID, Long> cdMap = getCooldownMap(mob);

        // Cooldown expiration
        cdMap.entrySet().removeIf(entry -> {
            UUID targetId = entry.getKey();
            long expire = entry.getValue();

            if (now >= expire) {
                aggrMap.put(targetId, 0);
                return true;
            }
            return false;
        });

        // Attacker died → reset aggression
        aggrMap.entrySet().removeIf(entry -> {
            UUID targetId = entry.getKey();
            Entity target = mob.level().getEntity(targetId);

            if (target == null || !target.isAlive()) {
                aggrMap.put(targetId, 0);
                cdMap.remove(targetId);
                return true;
            }
            return false;
        });

    }
}
