package mail.api.event;

/**
 * Object that handles dispatching {@link Event events} to listeners.
 *
 * @see Event
 * @see Event.Subscribe
 */
public interface EventBus {

    /**
     * Registers a listener to this event bus.
     * <p>
     * If the argument is a {@link Class}, all {@code static} event handlers in it will be registered to the bus.<br/>
     * If the argument is any other {@link Object}, all non-{@code static} event handlers in it will be registered,
     * but <b>none of the static ones</b>.
     * </p>
     */
    void register(Object listener);

    /**
     * Unregisters a listener from this event bus.
     * <p>
     * May throw an {@link IllegalStateException} if the event bus has been locked.
     * </p>
     */
    void unregister(Object listener) throws IllegalStateException;

    /**
     * Posts an {@link Event} to all the listeners in the bus.
     *
     * @return The event.
     */
    <T extends Event> T post(T event);

    /**
     * Posts an {@link Event.WithResult} to all the listeners in the bus.
     *
     * @return The event's result.
     */
    <T> T post(Event.WithResult<T> event);

    /**
     * Posts an {@link Event} to all the listeners in the bus, but firing each phase is up to the developer.
     * <p>
     * Automatically handles the {@link EventPhase#CANCELLATION cancellation phase} if necessary.
     * </p>
     *
     * @return An object that allows the developer to fire each phase of the event.
     */
    PostedEvent postManually(Event event);

    /**
     * Posts an {@link Event} to all the listeners in the bus, but firing each phase is up to the developer.
     * <p>
     * Automatically handles the {@link EventPhase#CANCELLATION cancellation phase} if necessary.
     * </p>
     *
     * @param <T> The type of the event's result.
     * @return An object that allows the developer to fire each phase of the event and get the result.
     */
    <T> PostedEvent.WithResult<T> postManually(Event.WithResult<T> event);

    /**
     * Object that handles firing each phase of an event.
     *
     * @see WithResult
     */
    interface PostedEvent {

        /**
         * Checks whether the event was canceled during the {@link EventPhase#CANCELLATION cancellation phase} (automatically fired).
         */
        boolean wasCancelled();

        /**
         * Checks whether there are any listeners that will receive this event.<br/>
         * If not, performance could be improved by not executing some code.
         */
        boolean hasListeners();

        /**
         * Fires the {@link EventPhase#PRE PRE} phase of the event.
         */
        void firePre();

        /**
         * Fires the {@link EventPhase#DEFAULT DEFAULT} phase of the event.
         */
        void fireDefault();

        /**
         * Fires the {@link EventPhase#POST POST} phase of the event.
         */
        void firePost();

        /**
         * Object that handles firing each phase of an event with a result.
         *
         * @param <T> The result type.
         * @see PostedEvent
         */
        interface WithResult<T> extends PostedEvent {

            /**
             * Gets the result of the event.
             */
            T getResult();

        }

    }

}
