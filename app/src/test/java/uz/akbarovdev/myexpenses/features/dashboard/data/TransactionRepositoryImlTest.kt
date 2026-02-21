package uz.akbarovdev.myexpenses.features.dashboard.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import uz.akbarovdev.myexpenses.features.dashboard.data.repositories.TransactionRepositoryImpl
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.Transaction
import uz.akbarovdev.myexpenses.features.dashboard.domain.repositories.TransactionRepository

class TransactionRepositoryImlTest {
    private lateinit var repository: TransactionRepository

    @BeforeEach
    fun setUp() {
        repository = TransactionRepositoryImpl()
    }



    @RepeatedTest(200)
    fun `Add transactions, count of transaction is equal which is added`() {
        repository.createTransaction(
            Transaction(
                icon = "!", name = "Laptop", 25.0
            )
        )
        val items = repository.getTransactions()
        assertThat(items.size).isEqualTo(1)
    }

}