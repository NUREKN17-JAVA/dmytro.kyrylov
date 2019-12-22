package ua.nure.kn.kyrylov.usermanagement.web;

import ua.nure.kn.kyrylov.usermanagement.domain.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BrowseServletTest extends MockServletTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    public void testBrowse() {
        User user = new User(1000L, "Dima", "Kirillov", new Date());
        List<User> users = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findAllUsers", users);
        doGet();
        List<User> users1 = (List<User>) getWebMockObjectFactory().getMockSession().getAttribute("users");
        assertNotNull("Cannot find users", users1);
        assertSame(users, users1);
    }

    public void testEdit() {
        User user = new User(1000L, "Dima", "Kirillov", new Date());
        getMockUserDao().expectAndReturn("findUser", 1000L, user);
        addRequestParameter("editButton", "Edit");
        addRequestParameter("id", "1000");
        doPost();
        User user1 = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull("Cannot find user", user1);
        assertSame(user, user1);
    }
}
