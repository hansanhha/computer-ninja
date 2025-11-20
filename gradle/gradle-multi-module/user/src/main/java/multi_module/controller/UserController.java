package multi_module.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ApplicationContext context;

    public UserController(ApplicationContext context) {
        this.context = context;
    }
    
    @GetMapping("/hello")
    public String[] hello() {
        return context.getBeanDefinitionNames();
    }

}
