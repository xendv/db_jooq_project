/*
 * This file is generated by jOOQ.
 */
package db.jooq.generated.tables;


import db.jooq.generated.Keys;
import db.jooq.generated.Public;
import db.jooq.generated.tables.records.InvoiceRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Invoice extends TableImpl<InvoiceRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.invoice</code>
     */
    public static final Invoice INVOICE = new Invoice();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<InvoiceRecord> getRecordType() {
        return InvoiceRecord.class;
    }

    /**
     * The column <code>public.invoice.id</code>.
     */
    public final TableField<InvoiceRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.invoice.date</code>.
     */
    public final TableField<InvoiceRecord, LocalDate> DATE = createField(DSL.name("date"), SQLDataType.LOCALDATE.nullable(false), this, "");

    /**
     * The column <code>public.invoice.sender_org_itn</code>.
     */
    public final TableField<InvoiceRecord, Integer> SENDER_ORG_ITN = createField(DSL.name("sender_org_itn"), SQLDataType.INTEGER.nullable(false), this, "");

    private Invoice(Name alias, Table<InvoiceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Invoice(Name alias, Table<InvoiceRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.invoice</code> table reference
     */
    public Invoice(String alias) {
        this(DSL.name(alias), INVOICE);
    }

    /**
     * Create an aliased <code>public.invoice</code> table reference
     */
    public Invoice(Name alias) {
        this(alias, INVOICE);
    }

    /**
     * Create a <code>public.invoice</code> table reference
     */
    public Invoice() {
        this(DSL.name("invoice"), null);
    }

    public <O extends Record> Invoice(Table<O> child, ForeignKey<O, InvoiceRecord> key) {
        super(child, key, INVOICE);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<InvoiceRecord, Integer> getIdentity() {
        return (Identity<InvoiceRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<InvoiceRecord> getPrimaryKey() {
        return Keys.INVOICE_PK;
    }

    @Override
    public List<ForeignKey<InvoiceRecord, ?>> getReferences() {
        return Arrays.asList(Keys.INVOICE__INVOICE_SENDER_ORG_ITN_FKEY);
    }

    private transient Organization _organization;

    public Organization organization() {
        if (_organization == null)
            _organization = new Organization(this, Keys.INVOICE__INVOICE_SENDER_ORG_ITN_FKEY);

        return _organization;
    }

    @Override
    public Invoice as(String alias) {
        return new Invoice(DSL.name(alias), this);
    }

    @Override
    public Invoice as(Name alias) {
        return new Invoice(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Invoice rename(String name) {
        return new Invoice(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Invoice rename(Name name) {
        return new Invoice(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, LocalDate, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
