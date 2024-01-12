/*
 * This file is generated by jOOQ.
 */
package pl.edu.prz.baw.houston.fed.db.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

import pl.edu.prz.baw.houston.fed.db.tables.FedUser;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FedUserRecord extends UpdatableRecordImpl<FedUserRecord> implements Record7<String, String, String, String, String, Boolean, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.fed_user.uuid</code>.
     */
    public void setUuid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.fed_user.uuid</code>.
     */
    public String getUuid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.fed_user.login_hash</code>.
     */
    public void setLoginHash(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.fed_user.login_hash</code>.
     */
    public String getLoginHash() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.fed_user.password_hash</code>.
     */
    public void setPasswordHash(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.fed_user.password_hash</code>.
     */
    public String getPasswordHash() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.fed_user.firstname</code>.
     */
    public void setFirstname(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.fed_user.firstname</code>.
     */
    public String getFirstname() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.fed_user.lastname</code>.
     */
    public void setLastname(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.fed_user.lastname</code>.
     */
    public String getLastname() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.fed_user.blocked</code>.
     */
    public void setBlocked(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.fed_user.blocked</code>.
     */
    public Boolean getBlocked() {
        return (Boolean) get(5);
    }

    /**
     * Setter for <code>public.fed_user.type</code>.
     */
    public void setType(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.fed_user.type</code>.
     */
    public String getType() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<String, String, String, String, String, Boolean, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<String, String, String, String, String, Boolean, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return FedUser.FED_USER.UUID;
    }

    @Override
    public Field<String> field2() {
        return FedUser.FED_USER.LOGIN_HASH;
    }

    @Override
    public Field<String> field3() {
        return FedUser.FED_USER.PASSWORD_HASH;
    }

    @Override
    public Field<String> field4() {
        return FedUser.FED_USER.FIRSTNAME;
    }

    @Override
    public Field<String> field5() {
        return FedUser.FED_USER.LASTNAME;
    }

    @Override
    public Field<Boolean> field6() {
        return FedUser.FED_USER.BLOCKED;
    }

    @Override
    public Field<String> field7() {
        return FedUser.FED_USER.TYPE;
    }

    @Override
    public String component1() {
        return getUuid();
    }

    @Override
    public String component2() {
        return getLoginHash();
    }

    @Override
    public String component3() {
        return getPasswordHash();
    }

    @Override
    public String component4() {
        return getFirstname();
    }

    @Override
    public String component5() {
        return getLastname();
    }

    @Override
    public Boolean component6() {
        return getBlocked();
    }

    @Override
    public String component7() {
        return getType();
    }

    @Override
    public String value1() {
        return getUuid();
    }

    @Override
    public String value2() {
        return getLoginHash();
    }

    @Override
    public String value3() {
        return getPasswordHash();
    }

    @Override
    public String value4() {
        return getFirstname();
    }

    @Override
    public String value5() {
        return getLastname();
    }

    @Override
    public Boolean value6() {
        return getBlocked();
    }

    @Override
    public String value7() {
        return getType();
    }

    @Override
    public FedUserRecord value1(String value) {
        setUuid(value);
        return this;
    }

    @Override
    public FedUserRecord value2(String value) {
        setLoginHash(value);
        return this;
    }

    @Override
    public FedUserRecord value3(String value) {
        setPasswordHash(value);
        return this;
    }

    @Override
    public FedUserRecord value4(String value) {
        setFirstname(value);
        return this;
    }

    @Override
    public FedUserRecord value5(String value) {
        setLastname(value);
        return this;
    }

    @Override
    public FedUserRecord value6(Boolean value) {
        setBlocked(value);
        return this;
    }

    @Override
    public FedUserRecord value7(String value) {
        setType(value);
        return this;
    }

    @Override
    public FedUserRecord values(String value1, String value2, String value3, String value4, String value5, Boolean value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FedUserRecord
     */
    public FedUserRecord() {
        super(FedUser.FED_USER);
    }

    /**
     * Create a detached, initialised FedUserRecord
     */
    public FedUserRecord(String uuid, String loginHash, String passwordHash, String firstname, String lastname, Boolean blocked, String type) {
        super(FedUser.FED_USER);

        setUuid(uuid);
        setLoginHash(loginHash);
        setPasswordHash(passwordHash);
        setFirstname(firstname);
        setLastname(lastname);
        setBlocked(blocked);
        setType(type);
        resetChangedOnNotNull();
    }
}
