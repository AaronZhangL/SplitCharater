/**
 * 
 */
package hk.denghuo.news.common.split;

/**
 * @author Aaron.Z
 *
 */
public class SplitStringWithZh {

	/**
	 * @param args
	 */
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
	       String s = "test中d文dsaf中男大3443n中国43中国人0ewldfls=103";
	        char[] dstChar = s.toCharArray();
	        s.getChars(0, s.length(), dstChar, 0);
	        for (int j = 0; j < dstChar.length; j++) 
	        {
	            //System.out.print(dstChar[j]+ "\t");
	        	System.out.println(dstChar[j]+ "\t");
	        }
	}
*/
	public void SplitsStringWithZh(String s){
		char[] dstChar = s.toCharArray();
        s.getChars(0, s.length(), dstChar, 0);
        for (int j = 0; j < dstChar.length; j++) 
        {
            //System.out.print(dstChar[j]+ "\t");
        	System.out.println(dstChar[j]+ "\t");
        }
	}
}
