package me.dolphin2410.sample.ability

import io.github.monun.invfx.InvFX
import io.github.monun.invfx.openFrame
import me.dolphin2410.gamelib.Registry
import me.dolphin2410.gamelib.abiity.ItemAbility
import me.dolphin2410.sample.Items
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

class MagicAppleAbility: ItemAbility(Items.magicApple) {
    override fun onInit() {
        isGlobalAbility = true
    }

    override fun onClick(e: PlayerInteractEvent) {
        e.item!!.amount--
        val frame = InvFX.frame(1, Component.text("Choose Who to Teleport To")) {
            list(1, 0, 7, 1, true, { Bukkit.getOnlinePlayers().toList() }) {
                transform { player ->
                    val head = ItemStack(Material.PLAYER_HEAD)
                    head.editMeta { meta ->
                        meta.displayName(player.displayName())
                        (meta as SkullMeta).owningPlayer = player
                    }
                    head
                }
                onClickItem { _, _, (player, _), _ ->
                    e.player.teleport(player.location.clone().addRandom(50))
                }
            }.also { list ->
                slot(0, 0) {
                    item = Registry.MENU_PREV
                    onClick { list.index-- }
                }
                slot(8, 0) {
                    item = Registry.MENU_AFTER
                    onClick { list.index++ }
                }
            }
        }

        e.player.openFrame(frame)
    }

    fun Location.addRandom(maxRadius: Int): Location {
        return this.apply {
            val prev = this.clone()
            val random = Random()
            val randomXRadius = random.nextInt(maxRadius * 2) - maxRadius
            val randomZRadius = random.nextInt(maxRadius * 2) - maxRadius
            add(randomXRadius.toDouble(), 0.0, randomZRadius.toDouble())
            y = world.getHighestBlockYAt(x.toInt(), z.toInt()).toDouble()
            if (this.block.type == Material.LAVA) {
                return prev.addRandom(maxRadius)
            }
        }
    }
}