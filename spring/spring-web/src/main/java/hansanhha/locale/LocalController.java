package hansanhha.locale;


import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/locale")
public class LocalController {

    private final MessageSource messageSource;

    public LocalController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // LocaleResolver가 요청 정보의 Locale을 추출한다
    @GetMapping("/greet")
    public String greet(Locale locale) {
        return messageSource.getMessage("greeting", null, locale);
    }
}
