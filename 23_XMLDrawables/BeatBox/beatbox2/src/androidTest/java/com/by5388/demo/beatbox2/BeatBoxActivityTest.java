package com.by5388.demo.beatbox2;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * 整合测试
 *
 * @author Administrator  on 2019/12/5.
 */
// TODO: 2019/12/5  @RunWith(AndroidJUnit4.class)
//  这是一个Android工具测试，需要activity和其他Android运行时环境支持
@RunWith(AndroidJUnit4.class)
public class BeatBoxActivityTest {
    private static final String FIRST_FILE_NAME = "65_cjipie";

    // TODO: 2019/12/5 @Rule
    //  @Rule注解告诉JUnit，运行测试前，要启动一个BeatBoxActivity实例
    @Rule
    public ActivityTestRule<BeatBoxActivity> mActivityRule = new ActivityTestRule<>(BeatBoxActivity.class);


    @Test
    public void testShowFirstFileName() {
        System.out.println(0);
        final Matcher<View> viewMatcher = withText("65_cjipie");
        System.out.println(1);
        // TODO: 2019/12/5 这里测试失败了
        final ViewInteraction viewInteraction = onView(viewMatcher);
        System.out.println(2);
        final ViewAssertion matches = matches(anything());
        System.out.println(3);
        final ViewInteraction check = viewInteraction.check(matches);
        System.out.println(4);
        if (true) {
            return;
        }
        // TODO: 2019/12/5 Espresso：浓缩咖啡
//        Espresso.onView(ViewMatchers.withText(FIRST_FILE_NAME))
//                .check(
//                        ViewAssertions.matches(
//                                Matchers.anything()));
        // TODO: 2019/12/5 模拟这个带指定文字的视图 ->点击事件
        //Espresso.onView(ViewMatchers.withText(FIRST_FILE_NAME)).perform(ViewActions.click());
        // TODO: 2019/12/5 长按事件
        //Espresso.onView(ViewMatchers.withText(FIRST_FILE_NAME)).perform(ViewActions.longClick());
    }
}