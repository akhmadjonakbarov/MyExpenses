package uz.akbarovdev.myexpenses.features.dashboard.data.sync

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import org.junit.jupiter.api.Test
import uz.akbarovdev.myexpenses.features.dashboard.daos.balance.BalanceEntity
import uz.akbarovdev.myexpenses.features.dashboard.daos.transaction.TransactionEntity

class SyncMappingsTest {

    @Test
    fun `balance maps to document data and back without loss`() {
        val balance = BalanceEntity(id = 7, amount = 1250.0, createdAt = 123456L)

        val map = balanceToMap(balance)
        val restored = balanceFromMap("7", map)

        assertThat(restored).isEqualTo(balance)
    }

    @Test
    fun `balance from map handles Firestore numeric types`() {
        val restored = balanceFromMap(
            "3",
            mapOf(
                FIELD_AMOUNT to 99.5, // Double
                FIELD_CREATED_AT to 42L, // Long
            )
        )

        assertThat(restored).isEqualTo(BalanceEntity(id = 3, amount = 99.5, createdAt = 42L))
    }

    @Test
    fun `balance from non numeric document id returns null`() {
        assertThat(
            balanceFromMap(
                "not-a-number",
                mapOf(FIELD_AMOUNT to 1.0, FIELD_CREATED_AT to 1L)
            )
        ).isNull()
    }

    @Test
    fun `transaction maps to document data and back without loss`() {
        val transaction = TransactionEntity(
            id = 9,
            amount = 350.5,
            type = "INCOME",
            note = "salary",
            receiver = "office",
            category = "Salary",
            createdAt = 999L,
        )

        val map = transactionToMap(transaction)
        val restored = transactionFromMap("9", map)

        assertThat(restored).isEqualTo(transaction)
    }

    @Test
    fun `transaction from map keeps nullable fields null`() {
        val restored = transactionFromMap(
            "4",
            mapOf(
                FIELD_AMOUNT to 100.0,
                FIELD_TYPE to "EXPENSE",
                FIELD_NOTE to null,
                FIELD_RECEIVER to null,
                FIELD_CATEGORY to "Food",
                FIELD_CREATED_AT to 55L,
            )
        )

        assertThat(restored?.note).isNull()
        assertThat(restored?.receiver).isNull()
        assertThat(restored?.category).isEqualTo("Food")
    }

    @Test
    fun `transaction from map ignores unknown extra fields`() {
        val restored = transactionFromMap(
            "2",
            mapOf(
                FIELD_AMOUNT to 10.0,
                FIELD_TYPE to "EXPENSE",
                FIELD_CREATED_AT to 1L,
                "someNewField" to "ignored",
            )
        )

        assertThat(restored?.amount).isEqualTo(10.0)
    }
}