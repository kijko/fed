/*
 * This file is generated by jOOQ.
 */
package pl.edu.prz.baw.houston.fed.db.tables;


import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function7;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import pl.edu.prz.baw.houston.fed.db.Keys;
import pl.edu.prz.baw.houston.fed.db.Public;
import pl.edu.prz.baw.houston.fed.db.tables.records.FedUserRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FedUser extends TableImpl<FedUserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.fed_user</code>
     */
    public static final FedUser FED_USER = new FedUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FedUserRecord> getRecordType() {
        return FedUserRecord.class;
    }

    /**
     * The column <code>public.fed_user.uuid</code>.
     */
    public final TableField<FedUserRecord, String> UUID = createField(DSL.name("uuid"), SQLDataType.VARCHAR(36).nullable(false), this, "");

    /**
     * The column <code>public.fed_user.login_hash</code>.
     */
    public final TableField<FedUserRecord, String> LOGIN_HASH = createField(DSL.name("login_hash"), SQLDataType.CHAR(64).nullable(false), this, "");

    /**
     * The column <code>public.fed_user.password_hash</code>.
     */
    public final TableField<FedUserRecord, String> PASSWORD_HASH = createField(DSL.name("password_hash"), SQLDataType.CHAR(64).nullable(false), this, "");

    /**
     * The column <code>public.fed_user.firstname</code>.
     */
    public final TableField<FedUserRecord, String> FIRSTNAME = createField(DSL.name("firstname"), SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * The column <code>public.fed_user.lastname</code>.
     */
    public final TableField<FedUserRecord, String> LASTNAME = createField(DSL.name("lastname"), SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * The column <code>public.fed_user.blocked</code>.
     */
    public final TableField<FedUserRecord, Boolean> BLOCKED = createField(DSL.name("blocked"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.fed_user.type</code>.
     */
    public final TableField<FedUserRecord, String> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR(32).nullable(false), this, "");

    private FedUser(Name alias, Table<FedUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private FedUser(Name alias, Table<FedUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.fed_user</code> table reference
     */
    public FedUser(String alias) {
        this(DSL.name(alias), FED_USER);
    }

    /**
     * Create an aliased <code>public.fed_user</code> table reference
     */
    public FedUser(Name alias) {
        this(alias, FED_USER);
    }

    /**
     * Create a <code>public.fed_user</code> table reference
     */
    public FedUser() {
        this(DSL.name("fed_user"), null);
    }

    public <O extends Record> FedUser(Table<O> child, ForeignKey<O, FedUserRecord> key) {
        super(child, key, FED_USER);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<FedUserRecord> getPrimaryKey() {
        return Keys.FED_USER_PKEY;
    }

    @Override
    public FedUser as(String alias) {
        return new FedUser(DSL.name(alias), this);
    }

    @Override
    public FedUser as(Name alias) {
        return new FedUser(alias, this);
    }

    @Override
    public FedUser as(Table<?> alias) {
        return new FedUser(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public FedUser rename(String name) {
        return new FedUser(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FedUser rename(Name name) {
        return new FedUser(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public FedUser rename(Table<?> name) {
        return new FedUser(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<String, String, String, String, String, Boolean, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function7<? super String, ? super String, ? super String, ? super String, ? super String, ? super Boolean, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function7<? super String, ? super String, ? super String, ? super String, ? super String, ? super Boolean, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
