package br.com.senior.techicaltest.vendas.architecture;

import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.Test;
import org.springframework.stereotype.Repository;

public class RepositoryTest {

    @Test
    public void repositoryImpl() {
        var rule = ArchRuleDefinition.classes()
                .that().arePublic()
                .and().haveSimpleNameEndingWith("RepositoryImpl")
                .and().doNotHaveModifier(JavaModifier.ABSTRACT)
                .should().beAnnotatedWith(Repository.class);

        rule.check(ArchUtil.getJavaClasses());
    }

}
