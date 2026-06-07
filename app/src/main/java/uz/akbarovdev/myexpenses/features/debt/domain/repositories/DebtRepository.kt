package uz.akbarovdev.myexpenses.features.debt.domain.repositories

import kotlinx.coroutines.flow.Flow
import uz.akbarovdev.myexpenses.features.debt.daos.DebtTransactionEntity
import uz.akbarovdev.myexpenses.features.debt.daos.DebtUserEntity

interface DebtRepository {
    fun getAllUsers(): Flow<List<DebtUserEntity>>
    suspend fun getUserById(id: Int): DebtUserEntity?
    suspend fun insertUser(user: DebtUserEntity)
    suspend fun updateUser(user: DebtUserEntity)
    suspend fun deleteUser(user: DebtUserEntity)

    fun getTransactionsByUserId(userId: Int): Flow<List<DebtTransactionEntity>>
    suspend fun insertTransaction(transaction: DebtTransactionEntity)
    suspend fun updateTransaction(transaction: DebtTransactionEntity)
    suspend fun deleteTransaction(transaction: DebtTransactionEntity)
}
