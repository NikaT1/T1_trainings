package help_support.v1.configuration;


import help_support.v1.SupportRepository;
import help_support.v1.SupportRepositoryInMem;
import help_support.v1.SupportService;
import help_support.v1.SupportServiceImpl;

@Configuration
public class SupportConfiguration {
    @Instance
    public SupportRepository supportRepository() {
        return new SupportRepositoryInMem();
    }

    @Instance
    public SupportService supportService() {
        return new SupportServiceImpl(supportRepository());
    }
}
