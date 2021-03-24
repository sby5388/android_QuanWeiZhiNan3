package com.bignerdranch.android.beatbox;

import org.junit.Test;

/**
 * @author Administrator  on 2020/12/24.
 */
public class ByteTest {
    @Test
    public void test() {
        // TODO: 2020/12/24 java
        //  二进制（0b开头） ，支持八进制（0开头），十进制(非0开头)，十六进制（0x开头）
        //这里居然是8进制
        int a = 0011;
        int b = 0010;

        //按位或
        final int c = a | b;
        System.out.println("c = " + c);
        System.out.println("sc = " + Integer.toBinaryString(c));

        //按位与
        final int d = a & b;
        System.out.println("d = " + d);
        System.out.println("sd = " + Integer.toBinaryString(d));
    }

    @Test
    public void test2() {
        // TODO: 2020/12/24 0b开头，二进制
        int a = 0b0110;
        int b = 0b0101;

        //按位或
        final int c = a | b;
        System.out.println("c = " + c);
        System.out.println("sc = 0b" + Integer.toBinaryString(c));
        System.out.println("10 = " + Integer.toBinaryString(10));

        // TODO: 2020/12/24 这个是什么鬼 0.0
        System.out.println(0e1011);

        // TODO: 2020/12/24   按16进制输出 17->11
        System.out.printf("%x\n", 17);
        // TODO: 2020/12/24 按8进制输出 13->15
        System.out.printf("%o\n", 13);
        System.out.printf("%o\n", 20);

        final int i = Integer.parseInt("0x20".replace("0x", ""), 16);
        System.out.println(i);
        assert i == 32;

        final Integer integer = Integer.valueOf("12", 16);
        System.out.println(integer);

        final int i1 = Integer.parseUnsignedInt("045", 8);
        System.out.println(i1);
    }


}
