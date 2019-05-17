package loh.calvin.imanagead.test_Login;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import loh.calvin.imanagead.LoginPage;
import loh.calvin.imanagead.R;

import static org.junit.Assert.assertNotNull;


public class test_login{

    @Rule
    public ActivityTestRule<LoginPage> mLoginTestRule1 = new ActivityTestRule<LoginPage>(LoginPage.class);

    private LoginPage mLoginTest = null;

    @Before
    public void setUp() throws Exception{
        mLoginTest = mLoginTestRule1.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = mLoginTest.findViewById(R.id.LoginButton);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception{
        mLoginTest = null;
    }

}
