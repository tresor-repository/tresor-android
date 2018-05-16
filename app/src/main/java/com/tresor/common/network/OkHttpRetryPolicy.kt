package com.tresor.common.network

/**
 * @author by alvinatin on 21/04/18.
 */

class OkHttpRetryPolicy(var readTimeout: Long,
                        var writeTimeout: Long,
                        var connectTimeout: Long,
                        var maxRetryAttempt: Long) {
    companion object {


        fun createdDefaultOkHttpRetryPolicy(): OkHttpRetryPolicy {
            return OkHttpRetryPolicy(45, 45, 45, 3)
        }

        fun createdOkHttpNoAutoRetryPolicy(): OkHttpRetryPolicy {
            return OkHttpRetryPolicy(45, 45, 45, 0)
        }
    }

}