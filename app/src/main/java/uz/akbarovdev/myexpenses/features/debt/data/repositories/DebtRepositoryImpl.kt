package uz.akbarovdev.myexpenses.features.debt.data.repositories

import kotlinx.coroutines.flow.Flow
import uz.akbarovdev.myexpenses.features.debt.daos.DebtTransactionDao
import uz.akbarovdev.myexpenses.features.debt.daos.DebtTransactionEntity
import uz.akbarovdev.myexpenses.features.debt.daos.DebtUserDao
import uz.akbarovdev.myexpenses.features.debt.daos.DebtUserEntity
import uz.akbarovdev.myexpenses.features.debt.domain.repositories.DebtRepository

class DebtRepositoryImpl(
    private val debtUserDao: DebtUserDao,
    private val debtTransactionDao: DebtTransactionDao
) : DebtRepository {

    override fun getAllUsers(): Flow<List<DebtUserEntity>> = debtUserDao.getAllUsers()

    override suspend fun getUserById(id: Int): DebtUserEntity? = debtUserDao.getUserById(id)

    override suspend fun insertUser(user: DebtUserEntity) = debtUserDao.insertUser(user)

    override suspend fun updateUser(user: DebtUserEntity) = debtUserDao.updateUser(user)

    override suspend fun deleteUser(user: DebtUserEntity) = debtUserDao.deleteUser(user)

    override fun getTransactionsByUserId(userId: Int): Flow<List<DebtTransactionEntity>> =
        debtTransactionDao.getTransactionsByUserId(userId)

    override suspend fun insertTransaction(transaction: DebtTransactionEntity) =
        debtTransactionDao.insertTransaction(transaction)

    override suspend fun updateTransaction(transaction: DebtTransactionEntity) =
        debtTransactionDao.updateTransaction(transaction)

    override suspend fun deleteTransaction(transaction: DebtTransactionEntity) =
        debtTransactionDao.deleteTransaction(transaction)
}
