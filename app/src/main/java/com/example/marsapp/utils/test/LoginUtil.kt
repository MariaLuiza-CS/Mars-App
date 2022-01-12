package com.example.marsapp.utils.test

/**
 * the input is not valid if...
 * ...the email/password is empty
 * ...the email does not exists
 * ...the password contains less than 8 digits
 */
object LoginUtil {

    private val existingUsersEmail =
        listOf("malalayousafzai@email.com", "dorothymaestang@email.com", "tiaciata@email.com")

    fun validateLoginInput(
        email: String,
        password: String,
    ): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            return false
        }
        if (password.count { it.isDigit() } < 8) {
            return false
        }
        if (email !in existingUsersEmail) {
            return false
        }
        return true
    }
}