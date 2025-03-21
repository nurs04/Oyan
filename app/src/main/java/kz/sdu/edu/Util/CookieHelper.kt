package kz.sdu.edu.Util

object CookieHelper {
    fun mergeCookies(existing: String, newCookies: String): String {
        val sessionCookieExisting = existing.split(";").find { it.trim().startsWith("sessionid=") }
        val sessionCookieNew = newCookies.split(";").find { it.trim().startsWith("sessionid=") }
        val csrfCookieExisting = existing.split(";").find { it.trim().startsWith("csrftoken=") }
        val csrfCookieNew = newCookies.split(";").find { it.trim().startsWith("csrftoken=") }

        val sessionCookie = sessionCookieNew ?: sessionCookieExisting
        val csrfCookie = csrfCookieNew ?: csrfCookieExisting

        return listOfNotNull(sessionCookie, csrfCookie).joinToString("; ")
    }

    fun extractCsrfToken(cookieString: String): String? {
        return cookieString.split(";")
            .firstOrNull { it.trim().startsWith("csrftoken=") }
            ?.substringAfter("=")
    }
}