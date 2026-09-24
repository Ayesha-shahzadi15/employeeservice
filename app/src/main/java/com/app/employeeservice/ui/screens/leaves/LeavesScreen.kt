package com.app.employeeservice.ui.screens.leaves

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val APPGreen = Color(0xFF075B32)

@Composable
fun LeavesScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAF9))
    ) {

        Surface(color = Color.White) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = APPGreen
                    )
                }

                Text(
                    "Leaves",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                "Leave Balance",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            LeaveType("Casual Leave", "08", "12")
            LeaveType("Earned Leave", "24", "30")
            LeaveType("Medical Leave", "10", "15")

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Leave History",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            HistoryRow("05 August 2026", "Casual Leave", "Approved")
            HistoryRow("18 July 2026", "Earned Leave", "Approved")
        }
    }
}

@Composable
private fun LeaveType(
    title: String,
    remaining: String,
    total: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = APPGreen
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    title,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    "Total: $total days",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Text(
                remaining,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = APPGreen
            )
        }
    }
}

@Composable
private fun HistoryRow(
    date: String,
    type: String,
    status: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(type, fontWeight = FontWeight.Bold)
            Text(date, fontSize = 12.sp, color = Color.Gray)
            Text(
                status,
                color = APPGreen,
                fontSize = 12.sp
            )
        }
    }
}