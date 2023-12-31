import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import webservices.HttpApiClient

fun runHttpData () {
    val scope = CoroutineScope(Dispatchers.Default)
    try {
        val def = scope.async{
            val httpsClietn = HttpApiClient()

            val stri = httpsClietn.authLinkForman("textFieldStateEmail", "textFieldStatePassword")
            println("str = " + stri)
        }
        //контрукция которая на сильно закрывает поток, как только он закончит выполнение
        while (def.isActive){
            if (def.isActive) Thread.sleep(1)
            else def.cancel()
        }
    }catch (e : Exception){
        println("E "  + e)
    }
}