package mikaojk.github.io

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ApplicationTest {

    @Test
    fun `Should be a bible group meeting 6 days from now`() {
        val bibleGroupMeetings = listOf(
            BibleGroupMeeting(
                date = LocalDate.now().plusDays(6),
                who = "Joakim",
                address = "My house",
                theme = "The Bible",
                bibleVerse = "John 3:16"
            )
        )

        val isFutureBibelGroupMeetingNextWeek = isFutureBibelGroupMeetingNextWeek(nearestFutureBibelGroupMeeting(bibleGroupMeetings)!!.date)

        assertEquals(true, isFutureBibelGroupMeetingNextWeek)
    }

}