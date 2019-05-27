package loh.calvin.imanagead.test_ProductInformation;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import loh.calvin.imanagead.ProductInformation;
import loh.calvin.imanagead.R;

import static org.junit.Assert.assertNotNull;


public class test_searchbutton{

    @Rule
    public ActivityTestRule<ProductInformation> adcd = new ActivityTestRule<ProductInformation>(ProductInformation.class);

    private ProductInformation zxcv = null;

    @Before
    public void setUp() throws Exception{
        zxcv = adcd.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = zxcv.findViewById(R.id.btn_searchname);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception{
        zxcv = null;
    }

}
