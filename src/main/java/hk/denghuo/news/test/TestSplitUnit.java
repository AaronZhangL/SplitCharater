/**
 * 
 */
package main.java.hk.denghuo.news.test;

import main.java.hk.denghuo.news.common.split.*;

/**
 * @author 00887
 *
 */
public class TestSplitUnit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    SplitManager splitChar = new SplitManager();
	    
	    String s = "test中d文dsaf中男大3443n,中国43中国人0ewldfls=103";
	    //s = "新手求助，带中文字符串分割问题";
	    splitChar.SplitsStringWithZh(s);
	}

}
