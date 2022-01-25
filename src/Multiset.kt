import java.util.LinkedList

/**
 * represents a multiset:
 * a modification of set that allows for multiple instances for each of its elements.
 * ref: https://en.wikipedia.org/wiki/Multiset
 */
class MutableMultiset<E>
private constructor()
{
	companion object
	{
		/**
		 * creates a multiset instance with specified elements.
		 */
		@JvmStatic
		fun <E> mutableMultisetOf(vararg elems : E) : MutableMultiset<E>
		{
			val multiset : MutableMultiset<E> = MutableMultiset()
			multiset.addAll(*elems)
			return multiset
		}

		/**
		 * creates an empty multiset instance.
		 */
		@JvmStatic
		fun <E> mutableMultisetOf() : MutableMultiset<E> = MutableMultiset()
	}

	/**
	 * stores distinct elements in this multiset.
	 */
	private val _elements : MutableList<E> = mutableListOf()

	/**
	 * a fake reference to $_elements avoiding direct manipulation from user.
	 * returns a copy of $_elements, hence modifications made does not matter.
	 * intended to be used by user only; within this class, use $_elements.
	 */
	val elements : MutableList<E>
		get() = this._elements.toMutableList()

	/**
	 * stores multiplicity, or frequencies of every element
	 * indices corresponding to $elements.
	 */
	private val _multiplicities : MutableList<Int> = mutableListOf()

	/**
	 * a fake reference to $_multiplicities avoiding direct manipulation from user.
	 * returns a copy of $_multiplicities, hence modifications made does not matter.
	 * intended to be used by user only; within this class, use $_multiplicities.
	 */
	val multiplicities : MutableList<Int>
		get() = this._multiplicities.toMutableList()

	/**
	 * adds an element to this multiset with specified multiplicity (by default 1).
	 * multiplicity should be at least 0.
	 * @throws IllegalArgumentException if the specified multiplicity is negative.
	 */
	fun add(elem : E , freq : Int = 1)
	{
		require(freq >= 0) {"Element multiplicity cannot be negative: $freq"}

		val index : Int = this._elements.indexOf(elem)

		// new element for this multiset
		if (index == -1)
		{
			_elements += elem
			_multiplicities += freq
		}

		// not a new element, add $freq only
		else _multiplicities[index] += freq
	}

	/**
	 * operator overload: += as `add`
	 */
	operator fun plusAssign(e : E) = this.add(e)

	/**
	 * adds elements in bulk to this multiset with each element once.
	 */
	fun addAll(vararg elements : E) = elements.forEach{this.add(it)}

	/**
	 * adds all elements in a collection to this multiset
	 * with each element once.
	 */
	fun addAll(c : Collection<E>) = c.forEach{this.add(it)}

	/**
	 * removes an element from this multiset with specified multiplicity (by default 1).
	 * does not remove if the specified freq is higher than that of the element in this multiset.
	 * @return true on a successful removal, otherwise false.
	 * @throws IllegalArgumentException if the specified multiplicity is negative.
	 */
	fun remove(elem : E , freq : Int = 1) : Boolean
	{
		require(freq >= 0) {"Element multiplicity cannot be negative: $freq"}

		val index : Int = this._elements.indexOf(elem)
		if (index == -1 || this._multiplicities[index] < freq) return false // no such element in this multiset, or multiplicity does not meet desired amount

		this._multiplicities[index] -= freq
		if (_multiplicities[index] == 0)
		{
			this._elements.removeAt(index)
			this._multiplicities.removeAt(index)
		}

		return true
	}

	/**
	 * operator overload: -= as `remove`
	 */
	operator fun minusAssign(e : E)
	{
		this.remove(e)
	}

	/**
	 * removes every occurrence of the specified element from this multiset.
	 * @return number of specified elements removed.
	 */
	fun removeAll(elem : E) : Int
	{
		var counter : Int = 0
		var index : Int = this._elements.indexOf(elem)

		while (index != -1)
		{
			this._elements.removeAt(index)
			counter++
			index = this._elements.indexOf(elem)
		}

		return counter
	}

	/**
	 * checks if this multiset contains at least 1 of the specified element.
	 */
	operator fun contains(elem : E) : Boolean = _elements.contains(elem)

	/**
	 * returns number of occurrences of the specified element.
	 */
	val count : (E) -> Int =
		{
			elem : E ->
			val index : Int = this._elements.indexOf(elem)

			if (index == -1) 0
			else this._multiplicities[index]
		}

	/**
	 * size of this multiset.
	 */
	val size : Int
		get()
		{
			var size = 0
			for (i in _multiplicities)
			{
				size += i
			}

			return size
		}

	val isEmpty : Boolean
		get() = this._elements.size == 0

	/**
	 * performs the specified action for each element in this multiset, including duplicates.
	 */
	fun forEach(action : (E) -> Unit)
	{
		for (i in _elements.indices)
		{
			val currElem = _elements[i]
			for (j in 1.._multiplicities[i])
			{
				action(currElem)
			}
		}
	}

	fun toString(separator : String) : String
	{
		val list : MutableList<E> = LinkedList()

		for (i in _elements.indices)
		{
			val currElem = _elements[i]
			for (j in 1.._multiplicities[i])
			{
				list += currElem
			}
		}

		return String.format("[%s]" , list.joinToString(separator = separator))
	}

	override fun toString() : String = this.toString(separator = " , ")

}
