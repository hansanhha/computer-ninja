package modulith;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.springframework.modulith.docs.Documenter.DiagramOptions;
import org.springframework.modulith.docs.Documenter.DiagramOptions.DiagramStyle;

public class DocumentationTest {
    
    ApplicationModules modules = ApplicationModules.of(SpringModulithApplication.class);

    @Test
    void generateC4ComponentDiagram() {
        Documenter doc = new Documenter(modules)
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml();
    }

    @Test
    void generateUMLComponentDiagrams() {
        DiagramOptions.defaults().withStyle(DiagramStyle.UML);
    }

    @Test
    void generateApplicationModuleCanvases() {
        new Documenter(modules)
            .writeModuleCanvases();
    }

    @Test
    void generateAggregateDocument() {
        new Documenter(modules)
            .writeAggregatingDocument();
    }

}
