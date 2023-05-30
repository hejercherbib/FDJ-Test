package com.hejercherbib.fdj.android.apiTests

import org.junit.Test
/*
internal fun MockWebServer.enqueueFile(fileName: String, code: Int) {
   enqueue(
       MockResponse()
           .setResponseCode(code)
           .setBody(
               javaClass.getResource(fileName)!!.readText()
           )
   )

   @ExperimentalCoroutinesApi
   class LeagueApiTest {
       private val mockWebServer = MockWebServer()

       private val client = OkHttpClient.Builder().build()
       private val service =
           NetworkModule().provideRetrofit(
               client
           )
               .create(LeagueService::class.java)

       @After
       fun tearDown() {
           mockWebServer.shutdown()
       }

       @Test
       fun testDeserializationBig() {
           mockWebServer.enqueueFile("/LeagueApiMock.json", 200)
           runBlocking {
               val actual = service.getAllLeagues()
               Assert.assertEquals(12, actual.body()?.leagues)
           }
       }
   }
}

 */
