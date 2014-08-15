package main.java.hk.denghuo.news.common.split;

public class IdentificationRule {
	
	/**
	 * 所有标点符号
	 */
	private static String SPLIT_RULE_ALL = "。|.|，|,|！|…|……|!|《|》|<|>|\"|'|:|：|？|\\?|、|\\||“|”|‘|’|；|—|（|）|·|\\(|\\)|　";
	
	public static String getSplitRuleAll() {
		return SPLIT_RULE_ALL;
	}
	
	/**
	 * 整句分隔标点符号
	 */
	private static String SPLIT_RULE_SENTENCE = "。|…|……|!|！|？|\\?";
	
	public static String getSplitRuleSentence() {
		return SPLIT_RULE_SENTENCE;
	}

	/**
	 * 整句中子句的分隔标点符号
	 */
	private static String SPLIT_RULE_SUB_SENTENCE = "，|,|《|》|<|>|\"|'|:|：|、|\\||“|”|‘|’|；|—|（|）|·|\\(|\\)";

	public static String getSplitRuleSubSentence() {
		return SPLIT_RULE_SUB_SENTENCE;
	}
	
	/**
	 * @return "人"
	 */
	public static String getNCharString() {
		return N_CHAR;
	}

	/**
	 * @return "是"
	 */
	public static String getSplitCharIsString() {
		return SPLIT_CHAR_IS;
	}

	/**
	 * @return "有"
	 */
	public static String getSplitCharHasString() {
		return SPLIT_CHAR_HAS;
	}

	/**
	 * 
	 */
	private static String N_CHAR = "人";
	
	/**
	 * 
	 */
	private static String SPLIT_CHAR_IS = "是";
	
	/**
	 * 
	 */
	private static String SPLIT_CHAR_HAS = "有";
	



}
