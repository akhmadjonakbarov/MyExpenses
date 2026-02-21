package uz.akbarovdev.myexpenses.features.dashboard.domain.repositories

import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceEntity

interface BalanceRepository {
    suspend fun instertBalance(balance: BalanceEntity)
    suspend fun getAllBalances(): List<BalanceEntity>


}