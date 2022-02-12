package me.dolphin2410.sample.ability

import me.dolphin2410.gamelib.GameLibAPI
import me.dolphin2410.gamelib.abiity.ItemAbility
import me.dolphin2410.sample.Items
import me.dolphin2410.sample.Recipes
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Bat
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import org.bukkit.inventory.meta.Damageable

class MortisAbility: ItemAbility(Items.mortisShovel.clone()) {
    override fun onInit() {
        isGlobalAbility = true
        Bukkit.getServer().addRecipe(Recipes.mortis)
    }

    override fun onClick(e: PlayerInteractEvent) {
        e.item?.itemMeta = (e.item!!.itemMeta as Damageable).apply {
            damage += 50
            if (damage >= e.item!!.type.maxDurability) {
                e.item!!.amount--
            }
        }
        var counter = 0
        object: BukkitRunnable() {
            override fun run() {
                if (counter < 10) {
                    launchProjectile(
                        e.player.eyeLocation.direction.clone(),
                        e.player.eyeLocation.clone().add(
                            e.player.eyeLocation.direction.normalize()
                                .divide(Vector(2.0, 2.0, 2.0))),
                        e.player)
                    counter++
                }
            }
        }.runTaskTimer(GameLibAPI.plugin, 0, 3)
    }

    private fun launchProjectile(velocity: Vector, location: Location, mortis: Player) {
        val entity = location.world!!.spawn(location, Bat::class.java)
        entity.velocity = velocity.multiply(4)
        var counter = 0
        object: BukkitRunnable() {
            override fun run() {
                if(counter < 30) {
                    entity.getNearbyEntities(0.5, 0.5, 0.5).forEach {
                        if (it is LivingEntity && it != mortis && it !is Bat) {
                            it.damage(2.0, mortis)
                            entity.remove()
                            cancel()
                        }
                    }
                    counter++
                } else {
                    entity.remove()
                    cancel()
                }
            }
        }.runTaskTimer(GameLibAPI.plugin, 0, 1)
    }
}