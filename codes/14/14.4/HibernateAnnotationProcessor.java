
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.*;

import java.io.*;
import java.util.*;

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
@SupportedSourceVersion(SourceVersion.RELEASE_8)
// 指定可處理@Persistent、@Id、@Property三個Annotation
@SupportedAnnotationTypes({"Persistent" , "Id" , "Property"})
public class HibernateAnnotationProcessor
	extends AbstractProcessor
{
	// 迴圈處理每個需要處理的程式物件
	public boolean process(Set<? extends TypeElement> annotations
		, RoundEnvironment roundEnv)
	{
		// 定義一個檔案輸出串流，用於產生額外的檔案
		PrintStream ps = null;
		try
		{
			// 遍歷每個被@Persistent修飾的class檔
			for (Element t : roundEnv.getElementsAnnotatedWith(Persistent.class))
			{
				// 獲取正在處理的類別名稱
				Name clazzName = t.getSimpleName();
				// 獲取類別定義前的@Persistent Annotation
				Persistent per = t.getAnnotation(Persistent.class);
				// 建立檔案輸出串流
				ps = new PrintStream(new FileOutputStream(clazzName
					+ ".hbm.xml"));
				// 執行輸出
				ps.println("<?xml version=\"1.0\"?>");
				ps.println("<!DOCTYPE hibernate-mapping PUBLIC");
				ps.println("	\"-//Hibernate/Hibernate "
					+ "Mapping DTD 3.0//EN\"");
				ps.println("	\"http://www.hibernate.org/dtd/"
					+ "hibernate-mapping-3.0.dtd\">");
				ps.println("<hibernate-mapping>");
				ps.print("	<class name=\"" + t);
				// 輸出per的table()的值
				ps.println("\" table=\"" + per.table() + "\">");
				for (Element f : t.getEnclosedElements())
				{
					// 只處理成員變數上的Annotation
					if (f.getKind() == ElementKind.FIELD)   // ①
					{
						// 獲取成員變數定義前的@Id Annotation
						Id id = f.getAnnotation(Id.class);      // ②
						// 當@Id Annotation存在時輸出<id.../>元素
						if(id != null)
						{
							ps.println("		<id name=\""
								+ f.getSimpleName()
								+ "\" column=\"" + id.column()
								+ "\" type=\"" + id.type()
								+ "\">");
							ps.println("		<generator class=\""
								+ id.generator() + "\"/>");
							ps.println("		</id>");
						}
						// 獲取成員變數定義前的@Property Annotation
						Property p = f.getAnnotation(Property.class);  // ③
						// 當@Property Annotation存在時輸出<property.../>元素
						if (p != null)
						{
							ps.println("		<property name=\""
								+ f.getSimpleName()
								+ "\" column=\"" + p.column()
								+ "\" type=\"" + p.type()
								+ "\"/>");
						}
					}
				}
				ps.println("	</class>");
				ps.println("</hibernate-mapping>");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (ps != null)
			{
				try
				{
					ps.close();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		return true;
	}
}
