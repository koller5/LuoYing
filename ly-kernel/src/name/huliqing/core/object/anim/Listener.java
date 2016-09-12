/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.object.anim;

/**
 * 侦听器，用于侦听动画的执行是否周期结束。
 * @author huliqing
 */
public interface Listener {
    
    /**
     * 当动画周期结束时执行。根据loop模式的不同逻辑如下：<br>
     * dontloop: 动画结束后执行<br>
     * loop: 动画在每执行一次周期后调用一次该方法。<br>
     * cycle: 动画在每<b>来回</b>执行一次后调用一次该方法。<br>
     * @param anim 
     */
    void onDone(Anim anim);
    
}
