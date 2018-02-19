package mail.movetolib.version;

public interface Version {

    int getMajor();

    int getMinor();

    int getPatch();

    String getPreRelease();

    String getMetadata();

}

