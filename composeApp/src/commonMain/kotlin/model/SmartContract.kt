 /*
    общий интерфейс для всех смарт-контрактов (специфичнее относительно Transaction)
    нужен для непосредственной связи с блокчейном
    reserveFunds - резервирование средств
    releaseFunds - освобождение средств
    TODO приведение типа amount к BigDecimal
 */
package model

expect interface SmartContract {
    fun reserveFunds(sender: User, receiver: User, amount: Any): Boolean
    fun releaseFunds(sender: User, receiver: User, amount: Any): Boolean
}