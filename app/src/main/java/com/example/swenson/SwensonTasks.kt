package com.example.swenson

import android.util.Log
import java.util.*

object SwensonTasks {
    fun loadMainQuestions(): String {
        val eval = "eval((3+1)/3f*9)) ${Expr.evalExp()} \n\n"
        val anagram1 =
            "ang(debit card , bad credit ) ${Anagram.areAnagrams("debit card", "bad credit")} \n"
        val anagram2 = "ang(punishments, nine thumps) ${
            Anagram.areAnagrams("punishments",
                "nine thumps")
        } \n\n"
        val fib1 = "fib recursive(9) ${Fibonacci.recursive(9)} \n"
        val fib2 = "fib iterative(9) ${Fibonacci.iterative(9)} \n"

        val result = eval + anagram1 + anagram2 + fib1 + fib2
        Log.d("all logcat", result)
        return result
    }
}

object Expr {
    fun evalExp(): Int {
        return (3 + 1) * 9 / 3
    }
}

object Anagram {
    fun isAnagram(str1: String, str2: String): Boolean {
        return Arrays.equals(str1.toCharArray().sorted().toCharArray(),
            str2.toCharArray().sorted().toCharArray())
    }

    fun String.removeSpaces(): String {
        return this.replace("\\s".toRegex(), "")
    }

    fun areAnagrams(one: String, two: String): Boolean {
        val map = HashMap<Char, Int>()

        for (c in one.toCharArray())
            if (map.containsKey(c))
                map[c] = map[c]!! + 1
            else
                map[c] = 1

        for (c in two.toCharArray())
            if (!map.containsKey(c))
                return false
            else {
                map[c] = map[c]!! - 1

                if (map[c] == 0)
                    map.remove(c)
            }
        return map.isEmpty()
    }
}

object Fibonacci {
    fun recursive(n: Long): Long = if (n < 2) n else recursive(n - 1) + recursive(n - 2)

    fun iterative(n: Long): Long {
        if (n < 2) return n
        var minusOne: Long = 1
        var minusTwo: Long = 0
        var result = minusOne
        for (i in 2..n) {
            result = minusOne + minusTwo
            minusTwo = minusOne
            minusOne = result
        }
        return result
    }
}
