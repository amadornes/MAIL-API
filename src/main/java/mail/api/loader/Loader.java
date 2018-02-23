package mail.api.loader;

import mail.api.event.EventBus;

import java.net.URL;
import java.util.Set;

/**
 * Object that handles the loading of {@link Mod mods} and modules.
 *
 * @see Mod
 * @see ModProvider
 */
public interface Loader {

    /**
     * Checks whether a {@link Mod} or module with the specified ID is loaded.
     */
    boolean isLoaded(String id);

    /**
     * Gets the set of currently loaded {@link Mod mods}.
     */
    Set<? extends Mod> getLoadedMods();

    /**
     * Object that represents the context in which a mod is being loaded.<br/>
     * Provides methods to inject sources into the classpath as well as an {@link EventBus} for lifecycle events.
     */
    interface ModContext {

        /**
         * Gets the classloader the current mod is being loaded into.
         */
        ClassLoader getClassLoader();

        /**
         * Adds the contents of the specified URL to the classpath this mod is being loaded into.
         */
        void addSources(URL url);

        /**
         * Gets the internal {@link EventBus} for lifecycle events.
         */
        EventBus getInternalEventBus();

    }

}
