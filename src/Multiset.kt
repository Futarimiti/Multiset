/**
 * represents a multiset:
 * a modification of set that allows for multiple instances for each of its elements.
 * ref: https://en.wikipedia.org/wiki/Multiset
 */
class MutableMultiset<E>
private constructor()
{
	/**
	 * creates a multiset instance with specified elements.
	 */
	fun mutableMultisetOf(vararg elems : E) : MutableMultiset<E>
	{
		val multiset : MutableMultiset<E> = MutableMultiset()
		multiset.addAll(*elems)
		return multiset
	}

	/**
	 * stores distinct elements in this multiset.
	 */
	private val elements0 : MutableList<E> = mutableListOf()

	/**
	 * an immutable alias for $elements0
	 * to prevent user from direct manipulation.
	 */
	val elements : List<E>
		get() = this.elements0

	/**
	 * stores multiplicity, or frequencies of every element
	 * indices corresponding to $elements.
	 */
	private val multiplicities0 : MutableList<Int> = mutableListOf()

	/**
	 * an immutable alias for $multiplicities0
	 * to prevent user from direct manipulation.
	 */
	val multiplicities : List<Int>
		get() = this.multiplicities0

	/**
	 * adds an element to this multiset with specified multiplicity (by default 1).
	 * multiplicity should be at least 0.
	 * @throws IllegalArgumentException if the specified multiplicity is negative.
	 */
	fun add(elem : E , freq : Int = 1)
	{
		require(freq >= 0) {"Element multiplicity cannot be negative: $freq"}

		val index : Int = this.elements0.indexOf(elem)

		// new element for this multiset
		if (index == -1)
		{
			elements0 += elem
			multiplicities0 += freq
		}

		// not a new element, add $freq only
		else multiplicities0[index] += freq
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

		val index : Int = this.elements0.indexOf(elem)
		if (index == -1 || this.multiplicities0[index] < freq) return false // no such element in this multiset, or multiplicity does not meet desired amount

		this.multiplicities0[index] -= freq
		if (multiplicities0[index] == 0)
		{
			this.elements0.removeAt(index)
			this.multiplicities0.removeAt(index)
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
		var index : Int = this.elements0.indexOf(elem)

		while (index != -1)
		{
			this.elements0.removeAt(index)
			counter++
			index = this.elements0.indexOf(elem)
		}

		return counter
	}

	/**
	 * checks if this multiset contains at least 1 of the specified element.
	 */
	operator fun contains(elem : E) : Boolean = elements0.contains(elem)

	/**
	 * returns number of occurrences of the specified element.
	 */
	val count : (E) -> Int =
		{
			elem : E ->
			val index : Int = this.elements0.indexOf(elem)

			if (index == -1) 0
			else this.multiplicities0[index]
		}

	/**
	 * size of this multiset.
	 */
	val size : Int
		get()
		{
			var size = 0
			for (i in multiplicities0)
			{
				size += i
			}

			return size
		}

	val isEmpty : Boolean
		get() = this.elements0.size == 0

	/**
	 * performs the specified action for each element in this multiset, including duplicates.
	 */
	fun forEach(action : (E) -> Unit)
	{
		for (i in elements0.indices)
		{
			val currElem = elements0[i]
			for (j in 1..multiplicities0[i])
			{
				action(currElem)
			}
		}
	}

	fun toString(separator : String) : String
	{
		val list : MutableList<E> = mutableListOf()

		for (i in elements0.indices)
		{
			val currElem = elements0[i]
			for (j in 1..multiplicities0[i])
			{
				list += currElem
			}
		}

		return String.format("[%s]" , list.joinToString(separator = separator))
	}

	override fun toString() : String
	{
		return this.toString(separator = " , ")
	}
}
