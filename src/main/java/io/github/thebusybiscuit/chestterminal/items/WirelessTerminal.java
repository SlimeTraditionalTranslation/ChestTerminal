package io.github.thebusybiscuit.chestterminal.items;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun4.core.attributes.Rechargeable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;

public abstract class WirelessTerminal extends SimpleSlimefunItem<ItemUseHandler> implements Rechargeable {

    public WirelessTerminal(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    public abstract int getRange();

    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            e.getInteractEvent().setCancelled(true);
            ItemStack stack = e.getItem();
            ItemMeta im = stack.getItemMeta();
            List<String> lore = im.getLore();

            if (lore.isEmpty()) {
                return;
            }

            if (e.getClickedBlock().isPresent() && e.getSlimefunBlock().isPresent()) {
                Player p = e.getPlayer();
                Block b = e.getClickedBlock().get();

                if (e.getSlimefunBlock().get() instanceof AccessTerminal) {
                    lore.set(0, ChatColors.color("&8\u21E8 &7連結至: &8") + b.getWorld().getName() + " X: " + b.getX() + " Y: " + b.getY() + " Z: " + b.getZ());
                    p.sendMessage(ChatColors.color("&b已建立連結!"));
                    im.setLore(lore);
                    stack.setItemMeta(im);
                    p.getInventory().setItemInMainHand(stack);
                } else {
                    openRemoteTerminal(p, stack, lore.get(0), getRange());
                }

                e.cancel();
            } else {
                openRemoteTerminal(e.getPlayer(), stack, lore.get(0), getRange());
            }
        };
    }

    private void openRemoteTerminal(Player p, ItemStack stack, String loc, int range) {
        if (loc.equals(ChatColors.color("&8\u21E8 &7連結至: &c無處"))) {
            p.sendMessage(ChatColors.color("&4失敗 &c- 此裝置未正確的連接至箱子終端!"));
            return;
        }

        loc = loc.replace(ChatColors.color("&8\u21E8 &7連結至: &8"), "");
        World world = Bukkit.getWorld(loc.split(" X: ")[0]);

        if (world == null) {
            p.sendMessage(ChatColors.color("&4失敗 &c- 此設備連接至的箱子終端已不存在!"));
            return;
        }

        int x = Integer.parseInt(loc.split(" X: ")[1].split(" Y: ")[0]);
        int y = Integer.parseInt(loc.split(" Y: ")[1].split(" Z: ")[0]);
        int z = Integer.parseInt(loc.split(" Z: ")[1]);

        Block block = world.getBlockAt(x, y, z);

        // Support for protection plugins (fixes #45)
        if (!SlimefunPlugin.getProtectionManager().hasPermission(p, block.getLocation(), ProtectableAction.INTERACT_BLOCK)) {
            p.sendMessage(ChatColors.color("&4你沒有權限在該區域訪問此終端!"));
            return;
        }

        if (!BlockStorage.check(block, "CHEST_TERMINAL")) {
            p.sendMessage(ChatColors.color("&4失敗 &c- 此設備連接至的箱子終端已不存在!"));
            return;
        }

        float charge = getItemCharge(stack);
        if (charge < 0.5F) {
            p.sendMessage(ChatColors.color("&4失敗 &c- 此終端已沒電!"));
            return;
        }

        if (range > 0 && !world.getUID().equals(p.getWorld().getUID())) {
            p.sendMessage(ChatColors.color("&4失敗 &c- 你已超出可連線範圍!"));
            return;
        }
        if (range > 0 && block.getLocation().distance(p.getLocation()) > range) {
            p.sendMessage(ChatColors.color("&4失敗 &c- 你已超出可連線範圍!"));
            return;
        }

        removeItemCharge(stack, 0.5F);
        BlockStorage.getInventory(block).open(p);
    }

}
