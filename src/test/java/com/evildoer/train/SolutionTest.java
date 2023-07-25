package com.evildoer.train;

import com.evildoer.train.utils.Log;
import mockit.Tested;
import org.junit.Test;

public class SolutionTest {
    @Tested
    private Solution solution;

    @Test
    public void test() {
        Log.print(removeDuplicateLetters("cbacdcbc"));
        // 下一位小于等于我或我是首位且下一位为0(连续删0)

        // 预处理：获取后续比我大的数字个数(另外一个数组全都可以再我后面？)
        // 比我大的数小于count
    }

    public String removeDuplicateLetters(String s) {
        boolean[] vis = new boolean[26];
        int[] nums = new int[26];
        for (int i = 0; i < s.length(); i++) {
            nums[s.charAt(i) - 'a']++;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 结果集非空, 新字符够小(想贪), 待淘汰字符有候选人(可贪)
            if (!vis[c - 'a']) {
                while (sb.length() > 0 && c < sb.charAt(sb.length() - 1)) {
                    // 待淘汰字符有候选人
                    if (nums[sb.charAt(sb.length() - 1) - 'a'] > 0) {
                        vis[sb.charAt(sb.length() - 1) - 'a'] = false;
                        sb.deleteCharAt(sb.length() - 1);
                    } else {
                        break;
                    }
                }
                vis[c - 'a'] = true;
                sb.append(c);
            }
            nums[c - 'a']--;
        }
        return sb.toString();
    }

}