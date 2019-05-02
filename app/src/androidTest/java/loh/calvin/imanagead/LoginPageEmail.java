package loh.calvin.imanagead;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginPageEmail {

    @Rule
    public ActivityTestRule<LoginPage> mLoginTestRule1= new ActivityTestRule<LoginPage>(LoginPage.class);

    private LoginPage mLoginTest = null;

    @Before
    public void setUp() throws Exception {
        mLoginTest = mLoginTestRule1.getActivity();
    }

    @Test
    public void testLaunch(){
        View view = mLoginTest.findViewById(R.id.Login_Email);
        assertNotNull(view);
    }
    @Test
    public void emailValidator(){
        assertTrue(emailValidator.isValidEmail("calvin@yahoo.com"));
    }
    @After
    public void tearDown() throws Exception {
        mLoginTest = null;
    }

}