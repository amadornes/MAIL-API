package mail.api.serial;

import java.io.IOException;
import java.io.InputStream;

public interface SerializationHandler {

    DataStructure read(InputStream inputStream) throws IOException;

}
