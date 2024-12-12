package oac.util

data class Node<T>(
    val data: T,
    private val _children: MutableList<Node<T>> = mutableListOf(),
    val children: List<Node<T>> = _children,
) {
    fun addChild(node: Node<T>) {
        _children.add(node)
    }
}
