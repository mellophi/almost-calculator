package com.mellophi.calculator.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mellophi.calculator.domain.use_case.AddParenthesisUseCase
import com.mellophi.calculator.domain.use_case.EvaluateExpressionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.IllegalStateException
import javax.inject.Inject


@HiltViewModel
class CalculatorViewModel @Inject constructor(
    val addParenthesisUseCase: AddParenthesisUseCase,
    val evaluateExpressionUseCase: EvaluateExpressionUseCase
): ViewModel() {

    var expression = mutableStateOf("")
        private set

    var evaluation = mutableStateOf("")
        private set

    var themeToggle = mutableStateOf(false)
        private set

    fun checkAndUpdateExpression(text: String) {
        val updatedString: String
        try {
            when (text) {
                "AC" -> {
                    expression.value = ""
                    evaluation.value = ""
                }
                "()" -> expression.value = addParenthesisUseCase(expression.value).getOrThrow()
                "DEL" -> expression.value = expression.value.dropLast(1)
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "+", "-", "/" -> expression.value = "${expression.value}$text"
                "X" -> expression.value = "${expression.value}*"
                "=" -> evaluation.value = evaluateExpressionUseCase(expression.value)
                "Theme" -> themeToggle.value = !themeToggle.value
                else -> throw IllegalStateException("Invalid $text Operator!")
            }
        } catch (e: Exception) {
            evaluation.value = e.message ?: "Format error!"
        }
    }

    companion object {
        private const val INVALID_STRING = "Invalid Format!"
    }
}