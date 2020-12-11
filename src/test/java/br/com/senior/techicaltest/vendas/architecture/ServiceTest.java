package br.com.senior.techicaltest.vendas.architecture;

import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.Test;
import org.springframework.stereotype.Service;

public class ServiceTest {

    @Test
    public void serviceImpl() {
        var rule = ArchRuleDefinition.classes()
                .that().arePublic()
                .and().haveSimpleNameEndingWith("ServiceImpl")
                .and().doNotHaveModifier(JavaModifier.ABSTRACT)
                .should().beAnnotatedWith(Service.class);

        rule.check(ArchUtil.getJavaClasses());
    }

}
