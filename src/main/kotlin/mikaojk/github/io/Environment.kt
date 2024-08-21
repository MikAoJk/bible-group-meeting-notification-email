package mikaojk.github.io


data class Environment(
    val googleSheetXlsxUrl: String = getEnvVar("GOOGLE_SHEET_XLSX_URL"),
)

fun getEnvVar(varName: String, defaultValue: String? = null) =
    System.getenv(varName)
        ?: defaultValue ?: throw RuntimeException("Missing required variable \"$varName\"")
