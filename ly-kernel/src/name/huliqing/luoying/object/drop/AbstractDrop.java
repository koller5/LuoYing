/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.luoying.object.drop;

import name.huliqing.luoying.data.DropData;
import name.huliqing.luoying.object.entity.Entity;
import name.huliqing.luoying.object.sound.SoundManager;

/**
 * @author huliqing
 */
public abstract class AbstractDrop implements Drop {
    
    protected DropData data;
    protected String[] sounds;
    
    @Override
    public void setData(DropData data) {
        this.data = data;
        sounds = data.getAsArray("sounds");
    }
    
    @Override
    public DropData getData() {
        return data;
    }

    @Override
    public void updateDatas() {
        // ignore
    }
    
    /**
     * 播放drop声效,这个方法由子类调用，当子类逻辑确认掉落物品时可调用这个方法来播放掉落声音。
     * @param source 
     */
    protected void playDropSounds(Entity source) {
        if (sounds != null) {
            for (String s : sounds) {
                SoundManager.getInstance().playSound(s, source.getSpatial().getWorldTranslation());
            }
        }
    }
    
}