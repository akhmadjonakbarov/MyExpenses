package uz.akbarovdev.myexpenses.features.dashboard.data.repositories

import kotlinx.coroutines.flow.Flow
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionDao
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionEntity
import uz.akbarovdev.myexpenses.features.dashboard.domain.repositories.TransactionRepository

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun getTransactions(): List<TransactionEntity> {
        return transactionDao.getAllTransactions()
    }

    override suspend fun createTransaction(transactionEntity: TransactionEntity) {
        transactionDao.insertTransaction(transactionEntity)
    }

    override suspend fun deleteTransaction(transactionEntity: TransactionEntity) {
        transactionDao.deleteTransaction(transactionEntity)
    }

    override suspend fun updateTransaction(transactionEntity: TransactionEntity) {
        TODO("Not yet implemented")
    }
}