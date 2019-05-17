package loh.calvin.imanagead.test_AddProductPage;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import loh.calvin.imanagead.AddProductPage;
import loh.calvin.imanagead.R;

import static org.junit.Assert.assertNotNull;


public class test_next{

    @Rule
    public ActivityTestRule<AddProductPage> adcd = new ActivityTestRule<AddProductPage>(AddProductPage.class);

    private AddProductPage zxcv = null;

    @Before
    public void setUp() throws Exception{
        zxcv = adcd.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = zxcv.findViewById(R.id.buttontodes);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception{
        zxcv = null;
    }

}
