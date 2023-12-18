package com.cursokotlin.mvvmexample.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cursokotlin.mvvmexample.domain.GetQuotesUseCase
import com.cursokotlin.mvvmexample.domain.GetRandomQuoteUseCase
import com.cursokotlin.mvvmexample.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Carlos A. Novaez Guerrero on 15/11/2023 17:02
 * cnovaez.dev@outlook.com
 */
@OptIn(ExperimentalCoroutinesApi::class)
class QuoteViewModelTest {

    @RelaxedMockK
    private lateinit var getQuotesUseCase: GetQuotesUseCase

    @RelaxedMockK
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    private lateinit var quoteViewModel: QuoteViewModel


    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        quoteViewModel = QuoteViewModel(getQuotesUseCase, getRandomQuoteUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when viewmodel is created call the getQuotesUseCase and load the first value`() =
        runTest {
            //Given
            val list = listOf(Quote("Hello", "ee"), Quote("Wellcome", "oeir"))
            coEvery { getQuotesUseCase() } returns list

            //When
            quoteViewModel.onCreate()

            //Then
            assert(quoteViewModel.quoteModel.value == list.first())
            assert(quoteViewModel.isLoading.value == false)


        }

    @Test
    fun `when viewmodel is created call the getQuotesUseCase and load the first value if list not empty`() =
        runTest {
            //Given
//            val list = listOf(Quote("Hello","ee"), Quote("Wellcome","oeir"))
            coEvery { getQuotesUseCase() } returns emptyList()

            //When
            quoteViewModel.onCreate()

            //Then
//          assert (quoteViewModel.quoteModel.value == list.first())
            assert(quoteViewModel.isLoading.value == true)


        }


    @Test
    fun `when calling random quote method, post a random quote in the quoteModel`() =
        runTest {

            //Given
            val quote = Quote("skks", "ifoiwi")
            coEvery {
                getRandomQuoteUseCase()
            } returns quote

            //When
            quoteViewModel.randomQuote()

            //Then
            assert(quote == quoteViewModel.quoteModel.value)
        }

    @Test
    fun `if random quotes returns a null, viewmodel will keep the previous value`() =
        runTest {
            //Given
            val quote = Quote("gdvwuyw", "ifbbiwubf")
            quoteViewModel.quoteModel.value = quote
            coEvery { getRandomQuoteUseCase() } returns null

            //When
            quoteViewModel.randomQuote()

            //Then
            assert(quoteViewModel.quoteModel.value == quote)

        }

}
