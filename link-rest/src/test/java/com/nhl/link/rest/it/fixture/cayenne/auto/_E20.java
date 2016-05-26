package com.nhl.link.rest.it.fixture.cayenne.auto;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

import com.nhl.link.rest.it.fixture.cayenne.E21;

/**
 * Class _E20 was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _E20 extends CayenneDataObject {

    private static final long serialVersionUID = 1L; 

    @Deprecated
    public static final String AGE_PROPERTY = "age";
    @Deprecated
    public static final String DESCRIPTION_PROPERTY = "description";
    @Deprecated
    public static final String NAME_PROPERTY = "name";
    @Deprecated
    public static final String E21_PROPERTY = "e21";

    public static final String ID_PK_COLUMN = "id";

    public static final Property<Integer> AGE = new Property<Integer>("age");
    public static final Property<String> DESCRIPTION = new Property<String>("description");
    public static final Property<String> NAME = new Property<String>("name");
    public static final Property<E21> E21 = new Property<E21>("e21");

    public void setAge(Integer age) {
        writeProperty("age", age);
    }
    public Integer getAge() {
        return (Integer)readProperty("age");
    }

    public void setDescription(String description) {
        writeProperty("description", description);
    }
    public String getDescription() {
        return (String)readProperty("description");
    }

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void setE21(E21 e21) {
        setToOneTarget("e21", e21, true);
    }

    public E21 getE21() {
        return (E21)readProperty("e21");
    }


}