/**
 * 
 */
package main.java.hk.denghuo.news.common.split;

import java.util.List;
import java.util.ArrayList;

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
	 * @param rule 切分字符（如：“是”）
	 * @return 切分完成的字符列表
	 */
	public List<String> getPartOfSentence(String srcText, String splitString)
	{		
		return this.GetSentences(srcText, splitString);
	}
	
	/**
	 * 将Strin[]转换成List\<String\>
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
	
	public static void main(String[] args) {
		SplitManager manager = new SplitManager();
		String testCaseString = "人（学名：智人、Homo sapiens，意为“有智慧的人”），是一种灵长目人科人属及直立行走的物种。粒线体DNA与化石证明人类大约于200万年前起源于东非。与其他动物相比，人具有高度发达的大脑，具有抽象思维、语言、自我意识以及解决问题的能力。此种能力，加之人类直立的身体导致人类的前肢可以自由活动，使得人类对工具的使用远超出其它任何物种。截止至2011年11月，世界人口已达到70亿，大约是所有曾生活在地球上的人的6%。";
		List<String> sentenStrings = manager.GetSentences(testCaseString, IdentificationRule.getSplitRuleSentence());
		
		for (String sentenString : sentenStrings) {
			// 根据定义【是】切分
			List<String> partOfSentenStrings = manager.getPartOfSentence(sentenString, IdentificationRule.getSplitCharIsString());
			for (String part : partOfSentenStrings) {
				if (part.isEmpty()) {
					continue;
				} else {
					if (2 == part.length() ) {
						// 将该词记忆
						System.out.println("-->" + part);
					}
				}
			}
		}
	}
}
