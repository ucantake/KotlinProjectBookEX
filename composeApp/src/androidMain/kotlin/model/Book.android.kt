package model

actual interface BookItem {
    actual val id: String
    actual val title: String
    actual val author: String
    actual val genre: String
    actual val description: String
    actual val publicationYear: Int
    actual val isbn: String
    actual val udk: String
    actual val bbk: String
    actual val exchangePrice: Any
}