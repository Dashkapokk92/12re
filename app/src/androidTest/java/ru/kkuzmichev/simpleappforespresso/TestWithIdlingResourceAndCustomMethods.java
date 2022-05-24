package ru.kkuzmichev.simpleappforespresso;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import static ru.kkuzmichev.simpleappforespresso.CustomViewAssertions.isRecycleView;
import static ru.kkuzmichev.simpleappforespresso.CustomViewMatcher.recyclerViewSizeMatcher;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestWithIdlingResourceAndCustomMethods {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResources() { // Подключаемся к "счетчику"
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource);
    }

    @After
    public void unregisterIdlingResources() { // Отключаемся от "счетчика"
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource);
    }

    @Test
    public void checkItemAndRecycleList() {
        ViewInteraction nav_menu = onView(withContentDescription("Open navigation drawer"));
        ViewInteraction gallery = onView(withId(R.id.nav_gallery));
        ViewInteraction galleryItem = onView(allOf(
                withId(R.id.item_number),
                withText("7")));
        ViewInteraction recycleList = onView(withId(R.id.recycle_view));
        nav_menu.check(matches(isDisplayed()));
        nav_menu.perform(click());
        gallery.check(matches(isDisplayed()));
        gallery.perform(click()); // шаг с длительным ожиданием
        // galleryItem.perform(scrollTo()); // скролл до элемента с текстом 7
        galleryItem.check(matches(isDisplayed()));
        recycleList.check(matches(recyclerViewSizeMatcher(10))); // кастомный метод
        recycleList.check(isRecycleView()); // кастомный метод
    }
}
