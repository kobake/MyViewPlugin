package jp.clockup.viewplugin;

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class DeleteAction implements IViewActionDelegate{
	private ExplorerView m_view;
	private IStructuredSelection m_sel;
	
	@Override
	public void init(IViewPart view) {
		this.m_view = (ExplorerView)view;
	}
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		m_sel = (IStructuredSelection)selection;
	}

	@Override
	public void run(IAction action) {
		TreeViewer viewer = (TreeViewer)m_view.getSite().getSelectionProvider();
		Object[] files = m_sel.toArray();
		if(files.length > 0){
			StringBuffer sb = new StringBuffer("以下のファイルを削除してよろしいですか\n\n");
			for(int i = 0; i < files.length; i++){
				sb.append(((File)files[i]).getName() + "\n");
			}
			if(MessageDialog.openQuestion(m_view.getSite().getShell(), "ファイルの削除", sb.toString())){
				for(int i = 0; i < files.length; i++){
					File file = (File)files[i];
					if(file.isDirectory()){
						deleteFolder(file);
					}
					else{
						file.delete();
					}
					viewer.refresh(file.getParentFile());
				}
			}
		}
	}
	
	private void deleteFolder(File file){
		File[] files = file.listFiles();
		for(int i=0; i < files.length; i++){
			if(files[i].isDirectory()){
				deleteFolder(files[i]);
			}
			files[i].delete();
		}
		file.delete();
	}


}
