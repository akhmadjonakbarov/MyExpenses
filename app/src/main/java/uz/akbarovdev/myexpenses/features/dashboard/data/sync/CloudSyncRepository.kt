package uz.akbarovdev.myexpenses.features.dashboard.data.sync

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceEntity
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionEntity
import uz.akbarovdev.myexpenses.features.dashboard.domain.repositories.BalanceRepository
import uz.akbarovdev.myexpenses.features.dashboard.domain.repositories.TransactionRepository

class CloudSyncRepository(
    private val balanceRepository: BalanceRepository,
    private val transactionRepository: TransactionRepository,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) {

    suspend fun pushAllLocal() {
        val uid = auth.currentUser?.uid ?: return
        pushBalances(uid, balanceRepository.getAllBalances())
        pushTransactions(uid, transactionRepository.getTransactions())
        Log.d(TAG, "Push completed for user $uid")
    }

    suspend fun pullAllRemote() {
        val uid = auth.currentUser?.uid ?: return
        val balances = pullBalances(uid)
        val transactions = pullTransactions(uid)
        balanceRepository.replaceAllBalances(balances)
        transactionRepository.replaceAllTransactions(transactions)
        Log.d(TAG, "Pull completed: ${balances.size} balances, ${transactions.size} transactions")
    }

    suspend fun clearLocalData() {
        balanceRepository.replaceAllBalances(emptyList())
        transactionRepository.replaceAllTransactions(emptyList())
    }

    private suspend fun pushBalances(uid: String, balances: List<BalanceEntity>) {
        val collection = firestore.collection(USER_COLLECTION).document(uid).collection(BALANCES_COLLECTION)
        balances.chunked(BATCH_SIZE).forEach { chunk ->
            val batch = firestore.batch()
            chunk.forEach { balance ->
                batch.set(
                    collection.document(balance.id.toString()),
                    balanceToMap(balance),
                    SetOptions.merge(),
                )
            }
            batch.commit().await()
        }
    }

    private suspend fun pushTransactions(uid: String, transactions: List<TransactionEntity>) {
        val collection = firestore.collection(USER_COLLECTION).document(uid).collection(TRANSACTIONS_COLLECTION)
        transactions.chunked(BATCH_SIZE).forEach { chunk ->
            val batch = firestore.batch()
            chunk.forEach { transaction ->
                batch.set(
                    collection.document(transaction.id.toString()),
                    transactionToMap(transaction),
                    SetOptions.merge(),
                )
            }
            batch.commit().await()
        }
    }

    private suspend fun pullBalances(uid: String): List<BalanceEntity> {
        val snapshot = firestore.collection(USER_COLLECTION).document(uid)
            .collection(BALANCES_COLLECTION).get().await()
        return snapshot.documents.mapNotNull { document ->
            val data = document.data ?: return@mapNotNull null
            balanceFromMap(document.id, data)
        }.sortedBy { it.createdAt }
    }

    private suspend fun pullTransactions(uid: String): List<TransactionEntity> {
        val snapshot = firestore.collection(USER_COLLECTION).document(uid)
            .collection(TRANSACTIONS_COLLECTION).get().await()
        return snapshot.documents.mapNotNull { document ->
            val data = document.data ?: return@mapNotNull null
            transactionFromMap(document.id, data)
        }
    }

    companion object {
        private const val TAG = "CloudSyncRepository"
        private const val USER_COLLECTION = "users"
        private const val BALANCES_COLLECTION = "balances"
        private const val TRANSACTIONS_COLLECTION = "transactions"
        private const val BATCH_SIZE = 400
    }
}