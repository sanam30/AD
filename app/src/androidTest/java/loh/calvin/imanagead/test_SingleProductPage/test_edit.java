package loh.calvin.imanagead.test_SingleProductPage;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import loh.calvin.imanagead.R;
import loh.calvin.imanagead.SingleProductPage;

import static org.junit.Assert.assertNotNull;


public class test_edit {

    @Rule
    public ActivityTestRule<SingleProductPage> adcd = new ActivityTestRule<SingleProductPage>(SingleProductPage.class);

    private SingleProductPage zxcv = null;

    @Before
    public void setUp() throws Exception{
        zxcv = adcd.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = zxcv.findViewById(R.id.edit_single_page);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception{
        zxcv = null;
    }

}
