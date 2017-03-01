package Interface;

public interface List {
	public void insert(int i,Object object) throws Exception;
	public Object delete(int i) throws Exception;
	public Object getData(int i)throws Exception;
	public int getSize();
	public boolean isEmpty();
}
