
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
// 通過組合HashMap物件來實作CrazyitMap，CrazyitMap要求value也不可重複
public class CrazyitMap<K,V>
{
	// 建立一個執行緒安全的HashMap
	public Map<K ,V> map = Collections.synchronizedMap(new HashMap<K,V>());
	// 根據value來刪除指定項目
	public synchronized void removeByValue(Object value)
	{
		for (Object key : map.keySet())
		{
			if (map.get(key) == value)
			{
				map.remove(key);
				break;
			}
		}
	}
	// 獲取所有value組成的Set集合
	public synchronized Set<V> valueSet()
	{
		Set<V> result = new HashSet<V>();
		// 將map中所有value添加到result集合中
		map.forEach((key , value) -> result.add(value));
		return result;
	}
	// 根據value尋找key。
	public synchronized K getKeyByValue(V val)
	{
		// 遍歷所有key組成的集合
		for (K key : map.keySet())
		{
			// 如果指定key對應的value與被搜尋的value相同，則返回對應的key
			if (map.get(key) == val || map.get(key).equals(val))
			{
				return key;
			}
		}
		return null;
	}
	// 實作put()方法，該方法不允許value重複
	public synchronized V put(K key,V value)
	{
		// 遍歷所有value組成的集合
		for (V val : valueSet() )
		{
			// 如果某個value與試圖放入集合的value相同
			// 則拋出一個RuntimeException異常
			if (val.equals(value)
				&& val.hashCode()== value.hashCode())
			{
				throw new RuntimeException("MyMap實例中不允許有重複value!");
			}
		}
		return map.put(key , value);
	}
}
