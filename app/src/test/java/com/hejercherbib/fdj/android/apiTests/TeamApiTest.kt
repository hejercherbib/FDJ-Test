package com.hejercherbib.fdj.android.apiTests

import kotlinx.coroutines.ExperimentalCoroutinesApi

// Todo to be fixed
@ExperimentalCoroutinesApi
class TeamApiTest {
   /* @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder().build()
    private val service =
        NetworkModule().provideRetrofit(
            client
        )
            .create(TeamService::class.java)

    @After
    fun shutDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testDeserializationBig() {
        mockWebServer.enqueueFile("/LeagueApiMock.json", 200)
        runBlocking {
            val actual = service.searchAllTeams("French Ligue 1")
            Assert.assertEquals(3, actual.body()?.teams?.size)
        }
    }

    */
}
