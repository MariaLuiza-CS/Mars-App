package com.example.marsapp.utils.test

/**
 * the input is not valid if...
 * ...the userName/password/confirmPassword is empty
 * ...the userName is already taken
 * ...the confirmed password is not equal as the real password
 * ...the password contains less than 8 digits
 */

object SignUpUtil {

    private val existingUsers = listOf("Rosa Parker", "Angela Davis", "Marsha P. Johnson")

    fun validateSignUpInput(
        userName: String,
        password: String,
        confirmPassword: String,
    ): Boolean {
        if (userName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return false
        }
        if (userName in existingUsers) {
            return false
        }
        if (password.count { it.isDigit() } < 8) {
            return false
        }
        if (confirmPassword != password) {
            return false
        }
        return true
    }
}