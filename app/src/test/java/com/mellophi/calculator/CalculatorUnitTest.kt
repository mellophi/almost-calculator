package com.mellophi.calculator

import com.mellophi.calculator.domain.use_case.AddParenthesisUseCase
import com.mellophi.calculator.domain.use_case.EvaluateExpressionUseCase
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalculatorUnitTest {

    private lateinit var addParenthesisUseCase: AddParenthesisUseCase
    private lateinit var evaluateExpressionUseCase: EvaluateExpressionUseCase

    @Before
    fun setup() {
        addParenthesisUseCase = AddParenthesisUseCase()
        evaluateExpressionUseCase = EvaluateExpressionUseCase()
    }

    @Test
    fun evaluateExpression_isCorrect() {
//        assertEquals("3.0", evaluateExpressionUseCase("1 + 2"))
//        assertEquals("4.0", evaluateExpressionUseCase("(1 + 2 )+1"))
//        assertEquals("8.0", evaluateExpressionUseCase("2/2(1+1)(2(2))"))
//        assertEquals("9.0",evaluateExpressionUseCase("((9))"))
        assertEquals("9.0",evaluateExpressionUseCase("((9))("))
    }


    @Test
    fun addParenthesis_isCorrect() {
        assertEquals(
            "(",
            addParenthesisUseCase("")
        )
        assertEquals(
            "((",
            addParenthesisUseCase("(")
        )
        assertEquals(
            "(5)",
            addParenthesisUseCase("(5")
        )
        assertEquals(
            "()(",
            addParenthesisUseCase("()")
        )
        assertEquals(
            "(5)(",
            addParenthesisUseCase("(5)")
        )
    }
}