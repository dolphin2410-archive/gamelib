package me.dolphin2410.sample

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

object Recipes {
    val backup = ShapelessRecipe(NamespacedKey.minecraft("backup_recipe"), Items.backupToken).apply {
        this.addIngredient(Items.magicBones)
        this.addIngredient(Items.magicCrimsonRoots)
        this.addIngredient(Items.magicWarpedRoots)
        this.addIngredient(Items.magicFlesh)
        this.addIngredient(Items.magicCream)
    }

    val xray = ShapedRecipe(NamespacedKey.minecraft("xray_recipe"), Items.xray).apply {
        shape(" 0 ", "010", " 0 ")
        setIngredient('0', ItemStack(Material.GOLDEN_CARROT))
        setIngredient('1', ItemStack(Material.DIAMOND))
    }

    val mortis = ShapedRecipe(NamespacedKey.minecraft("mortis_recipe"), Items.mortisShovel).apply {
        shape("202", "212", "212")
        setIngredient('0', ItemStack(Material.COBBLESTONE))
        setIngredient('1', ItemStack(Material.STICK))
        setIngredient('2', ItemStack(Material.SMOOTH_STONE))
    }

    val thunder = ShapedRecipe(NamespacedKey.minecraft("thunder_recipe"), Items.lightningWand).apply {
        shape(" 0 ", " 1 ", " 1 ")
        setIngredient('0', ItemStack(Material.COPPER_INGOT))
        setIngredient('1', ItemStack(Material.BLAZE_ROD))
    }
}