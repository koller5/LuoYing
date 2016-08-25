/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.object.attribute;

import name.huliqing.core.data.AttributeData;

/**
 * @author huliqing
 */
public class IntegerAttribute extends NumberAttribute<Integer, AttributeData> {

    private int value;

    @Override
    public void setData(AttributeData data) {
        super.setData(data);
        value = data.getAsInteger("value", value);
    }

    @Override
    public AttributeData getData() {
        data.setAttribute("value", value);
        return super.getData(); 
    }
    
    @Override
    public final int intValue() {
        return value;
    }

    @Override
    public final float floatValue() {
        return value;
    }
    
    @Override
    public final Integer getValue() {
        return value;
    }

    @Override
    public final void setValue(final Integer value) {
        setAndNotify(value);
    }
    
    @Override
    public final void add(final int other) {
        setAndNotify(value + other);
    }

    @Override
    public final void add(final float other) {
        setAndNotify((int)(value + other));
    }

    @Override
    public final void subtract(final int other) {
        setAndNotify(value - other);
    }

    @Override
    public final void subtract(final float other) {
        setAndNotify((int)(value - other));
    }

    @Override
    public final boolean isEqualTo(final int other) {
        return value == other;
    }

    @Override
    public final boolean isEqualTo(final float other) {
        return this.value == other;
    }

    @Override
    public final boolean greaterThan(final int other) {
        return value > other;
    }

    @Override
    public final boolean greaterThan(final float other) {
        return value > other;
    }

    @Override
    public final boolean lessThan(final int other) {
        return value < other;
    }

    @Override
    public final boolean lessThan(final float other) {
        return value < other;
    }
    
    @Override
    public final boolean match(final Attribute other) {
        if (other instanceof NumberAttribute) {
            return NumberCompare.isEqualTo(value, (NumberAttribute) other);
        }
        return false;
    }
    
    /**
     * 设置当前属性的值，如果设置后属性值发生改变，则通知监听器(只有在值发生改变才通知监听器).
     * @param value 
     */
    protected void setAndNotify(int value) {
        int oldValue = this.value;
        this.value = value;
        if (oldValue != this.value) {
            notifyValueChangeListeners(oldValue, this.value);
        }
    }
    
}