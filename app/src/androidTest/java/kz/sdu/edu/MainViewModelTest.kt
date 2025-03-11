package kz.sdu.edu

//import android.util.Log
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import kz.sdu.edu.ViewModel.Repository
//import org.junit.Rule
//import org.junit.Test
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class MainViewModelTest {
//
//    @get:Rule
//    val testRule = InstantTaskExecutorRule()
//
//    @Test
//    fun testApiResponse() = runTest {
//        val repository = Repository()
//        val response = repository.getBook(1)  // Тестовый запрос к API
//        Log.d("TEST_RESPONSE", "Response: $response")
//        assert(response.isSuccessful)
//    }
//}