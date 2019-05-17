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


public class test_description {

    @Rule
    public ActivityTestRule<DescriptionPage> asdf = new ActivityTestRule<DescriptionPage>(DescriptionPage.class);

    private DescriptionPage zxc = null;

    @Before
    public void setUp() throws Exception{
        zxc = asdf.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = zxc.findViewById(R.id.ET_ProductDesctiption);
        assertNotNull(view);

    }
    @After
    public void tearDown() throws Exception{
        asdf = null;
    }


}
