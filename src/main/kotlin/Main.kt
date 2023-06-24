import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.cors.*

data class Article(val title: String, val body: String)

fun main() {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    embeddedServer(Netty, port = 8080) {
        install(CORS) {
            allowMethod(HttpMethod.Get)
            anyHost() // WARNING: This allows access from any host, only use this for development
            allowHeader(HttpHeaders.ContentType)
        }
        routing {
            get("/articles") {
                val articles = generateArticles()
                val json = moshi.adapter(List::class.java).toJson(articles)
                call.respondText(json, contentType = ContentType.Application.Json)
            }
        }
    }.start(wait = true)
}

fun generateArticles(): List<Article> {
    val titles = listOf(
        "Article 1 Title",
        "Article 2 Title",
        "Article 3 Title",
        "Article 4 Title",
        "Article 5 Title",
        "Article 6 Title",
        "Article 7 Title",
        "Article 8 Title",
        "Article 9 Title",
        "Article 10 Title"
    )

    val bodies = listOf(
        "This is the body of article 1.",
        "This is the body of article 2.",
        "This is the body of article 3.",
        "This is the body of article 4.",
        "This is the body of article 5.",
        "This is the body of article 6.",
        "This is the body of article 7.",
        "This is the body of article 8.",
        "This is the body of article 9.",
        "This is the body of article 10."
    )

    return List(10) { index -> Article(titles[index], bodies[index]) }
}