package help_support.v1;

import java.util.Random;
import java.util.Set;

public class SupportServiceImpl implements SupportService {

    private final SupportRepository repository;
    private final Random random = new Random();
    public SupportServiceImpl(SupportRepository repository) {
        this.repository = repository;
    }


    @Override
    public String addPhrase(String phrase) {
        repository.addPhrase(phrase);
        return "Added new phrase " + phrase;
    }

    @Override
    public String getPhrase() {
        Set<String> keys = repository.getKeys();
        int phraseInd = random.nextInt(keys.size());
        int i = 0;
        for(String key : keys)
        {
            if (i == phraseInd)
                return repository.getPhrase(key);
            i++;
        }
        return "String not found";
    }
}
