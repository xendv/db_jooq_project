package db.jooq.dao;

import db.jooq.entities.InvoiceItem;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

import static db.jooq.generated.tables.InvoiceItem.INVOICE_ITEM;

public class InvoiceItemDAO extends DAO<InvoiceItem>{

    public InvoiceItemDAO(@NotNull Connection connection) {
        super(connection, INVOICE_ITEM, InvoiceItem.class);
    }
}
