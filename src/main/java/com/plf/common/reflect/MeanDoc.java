package com.plf.common.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//SOURCE 在编译的字节码中去掉注解，CLASS 字节码中有注解但是不加载到虚拟机，
//RUNTIME可以通过反射获取到注解信息，而注解框架也是采用的这种原理。
public @interface MeanDoc {
	 String value() default "";
}


/**
 * SOURCE
 * 
 * Annotations are to be discarded by the compiler.
 * 
 *  注解将被编译器丢弃
 */

/**
 * CLASS
 * Annotations are to be recorded in the class file by the compiler
 * but need not be retained by the VM at run time.  This is the default
 * behavior.
 * 
 * 注解由被编译器留在类文件中，但不会加载到JVM中。 这是默认值。
 */

/**
 * RUNTIME
 * 
 * Annotations are to be recorded in the class file by the compiler and
 * retained by the VM at run time, so they may be read reflectively.
 * 注解由被编译器留在类文件中，并会加载到JVM中。 因此可以通过反射方式读取它们。
 * @see java.lang.reflect.AnnotatedElement
 */
