package work.lqdfxnet.lqdfxspeacefulbliss.Events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.monster.Slime;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import work.lqdfxnet.lqdfxspeacefulbliss.Init.AttributeAggression;

@EventBusSubscriber
public class OnSpawn {
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Mob mob)) return;
        if (entity instanceof Slime) event.setCanceled(true);
        // Ensure the mob has the Aggression attribute instance
        AttributeInstance inst = mob.getAttribute(AttributeAggression.AGGRESSION);
        if (inst == null) return;
        inst.setBaseValue(0.0); // default passive
    }
}
