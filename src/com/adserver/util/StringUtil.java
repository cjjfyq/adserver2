package com.adserver.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	public static final String DATE_TIME_FOMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FOMAT = "yyyy-MM-dd";
	
	
//	public static final int OPERATOR_CHINA_MOBILE = 1;	移动
//	public static final int OPERATOR_CHINA_UNICOM = 2;	联通
//	public static final int OPERATOR_CHINA_TELECOM = 4;	电信
	
	public static final int[][] CLECS = {
		{46000, 1},
		{46001, 2},
		{46002, 1},
		{46003, 4},	
		{46005, 4},	
		{46006, 2},	
		{46007, 1}
	};
	
	public static int getGatewayType(String imsi) {

		// 非中国地区用户
		if(empty(imsi) || !imsi.startsWith("4600")) {
			return -1;
		}
		
		for(int i=0 ; i<CLECS.length; i++) {
			if(imsi.indexOf(CLECS[i][0]+"") == 0 ) {
				return CLECS[i][1];
			}
		}
		
		return 1;
	}
	
	public static String getPinyinWithFix(String cn, int length) {
		
		List<String> list = CnToSpell.getPinyin(cn);
		if(list == null || list.size() < 1) {
			return "";
		}
		String s = list.get(0);
		int end = s.length();
		s = s.substring(0, end > length ? length : end);
		return getFixStr(s);
	}
	
	public static String getPinyin(String cn, int length) {
		
		List<String> list = CnToSpell.getPinyin(cn);
		if(list == null || list.size() < 1) {
			return "";
		}
		String s = list.get(0);
		int end = s.length();
		s = s.substring(0, end > length ? length : end);
		s = s.replaceAll(" ", "");
		return s;
	}
	
	public static String getFixStr(String cn) {
		if(null == cn) {
			return "";
		}
		String str = cn;
		str = str.replaceAll("[']", "");
		str = str.replaceAll("[\"]", "");
		str = str.replaceAll("[\\\\]", "");
		str = str.replaceAll("[/]", "");
		str = str.replaceAll("[~]", "");
		str = str.replaceAll("[!]", "");
		str = str.replaceAll("[@]", "");
		str = str.replaceAll("[#]", "");
		str = str.replaceAll("[$]", "");
		str = str.replaceAll("[%]", "");
		str = str.replaceAll("[\\^]", "");
		str = str.replaceAll("[&]", "");
		str = str.replaceAll("[*]", "");
		str = str.replaceAll("[(]", "");
		str = str.replaceAll("[)]", "");
		str = str.replaceAll("[<]", "");
		str = str.replaceAll("[>]", "");
		str = str.replaceAll("[?]", "");
		str = str.replaceAll("[,]", "");
		str = str.replaceAll("[+]", "");
		str = str.replaceAll("[-]", "");
		str = str.replaceAll("[（]", "");
		str = str.replaceAll("[）]", "");
		str = str.replaceAll("[\\[]", "");
		str = str.replaceAll("[\\]]", "");
		str = str.replaceAll("[\\{]", "");
		str = str.replaceAll("[\\}]", "");
		str = str.replaceAll(" ", "");
		
		return str;
	}
	
	
	public static String getPinyin(String cn) {

		List<String> list = CnToSpell.getPinyin(cn);
		if (list == null || list.size() < 1) {
			return "";
		}
		
		String str = list.get(0);
		str = getFixStr(str);
		return str;
	}
	
	/**
	 * 分割字符串为数组
	 * 
	 * @param s
	 *            "aa|||||c|||d"
	 * @param regex
	 *            "|"
	 * @return {"aa","c","d"}
	 */
	public static String[] split(String s, String regex) {
		if(s == null) {
			return new String[]{};
		}
		String[] temp = s.split(regex);
		List<String> list = clearEmpty(temp);
		
		String[] result = new String[list.size()];
		for(int i=0; i<list.size(); i++) {
			result[i] = list.get(i);
		}
		
		return result;
	}
	
	/**
	 * 数组拼接为字符串
	 * @param strs	{"a", "b", "c"}
	 * @param regex	", "
	 * @return		"a, b, c"
	 */
	public static String parseToString(String[] strs, String regex) {
		String result = "";
		if (strs == null) {
			return result;
		}
		for (int i = 0; i < strs.length; i++) {
			if ("".equals(result)) result = strs[i];
			else result = result.concat(regex+strs[i]);
		}
		
		return result;
	}

	/**
	 * 分割字符串为List
	 * @param s 	"aa|||||c|||d|aa|c"
	 * @param regex	"|"
	 * @return		aa,c,d,aa,c
	 */
	public static List<String> splitToList(String s, String regex) {
		if(s == null) {
			return new ArrayList<String>();
		}
		String[] temp = s.split(regex);
		List<String> list = clearEmpty(temp);
		
		return list;
	}
	
	/**
	 * 可以去重复
	 * 分割字符串为Set()
	 * @param s 	"aa|||||c|||d|aa|c"
	 * @param regex	"|"
	 * @return		aa,c,d
	 */
	public static Set<String> splitToSet(String s, String regex) {
		Set<String> set = new HashSet<String>();
		if(s == null) {
			return set;
		}
		String[] temp = s.split(regex);
		for(int i=0; i<temp.length; i++) {
			set.add(temp[i]);
		}
		
		return set;
	}
	
	/**
	 * 去除空串
	 * @param strArray  	{"a", "", "b", "c","  ", "d"}
	 * @return				["a", "b", "c", "d"]
	 */
	public static List<String> clearEmpty(String[] strArray){
		List<String> list = new ArrayList<String>();
		
		for(int i=0; i<strArray.length; i++) {
			if(strArray[i] == null || "".equals(strArray[i].trim())) {
				continue ;
			}
			list.add(strArray[i].trim());
		}
		return list;
	}
	
	public static long getLong(String str, long defaultValue) {
		
		if(str == null) {
			return defaultValue;
		}
		
		long res = Long.MIN_VALUE;
		try {
			res = Long.valueOf(str.trim());
		} catch(Exception e) {
			LogUtil.log.debug(e);
			res = defaultValue;
		}
		return res;
	}
	
	public static int getInt(String str, int defaultValue) {
		
		if(str == null) {
			return defaultValue;
		}
		
		int res = Integer.MIN_VALUE;
		try {
			res = Integer.valueOf(str.trim());
		} catch(Exception e) {
			LogUtil.log.debug(e);
			res = defaultValue;
		}
		return res;
	}
	
	public static double getDouble(String str, double defaultValue) {
		
		if(str == null) {
			return defaultValue;
		}
		
		double res = Double.MIN_VALUE;
		try {
			res = Double.valueOf(str.trim());
		} catch(Exception e) {
			LogUtil.log.debug(e);
			res = defaultValue;
		}
		return res;
	}
	
	public static int getInt(String str) {
		try {
			return Integer.valueOf(str.trim());
		} catch(Exception e) {
			LogUtil.log.debug(e);
		}
		return Integer.MIN_VALUE;
	}
	
	public static byte[] getBytes(String args) {
		byte[] res = new byte[]{};
		
		args = replaceBlank(args);
		
		try {
			res = (args == null ? res : args.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LogUtil.log.error(e);
		} 
		
		return res;
	}
	
	public static byte[] getBytes(String args, String encoding) {
		byte[] res = new byte[]{};
		
		try {
			res = (args == null ? res : args.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			LogUtil.log.error(e);
		} 
		
		return res;
	}
	
	
	private static String replaceBlank(String args) {
		if(args == null) {
			return null;
		}
		
		Pattern pat = Pattern.compile("\r\n"); 
	    Matcher m = pat.matcher(args); 
	    args = m.replaceAll("\n");
	    
//		args = args.replaceAll("\r\n", "\n");
		return args;
	}

	public static Date getDate(String dateStr, String formatStr) {
		if(StringUtils.isBlank(formatStr)) {
			formatStr = DATE_TIME_FOMAT;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		try {
			return sdf.parse(dateStr);
		} catch(Exception e) {
			return null;
		}
	}

	public static boolean empty(String str) {
		
		if(str == null) {
			return true;
		}
		if("".equals(str.trim())) {
			return true;
		}
		
		return false;
	}

	public static boolean notEmpty(String str) {
		
		if(str != null && !"".equals(str.trim())) {
			return true;
		}
		
		return false;
	}

	public static String getString(String dpi, String defaultValue) {
		if(dpi == null) {
			return defaultValue;
		}
		return dpi;
	}

	/**
	 * 去重复方法：参考splitToSet()
	 * @param s
	 * @param regex
	 * @return
	 */
	public static String distinct(String s, String regex) {
		if(empty(s) || empty(regex)) {
			return null;
		}
		String[] strs = split(s, regex);
		Map<String, String> map = new HashMap<String, String>();
		for(int i=0; i<strs.length; i++) {
			map.put(strs[i], "");
		}
		StringBuilder sb = new StringBuilder();
		for(Object obj : map.keySet()) {
			sb.append(obj).append(",");
		}
		String res = sb.substring(0, sb.length()-1);
		
		return res;
	}

	public static void printArray(String[] arr) {
		if(arr == null || arr.length < 1) {
			System.out.println("Empty array......");
		}
		System.out.print("[");
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i]);
			if(i < arr.length-1) {
				System.out.print(",");
			}
		}
		System.out.print("]\n");
	}
	
	/**
	 * v1是否大于等于v4
	 * 格式必须如下：X.X.X
	 * @param v1	1.0.1
	 * @param v4	1.0.4
	 * @return 大于：true  等于:true  小于：false
	 */
	public static boolean greaterEqualThen(String v1, String v4) {
		if(empty(v1) && empty(v4)) {
			return false;
		}
		if(empty(v1)) {
			return false;
		}
		if(empty(v4)) {
			return true;
		}
		
		for(int i=0; i<v1.length() || i<v4.length(); i++) {
			int t1 = 0, t2 = 0;
			try {
				t1 = Integer.valueOf("" + v1.charAt(i));
			} catch(Exception e) {
				t1 = 0;
			}
			try {
				t2 = Integer.valueOf("" + v4.charAt(i));
			} catch(Exception e) {
				t2 = 0;
			}
			if(t1 == t2) {
				continue;
			}
			
			if(t1 > t2) {
				return true;
			} else {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * v1是否大于v4
	 * 格式必须如下：X.X.X
	 * @param v1	1.0.1
	 * @param v4	1.0.4
	 * @return 大于：true  等于:false  小于：false
	 */
	public static boolean greaterThen(String v1, String v4) {
		if(empty(v1) && empty(v4)) {
			return false;
		}
		if(empty(v1)) {
			return false;
		}
		if(empty(v4)) {
			return true;
		}
		
		for(int i=0; i<v1.length() || i<v4.length(); i++) {
			int t1 = 0, t2 = 0;
			try {
				t1 = Integer.valueOf("" + v1.charAt(i));
			} catch(Exception e) {
				t1 = 0;
			}
			try {
				t2 = Integer.valueOf("" + v4.charAt(i));
			} catch(Exception e) {
				t2 = 0;
			}

			if(t1 == t2) {
				continue;
			}
			
			if(t1 > t2) {
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * 获取AndroidManifest.xml中的包名
	 * @return
	 */
	public static String getMainfestPackageName(String content){
		String packageName = null;
		
		String regex = "package=\"(.+?)(\")";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(content);
		if(matcher.find()) {
			packageName = matcher.group(1).trim();
		}

		return packageName;
	}

	/**
	 * 获取AndroidManifest.xml中的包名
	 * @return
	 */
	public static String getMainfestPackageName(File mainfest){
		String packageName = null;
		
		String content = getContent(mainfest,true);
		
		String regex = "package=\"(.+?)(\")";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(content);
		if(matcher.find()) {
			packageName = matcher.group(1).trim();
		}
		
		return packageName;
	}

	public static String getContent(File mainfest,boolean lineFeed) {
		StringBuilder sb = new StringBuilder();
		
		try {
			//BufferedReader br = new BufferedReader(new FileReader(mainfest));
			InputStream ins = new FileInputStream(mainfest);
			InputStreamReader in = new InputStreamReader(ins,Charset.forName("UTF-8"));//解决中文乱码
			BufferedReader br = new BufferedReader(in);
			String line =br.readLine();  
			do {
				if(lineFeed){
					sb.append(line).append("\n");
				}else{
					sb.append(line);
				}
			} while((line = br.readLine()) != null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public static String convertToStr(String[] strArray){
		String str="";
		if(strArray!=null&&strArray.length>0){
			for(int i=0;i<strArray.length;i++){
				str=str+"'"+strArray[i]+"',";
			}
			str=str.substring(0,str.length()-1);
		}
		return str;
	}
	
	public static String convertToStr(int[] array){
		String str="";
		if(array!=null&&array.length>0){
			for(int i=0;i<array.length;i++){
				str=str+array[i]+",";
			}
			str=str.substring(0,str.length()-1);
		}
		return str;
	}
	
	/**
	 * 检查、去重、修正sql语句中的in(?)
	 * @param aids   ,1,,,,2,1,2,3,5a,76,
	 * @return		 1,2,3,76
	 */
	public static String checkUpdateToInt(String aids, String regex) {
		Set<String> set = splitToSet(aids, regex);
		StringBuilder sb  = new StringBuilder();
		Iterator<String> itor = set.iterator();
		while(itor.hasNext()) {
			int temp = 0;
			try {
				temp = Integer.parseInt(itor.next());
			} catch(Exception e) {
				continue;
			}
			if(temp <= 0) {
				continue;
			}
			sb.append(temp).append(",");
		}
		String s = sb.toString();
		if(s.endsWith(",")) {
			s = s.substring(0, s.lastIndexOf(","));
		}
		return s;
	}

	/**
	 * 检查、去重、修正
	 * @param aids   ,1,,,,2,1,2,3,5a,76,
	 * @return		 1,2,3,5a,76
	 */
	public static String checkUpdateToString(String aids, String regex) {
		Set<String> set = splitToSet(aids, regex);
		StringBuilder sb  = new StringBuilder();
		Iterator<String> itor = set.iterator();
		while(itor.hasNext()) {
			String temp = itor.next();
			if(StringUtil.empty(temp)) {
				continue;
			}
			sb.append(temp).append(",");
		}
		String s = sb.toString();
		if(s.endsWith(",")) {
			s = s.substring(0, s.lastIndexOf(","));
		}
		set.clear();
		return s;
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		
		String v1 = "2.1.53";
		String v2 = "2.1.52";
		
		System.out.println(greaterEqualThen(v1, v2));
		
		System.out.println(!greaterEqualThen("1.0.1","1.0.2"));
		
	}
	
}
