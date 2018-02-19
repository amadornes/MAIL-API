package mail.api.loader;

import java.nio.file.FileSystem;
import java.nio.file.Path;

public interface ModContainer {

    Path getPath();

    FileSystem getFiles();

    Metadata getMetadata();

    interface Metadata {

    }

}

