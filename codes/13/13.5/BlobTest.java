
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;
import java.util.ArrayList;
import java.io.*;
import javax.swing.filechooser.FileFilter;
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
public class BlobTest
{
	JFrame jf = new JFrame("圖片管理程式");
	private static Connection conn;
	private static PreparedStatement insert;
	private static PreparedStatement query;
	private static PreparedStatement queryAll;
	// 定義一個DefaultListModel物件
	private DefaultListModel<ImageHolder> imageModel
		= new DefaultListModel<>();
	private JList<ImageHolder> imageList = new JList<>(imageModel);
	private JTextField filePath = new JTextField(26);
	private JButton browserBn = new JButton("...");
	private JButton uploadBn = new JButton("上傳");
	private JLabel imageLabel = new JLabel();
	// 以當前路徑建立檔案選擇器
	JFileChooser chooser = new JFileChooser(".");
	// 建立檔案過濾器
	ExtensionFileFilter filter = new ExtensionFileFilter();
	static
	{
		try
		{
			Properties props = new Properties();
			props.load(new FileInputStream("mysql.ini"));
			String driver = props.getProperty("driver");
			String url = props.getProperty("url");
			String user = props.getProperty("user");
			String pass = props.getProperty("pass");
			Class.forName(driver);
			// 獲取資料庫連接
			conn = DriverManager.getConnection(url , user , pass);
			// 建立執行插入的PreparedStatement物件，
			// 該物件執行插入後可以返回自動產生的主鍵
			insert = conn.prepareStatement("insert into img_table"
				+ " values(null,?,?)" , Statement.RETURN_GENERATED_KEYS);
			// 建立兩個PreparedStatement物件，用於查詢指定圖片，查詢所有圖片
			query = conn.prepareStatement("select img_data from img_table"
				+ " where img_id=?");
			queryAll = conn.prepareStatement("select img_id, "
				+ " img_name from img_table");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void init()throws SQLException
	{
		// -------初始化檔案選擇器--------
		filter.addExtension("jpg");
		filter.addExtension("jpeg");
		filter.addExtension("gif");
		filter.addExtension("png");
		filter.setDescription("圖片檔(*.jpg,*.jpeg,*.gif,*.png)");
		chooser.addChoosableFileFilter(filter);
		// 禁止「檔案類型」下拉列表中顯示「所有檔案」選項。
		chooser.setAcceptAllFileFilterUsed(false);
		// ---------初始化程式介面---------
		fillListModel();
		filePath.setEditable(false);
		// 只能單選
		imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JPanel jp = new JPanel();
		jp.add(filePath);
		jp.add(browserBn);
		browserBn.addActionListener(event -> {
			// 顯示檔案對話方塊
			int result = chooser.showDialog(jf , "瀏覽圖片檔上傳");
			// 如果使用者選擇了APPROVE（核准）按鈕，即開啟，儲存等效按鈕
			if(result == JFileChooser.APPROVE_OPTION)
			{
				filePath.setText(chooser.getSelectedFile().getPath());
			}
		});
		jp.add(uploadBn);
		uploadBn.addActionListener(avt -> {
			// 如果上傳檔案的文字方塊有內容
			if (filePath.getText().trim().length() > 0)
			{
				// 將指定檔案存放到資料庫
				upload(filePath.getText());
				// 清空文字方塊內容
				filePath.setText("");
			}
		});
		JPanel left = new JPanel();
		left.setLayout(new BorderLayout());
		left.add(new JScrollPane(imageLabel) , BorderLayout.CENTER);
		left.add(jp , BorderLayout.SOUTH);
		jf.add(left);
		imageList.setFixedCellWidth(160);
		jf.add(new JScrollPane(imageList) , BorderLayout.EAST);
		imageList.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				// 如果滑鼠雙擊
				if (e.getClickCount() >= 2)
				{
					// 取出選取的List項目
					ImageHolder cur = (ImageHolder)imageList.
					getSelectedValue();
					try
					{
						// 顯示選取項目對應的Image
						showImage(cur.getId());
					}
					catch (SQLException sqle)
					{
						sqle.printStackTrace();
					}
				}
			}
		});
		jf.setSize(620, 400);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	// ----------尋找img_table填充ListModel----------
	public void fillListModel()throws SQLException
	{

		try(
			// 執行查詢
			ResultSet rs = queryAll.executeQuery())
		{
			// 先清除所有元素
			imageModel.clear();
			// 把查詢的全部記錄添加到ListModel中
			while (rs.next())
			{
				imageModel.addElement(new ImageHolder(rs.getInt(1)
					,rs.getString(2)));
			}
		}
	}
	// ---------將指定圖片放入資料庫---------
	public void upload(String fileName)
	{
		// 截取檔名
		String imageName = fileName.substring(fileName.lastIndexOf('\\')
			+ 1 , fileName.lastIndexOf('.'));
		File f = new File(fileName);
		try(
			InputStream is = new FileInputStream(f))
		{
			// 設置圖片名參數
			insert.setString(1, imageName);
			// 設置二進位串流參數
			insert.setBinaryStream(2, is , (int)f.length());
			int affect = insert.executeUpdate();
			if (affect == 1)
			{
				// 重新更新ListModel，將會讓JList顯示最新的圖片列表
				fillListModel();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	// ---------根據圖片ID來顯示圖片----------
	public void showImage(int id)throws SQLException
	{
		// 設置參數
		query.setInt(1, id);
		try(
			// 執行查詢
			ResultSet rs = query.executeQuery())
		{
			if (rs.next())
			{
				// 取出Blob欄
				Blob imgBlob = rs.getBlob(1);
				// 取出Blob欄裡的資料
				ImageIcon icon=new ImageIcon(imgBlob.getBytes(1L
					,(int)imgBlob.length()));
				imageLabel.setIcon(icon);
			}
		}
	}
	public static void main(String[] args)throws SQLException
	{
		new BlobTest().init();
	}
}
// 建立FileFilter的子類別，用以實作檔案過濾功能
class ExtensionFileFilter extends FileFilter
{
	private String description = "";
	private ArrayList<String> extensions = new ArrayList<>();
	// 自訂方法，用於添加檔案副檔名
	public void addExtension(String extension)
	{
		if (!extension.startsWith("."))
		{
			extension = "." + extension;
			extensions.add(extension.toLowerCase());
		}
	}
	// 用於設置該檔案過濾器的描述文字
	public void setDescription(String aDescription)
	{
		description = aDescription;
	}
	// 繼承FileFilter類別必須實作的抽象方法，返回該檔案過濾器的描述文字
	public String getDescription()
	{
		return description;
	}
	// 繼承FileFilter類別必須實作的抽象方法，判斷該檔案過濾器是否接受該檔案
	public boolean accept(File f)
	{
		// 如果該檔案是路徑，接受該檔案
		if (f.isDirectory()) return true;
		// 將檔名轉為小寫（全部轉為小寫後比較，用於忽略檔名大小寫）
		String name = f.getName().toLowerCase();
		// 遍歷所有可接受的副檔名，如果副檔名相同，該檔案就可接受。
		for (String extension : extensions)
		{
			if (name.endsWith(extension))
			{
				return true;
			}
		}
		return false;
	}
}
// 建立一個ImageHolder類別，用於封裝圖片名、圖片ID
class ImageHolder
{
	// 封裝圖片的ID
	private int id;
	// 封裝圖片的圖片名字
	private String name;
	public ImageHolder(){}
	public ImageHolder(int id , String name)
	{
		this.id = id;
		this.name = name;
	}
	// id的setter和getter方法
	public void setId(int id)
	{
		this.id = id;
	}
	public int getId()
	{
		return this.id;
	}
	// name的setter和getter方法
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	// 覆寫toString方法，返回圖片名
	public String toString()
	{
		return name;
	}
}
