package com.app.employeeservice.ui.screens.income_tax

// =============================================================
// IMPORTS
// =============================================================

import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Person
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

import java.util.Calendar


// =============================================================
// COLORS
// =============================================================

private val APPGreen = Color(0xFF075B32)

private val LightGreen = Color(0xFFF1F8F4)


// =============================================================
// INCOME TAX SCREEN
// =============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeTaxScreen(
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
     *
     * IMPORTANT:
     * Employee should NOT be able to edit these values.
     */

    val employeeName = "Ayesha Shahzadi"

    val employeeId = "APP-1023"


    // =========================================================
    // CURRENT DATE
    // =========================================================

    /*
     * IMPORTANT:
     *
     * We are using Calendar instead of java.time.LocalDate
     * because the application supports Android API 24.
     *
     * java.time.LocalDate requires API 26.
     */

    val calendar = Calendar.getInstance()

    val currentYear =
        calendar.get(Calendar.YEAR)

    /*
     * Calendar.MONTH is zero-based:
     *
     * January = 0
     * February = 1
     * ...
     * June = 5
     * July = 6
     */

    val currentMonth =
        calendar.get(Calendar.MONTH)


    // =========================================================
    // CURRENT FINANCIAL YEAR
    // =========================================================

    /*
     * APP financial year:
     *
     * 01 July → 30 June
     *
     * If current month is July or later:
     *
     * 2026 → 2026 - 2027
     *
     * If current month is January-June:
     *
     * 2027 → 2026 - 2027
     */

    val currentFinancialStartYear =
        if (currentMonth >= Calendar.JULY) {
            currentYear
        } else {
            currentYear - 1
        }


    // =========================================================
    // FINANCIAL YEAR DROPDOWN DATA
    // =========================================================

    /*
     * Creates the latest 10 financial years.
     *
     * Example:
     *
     * 2026 - 2027
     * 2025 - 2026
     * 2024 - 2025
     * 2023 - 2024
     * ...
     */

    val financialYears = remember {

        (0..9).map { difference ->

            val startYear =
                currentFinancialStartYear - difference

            "$startYear - ${startYear + 1}"
        }
    }


    // =========================================================
    // SELECTED FINANCIAL YEAR
    // =========================================================

    var selectedFinancialYear by remember {

        mutableStateOf("")
    }


    // =========================================================
    // FINANCIAL YEAR DROPDOWN STATE
    // =========================================================

    var financialYearExpanded by remember {

        mutableStateOf(false)
    }


    // =========================================================
    // ERROR MESSAGE
    // =========================================================

    var errorMessage by remember {

        mutableStateOf("")
    }


    // =========================================================
    // CERTIFICATE RESULT STATE
    // =========================================================

    var showCertificate by remember {

        mutableStateOf(false)
    }


    // =========================================================
    // PDF EXPORT / SAVE AS
    // =========================================================

    /*
     * This opens Android's system "Save As" screen.
     *
     * The employee can choose:
     *
     * - Downloads
     * - Documents
     * - Google Drive
     * - Other available storage locations
     *
     * The file is saved as PDF.
     */

    val exportLauncher =
        rememberLauncherForActivityResult(

            contract =
                ActivityResultContracts.CreateDocument(
                    "application/pdf"
                )

        ) { uri ->

            if (uri != null) {

                try {

                    // =================================================
                    // CREATE PDF DOCUMENT
                    // =================================================

                    val pdfDocument =
                        PdfDocument()


                    // =================================================
                    // CREATE PDF PAGE
                    // =================================================

                    val pageInfo =
                        PdfDocument.PageInfo.Builder(
                            595,
                            842,
                            1
                        ).create()


                    val page =
                        pdfDocument.startPage(
                            pageInfo
                        )


                    // =================================================
                    // PDF CANVAS
                    // =================================================

                    val canvas =
                        page.canvas


                    // =================================================
                    // NORMAL TEXT PAINT
                    // =================================================

                    val paint =
                        Paint().apply {

                            textSize = 16f

                            isAntiAlias = true
                        }


                    // =================================================
                    // BOLD TEXT PAINT
                    // =================================================

                    val boldPaint =
                        Paint().apply {

                            textSize = 18f

                            isFakeBoldText = true

                            isAntiAlias = true
                        }


                    // =================================================
                    // TITLE PAINT
                    // =================================================

                    val titlePaint =
                        Paint().apply {

                            textSize = 24f

                            isFakeBoldText = true

                            isAntiAlias = true
                        }


                    // =================================================
                    // PDF CONTENT POSITION
                    // =================================================

                    var yPosition = 60f


                    // =================================================
                    // ORGANIZATION NAME
                    // =================================================

                    canvas.drawText(
                        "ASSOCIATED PRESS OF PAKISTAN",
                        60f,
                        yPosition,
                        titlePaint
                    )

                    yPosition += 35f


                    // =================================================
                    // EMPLOYEE SELF SERVICE
                    // =================================================

                    canvas.drawText(
                        "EMPLOYEE SELF SERVICE",
                        60f,
                        yPosition,
                        boldPaint
                    )

                    yPosition += 55f


                    // =================================================
                    // CERTIFICATE TITLE
                    // =================================================

                    canvas.drawText(
                        "INCOME TAX CERTIFICATE",
                        60f,
                        yPosition,
                        titlePaint
                    )

                    yPosition += 50f


                    // =================================================
                    // EMPLOYEE NAME
                    // =================================================

                    canvas.drawText(
                        "Employee Name: $employeeName",
                        60f,
                        yPosition,
                        paint
                    )

                    yPosition += 30f


                    // =================================================
                    // EMPLOYEE ID
                    // =================================================

                    canvas.drawText(
                        "Employee ID: $employeeId",
                        60f,
                        yPosition,
                        paint
                    )

                    yPosition += 30f


                    // =================================================
                    // FINANCIAL YEAR
                    // =================================================

                    canvas.drawText(
                        "Financial Year: $selectedFinancialYear",
                        60f,
                        yPosition,
                        paint
                    )

                    yPosition += 50f


                    // =================================================
                    // CERTIFICATE INFORMATION
                    // =================================================

                    canvas.drawText(
                        "Income Tax Certificate",
                        60f,
                        yPosition,
                        boldPaint
                    )

                    yPosition += 35f


                    canvas.drawText(
                        "This certificate is generated for",
                        60f,
                        yPosition,
                        paint
                    )

                    yPosition += 25f


                    canvas.drawText(
                        employeeName,
                        60f,
                        yPosition,
                        paint
                    )

                    yPosition += 25f


                    canvas.drawText(
                        "for the financial year $selectedFinancialYear.",
                        60f,
                        yPosition,
                        paint
                    )

                    yPosition += 60f


                    // =================================================
                    // DATABASE DATA AREA
                    // =================================================

                    /*
                     * TEMPORARY PLACEHOLDER
                     *
                     * Later replace this section with actual
                     * tax information received from the API.
                     */

                    canvas.drawText(
                        "Tax details will be populated",
                        60f,
                        yPosition,
                        paint
                    )

                    yPosition += 25f


                    canvas.drawText(
                        "from the employee database.",
                        60f,
                        yPosition,
                        paint
                    )

                    yPosition += 70f


                    // =================================================
                    // PDF FOOTER
                    // =================================================

                    canvas.drawText(
                        "Generated through APP Employee Self Service",
                        60f,
                        yPosition,
                        paint
                    )


                    // =================================================
                    // FINISH PDF PAGE
                    // =================================================

                    pdfDocument.finishPage(
                        page
                    )


                    // =================================================
                    // SAVE PDF TO SELECTED LOCATION
                    // =================================================

                    context.contentResolver
                        .openOutputStream(uri)
                        ?.use { outputStream ->

                            pdfDocument.writeTo(
                                outputStream
                            )
                        }


                    // =================================================
                    // CLOSE PDF
                    // =================================================

                    pdfDocument.close()

                } catch (exception: Exception) {

                    exception.printStackTrace()
                }
            }
        }


    // =========================================================
    // MAIN SCREEN
    // =========================================================

    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Color(0xFFF8FAF9)
                )
    ) {


        // =====================================================
        // TOP BAR
        // =====================================================

        Surface(

            modifier =
                Modifier.fillMaxWidth(),

            color =
                Color.White
        ) {

            Row(

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        ),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                IconButton(

                    onClick =
                        onBackClick
                ) {

                    Icon(

                        imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,

                        contentDescription =
                            "Back",

                        tint =
                            APPGreen
                    )
                }


                Spacer(
                    modifier =
                        Modifier.width(4.dp)
                )


                Text(

                    text =
                        "Income Tax",

                    fontSize =
                        20.sp,

                    fontWeight =
                        FontWeight.Bold
                )
            }
        }


        // =====================================================
        // MAIN CONTENT
        // =====================================================

        LazyColumn(

            modifier =
                Modifier.fillMaxSize(),

            contentPadding =
                androidx.compose.foundation.layout
                    .PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 18.dp,
                        bottom = 30.dp
                    ),

            verticalArrangement =
                Arrangement.spacedBy(14.dp)
        ) {


            // =================================================
            // PAGE TITLE
            // =================================================

            item {

                Text(

                    text =
                        "My Income Tax Certificate",

                    fontSize =
                        24.sp,

                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )


                Text(

                    text =
                        "Select a financial year to view your income tax certificate.",

                    fontSize =
                        13.sp,

                    color =
                        Color.Gray
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

                        modifier =
                            Modifier
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

                            tint =
                                APPGreen
                        )


                        Spacer(
                            modifier =
                                Modifier.width(12.dp)
                        )


                        Column {

                            Text(

                                text =
                                    "Employee",

                                fontSize =
                                    12.sp,

                                color =
                                    Color.Gray
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(3.dp)
                            )


                            Text(

                                text =
                                    employeeName,

                                fontSize =
                                    16.sp,

                                fontWeight =
                                    FontWeight.Bold
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(2.dp)
                            )


                            Text(

                                text =
                                    employeeId,

                                fontSize =
                                    12.sp,

                                color =
                                    Color.Gray
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

                    text =
                        "Financial Year",

                    fontSize =
                        14.sp,

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

                        readOnly =
                            true,

                        modifier =
                            Modifier
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

                                tint =
                                    APPGreen
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


                    // =============================================
                    // FINANCIAL YEAR DROPDOWN MENU
                    // =============================================

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

                                    errorMessage =
                                        ""

                                    showCertificate =
                                        false
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

                    fontSize =
                        12.sp,

                    color =
                        Color.Gray
                )
            }


            // =================================================
            // ERROR MESSAGE
            // =================================================

            item {

                if (
                    errorMessage.isNotEmpty()
                ) {

                    Text(

                        text =
                            errorMessage,

                        color =
                            Color.Red,

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.Medium
                    )
                }
            }


            // =================================================
            // VIEW CERTIFICATE BUTTON
            // =================================================

            item {

                Button(

                    onClick = {

                        when {

                            selectedFinancialYear.isEmpty() -> {

                                errorMessage =
                                    "Please select a financial year."

                                showCertificate =
                                    false
                            }

                            else -> {

                                errorMessage =
                                    ""

                                /*
                                 * DATABASE/API WILL BE
                                 * CONNECTED HERE.
                                 *
                                 * Example:
                                 *
                                 * getIncomeTaxCertificate(
                                 *     employeeId = employeeId,
                                 *     financialYear =
                                 *         selectedFinancialYear
                                 * )
                                 */

                                showCertificate =
                                    true
                            }
                        }
                    },

                    modifier =
                        Modifier
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
                            "View"
                    )


                    Spacer(
                        modifier =
                            Modifier.width(8.dp)
                    )


                    Text(

                        text =
                            "View Tax Certificate",

                        fontSize =
                            15.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }


            // =================================================
            // DATABASE CERTIFICATE RESULT
            // =================================================

            if (showCertificate) {

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
                            )
                    ) {

                        Column(

                            modifier =
                                Modifier.padding(16.dp)
                        ) {


                            // =================================
                            // CERTIFICATE HEADER
                            // =================================

                            Row(

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.Description,

                                    contentDescription =
                                        "Tax Certificate",

                                    tint =
                                        APPGreen
                                )


                                Spacer(
                                    modifier =
                                        Modifier.width(10.dp)
                                )


                                Text(

                                    text =
                                        "Income Tax Certificate",

                                    fontSize =
                                        18.sp,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        APPGreen
                                )
                            }


                            Spacer(
                                modifier =
                                    Modifier.height(14.dp)
                            )


                            // =================================
                            // EMPLOYEE DETAILS
                            // =================================

                            Text(

                                text =
                                    "Employee: $employeeName",

                                fontSize =
                                    13.sp
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )


                            Text(

                                text =
                                    "Employee ID: $employeeId",

                                fontSize =
                                    13.sp
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )


                            Text(

                                text =
                                    "Financial Year: $selectedFinancialYear",

                                fontSize =
                                    13.sp
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(14.dp)
                            )


                            // =================================
                            // DATABASE DATA AREA
                            // =================================

                            /*
                             * IMPORTANT:
                             *
                             * Do NOT put fake tax figures here.
                             *
                             * Later the actual certificate information
                             * will come from the backend/database.
                             */

                            Text(

                                text =
                                    "Tax certificate details will appear here when the database is connected.",

                                fontSize =
                                    13.sp,

                                color =
                                    Color.Gray
                            )


                            Spacer(
                                modifier =
                                    Modifier.height(18.dp)
                            )


                            // =================================
                            // EXPORT / SAVE AS PDF BUTTON
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
                                        "Income_Tax_Certificate_${employeeId}_${safeYear}.pdf"

                                    /*
                                     * This launches Android's
                                     * system Save As screen.
                                     */

                                    exportLauncher.launch(
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
                                        Icons.Default.Download,

                                    contentDescription =
                                        "Export"
                                )


                                Spacer(
                                    modifier =
                                        Modifier.width(8.dp)
                                )


                                Text(

                                    text =
                                        "Export / Save as PDF",

                                    fontSize =
                                        15.sp,

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