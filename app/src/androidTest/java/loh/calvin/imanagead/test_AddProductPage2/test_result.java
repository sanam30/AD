package loh.calvin.imanagead.test_AddProductPage2;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import loh.calvin.imanagead.AddProductPage2;
import loh.calvin.imanagead.R;

import static org.junit.Assert.assertNotNull;


public class test_result{

    @Rule
    public ActivityTestRule<AddProductPage2> adcd = new ActivityTestRule<AddProductPage2>(AddProductPage2.class);

    private AddProductPage2 zxcv = null;

    @Before
    public void setUp() throws Exception{
        zxcv = adcd.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = zxcv.findViewById(R.id.ResultBarcode);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception{
        zxcv = null;
    }

}
