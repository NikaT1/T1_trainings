package help_support.v1;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SupportRepositoryInMem implements SupportRepository {
    private final ConcurrentHashMap<String, String> phrases = new ConcurrentHashMap();

    public SupportRepositoryInMem(){
        addPhrase("У тебя все получится!");
        addPhrase("Не сдавайся!");
        addPhrase("Ты молодец!");
    }

    @Override
    public void addPhrase(String phrase) {
        phrases.put(UUID.randomUUID().toString(), phrase);
    }

    @Override
    public Set<String> getKeys() {
        return phrases.keySet();
    }

    @Override
    public String getPhrase(String uuid) {
        return phrases.get(uuid);
    }
}
