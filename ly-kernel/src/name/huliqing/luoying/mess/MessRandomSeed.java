/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.luoying.mess;

import com.jme3.network.serializing.Serializable;
import name.huliqing.luoying.manager.RandomManager;

/**
 * 用于服务端和客户端之间同步随机种子。
 * @author huliqing
 */
@Serializable
public class MessRandomSeed extends MessBase {

    private int randomSeed;

    public int getRandomSeed() {
        return randomSeed;
    }

    /**
     * 设置在客户端要随机更新的种子
     * @param randomSeed 
     */
    public void setRandomSeed(int randomSeed) {
        this.randomSeed = randomSeed;
    }
    
    @Override
    public void applyOnClient() {
        super.applyOnClient();
        RandomManager.setRandomSeed(randomSeed);
    }
    
}