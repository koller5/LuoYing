/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.luoying.layer.service;

import name.huliqing.luoying.object.attribute.BooleanAttribute;
import name.huliqing.luoying.object.attribute.NumberAttribute;
import name.huliqing.luoying.object.entity.Entity;

/**
 *
 * @author huliqing
 */
public class EntityServiceImpl implements EntityService {
//    private static final Logger LOG = Logger.getLogger(EntityServiceImpl.class.getName());
    
    @Override
    public void inject() {
    }
        
    @Override
    public void hitAttribute(Entity entity, String attribute, Object value, Entity hitter) {
        entity.hitAttribute(attribute, value, hitter);
    }
    
    @Override
    public void hitNumberAttribute(Entity entity, String attribute, float addValue, Entity hitter) {
        NumberAttribute nattr = entity.getAttributeManager().getAttribute(attribute, NumberAttribute.class);
        if (nattr != null) {
            entity.hitAttribute(attribute, nattr.getValue().floatValue() + addValue, hitter);
        } 
    }
    
    @Override
    public Number getNumberAttributeValue(Entity entity, String attributeName, float defValue) {
        NumberAttribute nattr = entity.getAttributeManager().getAttribute(attributeName, NumberAttribute.class);
        if (nattr != null) {
            return nattr.getValue().floatValue();
        }
        return defValue;
    }

    @Override
    public boolean getBooleanAttributeValue(Entity entity, String attributeName, boolean defValue) {
        BooleanAttribute attr = entity.getAttributeManager().getAttribute(attributeName, BooleanAttribute.class);
        if (attr != null) {
            return attr.getValue();
        }
        return defValue;
    }

    
}
