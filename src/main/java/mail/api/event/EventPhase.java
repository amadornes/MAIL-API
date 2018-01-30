package mail.api.event;

/**
 * Indicates the phase of an {@link Event}'s lifecycle.
 */
public enum EventPhase {
    /**
     * Fired before the {@link Event}'s functional phases to check if it should be canceled.
     * <p>
     * Only fired on events that implement {@link Event.Cancelable}.<br/>
     * Others are assumed not to be canceled.
     * </p>
     * <p><b>
     * No changes should happen in this phase.<br/>
     * It should only be used to cancel/uncancel the event.
     * </b></p>
     */
    CANCELLATION,
    /**
     * First functional phase of an {@link Event}'s lifecycle.
     * <p>
     * Mainly used to set up the state before the {@link #DEFAULT} phase.
     * </p>
     */
    PRE,
    /**
     * Second (and main) functional phase of an {@link Event}'s lifecycle.
     * <p>
     * This phase is where most of the interactions should happen and is the default for all subscribers.
     * </p>
     */
    DEFAULT,
    /**
     * Last functional phase of an {@link Event}'s lifecycle.
     * <p>
     * Mainly used to reset the states altered in the {@link #PRE} phase.
     * </p>
     */
    POST
}
