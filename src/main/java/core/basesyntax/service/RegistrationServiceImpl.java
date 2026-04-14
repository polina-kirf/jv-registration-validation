package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final int MIN_POSSIBLE_AGE = 0;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is required");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login is required");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password is required");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age is required");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login already exists");
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Invalid login length: " + user.getLogin().length()
                    + ". Min allowed login length is " + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Invalid password length: "
                    + user.getPassword().length() + ". Min allowed password length is "
                    + MIN_PASSWORD_LENGTH);
        }

        if (user.getAge() < MIN_POSSIBLE_AGE) {
            throw new RegistrationException("Age cannot be negative");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }

        return storageDao.add(user);
    }
}
