package br.com.senior.techicaltest.vendas.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

class ArchUtil {

    private static JavaClasses javaClasses;

    static JavaClasses getJavaClasses() {
        if (javaClasses == null) {
            javaClasses = new ClassFileImporter().importPackages("br.com.senior");
        }
        return javaClasses;
    }

}
