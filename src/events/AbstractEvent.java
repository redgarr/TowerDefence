package events;

public class AbstractEvent implements Event
{

	private Object source;
	private Object data;
	
	
	public AbstractEvent(Object source, Object data) 
	{
		this.source = source;
		this.data = data;
	}
	
	public Object getSource()
	{
		return source;
	}
	
	public void setSource(Object source) {
		this.source = source;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
