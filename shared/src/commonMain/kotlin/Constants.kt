const val SERVER_PORT = 8888
const val NAME_DB = "gauti"
const val USER_NAME = "aspasia"
const val USER_PASSWORD = "toJT5GVCF0cgNQqqyVXh"
const val PRIVATE_PASSWORD = "CklevUWlXkJNi1yB3so1DLJna7mFr1FodNHVA0mES4nyao7jTQ"
const val ALIAS = "server"
//const val NAME_CERTIFICATE = "serverStore"
const val NAME_CERTIFICATE = "keystore.jks"
const val BASE_LINK_GET = "YdQsW0JUbekCAT86jHb0ZH6FhoCSxEeQa72TFIX1nCpJRecjKX"
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
val LOCALACCESSDATA = "ASDDSA" //авторизационные данные для локального доступа
val LOCALACCESS = true //локальный доступ для тестирования
lateinit var WORKMODE : String
const val OFFLINEDATA = "{\"user\":{\"name\":\"\",\"email\":\"\",\"role\":\"user\"},\"wallet\":{\"account\":\"0x0000000000\",\"key\":\"0x0000000000\"},\"books\":{\"title\":\"\",\"author\":\"\",\"price\":\"0\",\"quantity\":0},\"balance\":{\"account\":\"0x0000000000\",\"balanceWei\":\"0\",\"balanceEth\":\"0\"}}"