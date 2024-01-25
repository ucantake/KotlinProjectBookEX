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
var NAMEUSER: String = ""
var PASSWORDUSER: String = ""
var EMAIL : String = ""
var ACCOUNT : String = ""
const val GANACHE_RPC_SERVER : String = "http://127.0.0.1:8545" //адрес сервера ganache (блокчейна эфириум)
var BALANCE : String = ""
var JSON : String = ""//json с данными пользователя получаемыми с сервера
var ROLE : String = ""
var DATADOWNLOADING = false
var WindowsName : String = ""
var WORKMODE = "offline"
var JSON_PROFILE : String = ""//json с списком книг, используется на странице профиля, для отображения таблицы книг содержащийся у пользователя
var JSON_SEARCH_USERS_BOOKS : String = ""//json с списком пользователей и книг готовых к обмену, используется на странице создания смарт транзакций
var JSON_TRANSACTION_BOOKS : String = ""//json с списком книг транзакций, используется на домашней странице для подтверждения транзакций
var DOWNLOAD_DATA_ALL = false
var HASH_DATA_DOWNLOAD = ""

const val SECRET_KEY = "YourSecretKeyHere"
val DATE_PUBLICHED = LocalDate.now().year.toString()

val GENRE_BOOKS : List<String> = listOf("Комедия","Проза","Научная","Детская","Детектив", "Роман", "Поэзия", "Приключения","Ужасы","Биография", "Философия","Другое")