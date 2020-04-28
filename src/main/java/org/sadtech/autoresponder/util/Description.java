package org.sadtech.autoresponder.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Служит для описания роли полей в классах.
 *
 * @author upagge [07/07/2019]
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface Description {

    String value() default "";

}
