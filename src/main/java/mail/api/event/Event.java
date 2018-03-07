package mail.api.event;

import mail.api.annotations.ClientOnly;
import mail.api.annotations.ServerOnly;
import mail.api.game.Environment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An object which represents an action elsewhere in the code.
 * <p>
 * Its functional subtypes are {@link Cancelable}, {@link Generic}, {@link SideAware} and {@link WithResult}.<br/>
 * For the full list of available events, check the class hierarchy.
 * </p>
 *
 * @see EventBus
 * @see Subscribe
 */
public interface Event {

    /**
     * Marks {@link Event events} that will have a {@link EventPhase#CANCELLATION cancellation phase}.
     * <p>
     * The result of the cancellation must be returned as a {@code boolean} in the handler method.<br/>
     * The output of the previous handler (or {@code false} if it is the first one) can be obtained as a parameter
     * tagged with the {@link Result} annotation.
     * </p>
     * <p>
     * If the handler does not intend on cancelling or uncancelling the event, it must return the result of the
     * previous one.
     * </p>
     */
    interface Cancelable extends Event {
    }

    /**
     * Marks {@link Event events} that have generic arguments.
     * <p>
     * The generic types will be obtained when the event is initially being posted and cached going forward.<br/>
     * Every event handler will be tested and will only receive the event if its type arguments are the same or
     * supertypes of the ones specified by the event.<br/>
     * Using {@code <?>} as a type argument in a handler will make it accept any generic type.
     * </p>
     */
    interface Generic extends Event {

        /**
         * Checks whether or not the generic type in the specified index matches this event's requirements.
         */
        boolean matchesGenericType(Class<? extends Event.Generic> eventType, int index, Class<?> type);

    }

    /**
     * Marks {@link Event events} as happening either on the client or the server.
     * <p>
     * Discrimination on the event handler can be done through the {@link ClientOnly} and {@link ServerOnly} annotations.<br/>
     * Statically-typed sidedness provides performance improvements over dynamically-typed alternatives, so it should
     * be used whenever possible.
     * </p>
     * <p>
     * Depending on the event, the side provided may be whether the current world is clientside or serverside, or
     * whether we are running on a dedicated server or a client.<br/>
     * Always read the event's documentation if this could be unclear.
     * </p>
     */
    interface SideAware extends Event {

        /**
         * Gets the side on which the event is being fired.
         */
        Environment.Side getEventSide();

    }

    /**
     * Marks {@link Event events} that allow handlers to provide a resulting value.
     * <p>
     * Providing a result is optional, so handlers with a {@code void} return type are valid.<br/>
     * If a handler wants to provide one, the result of the event must be returned as a value of type {@code T}.<br/>
     * The output of the previous handler (or the default if it is the first one) can be obtained as a parameter
     * tagged with the {@link Result} annotation.
     * </p>
     *
     * @param <T> The type of the event's result.
     */
    interface WithResult<T> extends Event {

        /**
         * Gets the default result of the event as provided to the first handler.
         */
        T getDefaultResult();

    }

    /**
     * Marks a method as an event handler.
     * <p>
     * The first parameter must be a subtype of {@link Event}.<br/>
     * The handler will receive this and any of its subtypes.<br/>
     * Event properties can be unpacked as extra parameters using the {@link Unpack} annotation.
     * </p>
     * <p>
     * Registration to an event bus depends on whether the method is static or not:<br/>
     * If static, the class containing the method must be registered.<br/>
     * If non-static, an instance of the class containing the method must be registered.
     * </p>
     * <p>
     * The phase in which the event will be received can be determined by {@link #phase()}.<br/>
     * More information about each phase can be read in {@link EventPhase their respective documentation}.
     * </p>
     * <p>
     * An event handler may still want to receive a canceled event. If that is the case, {@link #receiveCanceled()}
     * can be set to true.
     * </p>
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Subscribe {

        /**
         * Determines which phase the handler will receive the event in.
         */
        EventPhase phase() default EventPhase.DEFAULT;

        /**
         * Determines whether the handler will receive canceled events or not.
         * <p>
         * Default: {@code false}.
         * </p>
         */
        boolean receiveCanceled() default false;

        /**
         * Determines whether the annotated method will always receive events, or only if it is overridden.
         * <p>
         * Default: {@code false}
         * </p>
         */
        boolean deferred() default false;

    }

    /**
     * Marks a method in an event as a property provider, allowing it to be unpacked in handlers.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Property {

        /**
         * The name of the property. Used when unpacking.
         */
        String value();

        /**
         * Whether the value returned by the method can be different every time it is called.
         * <p>
         * If immutable, the value will be cached the first time a handler requires it.<br/>
         * If mutable, the value will be requested every time a handler needs it.
         * </p>
         * <p>
         * Default: {@code false}.
         * </p>
         */
        boolean mutable() default false;

    }

    /**
     * Marks a handler's parameter to receive one of the event's properties.
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Unpack {

        /**
         * The name of the property to be unpacked.
         */
        String value();

    }

    /**
     * Marks a handler's parameter to receive the previous handler's result.
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Result {
    }

}
