package io.github.thebusybiscuit.ecopower.generators;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * This is the multiblock variant of the {@link WindTurbine}, as shown in the {@link SlimefunGuide}.
 * 
 * @author poma123
 *
 */
public class WindTurbineMultiblock extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {

    public WindTurbineMultiblock(Category category, SlimefunItemStack item, WindTurbine turbine) {
        super(category, item, RecipeType.MULTIBLOCK, new ItemStack[] {
                null, turbine.getItem(), null,
                null, new ItemStack(Material.OAK_FENCE), null,
                null, new ItemStack(Material.OAK_FENCE), null
        });
    }

    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            e.cancel();
            e.getPlayer().sendMessage("抱歉,此物品只是个模型. 你必须按照配方中的样子摆放.");
        };
    }

}
