package oac.util

data class PosNode<T>(
    val data: T,
    val position: Point,
    private val _children: MutableList<PosNode<T>> = mutableListOf(),
    val children: List<PosNode<T>> = _children,
) {
    fun addChild(node: PosNode<T>) {
        _children.add(node)
    }

    override fun toString(): String {
        return "Node(data=$data,position=$position,children=${children.size})"
    }
}

