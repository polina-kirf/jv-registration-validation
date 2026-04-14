package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User is required");
        } else if (user.getLogin() == null) {
            throw new RegistrationException("Login is required");
        } else if (user.getPassword() == null) {
            throw new RegistrationException("Password is required");
        } else if (user.getAge() == null) {
            throw new RegistrationException("Age is required");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login already exists");
        }

        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Invalid login");
        } else if (user.getPassword().length() < 6) {
            throw new RegistrationException("Invalid password");
        }

        if (user.getAge() < 18) {
            throw new RegistrationException("Registration failed. Age must be over 18");
        }

        storageDao.add(user);

        return user;
    }
}
