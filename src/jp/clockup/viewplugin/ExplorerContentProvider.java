package jp.clockup.viewplugin;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ExplorerContentProvider implements ITreeContentProvider{
	@Override
	public Object[] getChildren(Object parentElement) {
		File[] children = ((File)parentElement).listFiles();
		return children == null ? new Object[0] : children;
	}

	@Override
	public Object getParent(Object element) {
		return ((File)element).getParentFile();
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length == 0 ? false : true;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return (File[])inputElement;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}
