package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.Assert;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_GROUP = "#friend";
    private static final String INVALID_PREFERENCE = "#shoes";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_GROUP = "friends"; // A valid group that BENSON has.
    private static final List<XmlAdaptedGroup> VALID_GROUPS = BENSON.getGroupTags().stream()
            .map(XmlAdaptedGroup::new)
            .collect(Collectors.toList());
    private static final String VALID_PREFERENCE = "computers"; // A valid preference that BENSON has.
    private static final List<XmlAdaptedPreference> VALID_PREFERENCES = BENSON.getPreferenceTags().stream()
            .map(XmlAdaptedPreference::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_GROUPS, VALID_PREFERENCES);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_GROUPS, VALID_PREFERENCES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_GROUPS, VALID_PREFERENCES);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                        VALID_GROUPS, VALID_PREFERENCES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_GROUPS, VALID_PREFERENCES);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                        VALID_GROUPS, VALID_PREFERENCES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_GROUPS, VALID_PREFERENCES);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                        VALID_GROUPS, VALID_PREFERENCES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidGroupName_throwsIllegalValueException() {
        List<XmlAdaptedGroup> invalidGroups = new ArrayList<>(VALID_GROUPS);
        invalidGroups.add(new XmlAdaptedGroup(INVALID_GROUP));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        invalidGroups, VALID_PREFERENCES);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_duplicateGroups_throwsIllegalValueException() {
        List<XmlAdaptedGroup> invalidGroups = new ArrayList<>(VALID_GROUPS);
        invalidGroups.add(new XmlAdaptedGroup(VALID_GROUP));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        invalidGroups, VALID_PREFERENCES);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidPreferenceName_throwsIllegalValueException() {
        List<XmlAdaptedPreference> invalidPreferences = new ArrayList<>(VALID_PREFERENCES);
        invalidPreferences.add(new XmlAdaptedPreference(INVALID_PREFERENCE));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_GROUPS, invalidPreferences);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_duplicatePreferences_throwsIllegalValueException() {
        List<XmlAdaptedPreference> invalidPreferences = new ArrayList<>(VALID_PREFERENCES);
        invalidPreferences.add(new XmlAdaptedPreference(VALID_PREFERENCE));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_GROUPS, invalidPreferences);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }
}
