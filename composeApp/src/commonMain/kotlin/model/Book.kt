 /*
    общий интерфейс для книги
    TODO сделать валидацию полей, например, название книги не может быть пустым
    TODO сделать валидацию полей, например, год публикации не может быть больше текущего года
    TODO сделать валидацию полей, например, isbn должен быть валидным
    может отсутствовать ISBN, УБК, ББК, но не всё сразу
    TODO exchangePrice сделать приведение к типу BigDecimal
 */

package model

expect interface Book {
    val id: String              //уникальный идентификатор в системе
    val title: String           //название книги
    val author: String          //автор книги
    val genre: String           //жанр книги
    val description: String     //описание книги
    val publicationYear: Int    //год публикации
    val isbn: String            //ISBN код книги
    val udk: String             //УБК код книги
    val bbk: String             //ББК код книги
    val exchangePrice: Any      //цена обмена книги
}