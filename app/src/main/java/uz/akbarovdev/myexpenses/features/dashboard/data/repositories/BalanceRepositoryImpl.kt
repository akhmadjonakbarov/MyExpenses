package uz.akbarovdev.myexpenses.features.dashboard.data.repositories

import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceDao
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceEntity
import uz.akbarovdev.myexpenses.features.dashboard.domain.repositories.BalanceRepository

class BalanceRepositoryImpl(
    private val balanceDao: BalanceDao
) : BalanceRepository {
    override suspend fun instertBalance(balance: BalanceEntity) {
        balanceDao.insertBalance(balance)
    }

    override suspend fun getAllBalances(): List<BalanceEntity> {
        return balanceDao.getAllBalances()
    }

}