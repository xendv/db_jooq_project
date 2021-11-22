package db.jooq.dao;

import db.jooq.entities.Organization;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

import static db.jooq.generated.tables.Organization.ORGANIZATION;

public class OrganizationDAO extends DAO<Organization>{

    public OrganizationDAO(@NotNull Connection connection) {
        super(connection, ORGANIZATION, Organization.class);
    }
}
