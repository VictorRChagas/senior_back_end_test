package br.com.senior.techicaltest.vendas.architecture;

import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class ControllerTest {

    @Test
    public void controllerImpl() {
        var rule = ArchRuleDefinition.classes()
                .that().arePublic()
                .and().haveSimpleNameEndingWith("ControllerImpl")
                .and().doNotHaveModifier(JavaModifier.ABSTRACT)
                .should().beAnnotatedWith(Controller.class)
                .orShould().beAnnotatedWith(RestController.class);

        rule.check(ArchUtil.getJavaClasses());
    }

    @Test
    public void protectedImpl() {
        var rule = ArchRuleDefinition.classes()
                .that().arePublic()
                .and().areAnnotatedWith(Controller.class)
                .and().areAnnotatedWith(RestController.class)
                .should().beAnnotatedWith(RequestMapping.class);

        rule.check(ArchUtil.getJavaClasses());
    }

}
