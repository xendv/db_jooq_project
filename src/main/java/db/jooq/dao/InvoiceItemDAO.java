package db.jooq.dao;

import db.jooq.entities.InvoiceItem;
import db.jooq.generated.tables.records.InvoiceItemRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;

import java.sql.Connection;
import java.util.List;

import static db.jooq.generated.tables.InvoiceItem.INVOICE_ITEM;

public class InvoiceItemDAO extends DAO<InvoiceItem>{

    public InvoiceItemDAO(@NotNull Connection connection) {
        super(connection);
        tableName = "invoice_item";
        primaryKey = "id";
    }

    @NotNull
    @Override
    public InvoiceItem get(int id) {
        final InvoiceItemRecord invoiceItemRecord = context.fetchOne(INVOICE_ITEM, INVOICE_ITEM.ID.eq(id));
        if (invoiceItemRecord == null)
            throw new IllegalStateException("Record with id " + id + " not found");
        return invoiceItemRecord.into(InvoiceItem.class);
    }

    @NotNull
    @Override
    public List<InvoiceItem> all() {
        final Result<Record> records = context.select().from(INVOICE_ITEM).fetch();
        return records.into(InvoiceItem.class);
    }

    @Override
    public void save(@NotNull InvoiceItem entity) {
        InvoiceItemRecord invoiceItemRecord = context.newRecord(INVOICE_ITEM, entity);
        invoiceItemRecord.store();
    }

    @Override
    public void update(@NotNull InvoiceItem entity) {
        InvoiceItemRecord invoiceItemRecord = context.newRecord(INVOICE_ITEM, entity);
        try {
            invoiceItemRecord.refresh();
        }
        catch (DataAccessException e){
            throw new DataAccessException("Invoice item doesn't exist");
        }
    }

    @Override
    public void delete(@NotNull InvoiceItem entity) {
        try {
            InvoiceItemRecord invoiceItemRecord = context.fetchOne(INVOICE_ITEM, INVOICE_ITEM.ID.eq(entity.getId()));
            invoiceItemRecord.delete();
        }
        catch (NullPointerException e){
            throw new IllegalStateException("Invoice Item doesn't exist");
        }
    }
}
