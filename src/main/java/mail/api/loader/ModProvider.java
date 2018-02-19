package mail.api.loader;

import java.util.Set;

public interface ModProvider<P extends Mod.Prototype> {

    Set<P> identify(Set<ModContainer> containers);

    void load(P prototype, Loader.ModContext context);

}

