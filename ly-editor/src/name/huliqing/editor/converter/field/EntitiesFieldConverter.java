/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.editor.converter.field;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import name.huliqing.editor.component.ComponentDefine;
import name.huliqing.editor.constants.AssetConstants;
import name.huliqing.editor.constants.ComponentConstants;
import name.huliqing.editor.converter.DataConverter;
import name.huliqing.editor.converter.FieldConverter;
import name.huliqing.editor.edit.Mode;
import name.huliqing.editor.edit.controls.ControlTile;
import name.huliqing.editor.edit.scene.JfxSceneEdit;
import name.huliqing.editor.edit.scene.JfxSceneEditListener;
import name.huliqing.editor.edit.controls.entity.EntityControlTile;
import name.huliqing.editor.manager.ComponentManager;
import name.huliqing.editor.manager.ConverterManager;
import name.huliqing.editor.ui.ComponentSearch;
import name.huliqing.editor.ui.utils.JfxUtils;
import name.huliqing.luoying.data.EntityData;

/**
 * 场景的"entities"字段的转换器, 将entities转换为列表
 * @author huliqing
 */
public class EntitiesFieldConverter extends FieldConverter<JfxSceneEdit, EntityData> implements JfxSceneEditListener {

    private final VBox layout = new VBox();
    private final ToolBar toolBar = new ToolBar();
    private final ListView<EntityData> listView = new ListView();
    private boolean ignoreSelectEvent;
    
    private final TitledPane entityPanel = new TitledPane();
    private final Map<EntityData, DataConverter> entityConverterMaps = new HashMap();
    // 当前正在显示的EntityConverter
    private DataConverter currentDisplayConverter;
    
    private final ComponentSearch<ComponentDefine> componentSearch = new ComponentSearch(ComponentManager.getComponentsByType(ComponentConstants.ENTITY));
    
    public EntitiesFieldConverter() {
        // 工具栏
        Button add = new Button("", JfxUtils.createIcon(AssetConstants.INTERFACE_ICON_ADD));
        Button remove = new Button("", JfxUtils.createIcon(AssetConstants.INTERFACE_ICON_SUBTRACT));
        toolBar.getItems().addAll(add, remove);
        add.setOnAction(e -> {
            componentSearch.show(add, -10, -10);
        });
        remove.setOnAction(e -> {
            EntityData ed = listView.getSelectionModel().getSelectedItem();
            if (ed != null) {
                jfxEdit.removeEntity(ed);
                listView.getItems().remove(ed);
            }
        });
        componentSearch.getListView().setOnMouseClicked(e -> {
            ComponentDefine cd = componentSearch.getListView().getSelectionModel().getSelectedItem();
            if (cd != null) {
                ComponentManager.createComponent(cd, jfxEdit);
                componentSearch.hide();
            }
        });
        
        // 列表
        listView.setCellFactory(new CellInner());
        listView.getSelectionModel().selectedItemProperty().addListener(this::onJfxSelectChanged);
        layout.getChildren().addAll(toolBar, listView);
        layout.setStyle("-fx-padding:0;-fx-border-width:0;");
        
        entityPanel.setVisible(false);
    }
        
    @Override
    protected Node createLayout() {
        return layout;
    }
    
    @Override
    public void initialize() {
        super.initialize();
        
        // remove20170116不再需要
//        List<EntityData> eds = parent.getData().getEntityDatas();
//        if (eds != null) {
//            listView.getItems().clear();
//            eds.forEach(t -> {
//                listView.getItems().add(t);
//            });
//        }
        
        jfxEdit.getPropertyPanel().getChildren().add(entityPanel);
        
        // 用于监听3D场景中选择物体的变化
        jfxEdit.addListener(this);
    }
    
    @Override
    public void cleanup() {
        jfxEdit.getPropertyPanel().getChildren().remove(entityPanel);
        jfxEdit.removeListener(this);
        super.cleanup(); 
    }

    private void onJfxSelectChanged(ObservableValue observable, EntityData oldValue, EntityData newValue) {
        if (ignoreSelectEvent) {
            return;
        }
        if (newValue != null) {
            // 注：重新设置选择的时候会触发事件，回调到onSelectChanged(EntitySelectObj)
            // 要注意避免在该方法中导致死循环重复。
            jfxEdit.setSelected(newValue);
            doUpdateEntityView(newValue);
        }
    }

    @Override
    public void onModeChanged(Mode mode) {
        // 不管
    }

    @Override
    public void onEntityAdded(EntityData entityData) {
        listView.getItems().add(entityData);
    }

    @Override
    public void onEntityRemoved(EntityData ed) {
        listView.getItems().remove(ed);
        entityConverterMaps.remove(ed);
    }

    @Override
    public void onSelectChanged(ControlTile selectObj) {
        if (selectObj == null) {
            ignoreSelectEvent = true;
            listView.getSelectionModel().clearSelection();
            doUpdateEntityView(null);
            ignoreSelectEvent = false;
            return;
        }
        if (!(selectObj instanceof EntityControlTile))
            return;
        
        ignoreSelectEvent = true;
        EntityData ed = ((EntityControlTile)selectObj).getTarget().getData();
        listView.getSelectionModel().select(ed);
        doUpdateEntityView(ed);
        ignoreSelectEvent = false;
    }
    
    private void doUpdateEntityView(EntityData entityData) {
        if (entityData == null) {
            return;
        }
        
        DataConverter dc = entityConverterMaps.get(entityData);
        if (dc == null) {
            dc = ConverterManager.createDataConverter(jfxEdit, entityData, this);
            entityConverterMaps.put(entityData, dc);
        }
        if (currentDisplayConverter != null) {
            currentDisplayConverter.cleanup();
        }
        currentDisplayConverter = dc;
        currentDisplayConverter.initialize();
        entityPanel.setText(entityData.getId());
        entityPanel.setContent(currentDisplayConverter.getLayout());
        entityPanel.setVisible(true);
    }

    @Override
    public void updateView() {
        // ignore
    }

    private class CellInner implements Callback<ListView<EntityData>, ListCell<EntityData>> {

        @Override
        public ListCell<EntityData> call(ListView<EntityData> param) {
            ListCell<EntityData> lc = new ListCell<EntityData>() {
                @Override
                protected void updateItem(EntityData item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(null);
                    if (!empty && item != null) {
                        setText(item.getId());
                    } else {
                        setText(null); // 必须设置为null,否则会有重复数据可能(在动态添加item的时候)
                    }
                }
            };
            return lc;
        }
        
    }
}
