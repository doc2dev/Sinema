package com.example.karumbi.moviedb.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.karumbi.moviedb.R;
import com.example.karumbi.moviedb.dependency_injection.NetworkComponent;
import com.example.karumbi.moviedb.dependency_injection.mocks.DaggerTestNetworkComponentInstrumented;
import com.example.karumbi.moviedb.dependency_injection.mocks.TestNetworkModuleInstrumented;
import com.example.karumbi.moviedb.viewmodel.MovieListViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MoviesListActivityTest {

    private ActivityTestRule<MoviesListActivity> testRule
            = new ActivityTestRule<>(MoviesListActivity.class);

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Before
    public void setup() throws Exception {
        NetworkComponent networkComponent = DaggerTestNetworkComponentInstrumented.builder()
                .testNetworkModuleInstrumented(new TestNetworkModuleInstrumented())
                .build();
        MovieListViewModel.getInstance(networkComponent);
    }

    @Test
    public void testMoviesDisplayed() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(context, MoviesListActivity.class);
        testRule.launchActivity(intent);
        IdlingRegistry.getInstance().register(testRule.getActivity().countingIdlingResource);
        onView(withRecyclerView(R.id.recycler).atPosition(0))
                .check(matches(hasDescendant(withText("It (2017)"))));
        onView(withRecyclerView(R.id.recycler).atPosition(1))
                .check(matches(hasDescendant(withText("Minions (2015)"))));
    }

    @Test
    public void testTransitionToDetailView() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(context, MoviesListActivity.class);
        testRule.launchActivity(intent);
        IdlingRegistry.getInstance().register(testRule.getActivity().countingIdlingResource);
        Intents.init();
        onView(withRecyclerView(R.id.recycler).atPosition(0))
                .perform(click());
        intended(hasComponent(MovieDetailActivity.class.getCanonicalName()));
        Intents.release();
    }

    @Test
    public void testMovieSearch() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(context, MoviesListActivity.class);
        testRule.launchActivity(intent);
        IdlingRegistry.getInstance().register(testRule.getActivity().countingIdlingResource);
        onView(withId(R.id.search_button)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("man"), pressImeActionButton());
        onData(anything())
                .inAdapterView(withId(R.id.search_results))
                .atPosition(0)
                .onChildView(withId(R.id.movie_title))
                .check(matches(withText("Spider-Man: Homecoming")));
        onData(anything())
                .inAdapterView(withId(R.id.search_results))
                .atPosition(1)
                .onChildView(withId(R.id.movie_title))
                .check(matches(withText("Wonder Woman")));
    }
}