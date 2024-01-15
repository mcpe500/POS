package Services

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class ApiService(private val baseURL:String) {
    companion object {
        fun create(): ApiService {
            return ApiService("http://localhost:8080")
        }
    }

    private val apiURL: URL = URL(baseURL);

    private fun executeRequest(connection: HttpURLConnection): String {
        val responseCode = connection.responseCode;
        val content: String;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            content =
                BufferedReader(InputStreamReader(connection.inputStream)).use { it.readText() };
        } else {
            content = "Error: $responseCode";
        }
        connection.disconnect()
        return content
    }

    fun get(path: String, headers: Map<String, String> = emptyMap()): String {
        val connection = (apiURL.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            headers.forEach { (key, value) ->
                {
                    setRequestProperty(key, value)
                }
            }
        }
        return executeRequest(connection)
    }

    fun post(
        path: String,
        body: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): String {
        val connection = (apiURL.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            doOutput = true
            headers.forEach { (key, value) ->
                {
                    setRequestProperty(key, value)
                }
            }
            val postData = body.map {
                "${URLEncoder.encode(it.key, "UTF-8")}=${
                    URLEncoder.encode(
                        it.value,
                        "UTF-8"
                    )
                }"
            }.joinToString("&")
            outputStream.write(postData.toByteArray(StandardCharsets.UTF_8))
        }
        return executeRequest(connection)
    }

    fun put(
        path: String,
        body: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): String {
        val connection = (apiURL.openConnection() as HttpURLConnection).apply {
            requestMethod = "PUT"
            doOutput = true
            headers.forEach { (key, value) ->
                {
                    setRequestProperty(key, value)
                }
            }
            val postData = body.map {
                "${URLEncoder.encode(it.key, "UTF-8")}=${
                    URLEncoder.encode(
                        it.value,
                        "UTF-8"
                    )
                }"
            }.joinToString("&")
            outputStream.write(postData.toByteArray(StandardCharsets.UTF_8))
        }
        return executeRequest(connection)
    }

    fun delete(path: String, headers: Map<String, String> = emptyMap()): String {
        val connection = (apiURL.openConnection() as HttpURLConnection).apply {
            requestMethod = "DELETE"
            headers.forEach { (key, value) -> setRequestProperty(key, value) }
        }
        return executeRequest(connection)
    }

    fun patch(
        path: String,
        body: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): String {
        val connection = (apiURL.openConnection() as HttpURLConnection).apply {
            requestMethod = "PATCH"
            doOutput = true
            headers.forEach { (key, value) -> setRequestProperty(key, value) }
            val postData = body.map {
                "${URLEncoder.encode(it.key, "UTF-8")}=${
                    URLEncoder.encode(
                        it.value,
                        "UTF-8"
                    )
                }"
            }
                .joinToString("&")
            outputStream.write(postData.toByteArray(StandardCharsets.UTF_8))
        }
        return executeRequest(connection)
    }

    fun request(
        method: String,
        path: String,
        body: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap()
    ): String {
        val connection = (apiURL.openConnection() as HttpURLConnection).apply {
            requestMethod = method
            doOutput = true
            headers.forEach { (key, value) -> setRequestProperty(key, value) }
            val postData = body.map {
                "${URLEncoder.encode(it.key, "UTF-8")}=${
                    URLEncoder.encode(
                        it.value,
                        "UTF-8"
                    )
                }"
            }
                .joinToString("&")
            outputStream.write(postData.toByteArray(StandardCharsets.UTF_8))
        }
        return executeRequest(connection)
    }

    fun head(path: String, headers: Map<String, String> = emptyMap()): String {
        val connection = (apiURL.openConnection() as HttpURLConnection).apply {
            requestMethod = "HEAD"
            headers.forEach { (key, value) -> setRequestProperty(key, value) }
        }
        return executeRequest(connection)
    }

    fun options(path: String, headers: Map<String, String> = emptyMap()): String {
        val connection = (apiURL.openConnection() as HttpURLConnection).apply {
            requestMethod = "OPTIONS"
            headers.forEach { (key, value) -> setRequestProperty(key, value) }
        }
        return executeRequest(connection)
    }
}
