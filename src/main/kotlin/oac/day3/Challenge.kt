package oac.day3


import oac.util.getResourceAsText
import java.lang.Integer.parseInt
import kotlin.text.Regex

val txtInput = getResourceAsText("/day3/input.txt")!!

val rgxMul = Regex("""mul\((\d+),(\d+)\)""")

fun part1() {
    val total = rgxMul.findAll(txtInput).map {
        val (a, b) = it.destructured
        parseInt(a) * parseInt(b)
    }.sum()
    println(total)
}

val rgxActions = Regex("""(do\(\))|(don't\(\))|((mul)\((\d+),(\d+)\))""")

fun part2(){
    var enabled = true
    var total = 0
    for (match in rgxActions.findAll(txtInput)){
        val (doAction, dontAction, _, mullAction, n1, n2) = match.destructured
        if (doAction == "do()") {
            enabled = true
        } else if (dontAction == "don't()") {
            enabled = false
        } else if (mullAction == "mul") {
            if (enabled) total += parseInt(n1) * parseInt(n2)
        }
    }
    println(total)
}

fun main() {
    part2()
}