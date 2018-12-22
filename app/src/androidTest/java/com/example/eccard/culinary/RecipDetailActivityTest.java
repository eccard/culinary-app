package com.example.eccard.culinary;

import android.content.Intent;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.eccard.culinary.utils.RecyclerViewItemCountAssertion;
import com.example.eccard.culinary.utils.Util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import eccard.adnd.culinary.R;
import eccard.adnd.culinary.network.model.Recip;
import eccard.adnd.culinary.network.model.Step;
import eccard.adnd.culinary.ui.recip_steps.StepDetailsActivity;
import eccard.adnd.culinary.ui.recipdetail.RecipDetailActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipDetailActivityTest {

    private Recip recipe;

    @Rule
    public IntentsTestRule<RecipDetailActivity> mActivityRule =
            new IntentsTestRule<RecipDetailActivity>(
                    RecipDetailActivity.class, false, true) {

                @Override
                protected Intent getActivityIntent() {

                    recipe = Util.getRecipeDataMock();

                    Intent intent = new Intent();
                    intent.putExtra(Recip.class.getSimpleName(), recipe);

                    return intent;
                }
            };

    @Test
    public void all_recipe_infos_areShow(){


        int stepsCount = recipe.getSteps().size();
        int ingredientsCount = recipe.getIngredients().size();
        int headersCount = 1;
        int totalItems = stepsCount +ingredientsCount + headersCount;


        onView(withId(R.id.recycler_view))
                .check(new RecyclerViewItemCountAssertion(totalItems));
    }

    @Test
    public void click_on_step_open_Details_of_step(){

        int stepIdx = 2;

        int stepIdxOnRecyvleView = recipe.getIngredients().size()
                + 1  // header
                + stepIdx;

        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(stepIdxOnRecyvleView,
                        click()));


        Step expectedStep = recipe.getSteps().get(stepIdx);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if ( isTablet ){
            onView(allOf(
                    withId(R.id.tv_step_description),
                    withText(expectedStep.getDescription())
            )).check(matches(isDisplayed()));

        } else {
            intended(allOf(hasComponent(StepDetailsActivity.class.getName()), isInternal()));
        }

    }

    private Resources getResources() {
        return InstrumentationRegistry.getTargetContext().getResources();
    }
}
