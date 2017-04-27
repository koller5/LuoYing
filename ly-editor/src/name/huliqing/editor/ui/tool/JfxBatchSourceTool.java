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
package name.huliqing.editor.ui.tool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import name.huliqing.editor.constants.AssetConstants;
import name.huliqing.editor.edit.scene.SceneEdit;
import name.huliqing.editor.tools.batch.BatchSourceTool;
import name.huliqing.editor.ui.utils.JfxUtils;
import name.huliqing.editor.ui.utils.SearchListView;
import name.huliqing.fxswing.Jfx;
import name.huliqing.luoying.object.entity.Entity;

/**
 * 用于选择实体列表进行batch
 * @author huliqing
 */
public class JfxBatchSourceTool extends JfxAbstractTool<BatchSourceTool>{

    private final VBox view = new VBox();
    
    private final Label title = new Label();
    private final ToolBar btnPanel = new ToolBar();
    private final Button add = new Button("", JfxUtils.createIcon(AssetConstants.INTERFACE_ICON_ADD));
    private final Button remove = new Button("", JfxUtils.createIcon(AssetConstants.INTERFACE_ICON_SUBTRACT));
    
    private final EntitySearch<Entity> entitySearch= new EntitySearch();
    
    private final ListView<Entity> content = new ListView();
    
    public JfxBatchSourceTool() {
        btnPanel.getItems().addAll(add, remove);
        view.getChildren().addAll(title, btnPanel, content);
        
        add.setOnAction((ActionEvent event) -> {
            SceneEdit se = (SceneEdit) toolbar.getEdit();
            if (se == null || se.getScene() == null)
                return;
            entitySearch.setItems(se.getScene().getEntities());
            entitySearch.show(add, -10, -10);
        });
        
        remove.setOnAction((ActionEvent event) -> {
            List<Entity> items = content.getSelectionModel().getSelectedItems();
            if (items == null)
                return;
            Jfx.runOnJme(() -> {
                tool.removeEntities(items);
                Jfx.runOnJfx(() -> {updateView();});
            });
        });
        
        content.setCellFactory((ListView<Entity> param) -> new ListCell<Entity>() {
            @Override
            protected void updateItem(Entity item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(null);
                if (!empty && item != null) {
                    setText(item.getData().getId() + "(" + item.getData().getName() + ")(" + item.getEntityId() + ")");
                }
            }
        });
        content.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        content.prefWidthProperty().bind(view.widthProperty());
        content.prefHeightProperty().bind(view.heightProperty().subtract(btnPanel.heightProperty()));
        view.setMinHeight(256);
    }
    
    @Override
    protected Region createView() {
        return view;
    }

    @Override
    public void initialize() {
        super.initialize();
        title.setText(tool.getName());
        if (tool.getTips() != null) {
            btnPanel.setTooltip(new Tooltip(tool.getTips()));
        }
        updateView();
    }
    
    private void updateView() {
        List<Entity> entities = tool.getEntities();
        content.getItems().clear();
        if (entities != null) {
            content.getItems().addAll(entities);
        }
    }
    
    // ---- 用于查询场景中的实体
    private final class EntitySearch<T extends Entity> {

        private final Popup popup = new Popup();
        private final VBox searchViewGroup = new VBox();
        private final SearchListView<Entity> searchListView = new SearchListView(new ListView());
        private final Button addAll = new Button("全部添加");

        public EntitySearch() {
            this(null);
        }

        public EntitySearch(List<Entity> items) {
            if (items != null) {
                setItems(items);
            }
            searchListView.getChildren().add(addAll);
            searchViewGroup.getChildren().addAll(searchListView);
            popup.getContent().add(searchViewGroup);
            popup.setAutoHide(true);
            searchListView.getListView().setCellFactory((ListView<Entity> param) -> new ListCell<Entity>() {
                @Override
                protected void updateItem(Entity item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item.getData().getId() + "(" + item.getData().getName() + ")");
                    }
                }
            });
            // 匹配检查的时候要用字符串转换器
            searchListView.setConverter((Entity t) -> t.getData().getId());
            searchListView.setPrefWidth(250);
            searchListView.setPrefHeight(250);
            searchListView.setEffect(new DropShadow());
            searchListView.setConverter((Entity t) -> t.getData().getId() + "(" + t.getData().getName() + ")" + t.getData().getUniqueId());
            
            // 将经过过滤后的所有实体添加到SourceTool列表中。
            addAll.setOnAction((e) -> {
                popup.hide();
                List<Entity> filterItems = searchListView.getListView().getItems();
                Jfx.runOnJme(() -> {
                    tool.addEntities(filterItems);
                    Jfx.runOnJfx(() -> {updateView();});
                });
            });
            
            searchListView.getListView().setOnMouseClicked(e -> {
                popup.hide();
                Entity entity = searchListView.getListView().getSelectionModel().getSelectedItem();
                if (entity == null)
                    return;
                Jfx.runOnJme(() -> {
                    tool.addEntity(entity);
                    Jfx.runOnJfx(() -> {updateView();});
                });
            });
        }

        public void setItems(List<Entity> items) {
            if (items == null) {
                return;
            }
            searchListView.setAllItems(items);
        }

        public void show(Region node, double offsetX, double offsetY) {
            Point2D txtCoords = node.localToScene(0.0, 0.0);
            popup.show(node,
                    txtCoords.getX() + node.getScene().getX() + node.getScene().getWindow().getX() + offsetX,
                    txtCoords.getY() + node.getScene().getY() + node.getScene().getWindow().getY() + offsetY + node.heightProperty().getValue());
        }
    }

}