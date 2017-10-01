package net.viralpatel.spring.model;

import java.time.LocalDate;

/**
 * Интерфейс для имлементации класса UserRepository
 * Простое подписание договора об использовании определенных методов
 *
 * @author  Azamat Palmakhanbetov
 * @version 1.0
 * @since   2017-10-01
 */
public interface UserDAO {
    public User insert(String firstName, String lastName, LocalDate birthDate);
    public User getUserFromDb(int id);
}
