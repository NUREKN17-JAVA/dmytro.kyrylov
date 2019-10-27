package ua.nure.kn.kyrylov.usermanagement;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class UserTest extends TestCase {

    private static final int INIT_USER_BIRTH_YEAR = 1997;
    private static final int INIT_USER_BIRTH_MONTH = Calendar.NOVEMBER;
    private static final int INIT_USER_BIRTH_DAY = 18;
    private static final String INIT_USER_FIRST_NAME = "Дмитрий";
    private static final String INIT_USER_LAST_NAME = "Кириллов";
    private static final long INIT_USER_ID = 1L;

    private static final String REFERENCE_USER_FULL_NAME = "Кириллов, Дмитрий";
    private static final int REFERENCE_USER_AGE_IN_YEARS = 21;
    private static final int REFERENCE_USER_AGE_IN_MONTHS = 263;
    private static final int REFERENCE_USER_AGE_IN_DAYS = 8379;
    private static final String REFERENCE_USER_AGE = "21-11-10";

    private User user;
    private int[] ageAsAnIntArray;

    public void setUp() throws Exception {
        Calendar userBirthDay = Calendar.getInstance();
        userBirthDay.set(INIT_USER_BIRTH_YEAR, INIT_USER_BIRTH_MONTH, INIT_USER_BIRTH_DAY);
        Date dateOfBirth = userBirthDay.getTime();

        setUser(new User(INIT_USER_ID, INIT_USER_FIRST_NAME, INIT_USER_LAST_NAME, dateOfBirth));

        setAgeAsAnIntArray(parseAgeToIntArray(getUser().getAgeStr()));
    }

    public void testGetFullName() {
        assertEquals(REFERENCE_USER_FULL_NAME, getUser().getFullName());
    }

    public void testGetAge() {
        assertEquals(REFERENCE_USER_AGE, getUser().getAgeStr());
    }

    public void testGetAgeYear() {
        assertEquals(REFERENCE_USER_AGE_IN_YEARS, getUser().getAge());
    }

    public void testGetAgeInMonths() {
        int ageInMonths = getAgeAsAnIntArray()[0] * 12 + getAgeAsAnIntArray()[1];
        assertEquals(REFERENCE_USER_AGE_IN_MONTHS, ageInMonths);
    }

    public void testGetAgeDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(INIT_USER_BIRTH_YEAR, Calendar.JANUARY, INIT_USER_BIRTH_DAY);

        long ageInDays = getAgeAsAnIntArray()[2];

        for (int year = 0; year <= getAgeAsAnIntArray()[0]; ++year) {
            ageInDays += calendar.get(Calendar.YEAR) % 4 == 0 ? 366 : 365;
            calendar.add(Calendar.YEAR, 1);
        }

        for (int i = 0; i < getAgeAsAnIntArray()[1]; ++i) {
            calendar.set(calendar.get(Calendar.YEAR), i, 1);
            ageInDays += calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        assertEquals(REFERENCE_USER_AGE_IN_DAYS, ageInDays);
    }

    private int[] parseAgeToIntArray(String age) {
        int[] difference = new int[3];

        int firstSeparator = age.indexOf("-");
        int lastSeparator = age.lastIndexOf("-");

        difference[0] = Integer.parseInt(age.substring(0, firstSeparator));
        difference[1] = Integer.parseInt(age.substring(firstSeparator + 1, lastSeparator));
        difference[2] = Integer.parseInt(age.substring(lastSeparator + 1));

        return difference;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int[] getAgeAsAnIntArray() {
        return ageAsAnIntArray;
    }

    public void setAgeAsAnIntArray(int[] ageAsAnIntArray) {
        this.ageAsAnIntArray = ageAsAnIntArray;
    }
}