package work.lqdfxnet.lqdfxspeacefulbliss;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

@EventBusSubscriber
// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Pacifier
    public static ModConfigSpec.BooleanValue pacifierEnabled;
    public static ModConfigSpec.BooleanValue no_surface_spawns;
    public static ModConfigSpec.ConfigValue<List<? extends String>> exclude_bosses;
    public static ModConfigSpec.ConfigValue<List<? extends String>> exclude_monsters;
    public static ModConfigSpec.ConfigValue<List<? extends String>> exclude_animals;
    public static ModConfigSpec.BooleanValue peaceful_nether;
    public static ModConfigSpec.BooleanValue peaceful_end;

    static {

        // -------------------------------------------------
        // Pacifier
        // -------------------------------------------------
        BUILDER.comment("Peaceful Bliss").push("peacefulbliss");

        pacifierEnabled = BUILDER
                .comment("Enable or disable the pacifier system")
                .define("peacefulbliss_enabled", true);

        no_surface_spawns = BUILDER
                .comment("Turn off surface spawning of Skeletons and Zombies")
                .define("no_surface_spawns", false);

        exclude_bosses = BUILDER
                .comment("Boss mobs excluded from pacification")
                .defineListAllowEmpty("exclude_boss",
                        List.of("minecraft:ender_dragon", "minecraft:wither", "minecraft:elder_guardian"), null, Config::validateEntity);

        exclude_monsters = BUILDER
                .comment("Monster mobs excluded from pacification")
                .defineListAllowEmpty("exclude_monster",
                        List.of("minecraft:enderman", "minecraft:pillager", "minecraft:vindicator",
                                "minecraft:evoker", "minecraft:ravager", "minecraft:witch"), null, Config::validateEntity);

        exclude_animals = BUILDER
                .comment("Animal mobs excluded from pacification")
                .defineListAllowEmpty("exclude_animal",
                        List.of("minecraft:wolf"), null, Config::validateEntity);

        peaceful_nether = BUILDER
                .comment("Allow pacifier system in the Nether")
                .define("in_nether", false);

        peaceful_end = BUILDER
                .comment("Allow pacifier system in the End")
                .define("in_end", false);

        BUILDER.pop();
    }

    private static boolean validateEntity(final Object obj) {
        return obj instanceof String entityName && BuiltInRegistries.ENTITY_TYPE.containsKey(Identifier.parse(entityName));
    }

    public static final ModConfigSpec SPEC = BUILDER.build();
}