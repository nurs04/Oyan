package kz.sdu.edu.Util

object Validator {
    fun validateUserName(username : String) : String? {
        return when{
            username.isEmpty() -> "Username is required"
            !username.matches(Regex("^[a-z0-9_]+$")) -> "Only lowercase letters, number and _ allowed"
            else -> null
        }
    }

    fun validateEmail(email : String) : String?{
        return when {
            email.isEmpty() -> "Email is required"
            !email.matches(Regex("^\\S+@\\S+\\.\\S+$")) -> "Invalid email format"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.isEmpty() -> "Password is required"
            password.length < 8 -> "Min 8 characters"
            !password.any { it.isUpperCase() } -> "At least 1 uppercase letter"
            !password.any { it.isDigit() } -> "At least 1 digit"
            else -> null
        }
    }

}