package com.bignerdranch.android.beatbox;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * @author admin  on 2019/3/11.
 */
@RunWith(AndroidJUnit4.class)
public class BeatBoxActivityTest {

    @Before
    public void setUp() throws Exception {
        System.out.println();
    }

    @Rule
    public ActivityTestRule<BeatBoxActivity> mActivityActivityTestRule
            = new ActivityTestRule<>(BeatBoxActivity.class);

    @Test
    public void createFragment() {
    }

    @Test
    public void testShowFirstFileName() {
        // TODO: 2019/3/11  这里整合测试出现了问题 "Error: Program type already present: org.hamcrest.Matchers"
        // TODO: 2019/3/12 测试发现是  module.build.gradle 引用了重复的包
        System.out.println(0);
        Matcher<View> viewMatcher = withText("65_cjipie");
        System.out.println(1);
        ViewInteraction viewInteraction = onView(viewMatcher);
        System.out.println(2);
        ViewAssertion matches = matches(anything());
        System.out.println(3);
        viewInteraction.check(matches);
        System.out.println(4);
    }
}