package kg.iuca.campuscard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kg.iuca.campuscard.ui.theme.CampusCardLabTheme

/**
 * New homework: alternating row colors in Jetpack Compose.
 *
 * Odd rows: theme primary color.
 * Even rows: light grey, with three randomly selected yellow letter boxes.
 */
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val words = listOf(
        "ANDROID",
        "KOTLIN",
        "COMPOSE",
        "MOBILE",
        "STUDIO",
        "LAYOUT"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Alternating Row Colors",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Every second row is grey with random yellow letters",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        words.forEachIndexed { index, word ->
            LetterRow(
                word = word,
                rowNumber = index + 1
            )
        }
    }
}

@Composable
private fun LetterRow(word: String, rowNumber: Int) {
    val isEvenRow = rowNumber % 2 == 0
    val rowColor = if (isEvenRow) {
        Color.LightGray
    } else {
        MaterialTheme.colorScheme.primary
    }

    // Remember the random positions so they do not jump on recomposition.
    // Each even row gets exactly three distinct highlighted letters.
    val yellowIndices = remember(rowNumber, word) {
        if (isEvenRow) {
            word.indices.shuffled().take(3).toSet()
        } else {
            emptySet<Int>()
        }
    }

    Row(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .background(rowColor, RoundedCornerShape(8.dp))
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        word.forEachIndexed { letterIndex, letter ->
            val isHighlighted = isEvenRow && letterIndex in yellowIndices
            val tileColor = if (isHighlighted) Color.Yellow else rowColor

            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(tileColor, RoundedCornerShape(4.dp))
                    .border(
                        width = 1.dp,
                        color = if (isEvenRow) {
                            Color.DarkGray.copy(alpha = 0.25f)
                        } else {
                            Color.White.copy(alpha = 0.65f)
                        },
                        shape = RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = letter.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isEvenRow) {
                        Color.Black
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    CampusCardLabTheme {
        Greeting()
    }
}
