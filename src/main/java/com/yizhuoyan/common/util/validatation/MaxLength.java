package com.yizhuoyan.common.util.validatation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Administrator on 2017/11/24 0024.
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MaxLengthValidatorImpl.class)
public @interface MaxLength {
    int value() default 0;

    String message() default "must-less-than(value)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
