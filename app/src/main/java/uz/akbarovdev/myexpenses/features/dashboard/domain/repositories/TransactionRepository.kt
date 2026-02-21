package uz.akbarovdev.myexpenses.features.dashboard.domain.repositories

import kotlinx.coroutines.flow.Flow
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionEntity

interface TransactionRepository {
    suspend fun getTransactions(): List<TransactionEntity>
    suspend fun createTransaction(transactionEntity: TransactionEntity)
    suspend fun deleteTransaction(transactionEntity: TransactionEntity)
    suspend fun updateTransaction(transactionEntity: TransactionEntity)
}