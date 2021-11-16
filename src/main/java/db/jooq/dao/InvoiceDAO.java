package db.jooq.dao;

import db.jooq.entities.Invoice;
import db.jooq.generated.tables.records.InvoiceRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;

import java.sql.Connection;
import java.util.List;

import static db.jooq.generated.tables.Invoice.INVOICE;

public class InvoiceDAO extends DAO<Invoice>{

    public InvoiceDAO(@NotNull Connection connection) {
        super(connection);
        tableName = "invoice";
        primaryKey = "id";
    }

    @NotNull
    @Override
    public Invoice get(int id) {
        final InvoiceRecord invoiceRecord = context.fetchOne(INVOICE, INVOICE.ID.eq(id));
        if (invoiceRecord == null)
            throw new IllegalStateException("Record with id " + id + " not found");
        return invoiceRecord.into(Invoice.class);
    }

    @NotNull
    @Override
    public List<Invoice> all() {
        final Result<Record> records = context.select().from(INVOICE).fetch();
        return records.into(Invoice.class);
    }

    @Override
    public void save(@NotNull Invoice entity) {
        InvoiceRecord invoiceRecord = context.newRecord(INVOICE, entity);
        invoiceRecord.store();
    }

    @Override
    public void update(@NotNull Invoice entity) {
        InvoiceRecord invoiceRecord = context.newRecord(INVOICE, entity);
        try {
            invoiceRecord.refresh();
        }
        catch (DataAccessException e){
            throw new DataAccessException("Invoice doesn't exist");
        }
    }

    @Override
    public void delete(@NotNull Invoice entity) {
        try {
            InvoiceRecord invoiceRecord = context.fetchOne(INVOICE, INVOICE.ID.eq(entity.getId()));
            invoiceRecord.delete();
        }
        catch (NullPointerException e){
            throw new IllegalStateException("Invoice doesn't exist");
        }
    }
}
