/**
 * represents a multiset:
 * a modification of set that allows for multiple instances for each of its elements.
 * ref: https://en.wikipedia.org/wiki/Multiset
 */
class Multiset<E>
{
	/**
	 * stores distinct elements in this multiset.
	 */
	val elements : MutableList<E> = mutableListOf()

	/**
	 * stores multiplicity, or frequencies of every element
	 * indices corresponding to $elements.
	 */
	val multiplicities : MutableList<Int> = mutableListOf()

	/**
	 * adds an element to this multiset with specified multiplicity (by default 1).
	 * multiplicity should be at least 0.
	 * @throws IllegalArgumentException if the specified multiplicity is negative.
	 */
	fun add(elem : E , freq : Int = 1)
	{
		when
		{
			// filter illegal args
			(freq < 0) -> throw IllegalArgumentException("Element multiplicity cannot be negative: $freq")
			else -> when (val index : Int = elements.indexOf(elem))
			{
				// new element for this multiset
				-1 ->
				{
					elements += elem
					multiplicities += freq
				}

				// not a new element, add $freq only
				else -> multiplicities[index] += freq
			}
		}
	}

	/**
	 * adds elements in bulk to this multiset with each element once.
	 */
	fun addAll(vararg elements : E)
	{
		elements.forEach{this.add(it)}
	}

	/**
	 * removes an element with specified multiplicity (by default 1).
	 * @return true on a successful removal, otherwise false.
	 * @throws IllegalArgumentException if the specified multiplicity is negative.
	 */
	fun remove(elem : E , freq : Int = 1) : Boolean
	{
		// filter illegal arg
		if (freq < 0) throw IllegalArgumentException("Element multiplicity cannot be negative: $freq")

		val index : Int = this.elements.indexOf(elem)
		if (index == -1 || this.multiplicities[index] < freq) return false // no such element in this multiset, or multiplicity does not meet desired amount for removal

		this.multiplicities[index] -= freq
		if (multiplicities[index] == 0)
		{
			this.elements.removeAt(index)
			this.multiplicities.removeAt(index)
		}

		return true
	}
}
