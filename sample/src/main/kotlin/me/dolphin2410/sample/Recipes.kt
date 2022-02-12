package me.dolphin2410.sample

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.PigZombie
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

object Recipes {
    @JvmStatic
    val backup = ShapelessRecipe(NamespacedKey.minecraft("backup_recipe"), Items.backupToken).apply {
        addIngredient(Items.magicBones)
        addIngredient(Items.magicCrimsonRoots)
        addIngredient(Items.magicWarpedRoots)
        addIngredient(Items.magicFlesh)
        addIngredient(Items.magicCream)
    }

    @JvmStatic
    val xray = ShapedRecipe(NamespacedKey.minecraft("xray_recipe"), Items.xray).apply {
        shape(" 0 ", "010", " 0 ")
        setIngredient('0', ItemStack(Material.GOLD_INGOT))
        setIngredient('1', ItemStack(Material.DIAMOND))
    }

    @JvmStatic
    val mortis = ShapedRecipe(NamespacedKey.minecraft("mortis_recipe"), Items.mortisShovel).apply {
        shape("202", "212", "212")
        setIngredient('0', ItemStack(Material.COBBLESTONE))
        setIngredient('1', ItemStack(Material.STICK))
        setIngredient('2', ItemStack(Material.SMOOTH_STONE))
    }

    @JvmStatic
    val thunder = ShapedRecipe(NamespacedKey.minecraft("thunder_recipe"), Items.lightningWand).apply {
        shape(" 0 ", " 1 ", " 1 ")
        setIngredient('0', ItemStack(Material.COPPER_INGOT))
        setIngredient('1', ItemStack(Material.BLAZE_ROD))
    }

    @JvmStatic
    val powerBeacon = ShapedRecipe(NamespacedKey.minecraft("power_beacon"), Items.powerBeacon).apply {
        shape("ziz", "edc", "zgz")
        setIngredient('z', ItemStack(Material.GLASS))
        setIngredient('i', ItemStack(Material.IRON_INGOT))
        setIngredient('d', ItemStack(Material.DIAMOND))
        setIngredient('e', ItemStack(Material.EMERALD))
        setIngredient('g', ItemStack(Material.GOLD_INGOT))
        setIngredient('c', ItemStack(Material.COPPER_INGOT))
    }

    @JvmStatic
    val skellySword = ShapedRecipe(NamespacedKey.minecraft("skelly_sword"), Items.skellySword).apply {
        shape(" z ", "fdf", " s ")
        setIngredient('z', ItemStack(Material.ZOMBIE_HEAD))
        setIngredient('f', ItemStack(Material.FIREWORK_ROCKET))
        setIngredient('d', Material.DIAMOND_SWORD)
        setIngredient('s', ItemStack(Material.SKELETON_SKULL))
    }

    @JvmStatic
    val magicFishingRod = ShapedRecipe(NamespacedKey.minecraft("magic_fishing_rod"), Items.magicFishingRod).apply {
        shape("c c", "pfp", "c c")
        setIngredient('c', ItemStack(Material.COD))
        setIngredient('p', ItemStack(Material.LILY_PAD))
        setIngredient('f', ItemStack(Material.FISHING_ROD))
    }

    @JvmStatic
    val ultimateFurnace = ShapedRecipe(NamespacedKey.minecraft("ultimate_furnace"), Items.ultimateFurnace).apply {
        shape("ccc", "l l", "ccc")
        setIngredient('c', ItemStack(Material.COBBLESTONE))
        setIngredient('l', ItemStack(Material.LAVA_BUCKET))
    }

    @JvmStatic
    val magicCarrot = ShapedRecipe(NamespacedKey.minecraft("magic_carrot"), Items.magicGoldenCarrot).apply {
        shape(" g ", "gcg", " g ")
        setIngredient('c', ItemStack(Material.CARROT))
        setIngredient('g', ItemStack(Material.GOLD_INGOT))
    }
}