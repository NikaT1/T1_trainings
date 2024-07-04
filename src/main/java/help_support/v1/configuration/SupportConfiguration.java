package help_support.v1.configuration;


import help_support.v1.handler.HandlerMapper;
import help_support.v1.handler.HandlerMapperImpl;
import help_support.v1.repositories.SupportRepository;
import help_support.v1.repositories.SupportRepositoryInMem;
import help_support.v1.servicies.SupportService;
import help_support.v1.servicies.SupportServiceImpl;

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

    @Instance
    public HandlerMapper handlerMapper() {
        return new HandlerMapperImpl(supportService());
    }
}
