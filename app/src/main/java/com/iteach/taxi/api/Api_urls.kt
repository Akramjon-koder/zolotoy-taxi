package com.iteach.taxi.api

object  Api_urls {
    const val BASE_URL = "http://taxi.biznesgoya.uz"
    const val LOGIN_URL = "/api/driver/user/login"
    const val REGISTR_URL = "/api/driver/user/register"
    const val REGISTR_CODE_URL = "/api/driver/user/verify-code?phone=+998936448111"
    const val DEFINITION_BEGIN_URL = "/api/driver/order/begin"
    const val DEFINITION_END_URL = "/api/driver/order/end"
    const val RESEND_REGISTR_CODE_URL = "/api/driver/user/resend-register-verify-code"

}