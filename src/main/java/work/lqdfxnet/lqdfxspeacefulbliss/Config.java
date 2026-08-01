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
    public static final ModConfigSpec SPEC = BUILDER.build();
    // Pacifier
    public static ModConfigSpec.BooleanValue pacifierEnabled;
    public static ModConfigSpec.ConfigValue<List<? extends String>> pacifierExcludeBoss;
    public static ModConfigSpec.ConfigValue<List<? extends String>> pacifierExcludeMonster;
    public static ModConfigSpec.ConfigValue<List<? extends String>> pacifierExcludeAnimal;
    public static ModConfigSpec.BooleanValue pacifierInNether;
    public static ModConfigSpec.BooleanValue pacifierInEnd;

    static {

        // -------------------------------------------------
        // Pacifier
        // -------------------------------------------------
        BUILDER.comment("Pacifier system settings").push("Pacifier");

        pacifierEnabled = BUILDER
                .comment("Enable or disable the pacifier system")
                .define("pacifier_enabled", true);

        pacifierExcludeBoss = BUILDER
                .comment("Boss mobs excluded from pacification")
                .defineListAllowEmpty("pacifier_exclude_boss",
                        List.of("minecraft:ender_dragon", "minecraft:wither", "minecraft:elder_guardian"), null, Config::validateEntity);

        pacifierExcludeMonster = BUILDER
                .comment("Monster mobs excluded from pacification")
                .defineListAllowEmpty("pacifier_exclude_monster",
                        List.of("minecraft:enderman", "minecraft:pillager", "minecraft:vindicator",
                                "minecraft:evoker", "minecraft:ravager", "minecraft:witch"), null, Config::validateEntity);

        pacifierExcludeAnimal = BUILDER
                .comment("Animal mobs excluded from pacification")
                .defineListAllowEmpty("pacifier_exclude_animal",
                        List.of("minecraft:cow"), null, Config::validateEntity);

        pacifierInNether = BUILDER
                .comment("Allow pacifier system in the Nether")
                .define("pacifier_in_nether", false);

        pacifierInEnd = BUILDER
                .comment("Allow pacifier system in the End")
                .define("pacifier_in_end", false);

        BUILDER.pop();
    }

    private static boolean validateEntity(final Object obj) {
        return obj instanceof String entityName && BuiltInRegistries.ENTITY_TYPE.containsKey(Identifier.parse(entityName));
    }
}