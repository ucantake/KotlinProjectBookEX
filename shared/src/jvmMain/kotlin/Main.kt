import kotlinx.coroutines.*
import webservices.HttpApiClient

fun main() {

    println("A")

//    val scope = CoroutineScope(Dispatchers.Default)
//
//    try {
//        val def = scope.async{
//            get()
////            test()
//        }
//        //контрукция которая на сильно закрывает поток, как только он закончит выполнение
//        while (def.isActive){
//            if (def.isActive) Thread.sleep(1)
//            else def.cancel()
//        }
//    }catch (e : Exception){
//        println("E "  + e)
//    }

runHttpData()



    println("B")
}

suspend fun get(){
    try {
        val httpsClietn = HttpApiClient()

        val stri = httpsClietn.authLinkForman("textFieldStateEmail", "textFieldStatePassword")
        println("str = " + stri)
    } catch (e : Exception) {
        println("EXEPTION $e")
    }

}

suspend fun test () {

    for (i in 0..5){
        delay(400L)
        println(i)
    }
    return
}