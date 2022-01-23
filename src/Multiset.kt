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
