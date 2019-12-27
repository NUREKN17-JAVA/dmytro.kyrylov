package ua.nure.kn.kyrylov.usermanagement.db.interfaces.utils;

import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection createConnection() throws DatabaseException;
}
