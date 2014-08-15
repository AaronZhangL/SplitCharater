/**
 * 
 */
package main.java.hk.denghuo.news.common.split;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aaron.Z
 *
 */
public class SplitManager {
	public void SplitsStringWithZh(String s){
		char[] dstChar = s.toCharArray();
        s.getChars(0, s.length(), dstChar, 0);
        for (int j = 0; j < dstChar.length; j++) 
        {
            //System.out.print(dstChar[j]+ "\t");
        	System.out.println(dstChar[j]+ "\t");
        }
	}
	
	/**
	 * 根据给定规则切分句子
	 * @param srcText 被切分字段
	 * @param rule 切分规则
	 * @return 切分完成的句子列表
	 */
	public List<String> GetSentences(String srcText, String splitRuleString)
	{
		List<String> sentences = new ArrayList<String>();
		if (splitRuleString.isEmpty()) {
			return sentences;
		} 
		String[] sents = srcText.split(splitRuleString);
		sentences = this.changeArrayString2ListString(sents);
		return sentences;
	}
	
	/**
	 * 根据给定规则切分句子
	 * @param srcText 被切分字段
	 * @param regEx 切分字符chuan（如：“是”，"但是"）
	 * @return 切分完成的字符列表
	 */
	public String[] getPartOfSentence(String srcText, String regEx)
	{
		// org.apache.commons.lang.StringUtils 
		if (srcText.isEmpty() || regEx.isEmpty()) {
			return null;
		}
		
		String[] firstTimeSplit = srcText.split(regEx);
		String[] resultSplit = firstTimeSplit.clone();
		if (1 < firstTimeSplit.length) {
			resultSplit = new String[firstTimeSplit.length*2-1];
		} else {
			return firstTimeSplit;
		}
		
		for (int i = 0; i < firstTimeSplit.length; i++) {
			resultSplit[i*2] = firstTimeSplit[i];
			if (i < (firstTimeSplit.length - 1)) {
				resultSplit[i*2+1] = regEx;
			}
		}
		return resultSplit;
	}
	
	/**
	 * 将String[]转换成List\<String\>
	 * @param sents
	 * @return
	 */
	private List<String> changeArrayString2ListString(String[] sents)
	{
		List<String> listStrings = new ArrayList<String>();
		for (String sent : sents) {
			listStrings.add(sent);	
		}
		return listStrings;
	}
	
	/**
	 * 
	 * @param str 需要分割的字符串
	 * @param regEx 正则表达式：句子结束符
	 * @return 根据正则表达式分割的句子数组
	 */
	public String[] split(String str, String regEx) {
		/* 需要分割的文章 */
		// String str = "第一句。第二句！第三句：第四句；第五句。";

		/* 正则表达式：句子结束符 */
		//String regEx = "：|。|！|；";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);

		/* 按照句子结束符分割句子 */
		String[] words = p.split(str);

		/* 将句子结束符连接到相应的句子后 */
		if (words.length > 0) {
			int count = 0;
			while (count < words.length) {
				if (m.find()) {
					words[count] += m.group();
				}
				count++;
			}
		}
		return words;
	}
	
	public static void main(String[] args) {
		SplitManager manager = new SplitManager();
		String testCaseString = "人（学名：智人、Homo sapiens，意为“有智慧的人”），是一种灵长目人科人属及直立行走的物种。粒线体DNA与化石证明人类大约于200万年前起源于东非。与其他动物相比，人具有高度发达的大脑，具有抽象思维、语言、自我意识以及解决问题的能力。此种能力，加之人类直立的身体导致人类的前肢可以自由活动，使得人类对工具的使用远超出其它任何物种。截止至2011年11月，世界人口已达到70亿，大约是所有曾生活在地球上的人的6%。";
		String[] sentenStrings = manager.split(testCaseString, IdentificationRule.getSplitRuleSentence());
		
		Integer sentenCountInteger = 0;
		for (String sentenString : sentenStrings) {
			sentenCountInteger++;
			System.out.println();
			System.out.println(sentenCountInteger + "行：" + sentenString);
			System.out.println("------------------根据定义【是】切分------------------------");
			
			// 根据定义【是】切分
			String[] partOfSentenStrings = manager.getPartOfSentence(sentenString, IdentificationRule.getSplitCharIsString());
			for (String part : partOfSentenStrings) {
				if (null == part || part.isEmpty()) {
					continue;
				} else {
					if (2 == part.length() ) {
						// 将该词记忆
						//System.out.println("-->" + part);
					}
				}
				System.out.println(part);
			}

		}
	}
}
