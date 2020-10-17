package qu.cmps312.coroutinebasics.console.examples

class NetworkLibrary {
    fun networkCall(url: String, callback: (String) -> Unit) {
        Thread.sleep(1000)
        callback("networkCall result")
    }
}

/*
Consider the showUserOrders function below that needs to make two network calls,
one to login a user and another to fetch list of user’s order
*/


// Very nice https://proandroiddev.com/async-operations-with-kotlin-coroutines-part-1-c51cc581ad33
// Another good example https://ahsensaeed.com/asynchronous-programming-kotlin-coroutines/
/*
// Synchronous
fun showUserOrders(username: String, password: String) {
    val user = login(username, password)
    val orders = fetchUserOrders(user.userId)
    showUserOrders(orders)
}

fun login(username: String, password: String) { }

fun fetchUserOrders(userId: Long) { }
*/

// Callbacks
/*
If the login and fetchUserOrders functions take callbacks, the implementation will look like this:
*/
/*
fun showUserOrders(username: String, password: String) {
    login(username, password) {
            user -> fetchUserOrders(user.userId) {
            orders -> showUserOrders(orders)
        }
    }
}

fun login(username: String, password: String, callback: (User) -> Unit)
fun fetchUserOrders(userId: Long, callback: (List<Orders>) -> Unit)
*/
/*
Aside the problem with callback hell which is inherent with this approach especially when we have
more functions to compose, it is also difficult to cancel background operations which consequently
leads to memory leaks when the lifecycle owner gets destroyed.
*/

/*
Coroutines allow us to write asynchronous code in a sequential manner while still ensuring that
long running operations are rightly dispatched to run in the background and not block on the UI thread.
The coroutine implementation of the above example is as follows:
*/

/*
fun showUserOrders(username: String, password: String) = GlobalScope.launch(Dispatchers.Main) {
    val user = withContext(Dispatchers.Default) { login(username, password) }
    val orders = withContext(Dispatchers.Default) { fetchUserOrders(user.userId) }
    showUserOrders(orders)
}

suspend fun login(username: String, password: String) { }

suspend fun fetchUserOrders(userId: Long) { }
 */

// https://gist.github.com/vitalybe/34a1d94fad036a66f0c964539093018f
fun main() {
    val networkLibrary = NetworkLibrary()
    networkLibrary.networkCall("test.com") {
        println(it)
    }
}

// Get a new personal access token
// https://github.com/settings/tokens/new
// Access Token: ce44d7bf824fdf1a27b4b622f7163f20a6d92d55
// Get tokens https://github.com/settings/tokens
// GitHub API: https://docs.github.com/en/free-pro-team@latest/rest/guides/getting-started-with-the-rest-api

// https://api.github.com/zen
// -> The response will be a random selection from GitHub design philosophies.

// https://api.github.com/users/erradi
// Get GitHub profile for erradi

// https://api.github.com/users/erradi/repos
// Get erradi repositories

// Get all repos including private ones https://api.github.com/user/repos
// Need to add the authentication token as an HTTP header:
// Authorization: Bearer ce44d7bf824fdf1a27b4b622f7163f20a6d92d55
// Return only the owned repos (not the ones on which she collaborates): https://api.github.com/users/octocat/repos?type=owner

// Get issues https://api.github.com/issues

