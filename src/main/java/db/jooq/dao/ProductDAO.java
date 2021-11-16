package db.jooq.dao;

import db.jooq.entities.Invoice;
import db.jooq.entities.Product;
import db.jooq.generated.tables.records.InvoiceRecord;
import db.jooq.generated.tables.records.ProductRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static db.jooq.generated.tables.Invoice.INVOICE;
import static db.jooq.generated.tables.Product.PRODUCT;

public class ProductDAO extends DAO<Product>{

    public ProductDAO(@NotNull Connection connection) {
        super(connection);
        tableName = "product";
        primaryKey = "internal_code";
    }

    @NotNull
    @Override
    public Product get(int id) {
        final ProductRecord productRecord = context.fetchOne(PRODUCT, PRODUCT.INTERNAL_CODE.eq(id));
        if (productRecord == null)
            throw new IllegalStateException("Record with id " + id + " not found");
            return productRecord.into(Product.class);
    }

    @NotNull
    @Override
    public List<Product> all() {
        final Result<Record> records = context.select().from(PRODUCT).fetch();
        return records.into(Product.class);
    }

    @Override
    public void save(@NotNull Product entity) {
        ProductRecord productRecord = context.newRecord(PRODUCT, entity);
        productRecord.store();
    }

    @Override
    public void update(@NotNull Product entity) {
        ProductRecord productRecord = context.newRecord(PRODUCT, entity);
        try {
            productRecord.refresh();
        }
        catch (DataAccessException e){
            throw new DataAccessException("Product doesn't exist");
        }
    }

    @Override
    public void delete(@NotNull Product entity) {
        try {
            ProductRecord productRecord = context.fetchOne(PRODUCT, PRODUCT.INTERNAL_CODE.eq(entity.getInternalCode()));
            productRecord.delete();
        }
        catch (NullPointerException e){
            throw new IllegalStateException("Product doesn't exist");
        }
    }
}
