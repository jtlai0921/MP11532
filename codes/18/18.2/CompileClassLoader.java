
import java.io.*;
import java.lang.reflect.*;
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
public class CompileClassLoader extends ClassLoader
{
	// 讀取一個檔案的內容
	private byte[] getBytes(String filename)
		throws IOException
	{
		File file = new File(filename);
		long len = file.length();
		byte[] raw = new byte[(int)len];
		try(
			FileInputStream fin = new FileInputStream(file))
		{
			// 一次讀取class檔的全部二進位資料
			int r = fin.read(raw);
			if(r != len)
			throw new IOException("無法讀取全部檔案："
				+ r + " != " + len);
			return raw;
		}
	}
	// 定義編譯指定Java檔的方法
	private boolean compile(String javaFile)
		throws IOException
	{
		System.out.println("CompileClassLoader:正在編譯 "
			+ javaFile + "...");
		// 呼叫系統的javac命令
		Process p = Runtime.getRuntime().exec("javac " + javaFile);
		try
		{
			// 其他執行緒都等待這個執行緒完成
			p.waitFor();
		}
		catch(InterruptedException ie)
		{
			System.out.println(ie);
		}
		// 獲取javac執行緒的結束值
		int ret = p.exitValue();
		// 返回編譯是否成功
		return ret == 0;
	}
	// 覆寫ClassLoader的findClass方法
	protected Class<?> findClass(String name)
		throws ClassNotFoundException
	{
		Class clazz = null;
		// 將包路徑中的點（.）替換成斜線（/）。
		String fileStub = name.replace("." , "/");
		String javaFilename = fileStub + ".java";
		String classFilename = fileStub + ".class";
		File javaFile = new File(javaFilename);
		File classFile = new File(classFilename);
		// 當指定Java原始檔存在，且class檔不存在、或者Java原始檔
		// 的修改時間比class檔案修改時間更晚，重新編譯
		if(javaFile.exists() && (!classFile.exists()
			|| javaFile.lastModified() > classFile.lastModified()))
		{
			try
			{
				// 如果編譯失敗，或者該Class檔不存在
				if(!compile(javaFilename) || !classFile.exists())
				{
					throw new ClassNotFoundException(
						"ClassNotFoundExcetpion:" + javaFilename);
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		// 如果class檔存在，系統負責將該檔案轉換成Class物件
		if (classFile.exists())
		{
			try
			{
				// 將class檔的二進位資料讀入陣列
				byte[] raw = getBytes(classFilename);
				// 呼叫ClassLoader的defineClass方法將二進位資料轉換成Class物件
				clazz = defineClass(name,raw,0,raw.length);
			}
			catch(IOException ie)
			{
				ie.printStackTrace();
			}
		}
		// 如果clazz為null，表明載入失敗，則拋出異常
		if(clazz == null)
		{
			throw new ClassNotFoundException(name);
		}
		return clazz;
	}
	// 定義一個主方法
	public static void main(String[] args) throws Exception
	{
		// 如果運行該程式時沒有參數，即沒有目標類別
		if (args.length < 1)
		{
			System.out.println("缺少目標類別，請按如下格式運行Java原始檔：");
			System.out.println("java CompileClassLoader ClassName");
		}
		// 第一個參數是需要運行的類別
		String progClass = args[0];
		// 剩下的參數將作為運行目標類別時的參數，
		// 將這些參數複製到一個新陣列中
		String[] progArgs = new String[args.length-1];
		System.arraycopy(args , 1 , progArgs
			, 0 , progArgs.length);
		CompileClassLoader ccl = new CompileClassLoader();
		// 載入需要運行的類別
		Class<?> clazz = ccl.loadClass(progClass);
		// 獲取需要運行的類別的主方法
		Method main = clazz.getMethod("main" , (new String[0]).getClass());
		Object[] argsArray = {progArgs};
		main.invoke(null,argsArray);
	}
}
