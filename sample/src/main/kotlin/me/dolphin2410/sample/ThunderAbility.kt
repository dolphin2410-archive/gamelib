package me.dolphin2410.sample

import me.dolphin2410.gamelib.abiity.ItemAbility
import org.bukkit.entity.LightningStrike
import org.bukkit.entity.LivingEntity
import org.bukkit.event.player.PlayerInteractEvent

class ThunderAbility: ItemAbility(Items.lightningWand.clone()) {
    override fun onInit() {
        isGlobalAbility = true
    }

    override fun onClick(e: PlayerInteractEvent) {
        val result = e.player.world.rayTraceEntities(e.player.location, e.player.eyeLocation.direction, 25.0) { it != e.player }
        val entity = result?.hitEntity ?: return
        e.item!!.amount--
        val lightning = e.player.world.spawn(entity.location, LightningStrike::class.java)
        lightning.setCausingPlayer(e.player)
        (entity as LivingEntity).damage(5.0, e.player)
    }
}