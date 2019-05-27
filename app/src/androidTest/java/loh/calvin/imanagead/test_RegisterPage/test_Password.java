package loh.calvin.imanagead.test_RegisterPage;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import loh.calvin.imanagead.R;
import loh.calvin.imanagead.RegisterPage;

import static org.junit.Assert.assertNotNull;


public class test_Password{

    @Rule
    public ActivityTestRule<RegisterPage> adcd = new ActivityTestRule<RegisterPage>(RegisterPage.class);

    private RegisterPage zxcv = null;

    @Before
    public void setUp() throws Exception{
        zxcv = adcd.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = zxcv.findViewById(R.id.Register_Password);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception{
        zxcv = null;
    }

}
