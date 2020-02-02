package yetanotherdima.instaclone.models

data class User(val name: String = "",
                val username: String = "",
                val website: String = "",
                val bio: String = "",
                val email: String = "",
                val phone: String = "") : ModelBase() {
    override fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "name" to name,
            "username" to username,
            "website" to website,
            "bio" to bio,
            "email" to email,
            "phone" to phone
        )
    }
}