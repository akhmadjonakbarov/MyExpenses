package uz.akbarovdev.myexpenses.features.dashboard.domain.repositories

import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceEntity

interface BalanceRepository {
    suspend fun insertBalance(balance: BalanceEntity)
    suspend fun getAllBalances(): List<BalanceEntity>
    suspend fun replaceAllBalances(balances: List<BalanceEntity>)


}