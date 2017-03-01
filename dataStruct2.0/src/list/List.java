package list;

public interface List<E> {
	public void init();
	/**
	 * 在下标index处增加节点
	 * @param index
	 * @param elem
	 * @throws Exception
	 */
	public void add(int index,E elem);
	/**
	 * 在表尾增加节点
	 * @param elem
	 * @throws Exception
	 */
	public void add(E elem);
	/**
	 * 删除index处的节点
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public E remove(int index);
	/**
	 * 删除尾结点
	 * @return
	 * @throws Exception
	 */
	public E remove();
	/**
	 * 删除所有值为elem的节点
	 * @param elem
	 * @return 返回是否存在elem且删除成功
	 */
	public boolean remove(E elem);
	public boolean contain(E elem);
	public E get(int index);
	public int size();
	public boolean isEmpty();
	public void print();
}
