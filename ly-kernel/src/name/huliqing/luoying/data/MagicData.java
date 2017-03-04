/*
 * LuoYing is a program used to make 3D RPG game.
 * Copyright (c) 2014-2016 Huliqing <31703299@qq.com>
 * 
 * This file is part of LuoYing.
 *
 * LuoYing is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LuoYing is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with LuoYing.  If not, see <http://www.gnu.org/licenses/>.
 */
package name.huliqing.luoying.data;

import com.jme3.network.serializing.Serializable;

/**
 * 魔法数据
 * @author huliqing
 */
@Serializable
public class MagicData extends ModelEntityData {
    
    // 魔法所针对的特定目标
    private long[] targets;

    /**
     * 获取魔法的释放源，这个方法返回的是一个entity的唯一id.
     * @return 
     */
    public long getSource() {
        return getAsLong("source");
    }

    /**
     * 设置魔法的释放源,如果要清除释放源，可以设置一个小于或等于0的值。
     * @param source 
     */
    public void setSource(long source) {
        setAttribute("source", source);
    }
    
    /**
     * 获得魔法所针对的目标对象,如果不存在则返回null.
     * @return 
     */
    public long[] getTargets() {
        return targets;
    }

    /**
     * 设置魔法的针对的目标对象, 当魔法是特别针对个别对象时，可以通过这个方法来设置目标对象。
     * @param targets 
     */
    public void setTargets(long... targets) {
        this.targets = targets;
    }
    
    // remove
//    /**
//     * 获取魔法使用的效果组，如果没有设置则返回null.
//     * @return 
//     */
//    public EffectData[] getEffectDatas() {
//        return (EffectData[]) getAttribute("effectDatas");
//    }
//    
//    /**
//     * 设置魔法使用的效果组
//     * @param effectDatas 
//     */
//    public void setEffectDatas(EffectData[] effectDatas) {
//        setAttribute("effectDatas", effectDatas);
//    }
//    
    // ----------------------------- outdate 
//    // 魔法的位置
//    private Vector3f location;
//    // 魔法的旋转
//    private Quaternion rotation;
//    
//    private boolean debug;
//    // 魔法的执行时间，单位秒
//    private float useTime;
//    
//    private long sourceActor;
//    private long targetActor;
//    
//    // 魔法的所要跟随的角色的唯一ID
//    private long traceActor;
//    private TraceType tracePosition;
//    private Vector3f tracePositionOffset;
//    
//    private TraceType traceRotation;
//    private Quaternion traceRotationOffset; // x,y,z angle
//    
//    private float timeUsed;
//    
//    private HitCheckerData hitCheckerData;
    
//    @Override
//    public void write(JmeExporter ex) throws IOException {
//        super.write(ex);
//        OutputCapsule oc = ex.getCapsule(this);
//        oc.write(useTime, "useTime", 1);
//    }
//
//    @Override
//    public void read(JmeImporter im) throws IOException {
//        super.read(im);
//        InputCapsule ic = im.getCapsule(this);
//        useTime = ic.readFloat("useTime", 1);
//    }
//    
//    public MagicData() {}
//
//    public Vector3f getLocation() {
//        return location;
//    }
//
//    public void setLocation(Vector3f location) {
//        this.location = location;
//    }
//
//    public Quaternion getRotation() {
//        return rotation;
//    }
//
//    public void setRotation(Quaternion rotation) {
//        this.rotation = rotation;
//    }
//
//    public boolean isDebug() {
//        return debug;
//    }
//
//    public void setDebug(boolean debug) {
//        this.debug = debug;
//    }
//
//    /**
//     * 获取魔法的执行时间单位秒。
//     * @return 
//     */
//    public float getUseTime() {
//        return useTime;
//    }
//
//    /**
//     * 设置魔法的执行时间，单位秒。
//     * @param useTime 
//     */
//    public void setUseTime(float useTime) {
//        this.useTime = useTime;
//    }
//
//    /**
//     * 魔法的所要跟随的角色的唯一ID
//     * @return 
//     */
//    public long getTraceActor() {
//        return traceActor;
//    }
//
//    /**
//     * 魔法的所要跟随的角色的唯一ID
//     * @param traceActor 
//     */
//    public void setTraceActor(long traceActor) {
//        this.traceActor = traceActor;
//    }
//
//    public TraceType getTracePosition() {
//        return tracePosition;
//    }
//
//    public void setTracePosition(TraceType tracePosition) {
//        this.tracePosition = tracePosition;
//    }
//
//    public Vector3f getTracePositionOffset() {
//        return tracePositionOffset;
//    }
//
//    public void setTracePositionOffset(Vector3f tracePositionOffset) {
//        this.tracePositionOffset = tracePositionOffset;
//    }
//
//    public TraceType getTraceRotation() {
//        return traceRotation;
//    }
//
//    public void setTraceRotation(TraceType traceRotation) {
//        this.traceRotation = traceRotation;
//    }
//
//    public Quaternion getTraceRotationOffset() {
//        return traceRotationOffset;
//    }
//
//    public void setTraceRotationOffset(Quaternion traceRotationOffset) {
//        this.traceRotationOffset = traceRotationOffset;
//    }
//
//    /**
//     * 魔法的施放者，如果没有则返回小于或等于0的值。
//     * @return 
//     */
//    public long getSourceActor() {
//        return sourceActor;
//    }
//
//    /**
//     * 魔法的施放者，如果没有则设置小于或等于0的值.
//     * 这个值是角色的唯一ID。
//     * @param sourceActor 
//     */
//    public void setSourceActor(long sourceActor) {
//        this.sourceActor = sourceActor;
//    }
//
//    /**
//     * 魔法的被作用的主目标，指向角色的唯一ID,如果没有特殊目标，则该值返回
//     * 小于或等于0的值。
//     * @deprecated 要重构掉，不要这个参数,这是一个不好的设计
//     * @return 
//     */
//    public long getTargetActor() {
//        return targetActor;
//    }
//
//    /**
//     * 魔法的被作用的主目标，指向角色的唯一ID,如果没有特殊目标，则设置
//     * 小于或等于0的值。
//     * @deprecated 要重构掉，不要这个参数，这是一个不好的设计,因为并不是所有魔法
//     * 都需要一个主要目标。
//     * @param targetActor 
//     */
//    public void setTargetActor(long targetActor) {
//        this.targetActor = targetActor;
//    }
//
//    /**
//     * 获取魔法当前已经运行的时间，单位秒。
//     * @return 
//     */
//    public float getTimeUsed() {
//        return timeUsed;
//    }
//
//    /**
//     * 设置魔法当前已经运行的时间，单位秒。
//     * @param timeUsed 
//     */
//    public void setTimeUsed(float timeUsed) {
//        this.timeUsed = timeUsed;
//    }
//
//    public HitCheckerData getHitCheckerData() {
//        return hitCheckerData;
//    }
//
//    public void setHitCheckerData(HitCheckerData hitCheckerData) {
//        this.hitCheckerData = hitCheckerData;
//    }

    
}
