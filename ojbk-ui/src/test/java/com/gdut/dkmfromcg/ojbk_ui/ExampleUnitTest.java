package com.gdut.dkmfromcg.ojbk_ui;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    /**
     * 验证 ArrayList 的添加顺序和输出顺序
     */
    @Test
    public void addList() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("AAAAA");
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int c= 'B'+i;
            strings.add(String.valueOf(c));
        }
        arrayList.addAll(strings);

        for (String s:arrayList){
            System.out.println(s);
        }
    }
}