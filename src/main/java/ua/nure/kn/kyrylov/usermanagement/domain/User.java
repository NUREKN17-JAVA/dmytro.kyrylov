package ua.nure.kn.kyrylov.usermanagement.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {

    private static final long serialVersionUID = -7322869543120481946L;

    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public User() {
    }

    public User(Long id, String firstName, String lastName, Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return getLastName() + ", " + getFirstName();
    }

    public String getAgeStr() {
        if (getDateOfBirth() == null) {
            return "";
        }
        Calendar today = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();

        today.setTime(new Date());
        birth.setTime(dateOfBirth);

        today.add(Calendar.DAY_OF_MONTH, -birth.get(Calendar.DAY_OF_MONTH));
        today.add(Calendar.MONTH, -birth.get(Calendar.MONTH));

        int years = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        int months = today.get(Calendar.MONTH);
        int days = today.get(Calendar.DAY_OF_MONTH);

        if (months != 0) ++days;

        if (days == 31) {
            ++months;
            days = 0;
        }

        if (months > 11) {
            ++years;
            months = 0;
        }

        if (years < 0) {
            years = 0;
        }

        return years + "-" + months + "-" + days;
    }

    public int getAge() {
        return Integer.parseInt(getAgeStr().substring(0, getAgeStr().indexOf("-")));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return (id != null ? !id.equals(user.id) : user.id != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        return result;
    }
}
