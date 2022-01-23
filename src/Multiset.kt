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
}
