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
	val multiplicity : MutableList<Int> = mutableListOf()

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
			(freq == 0) -> return
			else -> when (val index : Int = elements.indexOf(elem))
			{
				// new element for this multiset
				-1 ->
				{
					elements += elem
					multiplicity += freq
				}

				// not a new element, add $freq only
				else -> multiplicity[index] += freq
			}
		}
	}
}
