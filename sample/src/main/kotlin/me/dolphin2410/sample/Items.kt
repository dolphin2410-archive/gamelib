package me.dolphin2410.sample

import net.kyori.adventure.text.Component.text
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object Items {
    val mortisShovel = ItemStack(Material.STONE_SHOVEL).apply {
        editMeta { meta ->
            meta.displayName(text("Mortis Shovel"))
            meta.addEnchant(Enchantment.DAMAGE_ALL, 3, false)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }

    val lightningWand = ItemStack(Material.BLAZE_ROD).apply {
        editMeta { meta ->
            meta.displayName(text("Lightning Wand"))
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }

    val xray = ItemStack(Material.DIAMOND).apply {
        editMeta { meta ->
            meta.displayName(text("XRay"))
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }

    val backupToken = ItemStack(Material.EMERALD).apply {
        editMeta { meta ->
            meta.displayName(text("Backup Token"))
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }

    val magicCrimsonRoots = ItemStack(Material.CRIMSON_ROOTS).apply {
        editMeta { meta ->
            meta.displayName(text("Magic Crimson Roots"))
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }

    val magicWarpedRoots = ItemStack(Material.WARPED_ROOTS).apply {
        editMeta { meta ->
            meta.displayName(text("Magic Warped Roots"))
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }

    val magicCream = ItemStack(Material.MAGMA_CREAM).apply {
        editMeta { meta ->
            meta.displayName(text("Magic Magma Cream"))
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }

    val magicBones = ItemStack(Material.BONE).apply {
        editMeta { meta ->
            meta.displayName(text("Magic Bone"))
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }

    val magicFlesh = ItemStack(Material.ROTTEN_FLESH).apply {
        editMeta { meta ->
            meta.displayName(text("Magic Flesh"))
            meta.addEnchant(Enchantment.KNOCKBACK, 1, false)
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }
}