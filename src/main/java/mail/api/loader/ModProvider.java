package mail.api.loader;

import java.util.Set;

/**
 * Loader API extension that provides the mods in a set of containers, as per those mods' request.
 *
 * @param <P> The type of mod prototype this mod will provide. Used internally to store mods between load phases.
 * @see Loader
 * @see ModContainer
 */
public interface ModProvider<P extends Mod.Prototype> {

    /**
     * Identifies the set of mods this provider will load.<br/>
     *
     * @param containers The set of containers that have requested this provider to handle them.
     */
    Set<P> identify(Set<ModContainer> containers);

    /**
     * Pre-loads a mod prototype by adding it to the classpath and doing all other necessary tasks before
     * all mods are loaded.
     *
     * @param prototype The mod prototype to preload.
     * @param context   The context in which the mod is being loaded.
     * @throws Exception
     */
    void preload(P prototype, Loader.ModContext context) throws Exception;

    /**
     * Loads a mod by instantiating it, subscribing it to the lifecycle event bus and doing all other necessary tasks.
     *
     * @param prototype The mod prototype to load.
     * @param context   The context in which the mod is being loaded.
     * @throws Exception
     */
    void load(P prototype, Loader.ModContext context) throws Exception;

}
