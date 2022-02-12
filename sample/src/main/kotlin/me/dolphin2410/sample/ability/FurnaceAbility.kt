package me.dolphin2410.sample.ability

import me.dolphin2410.gamelib.abiity.BlockAbility
import me.dolphin2410.gamelib.event.EventRegistry
import me.dolphin2410.gamelib.util.ConfigHolder
import me.dolphin2410.sample.Items
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Furnace
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockDropItemEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.inventory.FurnaceBurnEvent
import org.bukkit.event.inventory.FurnaceSmeltEvent
import org.bukkit.event.inventory.FurnaceStartSmeltEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class FurnaceAbility: BlockAbility<String, Int>(Items.ultimateFurnace) {
    override fun onPlace(e: BlockPlaceEvent, itemInHand: ItemStack) {
        super.onPlace(e, itemInHand)
        println("placed")
        placedBlocks[e.block]!!.set("uses_left", itemInHand.itemMeta.persistentDataContainer.get(NamespacedKey.minecraft("uses_left"), PersistentDataType.INTEGER) ?: 15)
    }

    override fun onDrop(e: BlockDropItemEvent) {
        println("Dropped")
        e.items.removeIf { it.itemStack.isSimilar(ItemStack(Material.FURNACE)) }
        e.items.add(e.player.world.dropItemNaturally(e.player.location, item.clone().apply {
            editMeta { meta ->
                meta.persistentDataContainer.set(NamespacedKey.minecraft("uses_left"), PersistentDataType.INTEGER, placedBlocks[e.block]!!.get("uses_left"))
            }
        }))
        super.onDrop(e)
    }

    override fun onUse(e: PlayerInteractEvent, config: ConfigHolder<String, Int>) {

    }

    override fun onInit() {
        EventRegistry.register<FurnaceStartSmeltEvent> { furnaceSmelt ->
            if (!placedBlocks.containsKey(furnaceSmelt.block)) return@register
            val config = placedBlocks[furnaceSmelt.block]!!
            val left = config.get("uses_left")

            val item = furnaceSmelt.recipe.result
            if (furnaceSmelt.source.amount > left) {
                item.amount = left
                furnaceSmelt.source.amount -= left
                config.set("uses_left", 0)
            } else {
                item.amount = furnaceSmelt.source.amount
                furnaceSmelt.source.amount = 0
                config.set("uses_left", left - furnaceSmelt.source.amount)
            }

            (furnaceSmelt.block as Furnace).inventory.result = item

            if (config.get("uses_left") <= 0) {
                // Break Furnace
                placedBlocks.remove(furnaceSmelt.block)
                furnaceSmelt.block.blockData = Material.AIR.createBlockData()
            }
        }
    }
}