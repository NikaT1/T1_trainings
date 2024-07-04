package help_support.v1.repositories;

import java.util.Set;
import java.util.UUID;

public interface SupportRepository {
    void addPhrase(String phrase);

    Set<String> getKeys();

    String getPhrase(String uuid);
}
