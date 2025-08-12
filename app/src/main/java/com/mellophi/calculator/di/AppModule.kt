package com.mellophi.calculator.di

import com.mellophi.calculator.domain.use_case.AddParenthesisUseCase
import com.mellophi.calculator.domain.use_case.EvaluateExpressionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAddParenthesisUseCase(): AddParenthesisUseCase {
        return AddParenthesisUseCase()
    }

    @Provides
    @Singleton
    fun provideEvaluateExpressionUseCase(): EvaluateExpressionUseCase {
        return EvaluateExpressionUseCase()
    }
}