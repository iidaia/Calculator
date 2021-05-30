package nl.vu.labs.phoenix.ap;

import java.io.PrintStream;


public class Set<T extends Comparable<T>> implements SetInterface<T> {


	public ListInterface<T> list;

	public Set() {

		list = new LinkedList<T>();
	}

	public Set(Set<T> OldSet) {

		list = OldSet.list.copy();
		list.goToFirst();
	}

	public boolean contains(T t) {

		return list.find(t);
	}

	@Override
	public boolean add(T t) {

		if(list.find(t)) {

			return false;
		}

		list.insert(t);
		return true;
	}

	@Override
	public T get() {

		return list.retrieve();
	}

	@Override
	public boolean remove(T t) {

		if(list.find(t)) {

			list.remove();
			return true;

		} else {

			return false;
		}
	}

	@Override
	public int size() {

		return list.size();
	}

	@Override
	public SetInterface<T> copy() {

		SetInterface<T> copySet = new Set<T>(this);

		return copySet;
	}


	public String print() {

		if(size() == 0) {

			return "";
		}

		list.goToFirst();
		StringBuffer s = new StringBuffer();

		for(int i = 0; i < size(); i++) {

			s.append(get().toString());

			if(i != size()-1) {

				s.append(" ");
			}

			list.goToNext();
		}

		return s.toString();
	}


	@Override
	public SetInterface<T> complement(SetInterface<T> setB) {

		SetInterface<T> complement = new Set<T>();
		list.goToFirst();

		for(int i = 0; i < size(); i++) {

			if(!setB.contains(get())) {

				complement.add(get());
			}

			list.goToNext();
		}

		return complement;
	}

	@Override
	public SetInterface<T> intersection(SetInterface<T> setB) {

		SetInterface<T> intersection = new Set<T>();
		list.goToFirst();

		for(int i = 0; i < size(); i++) {

			if(setB.contains(get())) {

				intersection.add(get());
				list.goToNext();

			} else {

				list.goToNext();
			}
		}

		return intersection;
	}

	@Override
	public SetInterface<T> union(SetInterface<T> setB) {

		SetInterface<T> union = setB.copy();
		list.goToFirst();

		for(int i = 0; i < size(); i++) {

			union.add(get());
			list.goToNext();
		}

		return union;
	}

	@Override
	public SetInterface<T> symdiff(SetInterface<T> setB) {

		SetInterface<T> union = union(setB);
		SetInterface<T> intersection = intersection(setB);
		SetInterface<T> symDiff = union.complement(intersection);

		return symDiff;
	}

}
