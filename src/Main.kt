const val size = 26
const val specialChar = '$'

fun String.getVertex(pos: Int): Int {
    return this[pos].toInt() - 'a'.toInt()
}

fun isCycled(u: Int, graph: Array<ArrayList<Int>>, color: Array<Int>): Boolean {
    color[u] = 1
    for (v in graph[u]) {
        when (color[v]) {
            0 -> if (isCycled(v, graph, color)) return true
            1 -> return true
        }
    }
    color[u] = 2
    return false
}

fun dfs(u: Int, graph: Array<ArrayList<Int>>, visited: Array<Boolean>, answer: ArrayList<Int>) {
    visited[u] = true
    for (v in graph[u]) {
        if (!visited[v])
            dfs(v, graph, visited, answer)
    }
    answer.add(u)
}

fun topologicalSort(graph: Array<ArrayList<Int>>) {
    var cycled = false
    val color: Array<Int> = Array(size) { 0 }
    for (u in 0 until size) {
        cycled = cycled || isCycled(u, graph, color)
    }
    if (!cycled) {
        val visited: Array<Boolean> = Array(size) { false }
        val answer: ArrayList<Int> = ArrayList()
        for (u in 0 until size) {
            if (!visited[u]) {
                dfs(u, graph, visited, answer)
            }
        }
        for (u in size - 1 downTo 0) {
            print("${(answer[u] + 'a'.toInt()).toChar()}")
        }
    } else {
        print("Impossible")
    }
}

fun main() {
    val n: Int = readLine()!!.toInt()
    val graph: Array<ArrayList<Int>> = Array(size) { ArrayList() }
    var prevString: String = readLine()!! + specialChar
    if (n != 1) {
        var curString: String
        for (i in 1 until n) {
            curString = readLine()!! + specialChar
            var pos = 0
            while (curString[pos] == prevString[pos]) {
                pos++
            }
            if (pos == curString.length - 1) {
                print("Impossible")
                return
            }
            if (prevString[pos] != specialChar) {
                graph[prevString.getVertex(pos)].add(curString.getVertex(pos))
            }
            prevString = curString
        }
        topologicalSort(graph)
    } else {
        print(prevString[0])
        for (i in 'a'..'z') {
            if (i != prevString[0]) {
                print(i)
            }
        }
    }
}