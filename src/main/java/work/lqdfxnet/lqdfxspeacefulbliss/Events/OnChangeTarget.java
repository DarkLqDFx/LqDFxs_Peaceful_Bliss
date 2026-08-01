package work.lqdfxnet.lqdfxspeacefulbliss.Events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import work.lqdfxnet.lqdfxspeacefulbliss.Utility.MobHostilityCache;
import work.lqdfxnet.lqdfxspeacefulbliss.Utility.MobHostilityOverride;

import static work.lqdfxnet.lqdfxspeacefulbliss.Init.AttributeAggression.getAggression;

@EventBusSubscriber
public class OnChangeTarget {

    @SubscribeEvent
    public static void onChangeTargetEvent(LivingChangeTargetEvent event) {
        Entity newTarget = event.getNewAboutToBeSetTarget();
        if (!(event.getEntity() instanceof Mob mob)) return;
        if (!(newTarget instanceof LivingEntity target)) return;

        Boolean override = MobHostilityOverride.getOverride(mob);

        // Hostility override (config)
        if (override != null) {
            if (!override) {
                event.setNewAboutToBeSetTarget(null);
                return;
            }
            // Forced hostile → skip natural hostility check
        } else {
            // 2. Natural hostility check (players AND mobs)
            if (!MobHostilityCache.isNaturallyHostileToEntity(mob, target)) {
                event.setNewAboutToBeSetTarget(null);
                return;
            }
        }

        int aggression = getAggression(mob, target);
        boolean canAttack = aggression == 1;

        event.setNewAboutToBeSetTarget(canAttack ? target : null);
    }
}
