package com.plf.javassist;

import java.io.File;
import java.nio.file.Files;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author Panlf
 *
 */
public class JavassistTest {
/*
	Javassist 核心API
	Javassist是一个开源的分析、编辑和创建Java 字节码的类库，其主要的优点，在于简单，而且快速。
	直接使用Java编码形式，而不需要了解虚拟机指令，就能动态改变类的结构，或者动态生成。
	
	应用场景
		监控、动态代理
		dubbo、MyBatis、Spring都有不同程度的应用。
	
	执行流程
		构造ClassPool对象  插入类查找路径insertClassPath() --> 获取Class  构造新类 makeClass() ; 加载已有类 get() -->
			修改构造Ctclass  添加修改方法addMethod();添加修改属性addField() --> 生成并装载Class  toClass()  toBytecode()
			
		
	特殊符号使用
	$0, $1, $2, … $0表示this，其他的表示实际的参数
	$args 参数数组.相当于new Object[]($1,$2...…)，其中的基本类型会被转为包装类型
	$$ 所有的参数，如m($$)相当于m($1,$2...)，如果m无参数则m($$)相当于m()
	$cflow(..) 表示一个指定的递归调用的深度
	$r 用于类型装换，表示返回值的类型.
	$w 将基础类型转换为一个包装类型，如Integer a=($w)5；表示将5转换为Integer，如果不是基本类型则什么都不做。
	$_ 返回值，如果方法为void则返回值为null；值在方法返回前获得，
		如果希望发生异常是有返回值（默认值，如null），需要将insertAfter方法的第二个参数asFinally设置为true
	$sig 方法参数的类型数组，数组的顺序为参数的顺序
	$type 返回类型的class.如返回Integer则$type相当于java.lang.Integer.class.注意其与$r的区别
	$class 方法所在的类的class
	
	特殊语法的说明
		a)	不能引用在方法中其他地方定义的局部变量
		b)	不会对类型进行强制检查：如int start = System.currentTimeMillis();或String i = "abc";
		c)  使用特殊的项目语法符号
*/
	@Test
	public void updateMethod() throws Exception {
		ClassPool pool = new ClassPool();
		pool.appendSystemPath();
		CtClass ctl = pool.get("com.plf.javassist.UserService");
		
		CtMethod method = ctl.getDeclaredMethod("updateUser");
		method.insertBefore("System.out.println(\"abc\");");
		
		CtField f = new CtField(pool.get(String.class.getName()),"abc",ctl);
		ctl.addField(f);

		File file = new File(System.getProperty("user.dir")+"/target/classes/com/plf/javassist/UserService.class");
		file.createNewFile();
		Files.write(file.toPath(), ctl.toBytecode());
	}
	
	@Test
	public void addMethod() throws Exception {
		ClassPool pool = new ClassPool();
		pool.appendSystemPath();
		CtClass ctl = pool.get("com.plf.javassist.UserService");
		
		CtMethod method = ctl.getDeclaredMethod("addUser");
		method.insertBefore("System.out.println($0);");
		method.insertBefore("System.out.println($1);");
		method.insertBefore("System.out.println($2);");
		ctl.toClass();
		new UserService().addUser("Panlf", "26");
	}
}
