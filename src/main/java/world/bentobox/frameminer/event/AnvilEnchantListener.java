package world.bentobox.frameminer.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import world.bentobox.frameminer.FrameMiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class AnvilEnchantListener implements Listener {

    FrameMiner addon;

    public AnvilEnchantListener(FrameMiner addon) {
        super();
        this.addon = addon;
    }

    @EventHandler
    public void openAnvil(PlayerInteractEvent event) {
        try {
            if (event.getClickedBlock() != null) {
                Material anvil = Material.ANVIL;
                Player player = event.getPlayer();
                if (event.getClickedBlock().getType() == anvil && player.isSneaking() && player.getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
                    ItemStack result = player.getItemInHand().clone();
                    ItemMeta resultMeta = result.getItemMeta();

                    ItemStack pickaxe = player.getItemInHand();

                    ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                    ItemMeta bookMeta = book.getItemMeta();

                    List<String> bookLore = new ArrayList<>(Collections.singletonList("§7Brutal I"));
                    bookMeta.setLore(bookLore);
                    book.setItemMeta(bookMeta);

                    if (pickaxe.getItemMeta() != null && resultMeta.hasLore() && player.getItemInHand().getItemMeta().getLore().contains("§7Brutal I")) {
                        return;
                    }

                    if (resultMeta != null) {
                        if (resultMeta.hasLore()) {
                            List<String> oldLore = new ArrayList<>(resultMeta.getLore());
                            List<String> newLore = new ArrayList<>(Collections.singletonList("§7Brutal I"));
                            List<String> lore = new ArrayList<>();
                            lore.addAll(newLore);
                            lore.addAll(oldLore);
                            resultMeta.setLore(lore);
                            result.setItemMeta(resultMeta);
                        } else {
                            List<String> lore = new ArrayList<>(Collections.singletonList("§7Brutal I"));
                            resultMeta.setLore(lore);
                            result.setItemMeta(resultMeta);
                        }
                    } else {
                        List<String> lore = new ArrayList<>(Collections.singletonList("§7Brutal I"));
                        resultMeta.setLore(lore);
                        result.setItemMeta(resultMeta);
                    }

                    // create merchant:
                    Merchant merchant = Bukkit.createMerchant("§8FrameMiner Anvil");

                    // setup trading recipes:
                    List<MerchantRecipe> merchantRecipes = new ArrayList<MerchantRecipe>();
                    MerchantRecipe recipe = new MerchantRecipe(result, 10000); // no max-uses limit
                    recipe.setExperienceReward(false); // no experience rewards
                    recipe.addIngredient(pickaxe);
                    recipe.addIngredient(book);
                    merchantRecipes.add(recipe);

                    // apply recipes to merchant:
                    merchant.setRecipes(merchantRecipes);

                    // open trading window:
                    player.openMerchant(merchant, true);
                }
            }
        } catch (Exception e) {
            //nothing
        }
    }

    /*@EventHandler
    public void customAnvil(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.ANVIL && event.getPlayer().getItemInHand().getType() == Material.ENCHANTED_BOOK && event.getPlayer().getItemInHand().getItemMeta().getLore().contains("§7Brutal I")) {
            event.setCancelled(true);
            HumanEntity player = event.getPlayer();
            ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
            ItemStack result = new ItemStack(Material.BARRIER);

            ItemMeta bookMeta = book.getItemMeta();

            bookMeta.setLore(Collections.singletonList("§7Brutal I"));
            book.setItemMeta(bookMeta);

            // create merchant:
            Merchant merchant = Bukkit.createMerchant("§7FrameMiner Anvil");

            // setup trading recipes:
            List<MerchantRecipe> merchantRecipes = new ArrayList<MerchantRecipe>();
            MerchantRecipe recipe = new MerchantRecipe(result, 10000); // no max-uses limit
            recipe.setExperienceReward(false); // no experience rewards
            recipe.addIngredient(pickaxe);
            recipe.addIngredient(book);
            merchantRecipes.add(recipe);

            // apply recipes to merchant:
            merchant.setRecipes(merchantRecipes);
            merch = merchant;

            // open trading window:
            player.openMerchant(merch, true);
        }
    }*/

        /*Inventory inventory = event.getInventory();
        if (inventory.getType() == InventoryType.PLAYER && event.getCursor() != null) {
            HumanEntity player = event.getWhoClicked();

            ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
            ItemStack result = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemStack air = new ItemStack(Material.AIR);

            ItemMeta pickaxeMeta = pickaxe.getItemMeta();
            ItemMeta bookMeta = book.getItemMeta();
            ItemMeta resultMeta = result.getItemMeta();

            bookMeta.setLore(Collections.singletonList("§7Brutal I"));
            book.setItemMeta(bookMeta);

            pickaxe = event.getCursor();

            pickaxe.setItemMeta(pickaxeMeta);
            result = pickaxe;

            if (resultMeta != null) {
                List<String> lore = resultMeta.getLore();
                if (lore != null) {
                    lore.add("§7Brutal I");
                }
                resultMeta.setLore(lore);
                result.setItemMeta(resultMeta);
            }

            if (event.getCurrentItem() == book && event.getCursor().getType() == Material.DIAMOND_PICKAXE && !pickaxeMeta.getLore().contains("§7Brutal I")) {
                player.setItemOnCursor(air);
                event.setCurrentItem(result);
            }
        }
    }*/

        /*addon.log("Slot 0: " + event.getInventory().getItem(0));
        addon.log("Slot 1: " + event.getInventory().getItem(1));
        try {
            Player player = (Player) event.getViewers().get(0);
            addon.log("Player: " + player);
            Inventory inv = event.getInventory();
            Material diamondPickaxe = Material.DIAMOND_PICKAXE;
            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
            ItemStack pickaxe = inv.getItem(0);
            Material slot0 = inv.getItem(0).getType();
            ItemStack slot1 = inv.getItem(1);
            ItemMeta bookMeta = book.getItemMeta();
            bookMeta.setLore(Collections.singletonList("§7Brutal I"));
            book.setItemMeta(bookMeta);
            if (slot0 == diamondPickaxe && slot1 == book && pickaxe != null) {
                addon.log("Meta: " + pickaxe.getItemMeta());
                ItemMeta meta = pickaxe.getItemMeta();
                if (meta != null) {
                    List<String> lore = pickaxe.getItemMeta().getLore();
                    if (lore != null) {
                        lore.add("§7Brutal I");
                    }
                    pickaxe.getItemMeta().setLore(lore);
                    pickaxe.setItemMeta(meta);
                    event.setResult(pickaxe);
                    player.updateInventory();
                } else {
                    event.setResult(pickaxe);
                    player.updateInventory();
                }
            }
        } catch (Exception e) {e.printStackTrace();}
    }*/
}
