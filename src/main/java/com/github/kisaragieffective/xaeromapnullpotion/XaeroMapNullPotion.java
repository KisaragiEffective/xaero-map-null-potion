package com.github.kisaragieffective.xaeromapnullpotion;

import net.minecraft.crash.CrashReport;
import net.minecraft.potion.Potion;
import net.minecraft.util.ReportedException;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
@Mod(
        modid = XaeroMapNullPotion.MOD_ID,
        name = XaeroMapNullPotion.MOD_NAME,
        version = XaeroMapNullPotion.VERSION
)
public class XaeroMapNullPotion {

    public static final String MOD_ID = "xaero-map-null-potion";
    public static final String MOD_NAME = "Xaero's map compat null potion";
    public static final String VERSION = "1.0.0";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static XaeroMapNullPotion INSTANCE;
    private boolean isWorldMapAvailable;
    private boolean isMinimapAvailable;

    /**
     * This is the first initialization event. Register tile entities here.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        this.isWorldMapAvailable = Loader.instance().getActiveModList().stream().anyMatch(
                mod -> mod.getModId().equals("xaeroworldmap")
        );

        this.isMinimapAvailable = Loader.instance().getActiveModList().stream().anyMatch(
                mod -> mod.getModId().equals("xaerominimap")
        );

        Logger logger = LogManager.getLogger(XaeroMapNullPotion.MOD_ID);
        logger.info("installed >>> worldmap: {}, minimap: {}", isWorldMapAvailable, isMinimapAvailable);

        if (isWorldMapAvailable && isMinimapAvailable) {
            throw new ReportedException(
                    new CrashReport(
                            "This mod should not be installed. You have to " +
                                    "remove this mod.",
                            new RuntimeException()
                    )
            );
        }
    }

    /**
     * This is a special class that listens to registry events, to allow creation of mod blocks and items at the proper time.
     */
    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        @SubscribeEvent
        public static void register(RegistryEvent.Register<Potion> register) {
            Logger logger = LogManager.getLogger(XaeroMapNullPotion.MOD_ID);
            if (!XaeroMapNullPotion.INSTANCE.isWorldMapAvailable) {
                logger.info("Creating potion overrides. Warnings will be shown, but ignore them. THIS IS INTENDED OVERRIDES.");
                String modId = "xaeroworldmap";
                List<? extends Potion> list = Arrays.asList(
                        new NullPotion(modId, "no_world_map"),
                        new NullPotion(modId, "no_world_map_harmful"),
                        new NullPotion(modId, "no_world_map_beneficial")
                );
                register0(register, list);
            }

            if (!XaeroMapNullPotion.INSTANCE.isMinimapAvailable) {
                logger.info("Creating potion overrides. Warnings will be shown, but ignore them. THIS IS INTENDED OVERRIDES.");
                String modId = "xaerominimap";
                List<? extends Potion> list = Arrays.asList(
                        new NullPotion(modId, "no_rader"),
                        new NullPotion(modId, "no_rader_harmful"),
                        new NullPotion(modId, "no_rader_beneficial"),
                        new NullPotion(modId, "no_waypoints"),
                        new NullPotion(modId, "no_waypoints_harmful"),
                        new NullPotion(modId, "no_waypoints_beneficial"),
                        new NullPotion(modId, "no_cave_maps"),
                        new NullPotion(modId, "no_cave_maps_harmful"),
                        new NullPotion(modId, "no_cave_maps_beneficial"),
                        new NullPotion(modId, "no_minimap"),
                        new NullPotion(modId, "no_minimap_harmful"),
                        new NullPotion(modId, "no_minimap_beneficial"),
                        new NullPotion(modId, "no_entity_radar"),
                        new NullPotion(modId, "no_entity_radar_harmful"),
                        new NullPotion(modId, "no_entity_radar_beneficial")
                );
                register0(register, list);
            }
        }

        public static <T extends IForgeRegistryEntry<T>> void register0(RegistryEvent.Register<T> register, List<? extends T> ts) {
            ts.forEach(register.getRegistry()::register);
        }
    }
}
