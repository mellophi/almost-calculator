package com.mellophi.calculator.domain.use_case

import android.util.Log
import kotlin.collections.ArrayDeque

class AddParenthesisUseCase {
    operator fun invoke(expression: String): Result<String> {
        return runCatching {
            val isBalanced = checkBalancedParenthesis(expression)
            val mExpression =
                with (expression.lastOrNull()) {
                    when {
                        this == '%' || this == '-' || this == '(' -> "$expression("
                        isBalanced || this == null -> "$expression("
                        else -> "$expression)"
                    }
                }
            mExpression
        }.onFailure { e ->
            Log.e(TAG, "Invalid expression: ${e.message}")
        }
    }

    private fun checkBalancedParenthesis(expression: String): Boolean {
        val stack = ArrayDeque<Char>()

        expression.forEach { element ->
            if(element == '(') {
                stack.add(element)
            } else if(stack.isNotEmpty() && (element == ')' && stack.last() == '(')) {
                stack.removeLast()
            } else if(element == ')') {
                throw IllegalArgumentException("Format error!")
            }
        }

        return stack.isEmpty()
    }

    companion object {
        private const val TAG = "AddParenthesisUseCase"
    }
}