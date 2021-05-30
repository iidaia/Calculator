package nl.vu.labs.phoenix.ap;

public class LinkedList<E extends Comparable<E>> implements ListInterface<E> {

    private class Node {

        E data;
        Node prior, next;

        public Node(E data) {
            this(data, null, null);
        }

        public Node(E data, Node prior, Node next) {
            this.data = data == null ? null : data;
            this.prior = prior;
            this.next = next;
        }

    }

    private int numberOfElements;
    private Node current;

    public LinkedList(){

    	init();
    }

    @Override
    public boolean isEmpty() {

    	return size() == 0;
    }

    @Override
    public ListInterface<E> init() {

    	numberOfElements = 0;
    	current = new Node(null);

        return this;
    }

    @Override
    public int size() {

        return numberOfElements;
    }

    @Override
    public ListInterface<E> insert(E d) {


    	if(isEmpty()) {

    		current = new Node(d);
    		numberOfElements++;

    		return this;
    	}


    	goToFirst();

    	while(d.compareTo(current.data) > 0 && goToNext()){

    	}

    	if(current.prior == null && d.compareTo(current.data) < 0 || current.prior == null && d.compareTo(current.data) == 0) {

			current.prior = new Node(d, null, current);
			current = current.prior;
			current.next.prior = current;
		}
    	else if(current.prior == null && d.compareTo(current.data) > 0) {

    		current.next = new Node(d, current, current.next);
			current = current.next;
			current.prior.next = current;
		}
		else if(current.next == null && d.compareTo(retrieve()) > 0) {

			current.next = new Node(d, current, null);
			current = current.next;
			current.prior.next = current;
		}
		else {

			current.prior = new Node (d, current.prior , current);
			current = current.prior;
			current.next.prior = current.prior.next = current;
		}

    	numberOfElements++;

        return this;
    }

    @Override
    public E retrieve() {

    	if(isEmpty()) {

    		return null;

    	} else return current.data;
    }

    @Override
    public ListInterface<E> remove() {

    	if(isEmpty()) {

    		return this;

    	} else if(size() == 1) {

    		current = new Node(null);

    	} else if(current.prior == null) {

    		current = current.next;
    		current.prior = null;

    	} else if(current.next == null) {

    		current = current.prior;
    		current.next = null;

    	} else {

    		current.prior.next = current.next;
    		current.next.prior = current.prior;
    		current = current.next;
    	}

    	numberOfElements--;
		return this;
    }

    @Override
    public boolean find(E d) {

    	if(isEmpty()) {

    		current = new Node(null);
    		return false;
    	}

    	goToFirst();
    	while(d.compareTo(retrieve()) > 0 && goToNext()) {

    		}

    	if(d.compareTo(retrieve()) == 0) {

			return true;

    	} else {

    		return false;

    	}
    }

    @Override
    public boolean goToFirst() {

    	if(isEmpty()) {

    		return false;
    	}

    	while(current.prior != null && goToPrevious()) {

    	}

    	return true;
    }

    @Override
    public boolean goToLast() {

    	if(isEmpty()) {

    		return false;
    	}

    	while(current.next != null && goToNext()) {

    	}

    	return true;
    }

    @Override
    public boolean goToNext() {

    	if(isEmpty() || current.next == null){

    		return false;
		}
		else{

			current = current.next;
			return true;
		}
    }

    @Override
    public boolean goToPrevious() {

    	if(isEmpty() || current.prior == null){

    		return false;
		}
		else{

			current = current.prior;
			return true;
		}
    }

    @Override
    public ListInterface<E> copy() {

    	ListInterface<E> list = new LinkedList<E>();

		if(goToFirst()){

			list.insert(retrieve());

			while(goToNext()){

				list.insert(retrieve());
			}
		}

		return list;
    }
}
