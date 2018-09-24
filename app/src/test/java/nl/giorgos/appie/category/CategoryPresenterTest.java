package nl.giorgos.appie.category;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CategoryPresenterTest {

    @Test
    public void onCountrySelected_us_opensNewActivityWithUs() {
        // arrange
        final CategoryContract.View view = mock(CategoryContract.View.class);
        final CategoryPresenter categoryPresenter = new CategoryPresenter(view);

        // act
        categoryPresenter.onCountrySelected("us");

        // arrange
        view.openNewsActivity("us");
    }
}