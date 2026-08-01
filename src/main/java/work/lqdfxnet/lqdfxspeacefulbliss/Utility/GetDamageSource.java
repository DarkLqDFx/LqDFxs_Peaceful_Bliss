package work.lqdfxnet.lqdfxspeacefulbliss.Utility;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class GetDamageSource {

    public static boolean isPlayerAttack(DamageSource source) {
        Entity direct = source.getDirectEntity();
        Entity attacker = source.getEntity();

        // Melee
        if (source.is(DamageTypes.PLAYER_ATTACK)) return true;
        if (source.is(DamageTypes.PLAYER_EXPLOSION)) return true;
        // Direct attacker
        if (attacker instanceof Player) return true;
        // Projectile owner
        if (direct instanceof Projectile proj && proj.getOwner() instanceof Player) return true;
        // Thrown items
        return direct instanceof ThrowableItemProjectile tip && tip.getOwner() instanceof Player;
    }

    public static boolean isMobAttack(DamageSource source) {
        Entity direct = source.getDirectEntity();
        Entity attacker = source.getEntity();

        // Melee
        if (source.is(DamageTypes.MOB_ATTACK)) return true;

        // Direct attacker
        if (attacker instanceof Player) return false;
        if (attacker instanceof Mob) return true;

        // Projectile/Thrown owner
        if (direct instanceof Projectile proj && proj.getOwner() instanceof Mob) return true;
        return direct instanceof ThrowableItemProjectile tip && tip.getOwner() instanceof Mob;
    }
}
