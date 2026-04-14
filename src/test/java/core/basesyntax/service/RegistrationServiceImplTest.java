package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl service;
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        service = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        User user = new User();

        user.setLogin(null);
        user.setPassword(null);
        user.setAge(null);

        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
        Assertions.assertNull(storageDao.get(String.valueOf(user)));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();

        user.setLogin(null);

        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
        Assertions.assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();

        user.setPassword(null);

        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
        Assertions.assertNull(storageDao.get(user.getPassword()));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();

        user.setAge(null);

        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
        Assertions.assertNull(storageDao.get(String.valueOf(user.getAge())));
    }

    @Test
    void register_validUser_Ok() {
        User user = new User();

        user.setLogin("login123");
        user.setPassword("password123");
        user.setAge(19);

        Assertions.assertNull(storageDao.get(user.getLogin()));

        User result = service.register(user);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getLogin(), result.getLogin());
        Assertions.assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();

        user.setLogin("log1");

        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
        Assertions.assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User existing = new User();

        existing.setLogin("login789");
        existing.setPassword("password456");
        existing.setAge(25);

        Storage.people.add(existing);

        User duplicate = new User();

        duplicate.setLogin("login789");
        duplicate.setPassword("passWord098");
        duplicate.setAge(22);

        Assertions.assertThrows(RegistrationException.class, () -> service.register(duplicate));
        Assertions.assertEquals(existing, storageDao.get("login789"));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();

        user.setPassword("pass3");

        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
        Assertions.assertNull(storageDao.get(user.getPassword()));
    }

    @Test
    void register_underage_notOk() {
        User user = new User();

        user.setAge(15);

        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
        Assertions.assertNull(storageDao.get(String.valueOf(user.getAge())));
    }

    @Test
    void register_validUser_storageContainsUser_ok() {
        User user = new User();

        user.setLogin("login456");
        user.setPassword("password789");
        user.setAge(20);

        Assertions.assertNull(storageDao.get(user.getLogin()));

        User registered = service.register(user);

        Assertions.assertNotNull(registered);
        Assertions.assertEquals(user.getLogin(), registered.getLogin());
        Assertions.assertEquals(user, storageDao.get(user.getLogin()));
    }
}
