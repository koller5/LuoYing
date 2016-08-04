/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.data;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.FastMath;
import com.jme3.network.serializing.Serializable;
import java.io.IOException;

/**
 * 状态
 * @author huliqing
 */
@Serializable
public class StateData extends ObjectData {
    
    /**
     * 状态的存在时间,单位秒
     */
    private float useTime;
    
    /**
     * 状态的更新频率,单位秒。该参数用于减少状态的处理频率，以提高性能。
     */
    private float interval;
    
    /**
     * 角色获得状态时的效果,这些效果会在状态开始时附加在角色身上，在状态结束时停止．
     */
    private String[] effects;
    
    // 当角色死亡时从角色身上移除这个状态.
    private boolean removeOnDead;
    
    /**
     * 设置一个对源角色的引用(唯一ID)，即产生这个状态的源角色，如果没有则可以设置为小于
     * 或等于0的值。<br />
     * 说明：<br />
     * 源角色，这个角色主要是指制造这个状态的源角色，比如某些状态是由特定角色发出
     * 的，则这里可以保留一个引用。比如：角色A攻击了角色B, A的这个攻击技能对B产生
     * 了一个“流血”状态。这时A即可以设置为这个“流血”状态的sourceActor。这样状
     * 态在运行时就可以获得源角色的引用，以便知道谁产生了这个状态。对于一些状态
     * 效果非常有用，比如“流血”这类伤害效果状态，这些状态在运行时要计算伤害，并
     * 要知道是谁产生了这些伤害。
     */
    private long sourceActor;
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(useTime, "useTime", 3);
        oc.write(interval, "interval", 3);
        oc.write(effects, "effects", null);
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule ic = im.getCapsule(this);
        useTime = ic.readFloat("useTime", 3);
        interval = ic.readFloat("interval", 3);
        effects = ic.readStringArray("effects", null);
    }
    
    // ----------------------------------------------------- 以下参数不开放到xml
    
    /**
     * 关闭抗性值，取值为0.0~1.0, 这个值会抵消状态的作用，值为0时无抵消作用，值为
     * 1时，则全抵消。
     */
    private float resist;
    
    public StateData() {}

    public float getUseTime() {
        return useTime;
    }

    public void setUseTime(float useTime) {
        this.useTime = useTime;
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public String[] getEffects() {
        return effects;
    }

    public void setEffects(String[] effects) {
        this.effects = effects;
    }

    public float getResist() {
        return resist;
    }

    /**
     * 设置状态削弱值，取值[0.0~1.0],该值主要用于削弱状态的作用，根据各
     * 种状态的实际情况各自实现该功能．0表示状态不削弱，1表示状态完全被削
     * 弱．0.5表示削弱一半，依此类推．示例：如实现一个击晕3秒的状态，如果
     * resist=0.3,则可实现最终的击晕时间为 3 * (1 - 0.3) = 2.1秒，换句话说，
     * 击晕效果被削弱了0.9秒．根据实现的不同，削弱方式可以完全不同，如实现
     * 一个中毒状态效果，你可以实现为削弱中毒时间，也可以实现为削弱中毒伤害
     * 等．
     * @param resist 
     */
    public void setResist(float resist) {
        this.resist = FastMath.clamp(resist, 0f, 1.0f);
    }

    /**
     * @see #setRemoveOnDead(boolean) 
     * @return 
     */
    public boolean isRemoveOnDead() {
        return removeOnDead;
    }

    /**
     * 设置是否在角色死亡时从角色身上移除这个状态.
     * @param removeOnDead 
     */
    public void setRemoveOnDead(boolean removeOnDead) {
        this.removeOnDead = removeOnDead;
    }

    /**
     * 获取产生这个状态效果的源角色,如果返回值为小于或等于0的值，则表示没有
     * 任何源角色。
     * @see #setSourceActor(long) 
     * @return 
     */
    public long getSourceActor() {
        return sourceActor;
    }

    /**
     * 设置一个对源角色的引用(唯一ID)，即产生这个状态的源角色，如果没有则可以设置为小于
     * 或等于0的值。<br />
     * 说明：<br />
     * 源角色，这个角色主要是指制造这个状态的源角色，比如某些状态是由特定角色发出
     * 的，则这里可以保留一个引用。比如：角色A攻击了角色B, A的这个攻击技能对B产生
     * 了一个“流血”状态。这时A即可以设置为这个“流血”状态的sourceActor。这样状
     * 态在运行时就可以获得源角色的引用，以便知道谁产生了这个状态。对于一些状态
     * 效果非常有用，比如“流血”这类伤害效果状态，这些状态在运行时要计算伤害，并
     * 要知道是谁产生了这些伤害。
     */
    public void setSourceActor(long sourceActor) {
        this.sourceActor = sourceActor;
    }
    
    
}
