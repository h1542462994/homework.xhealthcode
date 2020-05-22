package ext.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reg {
    String reg() default "";
    String msg() default  "不符合正则表达式";
}
