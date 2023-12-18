package com.cursokotlin.mvvmexample.ui.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.cursokotlin.mvvmexample.data.QuoteRepository
import com.cursokotlin.mvvmexample.data.database.QuoteDatabase
import com.cursokotlin.mvvmexample.data.database.entities.toDatabase
import com.cursokotlin.mvvmexample.data.network.QuoteApiClient
import com.cursokotlin.mvvmexample.data.network.QuoteService
import com.cursokotlin.mvvmexample.domain.model.Quote
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock

/**
 ** Created by Carlos A. Novaez Guerrero on 15/11/2023 18:55
 ** cnovaez.dev@outlook.com
 **/
@RunWith(AndroidJUnit4::class)
class IntegrationTest {

    private lateinit var database: QuoteDatabase

    private lateinit var quoteApiClient: QuoteApiClient

    private lateinit var quoteRepository: QuoteRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, QuoteDatabase::class.java)
            .allowMainThreadQueries().build()
        quoteApiClient = mock(QuoteApiClient::class.java)
        quoteRepository = QuoteRepository(QuoteService(quoteApiClient), database.getQuoteDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUserAndGetById() = runBlocking {
        val quote = Quote("ldw", "John")
        quoteRepository.insertQuotes(listOf(quote.toDatabase()))

        val fetchedQuote = quoteRepository.getAllQuotesFromDatabase()

        assert(quote == fetchedQuote.first())
    }

}