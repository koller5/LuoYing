/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.luoying.object.entity;

import com.jme3.scene.Spatial;
import name.huliqing.luoying.data.EntityData;
import name.huliqing.luoying.object.scene.Scene;
import name.huliqing.luoying.xml.DataProcessor;

/**
 * Entity定义一种可以直接存放在Scene中的存在，这类<b>存在</b>包含任何可能性的有形物体或无形物体,
 * 如：水、天空、重力、阴影、模型、阳光、相机等...
 * @author huliqing
 * @param <T>
 */
public interface Entity<T extends EntityData> extends DataProcessor<T>{

    @Override
    public void setData(T data);

    @Override
    public T getData();
    
    /**
     * 初始化
     */
    void initialize();
        
    /**
     * 判断是否已经初始化
     * @return 
     */
    boolean isInitialized();
    
    /**
     * 清理并释放资源
     */
    void cleanup();
    
    /**
     * 这个方法会在物体被添加到场景的时候被调用, 如果一个物体没有被直接被放到场景中，则这个方法
     * 可能不会被调用。
     * @param scene 
     */
    void onInitScene(Scene scene);
    
    /**
     * 获取物体的唯一id, 通过这个id可以从scene中直接找到这个物体
     * @return 
     */
    long getEntityId();
    
    /**
     * 获得当前物体所在的场景,当一个Entity存在于场景中时这个方法应该返回对当前角色所在场景的引用，
     * 如果Entity已经脱离场景，则该返回值应该为null. 即可以通过这个方法来判断Entity是否正在场景中。
     * @return 
     */
    Scene getScene();
    
    /**
     * 获取代表当前物体在场景(Scene)中的"存在"，这个存在代表了物体本身，一般也是Entity的根节点，
     * 一个Entity可以是有形的或是无形的存在，有形的如：花、草、树木、动物、生物、各种角色等拥有模型的物体。
     * 无形的Entity可以是如：物理环境、光照系统、水体环境、天空系统、包围盒、阴影及各种SceneProcessor或Filter等。<br>
     * <br>
     * 1.对于有形的Entity，这个方法必须返回一个代表物体在场景中的实际存在，如对于角色一般应该返回角色的模型节点.<br>
     * 2.对于无形的Entity，这个方法可以返回null, 但是建议返回一个无任何意义的Node(这可以减少一些引用上的判断和NullPointerException)<br>
     * 
     * 注：一般这个Spatial只作为<b>只读</b>使用，外部在引用这个Spatial的时候不应该直接去<b>写操作</b>这个Spatial
     * 的各种属性，而是应该通过Entity(DataProcessor)所定义的各种接口API来改变Entity的各种行为，这可以避免状态的不同步，
     * 特别是在Network环境下。
     * @return 
     * @see ModelEntity
     * @see NoneModelEntity
     */
    Spatial getSpatial();
    
    /**
     * 让Entity从当前场景中脱离
     * @return 
     */
    boolean removeFromScene();
    
    /**
     * 获取Entity模块
     * @return 
     */
    EntityModule getEntityModule();
}