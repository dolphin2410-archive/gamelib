package me.dolphin2410.gamelib.abiity

import me.dolphin2410.gamelib.util.ItemCompare
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

abstract class ItemAbility(val item: ItemStack): Ability {
    override var isGlobalAbility: Boolean = false

    override fun onInit() {

    }

    open fun onClick(e: PlayerInteractEvent) {

    }

    final override fun handleEvent(e: Event) {
        when (e) {
            is PlayerInteractEvent -> {
                if (ItemCompare.generousMetaCompare(e.item?.itemMeta, item.itemMeta)) {
                    onClick(e)
                }
            }
        }
    }
}