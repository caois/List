package com.collection;


public class MyLinkedList<E> {
	//首结点,首结点一般记录的是第一个结点的 Node，且只在第一次时，会被赋值
	private Node<E> first;	
	//尾结点，用于记录当前的newNode，但是在下一次调用时，last就成为了 当时newNode.prev 了
	private Node<E> last;	
	
	
	private int size = 0;
	
	//从头开始查找，first
	public boolean remov(E element) {
		
		if(element == null) {
			for(Node<E> temp = first; temp != null; temp = temp.next) {
				if(element == temp.element) {
					unlink(temp);
					return true;
				}
			}
		}else {
			for(Node<E> temp = first; temp!=null; temp = temp.next) {
				if(element.equals(temp.element)) {//找到element
					//删除 element所在的结点(temp)
					unlink(temp);
					return true;
				}
			}
		}
		return false;
		
	}
	public void remove(int index) {
		checkIndex(index);
		Node<E> temp = getNode(index);
		unlink(temp);
		
			
	}
	/**
	 * 解开指定的结点 temp
	 * @param temp
	 */
	private void unlink(Node<E> temp) {
		Node<E> up = temp.prev;
		Node<E> down = temp.next;
		if(up!=null) {
			up.next = down;
		}else {//index == 0时，up为null，即temp.prev 为空
			//头部结点更改为 当前结点的下一个temp.next
			first = down;
		}
		
		if(down!=null) {
			down.prev = up;
		}else {//index == size-1时，down为空，即temp.next 为空
			//尾部结点更改为当前结点的上一个temp.prev;
			last = up;
		}
		size--;
		
		
	}
	/**
	 * 
	 * @param index 根据索引返回索引所在的值
	 * @return
	 */
	public E get(int index) {
		checkIndex(index);
		
//		Node<E> temp = getNode(index);
		
//		return (E)temp.element;
		return element(index);
	}
	//下面这个方法效果和上面是一样的。。。完全多余
	@SuppressWarnings("unchecked")
	private E element(int index) {
		//根据索引返回 节点中的值 element
		return (E)getNode(index).element;
	}
	/**
	 * 
	 * @param index	
	 * 		根据指定的索引index，返回索引位置的节点对象Node
	 * @return 
	 * 		返回节点对象
	 */
	private Node<E> getNode(int index) {
		Node<E> temp = null;
		if(index < (size>>1)) {//表示要查找的索引在链表的头部到中间之间
			temp = first;
			for(int i = 0; i<index;i++) {
				temp = temp.next;	//向后叠加赋值，直到赋值到index位置，就返回
			}
		}else {	//表示要查找的索引在链表的中间和尾部之间
			temp = last;	//如果传入的index 大于size，则直接打印 last.element
			for(int i = size-1; i>index; i--) {
				temp = temp.prev;	//向前叠加赋值，直到index，返回
			}
		}
		return temp;
	}
	
	
	public void add(E element) {
		//新建一个结点
		Node<E> newNode = new Node<>(element);
		if(first == null) {
			//临时保存，first，last在一个容器中都且只有一个，Node可以无数个
			first = newNode;
			last = newNode;
					
		}else {
			//新Nede的上一个结点指向了 上一个结点
			newNode.prev = last;	
//			newNode.next = null;//这句话不写也可以，在新建结点类时， Node.next 会默认为null
			//旧结点的下一个指向 当前新建的结点
			last.next = newNode;
			
			
			//使用完last后，更改last的指向，由上一个旧结点改变为当前新建的Node
			last = newNode;
		}
		
		size++;
	}
	
	public void add(int index, E element) {
		checkIndex(index);
		Node<E> newNode = new Node<>(element);
		if(index == size ) {
			addA(element);
		}else if(index == 0){
			linkedFirst(element);
		}else {
			Node<E> temp = getNode(index);
			
			//获取到E 的上一个结点,保存为 eup，原链表中 index角标上表示的结点 的上一个结点
			Node<E> eup = temp.prev;		
			//index位置上的结点 表示为E
			temp.prev = newNode;		//新结点指向了 E的prev
			newNode.next = temp;		//E 重新指向了 新节点的 next，这样就形成了一个双向链表指向
			
			//新加入的结点存储在 E的前面，原E结点前面结点的后面
			eup.next = newNode;			//原链表中E的上一个结点的 next指向 新结点
			newNode.prev = eup;			//新结点的prev 指向 原链表中E的上一个结点
			
			size++;
		}
		
	}
	//末节点添加
	public void addA(E element) {
		//建立一个临时的Node用来记录 值， 所以这个temp记录的上一个结点Node
		Node<E> temp = last;	//temp记录last的值，last在上一次记录的是 上一次当时的节点
		
		/*new Node<>(temp,element,null);可以看成
		 * newNode.priv = temp = last
		 */
		Node<E> newNode = new Node<>(temp,element,null);
		last = newNode;//last记录当前的新结点，在下一次调用时就位成为 新节点的 上一个（prev）
		
		//
		if(temp == null) {
			first = newNode;
		}else {//如果不为null，那么表示
			temp.next = newNode;	//上一个结点(Node).下一个（next）记录当前的结点
//			newNode.prev = temp;	//不写这句，倒着查找找不到
		}
		size ++;
	}
	
	//首节点添加
	private void linkedFirst(E element) {
		Node<E> temp = first;
		Node<E> newNode = new Node<>(element);
		
		first = newNode;
		//如果是第一次添加首节点
		if(temp == null) {
			last = newNode;
		}else {
			temp.prev = newNode;	//如果不是第一次添加，原节点的prev(上一个结点)指向新节点
			newNode.next = temp;	//没有这句 顺序查找找不到，前提是该方法被调用过
		}
		size++;
		
	/*	Node<E> f = first;
		Node<E> newNode = new Node<>(null, e, f);
		上两句，隐藏了下面这句
		e.next = f	等同于 newNode.next = temp;
		*/
	}
	
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Node temp = first;
		
		while(temp != null) {
			sb.append(temp.element+",");
			temp = temp.next;
		}
		sb.setCharAt(sb.length()-1, ']');
		
		
		return sb.toString();
	}
	
	private void checkIndex(int index) {
		if(index < 0 || index > size) {
			throw new RuntimeException("索引不合法："+index);
		}
	}
	
	
	public static void main(String[] args) {
		MyLinkedList<String> ml = new MyLinkedList<>();
		for(int i=0;i<=20;i++) {
			ml.add("A"+i);
		}
		ml.add(null);
		System.out.println(ml);
		System.out.println(ml.get(10));
		ml.remove(ml.size()-11);
		System.out.println(ml);
		
		
		System.out.println(ml.remov(null));
		System.out.println(ml);
		System.out.println("``````````````");
		ml.add(0,"is first");
		System.out.println(ml);
		
		
	}
	
}

class Node<E>{
	Node<E> prev;
	Node<E> next;
	
	Object element;

	public Node(Node<E> prev, E element, Node<E> next) {
		this.prev = prev;
		this.element = element;
		this.next = next;
	}

	public Node(E element) {
		this.element = element;
	}
	
	
} 
