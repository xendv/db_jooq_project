package db.jooq.dao;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.impl.UpdatableRecordImpl;

import java.sql.Connection;
import java.util.List;

public abstract class DAO<T> implements IDAO<T> {

        protected String sql;

        protected final DSLContext context;
        protected TableImpl<?> tableGenerated;
        protected Class<?> recordTypeGenerated;
        protected Class<T> returnClassType;
        protected TableField<?, ?> pk;

        public DAO(Connection connection, TableImpl<?> tableGenerated, Class<T> returnClassType){
                this.context = DSL.using(connection, SQLDialect.POSTGRES);
                this.tableGenerated = tableGenerated;
                this.recordTypeGenerated = tableGenerated.getRecordType();
                this.returnClassType = returnClassType;

                var p = tableGenerated.getPrimaryKey();
                if (p!=null)
                        this.pk = p.getFieldsArray()[0];
        }

        @Override
        @NotNull
        public List<T> all() {
                return context.select().from(tableGenerated).fetchInto(returnClassType);
        }

        @Override
        @NotNull
        public T get(int id){
                var x = context.selectFrom(tableGenerated).where(((TableField)pk).eq(id)).fetchOne();
                if (x == null) throw new IllegalStateException("No record found");
                else return x.into(returnClassType);
        }

        @Override
        public void save(@NotNull T entity) {
                var rec = context.newRecord(tableGenerated, entity);
                ((UpdatableRecordImpl<?>)rec).store();
        }

        @Override
        public void update(@NotNull T entity) {
                var rec = context.newRecord(tableGenerated, entity);
                ((UpdatableRecordImpl<?>)rec).refresh();
        }
        @Override
        public void delete(@NotNull T entity) {
                var rec = (UpdatableRecord<?>)context.newRecord(tableGenerated, entity);
                if (context.executeDelete(rec) == 0)
                        throw new IllegalStateException("No record found");
        }
}
