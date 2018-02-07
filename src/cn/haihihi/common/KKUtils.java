package cn.haihihi.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

/**
 * 
 * @author 李笔振 创建时间：2018-02-03 是封装一个经常使用的工具包，如加密，复制文件等等常用操作。
 */
public class KKUtils {

	public static void main(String[] args) throws Exception {
		
		
		/*int pageSize = 28;
		for(int i=1;i<=pageSize;i++) {
			org.jsoup.nodes.Document document = Jsoup.connect("http://newhouse.hn.fang.com/house/s/b9"+i+"/").get();
			Elements houseList = document.getElementById("newhouse_loupai_list").getElementsByTag("ul").get(0).getElementsByTag("li");
			for(Element house : houseList){
				String houseName = house.getElementsByClass("nlcd_name").get(0).text();
				writeTextFile(houseName+"\r\n",new File("D:\\视频+全景\\houseName.txt"),true);
				
			}
		}*/
		
		/*String[] houseNames = readerTextFile(new File("D:\\视频+全景\\houseName.txt")).split("\r\n");
		for(String name : houseNames) {
			String target = name + " $$ " ;
			String search = "https://m.fang.com/xf/hn/?keyword=" + URLEncoder.encode(name,"GBK");
			
			Element ul = Jsoup.connect(search).timeout(35000).userAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Mobile Safari/537.36").get().getElementById("xfContentList");
			Elements lis = ul.getElementsByTag("li");
			
			if(lis.size() ==0) {
				System.out.println("搜索为空，当前为：" + name);
			}else {
				Element houseLi = lis.get(0);
				if(houseLi.getElementsByClass("qj360").size()!=0 || houseLi.getElementsByClass("video2").size()!=0) {
					String link = "https:"+houseLi.getElementsByTag("a").get(0).attr("href");
					target = target + link;
					writeTextFile(target+"\r\n", new File("D:\\视频+全景\\houseName+link.txt"), true);
				}
				System.out.println("target=" + target);
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}*/
		
	}

	/**
	 * 将一行文字根据需要追加到文件后面
	 * @param content 要追加的内容
	 * @param file 文件
	 * @param isAppend 是否追加到文件尾部
	 */
	public static void writeTextFile(String content,File file,boolean isAppend) {
		FileWriter fw = null;
		
		BufferedWriter bw = null;

		try {
			fw = new FileWriter(file, isAppend);
			bw = new BufferedWriter(fw);
			bw.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bw.flush();
				bw.close();
				fw.close();
				System.out.println("writeContent = "+ content +" success");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * 读一个文件的内容
	 * @param file 文本文件
	 * @return 返回改文件内容，按照行来读取
	 */
	public static String readerTextFile(File file) {
		String content = "";
		FileReader fileReader = null;
		BufferedReader br = null;
		try {
			fileReader = new FileReader(file);
			br = new BufferedReader(fileReader);
			String s;
			while ((s = br.readLine()) != null) {
				content += s+"\r\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				br.close();
				fileReader.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return content;
	}
	
	/**
	 * 讲一个文件输入流写到一个新的文件上
	 * @param in 输入流
	 * @param newFile 新文件
	 */
	public static void writeFileByInputStream(InputStream in,File newFile) {
		BufferedInputStream bis = new BufferedInputStream(in);
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(newFile));
			int i;
			while ((i = bis.read()) != -1) {
				bos.write(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bos.flush();
				bos.close();
				bis.close();
				System.out.println("writeFile success");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 格式化emoji表情
	 * @param source 原文本
	 * @return 除去emoji表情之后留下的文本
	 */
	public static String filterEmoji(String source) {
		if (source != null && source.length() > 0) {
			return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
		} else {
			return source;
		}
	}

	/**
	 * 返回一个指定格式的当前时间 如yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 指定格式的当前时间 为空则默认 yyyy-MM-dd
	 */
	public static String getCurrentDate(String formatStr) {
		if (formatStr == null)
			formatStr = "yyyy-MM-dd";
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatStr);
		Calendar cal = Calendar.getInstance();
		return format.format(cal.getTime());
	}

	/**
	 * 返回一个uuid
	 * 
	 * @return 32位uuid
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String string = uuid.toString();
		string = string.replace("-", "");
		string = string.toUpperCase();
		return string;
	}

	/**
	 * MD5加密
	 * 
	 * @param 要加密的文本
	 * @return 加密成功的md5值
	 */
	public final static String encrypForMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析xml，获得具体标签的文本;注意这里只能获取第一个元素/标签，不管你有多少个
	 * 
	 * @param tagName
	 *            元素名/标签名 <myxml>张三</myxml>。
	 * @param fileInputStream
	 *            xml文件流对象
	 * @return 返回该sql语句
	 */
	public static String getTextByTagName(String tagName, InputStream fileInputStream) {
		String sqlStatement = null;
		try {
			// 创建解析器工厂
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			// 创建解析器
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			InputStream in = fileInputStream;
			// 解析xml
			Document document = builder.parse(in); // 这里的这个Document是w3c的包下的
			sqlStatement = document.getElementsByTagName(tagName).item(0).getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlStatement.trim();
	}
}
