package com.evildoer.train.algorithm;

/**
 * BigNumber
 *
 * @author evildoer
 * @version v1.0
 * @date 2023/8/1
 */
public class BigNumber {

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        String ans = "0";
        StringBuffer pow = new StringBuffer();
        for (int i = num1.length() - 1; i >= 0; i--) {
            int add = 0;
            StringBuffer sb = new StringBuffer();
            int a = num1.charAt(i) - '0';
            for (int j = num2.length() - 1; j >= 0; j--) {
                int r = a * (num2.charAt(j) - '0') + add;
                add = r / 10;
                sb.append(r % 10);
            }
            if (add > 0) {
                sb.append(add);
            }
            ans = addStrings(ans, sb.reverse().append(pow).toString());
            pow.append(0);
        }
        return ans;
    }

    public String addStrings(String num1, String num2) {
        StringBuffer sb = new StringBuffer();
        // 进位
        int add = 0;
        int i = num1.length() - 1, j = num2.length() - 1;
        while (i >= 0 || j >= 0 || add != 0) {
            int c = add;
            if (i >= 0) {
                c += num1.charAt(i--) - '0';
            }
            if (j >= 0) {
                c += num2.charAt(j--) - '0';
            }
            if (c >= 10) {
                add = 1;
                c -= 10;
            } else {
                add = 0;
            }
            sb.append(c);
        }
        return sb.reverse().toString();
    }

    public String subStrings(String num1, String num2) {
        boolean isReverse = false;
        if (num1.length() > num2.length()) {
            isReverse = false;
        } else if(num1.length() < num2.length()) {
            isReverse = true;
        } else {
            int i;
            for (i = 0; i < num1.length(); i++) {
                if (num1.charAt(i) < num2.charAt(i)) {
                    isReverse = true;
                    break;
                } else if (num1.charAt(i) > num2.charAt(i)) {
                    isReverse = false;
                    break;
                }
            }
            if (i - num1.length() == 0) {
                return "0";
            }
        }
        if (isReverse) {
            return "-" + getSubStrings(num2, num1);
        }
        return getSubStrings(num1, num2);
    }


    private static String getSubStrings(String num1, String num2) {
        // num1一定大于num2
        StringBuffer sb = new StringBuffer();
        // 进位
        int add = 0;
        int i = num1.length() - 1, j = num2.length() - 1;
        while (i >= 0 || j >= 0) {
            int c = add;
            if (i >= 0) {
                c += num1.charAt(i--) - '0';
            }
            if (j >= 0) {
                c -= num2.charAt(j--) - '0';
            }
            if (c >= 0) {
                add = 0;
            } else {
                add = -1;
                c += 10;
            }
            sb.append(c);
        }
        return sb.reverse().toString();
    }
}