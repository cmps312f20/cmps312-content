package classes

import dataclasses.Book

/*
//Interface does not have an implementation (default implementation)
interface BookRepository {
    fun getBook(isbn: String) : Book
    fun addBook(book: Book)
}

class FileBookRepository() : BookRepository {
    override fun getBook(isbn: String) : Book {
        return Book()
    }

    override fun addBook(book: Book) {}
}

class DBBookRepository() : BookRepository {
    override fun getBook(isbn: String) : Book {
        return Book()
    }

    override fun addBook(book: Book) {}
}


interface Company {
    val company: String
    fun getName() = company
}

interface Employee {
    val first: String
    val last: String

    fun getName() = "$first $last"
}

class CompanyEmployee(
    override val first: String,
    override val last: String,
    override val company: String
) : Company, Employee {

    override fun getName(): String =
        "${super<Employee>.getName()} working for ${super<Company>.getName()}"
}

fun main() {
    val bookRepository : BookRepository
    bookRepository = DBBookRepository()
    // Porgramming more generic
    // 251 - Polymorphism
    bookRepository.getBook("1234")
}
 */