package me.dolphin2410.sample.ability

import me.dolphin2410.gamelib.abiity.ItemAbility
import me.dolphin2410.sample.Items
import me.dolphin2410.sample.Recipes
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.entity.LightningStrike
import org.bukkit.entity.LivingEntity
import org.bukkit.event.player.PlayerInteractEvent

class ThunderAbility: ItemAbility(Items.lightningWand.clone()) {
    override fun onInit() {
        Bukkit.getServer().addRecipe(Recipes.thunder)
        isGlobalAbility = true
    }

    override fun onClick(e: PlayerInteractEvent) {
        val result = e.player.world.rayTraceEntities(e.player.location, e.player.eyeLocation.direction, 25.0) { it != e.player }
        val entity = result?.hitEntity ?: return
        e.item!!.amount--
        if (entity is Item) {
            if (entity.itemStack.type == Material.GOLDEN_APPLE) {
                entity.itemStack = Items.magicApple.clone().apply {
                    amount = entity.itemStack.amount
                }
            }
            return
        }
        if (entity is LivingEntity) {
            val lightning = e.player.world.spawn(entity.location, LightningStrike::class.java)
            lightning.setCausingPlayer(e.player)
            entity.damage(5.0, e.player)
        }
    }
}