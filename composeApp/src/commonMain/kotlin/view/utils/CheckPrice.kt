package view.utils

fun CheckPrice (price : String) :Boolean {
    try {
        val num = price.toLong()
    }catch (e : Exception){
        return false
    }
    return true

}