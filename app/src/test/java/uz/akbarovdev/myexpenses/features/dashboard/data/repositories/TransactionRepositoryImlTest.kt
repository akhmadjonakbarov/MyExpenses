package uz.akbarovdev.myexpenses.features.dashboard.data.repositories

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionDao
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionEntity
import uz.akbarovdev.myexpenses.features.dashboard.domain.repositories.TransactionRepository

private class FakeTransactionDao : TransactionDao {
    private val transactions = mutableListOf<TransactionEntity>()

    override suspend fun getAllTransactions(): List<TransactionEntity> = transactions.toList()

    override suspend fun insertTransaction(transaction: TransactionEntity) {
        transactions.removeAll { it.id == transaction.id }
        transactions.add(transaction)
    }

    override suspend fun insertTransactions(transactions: List<TransactionEntity>) {
        this.transactions.removeAll { t -> transactions.any { it.id == t.id } }
        this.transactions.addAll(transactions)
    }

    override suspend fun deleteTransaction(transaction: TransactionEntity) {
        transactions.removeAll { it.id == transaction.id }
    }

    override suspend fun clearAll() {
        transactions.clear()
    }
}

class TransactionRepositoryImlTest {

    private val dao = FakeTransactionDao()
    private val repository: TransactionRepository = TransactionRepositoryImpl(dao)

    private fun sample(id: Int, amount: Double = 250.0) = TransactionEntity(
        id = id,
        amount = amount,
        type = "EXPENSE",
        note = "note $id",
        receiver = "receiver $id",
        category = "Food",
        createdAt = id * 1000L,
    )

    @Test
    fun `createTransaction stores the transaction`() = runBlocking {
        repository.createTransaction(sample(1))

        assertThat(repository.getTransactions()).containsExactly(sample(1))
    }

    @Test
    fun `getTransactions returns all stored transactions`() = runBlocking {
        repository.createTransaction(sample(1))
        repository.createTransaction(sample(2))

        assertThat(repository.getTransactions().size).isEqualTo(2)
    }

    @Test
    fun `deleteTransaction removes only the matching transaction`() = runBlocking {
        repository.createTransaction(sample(1))
        repository.createTransaction(sample(2))

        repository.deleteTransaction(sample(1))

        assertThat(repository.getTransactions()).containsExactly(sample(2))
    }

    @Test
    fun `replaceAllTransactions clears previous data and inserts the new list`() = runBlocking {
        repository.createTransaction(sample(1))

        repository.replaceAllTransactions(listOf(sample(5), sample(6)))

        assertThat(repository.getTransactions().size).isEqualTo(2)
        assertThat(repository.getTransactions().last().id).isEqualTo(6)
    }

    @Test
    fun `replaceAllTransactions with empty list removes everything`() = runBlocking {
        repository.createTransaction(sample(1))

        repository.replaceAllTransactions(emptyList())

        assertThat(repository.getTransactions()).isEmpty()
    }
}