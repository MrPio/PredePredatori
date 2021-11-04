package gui;

import javax.swing.text.Utilities;

public enum EnviromentSize {
	small(new utilities.Point(300,200)),
	medium(new utilities.Point(600,400)),
	large(new utilities.Point(1200,800));
	utilities.Point size;
	EnviromentSize(utilities.Point size){
		this.size=size;
	}
}
