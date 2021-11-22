/*
 * This file is generated by jOOQ.
 */
package db.jooq.generated.tables.records;


import db.jooq.generated.tables.InvoiceItem;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class InvoiceItemRecord extends UpdatableRecordImpl<InvoiceItemRecord> implements Record5<Integer, Integer, Integer, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.invoice_item.id</code>.
     */
    public InvoiceItemRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.invoice_item.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.invoice_item.invoice_id</code>.
     */
    public InvoiceItemRecord setInvoiceId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.invoice_item.invoice_id</code>.
     */
    public Integer getInvoiceId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.invoice_item.product_code</code>.
     */
    public InvoiceItemRecord setProductCode(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.invoice_item.product_code</code>.
     */
    public Integer getProductCode() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.invoice_item.price</code>.
     */
    public InvoiceItemRecord setPrice(Integer value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.invoice_item.price</code>.
     */
    public Integer getPrice() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.invoice_item.quantity</code>.
     */
    public InvoiceItemRecord setQuantity(Integer value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.invoice_item.quantity</code>.
     */
    public Integer getQuantity() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Integer, Integer, Integer, Integer, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return InvoiceItem.INVOICE_ITEM.ID;
    }

    @Override
    public Field<Integer> field2() {
        return InvoiceItem.INVOICE_ITEM.INVOICE_ID;
    }

    @Override
    public Field<Integer> field3() {
        return InvoiceItem.INVOICE_ITEM.PRODUCT_CODE;
    }

    @Override
    public Field<Integer> field4() {
        return InvoiceItem.INVOICE_ITEM.PRICE;
    }

    @Override
    public Field<Integer> field5() {
        return InvoiceItem.INVOICE_ITEM.QUANTITY;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getInvoiceId();
    }

    @Override
    public Integer component3() {
        return getProductCode();
    }

    @Override
    public Integer component4() {
        return getPrice();
    }

    @Override
    public Integer component5() {
        return getQuantity();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getInvoiceId();
    }

    @Override
    public Integer value3() {
        return getProductCode();
    }

    @Override
    public Integer value4() {
        return getPrice();
    }

    @Override
    public Integer value5() {
        return getQuantity();
    }

    @Override
    public InvoiceItemRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public InvoiceItemRecord value2(Integer value) {
        setInvoiceId(value);
        return this;
    }

    @Override
    public InvoiceItemRecord value3(Integer value) {
        setProductCode(value);
        return this;
    }

    @Override
    public InvoiceItemRecord value4(Integer value) {
        setPrice(value);
        return this;
    }

    @Override
    public InvoiceItemRecord value5(Integer value) {
        setQuantity(value);
        return this;
    }

    @Override
    public InvoiceItemRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached InvoiceItemRecord
     */
    public InvoiceItemRecord() {
        super(InvoiceItem.INVOICE_ITEM);
    }

    /**
     * Create a detached, initialised InvoiceItemRecord
     */
    public InvoiceItemRecord(Integer id, Integer invoiceId, Integer productCode, Integer price, Integer quantity) {
        super(InvoiceItem.INVOICE_ITEM);

        setId(id);
        setInvoiceId(invoiceId);
        setProductCode(productCode);
        setPrice(price);
        setQuantity(quantity);
    }
}
