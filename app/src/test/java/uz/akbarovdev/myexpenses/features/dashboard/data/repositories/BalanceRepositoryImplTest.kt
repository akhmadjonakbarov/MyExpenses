package uz.akbarovdev.myexpenses.features.dashboard.data.repositories

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceDao
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceEntity
import uz.akbarovdev.myexpenses.features.dashboard.domain.repositories.BalanceRepository

private class FakeBalanceDao : BalanceDao {
    private val balances = mutableListOf<BalanceEntity>()

    override suspend fun getAllBalances(): List<BalanceEntity> = balances.toList()

    override suspend fun insertBalance(balance: BalanceEntity) {
        balances.removeAll { it.id == balance.id }
        balances.add(balance)
    }

    override suspend fun insertBalances(balances: List<BalanceEntity>) {
        this.balances.removeAll { b -> balances.any { it.id == b.id } }
        this.balances.addAll(balances)
    }

    override suspend fun deleteBalance(balance: BalanceEntity) {
        balances.removeAll { it.id == balance.id }
    }

    override suspend fun clearAll() {
        balances.clear()
    }
}

class BalanceRepositoryImplTest {

    private val dao = FakeBalanceDao()
    private val repository: BalanceRepository = BalanceRepositoryImpl(dao)

    @Test
    fun `insertBalance stores balance and is returned by getAllBalances`() = runBlocking {
        repository.insertBalance(BalanceEntity(id = 0, amount = 1000.0, createdAt = 10L))

        val all = repository.getAllBalances()

        assertThat(all).containsExactly(BalanceEntity(id = 0, amount = 1000.0, createdAt = 10L))
    }

    @Test
    fun `inserting multiple balances keeps all of them`() = runBlocking {
        repository.insertBalance(BalanceEntity(id = 1, amount = 100.0, createdAt = 10L))
        repository.insertBalance(BalanceEntity(id = 2, amount = 200.0, createdAt = 20L))

        assertThat(repository.getAllBalances().size).isEqualTo(2)
    }

    @Test
    fun `replaceAllBalances clears previous data and inserts new list`() = runBlocking {
        repository.insertBalance(BalanceEntity(id = 1, amount = 100.0, createdAt = 10L))

        repository.replaceAllBalances(
            listOf(
                BalanceEntity(id = 3, amount = 300.0, createdAt = 30L),
                BalanceEntity(id = 4, amount = 400.0, createdAt = 40L),
            )
        )

        assertThat(repository.getAllBalances().size).isEqualTo(2)
        assertThat(repository.getAllBalances().last().amount).isEqualTo(400.0)
    }

    @Test
    fun `replaceAllBalances with empty list removes everything`() = runBlocking {
        repository.insertBalance(BalanceEntity(id = 1, amount = 100.0, createdAt = 10L))

        repository.replaceAllBalances(emptyList())

        assertThat(repository.getAllBalances()).isEmpty()
    }
}