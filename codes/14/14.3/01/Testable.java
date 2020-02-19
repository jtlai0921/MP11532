
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
// 使用JDK的元資料Annotation：Retention
@Retention(RetentionPolicy.RUNTIME)
// 使用JDK的元資料Annotation：Target
@Target(ElementType.METHOD)
// 定義一個標記註文，不包含任何成員變數，即不可傳入元資料
public @interface Testable
{
}
