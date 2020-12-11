package br.com.senior.techicaltest.vendas.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import lombok.AllArgsConstructor;
import org.junit.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityTest extends DatabaseTest {

    private static Map<String, List<EntityInfo>> mapEntityInfoList = new HashMap<>();

    @Test
    public void isTable() {
        var rule = ArchRuleDefinition.classes()
                .that().arePublic()
                .and().areAnnotatedWith(Entity.class)
                .should().beAnnotatedWith(Table.class);

        rule.check(ArchUtil.getJavaClasses());
    }

    @Test
    public void precision() {
        var condition = new ArchCondition<JavaClass>("Property value with database precision") {
            @Override
            public void check(JavaClass item, ConditionEvents events) {
                var table = item.getAnnotationOfType(Table.class);
                var databaseInfoList = getColumnListByTable(table.name());
                for (JavaField field : item.getAllFields()) {
                    if (field.isAnnotatedWith(Column.class) && field.getRawType().isAssignableFrom(BigDecimal.class)) {
                        var column = field.getAnnotationOfType(Column.class);
                        databaseInfoList.stream()
                                .filter(c -> c.column.equals(column.name()))
                                .forEach(d -> {
                                    if (d.precision != column.precision()) {
                                        var message = String.format(
                                                "Precision property %s on class %s which is in the package %s is with the value %s but in the database is %d",
                                                field.getName(),
                                                item.getSimpleName(),
                                                item.getPackageName(),
                                                column.precision(),
                                                d.precision);
                                        events.add(SimpleConditionEvent.violated(field, message));
                                    }
                                });
                    }
                }
            }
        };

        var rule = ArchRuleDefinition.classes()
                .that().arePublic()
                .and().areAnnotatedWith(Entity.class)
                .and().areAnnotatedWith(Table.class)
                .should(condition);

        rule.check(ArchUtil.getJavaClasses());
    }

    @Test
    public void scale() {
        var condition = new ArchCondition<JavaClass>("Property value scale with database") {
            @Override
            public void check(JavaClass item, ConditionEvents events) {
                var table = item.getAnnotationOfType(Table.class);
                var databaseInfoList = getColumnListByTable(table.name());
                for (JavaField field : item.getAllFields()) {
                    if (field.isAnnotatedWith(Column.class) && field.getRawType().isAssignableFrom(BigDecimal.class)) {
                        var column = field.getAnnotationOfType(Column.class);
                        databaseInfoList.stream()
                                .filter(c -> c.column.equals(column.name()))
                                .forEach(d -> {
                                    if (d.scale != column.scale()) {
                                        var message = String.format(
                                                "Property scale %s in class %s which is in package %s has this value %d but in the database is %d",
                                                field.getName(),
                                                item.getSimpleName(),
                                                item.getPackageName(),
                                                column.scale(),
                                                d.scale);
                                        events.add(SimpleConditionEvent.violated(field, message));
                                    }
                                });
                    }
                }
            }
        };

        var rule = ArchRuleDefinition.classes()
                .that().arePublic()
                .and().areAnnotatedWith(Entity.class)
                .should(condition);

        rule.check(ArchUtil.getJavaClasses());
    }

    @Test
    public void nullable() {
        var condition = new ArchCondition<JavaClass>("Property value nullabe with the database") {
            @Override
            public void check(JavaClass item, ConditionEvents events) {
                var table = item.getAnnotationOfType(Table.class);
                var databaseInfoList = getColumnListByTable(table.name());
                for (JavaField field : item.getAllFields()) {
                    if (field.isAnnotatedWith(Column.class) && !field.isAnnotatedWith(Id.class)) {
                        var column = field.getAnnotationOfType(Column.class);
                        databaseInfoList.stream()
                                .filter(c -> c.column.equals(column.name()))
                                .filter(d -> !d.isNullable && column.nullable())
                                .forEach(d -> {
                                    var message = String.format(
                                            "Property nullabe %S in class %S which is in the package %s is with the value %b but in the database is %b",
                                            field.getName(),
                                            item.getSimpleName(),
                                            item.getPackageName(),
                                            column.nullable(),
                                            d.isNullable);
                                    events.add(SimpleConditionEvent.violated(field, message));
                                });
                    }
                }
            }
        };

        var rule = ArchRuleDefinition.classes()
                .that().arePublic()
                .and().areAnnotatedWith(Entity.class)
                .should(condition);

        rule.check(ArchUtil.getJavaClasses());
    }

    @Test
    public void length() {
        var condition = new ArchCondition<JavaClass>("Property length value on database") {
            @Override
            public void check(JavaClass item, ConditionEvents events) {
                var table = item.getAnnotationOfType(Table.class);
                var databaseInfoList = getColumnListByTable(table.name());
                for (JavaField field : item.getAllFields()) {
                    if (field.isAnnotatedWith(Column.class) && field.getRawType().isAssignableFrom(String.class)) {
                        var column = field.getAnnotationOfType(Column.class);
                        databaseInfoList.stream()
                                .filter(c -> c.column.equals(column.name()))
                                .filter(d -> "varchar".equals(d.type) || "enum".equals(d.type))
                                .forEach(d -> {
                                    if (d.maxLength != column.length()) {
                                        var message = String.format(
                                                "Prperty length %s in class %s which is in the package %s is with this value %d but in the database is &d",
                                                field.getName(),
                                                item.getSimpleName(),
                                                item.getPackageName(),
                                                column.length(),
                                                d.maxLength);
                                        events.add(SimpleConditionEvent.violated(field, message));
                                    }
                                });
                    }
                }
            }
        };

        var rule = ArchRuleDefinition.classes()
                .that().arePublic()
                .and().areAnnotatedWith(Entity.class)
                .should(condition);

        rule.check(ArchUtil.getJavaClasses());
    }

    @Test
    public void lengthEqualsSize() {
        var condition = new ArchCondition<JavaClass>(
                "Property length of notation column is different from max property of size property in notation") {
            @Override
            public void check(JavaClass item, ConditionEvents events) {
                for (JavaField field : item.getAllFields()) {
                    if (field.isAnnotatedWith(Column.class)
                            && field.isAnnotatedWith(Size.class)
                            && field.getRawType().isAssignableFrom(String.class)) {
                        var column = field.getAnnotationOfType(Column.class);
                        var size = field.getAnnotationOfType(Size.class);
                        if (column.length() != size.max()) {
                            var message = String.format(
                                    "Property length % in class %s which is in the package %s is with the value %d but in the notation property size the value is %d",
                                    field.getName(),
                                    item.getSimpleName(),
                                    item.getPackageName(),
                                    column.length(),
                                    size.max());
                            events.add(SimpleConditionEvent.violated(field, message));
                        }
                    }
                }
            }
        };

        var rule = ArchRuleDefinition.classes()
                .that().arePublic()
                .and().areAnnotatedWith(Entity.class)
                .should(condition);

        rule.check(ArchUtil.getJavaClasses());
    }

    private synchronized List<EntityInfo> getColumnListByTable(String table) {
        if (mapEntityInfoList.get(table) == null) {
            String query = String.format("SELECT " +
                    "COLUMN_NAME, " +
                    "COALESCE(NUMERIC_PRECISION, 0) AS NUMERIC_PRECISION, " +
                    "COALESCE(NUMERIC_SCALE, 0) AS NUMERIC_SCALE, " +
                    "IS_NULLABLE, " +
                    "COALESCE(CHARACTER_MAXIMUM_LENGTH, 0) AS MAX_LENGTH, " +
                    "DATA_TYPE " +
                    "FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'CLIENTE_1_FISCAL' AND TABLE_NAME = '%s'", table);
            try (ResultSet resultSet = statement.executeQuery(query)) {
                List<EntityInfo> entityInfoList = new ArrayList<>();
                while (resultSet.next()) {
                    var column = resultSet.getString("COLUMN_NAME");
                    var precision = resultSet.getInt("NUMERIC_PRECISION");
                    var scale = resultSet.getInt("NUMERIC_SCALE");
                    var isNullable = "YES".equalsIgnoreCase(resultSet.getString("IS_NULLABLE").trim());
                    var maxLength = resultSet.getInt("MAX_LENGTH");
                    var type = resultSet.getString("DATA_TYPE");
                    entityInfoList.add(new EntityInfo(column, precision, scale, isNullable, maxLength, type));
                }
                mapEntityInfoList.put(table, entityInfoList);
                return entityInfoList;
            } catch (SQLException se) {
                throw new RuntimeException(se);
            }
        }
        return mapEntityInfoList.get(table);
    }

    @AllArgsConstructor
    private class EntityInfo {

        private String column;
        private int precision;
        private int scale;
        private boolean isNullable;
        private int maxLength;
        private String type;

    }

}
