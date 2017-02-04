/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.editor;

import com.jme3.system.AppSettings;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import name.huliqing.editor.manager.EditManager;
import name.huliqing.editor.ui.MenuView;
import name.huliqing.editor.ui.ResourceView;
import name.huliqing.editor.ui.MainLayout;
import name.huliqing.fxswing.Jfx;

/**
 *
 * @author huliqing
 */
public class Starter {
    
    
    public static void main(String[] args) {
        new Starter().start();
    }
    
    private void start() {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1280, 720);
        settings.setFrameRate(60);
        settings.setSamples(4);
        
        Jfx.create(Editor.class.getName(), settings);
        Jfx.getMainFrame().setLocationRelativeTo(null);
        Jfx.getMainFrame().setVisible(true);
        // 这里要等待JmeApp执行完simpleInit方法之后再开始执行Jfx UI,因为UI要依赖于JME的初始化
        Jfx.runOnJme(() -> {
            Jfx.runOnJfx(() -> {
                startJfx();
            });
        });
        
    }
    
    private void startJfx() {
        // 加载样式文件
        Jfx.getJfxRoot().getStylesheets().add("resources/style/style.css");

        // 创建Jfx主场景
        Pane jfxEditZone = initMainSceneInJfx(Jfx.getJfxRoot());

        // 将JFX中的UndoRedo按键转换到JME场景中
        JfxUndoRedoKeyEventToJme();

        // 初始化FormView
        Jfx.runOnJme(() -> {
            initJfxEditToJme(jfxEditZone);
        });
    }
    
    private Pane initMainSceneInJfx(Pane root) {
        MenuView menuView = new MenuView();
        ResourceView resourceView = new ResourceView();

        VBox jfxEditZone = new VBox();
        jfxEditZone.setBackground(Background.EMPTY);
        
        MainLayout mainLayout = new MainLayout(root);
        mainLayout.setZones(menuView, resourceView, jfxEditZone);
        root.getChildren().add(mainLayout);
        
        return jfxEditZone;
    }
    
    private void initJfxEditToJme(Pane jfxEditZone) {
        EditManager.registerEditZone(jfxEditZone);
        EditManager.openTestFormView();
    }
 
    // 将JFX中的UndoRedo按键转换到JME场景中
    private void JfxUndoRedoKeyEventToJme() {
        Jfx.getJfxRoot().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
            final KeyCombination redo = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
            @Override
            public void handle(KeyEvent ke) {
                if(redo.match(ke)) {
                    ((Editor) Jfx.getJmeApp()).redo();
                } else if (undo.match(ke)) {
                    ((Editor) Jfx.getJmeApp()).undo();
                }
            }
        });
    }
}