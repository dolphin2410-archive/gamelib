package me.dolphin2410.sample

import io.github.monun.invfx.InvFX.frame
import io.github.monun.invfx.frame.InvFrame
import io.github.monun.invfx.openFrame
import me.dolphin2410.gamelib.Registry
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

object Help {
    fun mainFrame(player: Player): InvFrame {
        return frame(1, Component.text("Help")) {
            list(1, 0, 7, 1, true, { Recipes::class.java.declaredFields.toList().filter { it.isAccessible = true; it.get(null) is Recipe } }) {
                transform { field ->
                    (field.get(null) as Recipe).result
                }
                onClickItem { _, _, (field, _), _ ->
                    player.openFrame(showRecipe(player, field.get(null) as Recipe))
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
    }

    fun showRecipe(player: Player, recipe: Recipe): InvFrame {
        return frame(6, recipe.result.displayName()) {
            pane(2, 1, 3, 3) {
                if (recipe is ShapedRecipe) {
                    val chars = ArrayList<Char?>()
                    recipe.shape.forEach { str -> chars.addAll(str.toCharArray().map { if (it == ' ') null else it }.toList()) }
                    chars.forEachIndexed { idx, item ->
                        if (item != null) {
                            item(idx % 3, idx / 3, recipe.ingredientMap[item])
                        }
                    }
                } else if (recipe is ShapelessRecipe) {
                    recipe.ingredientList.forEachIndexed { idx, item ->
                        item(idx % 3, idx / 3, item)
                    }
                }
            }

            item(6, 2, recipe.result)
            item(4, 5, Registry.MENU_PREV)
            onClick { x, y, _ ->
                if (x == 4 && y == 5) {
                    player.openFrame(mainFrame(player))
                }
            }
        }
    }
}