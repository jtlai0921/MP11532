

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
@FkTag(age=5)
@FkTag(name="瘋狂Java" , age=9)
//@FkTags({@FkTag(age=5),
//	@FkTag(name="瘋狂Java" , age=9)})
public class FkTagTest
{
	public static void main(String[] args)
	{
		Class<FkTagTest> clazz = FkTagTest.class;
		/* 使用Java 8新增的getDeclaredAnnotationsByType()方法獲取
			修飾FkTagTest類別的多個@FkTag註文 */
		FkTag[] tags = clazz.getDeclaredAnnotationsByType(FkTag.class);
		// 遍歷修飾FkTagTest類別的多個@FkTag註文
		for(FkTag tag : tags)
		{
			System.out.println(tag.name() + "-->" + tag.age());
		}
		/* 使用傳統的getDeclaredAnnotation()方法獲取
			修飾FkTagTest類別的@FkTags註文 */
		FkTags container = clazz.getDeclaredAnnotation(FkTags.class);
		System.out.println(container);
	}
}
