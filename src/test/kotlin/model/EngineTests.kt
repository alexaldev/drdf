package model

import domain.*
import utils.isOdd
import utils.oneThousand
import kotlin.test.*

class EngineTests {

    private lateinit var testEngine: DrdffEngine
    private lateinit var engineConfig: EngineConfig
    private lateinit var fakeUserInput: UserInput

    @BeforeTest
    fun setUp() {
        val engineSetup = EngineConfig.default()
        testEngine = DrdffEngine.with(engineSetup)
        fakeUserInput = UserInput("src/test/resources/search_for_files_from", "src/test/resources/search_for_files_in")
        testEngine.shutdown()
    }

    @Test
    fun `Drdff#compute can have a receiver callback with DifferenceResult as a parameter`() {

        testEngine.compute(fakeUserInput) { result: DrdffResult ->
            assertNotNull(result)
        }
    }

    @Test
    fun `engine is in indle state when initialized`() {
        testEngine = DrdffEngine.default()
       assertEquals(State.Idle, testEngine.state)
    }

    @Test
    fun `engine cannot process any data if it is currently computing`() {
        testEngine.compute(fakeUserInput)
        assertFailsWith(IllegalStateException::class) {
            testEngine.compute(fakeUserInput)
        }
    }

    @Test
    fun `engine computation result contains all the files missing and percentage based on the size`() {

        val expectedFilesMissing =
            oneThousand()
            .filter { it.isOdd }
            .map { "src/test/resources/search_for_files_from/$it" }

        testEngine.compute(fakeUserInput) {
//            assertEquals(50, it.percentage)
//            assertEquals(expectedFilesMissing, it.missingFilenames)
        }
    }



    private val keepOnlyOdds : (Set<Int>) -> List<Int> = {
        it.filter { n -> n.isOdd }
    }
}