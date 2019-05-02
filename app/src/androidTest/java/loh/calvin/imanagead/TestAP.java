package loh.calvin.imanagead;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAP {

    @Rule
    public ActivityTestRule<AddProductPage> mAddTestRule1= new ActivityTestRule<AddProductPage>(AddProductPage.class);

    private AddProductPage mAddTest = null;

    @Before
    public void setUp() throws Exception {
        mAddTest = mAddTestRule1.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mAddTest.findViewById(R.id.buttontodes);
        assertNotNull(view);
    }
    @After
    public void tearDown() throws Exception {
        mAddTest = null;
    }

}