package loh.calvin.imanagead.test_MainPage;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import loh.calvin.imanagead.MainPage;
import loh.calvin.imanagead.R;

import static org.junit.Assert.assertNotNull;


public class test_searchbutton {

    @Rule
    public ActivityTestRule<MainPage> asdf = new ActivityTestRule<MainPage>(MainPage.class);

    private MainPage zxc = null;

    @Before
    public void setUp() throws Exception{
        zxc = asdf.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = zxc.findViewById(R.id.searchtypebutton);
        assertNotNull(view);

    }
    @After
    public void tearDown() throws Exception{
        asdf = null;
    }


}
