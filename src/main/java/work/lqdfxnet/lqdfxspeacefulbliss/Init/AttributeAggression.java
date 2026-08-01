package work.lqdfxnet.lqdfxspeacefulbliss.Init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@EventBusSubscriber
public class AttributeAggression {

    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, "lqdfxspeacefulbliss");
    public static final DeferredHolder<Attribute, Attribute> AGGRESSION =
            ATTRIBUTES.register("aggression",
                    () -> new RangedAttribute(
                            "attribute.lqdfxspeacefulbliss.aggression",
                            0.0,   // default
                            0.0,   // min
                            1.0    // max
                    ).setSyncable(true)
            );
    private static final Map<UUID, Map<UUID, Integer>> perTargetAggression = new HashMap<>();
    private static final Map<UUID, Map<UUID, Long>> perTargetCooldown = new HashMap<>();

    @SubscribeEvent
    public static void addAttributes(EntityAttributeModificationEvent event) {
        //LqDFxsPeacefulBliss.LOGGER.info("Adding attributes");
        event.getTypes().forEach(type -> {
            Identifier id = EntityType.getKey(type);
            if (type.getCategory().isFriendly()) return;
            if (id != null) event.add(type, AttributeAggression.AGGRESSION);
        });
    }

    public static Map<UUID, Integer> getAggressionMap(Mob mob) {
        return perTargetAggression.computeIfAbsent(mob.getUUID(), k -> new HashMap<>());
    }

    public static Map<UUID, Long> getCooldownMap(Mob mob) {
        return perTargetCooldown.computeIfAbsent(mob.getUUID(), k -> new HashMap<>());
    }

    public static void setAggression(Mob mob, Entity target, int state) {
        getAggressionMap(mob).put(target.getUUID(), state);
    }

    public static int getAggression(Mob mob, Entity target) {
        return getAggressionMap(mob).getOrDefault(target.getUUID(), 0);
    }


}