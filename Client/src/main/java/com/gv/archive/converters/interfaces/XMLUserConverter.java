package com.gv.archive.converters.interfaces;

import com.gv.archive.models.User;

/**
 * defines contract for converting model user to xml string and vice versa
 * @see com.gv.archive.models.User
 */
public interface XMLUserConverter {

    /**
     * converts xml string to User model
     * @param xmlUser - string contains xml information about user
     * @return User object
     */
    User convertXMLStringToUserObject(String xmlUser);

    /**
     * converts User model to xml string
     * @param user - User object
     * @return xml string
     */
    String convertUserToXMLString(User user);
}
