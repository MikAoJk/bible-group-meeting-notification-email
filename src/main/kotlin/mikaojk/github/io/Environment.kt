package mikaojk.github.io


data class Environment(
    val googleSheetXlsxUrl: String = getEnvVar("GOOGLE_SHEET_XLSX_URL", "https://docs.google.com/spreadsheets/d/12312454123123/export?format=xlsx#gid=0"),
    val sendgridApiKey: String = getEnvVar("SENDGRID_API_KEY", "superdupersecretkey:)"),
    )

fun getEnvVar(varName: String, defaultValue: String? = null) =
    System.getenv(varName)
        ?: defaultValue ?: throw RuntimeException("Missing required variable \"$varName\"")
