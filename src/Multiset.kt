/**
 * represents a multiset:
 * a modification of set that allows for multiple instances for each of its elements.
 * ref: https://en.wikipedia.org/wiki/Multiset
 */
class Multiset<E>
{
    /**
     * stores distinct values in this multiset.
     */
    val values : MutableList<E> = mutableListOf()

    /**
     * stores frequencies of every distinct value
     * indices corresponding to $values.
     */
    val freq : MutableList<Int> = mutableListOf()
}
