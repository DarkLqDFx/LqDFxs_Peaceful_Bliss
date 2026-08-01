package work.lqdfxnet.lqdfxspeacefulbliss.Events;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import work.lqdfxnet.lqdfxspeacefulbliss.LqDFxsPeacefulBliss;
import work.lqdfxnet.lqdfxspeacefulbliss.Utility.MobHostilityCache;

import static work.lqdfxnet.lqdfxspeacefulbliss.Init.AttributeAggression.getCooldownMap;
import static work.lqdfxnet.lqdfxspeacefulbliss.Init.AttributeAggression.setAggression;
import static work.lqdfxnet.lqdfxspeacefulbliss.Utility.GetDamageSource.isMobAttack;
import static work.lqdfxnet.lqdfxspeacefulbliss.Utility.GetDamageSource.isPlayerAttack;

@EventBusSubscriber
public class onLivingDamage {

    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent.Pre event) {
        Entity victim = event.getEntity();
        DamageSource source = event.getSource();

        if (!(victim instanceof Mob mob)) return;

        Entity attacker = source.getEntity();
        Entity direct = source.getDirectEntity();


        LqDFxsPeacefulBliss.LOGGER.info("Pre-attacker check!?");
        if (attacker == null) {
            if (direct instanceof Projectile proj && proj.getOwner() instanceof Entity owner) {
                attacker = owner;
            }
        }
        if (attacker == null) return;
        LqDFxsPeacefulBliss.LOGGER.info("Post-attacker check!?");


        // Mob attack → aggression = 1, no cooldown
        if (isMobAttack(source)) {
            LqDFxsPeacefulBliss.LOGGER.info("Mob is the attacker!?");
            setAggression(mob, attacker, 1);
            getCooldownMap(mob).remove(attacker.getUUID());

            if (attacker instanceof LivingEntity living &&
                    MobHostilityCache.isNaturallyHostileToEntity(mob, attacker)) {
                LqDFxsPeacefulBliss.LOGGER.info("So they should attack each other!?");
                mob.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, living);
            }
        }
        // Player attack → aggression = 1 + cooldown
        else if (isPlayerAttack(source)) {
            LqDFxsPeacefulBliss.LOGGER.info("Player is the attacker!?");
            setAggression(mob, attacker, 1);

            long expire = mob.level().getGameTime() + 100;
            getCooldownMap(mob).put(attacker.getUUID(), expire);
        } else {
            LqDFxsPeacefulBliss.LOGGER.info("No one did the damage?");
        }
    }
}
