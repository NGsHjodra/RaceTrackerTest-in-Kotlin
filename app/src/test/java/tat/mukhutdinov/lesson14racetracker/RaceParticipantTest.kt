package tat.mukhutdinov.lesson14racetracker

import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import org.junit.Test
import tat.mukhutdinov.lesson14racetracker.ui.RaceParticipant
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

class RaceParticipantTest {
    // happy path

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    @Test
    fun raceStarted_ProgressUpdated() = runTest {
        val raceParticipant = RaceParticipant(
            name = "Test",
            maxProgress = 100,
            progressDelayMillis = 500L,
            initialProgress = 0,
            progressIncrement = 1
        )

        val expectedProgress = 1
        launch { raceParticipant.run() }
        advanceTimeBy(raceParticipant.progressDelayMillis)
        runCurrent()
        assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    @Test
    fun raceFinished_ProgressUpdated() = runTest {
        val raceParticipant = RaceParticipant(
            name = "Test",
            maxProgress = 100,
            progressDelayMillis = 500L,
            initialProgress = 0,
            progressIncrement = 1
        )
        raceParticipant.run()
        assertEquals(100, raceParticipant.currentProgress)
    }

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    @Test
    fun raceStartedHalfway_ProgressUpdated() = runTest {
        val raceParticipant = RaceParticipant(
            name = "Test",
            maxProgress = 100,
            progressDelayMillis = 500L,
            initialProgress = 50,
            progressIncrement = 2
        )

        val expectedProgress = 52
        launch { raceParticipant.run() }
        advanceTimeBy(raceParticipant.progressDelayMillis)
        runCurrent()
        assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    // boundary

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    @Test
    fun raceStartedNoInitialProgress_ProgressUpdated() = runTest {
        val raceParticipant = RaceParticipant(
            name = "Test",
            maxProgress = 100,
            progressDelayMillis = 500L,
//            initialProgress = 0,
            progressIncrement = 1
        )

        val expectedProgress = 1
        launch { raceParticipant.run() }
        advanceTimeBy(raceParticipant.progressDelayMillis)
        runCurrent()
        assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    @Test
    fun raceStartedNoProgressIncrement_ProgressUpdated() = runTest {
        val raceParticipant = RaceParticipant(
            name = "Test",
            maxProgress = 100,
            progressDelayMillis = 500L,
            initialProgress = 0,
//            progressIncrement = 1
        )

        val expectedProgress = 1
        launch { raceParticipant.run() }
        advanceTimeBy(raceParticipant.progressDelayMillis)
        runCurrent()
        assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    // Error path

    @Test
    fun RaceParticipantWronglyInitialized_ErrorFlagUpdated() {
        var ErrorFlag = false
        try {
            val raceParticipant = RaceParticipant(
                name = "Test",
                maxProgress = 100,
                progressDelayMillis = 500L,
                initialProgress = 0,
                progressIncrement = -1
            )
        } catch (exception: IllegalArgumentException) {
            ErrorFlag = true
        }
        assertEquals(true, ErrorFlag)
    }
}
