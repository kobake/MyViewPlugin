package jp.clockup.viewplugin;

import java.awt.PopupMenu;
import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.part.ViewPart;

// ビューア（Viewer）はモデル、コンテンツプロバイダ（ContentProvider）、ラベルプロバイダ（LabelProvider）から構成されます。
public class ExplorerView extends ViewPart {
	private TreeViewer m_viewer;
	private IAction m_refreshAction;
	private Menu m_popupMenu;
	
	public ExplorerView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		m_viewer = new TreeViewer(parent);
		m_viewer.setContentProvider(new ExplorerContentProvider());
		m_viewer.setLabelProvider(new ExplorerLabelProvider());
		m_viewer.setInput(File.listRoots());
		m_viewer.setSorter(new ViewerSorter(){
			public int category(Object element){
				if(((File)element).isDirectory()){
					return 0;
				}
				return 1;
			}
		});
		getSite().setSelectionProvider(m_viewer);
		// 追加要素
		createActions();
		createToolBar();
		createMenuBar();
		createPopupMenu();
	}

	@Override
	public void setFocus() {
		m_viewer.getTree().setFocus();
	}
	
	// アクション生成
	private void createActions(){
		// アイコンを取得
		ImageDescriptor icon = Activator.getImageDescriptor("icons/refresh.gif");
		// アクション生成
		m_refreshAction = new Action("リフレッシュ", icon){
			@Override
			public void run() {
				m_viewer.refresh();
			}
		};
	}
	
	// ツールバーにアクション追加
	private void createToolBar(){
		IToolBarManager manager = getViewSite().getActionBars().getToolBarManager();
		manager.add(m_refreshAction);
	}
	
	// メニューバーにアクション追加
	private void createMenuBar(){
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
		manager.add(m_refreshAction);
	}
	
	// ポップアップメニューを作成
	private void createPopupMenu(){
		MenuManager manager = new MenuManager("#PopupMenu");
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		m_popupMenu = manager.createContextMenu(m_viewer.getTree());
		m_viewer.getTree().setMenu(m_popupMenu);
		getSite().registerContextMenu(manager, m_viewer);
	}
	
	// これ呼ばれるのかな？？
	public void dispose(){
		m_popupMenu.dispose();
		super.dispose();
	}

	
}
