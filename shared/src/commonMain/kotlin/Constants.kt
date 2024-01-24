import java.time.LocalDate
import kotlin.properties.Delegates

const val SERVER_PORT = 8888
const val NAME_DB = "gauti"
const val DB_NAME = "aspasia"
const val USER_PASSWORD = "toJT5GVCF0cgNQqqyVXh"
const val PRIVATE_PASSWORD = "CklevUWlXkJNi1yB3so1DLJna7mFr1FodNHVA0mES4nyao7jTQ"
const val ALIAS = "server"
//const val NAME_CERTIFICATE = "serverStore"
const val NAME_CERTIFICATE = "keystore.jks"
const val BASE_LINK = "YdQsW0JUbekCAT86jHb0ZH6FhoCSxEeQa72TFIX1nCpJRecjKX"
//const val KEY_FILE_PATH = "/var/sftp/uploads/shared/src/commonMain/resources/keyC/v3/$NAME_CERTIFICATE.jks"
const val KEY_FILE_PATH = "/var/sftp/uploads/shared/src/commonMain/resources/$NAME_CERTIFICATE"
const val SERVER_IP = "193.108.114.117"
lateinit var NAMEUSER: String
lateinit var PASSWORDUSER: String
lateinit var EMAIL : String
lateinit var ACCOUNT : String
const val GANACHE_RPC_SERVER : String = "http://127.0.0.1:8545" //адрес сервера ganache (блокчейна эфириум)
lateinit var BALANCE : String
lateinit var JSON : String //json с данными пользователя получаемыми с сервера
lateinit var ROLE : String
var DATADOWNLOADING = false
lateinit var WindowsName : String
var WORKMODE = "offline"
lateinit var JSON_PROFILE : String //json с списком книг
lateinit var JSON_SEARCH_USERS_BOOKS : String //json с списком пользователей и книг готовых к обмену
lateinit var JSON_TRANSACTION_BOOKS : String //json с списком книг транзакций
var DOWNLOAD_DATA_ALL = false
var HASH_DATA_DOWNLOAD = ""

const val SECRET_KEY = "YourSecretKeyHere"
val DATE_PUBLICHED = LocalDate.now().year.toString()

val GENRE_BOOKS : List<String> = listOf("Комедия","Проза","Научная","Детская","Детектив", "Роман", "Поэзия", "Приключения","Ужасы","Биография", "Философия","Другое")