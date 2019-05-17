package loh.calvin.imanagead.test_DescriptionPage;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import loh.calvin.imanagead.DescriptionPage;
import loh.calvin.imanagead.R;

import static org.junit.Assert.assertNotNull;


public class test_save{

    @Rule
    public ActivityTestRule<DescriptionPage> adcd = new ActivityTestRule<DescriptionPage>(DescriptionPage.class);

    private DescriptionPage zxcv = null;

    @Before
    public void setUp() throws Exception{
        zxcv = adcd.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = zxcv.findViewById(R.id.next2);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception{
        zxcv = null;
    }

}
