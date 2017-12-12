package coroutines

import org.testng.annotations.Test

class Callback {

    @Test
    fun foo() {
        postItem(Item())
    }
}

private fun requestTokenAsync(cb: (Token) -> Unit) {
    println("request")
    val token = Token("teste")
    cb(token)
}

private fun createPostAsync(token: Token, item: Item, cb: (Post) -> Unit) {
    println("create")
    cb(Post("message"))
}

private fun processPost(post: Post) {
    println("process")
}

private fun postItem(item: Item) {
    requestTokenAsync { token ->
        createPostAsync(token, item) { post ->
            processPost(post)
        }
    }
}

class Item

class Post(val message: String)

class Token(val value: String)