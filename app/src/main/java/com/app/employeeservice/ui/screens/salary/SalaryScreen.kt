package com.app.employeeservice.ui.screens.salary

// =============================================================
// IMPORTS
// =============================================================

import android.content.ContentValues
import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate


// =============================================================
// COLORS
// =============================================================

private val APPGreen = Color(0xFF075B32)
private val LightGreen = Color(0xFFF1F8F4)


// =============================================================
// SALARY SCREEN
// =============================================================

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Composable
fun SalaryScreen(
    employeeId: String,
    onBackClick: () -> Unit
) {

    // =========================================================
    // CONTEXT
    // =========================================================

    val context = LocalContext.current


    // =========================================================
    // EMPLOYEE DATA
    // =========================================================

    /*
     * TEMPORARY DATA
     *
     * Later these values will come from the logged-in
     * employee's database record.
     */

    val employeeName = "Ayesha Shahzadi"
    val employeeId = "APP-1023"


    // =========================================================
    // FINANCIAL YEARS
    // =========================================================

    /*
     * Financial year:
     *
     * 01 July → 30 June
     */

    val currentDate = LocalDate.now()

    val currentYear = currentDate.year

    val currentFinancialStartYear =
        if (currentDate.monthValue >= 7) {
            currentYear
        } else {
            currentYear - 1
        }

    /*
     * Last 10 financial years.
     */

    val financialYears = remember {

        (0..9).map { difference ->

            val startYear =
                currentFinancialStartYear - difference

            "$startYear - ${startYear + 1}"
        }
    }


    // =========================================================
    // MONTHS
    // =========================================================

    /*
     * Financial-year month order:
     *
     * July → August → September → October
     * → November → December → January
     * → February → March → April → May → June
     */

    val months = listOf(
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
        "January",
        "February",
        "March",
        "April",
        "May",
        "June"
    )


    // =========================================================
    // SELECTED VALUES
    // =========================================================

    var selectedFinancialYear by remember {
        mutableStateOf("")
    }

    var fromMonth by remember {
        mutableStateOf("")
    }

    var toMonth by remember {
        mutableStateOf("")
    }


    // =========================================================
    // DROPDOWN STATES
    // =========================================================

    var financialYearExpanded by remember {
        mutableStateOf(false)
    }

    var fromExpanded by remember {
        mutableStateOf(false)
    }

    var toExpanded by remember {
        mutableStateOf(false)
    }


    // =========================================================
    // ERROR MESSAGE
    // =========================================================

    var errorMessage by remember {
        mutableStateOf("")
    }


    // =========================================================
    // SEARCH RESULT
    // =========================================================

    var showResults by remember {
        mutableStateOf(false)
    }


    // =========================================================
    // SAVE AS PDF LAUNCHER
    // =========================================================

    /*
     * Opens Android's Save As dialog.
     *
     * The employee can choose:
     *
     * - File name
     * - Folder
     * - Storage location
     */

    val saveAsLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.CreateDocument(
                    "application/pdf"
                )
        ) { uri: Uri? ->

            if (uri != null) {

                createSalaryPdf(
                    context = context,
                    uri = uri,
                    employeeName = employeeName,
                    employeeId = employeeId,
                    financialYear =
                        selectedFinancialYear,
                    fromMonth = fromMonth,
                    toMonth = toMonth
                )
            }
        }


    // =========================================================
    // MAIN SCREEN
    // =========================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAF9))
    ) {

        // =====================================================
        // TOP BAR
        // =====================================================

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,

                        contentDescription = "Back",

                        tint = APPGreen
                    )
                }

                Text(
                    text = "Salary Slips",

                    fontSize = 20.sp,

                    fontWeight = FontWeight.Bold
                )
            }
        }


        // =====================================================
        // CONTENT
        // =====================================================

        LazyColumn(
            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 18.dp,
                bottom = 30.dp
            ),

            verticalArrangement =
                Arrangement.spacedBy(14.dp)
        ) {

            // =================================================
            // TITLE
            // =================================================

            item {

                Text(
                    text = "My Salary Slips",

                    fontSize = 24.sp,

                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text =
                        "Select the financial year and month range to view your salary slips.",

                    fontSize = 13.sp,

                    color = Color.Gray
                )
            }


            // =================================================
            // EMPLOYEE INFORMATION
            // =================================================

            item {

                Card(
                    modifier =
                        Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(14.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                LightGreen
                        )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Person,

                            contentDescription =
                                "Employee",

                            tint = APPGreen
                        )

                        Spacer(
                            modifier =
                                Modifier.width(12.dp)
                        )

                        Column {

                            Text(
                                text = "Employee",

                                fontSize = 12.sp,

                                color = Color.Gray
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(3.dp)
                            )

                            Text(
                                text = employeeName,

                                fontSize = 16.sp,

                                fontWeight =
                                    FontWeight.Bold
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(2.dp)
                            )

                            Text(
                                text = employeeId,

                                fontSize = 12.sp,

                                color = Color.Gray
                            )
                        }
                    }
                }
            }


            // =================================================
            // FINANCIAL YEAR
            // =================================================

            item {

                Text(
                    text = "Financial Year",

                    fontSize = 14.sp,

                    fontWeight =
                        FontWeight.SemiBold
                )

                ExposedDropdownMenuBox(
                    expanded =
                        financialYearExpanded,

                    onExpandedChange = {

                        financialYearExpanded =
                            !financialYearExpanded
                    }
                ) {

                    OutlinedTextField(
                        value =
                            selectedFinancialYear,

                        onValueChange = {},

                        readOnly = true,

                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),

                        label = {
                            Text(
                                "Select Financial Year"
                            )
                        },

                        placeholder = {
                            Text(
                                "Example: 2026 - 2027"
                            )
                        },

                        leadingIcon = {

                            Icon(
                                imageVector =
                                    Icons.Default.CalendarMonth,

                                contentDescription =
                                    "Financial Year",

                                tint = APPGreen
                            )
                        },

                        trailingIcon = {

                            ExposedDropdownMenuDefaults
                                .TrailingIcon(
                                    expanded =
                                        financialYearExpanded
                                )
                        },

                        shape =
                            RoundedCornerShape(12.dp)
                    )

                    ExposedDropdownMenu(
                        expanded =
                            financialYearExpanded,

                        onDismissRequest = {

                            financialYearExpanded =
                                false
                        }
                    ) {

                        financialYears.forEach { year ->

                            DropdownMenuItem(
                                text = {
                                    Text(year)
                                },

                                onClick = {

                                    selectedFinancialYear =
                                        year

                                    financialYearExpanded =
                                        false

                                    errorMessage = ""

                                    showResults = false
                                }
                            )
                        }
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(5.dp)
                )

                Text(
                    text =
                        "Financial year runs from 01 July to 30 June.",

                    fontSize = 12.sp,

                    color = Color.Gray
                )
            }


            // =================================================
            // FROM MONTH
            // =================================================

            item {

                Text(
                    text = "From Month",

                    fontSize = 14.sp,

                    fontWeight =
                        FontWeight.SemiBold
                )

                ExposedDropdownMenuBox(
                    expanded = fromExpanded,

                    onExpandedChange = {

                        fromExpanded =
                            !fromExpanded
                    }
                ) {

                    OutlinedTextField(
                        value = fromMonth,

                        onValueChange = {},

                        readOnly = true,

                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),

                        label = {
                            Text(
                                "Select starting month"
                            )
                        },

                        trailingIcon = {

                            ExposedDropdownMenuDefaults
                                .TrailingIcon(
                                    expanded =
                                        fromExpanded
                                )
                        },

                        shape =
                            RoundedCornerShape(12.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = fromExpanded,

                        onDismissRequest = {

                            fromExpanded = false
                        }
                    ) {

                        months.forEach { month ->

                            DropdownMenuItem(
                                text = {
                                    Text(month)
                                },

                                onClick = {

                                    fromMonth = month

                                    /*
                                     * Clear To Month if it
                                     * becomes invalid.
                                     */

                                    if (
                                        toMonth.isNotEmpty() &&
                                        months.indexOf(toMonth) <
                                        months.indexOf(month)
                                    ) {

                                        toMonth = ""
                                    }

                                    fromExpanded = false

                                    errorMessage = ""

                                    showResults = false
                                }
                            )
                        }
                    }
                }
            }


            // =================================================
            // TO MONTH
            // =================================================

            item {

                Text(
                    text = "To Month",

                    fontSize = 14.sp,

                    fontWeight =
                        FontWeight.SemiBold
                )

                ExposedDropdownMenuBox(
                    expanded = toExpanded,

                    onExpandedChange = {

                        toExpanded =
                            !toExpanded
                    }
                ) {

                    OutlinedTextField(
                        value = toMonth,

                        onValueChange = {},

                        readOnly = true,

                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),

                        label = {
                            Text(
                                "Select ending month"
                            )
                        },

                        trailingIcon = {

                            ExposedDropdownMenuDefaults
                                .TrailingIcon(
                                    expanded =
                                        toExpanded
                                )
                        },

                        shape =
                            RoundedCornerShape(12.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = toExpanded,

                        onDismissRequest = {

                            toExpanded = false
                        }
                    ) {

                        months.forEach { month ->

                            val isBeforeFromMonth =
                                fromMonth.isNotEmpty() &&
                                        months.indexOf(month) <
                                        months.indexOf(fromMonth)

                            DropdownMenuItem(
                                text = {

                                    Text(
                                        text = month,

                                        color =
                                            if (
                                                isBeforeFromMonth
                                            ) {
                                                Color.LightGray
                                            } else {
                                                Color.Unspecified
                                            }
                                    )
                                },

                                enabled =
                                    !isBeforeFromMonth,

                                onClick = {

                                    toMonth = month

                                    toExpanded = false

                                    errorMessage = ""

                                    showResults = false
                                }
                            )
                        }
                    }
                }
            }


            // =================================================
            // ERROR MESSAGE
            // =================================================

            item {

                if (errorMessage.isNotEmpty()) {

                    Text(
                        text = errorMessage,

                        color = Color.Red,

                        fontSize = 13.sp,

                        fontWeight =
                            FontWeight.Medium
                    )
                }
            }


            // =================================================
            // VIEW SALARY SLIPS BUTTON
            // =================================================

            item {

                Button(
                    onClick = {

                        when {

                            selectedFinancialYear.isEmpty() -> {

                                errorMessage =
                                    "Please select a financial year."

                                showResults = false
                            }

                            fromMonth.isEmpty() -> {

                                errorMessage =
                                    "Please select the starting month."

                                showResults = false
                            }

                            toMonth.isEmpty() -> {

                                errorMessage =
                                    "Please select the ending month."

                                showResults = false
                            }

                            months.indexOf(toMonth) <
                                    months.indexOf(fromMonth) -> {

                                errorMessage =
                                    "To Month cannot be before From Month."

                                showResults = false
                            }

                            else -> {

                                errorMessage = ""

                                /*
                                 * DATABASE/API WILL BE
                                 * CONNECTED HERE.
                                 *
                                 * The actual salary-slip
                                 * records will eventually
                                 * be retrieved here.
                                 */

                                showResults = true
                            }
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    shape =
                        RoundedCornerShape(12.dp),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                APPGreen
                        )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Search,

                        contentDescription =
                            "Search"
                    )

                    Spacer(
                        modifier =
                            Modifier.width(8.dp)
                    )

                    Text(
                        text =
                            "View Salary Slips",

                        fontSize = 15.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }


            // =================================================
            // SALARY SLIP RESULT
            // =================================================

            if (showResults) {

                item {

                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(14.dp),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color.White
                            ),

                        elevation =
                            CardDefaults.cardElevation(
                                defaultElevation = 1.dp
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(16.dp)
                        ) {

                            // =================================
                            // RESULT TITLE
                            // =================================

                            Text(
                                text =
                                    "Salary Slips",

                                fontSize = 18.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color = APPGreen
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(10.dp)
                            )


                            // =================================
                            // EMPLOYEE
                            // =================================

                            Text(
                                text =
                                    "Employee: $employeeName",

                                fontSize = 13.sp
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )


                            // =================================
                            // EMPLOYEE ID
                            // =================================

                            Text(
                                text =
                                    "Employee ID: $employeeId",

                                fontSize = 13.sp
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )


                            // =================================
                            // FINANCIAL YEAR
                            // =================================

                            Text(
                                text =
                                    "Financial Year: $selectedFinancialYear",

                                fontSize = 13.sp
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )


                            // =================================
                            // PERIOD
                            // =================================

                            Text(
                                text =
                                    "Period: $fromMonth to $toMonth",

                                fontSize = 13.sp
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(14.dp)
                            )


                            // =================================
                            // DATABASE RESULT
                            // =================================

                            Text(
                                text =
                                    "Salary slips matching the selected period will appear here when the database is connected.",

                                fontSize = 12.sp,

                                color = Color.Gray
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(18.dp)
                            )


                            // =================================
                            // EXPORT BUTTON
                            // =================================

                            Button(
                                onClick = {

                                    exportSalaryPdf(
                                        context =
                                            context,

                                        employeeName =
                                            employeeName,

                                        employeeId =
                                            employeeId,

                                        financialYear =
                                            selectedFinancialYear,

                                        fromMonth =
                                            fromMonth,

                                        toMonth =
                                            toMonth
                                    )
                                },

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(12.dp),

                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            APPGreen
                                    )
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.Download,

                                    contentDescription =
                                        "Export PDF"
                                )

                                Spacer(
                                    modifier =
                                        Modifier.width(8.dp)
                                )

                                Text(
                                    text =
                                        "Export PDF",

                                    fontSize = 15.sp,

                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(10.dp)
                            )


                            // =================================
                            // SAVE AS BUTTON
                            // =================================

                            OutlinedButton(
                                onClick = {

                                    val safeYear =
                                        selectedFinancialYear
                                            .replace(
                                                " ",
                                                "_"
                                            )

                                    val fileName =
                                        "Salary_Slips_${employeeId}_${safeYear}.pdf"

                                    saveAsLauncher.launch(
                                        fileName
                                    )
                                },

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(12.dp),

                                colors =
                                    ButtonDefaults
                                        .outlinedButtonColors(
                                            contentColor =
                                                APPGreen
                                        )
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.Save,

                                    contentDescription =
                                        "Save As"
                                )

                                Spacer(
                                    modifier =
                                        Modifier.width(8.dp)
                                )

                                Text(
                                    text =
                                        "Save As",

                                    fontSize = 15.sp,

                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


// =============================================================
// CREATE SALARY PDF
// =============================================================

private fun createSalaryPdf(
    context: Context,
    uri: Uri,
    employeeName: String,
    employeeId: String,
    financialYear: String,
    fromMonth: String,
    toMonth: String
) {

    try {

        val pdfDocument = PdfDocument()

        val pageInfo =
            PdfDocument.PageInfo.Builder(
                595,
                842,
                1
            ).create()

        val page =
            pdfDocument.startPage(pageInfo)

        val canvas = page.canvas

        val paint = Paint()


        // =====================================================
        // PDF TITLE
        // =====================================================

        paint.textSize = 24f
        paint.isFakeBoldText = true

        canvas.drawText(
            "Associated Press of Pakistan",
            50f,
            70f,
            paint
        )


        // =====================================================
        // PDF SUBTITLE
        // =====================================================

        paint.textSize = 20f

        canvas.drawText(
            "Employee Salary Slips",
            50f,
            110f,
            paint
        )


        // =====================================================
        // EMPLOYEE DETAILS
        // =====================================================

        paint.textSize = 14f
        paint.isFakeBoldText = false

        canvas.drawText(
            "Employee Name: $employeeName",
            50f,
            160f,
            paint
        )

        canvas.drawText(
            "Employee ID: $employeeId",
            50f,
            190f,
            paint
        )

        canvas.drawText(
            "Financial Year: $financialYear",
            50f,
            220f,
            paint
        )

        canvas.drawText(
            "From Month: $fromMonth",
            50f,
            250f,
            paint
        )

        canvas.drawText(
            "To Month: $toMonth",
            50f,
            280f,
            paint
        )


        // =====================================================
        // PDF NOTE
        // =====================================================

        paint.textSize = 12f

        canvas.drawText(
            "Financial year: 01 July to 30 June",
            50f,
            330f,
            paint
        )

        canvas.drawText(
            "Salary slip records will be retrieved from the database.",
            50f,
            365f,
            paint
        )


        // =====================================================
        // FINISH PDF PAGE
        // =====================================================

        pdfDocument.finishPage(page)


        // =====================================================
        // WRITE PDF TO SELECTED LOCATION
        // =====================================================

        context.contentResolver
            .openOutputStream(uri)
            ?.use { outputStream ->

                pdfDocument.writeTo(
                    outputStream
                )
            }


        // =====================================================
        // CLOSE PDF
        // =====================================================

        pdfDocument.close()


        // =====================================================
        // SUCCESS MESSAGE
        // =====================================================

        Toast.makeText(
            context,
            "PDF saved successfully.",
            Toast.LENGTH_LONG
        ).show()

    } catch (e: Exception) {

        Toast.makeText(
            context,
            "Could not save PDF: ${e.message}",
            Toast.LENGTH_LONG
        ).show()
    }
}


// =============================================================
// EXPORT SALARY PDF TO DOWNLOADS
// =============================================================

private fun exportSalaryPdf(
    context: Context,
    employeeName: String,
    employeeId: String,
    financialYear: String,
    fromMonth: String,
    toMonth: String
) {

    try {

        // =====================================================
        // PDF FILE NAME
        // =====================================================

        val safeYear =
            financialYear.replace(
                " ",
                "_"
            )

        val fileName =
            "Salary_Slips_${employeeId}_${safeYear}.pdf"


        // =====================================================
        // CREATE PDF DOCUMENT
        // =====================================================

        val pdfDocument = PdfDocument()

        val pageInfo =
            PdfDocument.PageInfo.Builder(
                595,
                842,
                1
            ).create()

        val page =
            pdfDocument.startPage(pageInfo)

        val canvas = page.canvas

        val paint = Paint()


        // =====================================================
        // PDF TITLE
        // =====================================================

        paint.textSize = 24f
        paint.isFakeBoldText = true

        canvas.drawText(
            "Associated Press of Pakistan",
            50f,
            70f,
            paint
        )


        // =====================================================
        // PDF SUBTITLE
        // =====================================================

        paint.textSize = 20f

        canvas.drawText(
            "Employee Salary Slips",
            50f,
            110f,
            paint
        )


        // =====================================================
        // EMPLOYEE INFORMATION
        // =====================================================

        paint.textSize = 14f
        paint.isFakeBoldText = false

        canvas.drawText(
            "Employee Name: $employeeName",
            50f,
            160f,
            paint
        )

        canvas.drawText(
            "Employee ID: $employeeId",
            50f,
            190f,
            paint
        )

        canvas.drawText(
            "Financial Year: $financialYear",
            50f,
            220f,
            paint
        )

        canvas.drawText(
            "From Month: $fromMonth",
            50f,
            250f,
            paint
        )

        canvas.drawText(
            "To Month: $toMonth",
            50f,
            280f,
            paint
        )


        // =====================================================
        // PDF NOTE
        // =====================================================

        paint.textSize = 12f

        canvas.drawText(
            "Financial year: 01 July to 30 June",
            50f,
            330f,
            paint
        )

        canvas.drawText(
            "Salary slip records will be retrieved from the database.",
            50f,
            365f,
            paint
        )


        // =====================================================
        // FINISH PDF
        // =====================================================

        pdfDocument.finishPage(page)


        // =====================================================
        // SAVE TO DOWNLOADS
        // =====================================================

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val contentValues =
                ContentValues().apply {

                    put(
                        MediaStore.Downloads.DISPLAY_NAME,
                        fileName
                    )

                    put(
                        MediaStore.Downloads.MIME_TYPE,
                        "application/pdf"
                    )

                    put(
                        MediaStore.Downloads.RELATIVE_PATH,
                        Environment.DIRECTORY_DOWNLOADS
                    )

                    put(
                        MediaStore.Downloads.IS_PENDING,
                        1
                    )
                }

            val resolver =
                context.contentResolver

            val uri =
                resolver.insert(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                    contentValues
                )

            if (uri != null) {

                resolver
                    .openOutputStream(uri)
                    ?.use { outputStream ->

                        pdfDocument.writeTo(
                            outputStream
                        )
                    }

                contentValues.clear()

                contentValues.put(
                    MediaStore.Downloads.IS_PENDING,
                    0
                )

                resolver.update(
                    uri,
                    contentValues,
                    null,
                    null
                )

                Toast.makeText(
                    context,
                    "PDF exported to Downloads.",
                    Toast.LENGTH_LONG
                ).show()

            } else {

                Toast.makeText(
                    context,
                    "Unable to create PDF file.",
                    Toast.LENGTH_LONG
                ).show()
            }

        } else {

            // =================================================
            // OLD ANDROID VERSIONS
            // =================================================

            val downloadsDirectory =
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS
                )

            if (!downloadsDirectory.exists()) {
                downloadsDirectory.mkdirs()
            }

            val file =
                File(
                    downloadsDirectory,
                    fileName
                )

            FileOutputStream(file).use { outputStream ->

                pdfDocument.writeTo(
                    outputStream
                )
            }

            Toast.makeText(
                context,
                "PDF exported to Downloads.",
                Toast.LENGTH_LONG
            ).show()
        }


        // =====================================================
        // CLOSE PDF
        // =====================================================

        pdfDocument.close()

    } catch (e: Exception) {

        Toast.makeText(
            context,
            "Export failed: ${e.message}",
            Toast.LENGTH_LONG
        ).show()
    }
}