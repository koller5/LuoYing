/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.ly.object.effect;

import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.util.TempVars;
import java.util.ArrayList;
import java.util.List;
import name.huliqing.ly.data.EffectData;
import name.huliqing.ly.object.anim.AnimationControl;
import name.huliqing.ly.object.anim.Loop;
import name.huliqing.ly.object.anim.RandomRotationAnim;
import name.huliqing.ly.object.scene.Scene;
import name.huliqing.ly.utils.MathUtils;

/**
 * @deprecated 20160202
 * 一个由几个星光环绕着目标旋转的特效。
 * @author huliqing
 */
public class EncircleHaloEffect extends Effect {

    // 星光图片
    private String texture = "Textures/effect/halo_s.jpg";
    // 星光数量
    private int size = 7;
    private float radius = 2;
    // 星光的大小
    private Vector3f haloSize = new Vector3f(1,1,1);
    // 星光的颜色
    private ColorRGBA haloColor = new ColorRGBA(1,1,1,1);
    // 是否显示星光的环绕线
    private boolean showLine = true;
    private ColorRGBA lineColor = new ColorRGBA(0.8f, 0.8f, 1, 1f);
    
    // ---- inner
    // 对所有已经创建的星光环的引用
    private final List<HaloCircle> circles = new ArrayList<HaloCircle>(size);
    
    @Override
    public void setData(EffectData data) {
        super.setData(data);
        texture = data.getAsString("texture", texture);
        size = data.getAsInteger("size", size);
        radius = data.getAsFloat("radius", radius);
        haloSize = data.getAsVector3f("haloSize", haloSize);
        haloColor = data.getAsColor("haloColor", haloColor);
        showLine = data.getAsBoolean("showLine", showLine);
        lineColor = data.getAsColor("lineColor", lineColor);
        
        preCreate();
    }

    @Override
    public void initialize(Scene scene) {
        super.initialize(scene);
        // 重置速度
        for (HaloCircle hc : circles) {
            hc.setRotateSpeed(FastMath.nextRandomFloat() * 0.5f + 0.5f);
        }
    }
    
    private void preCreate() {
        // ---- 创建星光
        circles.clear();
        float avgAngle = FastMath.TWO_PI / size; // 每个环要旋转的弧度
        TempVars tv = TempVars.get();
        for (int i = 0; i < size; i++) {
            // 逐个创建星光
            HaloCircle hc = new HaloCircle(radius, texture, haloSize, haloColor, showLine, lineColor);
            circles.add(hc);
            
            // 初始化星光的旋转位置及旋转速度
            MathUtils.createRotation(avgAngle * i, Vector3f.UNIT_Z, tv.quat1);
            hc.setLocalRotation(tv.quat1);
            hc.startRotate(true);
            
            // 星光的随机旋转
            RandomRotationAnim rra = new RandomRotationAnim();
            rra.setTarget(hc);
            rra.setLoop(Loop.loop);
            rra.setSpeed(0.5f);
            hc.addControl(new AnimationControl(rra));
            animRoot.attachChild(hc);
            rra.start();
        }
        tv.release();
        
    }

    @Override
    protected void effectUpdate(float tpf) {
        super.effectUpdate(tpf);
        for (HaloCircle hc : circles) {
            hc.setRotateSpeed(hc.getRotateSpeed() + timeUsed * tpf);
        }
        
    }

    
}