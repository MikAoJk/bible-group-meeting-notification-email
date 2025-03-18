package mikaojk.github.io

import mikaojk.github.io.model.BibleGroupMeeting
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
            )
        )

        val isFutureBibelGroupMeetingNextWeek = isFutureBibelGroupMeetingNextWeek(nearestFutureBibelGroupMeeting(bibleGroupMeetings)!!.date)

        assertEquals(true, isFutureBibelGroupMeetingNextWeek)
    }

    @Test
    fun `Should not be a bible group meeting 7 days from now`() {
        val bibleGroupMeetings = listOf(
            BibleGroupMeeting(
                date = LocalDate.now().plusDays(7),
                who = "Joakim",
                address = "My house",
                theme = "The Bible",
            )
        )

        val isFutureBibelGroupMeetingNextWeek = isFutureBibelGroupMeetingNextWeek(nearestFutureBibelGroupMeeting(bibleGroupMeetings)!!.date)

        assertEquals(false, isFutureBibelGroupMeetingNextWeek)
    }

    @Test
    fun `Should check all emails and be valid`() {
        val environment = Environment()
        val emails: List<String> = environment.emails.trim().split(",")
        val allEmailsAreValid = checkEmails(emails)

        assertEquals(true, allEmailsAreValid)
    }

    @Test
    fun `Should check all emails and find some invalids`() {
        val emails: List<String> = listOf("joakim@gmai.com", "joaskim£gmail.com")
        val allEmailsAreValid = checkEmails(emails)

        assertEquals(false, allEmailsAreValid)
    }

}