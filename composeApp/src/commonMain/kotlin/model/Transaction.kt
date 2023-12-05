 /*
    общий объект транзакции, нужен для смарт-контрактов
    нужен для описания транзакции, которая будет отправлена в смарт-контракт
    TODO: добавить валидацию
    TODO: добавить возможность отправки транзакции с помощью web3j
 */
package model

expect interface Transaction {
    val id: String                      //уникальный идентификатор транзакции
    val sender: User                    //отправитель
    val receiver: User                  //получатель
    val amount: Any                     //сумма транзакции
    val smartContract: SmartContract    //смарт-контракт
    val date: String                    //дата транзакции

}