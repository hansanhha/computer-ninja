package modulith;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModuleStructureTest {
    
    @Test
    void verifyModuleStructure() {
        var modules = ApplicationModules.of(SpringModulithApplication.class);
        modules.forEach(System.out::println);
        modules.verify();
    }

    @Test
    void detectViolations() {
        ApplicationModules.of(SpringModulithApplication.class)
        .detectViolations()
        .filter(Objects::nonNull)
        .throwIfPresent();
    }

}
