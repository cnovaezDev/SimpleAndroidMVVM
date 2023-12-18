package com.cursokotlin.mvvmexample.domain

import com.cursokotlin.mvvmexample.data.QuoteRepository
import com.cursokotlin.mvvmexample.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by Carlos A. Novaez Guerrero on 15/11/2023 16:26
 * cnovaez.dev@outlook.com
 */
class GetRandomQuoteUseCaseTest {

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRandomQuoteUseCase = GetRandomQuoteUseCase(quoteRepository)
    }


    @Test
    fun `when there is no quote on database return null`() = runBlocking {
        //Given
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()
        //When
        val response = getRandomQuoteUseCase()
        //Then
        coVerify(exactly = 1) { quoteRepository.getAllQuotesFromDatabase() }
        assert(response == null)
    }

    @Test
    fun `when the database returns some quotes then show one random`() = runBlocking {
        //Given
        val quotes = listOf(Quote("Hola", "Yo"), Quote("Adios", "Tu"))
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quotes

        //When
        val response = getRandomQuoteUseCase()
        //Then
        coVerify(exactly = 1) { quoteRepository.getAllQuotesFromDatabase() }
        assert(response != null)
        assert(quotes.contains(response))
    }


}