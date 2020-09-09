package com.liyongquan.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 实现一个基本的计算器来计算一个简单的字符串表达式的值。
 * <p>
 * 字符串表达式仅包含非负整数，+， - ，*，/ 四种运算符和空格  。 整数除法仅保留整数部分。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "3+2*2"
 * 输出: 7
 * 示例 2:
 * <p>
 * 输入: " 3/2 "
 * 输出: 1
 * 示例 3:
 * <p>
 * 输入: " 3+5 / 2 "
 * 输出: 5
 * 说明：
 * <p>
 * 你可以假设所给定的表达式都是有效的。
 * 请不要使用内置的库函数 eval。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/basic-calculator-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Calculate {
    /**
     * 中序表达式其实是比较难实现的，实现思路是把 乘除运算 都出栈算好并放入栈内，让栈里只保存加的运算
     * <p>
     * 还需要有一个简单的词法分析器(lex)
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        int num = 0;
        //lex,词法分析
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                num = num * 10 + s.charAt(i) - '0';
                if (i == s.length() - 1) {
                    tokens.add(new Token(0, num));
                }
            } else if (s.charAt(i) == '+' || s.charAt(i) == '-'
                    || s.charAt(i) == '*' || s.charAt(i) == '/') {
                tokens.add(new Token(0, num));
                switch (s.charAt(i)) {
                    case '+':
                        tokens.add(new Token(1, 0));
                        break;
                    case '-':
                        tokens.add(new Token(1, 1));
                        break;
                    case '*':
                        tokens.add(new Token(1, 2));
                        break;
                    default:
                        tokens.add(new Token(1, 3));
                }
                num = 0;
            } else {
                tokens.add(new Token(0, num));
                num = 0;
            }
        }
        //语义分析
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.type == 0) {
                stack.add(token.value);
            } else {
                i++;
                if (token.value == 0) {
                    stack.push(tokens.get(i).value);
                } else if (token.value == 2) {
                    stack.push(-tokens.get(i).value);
                } else if (token.value == 3) {
                    Integer pop = stack.pop();
                    stack.push(pop * tokens.get(i).value);
                } else {
                    Integer pop = stack.pop();
                    stack.push(pop / tokens.get(i).value);
                }
            }
        }
        //计算栈的数据总和
        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        return sum;
    }

    static class Token {
        //0--数字,1--操作符
        int type;
        //type=0,value为对应的数字;type=1,value=0,1,2,3分别对应+-*/
        int value;

        public Token(int type, int value) {
            this.type = type;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Calculate calculate = new Calculate();
        int r = calculate.calculate("3+2*2");
        System.out.println(r);
    }
}
