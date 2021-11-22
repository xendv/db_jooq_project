package db.jooq.dao;

import db.jooq.entities.Invoice;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

import static db.jooq.generated.tables.Invoice.INVOICE;

public class InvoiceDAO extends DAO<Invoice>{

    public InvoiceDAO(@NotNull Connection connection) {
        super(connection, INVOICE, Invoice.class);
    }
}
