package jp.clockup.viewplugin;

import java.io.File;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

// ビューア（Viewer）はモデル、コンテンツプロバイダ（ContentProvider）、ラベルプロバイダ（LabelProvider）から構成されます。
public class ExplorerView extends ViewPart {
	private TreeViewer m_viewer;
	
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
	}

	@Override
	public void setFocus() {
		m_viewer.getTree().setFocus();
	}

}
