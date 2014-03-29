package jp.clockup.viewplugin;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ExplorerLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		if(((File)element).isDirectory()){
			return Activator.getImageDescriptor("icons/folder.gif").createImage();
		}
		else{
			return Activator.getImageDescriptor("icons/file.gif").createImage();
		}
	}

	@Override
	public String getText(Object element) {
		File file = (File)element;
		String name = file.getName();
		if(name.equals("")){
			name = file.getPath();
		}
		return name;
	}

}
