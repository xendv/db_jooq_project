package db.jooq.dao;

import db.jooq.entities.Organization;
import db.jooq.generated.tables.records.OrganizationRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;

import java.sql.Connection;
import java.util.List;

import static db.jooq.generated.tables.Organization.ORGANIZATION;

public class OrganizationDAO extends DAO<Organization>{

    public OrganizationDAO(@NotNull Connection connection) {
        super(connection);
        tableName = "organization";
        primaryKey = "itn";
    }

    @NotNull
    @Override
    public Organization get(int id) {
        final OrganizationRecord organizationRecord = context.fetchOne(ORGANIZATION, ORGANIZATION.ITN.eq(id));
        if (organizationRecord == null)
            throw new IllegalStateException("Record with id " + id + " not found");
        return organizationRecord.into(Organization.class);
    }

    @NotNull
    @Override
    public List<Organization> all() {
        final Result<Record> records = context.select().from(ORGANIZATION).fetch();
        return records.into(Organization.class);
    }

    @Override
    public void save(@NotNull Organization entity) {
        OrganizationRecord organizationRecord = context.newRecord(ORGANIZATION, entity);
        organizationRecord.store();
    }

    @Override
    public void update(@NotNull Organization entity) {
        OrganizationRecord OrganizationRecord = context.newRecord(ORGANIZATION, entity);
        try {
            OrganizationRecord.refresh();
        }
        catch (DataAccessException e){
            throw new DataAccessException("Organization doesn't exist");
        }
    }

    @Override
    public void delete(@NotNull Organization entity) {
        try {
            OrganizationRecord OrganizationRecord = context.fetchOne(ORGANIZATION, ORGANIZATION.ITN.eq(entity.getItn()));
            OrganizationRecord.delete();
        }
        catch (NullPointerException e){
            throw new IllegalStateException("Organization doesn't exist");
        }
    }
}
