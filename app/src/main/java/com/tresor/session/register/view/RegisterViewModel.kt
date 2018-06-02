package com.tresor.session.register.view

/**
 * @author by alvinatin on 27/04/18.
 */

data class RegisterViewModel(val isRegistered: Boolean,
                             val fields: Fields? = null) {
    data class Fields(val field: String,
                      val message: String)
}