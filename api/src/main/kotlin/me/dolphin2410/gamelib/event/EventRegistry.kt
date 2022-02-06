package me.dolphin2410.gamelib.event

import me.dolphin2410.gamelib.GameLibAPI
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * An event registerer
 * @author dolphin2410
 */
class EventRegistry {
    companion object {

        /**
         * Listen to the event
         * @param action The action that will be fired with the event
         */
        @JvmStatic
        inline fun <reified T: Event> register(noinline action: (T)->Unit): RegisteredListenerWrapper<T> {
            return RegisteredListenerWrapper(GameLibAPI.plugin, T::class.java, action).apply {
                try {
                    val handlers = T::class.java.methods.find { it.name == "getHandlerList" }!!.apply { isAccessible = true }.invoke(null) as HandlerList
                    handlers.register(this)
                }
                catch (e: NullPointerException) {
                    e.printStackTrace()
                }
                catch (e: ClassCastException) {
                    e.printStackTrace()
                }
            }
        }
        @JvmStatic
        fun <T: Event> register(clazz: Class<T>, action: (T) -> Unit): RegisteredListenerWrapper<T> {
            return RegisteredListenerWrapper(GameLibAPI.plugin, clazz, action).apply {
                try {
                    val handlers = clazz.methods.find { it.name == "getHandlerList" }!!.apply { isAccessible = true }.invoke(null) as HandlerList
                    handlers.register(this)
                }
                catch (e: NullPointerException) {
                    e.printStackTrace()
                }
                catch (e: ClassCastException) {
                    e.printStackTrace()
                }
            }
        }

        @JvmStatic
        fun removeListener(listener: RegisteredListenerWrapper<*>) {
            val handlers = listener.clazz.methods.find { it.name == "getHandlerList" }!!.apply {
                isAccessible = true
            }.invoke(null) as HandlerList
            handlers.unregister(listener)
        }
    }
}