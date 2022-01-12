package com.example.marsapp

import com.example.marsapp.utils.test.LoginUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LoginUtilTest {

    @Test
    fun `empty email returns false`() {
        val result = LoginUtil.validateLoginInput(
            "",
            "123456789"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`() {
        val result = LoginUtil.validateLoginInput(
            "tiaciata@email.com",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid email and passowrd returns false`() {
        val result = LoginUtil.validateLoginInput(
            "malalayousafzai@email.com",
            "123456789"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `email does not exists returns false`() {
        val result = LoginUtil.validateLoginInput(
            "mariadapenha@email.com",
            "123456789"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password contains less than 8 characters returns false`() {
        val result = LoginUtil.validateLoginInput(
            "dorothymaestang@email.com",
            "123456"
        )
        assertThat(result).isFalse()
    }
}
