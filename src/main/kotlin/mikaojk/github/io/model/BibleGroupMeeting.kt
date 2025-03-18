package mikaojk.github.io.model

import java.time.LocalDate

data class BibleGroupMeeting(
    val date: LocalDate,
    val who: String,
    val address: String,
    val theme: String,
)
