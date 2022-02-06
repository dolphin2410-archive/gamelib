package me.dolphin2410.gamelib.abiity

import me.dolphin2410.gamelib.event.EventRegistry
import org.bukkit.block.data.BlockData
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent

abstract class BlockAbility(val block: BlockData): Ability {
    override var isGlobalAbility: Boolean = true
    val events = HashMap<Class<out Event>, ArrayList<(Event) -> Unit>>()

    override fun onInit() {

    }

    inline fun <reified T: Event> registerEvent(noinline event: (T) -> Unit) {
        events.putIfAbsent(T::class.java, ArrayList())
        events[T::class.java]!!.add {
            event(it as T)
        }

        EventRegistry.register<T> {
            handleEvent(it)
        }
    }

    final override fun handleEvent(e: Event) {
        events[e::class.java]?.forEach {
            it(e)
        }
    }
}
