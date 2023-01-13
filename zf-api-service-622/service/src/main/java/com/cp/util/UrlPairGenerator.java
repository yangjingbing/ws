package com.cp.util;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class UrlPairGenerator {
    private static final Logger logger = LoggerFactory.getLogger(UrlPairGenerator.class);
    /**
     * null字段是否输出空字符串
     */
    private boolean outputNull;

    public UrlPairGenerator(boolean outputNull) {
        this.outputNull = outputNull;
    }

    public UrlPairGenerator() {
        this(false);
    }

    @Getter
    @Setter
    class UrlItem {
        String key;
        String value;
        int order;

        UrlItem(String key, String value, int order) {
            this.key = key;
            this.value = value;
            this.order = order;
        }
    }

    private <T> List<Pair<String, String>> generateMap(Map<String, Object> objectMap) {
        List<Pair<String, String>> pairList = new ArrayList<>();

        for (Map.Entry<String, Object> entry: objectMap.entrySet()) {
            pairList.add(new Pair<String, String>(entry.getKey(), entry.getValue().toString()));
        }

        return pairList;
    }

    public <T> List<Pair<String, String>> generate(T object) {

        if (object instanceof Map) {
            return generateMap((Map<String, Object>) object);
        }

        List<UrlItem> pairList = new ArrayList<>();

        List<Field> fields = ClassMapGenerator.getFields(object.getClass());
        for (Field field : fields) {
            if (field.getName().contains("$")) {
                logger.info("Field {} is filtered for name", field.getName());
                continue;
            }

            int order = 0;
            UrlParameter annotation = field.getAnnotation(UrlParameter.class);
            String outputKeyName = field.getName();
            if (annotation != null) {
                if (annotation.ignored()) {
                    // 此字段被忽略
                    logger.debug("Field {} is filtered for annotation", field.getName());
                    continue;
                }

                String name = annotation.name();
                order = annotation.order();
                if (!Strings.isNullOrEmpty(name)) {
                    outputKeyName = name;
                }
            }

            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (value != null) {
                pairList.add(new UrlItem(outputKeyName, value.toString(), order));
            } else {
                if (outputNull) {
                    pairList.add(new UrlItem(outputKeyName, "", order));
                }
            }
        }

        pairList.sort(Comparator.comparingInt(o -> o.order));

        return pairList
                .stream()
                .map(item -> new Pair<>(item.getKey(), item.getValue()))
                .collect(Collectors.toList());
    }

    public <T> String generateParamStr(T object) {
        List<Pair<String, String>> pairList = generate(object);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < pairList.size(); i++) {
            sb.append(pairList.get(i).getKey());
            sb.append("=");
            sb.append(pairList.get(i).getValue());
            if (i != pairList.size() - 1) {
                sb.append("&");
            }
        }

        String signSrc = sb.toString();

        return signSrc;
    }


}

class ClassMapGenerator {
    public static <T> List<Field> getFields(Class<T> cls) {
        List<Field> fieldList = new ArrayList<>();
        Map<String, Field> fieldMap = new HashMap<>();

        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields){
            if (fieldMap.containsKey(field.getName())){
                continue;
            } else {
                fieldMap.put(field.getName(), field);
            }
        }
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null) {
            getFields(superClass, fieldMap);
        }
        fieldList.addAll(fieldMap.values());
        return fieldList;
    }

    public static <T> Map<String, Field> getFields(Class<T> cls, Map<String, Field> fieldMap) {

        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields){
            if (fieldMap.containsKey(field.getName())){
                continue;
            } else {
                fieldMap.put(field.getName(), field);
            }
        }
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null) {
            getFields(superClass, fieldMap);
        }

        return fieldMap;
    }

    public static LinkedHashMap<String, String> generate(Object object) {
        LinkedHashMap<String, String> entries = new LinkedHashMap<>();
        List<Field> fields = getFields(object.getClass());
        for (Field field : fields) {
            if (field.getName().equals("this$0")) {
                continue;
            }

            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (value != null) {
                entries.put(field.getName(), value.toString());
            }
        }

        return entries;
    }
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface UrlParameter {
    String name() default "";

    int order() default 0;

    boolean ignored() default false;
}

