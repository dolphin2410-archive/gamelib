package me.dolphin2410.sample.ability

import me.dolphin2410.gamelib.abiity.ItemAbility
import me.dolphin2410.sample.Items
import me.dolphin2410.sample.Recipes
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*

class PowerBeaconAbility: ItemAbility(Items.powerBeacon) {
    val list = arrayListOf(Items.magicApple, Items.xray, Items.lightningWand, Items.backupToken, Items.mortisShovel, Items.magicFishingRod, Items.ultimateFurnace, Items.skellySword, Items.magicGoldenCarrot)
    override fun onInit() {
        Bukkit.getServer().addRecipe(Recipes.powerBeacon)
        isGlobalAbility = true
    }

    override fun onClick(e: PlayerInteractEvent) {
        val random = list[Random().nextInt(list.size)]
        e.player.inventory.addItem(random)
        e.item!!.amount--
        e.isCancelled = true
    }
}