/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.editor.ui.tool;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import name.huliqing.editor.constants.ResConstants;
import name.huliqing.editor.manager.Manager;
import name.huliqing.editor.tools.base.CameraTool;
import name.huliqing.editor.utils.BestEditCamera;
import name.huliqing.fxswing.Jfx;

/**
 *
 * @author huliqing
 */
public class JfxCameraTool extends JfxAbstractTool<CameraTool> {
    
    private final MenuButton view = new MenuButton();

    public JfxCameraTool() {
        view.setPrefWidth(80);
        view.setPrefHeight(25);
        view.setMaxHeight(25);
    }
    
    @Override
    public Region createView() {
        return view;
    }

    @Override
    public void initialize() {
        super.initialize();
        view.setText(tool.getName());
        
        if (tool.getTips() != null) {
            view.setTooltip(new Tooltip(tool.getTips()));
        }
        
        MenuItem focusOrigin = new MenuItem(Manager.getRes(ResConstants.VIEW_FOCUS_ORIGIN));
        MenuItem focusTarget = new MenuItem(Manager.getRes(ResConstants.VIEW_FOCUS_TARGET));
        MenuItem viewBack = new MenuItem(Manager.getRes(ResConstants.VIEW_BACK));
        MenuItem viewBottom = new MenuItem(Manager.getRes(ResConstants.VIEW_BOTTOM));
        MenuItem viewFront = new MenuItem(Manager.getRes(ResConstants.VIEW_FRONT));
        MenuItem viewLeft = new MenuItem(Manager.getRes(ResConstants.VIEW_LEFT));
        MenuItem viewRight = new MenuItem(Manager.getRes(ResConstants.VIEW_RIGHT));
        MenuItem viewTop = new MenuItem(Manager.getRes(ResConstants.VIEW_TOP));
        MenuItem viewOrtho = new MenuItem(Manager.getRes(ResConstants.VIEW_ORTHO));
        MenuItem viewPersp = new MenuItem(Manager.getRes(ResConstants.VIEW_PERSP));
        
        focusOrigin.setOnAction(t -> {Jfx.runOnJme(() -> tool.doChaseOrigin());});
        focusTarget.setOnAction(t -> {Jfx.runOnJme(() ->tool.doChaseSelected());});
        viewBack.setOnAction(t -> {Jfx.runOnJme(() ->tool.doSwitchView(BestEditCamera.View.back));});
        viewBottom.setOnAction(t -> {Jfx.runOnJme(() ->tool.doSwitchView(BestEditCamera.View.bottom));});
        viewFront.setOnAction(t -> {Jfx.runOnJme(() ->tool.doSwitchView(BestEditCamera.View.front));});
        viewLeft.setOnAction(t -> {Jfx.runOnJme(() ->tool.doSwitchView(BestEditCamera.View.left));});
        viewRight.setOnAction(t -> {Jfx.runOnJme(() ->tool.doSwitchView(BestEditCamera.View.right));});
        viewTop.setOnAction(t -> {Jfx.runOnJme(() ->tool.doSwitchView(BestEditCamera.View.top));});
        viewOrtho.setOnAction(t -> {Jfx.runOnJme(() ->tool.doOrthoView());});
        viewPersp.setOnAction(t -> {Jfx.runOnJme(() ->tool.doPerspView());});
        
        view.getItems().addAll(focusOrigin, focusTarget
                , viewBack, viewBottom, viewFront, viewLeft, viewRight, viewTop, viewOrtho, viewPersp);

    }

}
