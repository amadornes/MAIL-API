package mail.api.annotations;

import mail.api.event.Event;
import mail.api.game.Environment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks its target as only being relevant on the {@link Environment.Side#SERVER}.
 * <p>
 * Clashes with {@link ClientOnly}.
 * </p>
 * <p>
 * Currently only used by {@link Event events} for internal optimizations.
 * </p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServerOnly {
}
