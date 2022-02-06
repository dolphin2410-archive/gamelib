package me.dolphin2410.gamelib.warp

import me.dolphin2410.gamelib.game.Game
import me.dolphin2410.gamelib.util.Icon
import io.github.monun.invfx.frame.InvFrame
import org.bukkit.inventory.Inventory

interface WarpItem: Icon {
    val games: ArrayList<Game>
    val inventory: InvFrame

    fun registerGame(game: Game)

    fun initInventory()
}