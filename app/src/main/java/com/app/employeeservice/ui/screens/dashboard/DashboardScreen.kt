package com.app.employeeservice.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val APPGreen = Color(0xFF075B32)
private val LightGreen = Color(0xFFF1F8F4)
private val Gold = Color(0xFFD39D1A)

@Composable
fun DashboardScreen(
    onSalaryClick: () -> Unit = {},
    onIncomeTaxClick: () -> Unit = {},
    onProvidentFundClick: () -> Unit = {},
    onPFLoanClick: () -> Unit = {},
    onLeavesClick: () -> Unit = {},
    onDocumentsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    /*
     * Current date and time
     *
     * This value changes every second.
     */
    var currentTime by remember {
        mutableStateOf(Date())
    }

    LaunchedEffect(Unit) {

        while (true) {

            currentTime = Date()

            delay(1000)
        }
    }

    /*
     * Format current date
     */
    val dateText = remember(currentTime) {

        SimpleDateFormat(
            "EEEE, dd MMMM yyyy",
            Locale.getDefault()
        ).format(currentTime)
    }

    /*
     * Format current time
     */
    val timeText = remember(currentTime) {

        SimpleDateFormat(
            "hh:mm:ss a",
            Locale.getDefault()
        ).format(currentTime)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAF9))
    ) {

        DashboardTopBar()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 24.dp
            ),

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            /*
             * Employee profile header
             */
            item {

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                EmployeeHeader(
                    onClick = onProfileClick
                )
            }

            /*
             * Dynamic date and time
             */
            item {

                DateAndTimeCard(
                    date = dateText,
                    time = timeText
                )
            }

            /*
             * Quick access modules
             */
            item {

                QuickAccessSection(

                    onSalaryClick = onSalaryClick,

                    onIncomeTaxClick = onIncomeTaxClick,

                    onProvidentFundClick = onProvidentFundClick,

                    onPFLoanClick = onPFLoanClick,

                    onLeavesClick = onLeavesClick,

                    onDocumentsClick = onDocumentsClick
                )
            }

            /*
             * Announcement
             */
            item {

                AnnouncementCard()
            }

            /*
             * Leave summary
             */
            item {

                LeaveSummary()
            }
        }
    }
}


/* =========================================================
   TOP BAR
   ========================================================= */

@Composable
private fun DashboardTopBar() {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "APP",
                color = APPGreen,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(32.dp)
                    .background(Color.LightGray)
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = "Employee Self Service",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


/* =========================================================
   EMPLOYEE HEADER
   ========================================================= */

@Composable
private fun EmployeeHeader(
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = APPGreen
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            /*
             * Employee image
             */
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color.White),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Person,

                    contentDescription = "Employee Profile",

                    tint = APPGreen,

                    modifier = Modifier.size(46.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Good Morning,",

                    color = Color.White,

                    fontSize = 14.sp
                )

                Text(
                    text = "Ayesha Shahzadi",

                    color = Color.White,

                    fontSize = 21.sp,

                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Software Engineer",

                    color = Color.White.copy(alpha = 0.9f),

                    fontSize = 14.sp
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Surface(
                    shape = RoundedCornerShape(20.dp),

                    color = Color.White.copy(
                        alpha = 0.16f
                    )
                ) {

                    Text(
                        text = "Employee ID: APP-1023",

                        color = Color.White,

                        fontSize = 12.sp,

                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        )
                    )
                }
            }
        }
    }
}


/* =========================================================
   DATE AND TIME
   ========================================================= */

@Composable
private fun DateAndTimeCard(
    date: String,
    time: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(14.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.CalendarMonth,

                    contentDescription = "Date",

                    tint = APPGreen,

                    modifier = Modifier.size(22.dp)
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = date,

                    fontSize = 14.sp,

                    fontWeight = FontWeight.Medium,

                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Current Time",

                    color = Color.Gray,

                    fontSize = 12.sp,

                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = time,

                    color = APPGreen,

                    fontSize = 18.sp,

                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Integrity • Accuracy • Independence",

                fontSize = 10.sp,

                fontWeight = FontWeight.Medium,

                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


/* =========================================================
   QUICK ACCESS
   ========================================================= */

@Composable
private fun QuickAccessSection(
    onSalaryClick: () -> Unit,
    onIncomeTaxClick: () -> Unit,
    onProvidentFundClick: () -> Unit,
    onPFLoanClick: () -> Unit,
    onLeavesClick: () -> Unit,
    onDocumentsClick: () -> Unit
) {

    Column {

        Text(
            text = "Quick Access",

            fontSize = 19.sp,

            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )


        /*
         * ROW 1
         */

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            ModuleCard(
                title = "Salary Slips",

                description =
                    "View and download your salary slips",

                icon = Icons.Default.Payments,

                modifier = Modifier.weight(1f),

                onClick = onSalaryClick
            )

            ModuleCard(
                title = "Income Tax",

                description =
                    "View and download tax certificates",

                icon = Icons.Default.Description,

                modifier = Modifier.weight(1f),

                onClick = onIncomeTaxClick
            )
        }


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        /*
         * ROW 2
         */

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            ModuleCard(
                title = "Provident Fund",

                description =
                    "Check your PF balance & history",

                icon = Icons.Default.AccountBalance,

                modifier = Modifier.weight(1f),

                onClick = onProvidentFundClick
            )

            ModuleCard(
                title = "PF Loan",

                description =
                    "View your loan details and status",

                icon = Icons.Default.Wallet,

                modifier = Modifier.weight(1f),

                onClick = onPFLoanClick
            )
        }


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        /*
         * ROW 3
         */

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            ModuleCard(
                title = "Leaves",

                description =
                    "View leave balance and history",

                icon = Icons.Default.CalendarMonth,

                modifier = Modifier.weight(1f),

                onClick = onLeavesClick
            )

            ModuleCard(
                title = "Documents",

                description =
                    "Official documents and letters",

                icon = Icons.Default.Folder,

                modifier = Modifier.weight(1f),

                onClick = onDocumentsClick
            )
        }
    }
}


/* =========================================================
   MODULE CARD
   ========================================================= */

@Composable
private fun ModuleCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Card(
        onClick = onClick,

        modifier = modifier.height(155.dp),

        shape = RoundedCornerShape(12.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            Icon(
                imageVector = icon,

                contentDescription = title,

                tint = APPGreen,

                modifier = Modifier.size(30.dp)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = title,

                fontSize = 15.sp,

                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = description,

                fontSize = 11.sp,

                lineHeight = 15.sp,

                modifier = Modifier.weight(1f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.End
            ) {

                Icon(
                    imageVector =
                        Icons.AutoMirrored.Filled.ArrowForward,

                    contentDescription =
                        "Open $title",

                    tint = APPGreen
                )
            }
        }
    }
}


/* =========================================================
   ANNOUNCEMENT
   ========================================================= */

@Composable
private fun AnnouncementCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(14.dp),

        colors = CardDefaults.cardColors(
            containerColor = LightGreen
        )
    ) {

        Row(
            modifier = Modifier.padding(16.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Campaign,

                    contentDescription =
                        "Announcement",

                    tint = APPGreen
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {

                Text(
                    text = "Latest Announcement",

                    color = APPGreen,

                    fontSize = 15.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text =
                        "Income Tax Certificates for FY 2025-26 have been uploaded. You can download them from Income Tax section.",

                    fontSize = 12.sp,

                    lineHeight = 18.sp
                )
            }
        }
    }
}


/* =========================================================
   LEAVE SUMMARY
   ========================================================= */

@Composable
private fun LeaveSummary() {

    Column {

        Text(
            text = "Leave Summary",

            fontSize = 19.sp,

            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(10.dp)
        ) {

            LeaveCard(
                title = "Casual Leave",

                remaining = "08",

                used = "04",

                total = "12",

                modifier = Modifier.weight(1f)
            )

            LeaveCard(
                title = "Earned Leave",

                remaining = "24",

                used = "06",

                total = "30",

                modifier = Modifier.weight(1f),

                gold = true
            )
        }
    }
}


/* =========================================================
   LEAVE CARD
   ========================================================= */

@Composable
private fun LeaveCard(
    title: String,
    remaining: String,
    used: String,
    total: String,
    modifier: Modifier,
    gold: Boolean = false
) {

    Card(
        modifier = modifier,

        shape = RoundedCornerShape(12.dp),

        colors = CardDefaults.cardColors(
            containerColor =
                if (gold) {
                    Color(0xFFFFFBF0)
                } else {
                    LightGreen
                }
        )
    ) {

        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            Text(
                text = title,

                fontSize = 13.sp,

                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Row(
                verticalAlignment =
                    Alignment.Bottom
            ) {

                Text(
                    text = remaining,

                    fontSize = 28.sp,

                    fontWeight = FontWeight.Bold,

                    color =
                        if (gold) {
                            Gold
                        } else {
                            APPGreen
                        }
                )

                Spacer(
                    modifier = Modifier.width(5.dp)
                )

                Text(
                    text = "Remaining",

                    fontSize = 11.sp
                )
            }

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Used: $used  |  Total: $total",

                fontSize = 10.sp
            )
        }
    }
}