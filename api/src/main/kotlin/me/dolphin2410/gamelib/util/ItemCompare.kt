package me.dolphin2410.gamelib.util

import org.bukkit.inventory.meta.ItemMeta

object ItemCompare {
    fun generousMetaCompare(meta: ItemMeta?, toCompare: ItemMeta?): Boolean {
        if (meta == null || toCompare == null) {
            return false
        }
        if (meta::class.java != toCompare::class.java) {
            return false
        }
        if (meta.displayName()?.equals(toCompare.displayName()) != true) {
            return false
        }
        if (meta.enchants != toCompare.enchants) {
            return false
        }
        if (meta.itemFlags != toCompare.itemFlags) {
            return false
        }
        if (meta.hasCustomModelData() && toCompare.hasCustomModelData() && meta.customModelData != toCompare.customModelData) {
            return false
        }

        return true
    }
}