package hansanhha.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public ApiResponse hello() {
        return new ApiResponse("hello api");
    }

    public record ApiResponse(String message) {}
    
    
}
