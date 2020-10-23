package io.github.thebusybiscuit.ecopower;

//import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.ecopower.generators.HighEnergySolarGenerator;
import io.github.thebusybiscuit.ecopower.generators.LightningReceptor;
import io.github.thebusybiscuit.ecopower.generators.LunarGenerator;
import io.github.thebusybiscuit.ecopower.generators.SteamTurbine;
import io.github.thebusybiscuit.ecopower.generators.SteamTurbineMultiblock;
import io.github.thebusybiscuit.ecopower.generators.WindTurbine;
import io.github.thebusybiscuit.ecopower.generators.WindTurbineMultiblock;
import io.github.thebusybiscuit.ecopower.items.SteelRotor;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.gadgets.SolarHelmet;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.generators.SolarGenerator;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public class EcoPowerPlugin extends JavaPlugin implements SlimefunAddon {

    @Override
    public void onEnable() {

        //new Metrics(this, 8154);

        ItemStack categoryItem = new CustomItem(SlimefunUtils.getCustomHead("240775c3ad75763613f32f04986881bbe4eee4366d0c57f17f7c7514e2d0a77d"), "&2綠能發電");
        Category category = new Category(new NamespacedKey(this, "generators"), categoryItem, 4);

        SlimefunItemStack rotor = new SlimefunItemStack("STEEL_ROTOR", "c51944b488e11cda65177d5911d651282b3012665e63b8929e1b6a4744b7ca8", "&b鋼輪子");
        
        new SteelRotor(category, rotor, new ItemStack[] {
                null, SlimefunItems.STEEL_INGOT, null,
                SlimefunItems.STEEL_INGOT, new ItemStack(Material.IRON_BLOCK), SlimefunItems.STEEL_INGOT,
                null, SlimefunItems.STEEL_INGOT, null
        }, new SlimefunItemStack(rotor, 2)).register(this);

        SteamTurbine simpleTurbine = registerSteamTurbine(category, "STEAM_TURBINE", "aefd921cb61594324f3c09d7ac7d38185d2734333968f3ac38382cddf15f6d71", "&e簡易蒸氣發電機", MachineTier.MEDIUM, 4, new ItemStack[] {
                null, rotor, null,
                SlimefunItems.STEEL_INGOT, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.STEEL_INGOT,
                null, SlimefunItems.COPPER_WIRE, null
        });
        
        SteamTurbine advancedTurbine = registerSteamTurbine(category, "STEAM_TURBINE_2", "161aad79fb748bff1e6e94d4b6a5a277cc961c1a9abfe2a4ed88baab8a2b5971", "&c進階蒸氣發電機", MachineTier.ADVANCED, 6, new ItemStack[] {
                null, rotor, null,
                SlimefunItems.BRASS_INGOT, simpleTurbine.getItem(), SlimefunItems.BRASS_INGOT,
                SlimefunItems.BRASS_INGOT, SlimefunItems.COPPER_WIRE, SlimefunItems.BRASS_INGOT
        });
        
        registerSteamTurbine(category, "STEAM_TURBINE_3", "b65e29a67860d82f66afe1060ec8a9ceacc8c7afe108f5d42f52ba854b0a62dc", "&4黑鑽石蒸氣發電機", MachineTier.END_GAME, 13, new ItemStack[] {
                null, rotor, null,
                SlimefunItems.CARBONADO, advancedTurbine.getItem(), SlimefunItems.CARBONADO,
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.REINFORCED_ALLOY_INGOT
        });

        WindTurbine simpleWindTurbine = registerWindTurbine(category, "WIND_TURBINE", "d23e4ce096e00eae6aba10d356b785c3fecc5aa3d7dad4a4a2a27ed7750df981", "&e簡易風力發電機", MachineTier.MEDIUM, 5, new ItemStack[] {
                null, rotor, null,
                SlimefunItems.STEEL_THRUSTER, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.STEEL_THRUSTER,
                null, SlimefunItems.COPPER_WIRE, null
        });

        WindTurbine advancedWindTurbine = registerWindTurbine(category, "WIND_TURBINE_2", "2df9e595dbeac33f43b37dd4ffbc234ea0fa7c3f98aad77dc906ce5d6783c79d", "&c進階風力發電機", MachineTier.ADVANCED, 11, new ItemStack[] {
                null, rotor, null,
                SlimefunItems.ELECTRO_MAGNET, simpleWindTurbine.getItem(), SlimefunItems.ELECTRO_MAGNET,
                SlimefunItems.ALUMINUM_BRASS_INGOT, SlimefunItems.COPPER_WIRE, SlimefunItems.ALUMINUM_BRASS_INGOT
        });

        registerWindTurbine(category, "WIND_TURBINE_3", "3fcef461b43f06ef9d58c94065bbf41b77a10050520b44082d5f66f6dbe71da0", "&4黑鑽石風力發電機", MachineTier.END_GAME, 23, new ItemStack[] {
                SlimefunItems.FERROSILICON, rotor, SlimefunItems.FERROSILICON,
                SlimefunItems.ELECTRIC_MOTOR, advancedWindTurbine.getItem(), SlimefunItems.ELECTRIC_MOTOR,
                SlimefunItems.CARBONADO, SlimefunItems.FERROSILICON, SlimefunItems.CARBONADO
        });
        
        registerLightningReceptor(category, "LIGHTNING_RECEPTOR", "&e閃電接收器", 512, 8192, new ItemStack[] {
                null, new ItemStack(Material.END_ROD), null,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.POWER_CRYSTAL, SlimefunItems.BLISTERING_INGOT_3,
                SlimefunItems.REINFORCED_PLATE, SlimefunItems.ENERGY_REGULATOR, SlimefunItems.REINFORCED_PLATE
        });
        
        LunarGenerator lunarGenerator = registerLunarGenerator(category, "LUNAR_GENERATOR", "&5月光發電機", 128, new ItemStack[] {
                new ItemStack(Material.PHANTOM_MEMBRANE), SlimefunItems.SOLAR_GENERATOR_4, new ItemStack(Material.PHANTOM_MEMBRANE),
                SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.CARBONADO, SlimefunItems.DAMASCUS_STEEL_INGOT,
                SlimefunItems.COPPER_WIRE, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.COPPER_WIRE
        });
        
        SolarGenerator solarGenerator = registerHighEnergySolarGenerator(category, "HIGH_ENERGY_SOLAR_GENERATOR", "c4fe135c311f7086edcc5e6dbc4ef4b23f819fddaa42f827dac46e3574de2287", "&9萬能太陽能發電機", 256, new ItemStack[] {
                SlimefunItems.SOLAR_GENERATOR_2, lunarGenerator.getItem(), SlimefunItems.SOLAR_GENERATOR_2,
                SlimefunItems.CARBONADO, SlimefunItems.POWER_CRYSTAL, SlimefunItems.CARBONADO,
                SlimefunItems.BLISTERING_INGOT_3, new ItemStack(Material.NETHER_STAR), SlimefunItems.BLISTERING_INGOT_3
        });
        
        registerSolarHelmet(category, "HIGH_ENERGY_SOLAR_HELMET", "&9萬能太陽能頭盔", 5, new ItemStack[] {
                null, solarGenerator.getItem(), null,
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.REINFORCED_ALLOY_INGOT,
                SlimefunItems.REINFORCED_ALLOY_INGOT, null, SlimefunItems.REINFORCED_ALLOY_INGOT
        });
        
        registerHighEnergySolarGenerator(category, "RADIANT_SOLAR_GENERATOR", "240775c3ad75763613f32f04986881bbe4eee4366d0c57f17f7c7514e2d0a77d", "&9輻射太陽能發電機", 512, new ItemStack[] {
                lunarGenerator.getItem(), solarGenerator.getItem(), lunarGenerator.getItem(),
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.POWER_CRYSTAL, SlimefunItems.BLISTERING_INGOT_3,
                SlimefunItems.REINFORCED_PLATE, SlimefunItems.CARBONADO, SlimefunItems.REINFORCED_PLATE
        });
    }

    private WindTurbine registerWindTurbine(Category category, String id, String texture, String name, MachineTier tier, int power, ItemStack[] recipe) {
        SlimefunItemStack turbineItem = new SlimefunItemStack(id, texture, name, "" + name + " &7的一部分");
        WindTurbine turbine = new WindTurbine(category, turbineItem, power, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        turbine.register(this);

        SlimefunItemStack multiblockItem = new SlimefunItemStack(id + "_MULTIBLOCK", texture, name + "", "", LoreBuilder.machine(tier, MachineType.GENERATOR), LoreBuilder.powerBuffer(0), LoreBuilder.powerPerSecond(power * 2));
        new WindTurbineMultiblock(category, multiblockItem, turbine).register(this);
        return turbine;
    }

    private SteamTurbine registerSteamTurbine(Category category, String id, String texture, String name, MachineTier tier, int power, ItemStack[] recipe) {
        SlimefunItemStack turbineItem = new SlimefunItemStack(id, texture, name, "" + name + " &7的一部分");
        SteamTurbine turbine = new SteamTurbine(category, turbineItem, power, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        turbine.register(this);

        SlimefunItemStack multiblockItem = new SlimefunItemStack(id + "_MULTIBLOCK", texture, name + "", "", LoreBuilder.machine(tier, MachineType.GENERATOR), LoreBuilder.powerBuffer(0), LoreBuilder.powerPerSecond(power * 2));
        new SteamTurbineMultiblock(category, multiblockItem, turbine).register(this);
        return turbine;
    }
    
    private LightningReceptor registerLightningReceptor(Category category, String id, String name, int min, int max, ItemStack[] recipe) {
        final String texture = "31a3cd9b016b1228ec01fd6f0992c64f3b9b7b29773fa46439ab3f3c8a347704";
        
        SlimefunItemStack item = new SlimefunItemStack(id, texture, name, "", "&f此機器可在雷雨天氣時", "&f從雷擊中發電", "", "", LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR), LoreBuilder.power(min, " - " + max + " J 次雷擊"));
        LightningReceptor receptor = new LightningReceptor(category, item, min, max, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        receptor.register(this);
        return receptor;
    }
    
    private LunarGenerator registerLunarGenerator(Category category, String id, String name, int power, ItemStack[] recipe) {
        final String texture = "afdd9e588d2461d2d3d058cb3e0af2b3a3367607aa14d124ed92a833f25fb112";
        SlimefunItemStack item = new SlimefunItemStack(id, texture, name, "", "&f月光發電機", "&f只會在晚上運作!", "", LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR), LoreBuilder.powerBuffer(0), LoreBuilder.powerPerSecond(power * 2));
    
        LunarGenerator generator = new LunarGenerator(category, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe, power);
        generator.register(this);
        return generator;
    }
    
    private HighEnergySolarGenerator registerHighEnergySolarGenerator(Category category, String id, String texture, String name, int power, ItemStack[] recipe) {
        SlimefunItemStack item = new SlimefunItemStack(id, texture, name, "", "&f此太陽能發電機", "&f可在任何時間發電!", "", LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR), LoreBuilder.powerBuffer(0), LoreBuilder.powerPerSecond(power * 2));
    
        HighEnergySolarGenerator generator = new HighEnergySolarGenerator(category, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe, power);
        generator.register(this);
        return generator;
    }
    
    private SolarHelmet registerSolarHelmet(Category category, String id, String name, int power, ItemStack[] recipe) {
        SlimefunItemStack item = new SlimefunItemStack(id, Material.IRON_HELMET, name, "", "&f此太陽能頭盔可充", "&f身上的裝備和手持的物品", "", LoreBuilder.power(power, "/次充電"));
        
        SolarHelmet solarHelmet = new SolarHelmet(category, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe, power);
        solarHelmet.register(this);
        return solarHelmet;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/xMikux/EcoPower/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

}
