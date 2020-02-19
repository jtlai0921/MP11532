
import java.util.*;
import java.lang.reflect.*;
import java.lang.annotation.*;
/**
 * Description:
 * <br/>網站: <a href="http://www.crazyit.org">瘋狂Java聯盟</a>
 * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
// 定義可重複註文
@Repeatable(Annos.class)
@interface Anno {}
@Retention(value=RetentionPolicy.RUNTIME)
@interface Annos {
    Anno[] value();
}
// 使用4個註文修飾該類別
@SuppressWarnings(value="unchecked")
@Deprecated
// 使用重複註文修飾該類別
@Anno
@Anno
public class ClassTest
{
	// 為該類別定義一個私有的建構子
	private ClassTest()
	{
	}
	// 定義一個有參數的建構子
	public ClassTest(String name)
	{
		System.out.println("執行有參數的建構子");
	}
	// 定義一個無參數的info方法
	public void info()
	{
		System.out.println("執行無參數的info方法");
	}
	// 定義一個有參數的info方法
	public void info(String str)
	{
		System.out.println("執行有參數的info方法"
			+ "，其str參數值：" + str);
	}
	// 定義一個測試用的內部類別
	class Inner
	{
	}
	public static void main(String[] args)
		throws Exception
	{
		// 下面程式碼可以獲取ClassTest對應的Class
		Class<ClassTest> clazz = ClassTest.class;
		// 獲取該Class物件所對應類別的全部建構子
		Constructor[] ctors = clazz.getDeclaredConstructors();
		System.out.println("ClassTest的全部建構子如下：");
		for (Constructor c : ctors)
		{
			System.out.println(c);
		}
		// 獲取該Class物件所對應類別的全部public建構子
		Constructor[] publicCtors = clazz.getConstructors();
		System.out.println("ClassTest的全部public建構子如下：");
		for (Constructor c : publicCtors)
		{
			System.out.println(c);
		}
		// 獲取該Class物件所對應類別的全部public方法
		Method[] mtds = clazz.getMethods();
		System.out.println("ClassTest的全部public方法如下：");
		for (Method md : mtds)
		{
			System.out.println(md);
		}
		// 獲取該Class物件所對應類別的指定方法
		System.out.println("ClassTest裡帶一個字串參數的info()方法為："
			+ clazz.getMethod("info" , String.class));
		// 獲取該Class物件所對應類別的上的全部註文
		Annotation[] anns = clazz.getAnnotations();
		System.out.println("ClassTest的全部Annotation如下：");
		for (Annotation an : anns)
		{
			System.out.println(an);
		}
		System.out.println("該Class元素上的@SuppressWarnings註文為："
			+ Arrays.toString(clazz.getAnnotationsByType(SuppressWarnings.class)));
		System.out.println("該Class元素上的@Anno註文為："
			+ Arrays.toString(clazz.getAnnotationsByType(Anno.class)));
		// 獲取該Class物件所對應類別的全部內部類別
		Class<?>[] inners = clazz.getDeclaredClasses();
		System.out.println("ClassTest的全部內部類別如下：");
		for (Class c : inners)
		{
			System.out.println(c);
		}
		// 使用Class.forName方法載入ClassTest的Inner內部類別
		Class inClazz = Class.forName("ClassTest$Inner");
		// 通過getDeclaringClass()存取該類別所在的外部類別
		System.out.println("inClazz對應類別的外部類別為：" +
			inClazz.getDeclaringClass());
		System.out.println("ClassTest的套件為：" + clazz.getPackage());
		System.out.println("ClassTest的父類別為：" + clazz.getSuperclass());
	}
}

