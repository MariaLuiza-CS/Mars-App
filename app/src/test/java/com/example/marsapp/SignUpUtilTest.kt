package com.example.marsapp

import com.example.marsapp.utils.test.SignUpUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SignUpUtilTest {

    @Test
    fun `empty user name returns false`() {
        val result = SignUpUtil.validateSignUpInput(
            "",
            "123456789",
            "123456789"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`() {
        val result = SignUpUtil.validateSignUpInput(
            "Frida Kahlo",
            "",
            "123456789"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty confirmation password returns false`() {
        val result = SignUpUtil.validateSignUpInput(
            " Joana D'arc ",
            "123456789",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid user name and correct confirmation password returns true`() {
        val result = SignUpUtil.validateSignUpInput(
            "Marie Curie",
            "123456789",
            "123456789"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `user name already exists returns false`() {
        val result = SignUpUtil.validateSignUpInput(
            "Marsha P. Johnson",
            "123456789",
            "123456789"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `confirmation password repeat incorrectly returns false`() {
        val result = SignUpUtil.validateSignUpInput(
            "Anne Frank",
            "123456789",
            "123456798"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password contains less than 8 characters returns false`() {
        val result = SignUpUtil.validateSignUpInput(
            "Bertha Von Suttner",
            "123456",
            "123456"
        )
        assertThat(result).isFalse()
    }

}