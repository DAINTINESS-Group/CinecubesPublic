package TaskMgr;

import java.util.List;

public class FactCompilation {
   
    /**
	 * @uml.property  name="listTasks"
	 */
    private List<Task> ListTasks;

	/**
	 * @return the listTasks
	 */
	public List<Task> getListTasks() {
		return ListTasks;
	}

	/**
	 * @param listTasks the listTasks to set
	 */
	public void setListTasks(List<Task> listTasks) {
		ListTasks = listTasks;
	}
    
}
