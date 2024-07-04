package help_support.v1.controllers;

import help_support.v1.servicies.SupportService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SupportController {

    private final SupportService supportService;
    public SupportController(SupportService supportService){
        this.supportService = supportService;
    }

    @PostMapping(path = "/support")
    public String addNewSupportPhrase(HttpServletRequest request) {
        return supportService.addPhrase(request.getParameter("phrase"));
    }

    @GetMapping(path = "/support")
    public String getSupportPhrase() {
        return supportService.getPhrase();
    }
}
