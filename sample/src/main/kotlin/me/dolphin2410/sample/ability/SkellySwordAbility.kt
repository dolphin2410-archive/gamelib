package me.dolphin2410.sample.ability

import me.dolphin2410.gamelib.GameLibAPI
import me.dolphin2410.gamelib.abiity.ItemAbility
import me.dolphin2410.gamelib.util.VectorUtil.perpendicular
import me.dolphin2410.gamelib.util.VectorUtil.position
import me.dolphin2410.sample.Items
import me.dolphin2410.sample.Recipes
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.entity.Skeleton
import org.bukkit.entity.Zombie
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class SkellySwordAbility: ItemAbility(Items.skellySword) {
    override fun onInit() {
        Bukkit.getServer().addRecipe(Recipes.skellySword)
        isGlobalAbility = true
    }

    override fun onClick(e: PlayerInteractEvent) {
        if (e.player.hasCooldown(Material.DIAMOND_SWORD)) {
            e.player.sendActionBar(text().color(NamedTextColor.RED).content("You can't use this yet").build())
            return
        }
        val result = e.player.world.rayTraceEntities(e.player.location, e.player.eyeLocation.direction, 25.0) { it != e.player }
        val entity = result?.hitEntity ?: return
        if (e.action != Action.RIGHT_CLICK_AIR && e.action != Action.RIGHT_CLICK_BLOCK) return
        e.player.setCooldown(Material.DIAMOND_SWORD, 2 * 60 * 20)
        if (entity is Player) {
            for(i in 0..4) {
                val perpendicular = e.player.eyeLocation.direction.clone().multiply(-1.0).perpendicular().normalize().multiply(2.0)
                val skellyLocation = e.player.location.clone().add(perpendicular.position(i - 2)).apply {
                    y = e.player.world.getHighestBlockYAt(this).toDouble() + 1.0
                }
                val skeleton = entity.world.spawn(skellyLocation, Skeleton::class.java)
                var timer = 60
                object: BukkitRunnable() {
                    override fun run() {
                        timer--
                        skeleton.target = entity
                        if (timer == 0) {
                            skeleton.remove()
                            cancel()
                        }
                    }
                }.runTaskTimer(GameLibAPI.plugin, 0, 20)
            }

            object: BukkitRunnable() {
                override fun run() {
                    for(i in 0..4) {
                        val perpendicular = entity.eyeLocation.direction.clone().multiply(-1.0).perpendicular().normalize().multiply(2.0)
                        val zombieLocation = entity.location.clone().add(perpendicular.position(i - 2)).apply {
                            y = e.player.world.getHighestBlockYAt(this).toDouble() + 1.0
                        }
                        val zombie = entity.world.spawn(zombieLocation, Zombie::class.java)
                        zombie.equipment.helmet = ItemStack(Material.IRON_HELMET)
                        zombie.equipment.setItemInMainHand(ItemStack(Material.IRON_SWORD))
                        zombie.setAdult()
                        var timer = 60
                        object: BukkitRunnable() {
                            override fun run() {
                                timer--
                                zombie.target = entity
                                if (timer == 0) {
                                    zombie.remove()
                                    cancel()
                                }
                            }
                        }.runTaskTimer(GameLibAPI.plugin, 0, 20)
                    }
                }
            }.runTaskLater(GameLibAPI.plugin, 40)

            return
        }
    }
}