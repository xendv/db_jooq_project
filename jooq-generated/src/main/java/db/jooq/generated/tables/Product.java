/*
 * This file is generated by jOOQ.
 */
package db.jooq.generated.tables;


import db.jooq.generated.Keys;
import db.jooq.generated.Public;
import db.jooq.generated.tables.records.ProductRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Product extends TableImpl<ProductRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.product</code>
     */
    public static final Product PRODUCT = new Product();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProductRecord> getRecordType() {
        return ProductRecord.class;
    }

    /**
     * The column <code>public.product.internal_code</code>.
     */
    public final TableField<ProductRecord, Integer> INTERNAL_CODE = createField(DSL.name("internal_code"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.product.name</code>.
     */
    public final TableField<ProductRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR.nullable(false), this, "");

    private Product(Name alias, Table<ProductRecord> aliased) {
        this(alias, aliased, null);
    }

    private Product(Name alias, Table<ProductRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.product</code> table reference
     */
    public Product(String alias) {
        this(DSL.name(alias), PRODUCT);
    }

    /**
     * Create an aliased <code>public.product</code> table reference
     */
    public Product(Name alias) {
        this(alias, PRODUCT);
    }

    /**
     * Create a <code>public.product</code> table reference
     */
    public Product() {
        this(DSL.name("product"), null);
    }

    public <O extends Record> Product(Table<O> child, ForeignKey<O, ProductRecord> key) {
        super(child, key, PRODUCT);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ProductRecord> getPrimaryKey() {
        return Keys.PRODUCT_PK;
    }

    @Override
    public Product as(String alias) {
        return new Product(DSL.name(alias), this);
    }

    @Override
    public Product as(Name alias) {
        return new Product(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Product rename(String name) {
        return new Product(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Product rename(Name name) {
        return new Product(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
