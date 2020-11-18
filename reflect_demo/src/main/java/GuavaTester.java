/**
 * @Author: xiaohuan
 * @Date: 2019-12-21 19:56
 */
import com.google.common.base.Ascii;
import com.google.common.base.CaseFormat;

public class GuavaTester {
  public static void main(String args[]) {
    GuavaTester tester = new GuavaTester();
    tester.testCaseFormat();
  }

  /**
   * 将单词第一个字母变大写,其他变小写
   */
  private static String firstCharOnlyToUpper(String word) {
    int length = word.length();
    if (length == 0) {
      return word;
    }
    return new StringBuilder(length)
            .append(Ascii.toUpperCase(word.charAt(0)))
            .append(Ascii.toLowerCase(word.substring(1)))
            .toString();
  }

  private void testCaseFormat() {
    System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));//testData
    System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));//testData
    System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));//TestData

    System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testdata"));//testdata
    System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TestData"));//test_data
    System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "testData"));//data
  }
}
