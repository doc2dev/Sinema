package com.example.karumbi.moviedb.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.Toolbar;

import com.example.karumbi.moviedb.R;
import com.example.karumbi.moviedb.dependency_injection.NetworkComponent;
import com.example.karumbi.moviedb.dependency_injection.mocks.DaggerTestNetworkComponentInstrumented;
import com.example.karumbi.moviedb.dependency_injection.mocks.TestNetworkModuleInstrumented;
import com.example.karumbi.moviedb.model.Movie;
import com.example.karumbi.moviedb.viewmodel.MovieDetailViewModel;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

/**
 * Created by Eston on 13/10/2017.
 */
public class MovieDetailActivityTest {

    private ActivityTestRule<MovieDetailActivity> testRule
            = new ActivityTestRule<>(MovieDetailActivity.class);

    private Movie movie;

    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }

    @Before
    public void setup() throws Exception {
        NetworkComponent networkComponent = DaggerTestNetworkComponentInstrumented.builder()
                .testNetworkModuleInstrumented(new TestNetworkModuleInstrumented())
                .build();
        MovieDetailViewModel.getInstance().inject(networkComponent);
        movie = new Movie();
        movie.setPosterPath("/c24sv2weTHPsmDa7jEMN0m2P3RT.jpg");
        movie.setId(315635);
        movie.setOverview("Following the events of Captain America: Civil War, Peter Parker, with the help of his mentor Tony Stark, tries to balance his life as an ordinary high school student in Queens, New York City, with fighting crime as his superhero alter ego Spider-Man as a new threat, the Vulture, emerges.");
        movie.setVoteAverage(7.3);
        movie.setTitle("Spider-Man: Homecoming");
        movie.setReleaseDate("2017-07-05");
        MovieDetailViewModel.getInstance().movie = movie;
    }

    @Test
    public void testMovieDetailsShown() {
        Context context = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(context, MovieDetailActivity.class);
        testRule.launchActivity(intent);
        IdlingRegistry.getInstance().register(testRule.getActivity().countingIdlingResource);
        onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(movie.getTitle()))));
        onView(withId(R.id.movie_details))
                .check(matches(withText(movie.getOverview())));
        onView(withId(R.id.movie_date))
                .check(matches(withText("Release date: " + movie.getReleaseDate())));
        onView(withId(R.id.movie_rating))
                .check(matches(withText("Rating: " + String.valueOf(movie.getVoteAverage()))));
    }

}