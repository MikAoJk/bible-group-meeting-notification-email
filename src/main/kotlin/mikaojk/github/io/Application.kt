package mikaojk.github.io

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.HttpURLConnection
import java.net.URI
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.abs


val log: Logger = LoggerFactory.getLogger("mikaojk.github.io")

val objectMapper: ObjectMapper =
    ObjectMapper().apply {
        registerKotlinModule()
        registerModule(JavaTimeModule())
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

fun main() {
    val environment = Environment()
    val bibleGroupMeetings = fetchBibleGroupMeetingFromGoogleSheets(environment.googleSheetXlsxUrl)
    val nearestFutureBibelGroupMeeting = nearestFutureBibelGroupMeeting(bibleGroupMeetings)
    if (nearestFutureBibelGroupMeeting != null) {
        emailNotify(environment.sendgridApiKey)
    } else {
        log.info("No bible group meeting in scheduled")
    }
}

data class BibleGroupMeeting(
    val date: LocalDate,
    val who: String,
    val address: String,
    val theme: String
)

fun nearestFutureBibelGroupMeeting(bibelgroupmeetings: List<BibleGroupMeeting>): BibleGroupMeeting? {
    val currentDay = LocalDate.now()

    return bibelgroupmeetings
        .filter { it.date.isAfter(currentDay) }
        .minByOrNull {
            abs(ChronoUnit.DAYS.between(it.date, currentDay))
        }

}


fun fetchBibleGroupMeetingFromGoogleSheets(googleSheetXlsxUrl: String): List<BibleGroupMeeting> {


    val bibleGroupMeetingsGoogleSheetUrl =
        URI.create(googleSheetXlsxUrl)
            .toURL().openConnection() as HttpURLConnection

    val bibleGroupMeetingsWorkbook: Workbook = XSSFWorkbook(bibleGroupMeetingsGoogleSheetUrl.inputStream)
    val bibleGroupMeetingsSheet1: Sheet = bibleGroupMeetingsWorkbook.getSheetAt(0)


    val bibleGroupMeetings = bibleGroupMeetingsSheet1
        .asSequence()
        .filter { !it.getCell(0).getStringValue().matches(Regex("Når")) }
        .filter { !it.getCell(0).getStringValue().matches(Regex("Hos hvem")) }
        .filter { !it.getCell(0).getStringValue().matches(Regex("Adresse")) }
        .filter { !it.getCell(0).getStringValue().matches(Regex("Tema")) }
        .filter { !it.getCell(0).getStringValue().matches(Regex("Bibelvers")) }
        .filter { !it.getCell(0).getStringValue().matches(Regex("")) }
        .map { row ->
            BibleGroupMeeting(
                date = row.getCell(0).localDateTimeCellValue.toLocalDate(),
                who = row.getCell(1).getStringValue(),
                address = row.getCell(2).getStringValue(),
                theme = row.getCell(3).getStringValue()
            )
        }
        .toList()

    bibleGroupMeetingsGoogleSheetUrl.disconnect()

    log.info("done mapping to data class, firse meeting date: ${objectMapper.writeValueAsString(bibleGroupMeetings[0].date)}")

    return bibleGroupMeetings
}

fun Cell.getStringValue(): String {

    return when (this.cellType) {
        CellType.STRING -> {
            this.stringCellValue.toString()
        }

        CellType.NUMERIC -> {
            this.numericCellValue.toInt().toString()
        }

        else -> {
            ""
        }
    }
}

fun emailNotify(sendgridApiKey: String) {
    val from = Email("joakim@joakim-taule-kartveit.no")
    val subject = "Sending with Twilio SendGrid is Fun"
    val to = Email("joakimkartveit@gmail.com")
    val content = Content("text/plain", "and easy to do anywhere, even with Java")
    val mail = Mail(from, subject, to, content)
    val sg = SendGrid(sendgridApiKey)
    val request: Request = Request().apply {
        method = Method.POST
        endpoint = "mail/send"
        body = mail.build()
    }

    val response = sg.api(request)

    if (response.statusCode != 202) {
        log.info("Something whent wrong with sending the email")
        log.info("Status code ${response.statusCode}")
        log.info("body code ${response.body}")
    }

    log.info("All good, email is sendt")
}