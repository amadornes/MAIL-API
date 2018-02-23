package mail.api.loader;

import mail.movetolib.version.Version;

import java.util.Set;

/**
 * Object that represents a mod.
 *
 * @see Loader
 * @see ModProvider
 */
public interface Mod {

    /**
     * Gets the unique identifier of this mod.
     */
    String getModID();

    /**
     * Gets the display name of this mod.
     */
    String getName();

    /**
     * Gets the version of this mod according to the SEMVER standard.
     */
    Version getVersion();

    /**
     * Prototype for a mod used during the identification phase for compatibility purposes.
     */
    interface Prototype {

        /**
         * Gets the unique identifier of this mod.
         */
        String getModID();

        /**
         * Gets the display name of this mod.
         */
        String getName();

        /**
         * Gets the version of this mod according to the SEMVER standard.
         */
        Version getVersion();

        /**
         * Gets the set of dependencies for this mod.
         */
        Set<String> getDependencies();
    }

}
