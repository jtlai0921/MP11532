
import java.util.*;
import java.io.*;
import javax.swing.*;
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
// 定義一個簡單的Type Annotation，不帶任何成員變數
@Target(ElementType.TYPE_USE)
@interface NotNull{}
// 定義類別時使用Type Annotation
@NotNull
public class TypeAnnotationTest
	implements @NotNull /* implements時使用Type Annotation */ Serializable
{
	// 方法形式參數中使用Type Annotation
	public static void main(@NotNull String[] args)
		// throws時使用Type Annotation
		throws @NotNull FileNotFoundException
	{
		Object obj = "fkjava.org";
		// 強制類型轉換時使用Type Annotation
		String str = (@NotNull String)obj;
		// 建立物件時使用Type Annotation
		Object win = new @NotNull JFrame("瘋狂軟體");
	}
	// 泛型中使用Type Annotation
	public void foo(List<@NotNull String> info){}
}