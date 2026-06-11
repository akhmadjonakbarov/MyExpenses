@file:OptIn(ExperimentalMaterial3Api::class)

package uz.akbarovdev.myexpenses.features.transactions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import uz.akbarovdev.myexpenses.R
import uz.akbarovdev.myexpenses.core.design_system.buttons.BackButton
import uz.akbarovdev.myexpenses.core.design_system.top_bar.Title
import uz.akbarovdev.myexpenses.core.enums.TransactionType
import uz.akbarovdev.myexpenses.core.formatters.CurrencyFormatter
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.CategoryUi
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.TransactionGroup
import uz.akbarovdev.myexpenses.features.dashboard.domain.models.TransactionUi
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardAction
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardState
import uz.akbarovdev.myexpenses.features.dashboard.presentation.view_model.DashboardViewModel
import uz.akbarovdev.myexpenses.features.preference.domain.models.CurrencyUi
import uz.akbarovdev.myexpenses.features.transactions.components.BalanceHeroCard
import uz.akbarovdev.myexpenses.features.transactions.components.DailySpendingBarChart
import uz.akbarovdev.myexpenses.ui.currencySymbol
import kotlin.math.min

@Composable
fun TransactionStatisticsRoot(
    navController: NavController,
    viewModel: DashboardViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TransactionStatisticsScreen(
        navController = navController,
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun TransactionStatisticsScreen(
    navController: NavController,
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
) {
    val allTransactions = remember(state.transactionGroups) {
        state.transactionGroups.flatMap { it.transactions }
    }

    val totalIncome = remember(allTransactions) {
        allTransactions.filter { it.type == TransactionType.Income }.sumOf { it.amount }
    }

    val totalExpense = remember(allTransactions) {
        allTransactions.filter { it.type == TransactionType.Expense }.sumOf { it.amount }
    }

    val categoryTotals = remember(allTransactions) {
        allTransactions
            .filter { it.type == TransactionType.Expense }
            .groupBy { it.icon }
            .mapValues { (_, transactions) -> transactions.sumOf { it.amount } }
            .entries
            .sortedByDescending { it.value }
    }

    val topExpenses = remember(allTransactions) {
        allTransactions
            .filter { it.type == TransactionType.Expense }
            .sortedByDescending { it.amount }
            .take(5)
    }

    val hasData = allTransactions.isNotEmpty()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { BackButton(onClick = navController::navigateUp) },
                title = { Title(stringResource(R.string.statistics)) },
            )
        },
    ) { innerPadding ->
        if (hasData) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                BalanceHeroCard(
                    balance = totalIncome - totalExpense,
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                    currencyUi = state.selectedCurrencyUi,
                )

                CategoryDonutChart(
                    categoryTotals = categoryTotals,
                    totalExpense = totalExpense,
                    currencyUi = state.selectedCurrencyUi,
                )

                if (state.transactionGroups.isNotEmpty()) {
                    DailySpendingBarChart(
                        transactionGroups = state.transactionGroups,
                        currencyUi = state.selectedCurrencyUi,
                    )
                }

                TopExpensesSection(
                    topExpenses = topExpenses,
                    currencyUi = state.selectedCurrencyUi,
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    stringResource(R.string.no_transactions),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}





@Composable
private fun CategoryDonutChart(
    categoryTotals: List<Map.Entry<CategoryUi, Double>>,
    totalExpense: Double,
    currencyUi: CurrencyUi,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Text(
                text = stringResource(R.string.category_breakdown),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(16.dp))
            if (totalExpense > 0) {
                val animatedProgress by animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 1000),
                    label = "donutProgress",
                )

                val onSurface = MaterialTheme.colorScheme.onSurface
                val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier.size(160.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val strokeWidth = 32f
                            val padding = strokeWidth / 2f
                            val diameter = min(size.width, size.height) - strokeWidth
                            val topLeft = Offset(
                                (size.width - diameter) / 2f,
                                (size.height - diameter) / 2f
                            )
                            val arcSize = Size(diameter, diameter)

                            var startAngle = -90f
                            categoryTotals.forEachIndexed { index, entry ->
                                val sweep =
                                    (entry.value / totalExpense * 360f).toFloat() * animatedProgress
                                drawArc(
                                    color = entry.key.color,
                                    startAngle = startAngle,
                                    sweepAngle = sweep,
                                    useCenter = false,
                                    topLeft = topLeft,
                                    size = arcSize,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                                )
                                startAngle += sweep
                            }
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${currencySymbol(currencyUi)}${
                                    CurrencyFormatter.format(
                                        totalExpense
                                    )
                                }",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                                color = onSurface,
                                maxLines = 1,
                            )
                            Text(
                                text = stringResource(R.string.total_expense),
                                style = MaterialTheme.typography.labelSmall,
                                color = onSurfaceVariant,
                            )
                        }
                    }

                    Spacer(Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        categoryTotals.take(6).forEach { entry ->
                            val pct = (entry.value / totalExpense * 100).toInt()
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Text(
                                        text = entry.key.emoji,
                                        fontSize = 14.sp,
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        text = stringResource(entry.key.code),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f),
                                    )
                                    Text(
                                        text = "$pct%",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                        ),
                                        color = onSurfaceVariant,
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.surfaceContainerLow,
                                            shape = RoundedCornerShape(2.dp),
                                        ),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(fraction = pct / 100f)
                                            .fillMaxHeight()
                                            .background(
                                                color = entry.key.color,
                                                shape = RoundedCornerShape(2.dp),
                                            ),
                                    )
                                }
                            }
                        }
                        if (categoryTotals.size > 6) {
                            Text(
                                text = stringResource(R.string.more_categories, categoryTotals.size - 6),
                                style = MaterialTheme.typography.labelSmall,
                                color = onSurfaceVariant,
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.no_transactions),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun TopExpensesSection(
    topExpenses: List<TransactionUi>,
    currencyUi: CurrencyUi,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Text(
                text = stringResource(R.string.top_expenses),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(12.dp))
            if (topExpenses.isNotEmpty()) {
                topExpenses.forEachIndexed { index, transaction ->
                    if (index > 0) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        )
                    }
                    TopExpenseRow(
                        rank = index + 1,
                        categoryUi = transaction.icon,
                        amount = transaction.amount,
                        note = transaction.note,
                        currencyUi = currencyUi,
                    )
                }
            } else {
                Text(
                    text = stringResource(R.string.no_transactions),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun TopExpenseRow(
    rank: Int,
    categoryUi: CategoryUi,
    amount: Double,
    note: String,
    currencyUi: CurrencyUi,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "#$rank",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(28.dp),
        )
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = categoryUi.color.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = categoryUi.emoji, fontSize = 18.sp)
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(categoryUi.code),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (note.isNotBlank()) {
                Text(
                    text = note,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text = "${currencySymbol(currencyUi)}${CurrencyFormatter.format(amount)}",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

