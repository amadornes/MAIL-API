package mail.api.loader;

import mail.api.serial.DataStructure;

import java.nio.file.FileSystem;
import java.nio.file.Path;

/**
 * Object that represents a container (jar or directory) with a mod in it.
 *
 * @see Loader
 * @see ModProvider
 */
public interface ModContainer {

    /**
     * Gets the {@link Path} to the container.
     */
    Path getPath();

    /**
     * Gets a representation of the container's {@link FileSystem}.<br/>
     * To be used in combination with {@link #getFileSystemRoot()}.
     */
    FileSystem getFileSystem();

    /**
     * Gets the root {@link Path} of this container's contents inside its {@link FileSystem}.<br/>
     * To be used in combination with {@link #getFileSystem()}.
     */
    Path getFileSystemRoot();

    /**
     * Gets a parsed representation of this mod's metadata file.
     */
    DataStructure getMetadata();

}

