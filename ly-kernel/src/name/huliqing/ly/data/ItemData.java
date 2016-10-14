/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.ly.data;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.network.serializing.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import name.huliqing.ly.data.define.CostObject;

/**
 * 基本的原始物体数据
 * @author huliqing
 */
@Serializable
public class ItemData extends ObjectData implements CostObject{
    
    // 属性限制
    private List<AttributeMatch> matchAttributes;
    
    // 物品数量
    protected int total = 1;

    /**
     * 获取属性限制
     * @return 
     */
    public List<AttributeMatch> getMatchAttributes() {
        return matchAttributes;
    }

    /**
     * 设置使用物品时的属性限制。
     * @param matchAttributes 
     */
    public void setMatchAttributes(List<AttributeMatch> matchAttributes) {
        this.matchAttributes = matchAttributes;
    } 
    
    /**
     * 获取物品数量
     * @return 
     */
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    @Override
    public float getCost() {
        return getAsFloat("cost", 0);
    }
    
//    /**
//     * 增加物品数量(amount<0为减少).
//     * @param amount 
//     * @return  
//     */
//    public int increaseTotal(int amount) {
//        total += amount;
//        if (total < 0) {
//            total = 0;
//        }
//        return total;
//    }
    
    /**
     * 判断物品是否是可删除的 
     * @return 
     */
    public boolean isDeletable() {
        return getAsBoolean("deletable", true);
    }

    /**
     * 设置物品是否是可删除的
     * @param deletable 
     */
    public void setDeletable(boolean deletable) {
        setAttribute("deletable", deletable);
    }
    
    /**
     * 判断物品是否是可以出售的
     * @return 
     */
    public boolean isSellable() {
        return getAsBoolean("sellable", true);
    }
    
    /**
     * 设置物品是否是可以出售的。
     * @param sellable 
     */
    public void setSellable(boolean sellable) {
        setAttribute("sellable", sellable);
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(total, "total", 0);
        if (matchAttributes != null) {
            oc.writeSavableArrayList(new ArrayList<AttributeMatch>(matchAttributes), "matchAttributes", null);
        }
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule ic = im.getCapsule(this);
        total = ic.readInt("total", 0);
        matchAttributes = ic.readSavableArrayList("matchAttributes", null);
    }
}