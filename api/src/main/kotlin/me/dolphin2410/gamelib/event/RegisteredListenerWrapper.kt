package me.dolphin2410.gamelib.event

import io.github.teamcheeze.jaw.reflection.FieldAccessor
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.RegisteredListener

/**
 * An overridden Bukkit's [RegisteredListener]
 * @author dolphin2410
 * @param action The action that will be fired with the event
 */
class RegisteredListenerWrapper<T: Event>(p: Plugin, val clazz: Class<T>, action: T.()->Unit): RegisteredListener(object: Listener{},
    EventExecutorWrapper(action), EventPriority.NORMAL, p, false) {
    /**
     * A class that will be called when trying to execute from an non wrapped EventExecutor
     */
    class UnwrappedEventExecutorException(msg: String): Exception(msg)

    /**
     * Calling the event
     * @param event The event instance that is called
     */
    override fun callEvent(event: Event) {
        callEventWrapper(event)
    }

    /**
     * A wrapped call event method. Executes a overriden EventExecutor
     * @param event The event instance that is called
     */
    private fun <T: Event> callEventWrapper(event: T) {
        /**
         * This should have been called from Registered listener. My bad.
         */
        val executor = FieldAccessor(this, "executor").setDeclaringClass(RegisteredListener::class.java).get() as EventExecutor
        try {
            if (executor is EventExecutorWrapper<*>) {
                @Suppress("unchecked_cast")
                val executorWrapper = executor as EventExecutorWrapper<T>
                executorWrapper.execute(event)
            } else {
                UnwrappedEventExecutorException("You tried to cast a regular event executor to the EventExecutorWrapper").printStackTrace()
            }
        } catch (_: ClassCastException) {

        }
    }
}