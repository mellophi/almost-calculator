package com.mellophi.calculator.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mellophi.calculator.R
import com.mellophi.calculator.presentation.ui.theme.CalculatorTheme

@Composable
fun CalculatorApp(
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    // State for theme is now correctly read from the ViewModel
    val isDark by viewModel.themeToggle

    // The entire app is wrapped in our custom theme
    CalculatorTheme(darkTheme = isDark) {
        MainScreen(isDark = isDark, viewModel = viewModel)
    }
}


@Composable
fun MainScreen(
    isDark: Boolean,
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    // SunAnimationScreen now correctly uses the theme's background color
    SunAnimationScreen(isDark = isDark) {
        // The main Surface is now transparent to show the animated background
        Surface(modifier = modifier.fillMaxSize(), color = Color.Transparent) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CalculatorTextInput(
                    expression = viewModel.expression.value,
                    result = viewModel.evaluation.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                CalculatorButtonGrid(
                    columns = 4,
                    symbols = listOf(
                        CalculatorItem.Icon(R.drawable.cleaning_service, "AC"), // All Clear
                        CalculatorItem.Icon(R.drawable.just_backspace, "DEL"),
                        CalculatorItem.Text("()", "()"),
                        CalculatorItem.Text("/", "/"),

                        CalculatorItem.Text("7", "7"),
                        CalculatorItem.Text("8", "8"),
                        CalculatorItem.Text("9", "9"),
                        CalculatorItem.Text("X", "X"),


                        CalculatorItem.Text("4", "4"),
                        CalculatorItem.Text("5", "5"),
                        CalculatorItem.Text("6", "6"),
                        CalculatorItem.Text("-", "-"),

                        CalculatorItem.Text("1", "1"),
                        CalculatorItem.Text("2", "2"),
                        CalculatorItem.Text("3", "3"),
                        CalculatorItem.Text("+", "+"),

                        CalculatorItem.Text("0", "0"),
                        CalculatorItem.Text(".", "."),
                        CalculatorItem.Icon(R.drawable.allah_times, "Theme"),// Theme Toggle

                        // Example of a wider button for equals
                        CalculatorItem.Icon(R.drawable.clear_day, "=")
                    ),
                    onClick = viewModel::checkAndUpdateExpression,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun CalculatorButtonGrid(
    columns: Int,
    symbols: List<CalculatorItem>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = symbols,
            span = { item ->
                // Make wide buttons span 2 columns
                if (item.isWide) GridItemSpan(2) else GridItemSpan(1)
            }
        ) { item ->
            CalculatorButton(
                item = item,
                onClick = onClick,
                modifier = Modifier
                    .aspectRatio(if (item.isWide) 2f else 1f) // Adjust aspect ratio for wide buttons
            )
        }
    }
}

@Composable
private fun CalculatorButton(
    item: CalculatorItem,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Button colors are now derived from the MaterialTheme
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = when (item.symbol) {
            "=", "+", "-", "/", "X" -> MaterialTheme.colorScheme.primary
            "AC", "DEL" -> MaterialTheme.colorScheme.errorContainer
            else -> MaterialTheme.colorScheme.surfaceVariant
        },
        contentColor = when (item.symbol) {
            "=", "+", "-", "/", "X" -> MaterialTheme.colorScheme.onPrimary
            "AC", "DEL" -> MaterialTheme.colorScheme.onErrorContainer
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        }
    )

    Button(
        onClick = { onClick(item.symbol) },
        modifier = modifier,
        colors = buttonColors,
        shape = MaterialTheme.shapes.large // Use theme shapes
    ) {
        when (item) {
            is CalculatorItem.Text -> Text(
                text = item.label,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            is CalculatorItem.Icon -> Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.symbol,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun CalculatorTextInput(
    expression: String,
    result: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(150.dp), // Give it a fixed height
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Text colors are now derived from the MaterialTheme
        Text(
            text = expression,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = result,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f), // A bit faded
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun SunAnimationScreen(isDark: Boolean, content: @Composable () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val sunSize = 80.dp

    val sunY by animateDpAsState(
        targetValue = if (isDark) screenHeight + sunSize else -sunSize,
        animationSpec = tween(durationMillis = 1500, easing = LinearEasing),
        label = "sunY"
    )

    // The background color now animates between the theme's background colors
    val bgColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.background,
        animationSpec = tween(durationMillis = 1500),
        label = "bgColor"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        // The main UI content
        content()

        // The Sun/Moon animation
        Canvas(
            modifier = Modifier
                .size(sunSize)
                .offset(x = 0.dp, y = sunY) // Example offset
                .align(Alignment.TopCenter)
        ) {
            drawCircle(
                color = if(isDark) Color.Yellow else Color.Yellow,
                radius = size.minDimension / 2
            )
        }
    }
}

// Updated sealed class to support wide buttons
sealed class CalculatorItem(val symbol: String, val isWide: Boolean = false) {
    data class Text(val label: String, val symbolText: String, val wide: Boolean = false) : CalculatorItem(symbolText, wide)
    data class Icon(val icon: Int, val symbolText: String, val wide: Boolean = false) : CalculatorItem(symbolText, wide)
}