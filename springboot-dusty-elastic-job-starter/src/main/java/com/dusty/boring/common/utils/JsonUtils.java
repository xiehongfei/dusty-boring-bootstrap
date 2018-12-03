/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年04月20日 16:12
 * @Copyright ©2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * {@link com.fasterxml.jackson.core.JsonParser.Feature} 枚举说明
 * <p>
 * 这个特性，决定了解析器是否将自动关闭那些不属于parser自己的输入源。 如果禁止，则调用应用不得不分别去关闭那些被用来创建parser的基础输入流InputStream和reader；
 * 如果允许，parser只要自己需要获取closed方法（当遇到输入流结束，或者parser自己调用 JsonParder#close方法），就会处理流关闭。
 * <p>
 * 注意：这个属性默认是true，即允许自动关闭流
 * <p>
 * AUTO_CLOSE_SOURCE(true),
 * <p>
 * // // // Support for non-standard data format constructs
 * <p>
 * 该特性决定parser将是否允许解析使用Java/C++ 样式的注释（包括'/'+'*' 和'//' 变量）。 由于JSON标准说明书上面没有提到注释是否是合法的组成，所以这是一个非标准的特性；
 * 尽管如此，这个特性还是被广泛地使用。
 * <p>
 * 注意：该属性默认是false，因此必须显式允许，即通过JsonParser.Feature.ALLOW_COMMENTS 配置为true。
 * <p>
 * <p>
 * ALLOW_COMMENTS(false),
 * <p>
 * 这个特性决定parser是否将允许使用非双引号属性名字， （这种形式在Javascript中被允许，但是JSON标准说明书中没有）。
 * <p>
 * 注意：由于JSON标准上需要为属性名称使用双引号，所以这也是一个非标准特性，默认是false的。
 * 同样，需要设置JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES为true，打开该特性。
 * <p>
 * <p>
 * ALLOW_UNQUOTED_FIELD_NAMES(false),
 * <p>
 * 该特性决定parser是否允许单引号来包住属性名称和字符串值。
 * <p>
 * 注意：默认下，该属性也是关闭的。需要设置JsonParser.Feature.ALLOW_SINGLE_QUOTES为true
 * <p>
 * <p>
 * ALLOW_SINGLE_QUOTES(false),
 * <p>
 * 该特性决定parser是否允许JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）。 如果该属性关闭，则如果遇到这些字符，则会抛出异常。
 * JSON标准说明书要求所有控制符必须使用引号，因此这是一个非标准的特性。
 * <p>
 * 注意：默认时候，该属性关闭的。需要设置：JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS为true。
 * <p>
 * <p>
 * ALLOW_UNQUOTED_CONTROL_CHARS(false),
 * <p>
 * 该特性可以允许接受所有引号引起来的字符，使用‘反斜杠\’机制：如果不允许，只有JSON标准说明书中 列出来的字符可以被避开约束。
 * <p>
 * 由于JSON标准说明中要求为所有控制字符使用引号，这是一个非标准的特性，所以默认是关闭的。
 * <p>
 * 注意：一般在设置ALLOW_SINGLE_QUOTES属性时，也设置了ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER属性，
 * 所以，有时候，你会看到不设置ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER为true，但是依然可以正常运行。
 *
 * @since 1.6
 * <p>
 * ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER(false),
 * <p>
 * 该特性决定parser是否允许JSON整数以多个0开始(比如，如果000001赋值给json某变量，
 * 如果不设置该属性，则解析成int会抛异常报错：org.codehaus.jackson.JsonParseException: Invalid numeric value: Leading zeroes not
 * allowed)
 * <p>
 * 注意：该属性默认是关闭的，如果需要打开，则设置JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS为true。
 * @since 1.8
 * <p>
 * ALLOW_NUMERIC_LEADING_ZEROS(false),
 * <p>
 * 该特性允许parser可以识别"Not-a-Number" (NaN)标识集合作为一个合法的浮点数。 例如： allows (tokens are quoted contents, not including
 * quotes):
 * <ul>
 * <li>"INF" (for positive infinity), as well as alias of "Infinity"
 * <li>"-INF" (for negative infinity), alias "-Infinity"
 * <li>"NaN" (for other not-a-numbers, like result of division by zero)
 * </ul>
 * <p>
 * <p>
 * ALLOW_NON_NUMERIC_NUMBERS(false),
 * <p>
 * // // // Controlling canonicalization (interning etc)
 * <p>
 * 该特性决定JSON对象属性名称是否可以被String#intern 规范化表示。
 * <p>
 * 如果允许，则JSON所有的属性名将会 intern() ；如果不设置，则不会规范化，
 * <p>
 * 默认下，该属性是开放的。此外，必须设置CANONICALIZE_FIELD_NAMES为true
 * <p>
 * 关于intern方法作用：当调用 intern 方法时，如果池已经包含一个等于此 String 对象的字符串 （该对象由 equals(Object) 方法确定），则返回池中的字符串。否则，将此 String
 * 对象添加到池中， 并且返回此 String 对象的引用。
 * @since 1.3
 * <p>
 * INTERN_FIELD_NAMES(true),
 * <p>
 * 该特性决定JSON对象的属性名称是否被规范化。
 * @since 1.5
 * <p>
 * CANONICALIZE_FIELD_NAMES(true)
 */

/**
 * <Json工具类>
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2018年04月20日 16:12
 * @version: V1.0
 * @review: xiehongfei[humphrey@gmail.com]/2018年04月20日 16:12
 */
@Slf4j
public class JsonUtils {

    //private static Logger

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //允许使用反斜杠('\')机制
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        //允许使用非双引号属性字段，即：非标准json结构，如：{ name:"zhang san", gender="female" }
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, Boolean.TRUE);
        //允许
        objectMapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, Boolean.TRUE);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, Boolean.TRUE);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        //解决转义字符异常
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY, false);
        
    }


    /**
     * <pre>
     *     实体Bean转字符串
     *
     * @param  object 待转换实体对象
     * @return 转换结果字符串，包括null
     * </pre>
     */
    public static String object2String(Object object) {

        if (null == object)
            return null;
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("解析失败！", e);
        }
        return null;
    }



    /**
     * <pre>
     *     json字符串转map
     *
     * @param  content 字符串
     * @return 转换结果
     * </pre>
     */
    public static Map<String, String> readValue(String content) {
    
        if ((content == null || content.trim().equals("")))
            return Maps.newHashMap();

        try {
            return objectMapper.readValue(content, Map.class);
        } catch (IOException e) {
            log.error("转换map失败！", e);
        }

        return Maps.newHashMap();
    }

    /**
     * <pre>
     *     json字符串转List集合对象
     *
     * @param  content json字符串
     * @return 转换结果
     * </pre>
     */
    public static List<?> readListValue(String content) {

        if ((content == null || content.trim().equals(""))) {
            return Lists.newArrayList();
        }

        try {
            return objectMapper.readValue(content, List.class);
        } catch (IOException e) {
            log.error("转换List失败！", e);
        }
        return Lists.newArrayList();
    }


    /**
     * <pre>
     *
     *     Object 转 Map
     *
     * @param fromObj 源数据
     * @return  map   转换结果
     * </pre>
     */
    @Deprecated
    public static Map<String, Object> object2Map(Object fromObj) {

        if (null == fromObj) {
            return Maps.newHashMap();
        }

        return convertValue(fromObj, Map.class);
    }

    /**
     * <pre>
     *
     *     类型转换
     *
     * @param fromObj 源数据
     * @param clazz   类型
     * @param <T>     转换类型
     * @return T      转换结果
     * </pre>
     */
    @Deprecated
    public static <T> T convertValue(Object fromObj, Class<T> clazz) {

        if (fromObj == null || null == clazz) {
            return null;
        }

        return objectMapper.convertValue(fromObj, clazz);
    }
    
    /**
     * <pre>
     *     String TO Object
     *
     * @param source 源字符串
     * @param clazz  转换到类型
     * @param <T>    返回数据类型
     * @return       T t
     * </pre>
     */
    public static <T> T readValue(String source, Class<T> clazz) {
        if (null == source || source.trim().length() < 1) {
            return null;
        }
    
        try {
            return objectMapper.readValue(source, clazz);
        } catch (IOException e) {
            log.error("****转换异常", e);
        }
        
        return null;
    }
}
