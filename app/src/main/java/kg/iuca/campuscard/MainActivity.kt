package kg.iuca.campuscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kg.iuca.campuscard.ui.theme.CampusCardLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampusCardLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CampusCardScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CampusCardScreen(modifier: Modifier = Modifier) {
    var useSpaceBetween by remember { mutableStateOf(true) }
    var centerScheduleRows by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ProfileHeader(name = "Medikhanov Arman", status = "На занятии · корпус B")

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Campus Card",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Student ID: 1712",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Сегодня", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            ScheduleRow("09:00", "CS101 · Введение в программирование", "Лаб. 3", centerScheduleRows)
            ScheduleRow("11:00", "MATH210 · Дискретная математика", "A-204", centerScheduleRows)
            ScheduleRow(
                time = "14:00",
                title = "Мобильная разработка: проектирование интерфейсов и практика Compose",
                room = "Зал 1",
                centerVertically = centerScheduleRows
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Эксперименты", style = MaterialTheme.typography.titleMedium)
            ExperimentSwitch(
                title = "Распределить кнопки по краям",
                checked = useSpaceBetween,
                onCheckedChange = { useSpaceBetween = it }
            )
            ExperimentSwitch(
                title = "Выровнять время по центру строки",
                checked = centerScheduleRows,
                onCheckedChange = { centerScheduleRows = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Exercise 2: switch between SpaceBetween and Center + spacedBy(16.dp).
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (useSpaceBetween) {
                Arrangement.SpaceBetween
            } else {
                Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = { dialogMessage = "Карта кампуса · корпус B" }) {
                Text("Карта")
            }
            Button(onClick = { dialogMessage = "QR-код студенческого билета" }) {
                Text("Показать QR")
            }
        }
    }

    dialogMessage?.let { message ->
        AlertDialog(
            onDismissRequest = { dialogMessage = null },
            title = { Text("Campus Card") },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = { dialogMessage = null }) { Text("Закрыть") }
            }
        )
    }
}

@Composable
fun ProfileHeader(name: String, status: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text("Med", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
        Column {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Text(status, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ScheduleRow(time: String, title: String, room: String, centerVertically: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = if (centerVertically) Alignment.CenterVertically else Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = time,
            modifier = Modifier.width(52.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = room,
            modifier = Modifier.width(58.dp),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ExperimentSwitch(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Preview(showBackground = true)
@Composable
private fun CampusCardPreview() {
    CampusCardLabTheme {
        Surface { CampusCardScreen() }
    }
}
