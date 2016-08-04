/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.view.actor;

import java.util.ArrayList;
import java.util.List;
import name.huliqing.core.Factory;
import name.huliqing.core.data.AttributeApply;
import name.huliqing.core.data.SkinData;
import name.huliqing.core.manager.ResourceManager;
import name.huliqing.core.object.actor.Actor;
import name.huliqing.core.mvc.network.UserCommandNetwork;
import name.huliqing.core.mvc.service.ActorService;
import name.huliqing.core.mvc.service.PlayService;
import name.huliqing.core.mvc.service.SkinService;
import name.huliqing.core.ui.ListView;
import name.huliqing.core.ui.Row;
import name.huliqing.core.ui.UI;

/**
 * 武器面板列表
 * @author huliqing
 */
public class WeaponPanel extends ListView<SkinData> implements ActorPanel{
    private final UserCommandNetwork userCommandNetwork = Factory.get(UserCommandNetwork.class);
    private final PlayService playService = Factory.get(PlayService.class);
    private final ActorService actorService = Factory.get(ActorService.class);
    private final SkinService skinService = Factory.get(SkinService.class);
    
    private Actor actor;
    private List<SkinData> datas = new ArrayList<SkinData>();
    
    public WeaponPanel(float width, float height) {
        super(width, height);
    }

    @Override
    protected Row createEmptyRow() {
        final WeaponRow row = new WeaponRow();
        row.setRowClickListener(new Listener() {
            @Override
            public void onClick(UI ui, boolean isPress) {
                if (!isPress) {
                    userCommandNetwork.useObject(actor, row.getData());
                    refreshPageData();
                }
            }
        });
        row.setShortcutListener(new Listener() {
            @Override
            public void onClick(UI ui, boolean isPress) {
                if (!isPress) {
                    playService.addShortcut(actor, row.getData());
                }
            }
        });
        return row;
    }

    @Override
    public void setPanelVisible(boolean visible) {
        this.setVisible(visible);
    }

    @Override
    public void setPanelUpdate(Actor actor) {
        this.actor = actor;
        getDatas();
        super.refreshPageData();
    }

    @Override
    public List<SkinData> getDatas() {
        if (actor != null) {
            // 清理
            datas.clear();
            
            // 载入
            skinService.getWeaponSkins(actor, datas);
        }
        return datas;
    }
  
    @Override
    public boolean removeItem(SkinData data) {
        throw new UnsupportedOperationException();
    }
    
    private class WeaponRow extends ItemRow<SkinData> {

        public WeaponRow() {
            super();
        }
        
        @Override
        public void display(SkinData data) {
            SkinData wd = (SkinData) data;
            icon.setIcon(wd.getIcon());
            body.setNameText(ResourceManager.getObjectName(wd));
            
            List<AttributeApply> aas = wd.getApplyAttributes();
            if (aas != null) {
                StringBuilder sb = new StringBuilder();
                for (AttributeApply aa : aas) {
                    sb.append(ResourceManager.getObjectName(aa.getAttribute()))
                            .append(":")
                            .append(aa.getAmount())
                            .append("  ");
                }
                body.setDesText(sb.toString());
            } else {
                body.setDesText("");
            }
            
            num.setText(String.valueOf(wd.getTotal()));
            
            setBackgroundVisible(wd.isUsing());
        }
        
        @Override
        protected void clickEffect(boolean isPress) {
            super.clickEffect(isPress);
            setBackgroundVisible(((SkinData)data).isUsing());
        }
        
        @Override
        public void onRelease() {
            SkinData sd = ((SkinData)data);
            setBackgroundVisible(sd.isUsing());
        }
    }
}
