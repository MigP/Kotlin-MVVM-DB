package be.bf.android.kotlindemoapp.dal.entities

data class User(
    var username: String,
    var password: String,
    var id: Int? = null,
)