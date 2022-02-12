package me.dolphin2410.gamelib.abiity

import me.dolphin2410.gamelib.event.EventRegistry
import me.dolphin2410.gamelib.util.ConfigHolder
import me.dolphin2410.gamelib.util.ItemCompare
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.block.data.BlockData
import org.bukkit.event.Event
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockDropItemEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

abstract class BlockAbility<K, V>(val item: ItemStack): Ability {
    override var isGlobalAbility: Boolean = true

    val placedBlocks = HashMap<Block, ConfigHolder<K, V>>()

    override fun onInit() {

    }

    open fun onPlace(e: BlockPlaceEvent, itemInHand: ItemStack) {
        placedBlocks[e.block] = ConfigHolder()
    }

    open fun onBreak(e: BlockBreakEvent) {

    }

    open fun onDrop(e: BlockDropItemEvent) {
        placedBlocks.remove(e.block)
    }

    open fun onUse(e: PlayerInteractEvent, config: ConfigHolder<K, V>) {

    }

    final override fun handleEvent(e: Event) {
        if (e is BlockPlaceEvent && ItemCompare.generousMetaCompare(item.itemMeta, e.itemInHand.itemMeta)) {
            onPlace(e, e.itemInHand)
            println("placed")
        } else if (e is BlockBreakEvent && placedBlocks.containsKey(e.block)) {
            onBreak(e)
        } else if (e is PlayerInteractEvent && e.action == Action.RIGHT_CLICK_BLOCK && placedBlocks.containsKey(e.clickedBlock)) {
            onUse(e, placedBlocks[e.clickedBlock]!!)
        } else if (e is BlockDropItemEvent && placedBlocks.containsKey(e.block)) {
            onDrop(e)
        }
    }
}
