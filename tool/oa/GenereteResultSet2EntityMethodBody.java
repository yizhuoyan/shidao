package oa;

import com.yizhuoyan.shidao.entity.SystemRoleEntity;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class GenereteResultSet2EntityMethodBody {
    private static final Map<Class, Class> typeMappingMAP = new HashMap<Class, Class>() {{
        put(java.util.Date.class, java.sql.Timestamp.class);
        put(java.time.Instant.class, java.sql.Timestamp.class);
        put(java.time.LocalTime.class, java.sql.Time.class);
        put(java.time.LocalDate.class, java.sql.Date.class);
        put(java.time.LocalDateTime.class, java.sql.Timestamp.class);
    }};

    public static void main(String[] args) {

        run(SystemRoleEntity.class);
    }


    public static void run(Class type) {
        String className = type.getSimpleName();
        StringBuilder result = new StringBuilder();
        result.append(className).append(" e = new ").append(className).append("();\n");
        Method[] methods = type.getMethods();
        String methodName = null;
        for (Method method : methods) {
            methodName = method.getName();
            if ((methodName.startsWith("set") &&
                    method.getParameterCount() == 1)) {
                generateSetStatement(result, method);
            }
        }
        result.append("return e;");
        System.out.println(result);
    }

    private static void generateSetStatement(StringBuilder result, Method m) {
        Class type = m.getParameterTypes()[0];
        String methodName = m.getName();
        String fieldName = lowerFirstChar(methodName.substring(3));
        String conventer = null;
        String typeName = "Object";
        if (type == String.class) {
            typeName = "String";
        } else if (type == int.class || type == Integer.class) {
            typeName = "Int";
        } else if (type == boolean.class || type == Boolean.class) {
            typeName = "Boolean";
        } else if (type == long.class || type == Long.class) {
            typeName = "Long";
        } else if (type == float.class || type == Float.class) {
            typeName = "Float";
        } else if (type == double.class || type == Double.class) {
            typeName = "Double";
        } else if (type == java.util.Date.class) {
            typeName = "Timestamp";
        } else if (type == java.time.Instant.class) {
            typeName = "Timestamp";
            conventer = "timestamp2Instant";
        } else if (type == java.time.LocalDate.class) {
            typeName = "Date";
            conventer = "sqlDate2LocalDate";
        } else if (type == java.time.LocalTime.class) {
            typeName = "Time";
            conventer = "sqlTime2LocalTime";
        } else if (type == java.time.LocalDateTime.class) {
            typeName = "Timestamp";
            conventer = "timestamp2localDateTime";
        } else if (Collection.class.isAssignableFrom(type)) {
            //miss
            return;
        } else {//may be relation entity
            String relationTypeName = type.getSimpleName();
            String relationEntityName = fieldName;
            String idVarName = fieldName + "Id";
            result.append("String ").append(idVarName);
            result.append("=");
            result.append("rs.getString(\"").append(relationEntityName).append("_id\");\n");
            result.append("if(").append(idVarName).append("!=null){\n");
            result.append("\t").append(relationTypeName).append(" ").append(relationEntityName);
            result.append("= new ").append(relationTypeName).append("();\n");
            result.append("\t").append(relationEntityName).append(".setId(").append(idVarName).append(");\n");
            result.append("\t").append("e.").append(methodName);
            result.append("(").append(relationEntityName).append(");\n");
            result.append("}\n");
            return;
        }
        result.append("e.").append(methodName);
        result.append("(");
        if (conventer != null) {
            result.append(conventer).append("(");
        }
        result.append("rs.get").append(typeName);
        result.append("(\"");
        result.append(fieldName);
        result.append("\")");
        if (conventer != null) {
            result.append(")");
        }
        result.append(");\n");
    }

    static private String lowerFirstChar(String s) {
        char[] cs = s.toCharArray();
        cs[0] = Character.toLowerCase(cs[0]);
        return new String(cs);
    }

    static private String upperFirstChar(String s) {
        char[] cs = s.toCharArray();
        cs[0] = Character.toUpperCase(cs[0]);
        return new String(cs);
    }
}
