package me.dolphin2410.gamelib.event

import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor

/**
 * An overridden class for Bukkit's default [EventExecutor]
 * @author dolphin2410
 */
class EventExecutorWrapper<T: Event>(private val action: (T)->Unit): EventExecutor {
    /**
     * Executing a given action
     * @param event The event that will be listened to
     */
    fun execute(event: T) {
        action.invoke(event)
    }

    /**
     * The execution method that will be called by the overridden [RegisteredListenerWrapper]
     * @param listener The empty listener that the [RegisteredListenerWrapper] will use
     * @param event The event that will be listened to
     */
    override fun execute(listener: Listener, event: Event) {
        @Suppress("unchecked_cast")
        event as T
        execute(event)
    }
}