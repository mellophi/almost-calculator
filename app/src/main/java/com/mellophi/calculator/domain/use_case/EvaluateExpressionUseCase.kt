package com.mellophi.calculator.domain.use_case

class EvaluateExpressionUseCase {
    operator fun invoke(expression: String): String {
        val stack = ArrayDeque<String>()
        var lastOp = '+'
        var current = 0f
        var lastComputedSum = 0f
        var lastComputedProduct = 1f
        expression.forEachIndexed { index, c ->
            when (c) {
                in listOf('+', '-') -> {
                    when (lastOp) {
                        '+' -> lastComputedSum += current
                        '-' -> lastComputedSum -= current
                        '*' -> {
                            lastComputedProduct *= current
                            lastComputedSum += lastComputedProduct
                        }
                        '/' -> {
                            lastComputedProduct /= current
                            lastComputedSum += lastComputedProduct
                        }
                    }
                    lastOp = c
                    current = 0f
                    lastComputedProduct = 1f
                }

                in listOf('/', '*') -> {
                    when(lastOp) {
                        '+' -> lastComputedProduct = current
                        '-' -> lastComputedProduct = -current
                        '*' -> lastComputedProduct *= current
                        '/' -> lastComputedProduct /= current
                    }
                    current = 0f
                    lastOp = c
                }

                '(' -> {
                    if(index - 1 >= 0 && expression[index - 1] !in listOf('+', '-', '*', '/')) {
                        // need to do product
                        when(lastOp) {
                            '+' -> lastComputedProduct = current
                            '-' -> lastComputedProduct = -current
                            '*' -> lastComputedProduct *= current
                            '/' -> lastComputedProduct /= current
                        }
                        lastOp = '*'
                    }
                    stack.add(lastComputedSum.toString())
                    stack.add(lastComputedProduct.toString())
                    stack.add(lastOp.toString())
                    lastComputedSum = 0f
                    lastComputedProduct = 1f
                    current = 0f
                    lastOp = '+'
                }

                ')' -> {
                    when (lastOp) {
                        '+' -> lastComputedSum += current
                        '-' -> lastComputedSum -= current
                        '*' -> {
                            lastComputedProduct *= current
                            lastComputedSum += lastComputedProduct
                        }
                        '/' -> {
                            lastComputedProduct /= current
                            lastComputedSum += lastComputedProduct
                        }
                    }
                    current = lastComputedSum
                    lastOp = stack.last().first()
                    stack.removeLast()
                    lastComputedProduct = stack.last().toFloat()
                    stack.removeLast()
                    lastComputedSum = stack.last().toFloat()
                    stack.removeLast()
                }

                else -> {
                    if (c.isDigit()) {
                        current = current * 10 + c.digitToInt()
                    } else if(c.isWhitespace()) {
                        return@forEachIndexed
                    } else {
                        throw IllegalStateException("invalid expression!")
                    }
                }
            }
        }

        when (lastOp) {
            '+' -> lastComputedSum += current
            '-' -> lastComputedSum -= current
            '*' -> {
                lastComputedProduct *= current
                lastComputedSum += lastComputedProduct
            }
            '/' -> {
                lastComputedProduct /= current
                lastComputedSum += lastComputedProduct
            }
        }

        return lastComputedSum.toString()
    }
}