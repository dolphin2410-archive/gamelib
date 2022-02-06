package me.dolphin2410.gamelib.warp

import me.dolphin2410.gamelib.Registry
import me.dolphin2410.gamelib.game.Game
import me.dolphin2410.gamelib.util.Icon
import io.github.monun.invfx.InvFX.frame
import io.github.monun.invfx.frame.InvFrame
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

data class WarpItemImpl(override var name: Component, override val item: ItemStack) : WarpItem {
    override val games = ArrayList<Game>()
    override lateinit var inventory: InvFrame

    override fun registerGame(game: Game) {
        game.onInit()
        games.add(game)
    }

    override fun initInventory() {
        inventory = frame(6, name) {
            this.list(1, 1, 7, 4, true, { games }) {
                transform { game ->
                    val icon = if (game.hasIcon()) game.icon else Icon.DEFAULT
                    icon.item
                }
                onClickItem { _, _, (game, _), event ->
                    game.joinPlayer(event.whoClicked as Player)
                }
            }.also { list ->
                slot(3, 5) {
                    item = me.dolphin2410.gamelib.Registry.MENU_PREV
                    onClick { list.index-- }
                }
                slot(5, 5) {
                    item = me.dolphin2410.gamelib.Registry.MENU_AFTER
                    onClick { list.index++ }
                }
            }
        }
    }
}